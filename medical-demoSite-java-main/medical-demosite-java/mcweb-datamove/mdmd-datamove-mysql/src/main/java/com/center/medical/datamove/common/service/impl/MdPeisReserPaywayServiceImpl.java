package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPeisReserPaywayMapper;
import com.center.medical.datamove.common.bean.model.MdPeisReserPayway;
import com.center.medical.datamove.common.service.MdPeisReserPaywayService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检者结算方式表(MdPeisReserPayway)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:08
 */
@Slf4j
@Service("mdPeisReserPaywayService")
@RequiredArgsConstructor
public class MdPeisReserPaywayServiceImpl extends ServiceImpl<MdPeisReserPaywayMapper, MdPeisReserPayway> implements MdPeisReserPaywayService {

    private final MdPeisReserPaywayMapper mdPeisReserPaywayMapper;

    /**
     * 分页查询[体检者结算方式表]列表
     *
     * @param page  分页参数
     * @param param MdPeisReserPayway查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeisReserPayway> getPage(PageParam<MdPeisReserPayway> page, MdPeisReserPayway param) {
        return mdPeisReserPaywayMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeisReserPayway getInfoById(String id) {
        return mdPeisReserPaywayMapper.getInfoById(id);
    }

}


