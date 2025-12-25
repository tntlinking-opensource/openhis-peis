package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.ElectroAudiometer;
import com.center.medical.abteilung.bean.param.EAsaOrUpParam;
import com.center.medical.common.utils.page.PageParam;

import java.util.HashMap;
import java.util.List;

/**
 * KS电测听(ElectroAudiometer)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-09 11:43:19
 */
public interface ElectroAudiometerService extends IService<ElectroAudiometer> {

    /**
     * 分页查询[KS电测听]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ElectroAudiometer> getPage(PageParam<ElectroAudiometer> page, ElectroAudiometer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ElectroAudiometer getInfoById(String id);

    /**
     * 读卡
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    HashMap<String, Object> search(String patientCode, String ksId);

    /**
     * 审核
     *
     * @param param
     * @return
     */
    Boolean saOrUp(EAsaOrUpParam param);

    /**
     * 反审核
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    Boolean reverse(String patientCode, String ksId);


    /**
     * 上传图片
     *
     * @param param
     * @return
     */
    Boolean UploadImg(ElectroAudiometer param);

    /**
     * 保存并审核数据
     * @param electroAudiometers
     * @return
     */
    String uploadEle(List<ElectroAudiometer> electroAudiometers);
}

