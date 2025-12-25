package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.FamilyBirthParam;
import com.center.medical.member.bean.param.FamilyCardParam;
import com.center.medical.member.bean.param.FamilyChargeParam;
import com.center.medical.member.bean.param.FamilyMemberParam;
import com.center.medical.member.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者档案表(Peispatientarchive)表数据库访问层
 *
 * @author ay
 * @since 2023-03-01 10:34:46
 */
public interface FamilyMemberMapper extends BaseMapper<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    IPage<FamilyMemberVo> getList(PageParam<FamilyMemberVo> page, @Param("param") FamilyMemberParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(@Param("id") String id);

    /**
     * 家庭会员-家庭卡消费查询
     * @param param
     * @return
     */
    List<String> familyCardList(@Param("param")FamilyCardParam param);

    /**
     * 分页家庭卡消费记录
     * @param page
     * @param param
     * @return
     */
    IPage<FamilyChargeVo> familyChargeData(PageParam<FamilyChargeVo> page,@Param("param") FamilyChargeParam param);

    /**
     * 导出家庭卡消费记录数据
     * @param param
     * @return
     */
    List<FamilyChargeVo> chargeExportData(@Param("param")FamilyChargeParam param);

    /**
     * 分页查询家庭卡生日
     * @param page
     * @param param
     * @return
     */
    IPage<FamilyBirthVo> FamilyBirthPage(PageParam<FamilyBirthVo> page,@Param("param") FamilyBirthParam param);

    /**
     * 新增家庭卡办理-左下数据
     * @param page
     * @param id
     * @return
     */
    IPage<ChargeInfoVo> getChargeInfoData(PageParam<ChargeInfoVo> page,@Param("id") String id);

    /**
     * 获取其他家庭成员信息
     * @param cardno
     * @return
     */
    List<InfoListDataVo> getInfoListData(@Param("cardno") String cardno);
}
