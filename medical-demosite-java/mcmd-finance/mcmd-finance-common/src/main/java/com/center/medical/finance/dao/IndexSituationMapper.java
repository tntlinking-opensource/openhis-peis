package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.ISDataDto;
import com.center.medical.sellcrm.bean.model.Createorder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 订单表(Createorder)表数据库访问层
 *
 * @author ay
 * @since 2023-05-15 09:37:34
 */
public interface IndexSituationMapper extends BaseMapper<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    IPage<Createorder> getList(PageParam<Createorder> page, @Param("param") Createorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(@Param("id") String id);

    /**
     * 查询产值数据
     * @param time 时间
     * @param includingVaccines 是否包含疫苗,1包含,0不包含
     * @return
     */
    List<ISDataDto> getOutputValue(@Param("time") Date time, @Param("includingVaccines") int includingVaccines);

    /**
     * 获取收入 团检或个检
     * @param time
     * @return
     */
    List<ISDataDto> getIncome(@Param("time") Date time,@Param("fUsecodehiden") Integer fUsecodehiden);

    /**
     * 新单金额
     * @return
     */
    List<ISDataDto> getNewOrder();

    /**
     * 获取各个分中心的人数
     * @param time
     * @return
     */
    List<ISDataDto> getPersonCount(@Param("time") Date time);

    /**
     * 疫苗产值 开始
     * @param startTime
     * @return
     */
    List<ISDataDto> getVaccinumValue(@Param("time") Date startTime);
}
