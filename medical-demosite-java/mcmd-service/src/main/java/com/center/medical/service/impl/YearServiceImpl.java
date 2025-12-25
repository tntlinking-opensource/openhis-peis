package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.YearMapper;
import com.center.medical.bean.model.Year;
import com.center.medical.service.YearService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 年份表(Year)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:41
 */
@Slf4j
@Service("yearService")
@RequiredArgsConstructor
public class YearServiceImpl extends ServiceImpl<YearMapper, Year> implements YearService {

    private final YearMapper yearMapper;

    /**
     * 分页查询[年份表]列表
     *
     * @param page  分页参数
     * @param param Year查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Year> getList(PageParam<Year> page, Year param) {
        return yearMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Year getInfoById(String id) {
        return yearMapper.getInfoById(id);
    }

}

