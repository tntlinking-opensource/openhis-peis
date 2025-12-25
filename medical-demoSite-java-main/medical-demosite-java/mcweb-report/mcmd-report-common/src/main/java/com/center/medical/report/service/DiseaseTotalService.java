package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeisState;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.param.*;
import com.center.medical.report.bean.vo.CommonDataVo;
import com.center.medical.report.bean.vo.DtPeispatientVo;
import com.center.medical.report.bean.vo.TreatmentDataVo;
import com.center.medical.report.bean.vo.VerdictDataVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 总检管理-职业总检服务接口
 *
 * @author 路飞船长
 * @since 2022-12-07 18:53:54
 */
public interface DiseaseTotalService extends IService<Peispatient> {

    /**
     * 分页获取分检完成待总检人员
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<DtPeispatientVo> getPage(PageParam<DtPeispatientVo> page, HealthTotalParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);


    /**
     * 打开科室小结界面
     *
     * @param patientno
     * @return
     */
    List<CommonDataVo> getCommonListData(String patientno);

    /**
     * 获取图片头像
     *
     * @param peispatient
     * @return
     */
    String getPicture(Peispatient peispatient);

    /**
     * 增加总检记录及总检结论词表初始化
     *
     * @param patientCode
     * @return
     */
    SectionTotal addTotalContent(String patientCode);

    /**
     * 获取综述,结论,意见
     *
     * @param patientCode
     * @return
     */
    Map<String, String> getThreeData(String patientCode);

    /**
     * 根据档案号获取职业性病史
     *
     * @param patientarchiveno
     * @return
     */
    String getOccupationDiseast(String patientarchiveno);

    /**
     * 职业处理意见界面 条件搜索
     *
     * @param param
     * @return
     */
    List<HashMap> searchTreatmentsuggestion(SearchTreatParam param);

    /**
     * 科室小结 界面 数据
     *
     * @param patientno
     * @return
     */
    List<VerdictDataVo> getVerdictData(String patientno);


    /**
     * 职业意见 界面 数据
     *
     * @param patientno
     * @return
     */
    List<TreatmentDataVo> getTreatmentDataString(String patientno);

    /**
     * 保存职业意见增加界面 数据
     *
     * @param param
     * @return
     */
    Map<String, Object> saveTreatment(SaveTreatParam param);

    /**
     * 根据科室id和体检号 获取 体检项目和收费项目 和小结
     *
     * @param patientno
     * @param sectionId
     * @return
     */
    List<VerdictDataVo> getInspectChargeListData(String patientno, String sectionId);

    /**
     * 保存职业意见增加界面 数据
     *
     * @param param
     * @return
     */
    Boolean saOrUp(DiseaseSaOrUpParam param);

    /**
     * 设置体检者上传标志
     *
     * @param peispatient
     * @param i
     */
    PeisState setScbs(Peispatient peispatient, int i);

    /**
     * 保存综述 结论 建议 职业和健康共用
     *
     * @param param
     * @return
     */
    Boolean saveSign(SaveSignAllParam param);

    /**
     * 获取该体检者所有的体证词与所产生体证词的科室
     *
     * @param patientno
     * @return
     */
    List<HashMap> getSign(String patientno);

    /**
     * 获取该体检者所有的体证词与所产生体证词的科室
     *
     * @param srm
     * @param page
     * @param patientno
     * @return
     */
    IPage<Basconclusion> getConclusion(String srm, PageParam<Basconclusion> page, String patientno);

    /**
     * 未完成
     *
     * @param patientno
     * @return
     */
    Boolean unfinish(String patientno);

    /**
     * 完成
     *
     * @param param
     * @return
     */
    Boolean finish(DiseaseSaOrUpParam param);

    /**
     * 分科电测听读卡
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    HashMap<String, Object> search(String patientCode, String ksId);

    /**
     * 删除某条职业处理意见
     *
     * @param ids
     * @param patientno
     * @return
     */
    Map<String, Object> removeRows(List<String> ids, String patientno);

    /**
     * 判断是否需要选择总检医生
     *
     * @param patientno
     * @return
     */
    Map<String, Object> checkHarm(String patientno);

    /**
     * 解锁
     *
     * @param patientcode
     * @return
     */
    Boolean unlock(String patientcode);


    /**
     * 锁定
     *
     * @param patientcode
     * @return
     */
    Boolean lock(String patientcode);
}

