package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFinanceinput;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 销售财务录入表(MdFinanceinput)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:17
 */
public interface MdFinanceinputMapper extends BaseMapper<MdFinanceinput> {

    /**
     * 分页查询[销售财务录入表]列表
     *
     * @param page  分页参数
     * @param param MdFinanceinput查询参数
     * @return 分页数据
     */
    IPage<MdFinanceinput> getPage(PageParam<MdFinanceinput> page, @Param("param") MdFinanceinput param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFinanceinput getInfoById(@Param("id") String id);

}
