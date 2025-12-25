package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PeispatientAbnormalIgnore;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientAbnormalIgnoreMapper;
import com.center.medical.service.PeispatientAbnormalIgnoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 体检者异常忽视关联表(PeispatientAbnormalIgnore)服务实现类
 *
 * @author makejava
 * @since 2024-10-12 15:36:21
 */
@Slf4j
@Service("peispatientAbnormalIgnoreService")
@RequiredArgsConstructor
public class PeispatientAbnormalIgnoreServiceImpl extends ServiceImpl<PeispatientAbnormalIgnoreMapper, PeispatientAbnormalIgnore> implements PeispatientAbnormalIgnoreService {

    private final PeispatientAbnormalIgnoreMapper peispatientAbnormalIgnoreMapper;

    /**
     * 分页查询[体检者异常忽视关联表]列表
     *
     * @param page  分页参数
     * @param param PeispatientAbnormalIgnore查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientAbnormalIgnore> getPage(PageParam<PeispatientAbnormalIgnore> page, PeispatientAbnormalIgnore param) {
        return peispatientAbnormalIgnoreMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PeispatientAbnormalIgnore getInfoById(String id) {
        return peispatientAbnormalIgnoreMapper.getInfoById(id);
    }

    /**
     * 忽略数据
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean ignore(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)){
            throw new ServiceException("ids不能为空!");
        }

        String creator = SecurityUtils.getUserNo();
        List<PeispatientAbnormalIgnore> list = new ArrayList<>();
        for (String id : ids) {
            PeispatientAbnormalIgnore peispatientAbnormalIgnore = new PeispatientAbnormalIgnore(new Date(),id,creator);
            list.add(peispatientAbnormalIgnore);
        }
        saveBatch(list);
        return Boolean.TRUE;
    }
}

