package com.center.medical.finance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.finance.bean.dto.PhysicalExaminationDto;
import com.center.medical.sellcrm.bean.model.Createorder;

import java.util.List;

/**
 * 财务报表-产值报表(Createorder)表服务接口
 *
 * @author ay
 * @since 2023-05-15 09:37:35
 */
public interface OutputValueReportService extends IService<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @return 分页数据
     */
    List<PhysicalExaminationDto> getList(BaseParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(String id);

    /**
     * 获取体检产值
     * @param param
     * @return
     */
    PhysicalExaminationDto getInspectOutputValue(BaseParam param);

    /**
     * 获取疫苗产值
     * @param param
     * @return
     */
    PhysicalExaminationDto getVaccinesOutputValue(BaseParam param);
}

