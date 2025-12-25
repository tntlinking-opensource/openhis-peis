package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsPeispatient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-体检者表(MdPacsPeispatient)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
public interface MdPacsPeispatientMapper extends BaseMapper<MdPacsPeispatient> {

    /**
     * 分页查询[PACS-体检者表]列表
     *
     * @param page  分页参数
     * @param param MdPacsPeispatient查询参数
     * @return 分页数据
     */
    IPage<MdPacsPeispatient> getPage(PageParam<MdPacsPeispatient> page, @Param("param") MdPacsPeispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsPeispatient getInfoById(@Param("id") String id);

}
