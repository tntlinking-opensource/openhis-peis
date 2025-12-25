package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdUpperowerMapper;
import com.center.medical.datamove.common.bean.model.MdUpperower;
import com.center.medical.datamove.common.service.MdUpperowerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 上下级关系管理表(MdUpperower)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:14
 */
@Slf4j
@Service("mdUpperowerService")
@RequiredArgsConstructor
public class MdUpperowerServiceImpl extends ServiceImpl<MdUpperowerMapper, MdUpperower> implements MdUpperowerService {

    private final MdUpperowerMapper mdUpperowerMapper;

    /**
     * 分页查询[上下级关系管理表]列表
     *
     * @param page  分页参数
     * @param param MdUpperower查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdUpperower> getPage(PageParam<MdUpperower> page, MdUpperower param) {
        return mdUpperowerMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdUpperower getInfoById(String id) {
        return mdUpperowerMapper.getInfoById(id);
    }

}


