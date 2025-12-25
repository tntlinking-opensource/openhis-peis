package com.center.medical.reception.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.reception.bean.param.ImportDataParam;

import java.sql.SQLException;

/**
 * QT体检者表(Peispatient)服务接口
 *
 * @author ay
 * @since 2023-07-21 14:17:31
 */
public interface OrderImportDataService extends IService<Peispatient> {


    /**
     * 根据订单号导入老数据到新系统中
     * @param param
     * @return
     */
    Boolean importData(ImportDataParam param) throws SQLException;

    /**
     * 数据迁移
     * @return
     */
    Boolean dataMove() throws SQLException;
}

