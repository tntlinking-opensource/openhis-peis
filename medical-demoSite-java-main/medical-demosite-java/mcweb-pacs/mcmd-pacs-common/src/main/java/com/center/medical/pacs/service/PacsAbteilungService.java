package com.center.medical.pacs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacs.bean.param.*;
import com.center.medical.pacs.bean.vo.*;
import com.center.medical.system.bean.vo.AllUserDataVo;

import java.util.List;

/**
 * pacs科室
 *
 * @author xhp
 * @since 2023-03-15 9:41
 */
public interface PacsAbteilungService extends IService<PacsResult> {
    /**
     * 体检者列表分页查询
     *
     * @param page
     * @param patientListParam
     * @return
     */
    IPage<PacsAbteilungPatientListVo> getPatientList(PageParam page, PatientListParam patientListParam);

    /**
     * 收费项目列表查询
     *
     * @param patientcode
     * @param deptNo
     * @return
     */
    List<PacsAbteilungItemListVo> getItemList(String patientcode, String deptNo);

    /**
     * 体征词列表查询
     *
     * @param patientcode
     * @return
     */
    List<PacsAbteilungSignListVo> getSignList(String patientcode, String itemId);

    /**
     * 科室列表
     *
     * @param patientcode
     * @param userNo
     * @return
     */
    List<PacsAbteilungAbteilunListVo> getAbteilunList(String patientcode, String userNo);

    /**
     * 历史列表
     *
     * @param patientcode
     * @param deptNo
     * @return
     */
    IPage<PacsAbteilungHistoryListVo> getHistoryList(PageParam page, String patientcode, String deptNo,String describe);

    /**
     * 查询项目结果
     *
     * @param feeitemId
     * @return
     */
    PacsAbteilungItemSearchVo search(String feeitemId);

    /**
     * 保存、审核
     *
     * @param pacsAbteilungSaveParam
     */
    void saveOrUpdate(PacsAbteilungSaveParam pacsAbteilungSaveParam);

    /**
     * 反审核
     *
     * @param pacsAbteilungReverseParam
     */
    void reverse(PacsAbteilungReverseParam pacsAbteilungReverseParam);

    /**
     * 检查体检者状态
     *
     * @param patientcode
     */
    void check(String patientcode);

    /**
     * 医生列表
     *
     * @param deptNo
     * @param inputCode
     * @return
     */
    List<AllUserDataVo> getDoctorList(String deptNo, String inputCode);

    /**
     * 图片列表
     *
     * @param feeitemId
     * @return
     */
    List<PacsAbteilungImgVo> getImgList(String feeitemId);

    /**
     * 生成小结
     *
     * @param tjlx
     * @param patientcode
     * @param deptNo
     * @param jhys
     * @param medicaltype
     * @return
     */
    StringBuilder[] getConclusions(String tjlx, String patientcode, String deptNo, String jhys, String medicaltype);

    /**
     * 获取体检者总计
     *
     * @param patientListParam
     * @return
     */
    PacsAbteilungPatientTotalVo getPatientTotal(PatientListParam patientListParam);

    /**
     * 按id删除图片
     *
     * @param ids
     */
    void deleteAttachments(List<String> ids);

    /**
     * 按体检者收费项目id删除图片
     *
     * @param id
     */
    void deleteByFeeitemId(String id);

    /**
     * 彩超截图上传
     *
     * @param param
     * @return
     */
    PacsAbteilungSaveScreenshotVo uploadScreenshot(PacsAbteilungSaveScreenshotParam param);

    /**
     * 图片设置进报告
     * @param param
     * @return
     */
    Boolean setInReport(SetInReportParam param);
}
