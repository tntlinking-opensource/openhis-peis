package com.center.medical.pacslis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;

/**
 * @author xhp
 * @since 2023-02-28 13:39
 */
public interface MiddleDbInterfaceService extends IService<Peispatient> {
    /**
     * 读取插入中间库接口固定参数对象
     * @param patientcode 体检号
     * @return
     */
    MiddleDbDto getInsertMiddleDbData(String patientcode);

    /**
     * 修改已发状态
     * @param middleDbDto
     */
    void setInserted(MiddleDbDto middleDbDto);

    /**
     * 插入中间库
     * @param patientcode
     */
    void insert(String patientcode);
}
