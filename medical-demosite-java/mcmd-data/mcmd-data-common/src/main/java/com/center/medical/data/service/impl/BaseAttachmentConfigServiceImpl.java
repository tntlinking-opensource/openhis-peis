package com.center.medical.data.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Report;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseAttachmentConfig;
import com.center.medical.data.dao.BaseAttachmentConfigMapper;
import com.center.medical.data.service.BaseAttachmentConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 基础附件配置(BaseAttachmentConfig)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:39
 */
@Slf4j
@Service("baseAttachmentConfigService")
@RequiredArgsConstructor
public class BaseAttachmentConfigServiceImpl extends ServiceImpl<BaseAttachmentConfigMapper, BaseAttachmentConfig> implements BaseAttachmentConfigService {

    private final BaseAttachmentConfigMapper baseAttachmentConfigMapper;
    private final Snowflake snowflake;
    /**
     * 分页查询[基础附件配置]列表
     *
     * @param page  分页参数
     * @param param BaseAttachmentConfig查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseAttachmentConfig> getList(PageParam<BaseAttachmentConfig> page, BaseAttachmentConfig param) {
        return baseAttachmentConfigMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BaseAttachmentConfig getInfoById(String id) {
        return baseAttachmentConfigMapper.getInfoById(id);
    }


    /**
     * 新增或修改数据
     * @param pac 实体对象
     * @return
     */
    @Override
    public String saveOrUpdateBase(BaseAttachmentConfig pac) {
        //没传过来id
        if(ObjectUtils.isEmpty(pac.getId())){
            BaseAttachmentConfig flag = baseAttachmentConfigMapper.selectOne(new QueryWrapper<BaseAttachmentConfig>().eq("flag", pac.getFlag()));
            if(ObjectUtils.isNotEmpty(flag)){
                throw new ServiceException("保存失败，标志不能重复！");
            }
            //新增
            pac.setCreatedate(new Date());
            pac.setId(String.valueOf(snowflake.nextId()));
            save(pac);
        }else{
            BaseAttachmentConfig old = baseAttachmentConfigMapper.selectOne(new QueryWrapper<BaseAttachmentConfig>().eq("id",pac.getId()));
            if(ObjectUtils.isEmpty(old)){
                throw new ServiceException("保存失败，这条记录已经被删除！");
            }
            BaseAttachmentConfig flag = baseAttachmentConfigMapper.selectOne(new QueryWrapper<BaseAttachmentConfig>().eq("flag", pac.getFlag()).ne("id", pac.getId()));
            if(ObjectUtils.isNotEmpty(flag)){
                throw new ServiceException("保存失败，标志不能重复！");
            }
            //保存
            BeanUtils.copyProperties(pac, old, new String[]{"id","createDate","modifyDate"});
            updateById(old);
        }
        return "success";
    }


    @Override
    public String deleteBase(String ids) {
        String[] id = ids.split(",");
        baseAttachmentConfigMapper.delete(new QueryWrapper<BaseAttachmentConfig>().in("id", id));
        return "success";
    }

    @Override
    public String getReportRealPath(Report report) {
        String configId = report.getConfigId();
        String visitPath = "";
        if (StringUtils.isNotEmpty(configId)){
            BaseAttachmentConfig infoById = baseAttachmentConfigMapper.getInfoById(configId);
            visitPath = ObjectUtils.isNotEmpty(infoById)?infoById.getRealPath():FileTypePath.REAL_PATH;
        }
        return visitPath+report.getUrlPdf();
    }

    @Override
    public BaseAttachmentConfig getLatestConfig() {
        List<BaseAttachmentConfig> latestConfig = baseAttachmentConfigMapper.getLatestConfig();
        return CollectionUtil.isEmpty(latestConfig)?null:latestConfig.get(0);
    }

    /**
     * 获取附件的地址
     * @param att
     * @return
     */
    @Override
    public String getAttRealPath(Attachment att) {
        String configId = att.getConfigId();
        String visitPath = ObjectUtils.isEmpty(configId) ? "" :baseAttachmentConfigMapper.getInfoById(configId).getRealPath();
        return visitPath+att.getFilePath();
    }
}

