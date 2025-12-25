package com.center.medical.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.KingdeeConstants;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.KdDepartment;
import com.center.medical.system.bean.param.KdDepartmentParam;
import com.center.medical.system.dao.KdDepartmentMapper;
import com.center.medical.system.service.KdDepartmentService;
import com.center.medical.system.utils.KingdeeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * kingdeedepartment(KdDepartment)表服务实现类
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:42
 */
@Slf4j
@Service("kdDepartmentService")
@RequiredArgsConstructor
public class KdDepartmentServiceImpl extends ServiceImpl<KdDepartmentMapper, KdDepartment> implements KdDepartmentService {

    private final KdDepartmentMapper kdDepartmentMapper;

    private final KingdeeUtil kingdeeUtil;

    /**
     * 分页查询[kingdeedepartment]列表
     *
     * @param page  分页参数
     * @param param KdDepartment查询参数
     * @return 分页数据
     */
    @Override
    public IPage<KdDepartment> getPage(PageParam<KdDepartment> page, KdDepartmentParam param) {
        return kdDepartmentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    @Override
    public KdDepartment getInfoById(String id) {
        return kdDepartmentMapper.getInfoById(id);
    }


    @Override
    @Transactional
    public void upgradeDepartmentByKingdee() {
        String branchId = SecurityUtils.getCId();

        String json = kingdeeUtil.send(KingdeeConstants.METHOD_GET_DEPARTMENT);

        baseMapper.delete(new QueryWrapper<>());

        JSONObject jo = JSON.parseObject(json);
        JSONArray ja = jo.getJSONArray("Department");
        List<KdDepartment> kdDepartmentList = new ArrayList<>();
        for (int i = 0; i < ja.size(); i++) {
            JSONObject orgObj = ja.getJSONObject(i);
            KdDepartment kdDepartment = new KdDepartment();
            kdDepartment.setAccountName(orgObj.getString("NAME"));
            kdDepartment.setBranchId(branchId);
            kdDepartment.setAccountNo(orgObj.getString("Number"));
            kdDepartment.setCtDate(orgObj.getDate("CT_DATE"));//换成hutool的json，时间会增加8小时,2021-11-25T11:03:41.413
            kdDepartment.setLtDate(orgObj.getDate("LT_DATE"));
            kdDepartment.setUseStatusId(orgObj.getString("USE_STATUS_ID"));
            kdDepartmentList.add(kdDepartment);
        }
        saveBatch(kdDepartmentList);
    }
}

