package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBusinessCatMapper;
import com.center.medical.datamove.common.bean.model.MdBusinessCat;
import com.center.medical.datamove.common.service.MdBusinessCatService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务类型(MdBusinessCat)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:13
 */
@Slf4j
@Service("mdBusinessCatService")
@RequiredArgsConstructor
public class MdBusinessCatServiceImpl extends ServiceImpl<MdBusinessCatMapper, MdBusinessCat> implements MdBusinessCatService {

    private final MdBusinessCatMapper mdBusinessCatMapper;

    /**
     * 分页查询[业务类型]列表
     *
     * @param page  分页参数
     * @param param MdBusinessCat查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBusinessCat> getPage(PageParam<MdBusinessCat> page, MdBusinessCat param) {
        return mdBusinessCatMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键typeId
     * @return 详情信息
     */
    @Override
    public MdBusinessCat getInfoById(Object id) {
        return mdBusinessCatMapper.getInfoById(id);
    }

}


