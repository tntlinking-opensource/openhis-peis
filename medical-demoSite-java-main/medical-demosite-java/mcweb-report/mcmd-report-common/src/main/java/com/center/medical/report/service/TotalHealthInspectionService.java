package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeisState;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.GetGriddataVo;
import com.center.medical.report.bean.vo.HtPeispatientVo;
import com.center.medical.report.bean.vo.RemindPatientVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体检者收费项目表(Peispatientfeeitem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-18 19:40:42
 */
public interface TotalHealthInspectionService extends IService<Peispatient> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<HtPeispatientVo> getPage(PageParam<Peispatient> page, HealthTotalParam param);


    /**
     * 设置scbs
     * @param peispatient
     * @param scbs
     */
    PeisState setScbs(Peispatient peispatient, int scbs);

    /**
     * 健康+职业-新增总检主表、子表
     * @param peispatient
     * @param i
     * @return
     */
    SectionTotal init(Peispatient peispatient, int i);

    /**
     * 健康和职业总检保存
     * @param totalHealthSaOrUpParam
     * @return
     */
    Map<String, String> saOrUp(TotalHealthSaOrUpParam totalHealthSaOrUpParam);

    /**
     * 健康同步
     * @param patientno
     * @param dh
     * @return
     */
    Boolean synchronize(String patientno, int dh);

    /**
     * 是否可以总检
     * @param patientno
     * @param dh
     * @param flag
     * @return
     */
    Boolean canTotal(String patientno, int dh, String flag);

    /**
     * 存入词库（线下总检）
     * @param param
     * @return
     */
    String maintain(MaintainParam param);

    /**
     * 锁定
     * @param ids
     * @param state
     * @return
     */
    Boolean lock(List<String> ids, Integer state);

    /**
     * 根据体检号获取所有总检对象 暂时取第一个
     * @param patientno
     * @return
     */
    HashMap getSectionTotal(String patientno);

    /**
     * 获取该体检者所有的体证词与所产生体证词的科室
     * @param patientno
     * @return
     */
    List<HashMap> getSign(String patientno);

    /**
     * 点击完成保存或更新
     * @param param
     * @return
     */
    Boolean saOrUpFinish(FinishParam param);

    /**
     * 追加结论词和健康建议
     * @param param
     * @return
     */
    HashMap appendSign(AppendSignParam param);

    /**
     * 保存追加的总检建议
     * @param param
     * @return
     */
    Boolean saveNameAndAdvice(CommitSignParam param);

    /**
     * 根据体检号获取该体检者所有收费项目
     * @param patientno
     * @return
     */
    List<HashMap> getItemByPeople(String patientno);

    /**
     * 根据体检号获取该体检者所有收费项目 右侧
     * @param patientno
     * @return
     */
    List<HashMap> getRightItemByPeople(String patientno);

    /**
     * 保存更新复查通知单
     * @param param
     * @return
     */
    Boolean saveReview(SaveReviewParam param);

    /**
     * 保存职业处理意见
     * @param param
     * @return
     */
    Boolean saveTreatment(SaveTreatmentParam param);

    /**
     * 加载 总检结论词表
     * @param patientno
     * @param flag
     * @return
     */
    List<HashMap> loadSaveSign(String patientno, String flag);

    /**
     * 读卡
     * @param patientCode
     * @param ksId
     * @return
     */
    HashMap<String, Object> search(String patientCode, String ksId);

    /**
     * 查询分科检验
     * @param page
     * @param patientCode
     * @param ksId
     * @return
     */
    IPage<GetGriddataVo> getGriddata(PageParam<GetGriddataVo> page, String patientCode, String ksId);

    /**
     * 获取提醒接口
     * @param page
     * @param param
     * @return
     */
    IPage<RemindPatientVo> getRemindPatient(PageParam<RemindPatientVo> page, RemindPatientParam param);



    /**
     * 获取提醒接口
     * @param sectionTotal
     * @param dh
     * @return
     */
    Map<String, String> generateInfo(SectionTotal sectionTotal, int dh);
}

