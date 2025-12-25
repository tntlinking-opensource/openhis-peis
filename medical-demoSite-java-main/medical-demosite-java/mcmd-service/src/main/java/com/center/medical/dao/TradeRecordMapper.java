package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.TradeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 交易记录(TradeRecord)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:17
 */
public interface TradeRecordMapper extends BaseMapper<TradeRecord> {

    /**
     * 分页查询[交易记录]列表
     *
     * @param page  分页参数
     * @param param TradeRecord查询参数
     * @return 分页数据
     */
    IPage<TradeRecord> getList(PageParam<TradeRecord> page, @Param("param") TradeRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TradeRecord getInfoById(@Param("id") String id);

}
