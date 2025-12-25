package com.center.medical.finance.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.KdOrganization;
import com.center.medical.finance.constant.KingdeeConstants;
import com.center.medical.finance.dao.KdOrganizationMapper;
import com.center.medical.finance.service.KdOrganizationService;
import com.center.medical.system.utils.KingdeeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * kingdeeorganization(KdOrganization)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
@Slf4j
@Service("kdOrganizationService")
@RequiredArgsConstructor
public class KdOrganizationServiceImpl extends ServiceImpl<KdOrganizationMapper, KdOrganization> implements KdOrganizationService {

    private final KdOrganizationMapper kdOrganizationMapper;
    private final KingdeeUtil kingdeeUtil;

    /**
     * 分页查询[kingdeeorganization]列表
     *
     * @param page  分页参数
     * @param param KdOrganization查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdOrganization> getPage(PageParam<KdOrganization> page, KdOrganization param) {
        return kdOrganizationMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键orgId
     * @return 详情信息
     */
    @Override
    public KdOrganization getInfoById(String id) {
        return kdOrganizationMapper.getInfoById(id);
    }

    /**
     * 金蝶组织更新
     */
    @Override
    @Transactional
    public void upgradeKingdeeOrganization() {
        String branchId = SecurityUtils.getCId();

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_GET_ORGANIZATION);

        baseMapper.delete(new QueryWrapper<>());

        JSONObject jo = JSONUtil.parseObj(json);
        JSONArray ja = jo.getJSONArray("Organization");
        List<KdOrganization> kdOrganizations = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject orgObj = ja.getJSONObject(i);
            KdOrganization kdOrganization = new KdOrganization();
            kdOrganization.setOrgId(orgObj.getStr("OrgID"));
            kdOrganization.setOrgName(orgObj.getStr("OrgName"));
            kdOrganization.setOrgNumber(orgObj.getStr("OrgNumber"));
            kdOrganization.setParentId(orgObj.getStr("ParentID"));
            kdOrganization.setBranchId(branchId);
            kdOrganizations.add(kdOrganization);
        }
        saveBatch(kdOrganizations);
    }

}

