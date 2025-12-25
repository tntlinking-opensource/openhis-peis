/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 */

package com.center.medical.app.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.NotifyTemplate;
import com.center.medical.app.bean.model.NotifyTemplateTag;
import com.center.medical.app.bean.param.UserTagParam;
import com.center.medical.app.common.util.PageAdapter;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.dao.NotifyTemplateMapper;
import com.center.medical.app.dao.NotifyTemplateTagMapper;
import com.center.medical.app.service.NotifyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author lhd
 * @date 2020-07-01 15:44:27
 */
@Service
public class NotifyTemplateServiceImpl extends ServiceImpl<NotifyTemplateMapper, NotifyTemplate> implements NotifyTemplateService {

    @Autowired
    private NotifyTemplateMapper notifyTemplateMapper;
    @Autowired
    private NotifyTemplateTagMapper notifyTemplateTagMapper;

    @Override
    public IPage<NotifyTemplate> pageTagNotify(PageParam<NotifyTemplate> page) {
        List<NotifyTemplate> notifyTemplates = notifyTemplateMapper.pageTagNotify(new PageAdapter(page));
        for (NotifyTemplate notifyTemplate : notifyTemplates) {
            List<UserTagParam> tagParams = notifyTemplate.getTagParams();
            // 拼接字符串
            StringBuilder tagNames = new StringBuilder(100);
            for (UserTagParam tagParam : tagParams) {
                tagNames.append(tagParam.getTagName()).append(StrUtil.COMMA);
            }
            if (tagNames.length() > 0) {
                tagNames.subSequence(0, Math.min(tagNames.length() - 1, 100));
                if (tagNames.lastIndexOf(",") == tagNames.length() - 1) {
                    tagNames.deleteCharAt(tagNames.length() - 1);
                }
            }
            notifyTemplate.setApp(true);
            notifyTemplate.setTemplateCode(tagNames.toString());
        }
        page.setRecords(notifyTemplates);
        page.setTotal(notifyTemplateMapper.countTagNotify());
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplateInfoById(Long templateId) {
        notifyTemplateMapper.deleteById(templateId);
        notifyTemplateTagMapper.delete(Wrappers.lambdaQuery(NotifyTemplateTag.class).eq(NotifyTemplateTag::getTemplateId, templateId));
    }

    @Override
    public NotifyTemplate getInfoById(Long templateId) {
        return notifyTemplateMapper.getInfoById(templateId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfoById(NotifyTemplate notifyTemplate) {
        if (Objects.isNull(notifyTemplate.getTemplateId()) || CollUtil.isEmpty(notifyTemplate.getSelTagIds())) {
            return;
        }
        notifyTemplateTagMapper.delete(Wrappers.lambdaQuery(NotifyTemplateTag.class).eq(NotifyTemplateTag::getTemplateId, notifyTemplate.getTemplateId()));
        notifyTemplateTagMapper.insertBatch(notifyTemplate);
        notifyTemplateMapper.updateById(notifyTemplate);
    }

    /**
     * 短信内容替换
     *
     * @param maps 替换内容
     */
    private String replaceContent(Map<String, String> maps, String content) {
        for (Map.Entry<String, String> element : maps.entrySet()) {
            if (StrUtil.isNotBlank(element.getValue())) {
                content = content.replace("${" + element.getKey() + "}", element.getValue());
            }
        }
        return content;
    }

}
