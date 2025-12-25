package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.OutsideMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS外送登记结果主表(OutsideMain)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:22:33
 */
public interface OutsideMainMapper extends BaseMapper<OutsideMain> {

    /**
     * 分页查询[KS外送登记结果主表]列表
     *
     * @param page  分页参数
     * @param param OutsideMain查询参数
     * @return 分页数据
     */
    IPage<OutsideMain> getPage(PageParam<OutsideMain> page, @Param("param") OutsideMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OutsideMain getInfoById(@Param("id") String id);

}
