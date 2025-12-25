package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.abteilung.bean.param.AdditionProcessingParam;
import com.center.medical.abteilung.bean.vo.AdditionProcessingVo;
import com.center.medical.bean.model.HandleNewProjects;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * KS检验科加项处理(HandleNewProjects)表服务接口
 *
 * @author makejava
 * @since 2023-01-29 11:05:53
 */
public interface AdditionProcessingService extends IService<HandleNewProjects> {

    /**
     * 分页查询[KS检验科加项处理]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AdditionProcessingVo> getPage(PageParam<HandleNewProjects> page, AdditionProcessingParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    HandleNewProjects getInfoById(String id);

    /**
     * 批量处理
     *
     * @param ids
     * @param type
     * @return
     */
    Boolean CLSaveBatch(List<String> ids, String type);
}

