package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPatienttypeMapper;
import com.center.medical.datamove.common.bean.model.MdPatienttype;
import com.center.medical.datamove.common.service.MdPatienttypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者类型(MdPatienttype)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
@Slf4j
@Service("mdPatienttypeService")
@RequiredArgsConstructor
public class MdPatienttypeServiceImpl extends ServiceImpl<MdPatienttypeMapper, MdPatienttype> implements MdPatienttypeService {

    private final MdPatienttypeMapper mdPatienttypeMapper;

    /**
     * 分页查询[体检者类型]列表
     *
     * @param page  分页参数
     * @param param MdPatienttype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPatienttype> getPage(PageParam<MdPatienttype> page, MdPatienttype param) {
        return mdPatienttypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPatienttype getInfoById(String id) {
        return mdPatienttypeMapper.getInfoById(id);
    }

    ;

}


