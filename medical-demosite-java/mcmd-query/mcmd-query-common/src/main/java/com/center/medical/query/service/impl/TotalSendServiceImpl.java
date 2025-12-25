package com.center.medical.query.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.TotalSendParam;
import com.center.medical.query.bean.vo.TotalSendVo;
import com.center.medical.query.dao.TotalSendMapper;
import com.center.medical.query.service.TotalSendService;
import com.center.medical.reception.bean.model.OutsideMain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * KS外送登记结果主表(OutsideMain)表服务实现类
 *
 * @author ay
 * @since 2023-04-12 11:59:12
 */
@Slf4j
@Service("totalSendService")
@RequiredArgsConstructor
public class TotalSendServiceImpl extends ServiceImpl<TotalSendMapper, OutsideMain> implements TotalSendService {

    private final TotalSendMapper totalSendMapper;

    /**
     * 分页查询[KS外送登记结果主表]列表
     *
     * @param page  分页参数
     * @param param OutsideMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TotalSendVo> getList(PageParam<TotalSendVo> page, TotalSendParam param) {
        //结束时间为空就设置今天
        if (ObjectUtils.isEmpty(param.getEndTime())){
            param.setEndTime(DateUtil.endOfDay(new Date()));
        }
        return totalSendMapper.getList(page, param);
    }



    /**
     * 获取合计金额
     * @param param
     * @return
     */
    @Override
    public String amountTo(TotalSendParam param) {
        //结束时间为空就设置今天
        if (ObjectUtils.isEmpty(param.getEndTime())){
            param.setEndTime(DateUtil.endOfDay(new Date()));
        }
        return totalSendMapper.amountTo(param);
    }

    /**
     * 导出外送统计
     * @param param
     * @return
     */
    @Override
    public List<TotalSendVo> getExportData(TotalSendParam param) {
        //结束时间为空就设置今天
        if (ObjectUtils.isEmpty(param.getEndTime())){
            param.setEndTime(DateUtil.endOfDay(new Date()));
        }
        return totalSendMapper.getExportData(param);
    }
}

