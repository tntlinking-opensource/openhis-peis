package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.pacslis.bean.dto.PacsDto;

import java.util.List;

/**
 * 金卫pacs(华欧)
 * @author xhp
 * @since 2024-05-27 7:46
 */
public interface PacsJinweiService  extends IService<PacsResult> {

    /**
     * 查询pacs结果 如果没有结果应返回new ArrayList<></>()
     * @param patientcode
     * @return
     */
    List<PacsDto> selectList(String patientcode);


}
