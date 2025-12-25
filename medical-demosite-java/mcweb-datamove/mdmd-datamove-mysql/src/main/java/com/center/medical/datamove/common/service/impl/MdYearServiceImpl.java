package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdYearMapper;
import com.center.medical.datamove.common.bean.model.MdYear;
import com.center.medical.datamove.common.service.MdYearService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 年份表(MdYear)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
@Slf4j
@Service("mdYearService")
@RequiredArgsConstructor
public class MdYearServiceImpl extends ServiceImpl<MdYearMapper, MdYear> implements MdYearService {

    private final MdYearMapper mdYearMapper;

    /**
     * 分页查询[年份表]列表
     *
     * @param page  分页参数
     * @param param MdYear查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdYear> getPage(PageParam<MdYear> page, MdYear param) {
        return mdYearMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdYear getInfoById(String id) {
        return mdYearMapper.getInfoById(id);
    }

}


