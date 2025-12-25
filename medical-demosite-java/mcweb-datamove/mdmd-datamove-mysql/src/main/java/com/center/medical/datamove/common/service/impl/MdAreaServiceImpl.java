package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdAreaMapper;
import com.center.medical.datamove.common.bean.model.MdArea;
import com.center.medical.datamove.common.service.MdAreaService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 籍贯表(MdArea)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:06
 */
@Slf4j
@Service("mdAreaService")
@RequiredArgsConstructor
public class MdAreaServiceImpl extends ServiceImpl<MdAreaMapper, MdArea> implements MdAreaService {

    private final MdAreaMapper mdAreaMapper;

    /**
     * 分页查询[籍贯表]列表
     *
     * @param page  分页参数
     * @param param MdArea查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdArea> getPage(PageParam<MdArea> page, MdArea param) {
        return mdAreaMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdArea getInfoById(String id) {
        return mdAreaMapper.getInfoById(id);
    }

}


