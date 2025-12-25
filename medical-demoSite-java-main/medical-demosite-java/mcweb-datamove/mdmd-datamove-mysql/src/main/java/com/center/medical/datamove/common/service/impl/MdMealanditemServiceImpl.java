package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdMealanditemMapper;
import com.center.medical.datamove.common.bean.model.MdMealanditem;
import com.center.medical.datamove.common.service.MdMealanditemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 普通套餐与收费项目关联表(MdMealanditem)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:26
 */
@Slf4j
@Service("mdMealanditemService")
@RequiredArgsConstructor
public class MdMealanditemServiceImpl extends ServiceImpl<MdMealanditemMapper, MdMealanditem> implements MdMealanditemService {

    private final MdMealanditemMapper mdMealanditemMapper;

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param MdMealanditem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdMealanditem> getPage(PageParam<MdMealanditem> page, MdMealanditem param) {
        return mdMealanditemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdMealanditem getInfoById(String id) {
        return mdMealanditemMapper.getInfoById(id);
    }

}


