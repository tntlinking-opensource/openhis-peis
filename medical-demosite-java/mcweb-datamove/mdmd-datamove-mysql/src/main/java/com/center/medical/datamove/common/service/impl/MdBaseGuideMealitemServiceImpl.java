package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBaseGuideMealitemMapper;
import com.center.medical.datamove.common.bean.model.MdBaseGuideMealitem;
import com.center.medical.datamove.common.service.MdBaseGuideMealitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础收费项目(MdBaseGuideMealitem)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:10
 */
@Slf4j
@Service("mdBaseGuideMealitemService")
@RequiredArgsConstructor
public class MdBaseGuideMealitemServiceImpl extends ServiceImpl<MdBaseGuideMealitemMapper, MdBaseGuideMealitem> implements MdBaseGuideMealitemService {

    private final MdBaseGuideMealitemMapper mdBaseGuideMealitemMapper;

    /**
     * 分页查询[基础收费项目]列表
     *
     * @param page  分页参数
     * @param param MdBaseGuideMealitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBaseGuideMealitem> getPage(PageParam<MdBaseGuideMealitem> page, MdBaseGuideMealitem param) {
        return mdBaseGuideMealitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBaseGuideMealitem getInfoById(String id) {
        return mdBaseGuideMealitemMapper.getInfoById(id);
    }

}


