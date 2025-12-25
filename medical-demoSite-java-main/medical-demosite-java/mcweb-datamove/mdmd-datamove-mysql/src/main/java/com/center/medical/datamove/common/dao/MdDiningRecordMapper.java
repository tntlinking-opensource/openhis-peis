package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdDiningRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 就餐记录(MdDiningRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:13
 */
public interface MdDiningRecordMapper extends BaseMapper<MdDiningRecord> {

    /**
     * 分页查询[就餐记录]列表
     *
     * @param page  分页参数
     * @param param MdDiningRecord查询参数
     * @return 分页数据
     */
    IPage<MdDiningRecord> getPage(PageParam<MdDiningRecord> page, @Param("param") MdDiningRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdDiningRecord getInfoById(@Param("id") String id);

}
