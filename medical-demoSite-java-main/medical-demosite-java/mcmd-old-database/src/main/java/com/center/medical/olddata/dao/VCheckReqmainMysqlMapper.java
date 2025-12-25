package com.center.medical.olddata.dao;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.VCheckReqmainMysql;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (VCheckReqmainMysql)数据库访问层
 *
 * @author ay
 * @since 2024-08-09 15:46:19
 */
public interface VCheckReqmainMysqlMapper extends BaseMapper<VCheckReqmainMysql> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param VCheckReqmainMysql查询参数
     * @return 分页数据
     */
    IPage<VCheckReqmainMysql> getPage(PageParam<VCheckReqmainMysql> page, @Param("param") VCheckReqmainMysql param);

    /**
     * 获取视图数据
     * @param patientcode
     * @return
     */
    List<VCheckReqmainMysql> getCheckReqmain(@Param("patientcode") String patientcode);

    /**
     * 查询过期数据
     * @param dateTime
     * @return
     */
    Integer deleteTimeOutList(@Param("time") DateTime dateTime);
}
