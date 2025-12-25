package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCardRecheckRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 十周年卡复查金额记录表(MdCardRecheckRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
public interface MdCardRecheckRecordMapper extends BaseMapper<MdCardRecheckRecord> {

    /**
     * 分页查询[十周年卡复查金额记录表]列表
     *
     * @param page  分页参数
     * @param param MdCardRecheckRecord查询参数
     * @return 分页数据
     */
    IPage<MdCardRecheckRecord> getPage(PageParam<MdCardRecheckRecord> page, @Param("param") MdCardRecheckRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCardRecheckRecord getInfoById(@Param("id") String id);

}
