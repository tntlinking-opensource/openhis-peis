package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdTjbbXyjcMapper;
import com.center.medical.datamove.common.bean.model.MdTjbbXyjc;
import com.center.medical.datamove.common.service.MdTjbbXyjcService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS血压检测(MdTjbbXyjc)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:49:05
 */
@Slf4j
@Service("mdTjbbXyjcService")
@RequiredArgsConstructor
public class MdTjbbXyjcServiceImpl extends ServiceImpl<MdTjbbXyjcMapper, MdTjbbXyjc> implements MdTjbbXyjcService {

    private final MdTjbbXyjcMapper mdTjbbXyjcMapper;

    /**
     * 分页查询[KS血压检测]列表
     *
     * @param page  分页参数
     * @param param MdTjbbXyjc查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTjbbXyjc> getPage(PageParam<MdTjbbXyjc> page, MdTjbbXyjc param) {
        return mdTjbbXyjcMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTjbbXyjc getInfoById(String id) {
        return mdTjbbXyjcMapper.getInfoById(id);
    }

}


