package com.center.medical.statistics.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.AnalyseTestParam;
import com.center.medical.statistics.bean.vo.*;
import com.center.medical.statistics.dao.DivisionDoctorMapper;
import com.center.medical.statistics.service.DivisionDoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

/**
 * 医生工作量(Peispatientexamitem)表服务实现类
 *
 * @author ay
 * @since 2023-04-19 11:35:12
 */
@Slf4j
@Service("divisionDoctorService")
@RequiredArgsConstructor
public class DivisionDoctorServiceImpl extends ServiceImpl<DivisionDoctorMapper, Peispatientexamitem> implements DivisionDoctorService {

    private final DivisionDoctorMapper divisionDoctorMapper;

    /**
    * 分页查询[LIS结果(LisPacs数据)]列表
    *
    * @param page 分页参数
    * @param param Peispatientexamitem查询参数
    * @return 分页数据
    */
    @Override
    public IPage<AnalyseTestVo> getList(PageParam<AnalyseTestVo> page, AnalyseTestParam param) {
        IPage<AnalyseTestVo> iPage = divisionDoctorMapper.getList(page, param);
        List<AnalyseTestVo> list = iPage.getRecords();
        Calendar c = Calendar.getInstance();
        //设置属性
        for (AnalyseTestVo vo : list) {
            Integer gzlTotal = vo.getGzlTotal();
            Integer gzlGr = vo.getGzlGr();
            vo.setGzlTt(gzlTotal - gzlGr);
            if (ObjectUtils.isNotEmpty(vo.getRummagerTime())) {
                c.setTime(vo.getRummagerTime());
                int dayForWeek = 0;
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setDayForWeek(dayForWeek);
            }
        }
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 检验科科室医生工作量统计总计
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<AnalyseTestVo> analyseTestTotal(PageParam<AnalyseTestVo> page, AnalyseTestParam param) {
        return divisionDoctorMapper.analyseTestTotal(page,param);
    }


    /**
     * pacs科室医生工作量统计
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<AnalysePacsVo> analysePacs(PageParam<AnalysePacsVo> page, AnalyseTestParam param) {
        IPage<AnalysePacsVo> iPage = divisionDoctorMapper.analysePacs(page, param);
        List<AnalysePacsVo> list = iPage.getRecords();
        //设置属性
        Calendar c = Calendar.getInstance();
        for (AnalysePacsVo vo : list) {
            //设置星期几
            if (ObjectUtils.isNotEmpty(vo.getRummagerTime())) {
                c.setTime(vo.getRummagerTime());
                int dayForWeek = 0;
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setDayForWeek(dayForWeek);
            }
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * pacs科室医生工作量统计总计
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<AnalysePacsVo> analysePacsTotal(PageParam<AnalysePacsVo> page, AnalyseTestParam param) {
        IPage<AnalysePacsVo> iPage = divisionDoctorMapper.analysePacsTotal(page, param);
        List<AnalysePacsVo> list = iPage.getRecords();
        //设置属性
        for (AnalysePacsVo vo : list) {
            vo.setGzlTt(vo.getGzlTotal()-vo.getGzlGr());
            BigDecimal oriTt = vo.getOriTotal().subtract(vo.getOriGr());
            vo.setOriTt(oriTt);
        }
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 其他科室医生工作量统计
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<AnalysePacsVo> analyse(PageParam<AnalysePacsVo> page, AnalyseTestParam param) {
        IPage<AnalysePacsVo> iPage = divisionDoctorMapper.analyse(page, param);
        List<AnalysePacsVo> list = iPage.getRecords();
        //设置属性
        Calendar c = Calendar.getInstance();
        for (AnalysePacsVo vo : list) {
            vo.setGzlTt(vo.getGzlTotal()-vo.getGzlGr());
            BigDecimal oriTt = vo.getOriTotal().subtract(vo.getOriGr());
            vo.setOriTt(oriTt);
            BigDecimal yhTt = vo.getYhTotal().subtract(vo.getYhGr());
            vo.setYhTt(yhTt);
            //设置星期几
            if (ObjectUtils.isNotEmpty(vo.getRummagerTime())){
                c.setTime(vo.getRummagerTime());
                int dayForWeek = 0;
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setDayForWeek(dayForWeek);
            }
        }
        iPage.setRecords(list);
        return iPage;
    }

    /**
     * 其他科室医生工作量统计总计
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<AnalysePacsVo> analyseTotal(PageParam<AnalysePacsVo> page, AnalyseTestParam param) {
        IPage<AnalysePacsVo> iPage = divisionDoctorMapper.analyseTotal(page, param);
        List<AnalysePacsVo> list = iPage.getRecords();
        //设置属性
        for (AnalysePacsVo vo : list) {
            vo.setGzlTt(vo.getGzlTotal()-vo.getGzlGr());
            BigDecimal oriTt = vo.getOriTotal().subtract(vo.getOriGr());
            vo.setOriTt(oriTt);
            BigDecimal yhTt = vo.getYhTotal().subtract(vo.getYhGr());
            vo.setYhTt(yhTt);
        }
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 导出pacs科室工作量
     * @param param
     * @return
     */
    @Override
    public List<ExportPacsVo> exportPacs(AnalyseTestParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())){
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())){
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        List<ExportPacsVo> vos = divisionDoctorMapper.exportPacs(param);
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < vos.size(); i++) {
            //序号
            ExportPacsVo vo = vos.get(i);
            vo.setRownum(i+1);
            int dayForWeek = 0;
            c.setTime(vo.getExamdatetime());
            if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                dayForWeek = 7;
            } else {
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
            //星期
            vo.setDayForWeek(dayForWeek);
            //工作量团体
            vo.setGzlTT(vo.getGzlTotal()-vo.getGzlGr());
            //原始金额团体
            vo.setOriTt(vo.getOriTotal().subtract(vo.getOriGr()));
            //优惠金额团体
            vo.setYhTt(vo.getYhTotal().subtract(vo.getYhGr()));
        }
        return vos;
    }


    /**
     * 导出pacs科室工作量明细
     * @param param
     * @return
     */
    @Override
    public List<ExportPacsInfoVo> exportPacsInfoVo(AnalyseTestParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())){
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())){
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        List<ExportPacsInfoVo> vos = divisionDoctorMapper.exportPacsInfoVo(param);
        for (int i = 0; i < vos.size(); i++) {
            //序号
            ExportPacsInfoVo vo = vos.get(i);
            vo.setRownum(i+1);
            //工作量团体
            vo.setGzlTT(vo.getGzlTotal()-vo.getGzlGr());
            //原始金额团体
            vo.setOriTt(vo.getOriTotal().subtract(vo.getOriGr()));
            //优惠金额团体
            vo.setYhTt(vo.getYhTotal().subtract(vo.getYhGr()));
        }
        return vos;
    }


    /**
     * 导出其他科室工作量
     * @param param
     * @return
     */
    @Override
    public List<ExportOtherVo> exportOther(AnalyseTestParam param) {
        List<ExportOtherVo> vos = divisionDoctorMapper.exportOther(param);
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < vos.size(); i++) {
            //序号
            ExportOtherVo vo = vos.get(i);
            vo.setRownum(i+1);
            int dayForWeek = 0;
            if (ObjectUtils.isNotEmpty(vo.getRummagerTime())){
                c.setTime(vo.getRummagerTime());
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
            }
            //星期
            vo.setDayForWeek(dayForWeek);
            //工作量团体
            vo.setGzlTT(vo.getGzlTotal()-vo.getGzlGr());
            //原始金额团体
            vo.setOriTt(vo.getOriTotal().subtract(vo.getOriGr()));
            //优惠金额团体
            vo.setYhTt(vo.getYhTotal().subtract(vo.getYhGr()));
        }
        return vos;
    }


    /**
     * 导出其他科室工作量明细
     * @param param
     * @return
     */
    @Override
    public List<ExportOtherInfoVo> exportOtherInfo(AnalyseTestParam param) {
        List<ExportOtherInfoVo> vos = divisionDoctorMapper.exportOtherInfo(param);
        for (int i = 0; i < vos.size(); i++) {
            //序号
            ExportOtherInfoVo vo = vos.get(i);
            vo.setRownum(i+1);
            //工作量团体
            vo.setGzlTT(vo.getGzlTotal()-vo.getGzlGr());
            //原始金额团体
            vo.setOriTt(vo.getOriTotal().subtract(vo.getOriGr()));
            //优惠金额团体
            vo.setYhTt(vo.getYhTotal().subtract(vo.getYhGr()));
        }
        return vos;
    }
}

