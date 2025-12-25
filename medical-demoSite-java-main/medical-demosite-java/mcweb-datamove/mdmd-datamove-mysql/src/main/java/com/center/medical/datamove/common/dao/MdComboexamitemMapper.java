package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdComboexamitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 用于判断职业小结(MdComboexamitem)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
public interface MdComboexamitemMapper extends BaseMapper<MdComboexamitem> {

    /**
     * 分页查询[用于判断职业小结]列表
     *
     * @param page  分页参数
     * @param param MdComboexamitem查询参数
     * @return 分页数据
     */
    IPage<MdComboexamitem> getPage(PageParam<MdComboexamitem> page, @Param("param") MdComboexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdComboexamitem getInfoById(@Param("id") String id);

}
