package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientChargeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 收费记录(MdPeispatientChargeRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:11
 */
public interface MdPeispatientChargeRecordMapper extends BaseMapper<MdPeispatientChargeRecord> {

    /**
     * 分页查询[收费记录]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientChargeRecord查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientChargeRecord> getPage(PageParam<MdPeispatientChargeRecord> page, @Param("param") MdPeispatientChargeRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientChargeRecord getInfoById(@Param("id") String id);

}
