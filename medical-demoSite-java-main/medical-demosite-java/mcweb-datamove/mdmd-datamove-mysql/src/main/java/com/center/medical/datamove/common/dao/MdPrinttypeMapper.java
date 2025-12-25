package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPrinttype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售打印分类设置(MdPrinttype)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
public interface MdPrinttypeMapper extends BaseMapper<MdPrinttype> {

    /**
     * 分页查询[销售打印分类设置]列表
     *
     * @param page  分页参数
     * @param param MdPrinttype查询参数
     * @return 分页数据
     */
    IPage<MdPrinttype> getPage(PageParam<MdPrinttype> page, @Param("param") MdPrinttype param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPrinttype getInfoById(@Param("id") String id);

}
