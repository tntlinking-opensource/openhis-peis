package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatienthistory;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者（history）表(Peispatienthistory)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:37
 */
public interface PeispatienthistoryMapper extends BaseMapper<Peispatienthistory> {

    /**
     * 分页查询[体检者（history）表]列表
     *
     * @param page  分页参数
     * @param param Peispatienthistory查询参数
     * @return 分页数据
     */
    IPage<Peispatienthistory> getList(PageParam<Peispatienthistory> page, @Param("param") Peispatienthistory param);


    /**
     * 通过体检号获取对象
     *
     * @param patientcode
     * @return
     */
    Peispatienthistory getByPatientcode(@Param("peispatientCode") String patientcode);


    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Peispatienthistory getInForById(@Param("id") String id);
}
