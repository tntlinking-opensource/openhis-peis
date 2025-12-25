package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdNation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * JC民族(MdNation)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:27
 */
public interface MdNationMapper extends BaseMapper<MdNation> {

    /**
     * 分页查询[JC民族]列表
     *
     * @param page  分页参数
     * @param param MdNation查询参数
     * @return 分页数据
     */
    IPage<MdNation> getPage(PageParam<MdNation> page, @Param("param") MdNation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdNation getInfoById(@Param("id") String id);

}
