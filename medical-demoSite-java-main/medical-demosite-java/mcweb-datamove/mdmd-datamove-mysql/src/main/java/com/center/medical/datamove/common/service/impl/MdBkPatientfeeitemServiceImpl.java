package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBkPatientfeeitemMapper;
import com.center.medical.datamove.common.bean.model.MdBkPatientfeeitem;
import com.center.medical.datamove.common.service.MdBkPatientfeeitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者收费项目(MdBkPatientfeeitem)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
@Slf4j
@Service("mdBkPatientfeeitemService")
@RequiredArgsConstructor
public class MdBkPatientfeeitemServiceImpl extends ServiceImpl<MdBkPatientfeeitemMapper, MdBkPatientfeeitem> implements MdBkPatientfeeitemService {

    private final MdBkPatientfeeitemMapper mdBkPatientfeeitemMapper;

    /**
     * 分页查询[体检者收费项目]列表
     *
     * @param page  分页参数
     * @param param MdBkPatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBkPatientfeeitem> getPage(PageParam<MdBkPatientfeeitem> page, MdBkPatientfeeitem param) {
        return mdBkPatientfeeitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBkPatientfeeitem getInfoById(String id) {
        return mdBkPatientfeeitemMapper.getInfoById(id);
    }

}


