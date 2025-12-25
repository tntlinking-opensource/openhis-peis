package com.center.medical.pacslis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PacsResult;
import com.center.medical.common.core.domain.R;
import com.center.medical.pacslis.bean.dto.PacsDto;

import java.util.List;

/**
 * @author xhp
 * @since 2023-03-07 10:40
 */
public interface PacsInterfaceService extends IService<PacsResult> {

    /**
     * 获取pacs结果
     * @param patientcode
     * @return
     */
    R<String> receive(String patientcode);
    
    /**
     * 保存pacs结果
     * @param patientcode
     * @param pacsDtoList
     */
    void save(String patientcode, List<PacsDto> pacsDtoList);

    /**
     * 查询所有需要获取pacs结果的体检号
     * @return
     */
    List<String> receivePacsDataUser();
}
