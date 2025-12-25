package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatientexamitem;
import com.center.medical.olddata.dao.OrPeispatientexamitemMapper;
import com.center.medical.olddata.service.OrPeispatientexamitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * LIS结果(Peispatientexamitem)服务实现类
 *
 * @author ay
 * @since 2024-06-05 15:00:11
 */
@Slf4j
@Service("orPeispatientexamitemService")
@RequiredArgsConstructor
public class OrPeispatientexamitemServiceImpl extends ServiceImpl<OrPeispatientexamitemMapper, OrPeispatientexamitem> implements OrPeispatientexamitemService {

    private final OrPeispatientexamitemMapper orPeispatientexamitemMapper;

    /**
     * 分页查询[LIS结果]列表
     *
     * @param page  分页参数
     * @param param Peispatientexamitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OrPeispatientexamitem> getPage(PageParam<OrPeispatientexamitem> page, OrPeispatientexamitem param) {
        return orPeispatientexamitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public OrPeispatientexamitem getInfoById(String id) {
        return orPeispatientexamitemMapper.getInfoById(id);
    }

    /**
     * 通过体检号查询
     * @param patientCode
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public List<OrPeispatientexamitem> getListByPatientCode(String patientCode) {
        return orPeispatientexamitemMapper.selectList(new LambdaQueryWrapper<OrPeispatientexamitem>()
                .eq(OrPeispatientexamitem::getPatientcode,patientCode));
    }
}

