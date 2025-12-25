package com.center.medical.reservation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.param.SortQueryParam;
import com.center.medical.reservation.bean.vo.SortQueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检预约记录(Reservation)数据库访问层
 *
 * @author ay
 * @since 2024-01-18 17:12:03
 */
public interface SortQueryMapper extends BaseMapper<Reservation> {

    /**
     * 分页查询[体检预约记录]列表
     *
     * @param page  分页参数
     * @param param Reservation查询参数
     * @return 分页数据
     */
    IPage<SortQueryVo> getPage(PageParam<SortQueryVo> page, @Param("param") SortQueryParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Reservation getInfoById(@Param("id") String id);

    /**
     * 不包含个检的分页
     * @param page
     * @param param
     * @return
     */
    IPage<SortQueryVo> getPageNoContain(PageParam<SortQueryVo> page, @Param("param") SortQueryParam param);

    /**
     * 导出数据包含个检
     * @param param
     * @return
     */
    List<SortQueryVo> exportData( @Param("param") SortQueryParam param);

    /**
     * 导出数据不包含个检
     * @param param
     * @return
     */
    List<SortQueryVo> exportDataNoContain( @Param("param") SortQueryParam param);
}
