package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Lung;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.GetLungVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS肺功能(Lung)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:05
 */
public interface LungMapper extends BaseMapper<Lung> {

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param Lung查询参数
     * @return 分页数据
     */
    IPage<Lung> getList(PageParam<Lung> page, @Param("param") Lung param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Lung getInfoById(@Param("id") String id);


    /**
     * 根据体检码获取记录详情
     *
     * @param patientcode 体检号
     */
    Lung getInfoPatientCode(@Param("patientcode") String patientcode);

    /**
     * 获取肺功能数据
     * @param patientcode
     * @return
     */
    List<GetLungVo> getLung(@Param("patientcode") String patientcode);
}
