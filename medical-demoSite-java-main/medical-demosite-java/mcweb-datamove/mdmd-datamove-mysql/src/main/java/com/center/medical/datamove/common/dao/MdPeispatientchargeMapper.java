package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatientcharge;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者缴费表(MdPeispatientcharge)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:14
 */
public interface MdPeispatientchargeMapper extends BaseMapper<MdPeispatientcharge> {

    /**
     * 分页查询[体检者缴费表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientcharge查询参数
     * @return 分页数据
     */
    IPage<MdPeispatientcharge> getPage(PageParam<MdPeispatientcharge> page, @Param("param") MdPeispatientcharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatientcharge getInfoById(@Param("id") String id);

}
