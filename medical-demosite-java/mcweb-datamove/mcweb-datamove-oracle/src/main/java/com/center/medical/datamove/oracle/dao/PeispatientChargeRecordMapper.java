package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientChargeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * (PeispatientChargeRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:16
 */
public interface PeispatientChargeRecordMapper extends BaseMapper<PeispatientChargeRecord> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeRecord查询参数
     * @return 分页数据
     */
    IPage<PeispatientChargeRecord> getPage(PageParam<PeispatientChargeRecord> page, @Param("param") PeispatientChargeRecord param);


}
