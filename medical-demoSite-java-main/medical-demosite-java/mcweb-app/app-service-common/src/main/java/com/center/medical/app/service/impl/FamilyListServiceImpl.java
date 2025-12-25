package com.center.medical.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.model.FamilyList;
import com.center.medical.app.common.util.PageParam;
import com.center.medical.app.dao.FamilyListMapper;
import com.center.medical.app.service.FamilyListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 家人列表(FamilyList)服务实现类
 *
 * @author ay
 * @since 2024-03-13 14:31:01
 */
@Slf4j
@Service("familyListService")
@RequiredArgsConstructor
public class FamilyListServiceImpl extends ServiceImpl<FamilyListMapper, FamilyList> implements FamilyListService {

    private final FamilyListMapper familyListMapper;

    /**
     * 分页查询[家人列表]列表
     *
     * @param page  分页参数
     * @param param FamilyList查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FamilyList> getPage(PageParam<FamilyList> page, FamilyList param) {
        return familyListMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public FamilyList getInfoById(String id) {
        return familyListMapper.getInfoById(id);
    }

}

