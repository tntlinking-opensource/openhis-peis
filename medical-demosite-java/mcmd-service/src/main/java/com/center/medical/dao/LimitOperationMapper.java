package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.LimitOperation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * CW卡额度操作表(LimitOperation)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:05
 */
public interface LimitOperationMapper extends BaseMapper<LimitOperation> {

    /**
     * 分页查询[CW卡额度操作表]列表
     *
     * @param page  分页参数
     * @param param LimitOperation查询参数
     * @return 分页数据
     */
    IPage<LimitOperation> getList(PageParam<LimitOperation> page, @Param("param") LimitOperation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    LimitOperation getInfoById(@Param("id") String id);

}
