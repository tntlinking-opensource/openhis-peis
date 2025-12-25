package com.center.medical.pacs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.pacs.bean.param.CreateRadParam;

/**
 * PACS-数据(PacsResult)表服务接口
 *
 * @author ay
 * @since 2023-05-19 15:22:03
 */
public interface PacsReportService extends IService<PacsResult> {


    /**
     * 生成放射科室报告
     * @param param
     * @return
     */
    Boolean createRad(CreateRadParam param);
}

