package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.pacslis.bean.dto.PacsDto;

import java.util.List;

/**
 * 易影
 * @author xhp
 * @since 2025-02-25 15:33
 */
public interface PacsYiyingService extends IService<PacsResult> {

    /**
     * 查询pacs结果 如果没有结果应返回new ArrayList<></>()
     * @param patientcode
     * @return
     */
    List<PacsDto> selectList(String patientcode);

}
