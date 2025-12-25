package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdHarmMapper;
import com.center.medical.datamove.common.bean.model.MdHarm;
import com.center.medical.datamove.common.service.MdHarmService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * JC危害因素(MdHarm)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
@Slf4j
@Service("mdHarmService")
@RequiredArgsConstructor
public class MdHarmServiceImpl extends ServiceImpl<MdHarmMapper, MdHarm> implements MdHarmService {

    private final MdHarmMapper mdHarmMapper;

    /**
     * 分页查询[JC危害因素]列表
     *
     * @param page  分页参数
     * @param param MdHarm查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdHarm> getPage(PageParam<MdHarm> page, MdHarm param) {
        return mdHarmMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdHarm getInfoById(String id) {
        return mdHarmMapper.getInfoById(id);
    }

    ;

}


