package com.center.medical.olddata.service;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.VCheckReqmainMysql;

import java.util.List;

/**
 * (VCheckReqmainMysql)服务接口
 *
 * @author ay
 * @since 2024-08-09 15:46:19
 */
public interface VCheckReqmainMysqlService extends IService<VCheckReqmainMysql> {

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<VCheckReqmainMysql> getPage(PageParam<VCheckReqmainMysql> page, VCheckReqmainMysql param);

    /**
     * 获取视图数据
     * @param patientcode
     * @return
     */
    List<VCheckReqmainMysql> getCheckReqmain(String patientcode);

    /**
     * 删除体检号对应的数据
     * @param patientcode
     */
    void deleteByCode(String patientcode);

    /**
     * 批量保存
     * @param checkReqmainMysqlList
     */
    void saveList(List<VCheckReqmainMysql> checkReqmainMysqlList);

    /**
     * 查询过期数据
     * @param dateTime
     * @return
     */
    Integer deleteTimeOutList(DateTime dateTime);
}

