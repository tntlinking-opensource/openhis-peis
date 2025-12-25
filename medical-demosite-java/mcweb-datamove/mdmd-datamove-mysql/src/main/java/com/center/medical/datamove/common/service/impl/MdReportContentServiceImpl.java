package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReportContentMapper;
import com.center.medical.datamove.common.bean.model.MdReportContent;
import com.center.medical.datamove.common.service.MdReportContentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 生成报告内容(MdReportContent)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
@Slf4j
@Service("mdReportContentService")
@RequiredArgsConstructor
public class MdReportContentServiceImpl extends ServiceImpl<MdReportContentMapper, MdReportContent> implements MdReportContentService {

    private final MdReportContentMapper mdReportContentMapper;

    /**
     * 分页查询[生成报告内容]列表
     *
     * @param page  分页参数
     * @param param MdReportContent查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReportContent> getPage(PageParam<MdReportContent> page, MdReportContent param) {
        return mdReportContentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReportContent getInfoById(String id) {
        return mdReportContentMapper.getInfoById(id);
    }

}


