package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.bean.model.Review;
import com.center.medical.reception.bean.param.ReviewParam;
import com.center.medical.reception.bean.vo.ReviewPrintVo;

import java.util.List;

/**
 * ZJ复查表(Review)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 20:52:26
 */
public interface ReviewService extends IService<Review> {

    /**
     * 分页查询[ZJ复查表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Review> getPage(PageParam<Review> page, ReviewParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Review getInfoById(String id);

    /**
     * 打印
     *
     * @param ids 打印对象主键{id}集合，多个以英文逗号（,）隔开
     * @return
     */
    List<ReviewPrintVo> printData(List<String> ids);

}

