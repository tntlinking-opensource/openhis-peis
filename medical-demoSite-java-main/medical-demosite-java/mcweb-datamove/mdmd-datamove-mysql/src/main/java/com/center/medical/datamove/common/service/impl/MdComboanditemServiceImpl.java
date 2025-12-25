package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdComboanditemMapper;
import com.center.medical.datamove.common.bean.model.MdComboanditem;
import com.center.medical.datamove.common.service.MdComboanditemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 维护最小套餐与收费项目关联表(MdComboanditem)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
@Slf4j
@Service("mdComboanditemService")
@RequiredArgsConstructor
public class MdComboanditemServiceImpl extends ServiceImpl<MdComboanditemMapper, MdComboanditem> implements MdComboanditemService {

    private final MdComboanditemMapper mdComboanditemMapper;

    /**
     * 分页查询[维护最小套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param MdComboanditem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdComboanditem> getPage(PageParam<MdComboanditem> page, MdComboanditem param) {
        return mdComboanditemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdComboanditem getInfoById(String id) {
        return mdComboanditemMapper.getInfoById(id);
    }

}


