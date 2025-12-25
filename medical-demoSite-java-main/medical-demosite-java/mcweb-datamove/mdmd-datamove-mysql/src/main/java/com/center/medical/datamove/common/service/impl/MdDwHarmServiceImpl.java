package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdDwHarmMapper;
import com.center.medical.datamove.common.bean.model.MdDwHarm;
import com.center.medical.datamove.common.service.MdDwHarmService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 单位危害因素(MdDwHarm)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:15
 */
@Slf4j
@Service("mdDwHarmService")
@RequiredArgsConstructor
public class MdDwHarmServiceImpl extends ServiceImpl<MdDwHarmMapper, MdDwHarm> implements MdDwHarmService {

    private final MdDwHarmMapper mdDwHarmMapper;

    /**
     * 分页查询[单位危害因素]列表
     *
     * @param page  分页参数
     * @param param MdDwHarm查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdDwHarm> getPage(PageParam<MdDwHarm> page, MdDwHarm param) {
        return mdDwHarmMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdDwHarm getInfoById(String id) {
        return mdDwHarmMapper.getInfoById(id);
    }

}


