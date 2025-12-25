package com.center.medical.pacslis.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacslis.bean.model.PacsPeispatientfeeitem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PACS-体检者收费项目表(PacsPeispatientfeeitem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:20
 */
public interface PacsPeispatientfeeitemMapper extends BaseMapper<PacsPeispatientfeeitem> {

    /**
     * 分页查询[PACS-体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<PacsPeispatientfeeitem> getList(PageParam<PacsPeispatientfeeitem> page, @Param("param") PacsPeispatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsPeispatientfeeitem getInfoById(@Param("id") String id);

    /**
     * 无关联科室已检
     * @param patientcode
     * @return
     */
    List<PacsPeispatientfeeitem> selectOthers(@Param("patientcode")String patientcode);
}
