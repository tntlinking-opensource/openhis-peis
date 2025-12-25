package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Teamremind;
import com.center.medical.sellcrm.bean.param.DataExceptionPeiParam;
import com.center.medical.sellcrm.bean.param.TeamremindParam;
import com.center.medical.sellcrm.bean.vo.DataExceptionPeiVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户预检跟踪表(Teamremind)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 14:52:38
 */
public interface TeamremindMapper extends BaseMapper<Teamremind> {

    /**
     * 分页查询[客户预检跟踪表]列表
     *
     * @param page  分页参数
     * @param teamremindParam Teamremind查询参数
     * @return 分页数据
     */
    IPage<Teamremind> getPage(PageParam<Teamremind> page, @Param("param") TeamremindParam teamremindParam);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Teamremind getInfoById(@Param("id") String id);


    /**
     * 获取主页数据
     * @return
     */
    List<Teamremind> findBySql(@Param("param")TeamremindParam teamremindParam);

    /**
     * 获取数据异常体检者数据
     * @param page
     * @param param
     * @return
     */
    IPage<DataExceptionPeiVo> getDataExceptionPeiPage(PageParam<DataExceptionPeiVo> page,@Param("param") DataExceptionPeiParam param);
}
