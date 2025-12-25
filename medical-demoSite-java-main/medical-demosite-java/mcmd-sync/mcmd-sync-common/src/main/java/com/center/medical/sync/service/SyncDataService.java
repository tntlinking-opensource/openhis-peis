package com.center.medical.sync.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sync.bean.model.SyncData;
import com.center.medical.sync.bean.param.GenerateCodeParam;

import java.util.List;

/**
 * 同步数据操作(SyncData)表服务接口
 *
 * @author 路飞船长
 * @since 2023-02-03 08:42:31
 */
public interface SyncDataService extends IService<SyncData> {

    /**
     * 分页查询[同步数据操作]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SyncData> getPage(PageParam<SyncData> page, SyncData param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SyncData getInfoById(Long id);

    /**
     * 定时任务生成体检号
     *
     * @param param
     * @return
     */
    List<String> generateCode(GenerateCodeParam param);

    /**
     * 定时任务生成档案号
     * @param param
     * @return
     */
    List<String> generateArchiveCode(GenerateCodeParam param);
}

