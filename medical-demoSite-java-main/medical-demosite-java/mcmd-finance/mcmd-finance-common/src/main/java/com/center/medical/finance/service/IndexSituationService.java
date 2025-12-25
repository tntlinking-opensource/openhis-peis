package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ISDataParam;
import com.center.medical.finance.bean.vo.IndexSituationVo;
import com.center.medical.sellcrm.bean.model.Createorder;

import java.util.List;

/**
 * 订单表(Createorder)表服务接口
 *
 * @author ay
 * @since 2023-05-15 09:37:35
 */
public interface IndexSituationService extends IService<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Createorder> getList(PageParam<Createorder> page, Createorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createorder getInfoById(String id);

    /**
     * 获取统计数据
     * @return
     */
    List<IndexSituationVo> getData(ISDataParam param);
}

