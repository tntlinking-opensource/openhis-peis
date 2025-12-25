package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.model.OutsideCheckin;
import com.center.medical.reception.bean.vo.PictureDataVo;

import java.util.List;

/**
 * KS外送项目图片关联表(OutsideCheckin)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:35
 */
public interface OutsideCheckinService extends IService<OutsideCheckin> {

    /**
     * 分页查询[KS外送项目图片关联表]列表
     *
     * @param page  分页参数
     * @param param OutsideCheckin查询参数
     * @return 分页数据
     */
    IPage<OutsideCheckin> getList(PageParam<OutsideCheckin> page, OutsideCheckin param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OutsideCheckin getInfoById(String id);

    /**
     * 获取与图片结果关联项目
     *
     * @param patientcode
     * @return
     */
    List<PictureDataVo> getPictureData(String patientcode);
}

