package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPricture;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS图片存储表(MdPricture)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
public interface MdPrictureMapper extends BaseMapper<MdPricture> {

    /**
     * 分页查询[KS图片存储表]列表
     *
     * @param page  分页参数
     * @param param MdPricture查询参数
     * @return 分页数据
     */
    IPage<MdPricture> getPage(PageParam<MdPricture> page, @Param("param") MdPricture param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPricture getInfoById(@Param("id") String id);

}
