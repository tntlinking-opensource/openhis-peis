package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdTradeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 交易记录(MdTradeRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:49:12
 */
public interface MdTradeRecordMapper extends BaseMapper<MdTradeRecord> {

    /**
     * 分页查询[交易记录]列表
     *
     * @param page  分页参数
     * @param param MdTradeRecord查询参数
     * @return 分页数据
     */
    IPage<MdTradeRecord> getPage(PageParam<MdTradeRecord> page, @Param("param") MdTradeRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdTradeRecord getInfoById(@Param("id") String id);

}
