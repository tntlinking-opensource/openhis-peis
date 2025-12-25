package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.model.Nuclein;
import com.center.medical.abteilung.bean.param.NucleinParam;
import com.center.medical.common.utils.page.PageParam;

/**
 * 核酸检测报告上传记录(Nuclein)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-04 20:38:01
 */
public interface NucleinService extends IService<Nuclein> {

    /**
     * 分页查询[核酸检测报告上传记录]列表
     *
     * @param page  分页参数
     * @param param Nuclein查询参数
     * @return 分页数据
     */
    IPage<Nuclein> getList(PageParam<Nuclein> page, NucleinParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Nuclein getInfoById(String id);

}

