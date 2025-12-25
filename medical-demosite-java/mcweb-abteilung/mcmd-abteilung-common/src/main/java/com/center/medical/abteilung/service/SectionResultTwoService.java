package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.dto.SrtPcDto;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * KS科室检查结果表-结论词、小结(SectionResultTwo)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:20
 */
public interface SectionResultTwoService extends IService<SectionResultTwo> {

    /**
     * 分页查询[KS科室检查结果表-结论词、小结]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SectionResultTwo> getPage(PageParam<SectionResultTwo> page, SectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultTwo getInfoById(String id);

    /**
     * 获取体检者分检结论
     *
     * @param patientcode 体检号
     * @param dh          体检类型：0.健康体检 1.职业体检 2.综合 3.复查
     * @return
     */
    List<SrtPcDto> getListByPatientCode(String patientcode, int dh);
}

