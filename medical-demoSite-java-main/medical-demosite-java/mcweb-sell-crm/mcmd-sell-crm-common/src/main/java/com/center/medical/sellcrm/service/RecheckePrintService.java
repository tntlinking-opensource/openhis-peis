package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Review;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.param.RecheckePrintParam;
import com.center.medical.sellcrm.bean.vo.RecheckePrintVo;

/**
 * ZJ复查表(Review)表服务接口
 *
 * @author ay
 * @since 2023-02-08 11:58:35
 */
public interface RecheckePrintService extends IService<Review> {

    /**
    * 分页查询[ZJ复查表]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<RecheckePrintVo> getList(PageParam<RecheckePrintVo> page, RecheckePrintParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Review getInfoById(String id);
}

