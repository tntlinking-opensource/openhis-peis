package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;
import com.center.medical.pacslis.bean.dto.PacsDto;

import java.util.List;

/**
 * 沃德pacs
 * @author xhp
 * @since 2023-03-07 8:56
 */
public interface PacsService extends IService<PacsResult> {
    /**
     * 插入沃德pacs
     * @param middleDbDto
     */
    void save(MiddleDbDto middleDbDto);

    /**
     * 查询pacs结果
     * @param patientcode
     * @return
     */
    List<PacsDto> selectList(String patientcode);
}
