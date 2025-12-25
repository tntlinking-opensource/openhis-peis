package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.dto.*;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.UserExamDataParam;
import com.center.medical.bean.vo.PacsHistoryListVo;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatient;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2023-08-12 11:54:59
 */
public interface OrPeispatientMapper extends BaseMapper<OrPeispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<OrPeispatient> getPage(PageParam<OrPeispatient> page, @Param("param") OrPeispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatient getInfoById(@Param("id") String id);

    /**
     * 通过分组id获取体检者
     *
     * @param groupId
     * @return
     */
    List<OrPeispatient> getByGroupId(@Param("groupId") String groupId);

    /**
     * 查询未完成登记的体检者
     * @return
     */
    List<OrPeispatient> getUnRegistered();

    /**
     * 通过体检号查询体检者
     * @param oldPatientCode
     * @return
     */
    OrPeispatient getInfoByPatientCode(@Param("oldPatientCode") String oldPatientCode);

    /**
     * 查询老系统个检人员
     * @return
     */
    List<OrPeispatient> getImportPeople();

    /**
     * 通过订单号查询
     * @param ddh
     * @return
     */
    List<OrPeispatient> getByDdh(@Param("ddh") String ddh);

    /**
     * 批量查询
     * @param peispatient
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<OrPeispatient> getList(@Param("param") Peispatient peispatient);


    /**
     * 通过体检号批量查询
     * @param patientCodes
     * @return
     */
    List<OrPeispatient> getByPatientCodes(@Param("patientCodes") List<String> patientCodes);

    /**
     * 项目完成情况
     * @param patientCodes
     * @return
     */
    List<FindXmwcqkDto> findXmwcqk(@Param("patientCodes") List<String> patientCodes);

    /**
     * 查询老系统项目參检
     * @param patientCodes
     * @return
     */
    List<FindXmcjDto> findXmcj(@Param("patientCodes") List<String> patientCodes);

    /**
     * 检出统计团体小结
     * @param patientCodes
     * @return
     */
    List<FindJctjDto> findJctj(@Param("patientCodes") List<String> patientCodes);

    /**
     * 阳性结果
     * @param patientCodes
     * @return
     */
    List<FindYangxjgDto> findYangxjg(@Param("patientCodes") List<String> patientCodes);

    /**
     * 查询阴性结果
     * @param cid
     * @param patientCode
     * @param bhys
     * @return
     */
    List<FindYinxjgDto> findYinxjg(@Param("cid") String cid , @Param("patientCode") String patientCode , @Param("bhys") boolean bhys);

    /**
     * 查询职业报告体检者
     * @param patientCodes
     * @return
     */
    List<FindDtjzDto> findDtjz(@Param("patientCodes") List<String> patientCodes);

    /**
     * 职业必查项目
     * @param patientCodes
     * @return
     */
    List<FindZybcxmDto> findZybcxm(@Param("patientCodes") List<String> patientCodes);

    /**
     * 人员一览表
     * @param patientCodes
     * @return
     */
    List<FindRyylDto> findRyyl(@Param("patientCodes") List<String> patientCodes);

    /**
     * 复查明细
     * @param patientCodes
     * @return
     */
    List<FindFcmxDto> findFcmx(@Param("patientCodes") List<String> patientCodes);

    /**
     * 复查情况
     * @param patientCodes
     * @return
     */
    List<FindFcqkDto> findFcqk(@Param("patientCodes") List<String> patientCodes);

    /**
     * 根据复查体检号查询
     * @param patientCode
     * @return
     */
    List<OrPeispatient> getListByinPatientno(@Param("patientCode") String patientCode);

    /**
     * 查询复查结果
     * @param patientCode
     * @return
     */
    List<FindListInDto> findListIn(@Param("patientCode") String patientCode);

    /**
     * 查询复查数量
     * @param patientcode
     * @return
     */
    int findNumByPatientcode(@Param("patientCode") String patientcode);

    /**
     * 查询检查人数
     * @param patientCodes
     * @return
     */
    List<FindJcrsDto> findJcrs(@Param("patientCodes") List<String> patientCodes);

    /**
     * 查询结论数据
     * @param patientCodes
     * @return
     */
    List<FindListDateDto> findListDate(@Param("patientCodes") List<String> patientCodes);

    /**
     * 检查情况汇总
     * @param patientCodes
     * @return
     */
    List<FindJcqkhzDto> findJcqkhz(@Param("patientCodes") List<String> patientCodes);

    /**
     * 获取历史数据
     * @param idcardno
     * @param itemId
     * @param ksID
     * @return
     */
    List<PacsHistoryListVo> getHistoryList(@Param("idcardno") String idcardno, @Param("itemId") String itemId, @Param("ksID")  String ksID, @Param("ids") List<String> ids);

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
}
