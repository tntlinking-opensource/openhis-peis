package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.dto.*;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.OrderUserParam;
import com.center.medical.bean.param.PatientDtoParam;
import com.center.medical.bean.param.ReportSearchCodeParam;
import com.center.medical.bean.param.UserExamDataParam;
import com.center.medical.bean.vo.OrderUserVo;
import com.center.medical.bean.vo.SearchCodeVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:50
 */
public interface PeispatientMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getPage(PageParam<Peispatient> page, @Param("param") Peispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 通过体检号获取记录
     *
     * @param patientCode
     * @return
     */
    Peispatient getByPatientCode(@Param("patientCode") String patientCode);

    /**
     * 旧案召回 从体检者历史表 插入到体检者表
     *
     * @param ids
     * @return
     */
    Integer reSaveHistory(@Param("ids") List<String> ids);

    /**
     * 搜索体检号
     *
     * @param param
     * @return
     */
    List<SearchCodeVo> searchCode(@Param("param") ReportSearchCodeParam param);

    /**
     * 查询体检者
     *
     * @param param 筛选条件
     * @return
     */
    List<PatientDto> getPatientByTime(@Param("param") PatientDtoParam param);

    List<PatientDuplicate> geDuplicate(@Param("ddh") String ddh);

    List<Peispatient> geDuplicatePatients(@Param("param") PatientDuplicate item);

    /**
     * 更新体检者，设置hospitalcode、patientarchiveno为空
     *
     * @param id
     * @param patientcode
     */
    void updateDeduplication(@Param("id") String id, @Param("patientcode") String patientcode);

    /**
     * 根据订单号获取体检者列表
     * @param params 查询条件
     * @return
     */
    IPage<OrderUserVo> getByDdh(PageParam<OrderUserVo> page,  @Param("params") OrderUserParam params);

    /**
     * 查询推送给第三方的体检者数据
     * @param params
     * @return
     */
    List<UserExamDataDto> getPushData(@Param("params") UserExamDataParam params);

    /**
     * 获取总检数据
     * @param patientcode
     * @return
     */
    List<SectionTotalDto> getSectionTotal(@Param("patientcode") String patientcode);

    /**
     * 获取每个科室的检查结果和小结
     * @param patientcode
     * @return
     */
    List<ExamItemDataDto> getSectionResult(@Param("patientcode") String patientcode);

    /**
     * 获取自助登记机团检客户
     * @param idcardno
     * @return
     */
    List<Peispatient> getRegistrationMachineList(@Param("idcardno") String idcardno);

    /**
     * 检查体检号和手机号是否相符合
     * @param patientcode
     * @param phone
     * @return
     */
    long checkPatientcode(@Param("shortCode") String patientcode,@Param("phone") String phone);
}
