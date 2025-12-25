package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.dto.SiFormdataDto;
import com.center.medical.abteilung.bean.dto.SiGriddataDto;
import com.center.medical.abteilung.bean.param.ModifyStatusParam;
import com.center.medical.abteilung.bean.param.UpdateItemsDealParam;
import com.center.medical.abteilung.bean.vo.PopDataVo;
import com.center.medical.abteilung.bean.vo.ViewThirdPartyImagesVo;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-01-15 16:06:44
 */
public interface SignInInspectionService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getList(PageParam<Peispatient> page, Peispatient param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 体检者查询
     *
     * @param patientCode
     * @param autoFill
     * @param key
     * @return
     */
    HashMap getRecheckItems(String patientCode, String autoFill, String key);

    /**
     * 备单人员登记信息查询
     *
     * @param patientCode
     * @param id
     * @return
     */
    HashMap getPatientData(String patientCode, String id);

    /**
     * 检查危急值
     *
     * @param patientcode
     * @return
     */
    String checkDanger(String patientcode);

    /**
     * 交单
     *
     * @param patientCode
     * @return
     */
    Boolean surrender(List<String> patientCode);


    /**
     * 护理登记弃检
     *
     * @param pei
     * @param griddata
     * @param intReservation
     * @param formdata
     * @param noticeJyk
     */
    String saveOrUpdateItem(Peispatient pei, List<SiGriddataDto> griddata, int intReservation, SiFormdataDto formdata, String noticeJyk , String path) throws Exception;

    /**
     * 拒检
     *
     * @param ids
     * @param data
     * @return
     */
    Boolean jujian(List<String> ids, String data) throws Exception;

    /**
     * 反拒检
     *
     * @param ids
     * @return
     */
    Boolean fanjujian(List<String> ids);

    /**
     * 补检，反补检
     *
     * @param param
     * @return
     */
    Boolean updateItemsDeal(UpdateItemsDealParam param);


    /**
     * 获取登记页面最小套餐收费项目明细
     *
     * @param tcid
     * @return
     */
    List<HashMap<String, String>> getExamfeeitem(String tcid);


    /**
     * 获取体检者收费项目
     *
     * @param tcid
     * @return
     */
    List getExamfeeitemData(String tcid, String idOrder, String idGroup);


    /**
     * 获取体检者收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    List<Map<String, Object>> getExamfeeByPatientCode(String patientCode, String type);

    /**
     * 获取自助项目弹窗数据
     * @param patientCode
     * @return
     */
    List<PopDataVo> getPopData(String patientCode);

    /**
     * 设置获取自助项目弹窗数据
     * @param ids
     * @return
     */
    Boolean setPopData(List<String> ids);


    /**
     * 自助项目弃检
     * @param ids
     * @return
     */
    Boolean popGiveUp(List<String> ids,String path);

    /**
     * 上传第三方报告
     * @param file
     * @param feeItemId
     * @param patientcode
     * @return
     */
    Boolean uploadThirdReports(MultipartFile file, String feeItemId, String patientcode,String cId);

    /**
     * 删除第三方系统报告
     * @param feeItemId
     * @param patientcode
     * @return
     */
    Boolean deleteThirdReports(String feeItemId, String patientcode , String filePath);

    /**
     * 修改已检或未检
     * @param param
     * @return
     */
    Boolean modifyProjectStatus(ModifyStatusParam param);

    /**
     * 查看第三方图片
     * @param patientCode
     * @return
     */
    List<ViewThirdPartyImagesVo> viewThirdPartyImages(String patientCode);
}

