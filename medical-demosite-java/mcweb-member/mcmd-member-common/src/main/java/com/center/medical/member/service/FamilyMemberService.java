package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.member.bean.param.*;
import com.center.medical.member.bean.vo.*;

import java.util.List;
import java.util.Map;

/**
 * 体检者档案表(Peispatientarchive)表服务接口
 *
 * @author ay
 * @since 2023-03-01 10:34:46
 */
public interface FamilyMemberService extends IService<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<FamilyMemberVo> getList(PageParam<FamilyMemberVo> page, FamilyMemberParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(String id);

    /**
     * 家庭会员-家庭卡消费查询
     *
     * @param param
     * @return
     */
    Card familyCardList(FamilyCardParam param);

    /**
     * 家庭会员详情数据
     *
     * @param patientcardno
     * @return
     */
    Map<String, Object> getCardData(String patientcardno);

    /**
     * 家庭会员充值保存
     *
     * @return
     */
    Map<String, Object> saveCharge(SaveChargeParam param);

    /**
     * 新增家庭卡办理-保存
     *
     * @param param
     * @return
     */
    Boolean saOrUp(FMSaOrUpParam param);


    /**
     * 新增家庭卡办理-保存线上
     *
     * @param param
     * @return
     */
    Boolean saveOrUpdateOl(FMSaOrUpParam param);

    /**
     * 家庭卡消费
     *
     * @param param
     * @return
     */
    Map<String, Object> saveConsumex(SaveConParam param);

    /**
     * 分页家庭卡消费记录
     *
     * @param page
     * @param param
     * @return
     */
    IPage<FamilyChargeVo> familyChargeData(PageParam<FamilyChargeVo> page, FamilyChargeParam param);

    /**
     * 导出家庭卡消费记录数据
     *
     * @param param
     * @return
     */
    List<FamilyChargeVo> chargeExportData(FamilyChargeParam param);

    /**
     * 分页查询家庭卡生日
     *
     * @param page
     * @param param
     * @return
     */
    IPage<FamilyBirthVo> FamilyBirthPage(PageParam<FamilyBirthVo> page, FamilyBirthParam param);

    /**
     * 新增家庭卡办理-上方数据
     *
     * @param cardno
     * @return
     */
    Map<String, Object> getAddData(String cardno);

    /**
     * 新增家庭卡办理-左下数据
     *
     * @param page
     * @param cardno
     * @return
     */
    IPage<ChargeInfoVo> getChargeInfoData(PageParam<ChargeInfoVo> page, String cardno);

    /**
     * 获取其他家庭成员信息
     * @param cardno
     * @return
     */
    List<InfoListDataVo> getInfoListData(String cardno);
}

