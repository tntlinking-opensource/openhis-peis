package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdWzTjrecordMapper;
import com.center.medical.datamove.common.bean.model.MdWzTjrecord;
import com.center.medical.datamove.common.service.MdWzTjrecordService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS问诊——体检记录(MdWzTjrecord)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:26
 */
@Slf4j
@Service("mdWzTjrecordService")
@RequiredArgsConstructor
public class MdWzTjrecordServiceImpl extends ServiceImpl<MdWzTjrecordMapper, MdWzTjrecord> implements MdWzTjrecordService {

    private final MdWzTjrecordMapper mdWzTjrecordMapper;

    /**
     * 分页查询[KS问诊——体检记录]列表
     *
     * @param page  分页参数
     * @param param MdWzTjrecord查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdWzTjrecord> getPage(PageParam<MdWzTjrecord> page, MdWzTjrecord param) {
        return mdWzTjrecordMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdWzTjrecord getInfoById(String id) {
        return mdWzTjrecordMapper.getInfoById(id);
    }

}


