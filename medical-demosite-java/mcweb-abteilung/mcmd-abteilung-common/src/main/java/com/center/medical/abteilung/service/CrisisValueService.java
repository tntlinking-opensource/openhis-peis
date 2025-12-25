package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.param.CrisisValueParam;
import com.center.medical.abteilung.bean.param.CrisisValueSaveParam;
import com.center.medical.abteilung.bean.param.CrisisValuesaOrUpParam;
import com.center.medical.abteilung.bean.vo.CrisisValueVo;
import com.center.medical.abteilung.bean.vo.GetKsVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.vo.AllOrgVo;

import java.util.List;

/**
 * 高危人员管理表(Riskclient)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 19:29:10
 */
public interface CrisisValueService extends IService<Riskclient> {

    /**
     * 分页查询[高危人员管理表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CrisisValueVo> getPage(PageParam<CrisisValueVo> page, CrisisValueParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Riskclient getInfoById(String id);


    /**
     * 导出
     *
     * @param param
     * @return
     */
    List<CrisisValueVo> getExportData(CrisisValueParam param);

    /**
     * 保存或修改
     *
     * @param param
     * @return
     */
    Boolean saOrUp(CrisisValuesaOrUpParam param);

    /**
     * 删除高危人员管理表及中间表
     *
     * @param ids
     * @return
     */
    Boolean removeCrisisValue(List<String> ids);

    /**
     * 获取公司下拉
     *
     * @param page
     * @param key
     * @return
     */
    IPage<AllOrgVo> getListData(PageParam<AllOrgVo> page, String key);

    /**
     * 业务处理
     *
     * @param param
     * @return
     */
    Boolean saveYw(CrisisValueSaveParam param);

    /**
     * 回访处理
     *
     * @param param
     * @return
     */
    Boolean saveHf(CrisisValueSaveParam param);

    /**
     * 专家处理
     *
     * @param param
     * @return
     */
    Boolean saveZj(CrisisValueSaveParam param);

    /**
     * 获取科室及体检结果
     * @param patientcode
     * @param key
     * @return
     */
    List<GetKsVo> getKs(String patientcode, String key);
}

