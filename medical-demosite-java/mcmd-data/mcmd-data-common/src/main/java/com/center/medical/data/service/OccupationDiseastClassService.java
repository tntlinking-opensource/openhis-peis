package com.center.medical.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.OccupationDiseastClass;

/**
 * JC职业病种分类(OccupationDiseastClass)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:16
 */
public interface OccupationDiseastClassService extends IService<OccupationDiseastClass> {

    /**
     * 分页查询[JC职业病种分类]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseastClass查询参数
     * @return 分页数据
     */
    IPage<OccupationDiseastClass> getList(PageParam<OccupationDiseastClass> page, OccupationDiseastClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OccupationDiseastClass getInfoById(String id);

    /**
     * 保存或更新
     * @param occupationDiseastClass
     * @return
     */
    String saveOrUpdateOccu(OccupationDiseastClass occupationDiseastClass);

    /**
     * 逻辑删除
     * @param ids
     * @return
     */
    String removeOccupa(String ids);


}

