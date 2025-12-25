package com.center.medical.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Lung;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.GetLungVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * KS肺功能(Lung)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:05
 */
public interface LungService extends IService<Lung> {

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param Lung查询参数
     * @return 分页数据
     */
    IPage<Lung> getList(PageParam<Lung> page, Lung param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Lung getInfoById(String id);

    /**
     * 获取肺功能数据
     * @param patientcode
     * @return
     */
    List<GetLungVo> getLung(String patientcode);
}

