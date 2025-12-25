package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.param.RecordManageParam;
import com.center.medical.report.bean.param.UploadWordParam;
import com.center.medical.report.bean.vo.RecordManageVo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 体检者档案表(Peispatientarchive)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-24 10:08:13
 */
public interface RecordManageService extends IService<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<RecordManageVo> getPage(PageParam<RecordManageVo> page, RecordManageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(String id);

    /**
     * 预览对比报告
     * @param patientcode
     * @param patientcodeBefore
     * @param patientarchiveno
     * @return
     */
    Boolean previewContrastReport(String patientcode, String patientcodeBefore,String patientarchiveno) throws IOException;

    /**
     * 获取对比报告列表信息
     * @param patientarchiveno
     * @return
     */
    List<HashMap> getCompareReport(String patientarchiveno);

    /**
     * 导出对比报告
     * @param param
     * @return
     */
    List<RecordManageVo> getExportData(RecordManageParam param);

    /**
     * 对比报告预览 三个体检号
     * @param patientcode
     * @param patientcodeBefore
     * @param patientcodeFirst
     * @param patientarchiveno
     * @return
     */
    Boolean createThree(String patientcode, String patientcodeBefore, String patientcodeFirst, String patientarchiveno) throws IOException;

    /**
     * 上传word
     * @param param
     * @return
     */
    Boolean uploadWord(UploadWordParam param) throws IOException;
}

