package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Savefilepath;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 存放文件路径表(Savefilepath)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:01
 */
public interface SavefilepathService extends IService<Savefilepath> {

    /**
     * 分页查询[存放文件路径表]列表
     *
     * @param page  分页参数
     * @param param Savefilepath查询参数
     * @return 分页数据
     */
    IPage<Savefilepath> getList(PageParam<Savefilepath> page, Savefilepath param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Savefilepath getInfoById(String id);

    /**
     * 保存文件地址
     *
     * @param saveFilePath
     * @return
     */
    @SuppressWarnings("rawtypes")
    String SaveFilePath(Savefilepath saveFilePath);

    /**
     * 根据公共ID获取文件列表
     *
     * @param id
     * @return
     */
    List<Savefilepath> getListByGgid(String id);
}

