package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.Printtype;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售打印分类设置(Printtype)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:38
 */
public interface PrinttypeMapper extends BaseMapper<Printtype> {

    /**
     * 分页查询[销售打印分类设置]列表
     *
     * @param page  分页参数
     * @param param Printtype查询参数
     * @return 分页数据
     */
    IPage<Printtype> getPage(PageParam<Printtype> page, @Param("param") Printtype param);


}
