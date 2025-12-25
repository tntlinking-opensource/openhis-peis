package com.center.medical.finance.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.AccountNameDto;
import com.center.medical.finance.bean.model.KdPayway;
import com.center.medical.finance.bean.vo.KingdeeReserWayVo;
import com.center.medical.finance.constant.KingdeeConstants;
import com.center.medical.finance.dao.KdPaywayMapper;
import com.center.medical.finance.service.KdPaywayService;
import com.center.medical.system.utils.KingdeeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * kingdeepayway(KdPayway)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
@Slf4j
@Service("kdPaywayService")
@RequiredArgsConstructor
public class KdPaywayServiceImpl extends ServiceImpl<KdPaywayMapper, KdPayway> implements KdPaywayService {

    private final KdPaywayMapper kdPaywayMapper;

    private final KingdeeUtil kingdeeUtil;

    /**
     * 分页查询[kingdeepayway]列表
     *
     * @param page  分页参数
     * @param param KdPayway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdPayway> getPage(PageParam<KdPayway> page, KdPayway param) {
        return kdPaywayMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    @Override
    public KdPayway getInfoById(String id) {
        return kdPaywayMapper.getInfoById(id);
    }

    /**
     * 获取金蝶支付手段
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public PageParam<KdPayway> getKingdeeData(PageParam<KdPayway> page, String key) {
        QueryWrapper<KdPayway> queryWrapper = new QueryWrapper<>();
        //获取所有数据
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.like("account_name", key);
        }
        String[] usestatuslist = {"0", "1"};
        queryWrapper.in("use_status_id", usestatuslist);
        PageParam<KdPayway> kingdeepaywayPageParam = kdPaywayMapper.selectPage(page, queryWrapper);
        return kingdeepaywayPageParam;
    }


    /**
     * 获取银行结算方式列表
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<KingdeeReserWayVo> getKingdeeReserWay(PageParam<KingdeeReserWayVo> page, String key) {
        if (ObjectUtils.isNotEmpty(key)){
            //去除空格
            key = key.trim();
        }
        return kdPaywayMapper.getKingdeeReserWay(page, key);
    }

    /**
     * 收费方式-金蝶数据更新
     */
    @Override
    @Transactional
    public void upgradeKingdeePayWay() {
        String branchId = SecurityUtils.getCId();
        kdPaywayMapper.delete(new LambdaQueryWrapper<KdPayway>().eq(KdPayway::getBranchId,branchId));
        
        String json = kingdeeUtil.send(KingdeeConstants.METHOD_GET_FYXM);
        JSONObject jo = JSONUtil.parseObj(json);
        JSONArray ja = jo.getJSONArray("FYXM");
        List<KdPayway> kdPayways = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject kdPaywayJo = ja.getJSONObject(i);
            KdPayway kdPayway = new KdPayway();
            kdPayway.setBranchId(branchId);
            kdPayway.setAccountName(kdPaywayJo.getStr("ACCOUNT_NAME"));
            kdPayway.setCtDate(kingdeeUtil.getDateFromJson(kdPaywayJo, "CT_DATE"));
            kdPayway.setLtDate(kingdeeUtil.getDateFromJson(kdPaywayJo, "LT_DATE"));
            kdPayway.setUseStatusId(kdPaywayJo.getStr("USE_STATUS_ID"));
            kdPayway.setAccountNo(kdPaywayJo.getStr("ACCOUNT_NO"));
            kdPayways.add(kdPayway);
        }
        saveBatch(kdPayways);
    }


    /**
     * 获取账户名称
     * @param way
     * @return
     */
    @Override
    public List<AccountNameDto> getAccountName(String way) {
        return kdPaywayMapper.getAccountName(way);
    }
}

