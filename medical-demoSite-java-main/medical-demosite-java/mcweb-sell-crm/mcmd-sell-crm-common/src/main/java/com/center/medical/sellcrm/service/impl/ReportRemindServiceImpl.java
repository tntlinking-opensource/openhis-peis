package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.bean.model.Report;
import com.center.medical.sellcrm.bean.param.ReportRemindParam;
import com.center.medical.sellcrm.bean.vo.ReportRemindVo;
import com.center.medical.sellcrm.dao.ReportRemindMapper;
import com.center.medical.sellcrm.service.ReportRemindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * BG报告主表(Report)表服务实现类
 *
 * @author ay
 * @since 2023-02-08 15:34:05
 */
@Slf4j
@Service("reportRemindService")
@RequiredArgsConstructor
public class ReportRemindServiceImpl extends ServiceImpl<ReportRemindMapper, Report> implements ReportRemindService {

    private final ReportRemindMapper reportMapper;

    /**
    * 分页查询[BG报告主表]列表
    *
    * @param page 分页参数
    * @param param Report查询参数
    * @return 分页数据
    */
    @Override
    public IPage<ReportRemindVo> getList(PageParam<ReportRemindVo> page, ReportRemindParam param) {
        /**决策领导是所有
         领导是当前分中心
         团体的销售经理是当前用户*/
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            //是否领导
            if(SecurityUtils.isLeader()){
                param.setIsLeader(1);
                param.setCid(SecurityUtils.getCId());
            }else{
                param.setIsLeader(0);
                param.setUserNo(SecurityUtils.getUserNo());
            }
        }
        //去空格大写
        if (ObjectUtils.isNotEmpty(param.getPatientcode())){
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())){
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())){
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return reportMapper.getList(page, param);
    }

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    @Override
    public Report getInfoById(String id){
        return reportMapper.getInfoById(id);
    };

}

