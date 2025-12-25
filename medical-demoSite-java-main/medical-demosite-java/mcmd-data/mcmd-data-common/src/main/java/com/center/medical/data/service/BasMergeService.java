package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BasMerge;
import com.center.medical.data.bean.vo.BasMergeVo;

/**
 * 合并结伦词(BasMerge)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:15
 */
public interface BasMergeService extends IService<BasMerge> {

    /**
     * 分页查询[合并结伦词]列表
     *
     * @param page  分页参数
     * @param param BasMerge查询参数
     * @return 分页数据
     */
    IPage<BasMerge> getList(PageParam<BasMerge> page, BasMerge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    BasMerge getInfoById(String id);

    /**
     * 新增或更新
     * @param basMergeVo
     * @return
     */
    Boolean saOrUp(BasMergeVo basMergeVo);

    /**
     * 通过Con和mid获取合并结伦词
     * @param conIds
     * @param mId
     * @return
     */
    BasMerge getMergeByCon(String[] conIds, String mId);
}

