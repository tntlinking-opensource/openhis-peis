package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Whysqzfw;

/**
 * JC危害因素取值范围(Whysqzfw)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:52
 */
public interface WhysqzfwService extends IService<Whysqzfw> {

    /**
     * 分页查询[JC危害因素取值范围]列表
     *
     * @param page  分页参数
     * @param param Whysqzfw查询参数
     * @return 分页数据
     */
    IPage<Whysqzfw> getList(PageParam<Whysqzfw> page, Whysqzfw param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Whysqzfw getInfoById(String id);

    /**
     * 保存或修改
     * @param whysqzfw
     * @return
     */
    String saveOrUpdateWhysqzfw(Whysqzfw whysqzfw);

    /**
     *
     * @param ids
     * @return
     */
    String removeWhysqzfw(String ids);
}

