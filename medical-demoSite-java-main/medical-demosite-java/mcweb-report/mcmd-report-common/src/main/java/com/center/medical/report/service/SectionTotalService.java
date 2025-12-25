package com.center.medical.report.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.enums.ExamType;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.bean.vo.STHistoryVo;

import java.util.List;
import java.util.Map;

/**
 * ZJ总检主表(SectionTotal)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-08 17:32:56
 */
public interface SectionTotalService extends IService<SectionTotal> {

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SectionTotal> getPage(PageParam<SectionTotal> page, SectionTotal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionTotal getInfoById(String id);

    /**
     * 健康+职业-新增总检主表、子表
     *
     * @param patient 体检者信息
     * @param dh      体检类型：0.健康体检 1.职业体检 2.综合 3.复查
     * @return
     * @see ExamType
     */
    SectionTotal createNew(Peispatient patient, int dh);

    /**
     * 健康总检-生成总检综述、结论、健康建议
     *
     * @param sectionTotal 总检记录
     * @param dh           体检类型：0.健康 1.职业
     * @return
     */
    Map<String, String> generateInfo(SectionTotal sectionTotal, int dh);

    /**
     * 获取总检历史记录
     *
     * @param smId             总检id
     * @param dh               体检类型：0.健康 1.职业
     * @param patientarchiveno 档案号
     * @return
     */
    List<STHistoryVo> getHistory(String smId, int dh, String patientarchiveno);

    /**
     * 获取历史数据
     *
     * @param page
     * @param id
     * @param dh
     * @return
     */
    IPage<STHistoryVo> getHistoryData(PageParam<STHistoryVo> page, String id, String dh);


    /**
     * 生成职业处理意见。新增一条时，在diseaseTotalService.saveTreatment方法中生成
     *
     * @param patientCode
     * @return
     */
    String[] generateCom(String patientCode);

    /**
     * 根据体检号获取所有总检对象 暂时取第一个
     *
     * @param patientno
     * @return
     */
    SectionTotal getdata(String patientno);

    /**
     * 获取结构化体检报告数据
     * @param hospitalOrderId
     * @return
     */
    String getOffer(String hospitalOrderId);
}

