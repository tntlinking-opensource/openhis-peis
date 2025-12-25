package com.center.medical.pacslis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacslis.bean.dto.ChartDto;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.pacslis.bean.param.DivisionParam;
import com.center.medical.pacslis.bean.param.ItemListParam;
import com.center.medical.pacslis.bean.param.ReservationUserParam;
import com.center.medical.pacslis.bean.vo.DivisionVo;
import com.center.medical.pacslis.bean.vo.ReservationUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * PACS-体检者表(PacsPeispatient)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:08
 */
public interface PacsPeispatientMapper extends BaseMapper<PacsPeispatient> {

    /**
     * 分页查询[PACS-体检者表]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatient查询参数
     * @return 分页数据
     */
    IPage<PacsPeispatient> getPage(PageParam<PacsPeispatient> page, @Param("param") ItemListParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsPeispatient getInfoById(@Param("id") String id);

    /**
     * 查询图表数据
     * @param startTime
     * @param endTime
     * @return
     */
    List<ChartDto> getChart(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    /**
     * PACS-彩超审核查看分页
     * @param page
     * @param param
     * @return
     */
    IPage<DivisionVo> getDivPage(PageParam<PacsPeispatient> page, @Param("param") DivisionParam param);

    /**
     * 取得已预约客户
     * @param page
     * @param param
     * @return
     */
    PageParam<ReservationUserVo> getReservationUser(PageParam<PacsPeispatient> page, @Param("param") ReservationUserParam param);
}
