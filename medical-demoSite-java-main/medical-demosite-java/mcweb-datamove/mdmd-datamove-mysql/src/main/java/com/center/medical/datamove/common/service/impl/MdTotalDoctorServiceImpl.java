package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTotalDoctorMapper;
import com.center.medical.datamove.common.bean.model.MdTotalDoctor;
import com.center.medical.datamove.common.service.MdTotalDoctorService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 总检-医生 关联表(MdTotalDoctor)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:10
 */
@Slf4j
@Service("mdTotalDoctorService")
@RequiredArgsConstructor
public class MdTotalDoctorServiceImpl extends ServiceImpl<MdTotalDoctorMapper, MdTotalDoctor> implements MdTotalDoctorService {

    private final MdTotalDoctorMapper mdTotalDoctorMapper;

    /**
     * 分页查询[总检-医生 关联表]列表
     *
     * @param page  分页参数
     * @param param MdTotalDoctor查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTotalDoctor> getPage(PageParam<MdTotalDoctor> page, MdTotalDoctor param) {
        return mdTotalDoctorMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTotalDoctor getInfoById(String id) {
        return mdTotalDoctorMapper.getInfoById(id);
    }

    ;

}


