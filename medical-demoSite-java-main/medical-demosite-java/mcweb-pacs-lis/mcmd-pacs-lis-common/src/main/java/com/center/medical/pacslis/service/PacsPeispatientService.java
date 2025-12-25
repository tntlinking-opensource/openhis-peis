package com.center.medical.pacslis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.pacslis.bean.model.PacsPeispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.pacslis.bean.param.*;
import com.center.medical.pacslis.bean.vo.DivisionVo;
import com.center.medical.pacslis.bean.vo.ItemListVo;
import com.center.medical.pacslis.bean.vo.ReservationUserVo;

import java.util.HashMap;
import java.util.List;

/**
 * PACS-体检者表(PacsPeispatient)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:08
 */
public interface PacsPeispatientService extends IService<PacsPeispatient> {

    /**
     * 分页查询[PACS-体检者表]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatient查询参数
     * @return 分页数据
     */
    ItemListVo getPage(PageParam<PacsPeispatient> page, ItemListParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsPeispatient getInfoById(String id);

    /**
     * PACS-彩超审核查看分页
     * @param page
     * @param param
     * @return
     */
    IPage<DivisionVo> getDivPage(PageParam<PacsPeispatient> page, DivisionParam param);

    /**
     * 取得已预约客户
     * @param page
     * @param param
     * @return
     */
    PageParam<ReservationUserVo> getReservationUser(PageParam<PacsPeispatient> page, ReservationUserParam param);

    /**
     * 获取当前选中的已预约用户信息
     * @param patientCode
     * @param type
     * @param autoFill
     * @return
     */
    HashMap getCustomerData(String patientCode, String type, String autoFill);


    /**
     * 根据体检号查询体检者信息
     * @param patientCode
     * @param autoFill
     * @return
     */
    PacsPeispatient getPeispatient(String patientCode, String autoFill);


    /**
     * 获取右侧收费项目
     * @param patientCode
     * @param type
     * @return
     */
    List<PacsPeispatientfeeitem> getExamfeeByPatientCode(String patientCode, String type);

    /**
     * 团检退项匹配最小套餐
     * @param param
     * @return
     */
    Boolean compareMinTcContent(ComMinParam param);

    /**
     * 得到备单人员的信息
     * @param id
     * @param patientCode
     * @return
     */
    HashMap getPatientData(String id, String patientCode);

    /**
     * 根据体检号查询不同状态的收费项目
     * @param type
     * @param patientCode
     * @return
     */
    HashMap getKindItems(String type, String patientCode);


    /**
     * 查找剩余的未检收费项目是否是弃检、迟检、退项状态判断是否需要分检完成，无科室的检查完
     * @param pei
     */
    void checkFj(PacsPeispatient pei);

    /**
     * 登记保存右上列表
     * @param param
     * @return
     */
    Boolean saOrUp(PrSaOrUpParam param) throws Exception;
}

