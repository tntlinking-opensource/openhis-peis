package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.OccupationSymptom;
import com.center.medical.data.bean.param.ShowDataParam;

import java.util.List;

/**
 * JC职业症状(OccupationSymptom)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:30
 */
public interface OccupationSymptomService extends IService<OccupationSymptom> {

    /**
     * 分页查询[JC职业症状]列表
     *
     * @param page  分页参数
     * @param param OccupationSymptom查询参数
     * @return 分页数据
     */
    IPage<OccupationSymptom> getList(PageParam<OccupationSymptom> page, OccupationSymptom param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OccupationSymptom getInfoById(String id);

    /**
     * 保存或更新
     *
     * @param occupationSymptom
     * @return
     */
    String saveOrUpdateOcc(OccupationSymptom occupationSymptom);

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    String removeOccu(String ids);


    /**
     * 弹窗数据,职业性问诊用到
     *
     * @param param
     * @return
     */
    List<OccupationSymptom> getShowData(ShowDataParam param);
}

