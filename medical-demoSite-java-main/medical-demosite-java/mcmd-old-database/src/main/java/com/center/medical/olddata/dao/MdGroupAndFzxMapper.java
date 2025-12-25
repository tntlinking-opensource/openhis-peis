package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdGroupAndFzx;
import org.apache.ibatis.annotations.Param;

/**
 * 分组分中心(MdGroupAndFzx)数据库访问层
 *
 * @author ay
 * @since 2024-04-11 10:49:53
 */
public interface MdGroupAndFzxMapper extends BaseMapper<MdGroupAndFzx> {

    /**
     * 分页查询[分组分中心]列表
     *
     * @param page  分页参数
     * @param param MdGroupAndFzx查询参数
     * @return 分页数据
     */
    IPage<MdGroupAndFzx> getPage(PageParam<MdGroupAndFzx> page, @Param("param") MdGroupAndFzx param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdGroupAndFzx getInfoById(@Param("id") String id);

}
