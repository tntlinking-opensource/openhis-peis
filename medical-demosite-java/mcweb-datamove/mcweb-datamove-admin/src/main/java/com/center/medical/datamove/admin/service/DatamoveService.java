package com.center.medical.datamove.admin.service;

import java.sql.SQLException;

/**
 * @author: 路飞
 * @date: 2023-06-17 11:45
 * @description:
 */
public interface DatamoveService {

    /**
     * 开始迁移
     *
     * @return
     */
    Boolean move() throws SQLException;


    
//    /**
//     * 根据订单号导入老数据到新系统中
//     *
//     * @param ddh
//     * @return
//     */
//    Boolean importData(List<String> ddh);
//
//    /**
//     * 导入未完成的体检者
//     *
//     * @return
//     */
//    Boolean importPeispatient();
}
