package com.center.medical.machine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.machine.bean.param.ReadCardParam;

import java.util.Map;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
public interface ReadCardService extends IService<Peispatient> {


    /**
     * 查询对应的卡号及体检者信息
     *
     * @param message
     * @return
     */
    Map<String, Object> onMessage(ReadCardParam message);
}

