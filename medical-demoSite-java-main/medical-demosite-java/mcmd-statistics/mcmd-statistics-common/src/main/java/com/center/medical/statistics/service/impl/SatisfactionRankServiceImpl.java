package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.Satisfaction;
import com.center.medical.statistics.bean.param.SatisfactionRankParam;
import com.center.medical.statistics.bean.vo.SatisfactionRankVo;
import com.center.medical.statistics.dao.SatisfactionRankMapper;
import com.center.medical.statistics.service.SatisfactionRankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 科室满意度排名(Satisfaction)表服务实现类
 *
 * @author ay
 * @since 2023-04-20 10:01:50
 */
@Slf4j
@Service("satisfactionRankService")
@RequiredArgsConstructor
public class SatisfactionRankServiceImpl extends ServiceImpl<SatisfactionRankMapper, Satisfaction> implements SatisfactionRankService {

    private final SatisfactionRankMapper satisfactionRankMapper;

    /**
     * 分页查询[KF满意度]列表
     *
     * @param page  分页参数
     * @param param Satisfaction查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SatisfactionRankVo> getList(PageParam<SatisfactionRankVo> page, SatisfactionRankParam param) {
        return satisfactionRankMapper.getList(page, param);
    }


}

