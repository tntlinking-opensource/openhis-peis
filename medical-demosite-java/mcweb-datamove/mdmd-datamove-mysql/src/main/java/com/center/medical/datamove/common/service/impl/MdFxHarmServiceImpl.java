package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdFxHarmMapper;
import com.center.medical.datamove.common.bean.model.MdFxHarm;
import com.center.medical.datamove.common.service.MdFxHarmService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 职业团检分析-危害因素(MdFxHarm)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:46:18
 */
@Slf4j
@Service("mdFxHarmService")
@RequiredArgsConstructor
public class MdFxHarmServiceImpl extends ServiceImpl<MdFxHarmMapper, MdFxHarm> implements MdFxHarmService {

    private final MdFxHarmMapper mdFxHarmMapper;

    /**
     * 分页查询[职业团检分析-危害因素]列表
     *
     * @param page  分页参数
     * @param param MdFxHarm查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdFxHarm> getPage(PageParam<MdFxHarm> page, MdFxHarm param) {
        return mdFxHarmMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdFxHarm getInfoById(String id) {
        return mdFxHarmMapper.getInfoById(id);
    }

}


