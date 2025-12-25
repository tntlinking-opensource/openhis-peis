package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdLimitOperation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * CW卡额度操作表(MdLimitOperation)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:25
 */
public interface MdLimitOperationMapper extends BaseMapper<MdLimitOperation> {

    /**
     * 分页查询[CW卡额度操作表]列表
     *
     * @param page  分页参数
     * @param param MdLimitOperation查询参数
     * @return 分页数据
     */
    IPage<MdLimitOperation> getPage(PageParam<MdLimitOperation> page, @Param("param") MdLimitOperation param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdLimitOperation getInfoById(@Param("id") String id);

}
