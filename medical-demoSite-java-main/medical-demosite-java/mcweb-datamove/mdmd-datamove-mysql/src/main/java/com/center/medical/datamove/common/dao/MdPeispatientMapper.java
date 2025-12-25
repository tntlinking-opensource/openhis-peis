package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * QT体检者表(MdPeispatient)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:10
 */
public interface MdPeispatientMapper extends BaseMapper<MdPeispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatient查询参数
     * @return 分页数据
     */
    IPage<MdPeispatient> getPage(PageParam<MdPeispatient> page, @Param("param") MdPeispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPeispatient getInfoById(@Param("id") String id);

}
