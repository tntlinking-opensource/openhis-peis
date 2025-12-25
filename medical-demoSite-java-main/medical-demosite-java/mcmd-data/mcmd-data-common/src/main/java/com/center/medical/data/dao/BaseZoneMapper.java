package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseZone;
import com.center.medical.data.bean.vo.ZoneVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 所属地区(BaseZone)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:48
 */
public interface BaseZoneMapper extends BaseMapper<BaseZone> {

    /**
     * 分页查询[所属地区]列表
     *
     * @param page  分页参数
     * @param param BaseZone查询参数
     * @return 分页数据
     */
    IPage<BaseZone> getList(PageParam<BaseZone> page, @Param("param") BaseZone param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BaseZone getInfoById(@Param("id") String id);

    /**
     * 所属地区信息
     * @param zoneCode
     * @param level
     * @param area
     * @return
     */
    List<ZoneVo> getZoneSql(@Param("zoneCode") String zoneCode,@Param("level") String level,@Param("area") Integer area);
}
