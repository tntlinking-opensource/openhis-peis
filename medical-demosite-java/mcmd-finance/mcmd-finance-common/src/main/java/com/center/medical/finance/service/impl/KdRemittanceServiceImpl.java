package com.center.medical.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.KingdeeConstants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.redis.RedisSetUtil;
import com.center.medical.finance.bean.model.KdRemittance;
import com.center.medical.finance.bean.param.KingdeeRemittanceParam;
import com.center.medical.finance.dao.KdRemittanceMapper;
import com.center.medical.finance.service.KdRemittanceService;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.utils.KingdeeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * kingdeeremittance(KdRemittance)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:44
 */
@Slf4j
@Service("kdRemittanceService")
@RequiredArgsConstructor
public class KdRemittanceServiceImpl extends ServiceImpl<KdRemittanceMapper, KdRemittance> implements KdRemittanceService {

    private final KdRemittanceMapper kdRemittanceMapper;
    private final KingdeeUtil kingdeeUtil;
    private final SysBranchMapper sysBranchMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[kingdeeremittance]列表
     *
     * @param page  分页参数
     * @param param KdRemittance查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdRemittance> getPage(PageParam<KdRemittance> page, KdRemittance param) {
        return kdRemittanceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键fid
     * @return 详情信息
     */
    @Override
    public KdRemittance getInfoById(String id) {
        return kdRemittanceMapper.getInfoById(id);
    }

    /**
     * 银行交易流水更新
     *
     * @param param
     */
    @Override
    @Transactional
    public void upgradeKingdeeRemittanceByKingdee(KingdeeRemittanceParam param) {

        if (CollectionUtil.isEmpty(param.getBranchList())) {
            String branchId = SecurityUtils.getCId();
            if (StrUtil.isEmpty(branchId)) {
                throw new ServiceException("分中心ID不能为空！");
            }
            param.setBranchList(Arrays.asList(branchId));

        }
        for (String branchId : param.getBranchList()) {
            SysBranch sysBranch = sysBranchMapper.getByBranchId(branchId);
            if (sysBranch == null) {
                throw new ServiceException("当前用户所属分中心不存在，可能已被删除：" + branchId);
            }
            String centerOrgName = sysBranch.getCenterorgname();
            if (StrUtil.isEmpty(centerOrgName)) {
                throw new ServiceException("请联系管理员为分中心【" + sysBranch.getFzx() + "】维护当前中心组织名");
            }

            Map<String, String> params = new HashMap<>();
            params.put("StartDate", param.getStartTime());
            params.put("EndDate", param.getEndTime());
            params.put("OrgName", centerOrgName);
            String json = kingdeeUtil.send(KingdeeConstants.METHOD_GET_RECEIVE_BILL, params);
            log.warn("银行交易流水更新请求结果：{}", json);

            JSONObject jo = JSONUtil.parseObj(json);
            JSONArray ja = jo.getJSONArray("ReceiveBill");
            if (ja.size() > 0) {
                List<String> updateList = new ArrayList<>();
                List<KdRemittance> kdRemittances = new ArrayList<>();
                for (int i = 0; i < ja.size(); i++) {
                    JSONObject remittanceJo = ja.getJSONObject(i);
                    String fid = remittanceJo.getStr("FID");
                    KdRemittance kdRemittance = new KdRemittance();
                    kdRemittance.setBranchId(branchId);
                    kdRemittance.setFid(fid);
                    kdRemittance.setIncome(remittanceJo.getStr("Amount"));
                    kdRemittance.setRemitter(remittanceJo.getStr("PayUnit"));
                    //2025.11.28 这个结算号有传空的情况,如果是空的就用fid
                    String settleNo = remittanceJo.getStr("SettleNo");
                    kdRemittance.setTransactionnumber(StringUtils.isNotBlank(settleNo) ? settleNo : fid);
                    kdRemittance.setTransdate(kingdeeUtil.getDateFromJson(remittanceJo, "FDate"));
                    kdRemittance.setOnline(ZhongkangConfig.isOnline()?1:0);
                    updateList.add(fid);
                    Long count = baseMapper.selectCount(
                            new QueryWrapper<KdRemittance>()
                                    .eq("fid", fid)
                    );
                    if (count > 0) {
                        continue;
                    }
                    log.warn("银行交易流水更新记录：{}", kdRemittance);
                    kdRemittances.add(kdRemittance);
                }
                saveBatch(kdRemittances);
                //手动执行同步
                for (KdRemittance kr : kdRemittances) {
                    //执行同步
                    SyncData syncData = new SyncData();
                    syncData.setOptType(2);
                    syncData.setBizDb("medical_prod");
                    syncData.setBizTable("kd_remittance");
                    syncData.setBizModifydate(new Date());
                    syncData.setIsAll(0);
                    syncData.setStatus(0);
                    syncData.setBizId(kr.getFid());
                    RedisSetUtil.addToSortedSet(CacheConstants.SYNC_DATA_OBJECTS, syncData, DateUtil.currentSeconds());
                }

                params = new HashMap<>();
                params.put("ids", CollectionUtil.join(updateList, ","));
                params.put("OrgName", centerOrgName);
                kingdeeUtil.send(KingdeeConstants.METHOD_UPDATE_FLAG, params);
            }
        }

    }
}

