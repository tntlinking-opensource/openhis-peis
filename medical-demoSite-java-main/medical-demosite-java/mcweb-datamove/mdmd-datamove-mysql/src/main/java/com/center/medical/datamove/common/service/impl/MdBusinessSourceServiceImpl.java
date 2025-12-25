package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdBusinessSourceMapper;
import com.center.medical.datamove.common.bean.model.MdBusinessSource;
import com.center.medical.datamove.common.service.MdBusinessSourceService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 合作第三方(MdBusinessSource)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
@Slf4j
@Service("mdBusinessSourceService")
@RequiredArgsConstructor
public class MdBusinessSourceServiceImpl extends ServiceImpl<MdBusinessSourceMapper, MdBusinessSource> implements MdBusinessSourceService {

    private final MdBusinessSourceMapper mdBusinessSourceMapper;

    /**
     * 分页查询[合作第三方]列表
     *
     * @param page  分页参数
     * @param param MdBusinessSource查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdBusinessSource> getPage(PageParam<MdBusinessSource> page, MdBusinessSource param) {
        return mdBusinessSourceMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdBusinessSource getInfoById(Object id) {
        return mdBusinessSourceMapper.getInfoById(id);
    }

}


