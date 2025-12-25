package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.OutsideChargeItem;
import com.center.medical.abteilung.bean.param.SendRegisterParam;
import com.center.medical.abteilung.bean.param.SrSaOrUpParam;
import com.center.medical.abteilung.bean.vo.OutsideVo;
import com.center.medical.bean.vo.WsxmDataVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;
import java.util.Map;

/**
 * KS外送项目表(OutsideChargeItem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:25
 */
public interface OutsideChargeItemService extends IService<OutsideChargeItem> {

    /**
     * 分页查询[KS外送项目表]列表
     *
     * @param page  分页参数
     * @param param OutsideChargeItem查询参数
     * @return 分页数据
     */
    IPage<OutsideVo> getPage(PageParam<OutsideChargeItem> page, SendRegisterParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OutsideChargeItem getInfoById(String id);

    /**
     * 新增外送登记-保存
     * @param param
     * @return
     */
    Boolean saOrUp(SrSaOrUpParam param);

    /**
     * 不分页查询列表
     * @param param
     * @return
     */
    List<OutsideVo> findList(SendRegisterParam param);

    /**
     * 新增外送登记上方数据
     * @param patientcode
     * @return
     */
    Map<String,Object> getPatientData(String patientcode);

    /**
     * 登记外送项目数据获取
     * @param page
     * @param key
     * @param patientcode
     * @return
     */
    IPage<WsxmDataVo> getPictureWsxmData(PageParam<WsxmDataVo> page, String key, String patientcode);
}

