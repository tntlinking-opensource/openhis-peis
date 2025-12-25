package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdOccupationDiseastClassMapper;
import com.center.medical.datamove.common.bean.model.MdOccupationDiseastClass;
import com.center.medical.datamove.common.service.MdOccupationDiseastClassService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC职业病种分类(MdOccupationDiseastClass)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:28
 */
@Slf4j
@Service("mdOccupationDiseastClassService")
@RequiredArgsConstructor
public class MdOccupationDiseastClassServiceImpl extends ServiceImpl<MdOccupationDiseastClassMapper, MdOccupationDiseastClass> implements MdOccupationDiseastClassService {

    private final MdOccupationDiseastClassMapper mdOccupationDiseastClassMapper;

    /**
     * 分页查询[JC职业病种分类]列表
     *
     * @param page  分页参数
     * @param param MdOccupationDiseastClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdOccupationDiseastClass> getPage(PageParam<MdOccupationDiseastClass> page, MdOccupationDiseastClass param) {
        return mdOccupationDiseastClassMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdOccupationDiseastClass getInfoById(String id) {
        return mdOccupationDiseastClassMapper.getInfoById(id);
    }

}


