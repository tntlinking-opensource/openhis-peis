package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.statistics.bean.vo.GroupDistributeVo;
import com.center.medical.statistics.dao.GroupDistributeMapper;
import com.center.medical.statistics.service.GroupDistributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 体检团体分布情况(Createorder)表服务实现类
 *
 * @author ay
 * @since 2023-04-18 19:31:32
 */
@Slf4j
@Service("groupDistributeService")
@RequiredArgsConstructor
public class GroupDistributeServiceImpl extends ServiceImpl<GroupDistributeMapper, Createorder> implements GroupDistributeService {

    private final GroupDistributeMapper groupDistributeMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    @Override
    public IPage<GroupDistributeVo> getList(PageParam<GroupDistributeVo> page, BaseParam param) {
        return groupDistributeMapper.getList(page, param);
    }



}

