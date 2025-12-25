package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseZoneQd;
import com.center.medical.data.bean.vo.UnitAreaVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 所属地区(BaseZoneQd)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
public interface BaseZoneQdMapper extends BaseMapper<BaseZoneQd> {

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param BaseZoneQd查询参数
     * @return 分页数据
     */
    IPage<BaseZoneQd> getList(PageParam<BaseZoneQd> page, @Param("param") BaseZoneQd param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseZoneQd getInfoById(@Param("id") String id);

    /**
     * 获取所属区域
     * @return
     */
    List<UnitAreaVo> getUnitArea();
}
