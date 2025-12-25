package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.param.DmbShenHeParam;
import com.center.medical.bean.model.Attachment;
import com.center.medical.common.utils.page.PageParam;

import java.util.Map;

/**
 * KS科室检查结果主表(SectionResultMain)表服务接口
 *
 * @author ay
 * @since 2023-02-20 19:16:34
 */
public interface bmdService extends IService<SectionResultMain> {

    /**
    * 分页查询[KS科室检查结果主表]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<SectionResultMain> getList(PageParam<SectionResultMain> page, SectionResultMain param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    SectionResultMain getInfoById(String id);

    /**
     * 读卡
     * @param patientcode
     * @return
     */
    Map<String,Object> bmd(String patientcode, String gridSeclect, String autoFill, String ksId);

    /**
     * 获取骨密度审核数据
     * @param patientcode
     * @param ksId
     * @return
     */
    Object getBmdCheckedData(String patientcode, String ksId);

    /**
     * 获取地址
     * @param attachment
     * @return
     */
    String getPath(Attachment attachment);

    /**
     * 骨密度审核
     * @param param
     * @return
     */
    Boolean dmbshenhe(DmbShenHeParam param);
}

