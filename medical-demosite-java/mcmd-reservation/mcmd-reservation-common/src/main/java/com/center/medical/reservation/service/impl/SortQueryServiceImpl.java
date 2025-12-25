package com.center.medical.reservation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.SortQueryParam;
import com.center.medical.reservation.bean.vo.SortQueryVo;
import com.center.medical.reservation.dao.SortQueryMapper;
import com.center.medical.reservation.service.SortQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检预约记录(Reservation)服务实现类
 *
 * @author ay
 * @since 2024-01-18 17:12:03
 */
@Slf4j
@Service("sortQueryService")
@RequiredArgsConstructor
public class SortQueryServiceImpl extends ServiceImpl<SortQueryMapper, Reservation> implements SortQueryService {

    private final SortQueryMapper sortQueryMapper;

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param Reservation查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SortQueryVo> getPage(PageParam<SortQueryVo> page, SortQueryParam param) {
        return sortQueryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Reservation getInfoById(String id) {
        return sortQueryMapper.getInfoById(id);
    }

    /**
     * 导出项目预约明细
     * @param param
     * @return
     */
    @Override
    public List<SortQueryVo> exportData(SortQueryParam param) {
        return sortQueryMapper.exportData(param);
    }
}

