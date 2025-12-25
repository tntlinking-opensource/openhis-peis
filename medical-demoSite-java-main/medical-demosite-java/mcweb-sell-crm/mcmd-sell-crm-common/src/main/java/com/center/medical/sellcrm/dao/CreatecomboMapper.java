package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.vo.ItemsVo;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.param.CreatecomboParam;
import com.center.medical.sellcrm.bean.param.CreatecomboParam1;
import com.center.medical.sellcrm.bean.param.DataAddMealParam;
import com.center.medical.sellcrm.bean.param.ZxtcsDataParam;
import com.center.medical.sellcrm.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 最小套餐(Createcombo)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-14 19:21:18
 */
public interface CreatecomboMapper extends BaseMapper<Createcombo> {

    /**
     * 分页查询[最小套餐]列表
     *
     * @param page  分页参数
     * @param param Createcombo查询参数
     * @return 分页数据
     */
    IPage<Createcombo> getList(PageParam<Createcombo> page, @Param("param") CreatecomboParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createcombo getInfoById(@Param("id") String id);

    /**
     * 获取同步套餐信息
     *
     * @param comboIds 要同步的套餐id集合
     * @return
     */
    List<Comboexamitem> getSynchronous(@Param("comboIds") List<String> comboIds);

    /**
     * 根据输入套餐名称或拼音码分页查询
     *
     * @param page 分页参数
     * @param key  输入值
     * @return 所有数据
     */
    IPage<CoSimpleVo> getAutoCompleteData(PageParam<Createcombo> page, @Param("key") String key, @Param("cid") String cid);

    /**
     * 根据接害因素和职业体检类别获取关联的收费项目,将数据返回
     *
     * @return
     */
    List<ItemsVo> getPpZxtcData(@Param("param") CreatecomboParam1 param);

    /**
     * 判断是否必检
     *
     * @return
     */
    Integer getSfbj(@Param("itemsId") String itemsId, @Param("jhId") List<String> jhId, @Param("zyValue") Integer zyValue);

    /**
     * 获取存在于最小套餐内的收费项目
     *
     * @param jhys
     * @param idss
     * @param medicaltype
     * @return
     */
    String compareMinTcContent(@Param("strharm") String jhys, @Param("strids") List<String> idss, @Param("zytjlb") String medicaltype);

    /**
     * 新增套餐-体检套餐列表
     *
     * @param page
     * @param param
     * @return
     */
    IPage<DataAddMealVo> getListDataAddMeal(PageParam<DataAddMealVo> page, @Param("param") DataAddMealParam param);

    /**
     * 获取和套餐关联的收费项目
     *
     * @param page
     * @param test
     * @return
     */
    IPage<SmallItemsVo> getSmallItems(PageParam<SmallItemsVo> page, @Param("text") String test);

    /**
     * 加载所有最小套餐按照分中心
     *
     * @param page
     * @param param
     * @return
     */
    IPage<ZxtcsDataVo> getZxtcsData(PageParam<ZxtcsDataVo> page, @Param("param") ZxtcsDataParam param);

    /**
     * 获取所有套餐
     *
     * @param key
     * @return
     */
    List<AllComboMealVo> getAllComboAndMealData(@Param("key") String key);

    /**
     * 获取所有基础套餐
     *
     * @param key
     * @return
     */
    List<BasicDataVo> getBasicData(@Param("key") String key);

    /**
     * 通过最小套餐id获取成本价
     * @param id
     * @return
     */
    Double getCostpriceByTcid(@Param("id") String id);

    /**
     * 获取套餐的分中心名称
     * @param id
     * @return
     */
    String getBranchNames(@Param("id") String id);
}
