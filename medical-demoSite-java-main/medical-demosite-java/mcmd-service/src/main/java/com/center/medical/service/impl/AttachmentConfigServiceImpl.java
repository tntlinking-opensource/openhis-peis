package com.center.medical.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.AttachmentConfig;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.AttachmentConfigMapper;
import com.center.medical.service.AttachmentConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (AttachmentConfig)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:22
 */
@Slf4j
@Service("attachmentConfigService")
@RequiredArgsConstructor
public class AttachmentConfigServiceImpl extends ServiceImpl<AttachmentConfigMapper, AttachmentConfig> implements AttachmentConfigService {

    private final AttachmentConfigMapper attachmentConfigMapper;


    public final static String REALCONFIG = "real_path";
    public final static String VISITCONIG = "fictitious_path";
    public final static String CONFIG = "doc_config.properties";

    /**
     * 分页查询[]列表
     *
     * @param page 分页参数
     * @return 分页数据
     */
    @Override
    public IPage<AttachmentConfig> getPage(PageParam<AttachmentConfig> page) {
        return attachmentConfigMapper.getPage(page);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public AttachmentConfig getInfoById(String id) {
        return attachmentConfigMapper.getInfoById(id);
    }

    /**
     * 添加或修改
     *
     * @param pac
     * @return
     */
    @Override
    public Boolean saOrUp(AttachmentConfig pac) {
        //没id添加，有id修改
        if (StringUtils.isEmpty(pac.getId())) {
            Long count = attachmentConfigMapper.selectCount(new QueryWrapper<AttachmentConfig>().eq("flag", pac.getFlag()));
            if (count > 0) {
                throw new ServiceException("保存失败，标志不能重复！");
            }
            save(pac);
        } else {
            AttachmentConfig old = attachmentConfigMapper.getInfoById(pac.getId());
            if (ObjectUtils.isEmpty(old)) {
                throw new ServiceException("保存失败，这条记录已经被删除！");
            }
            Long count = attachmentConfigMapper.selectCount(new QueryWrapper<AttachmentConfig>().eq("flag", pac.getFlag()).ne("id", pac.getId()));
            if (count > 0) {
                throw new ServiceException("保存失败，标志不能重复！");
            }
            BeanUtil.copyProperties(pac, old);
            updateById(old);
        }
        return Boolean.TRUE;
    }


    /**
     * 获取查询路径
     *
     * @param att
     * @return
     */
    @Override
    public String getAttVisitPath(Attachment att) {
        String configId = att.getConfigId();
        String visitPath = configId == null ? ToolUtil.getPropert(CONFIG, VISITCONIG) : attachmentConfigMapper.getInfoById(configId).getVisitPath();
        return visitPath + "/" + att.getFilePath();
    }


    /**
     * 获取设置
     *
     * @return
     */
    @Override
    public AttachmentConfig getLatestConfig() {
        List<AttachmentConfig> pacs = attachmentConfigMapper.selectList(new QueryWrapper<AttachmentConfig>().orderByDesc("flag"));
        return pacs.size() == 0 ? null : pacs.get(0);
    }


    /**
     * 获取附件地址
     *
     * @param att
     * @return
     */
    @Override
    public String getPath(Attachment att) {
        String configId = att.getConfigId();
        String path = ObjectUtils.isEmpty(configId) ? "" : attachmentConfigMapper.getInfoById(configId).getRealPath();
        return path + "/" + att.getFilePath();
    }
}

