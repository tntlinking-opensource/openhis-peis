package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.BaseAdiconExamItemCode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (BaseAdiconExamItemCode)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:12:25
 */
public interface BaseAdiconExamItemCodeMapper extends BaseMapper<BaseAdiconExamItemCode> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param BaseAdiconExamItemCode查询参数
     * @return 分页数据
     */
    IPage<BaseAdiconExamItemCode> getPage(PageParam<BaseAdiconExamItemCode> page, @Param("param") BaseAdiconExamItemCode param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    BaseAdiconExamItemCode getInfoById(@Param("id") String id);

}
