package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.vo.ItemsVo;
import com.center.medical.sellcrm.bean.dto.CreatemealExportXyDto;
import com.center.medical.sellcrm.bean.model.Comboanditem;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.bean.param.*;
import com.center.medical.sellcrm.bean.vo.ZxtcsDataVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 普通套餐表(Createmeal)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-14 19:21:19
 */
public interface CreatemealService extends IService<Createmeal> {

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Createmeal> getPage(PageParam<Createmeal> page, CreatemealParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createmeal getInfoById(String id);

    /**
     * 新增/编辑操作
     *
     * @param param
     * @return
     */
    Boolean saOrUp(MealSaOrUpParam param);

    /**
     * 复制套餐
     *
     * @param param  新的属性参数
     * @param userNo
     * @return
     */
    Boolean repeat(TcCopyParam param, String userNo);

    /**
     * 判断套餐能否编辑
     *
     * @param tcId
     * @return
     */
    String isEdit(String tcId);

    /**
     * 判断套餐能否删除
     *
     * @param tcId
     * @return
     */
    String isRemove(List<String> tcId);

    /**
     * 根据接害因素和职业体检类别获取关联的收费项目,将数据返回
     *
     * @return
     */
    List<ItemsVo> getPpZxtcData(CreatecomboParam1 param);

    /**
     * 根据状态位判断套餐折扣
     *
     * @param discount 当前折扣
     * @param tczkId   套餐ID
     * @return
     */
    R<Boolean> onZk(Double discount, String tczkId);

    /**
     * 判断是否必检
     *
     * @return
     */
    List<Integer> getSfbj(CreatecomboParam1 param);

    /**
     * 禁用/反禁用套餐
     *
     * @param ids   操作的对象主键id集合
     * @param state 操作标识：0反禁用 1禁用
     * @return
     */
    Boolean setBan(List<String> ids, Integer state);

    /**
     * 设置平安ID
     *
     * @param id       操作的套餐id
     * @param pinganId 平安ID
     * @return
     */
    Boolean setPinganId(String id, String pinganId);

    /**
     * 获取普通套餐与最小套餐的数据
     *
     * @param param
     * @return
     */
    IPage<Createmeal> getTcData(PageParam<Createmeal> page, CreateorderParam param);


    /**
     * 返回客户从未使用过的套餐和客户单位电话
     *
     * @param khdwdhId
     * @param ids
     * @return
     */
    List<Createmeal> getKhdwdhAndTcs(String khdwdhId, List<String> ids);

    /**
     * 检查添加的收费项目下是否有检查项目重复,给予提示
     *
     * @param gridId
     * @return
     */
    String compareItemsToExam(List<String> gridId);

    /**
     * 获取普通套餐关联的收费项目
     *
     * @param list
     * @return
     */
    List<Map> getDatas(List<Comboanditem> list);

    /**
     * 加载所有最小套餐--按照分中心
     *
     * @param param
     * @return
     */
    IPage<ZxtcsDataVo> getZxtcsData(PageParam<ZxtcsDataVo> page, ZxtcsDataParam param);

    /**
     * 保存前判断是否重复
     *
     * @param jhysValue
     * @param zytjlbValue
     * @param id
     * @return
     */
    String isReport(String jhysValue, String zytjlbValue, String id);

    /**
     * 套餐导出
     *
     * @param tcIds 选择导出的套餐ID集合
     */
    void getExportData(HttpServletResponse response,List<String> tcIds);

    /**
     * 导出协议套餐
     *
     * @param tcId 选择的套餐ID
     */
    List<CreatemealExportXyDto> getExportXyData(List<String> tcId,String orderId);

    /**
     * 导出订单的套餐
     * @param response
     * @param tcIds
     */
    void getExportTc(HttpServletResponse response, String tcIds) throws IOException;

    /**
     * 添加项目的分中心
     * @param fzx
     * @return
     */
    Boolean addItemsFzx(String fzx);

    /**
     * 判断套餐价格和收费项目的总金额是否一致
     * @param patientcode
     * @param tcId
     * @return
     */
    boolean isConsistentPrice(String patientcode,String tcId);

    /**
     * addTotalCostprice
     * @return
     */
    Boolean addTotalCostprice();
}

