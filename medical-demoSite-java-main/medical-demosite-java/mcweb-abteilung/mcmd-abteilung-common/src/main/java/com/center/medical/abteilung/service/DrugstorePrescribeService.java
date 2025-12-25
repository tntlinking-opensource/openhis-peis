package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.DrugstorePrescribe;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.DrugstorePreParam;
import com.center.medical.abteilung.bean.param.PrescribeParam;
import com.center.medical.abteilung.bean.param.TakeDrugParam;
import com.center.medical.abteilung.bean.vo.DrugstorePreVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 开药记录(DrugstorePrescribe)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:18
 */
public interface DrugstorePrescribeService extends IService<DrugstorePrescribe> {

    /**
     * 分页查询[开药记录]列表
     *
     * @param page  分页参数
     * @param param DrugstorePrescribe查询参数
     * @return 分页数据
     */
    IPage<DrugstorePreVo> getList(PageParam<DrugstorePreVo> page, DrugstorePreParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    DrugstorePrescribe getInfoById(String id);

    /**
     * 分页查询药房管理售药统计
     * @param page
     * @param param
     * @return
     */
    IPage<DrugstorePreVo> getStatisticsListData(PageParam<DrugstorePreVo> page, DrugstorePreParam param);

    /**
     * 获取导出数据
     * @param param
     * @return
     */
    List<DrugstorePreVo> getExportData(DrugstorePreParam param);

    /**
     * 快捷开药-右侧-获得已开药记录
     * @param patientcode
     * @return
     */
    List<DrugstorePreVo> getAddedData(String patientcode);

    /**
     * 快捷开药-保存
     * @param param
     * @return
     */
    Boolean saOrUp(PrescribeParam param);

    /**
     * 药房取药
     * @param param
     * @return
     */
    Boolean takeDrug(TakeDrugParam param);

    /**
     * 获取上次体检开的什么药
     * @param patientcode
     * @return
     */
    List<DrugstorePreVo> getLastDrugs(String patientcode);


    /**
     * 退药
     * @param id
     * @return
     */
    Boolean drugRepercussion(String id);
}

