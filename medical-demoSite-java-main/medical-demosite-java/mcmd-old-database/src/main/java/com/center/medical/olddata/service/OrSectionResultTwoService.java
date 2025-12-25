package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrSectionResultTwo;

import java.util.List;

/**
 * KS科室检查结果表————结论词、小结(SectionResultTwo)服务接口
 *
 * @author ay
 * @since 2024-06-05 14:39:41
 */
public interface OrSectionResultTwoService extends IService<OrSectionResultTwo> {

    /**
     * 分页查询[KS科室检查结果表————结论词、小结]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrSectionResultTwo> getPage(PageParam<OrSectionResultTwo> page, OrSectionResultTwo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrSectionResultTwo getInfoById(String id);

    /**
     * 通过体检号查新
     * @param patientCode
     * @return
     */
    List<OrSectionResultTwo> getListByPatientCode(String patientCode);
}

