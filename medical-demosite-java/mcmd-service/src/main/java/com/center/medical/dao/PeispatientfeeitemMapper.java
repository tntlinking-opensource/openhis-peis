package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.dto.ItemsExamCountDto;
import com.center.medical.bean.dto.PriceAndFactPriceDto;
import com.center.medical.bean.dto.RecalculatePriceDto;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.param.ExportItemsParam;
import com.center.medical.bean.vo.*;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-18 19:40:38
 */
public interface PeispatientfeeitemMapper extends BaseMapper<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<Peispatientfeeitem> getPage(PageParam<Peispatientfeeitem> page, @Param("param") Peispatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientfeeitem getInfoById(@Param("id") String id);

    /**
     * 计算收费项目体检次数
     *
     * @param now    截止时间
     * @param deptId 科室ID
     * @return
     */
    List<ItemsExamCountDto> getItemsCount(@Param("now") Date now, @Param("deptId") String deptId);

    /**
     * 查询非功能科室
     *
     * @param patientCode
     * @return
     */
    List<Peispatientfeeitem> getNoCheckItems(@Param("patientCode") String patientCode);

    /**
     * 查询未缴费项目
     *
     * @param userNo
     * @param patientCode
     * @return
     */
    List<Peispatientfeeitem> selectUnfees(@Param("userNo") String userNo, @Param("patientCode") String patientCode);

    /**
     * 通过体检码查询收费项目名称
     *
     * @param patientcode
     * @return
     */
    List<String> findExamitemNameByCode(@Param("patientcode") String patientcode);

    /**
     * 项目列表
     *
     * @param page
     * @param patientcode
     * @return
     */
    IPage<GetItemDataVo> getItemData(PageParam<GetItemDataVo> page, @Param("patientcode") String patientcode);

    /**
     * 外送项目
     *
     * @param page
     * @param key
     * @param patientcode
     * @return
     */
    IPage<WsxmDataVo> getWsxmData(PageParam<WsxmDataVo> page, @Param("key") String key, @Param("patientcode") String patientcode);

    /**
     * 无关联科室已检
     *
     * @param patientcode
     * @return
     */
    List<Peispatientfeeitem> irrelevantInspect(@Param("patientcode") String patientcode);

    /**
     * 科室加项右侧数据
     *
     * @param patientcode
     * @return
     */
    List<AddListDataVo> getAddListData(@Param("patientcode") String patientcode);

    /**
     * 外送登记项目列表
     *
     * @param page
     * @param patientcode
     * @return
     */
    IPage<OSItemDataVo> getOSItemData(PageParam<OSItemDataVo> page, @Param("patientcode") String patientcode);

    /**
     * 导出体检者收费项目列表
     *
     * @param param
     * @return
     */
    List<ExportItemsVo> exportItems(@Param("param") ExportItemsParam param);

    /**
     * 获取所有尚未获取Lis数据的体检号
     *
     * @return
     */
    List<String> receiveLisDataUser(@Param("daysAgo") Integer daysAgo);

    /**
     * 通过体检号查询收费项目
     *
     * @param patientcode
     * @return
     */
    List<Peispatientfeeitem> getByPatientCode(@Param("patientcode") String patientcode);

    /**
     * 一键体检者收费项目去重
     *
     * @param patientcode 体检号
     * @return
     */
    Boolean deduplication(@Param("patientcode") String patientcode);

    /**
     * 查询该科室未出结果的项目
     * @param patientcode
     * @return
     */
    int getDepartmentResults(@Param("patientcode") String patientcode);

    /**
     * 获取原价和优惠价
     * @param patientCode
     * @return
     */
    PriceAndFactPriceDto getPriceAndFactprice(@Param("patientCode") String patientCode);

    /**
     * 查询未收费项目
     * @param patientCode
     * @return
     */
    Integer unpaidItems(@Param("patientCode") String patientCode);

    /**
     * 获取华欧医院外送登记项目列表
     * @param page
     * @param patientcode
     * @return
     */
    IPage<OSItemDataVo> getHuaOuItemData(PageParam<OSItemDataVo> page, @Param("patientcode") String patientcode);

    /**
     * 判断体检者收费项目是否重复
     * @param patientcode
     * @return
     */
    List<Peispatientfeeitem> isRepeat(@Param("patientcode") String patientcode);

    /**
     * 查出收费项目不对的体检号
     * @param ddhs
     * @return
     */
    List<RecalculatePriceDto> recalculatePrice(@Param("ddhs") List<String> ddhs);

    /**
     * 获取未退费的项目数量
     * @param patientcode
     * @return
     */
    int getUnreimbursedProjects(@Param("patientcode") String patientcode);

    /**
     * 判断是否包含早餐
     * @param patientcode
     * @return
     */
    Integer includesBreakfast(@Param("patientcode") String patientcode);

    /**
     * 获取 Boying 的心电图收费项目
     * @param patientcode
     * @param ksId
     * @return
     */
    Peispatientfeeitem selectByBoying(@Param("patientcode") String patientcode, @Param("ksId") String ksId);
}
