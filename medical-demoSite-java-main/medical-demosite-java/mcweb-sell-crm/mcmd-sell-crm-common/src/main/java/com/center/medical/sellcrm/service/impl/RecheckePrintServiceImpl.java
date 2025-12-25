package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Review;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.sellcrm.bean.param.RecheckePrintParam;
import com.center.medical.sellcrm.bean.vo.RecheckePrintVo;
import com.center.medical.sellcrm.dao.RecheckePrintMapper;
import com.center.medical.sellcrm.service.RecheckePrintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * ZJ复查表(Review)表服务实现类
 *
 * @author ay
 * @since 2023-02-08 11:58:35
 */
@Slf4j
@Service("recheckePrintService")
@RequiredArgsConstructor
public class RecheckePrintServiceImpl extends ServiceImpl<RecheckePrintMapper, Review> implements RecheckePrintService {

    private final RecheckePrintMapper recheckePrintMapper;
    private final PeispatientMapper peispatientMapper;

    /**
    * 分页查询[ZJ复查表]列表
    *
    * @param page 分页参数
    * @param param Review查询参数
    * @return 分页数据
    */
    @Override
    public IPage<RecheckePrintVo> getList(PageParam<RecheckePrintVo> page, RecheckePrintParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())){
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())){
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return recheckePrintMapper.getList(page, param);
    }

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    @Override
    public Review getInfoById(String id){
        return recheckePrintMapper.getInfoById(id);
    };

}

