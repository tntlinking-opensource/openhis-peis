package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.LimitOperation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * CW卡额度操作表(LimitOperation)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:19:37
 */
public interface LimitOperationMapper extends BaseMapper<LimitOperation> {

    /**
     * 分页查询[CW卡额度操作表]列表
     *
     * @param page  分页参数
     * @param param LimitOperation查询参数
     * @return 分页数据
     */
    IPage<LimitOperation> getPage(PageParam<LimitOperation> page, @Param("param") LimitOperation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    LimitOperation getInfoById(@Param("id") String id);

}
