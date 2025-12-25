package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.CardRecheckRecord;
import org.apache.ibatis.annotations.Param;

/**
 * 十周年卡复查金额记录表(CardRecheckRecord)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:36
 */
public interface CardRecheckRecordMapper extends BaseMapper<CardRecheckRecord> {

    /**
     * 分页查询[十周年卡复查金额记录表]列表
     *
     * @param page  分页参数
     * @param param CardRecheckRecord查询参数
     * @return 分页数据
     */
    IPage<CardRecheckRecord> getList(PageParam<CardRecheckRecord> page, @Param("param") CardRecheckRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    CardRecheckRecord getInfoById(@Param("id") String id);

}
