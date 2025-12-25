package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdReportUrlMapper;
import com.center.medical.datamove.common.bean.model.MdReportUrl;
import com.center.medical.datamove.common.service.MdReportUrlService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * BG科室报告目录表(MdReportUrl)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:17
 */
@Slf4j
@Service("mdReportUrlService")
@RequiredArgsConstructor
public class MdReportUrlServiceImpl extends ServiceImpl<MdReportUrlMapper, MdReportUrl> implements MdReportUrlService {

    private final MdReportUrlMapper mdReportUrlMapper;

    /**
     * 分页查询[BG科室报告目录表]列表
     *
     * @param page  分页参数
     * @param param MdReportUrl查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdReportUrl> getPage(PageParam<MdReportUrl> page, MdReportUrl param) {
        return mdReportUrlMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdReportUrl getInfoById(String id) {
        return mdReportUrlMapper.getInfoById(id);
    }

}


