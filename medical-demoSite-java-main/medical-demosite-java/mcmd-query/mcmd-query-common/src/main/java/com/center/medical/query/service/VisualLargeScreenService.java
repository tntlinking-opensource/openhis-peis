package com.center.medical.query.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.query.bean.vo.GetHomePageDataVo;

/**
 * 可视化大屏(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-05-20 11:00:32
 */
public interface VisualLargeScreenService extends IService<Peispatient> {

    /**
     * 查询首页数据
     * @param param
     * @return
     */
    GetHomePageDataVo getHomePageData(BaseParam param);
}

