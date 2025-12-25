package com.center.medical.statistics.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ExaminerStatusParam;
import com.center.medical.statistics.bean.vo.ExaminerStatusVo;
import com.center.medical.statistics.dao.ExaminerStatusMapper;
import com.center.medical.statistics.service.ExaminerStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 体检状态统计(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
@Slf4j
@Service("ExaminerStatusService")
@RequiredArgsConstructor
public class ExaminerStatusServiceImpl extends ServiceImpl<ExaminerStatusMapper, Peispatient> implements ExaminerStatusService {

    private final ExaminerStatusMapper examinerStatusMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ExaminerStatusVo> getList(PageParam<ExaminerStatusVo> page, ExaminerStatusParam param) {
        //没有时间时,设置默认时间
        if (ObjectUtils.isEmpty(param.getStartTime())){
            //当前时间减一周（七天）
            DateTime dateTime = DateUtil.offsetWeek(new Date(), -1);
            param.setStartTime(dateTime);
        }
        if (ObjectUtils.isEmpty(param.getEndTime())){
            param.setEndTime(new Date());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //设置属性
        IPage<ExaminerStatusVo> iPage = examinerStatusMapper.getList(page, param);
        List<ExaminerStatusVo> list = iPage.getRecords();
        for (ExaminerStatusVo vo : list) {
            int dayForWeek = 0;
            if (ObjectUtils.isNotEmpty(vo.getDateregister())) {
                Date d = null;
                try {
                    d = format.parse(vo.getDateregister());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long timeMillion = new Date().getTime() - d.getTime();
                c.setTime(d);
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setDate0(d);
                vo.setDate1(dayForWeek);
                vo.setDate2((int) (timeMillion / (24l * 60 * 60 * 1000)));
            }
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 导出体检者状态统计
     * @param param
     * @return
     */
    @Override
    public List<ExaminerStatusVo> exportData(ExaminerStatusParam param) {
        //没有时间时,设置默认时间
        if (ObjectUtils.isEmpty(param.getStartTime())){
            //当前时间减一周（七天）
            DateTime dateTime = DateUtil.offsetWeek(new Date(), -1);
            param.setStartTime(dateTime);
        }
        if (ObjectUtils.isEmpty(param.getEndTime())){
            param.setEndTime(new Date());
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        List<ExaminerStatusVo> list = examinerStatusMapper.exportData(param);
        //设置属性
        for (ExaminerStatusVo vo : list) {
            int dayForWeek = 0;
            if (ObjectUtils.isNotEmpty(vo.getDateregister())) {
                Date d = null;
                try {
                    d = format.parse(vo.getDateregister());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long timeMillion = new Date().getTime() - d.getTime();
                c.setTime(d);
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setDate0(d);
                vo.setDate1(dayForWeek);
                vo.setDate2((int) (timeMillion / (24l * 60 * 60 * 1000)));
            }
        }
        return list;
    }
}

