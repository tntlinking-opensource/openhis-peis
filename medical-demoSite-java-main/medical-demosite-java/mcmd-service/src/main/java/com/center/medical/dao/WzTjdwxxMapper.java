package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.WzTjdwxx;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS问诊——体检单位信息(WzTjdwxx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:51
 */
public interface WzTjdwxxMapper extends BaseMapper<WzTjdwxx> {

    /**
     * 分页查询[KS问诊——体检单位信息]列表
     *
     * @param page  分页参数
     * @param param WzTjdwxx查询参数
     * @return 分页数据
     */
    IPage<WzTjdwxx> getList(PageParam<WzTjdwxx> page, @Param("param") WzTjdwxx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    WzTjdwxx getInfoById(@Param("id") String id);

}
