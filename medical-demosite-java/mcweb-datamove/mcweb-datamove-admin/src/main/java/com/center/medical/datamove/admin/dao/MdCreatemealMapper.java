package com.center.medical.datamove.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datamove.admin.bean.model.MdCreatemeal;
import org.apache.ibatis.annotations.Param;

/**
 * 普通套餐表(MdCreatemeal)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 21:57:51
 */
public interface MdCreatemealMapper extends BaseMapper<MdCreatemeal> {

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param MdCreatemeal查询参数
     * @return 分页数据
     */
    IPage<MdCreatemeal> getPage(PageParam<MdCreatemeal> page, @Param("param") MdCreatemeal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCreatemeal getInfoById(@Param("id") String id);

}
