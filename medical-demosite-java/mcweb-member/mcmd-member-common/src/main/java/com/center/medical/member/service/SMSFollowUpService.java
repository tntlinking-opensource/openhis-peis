package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.SmsRecord;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.SMSFollowUpParam;
import com.center.medical.member.bean.param.SMSSaOrUpParam;
import com.center.medical.member.bean.param.SmsDataParam;
import com.center.medical.member.bean.param.SmssendParam;
import com.center.medical.member.bean.vo.SMSFollowUpVo;

import java.util.HashMap;
import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-02-10 14:25:16
 */
public interface SMSFollowUpService extends IService<Peispatient> {

    /**
    * 分页查询[QT体检者表]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<SMSFollowUpVo> getList(PageParam<SMSFollowUpVo> page, SMSFollowUpParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Peispatient getInfoById(String id);

    /**
     * 查看短信数据
     * @param page
     * @param param
     * @return
     */
    IPage<SmsRecord> getSmsData(PageParam<SmsRecord> page, SmsDataParam param);

    /**
     * 查看短信数据
     * @param param
     * @return
     */
    List<HashMap<String, Object>> smssend(SmssendParam param);

    /**
     * 取消发送
     * @param patientcode
     * @return
     */
    Boolean cancleSMS(List<String> patientcode);

    /**
     * 群发短信编辑-保存
     * @param param
     * @return
     */
    Boolean saveGroupData(SMSSaOrUpParam param);
}

