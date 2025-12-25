package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.dto.GetC13dataDto;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;

import java.util.HashMap;
import java.util.List;

/**
 * KS科室检查结果主表(SectionResultMain)表服务接口
 *
 * @author ay
 * @since 2023-02-20 19:16:34
 */
public interface VisionService extends IService<SectionResultMain> {

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
     * 视力检查
     * @param map
     * @param tjzsfxm
     * @return
     */
    List<GetC13dataDto> getVisiondata(HashMap<String, String> map, List<Peispatientfeeitem> tjzsfxm);
}

