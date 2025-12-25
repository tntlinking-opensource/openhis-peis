package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdTjreg;
import com.center.medical.olddata.dao.MdTjregMapper;
import com.center.medical.olddata.service.MdTjregService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * KS一般检查(MdTjreg)服务实现类
 *
 * @author ay
 * @since 2024-06-05 16:02:18
 */
@Slf4j
@Service("mdTjregService")
@RequiredArgsConstructor
public class MdTjregServiceImpl extends ServiceImpl<MdTjregMapper, MdTjreg> implements MdTjregService {

    private final MdTjregMapper mdTjregMapper;

    /**
     * 分页查询[KS一般检查]列表
     *
     * @param page  分页参数
     * @param param MdTjreg查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdTjreg> getPage(PageParam<MdTjreg> page, MdTjreg param) {
        return mdTjregMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdTjreg getInfoById(String id) {
        return mdTjregMapper.getInfoById(id);
    }

}

