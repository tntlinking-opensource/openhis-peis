package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Satisfaction;
import com.center.medical.statistics.bean.param.SatisfactionRankParam;
import com.center.medical.statistics.bean.vo.SatisfactionRankVo;

/**
 * 科室满意度排名(Satisfaction)表服务接口
 *
 * @author ay
 * @since 2023-04-20 10:01:49
 */
public interface SatisfactionRankService extends IService<Satisfaction> {

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SatisfactionRankVo> getList(PageParam<SatisfactionRankVo> page, SatisfactionRankParam param);


}

