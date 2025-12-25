package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPacsSectionResultTwoMapper;
import com.center.medical.datamove.common.bean.model.MdPacsSectionResultTwo;
import com.center.medical.datamove.common.service.MdPacsSectionResultTwoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PACS-科室结果表(MdPacsSectionResultTwo)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:36
 */
@Slf4j
@Service("mdPacsSectionResultTwoService")
@RequiredArgsConstructor
public class MdPacsSectionResultTwoServiceImpl extends ServiceImpl<MdPacsSectionResultTwoMapper, MdPacsSectionResultTwo> implements MdPacsSectionResultTwoService {

    private final MdPacsSectionResultTwoMapper mdPacsSectionResultTwoMapper;

    /**
     * 分页查询[PACS-科室结果表]列表
     *
     * @param page  分页参数
     * @param param MdPacsSectionResultTwo查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPacsSectionResultTwo> getPage(PageParam<MdPacsSectionResultTwo> page, MdPacsSectionResultTwo param) {
        return mdPacsSectionResultTwoMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPacsSectionResultTwo getInfoById(String id) {
        return mdPacsSectionResultTwoMapper.getInfoById(id);
    }

}


