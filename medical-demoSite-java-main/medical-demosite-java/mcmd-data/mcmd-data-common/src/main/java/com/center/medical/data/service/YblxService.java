package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Yblx;
import com.center.medical.data.bean.param.YblxParam;

import java.util.List;

/**
 * 样本类型(Yblx)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-29 11:22:58
 */
public interface YblxService extends IService<Yblx> {

    /**
     * 分页查询[样本类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Yblx> getPage(PageParam<Yblx> page, YblxParam param);

    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    List<Yblx> getList(YblxParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Yblx getInfoById(String id);

}

