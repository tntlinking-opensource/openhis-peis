package com.center.medical.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.HelpDocument;
import com.center.medical.system.dao.HelpDocumentMapper;
import com.center.medical.system.service.HelpDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 帮助文档(HelpDocument)服务实现类
 *
 * @author ay
 * @since 2024-04-24 13:56:58
 */
@Slf4j
@Service("helpDocumentService")
@RequiredArgsConstructor
public class HelpDocumentServiceImpl extends ServiceImpl<HelpDocumentMapper, HelpDocument> implements HelpDocumentService {

    private final HelpDocumentMapper helpDocumentMapper;

    /**
     * 分页查询[帮助文档]列表
     *
     * @param page  分页参数
     * @param param HelpDocument查询参数
     * @return 分页数据
     */
    @Override
    public IPage<HelpDocument> getPage(PageParam<HelpDocument> page, HelpDocument param) {
        return helpDocumentMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public HelpDocument getInfoById(String id) {
        return helpDocumentMapper.getInfoById(id);
    }

}

