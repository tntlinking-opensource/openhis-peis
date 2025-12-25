package com.center.medical.finance.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdCustomer;
import com.center.medical.finance.bean.vo.KdCustomerVo;
import com.center.medical.finance.constant.KingdeeConstants;
import com.center.medical.finance.dao.KdCustomerMapper;
import com.center.medical.finance.service.KdCustomerService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.utils.KingdeeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 金碟账户(KdCustomer)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:42
 */
@Slf4j
@Service("kdCustomerService")
@RequiredArgsConstructor
public class KdCustomerServiceImpl extends ServiceImpl<KdCustomerMapper, KdCustomer> implements KdCustomerService {

    private final KdCustomerMapper kdCustomerMapper;

    private final KingdeeUtil kingdeeUtil;

    private final SysBranchMapper sysBranchMapper;

    private final BranchService branchService;

    /**
     * 分页查询[金碟账户]列表
     *
     * @param page  分页参数
     * @param param KdCustomer查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdCustomer> getPage(PageParam<KdCustomer> page, KdCustomer param) {
        return kdCustomerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    @Override
    public KdCustomer getInfoById(String id) {
        return kdCustomerMapper.getInfoById(id);
    }

    /**
     * 客户升级时调用,获取金蝶系统客户
     */
    @Override
    public String upgradeIdentityByKingdee() {
        String branchId = SecurityUtils.getCId();
        if (StrUtil.isEmpty(branchId)) {
            throw new ServiceException("无法获取当前用户分中心id");
        }
        SysBranch sysBranch = sysBranchMapper.getByBranchId(branchId);
        if (sysBranch == null) {
            throw new ServiceException("当前用户所属分中心不存在，可能已被删除：" + branchId);
        }
        String centerOrgName = sysBranch.getCenterorgname();
        if (StrUtil.isEmpty(centerOrgName)) {
            throw new ServiceException("请联系管理员为分中心【" + sysBranch.getFzx() + "】维护当前中心组织名");
        }
        centerOrgName = KingdeeUtil.EncryptDES(centerOrgName);
        return kingdeeUtil.send(KingdeeConstants.METHOD_GET_CUSTOMER, "OrgName", centerOrgName);
    }

    /**
     * 金蝶客户数据更新
     */
    @Override
    @Transactional
    public void upgradeCustomer(String branchId) {
        if (StrUtil.isEmpty(branchId)) {
            throw new ServiceException("无法获取当前用户分中心id");
        }
        SysBranch sysBranch = sysBranchMapper.getByBranchId(branchId);
        if (sysBranch == null) {
            throw new ServiceException("当前用户所属分中心不存在，可能已被删除：" + branchId);
        }
        String centerOrgName = sysBranch.getCenterorgname();
        if (StrUtil.isEmpty(centerOrgName)) {
            throw new ServiceException("请联系管理员为分中心【" + sysBranch.getFzx() + "】维护当前中心组织名");
        }
        String centerOrgNameEncrypt = KingdeeUtil.EncryptDES(centerOrgName);

        baseMapper.delete(
                new QueryWrapper<KdCustomer>()
                        .eq("centerorgname", centerOrgName)
        );

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_GET_CUSTOMER, "OrgName", centerOrgNameEncrypt);
        JSONObject jo = JSONUtil.parseObj(json);
        JSONArray ja = jo.getJSONArray("Customer");
        List<KdCustomer> kdCustomerList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject customerJo = ja.getJSONObject(i);
            KdCustomer kdCustomer = new KdCustomer();
            kdCustomer.setCenterorgname(centerOrgName);
            kdCustomer.setAccountName(customerJo.getStr("NAME"));
            kdCustomer.setAccountNo(customerJo.getStr("Number"));
            kdCustomer.setBranchId(branchId);
            kdCustomer.setCtDate(kingdeeUtil.getDateFromJson(customerJo, "CT_DATE"));
            kdCustomer.setLtDate(kingdeeUtil.getDateFromJson(customerJo, "LT_DATE"));
            kdCustomer.setUseStatusId(customerJo.getStr("USE_STATUS_ID"));
            kdCustomerList.add(kdCustomer);
        }
        this.saveBatch(kdCustomerList);
    }


    /**
     * 获取金蝶客户
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<KdCustomerVo> getKingdeeCustomerData(PageParam<KdCustomerVo> page, String key) {
//        String centerOrgName = branchService.getCenterOrgName();
        return kdCustomerMapper.getKingdeeCustomerData(page,key);
    }
}

