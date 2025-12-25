package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.param.CreatecomboParam;
import com.center.medical.sellcrm.bean.param.DataAddMealParam;
import com.center.medical.sellcrm.bean.param.TcCopyParam;
import com.center.medical.sellcrm.bean.vo.*;

import java.util.List;

/**
 * 最小套餐(Createcombo)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-14 19:21:18
 */
public interface CreatecomboService extends IService<Createcombo> {

    /**
     * 分页查询[最小套餐]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Createcombo> getList(PageParam<Createcombo> page, CreatecomboParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createcombo getInfoById(String id);

    /**
     * 新增或者更新最小套餐信息
     *
     * @param createcombo
     * @return
     */
    Boolean saOrUp(Createcombo createcombo);

    /**
     * 复制套餐
     *
     * @param param  新的属性参数
     * @param userNo
     * @return
     */
    Boolean copy(TcCopyParam param, String userNo);

    /**
     * 根据接害因素和职业体检类别判断是否存在重复,存在重复不能进行保存
     *
     * @param param 判断的属性参数
     * @return
     */
    Boolean isRepeat(TcCopyParam param);

    /**
     * 同步套餐
     *
     * @param comboIds 要同步的套餐id集合
     * @return
     */
    Boolean synchronous(List<String> comboIds);

    /**
     * 设置/取消活动套餐
     *
     * @param comboIds 要操作的套餐id集合
     * @param state    操作状态：1设置 0取消
     * @return
     */
    Boolean setActive(List<String> comboIds, Integer state);

    /**
     * 设置/取消推荐套餐
     *
     * @param comboIds 要操作的套餐id集合
     * @param state    操作状态：1设置 0取消
     * @return
     */
    Boolean setRecommend(List<String> comboIds, Integer state);

    /**
     * 禁用/反禁用套餐
     *
     * @param comboIds 要操作的套餐id集合
     * @param state    操作状态：1设置 0取消
     * @return
     */
    Boolean setBan(List<String> comboIds, Integer state);

    /**
     * 根据输入套餐名称或拼音码分页查询
     *
     * @param page 分页参数
     * @param key  输入值
     * @return 所有数据
     */
    IPage<CoSimpleVo> getAutoCompleteData(PageParam<Createcombo> page, String key, Long userId);

    /**
     * 获取存在于最小套餐内的收费项目
     *
     * @param jhys
     * @param idss
     * @param medicaltype
     * @return
     */
    String compareMinTcContent(String jhys, List<String> idss, String medicaltype);

    /**
     * 新增套餐-体检套餐列表
     *
     * @param page
     * @param param
     * @return
     */
    IPage<DataAddMealVo> getListDataAddMeal(PageParam<DataAddMealVo> page, DataAddMealParam param);

    /**
     * 获取和套餐关联的收费项目
     *
     * @param page
     * @param test
     * @return
     */
    IPage<SmallItemsVo> getSmallItems(PageParam<SmallItemsVo> page, String test);

    /**
     * 获取所有套餐
     *
     * @param key
     * @return
     */
    List<AllComboMealVo> getAllComboAndMealData(String key);

    /**
     * 获取所有基础套餐
     *
     * @param key
     * @return
     */
    List<BasicDataVo> getBasicData(String key);

    /**
     * 批量添加分中心
     * @param fzxId
     * @return
     */
    Boolean addFzx(String fzxId);

    /**
     * 批量删除分中心
     * @param fzxId
     * @return
     */
    Boolean deleteFzx(String fzxId);

    /**
     * 添加项目成本价合计
     * @return
     */
    Boolean addTotalCostprice();
}

