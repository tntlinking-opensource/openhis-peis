package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PeispatientChargeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 收费记录(PeispatientChargeRecord)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:07
 */
public interface PeispatientChargeRecordMapper extends BaseMapper<PeispatientChargeRecord> {

    /**
     * 分页查询[收费记录]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeRecord查询参数
     * @return 分页数据
     */
    IPage<PeispatientChargeRecord> getList(PageParam<PeispatientChargeRecord> page, @Param("param") PeispatientChargeRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientChargeRecord getInfoById(@Param("id") String id);

}
