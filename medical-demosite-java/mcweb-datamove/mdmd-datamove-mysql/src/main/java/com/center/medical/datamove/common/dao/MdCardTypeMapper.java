package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCardType;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 卡类型(MdCardType)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:15
 */
public interface MdCardTypeMapper extends BaseMapper<MdCardType> {

    /**
     * 分页查询[卡类型]列表
     *
     * @param page  分页参数
     * @param param MdCardType查询参数
     * @return 分页数据
     */
    IPage<MdCardType> getPage(PageParam<MdCardType> page, @Param("param") MdCardType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCardType getInfoById(@Param("id") String id);

}
