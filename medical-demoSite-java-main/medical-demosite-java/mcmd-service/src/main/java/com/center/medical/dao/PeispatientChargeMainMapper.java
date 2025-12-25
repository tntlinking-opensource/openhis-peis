package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者费用主表(PeispatientChargeMain)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:24
 */
public interface PeispatientChargeMainMapper extends BaseMapper<PeispatientChargeMain> {

    /**
     * 分页查询[体检者费用主表]列表
     *
     * @param page  分页参数
     * @param param PeispatientChargeMain查询参数
     * @return 分页数据
     */
    IPage<PeispatientChargeMain> getList(PageParam<PeispatientChargeMain> page, @Param("param") PeispatientChargeMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PeispatientChargeMain getInfoById(@Param("id") String id);

    /**
     * 根据体检号获取记录详情
     * @param patientcode
     * @return
     */
    PeispatientChargeMain getByPatientCode(@Param("patientcode")String patientcode);

    /**
     * 查询缺少费用主表的体检号
     * @return
     */
    List<String> getAddChangMain();
}
