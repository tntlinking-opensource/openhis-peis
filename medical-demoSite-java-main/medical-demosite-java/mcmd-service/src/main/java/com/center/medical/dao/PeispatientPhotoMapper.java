package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PeispatientPhoto;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者头像(PeispatientPhoto)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:06
 */
public interface PeispatientPhotoMapper extends BaseMapper<PeispatientPhoto> {

    /**
     * 分页查询[体检者头像]列表
     *
     * @param page  分页参数
     * @param param PeispatientPhoto查询参数
     * @return 分页数据
     */
    IPage<PeispatientPhoto> getList(PageParam<PeispatientPhoto> page, @Param("param") PeispatientPhoto param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientPhoto getInfoById(@Param("id") String id);

    /**
     * 根据体检号获取记录详情
     *
     * @param patientCode id主键
     */
    PeispatientPhoto getByPatientCode(@Param("patientCode") String patientCode);
}
