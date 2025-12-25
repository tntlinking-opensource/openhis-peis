package com.center.medical.machine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.machine.bean.dto.RegisterSelectDTO;
import com.center.medical.machine.bean.model.PackageItemEntity;
import com.center.medical.machine.bean.model.RegisteredEntity;
import com.center.medical.machine.bean.param.ModelParam;
import com.center.medical.machine.bean.vo.ResultVo;

import java.util.List;
import java.util.Map;

/**
 * 自助登记机(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
public interface RegistrationMachineService extends IService<Peispatient> {


    /**
     * 自助登记
     * @param registeredEntity
     * @return
     */
    ResultVo register(RegisteredEntity registeredEntity);

    /**
     * 登记的体检信息
     * @param patientCodes
     * @return
     */
    List<RegisterSelectDTO> registerSelect(List<String> patientCodes);

    /**
     * 自助登记-确认登记
     * @param registeredEntity
     * @return
     */
    ResultVo confirmRegister(RegisteredEntity registeredEntity);

    /**
     * 自助登记-打印
     * @param registeredEntity
     * @return
     */
    Map<String,Object> print(RegisteredEntity registeredEntity);

    /**
     * 自助登记-完成
     * @param patientcode
     * @return
     */
    Map<String, Object> complete(String patientcode);

    /**
     * 自助登记-打印导引单数据
     * @param param
     * @return
     */
    List<Map<String, Object>> getBillModelData(ModelParam param);

    /**
     * 引导单模板、条码展示页数据
     * @param cid
     * @return
     */
    Map<String, Object> all(String cid);

    /**
     * 检查金额
     * @param patientCode
     * @return
     */
    ResultVo checkAmount(String patientCode);


    /**
     * 分组确认项目
     * @param registeredEntity
     * @return
     */
    ResultVo groupConfirmItems(RegisteredEntity registeredEntity);

    /**
     * 获取项目
     * @param registeredEntity
     * @return
     */
    PackageItemEntity getMedicalExaminationItem(RegisteredEntity registeredEntity);


    /**
     * 自助登记-个人信息
     * @param registeredEntity
     * @return
     */
    Peispatient registerInfo(RegisteredEntity registeredEntity);
}

