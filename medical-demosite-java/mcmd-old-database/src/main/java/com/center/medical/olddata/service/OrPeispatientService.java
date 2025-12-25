package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.*;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.RequestParam;
import com.center.medical.bean.vo.PacsHistoryListVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrPeispatient;

import java.util.ArrayList;
import java.util.List;

/**
 * QT体检者表(Peispatient)服务接口
 *
 * @author ay
 * @since 2023-08-12 11:55:00
 */
public interface OrPeispatientService extends IService<OrPeispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<OrPeispatient> getPage(PageParam<OrPeispatient> page, OrPeispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrPeispatient getInfoById(String id);

    /**
     * 根据分组id获取体检者
     *
     * @param groupId
     * @return
     */
    List<OrPeispatient> getByGroupId(String groupId);

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
    OrPeispatient getInfoByPatientCode(String oldPatientCode);

    /**
     * 查询老系统个检人员
     * @return
     */
    List<OrPeispatient> getImportPeople();

    /**
     * codeCorresponding
     * @param orPeispatient
     */
    void setFPaused(OrPeispatient orPeispatient);

    /**
     * 通过订单号查询
     * @param ddh
     * @return
     */
    List<OrPeispatient> getByDdh(String ddh);

    /**
     * 批量查询
     * @param peispatient
     * @return
     */
    List<Peispatient> getList(Peispatient peispatient);

    /**
     * 通过体检号批量查询
     * @param patientCodes
     * @return
     */
    List<OrPeispatient> getByPatientCodes(List<String> patientCodes);

    /**
     * 查询项目完成情况
     * @param patientCodes
     * @return
     */
    List<FindXmwcqkDto> findXmwcqk(List<String> patientCodes);

    /**
     * 查询老系统项目參检
     * @param patientCodes
     * @return
     */
    List<FindXmcjDto> findXmcj(List<String> patientCodes);

    /**
     * 检出统计 团体小结
     * @param patientCodes
     * @return
     */
    List<FindJctjDto> findJctj(List<String> patientCodes);

    /**
     * 阳性结果
     * @param strings
     * @return
     */
    List<FindYangxjgDto> findYangxjg(ArrayList<String> strings);

    /**
     * 查询阴性结果
     * @param cid
     * @param patientCode
     * @param bhys
     * @return
     */
    List<FindYinxjgDto> findYinxjg(String cid, String patientCode, boolean bhys);

    /**
     * 查询职业报告体检者
     * @param patientCodes
     * @return
     */
    List<FindDtjzDto> findDtjz(ArrayList<String> patientCodes);

    /**
     * 职业必查项目
     * @param patientCodes
     * @return
     */
    List<FindZybcxmDto> findZybcxm(ArrayList<String> patientCodes);

    /**
     * 人员一览表
     * @param patientCodes
     * @return
     */
    List<FindRyylDto> findRyyl(ArrayList<String> patientCodes);

    /**
     * 复查明细
     * @param patientCodes
     * @return
     */
    List<FindFcmxDto> findFcmx(ArrayList<String> patientCodes);

    /**
     * 复查情况
     * @param patientCodes
     * @return
     */
    List<FindFcqkDto> findFcqk(ArrayList<String> patientCodes);

    /**
     * 根据复查体检号查询
     * @param patientCode
     * @return
     */
    List<Peispatient> getListByinPatientno(String patientCode);

    /**
     * 查询复查结果
     * @param patientCode
     * @return
     */
    List<FindListInDto> findListIn(String patientCode);

    /**
     * 查询复查数量
     * @param patientcode
     * @return
     */
    int findNumByPatientcode(String patientcode);

    /**
     * 查询老系统实查人数
     * @param patientCodes
     * @return
     */
    int findScrs(List<String> patientCodes);

    /**
     * 查询检查人数
     * @param patientCodes
     * @return
     */
    List<FindJcrsDto> findJcrs(List<String> patientCodes);

    /**
     * 查询结论数据
     * @param patientCodes
     * @return
     */
    List<FindListDateDto> findListDate(List<String> patientCodes);

    /**
     * 检查情况汇总
     * @param patientCodes
     * @return
     */
    List<FindJcqkhzDto> findJcqkhz(List<String> patientCodes);

    /**
     * 查询老老系统历史
     * @param idcardno
     * @param itemId
     * @param ksId
     * @return
     */
    List<PacsHistoryListVo> getHistoryList(String idcardno, String itemId, String ksId,List<String> ids);

    /**
     * 查询老系统是否已登记
     * @param id
     * @return
     */
    Boolean isRegistered(String id);



    /**
     * 查询推送给第三方的旧系统的体检者数据
     *
     * @param params
     * @return
     */
    void pushOldDataToCoo(RequestParam params);
}

