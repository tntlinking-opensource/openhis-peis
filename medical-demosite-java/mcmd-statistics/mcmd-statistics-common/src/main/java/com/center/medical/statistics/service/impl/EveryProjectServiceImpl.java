package com.center.medical.statistics.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.dao.PeispatientexamitemMapper;
import com.center.medical.statistics.bean.param.AnalyseInfoParam;
import com.center.medical.statistics.bean.param.EveryProjectParam;
import com.center.medical.statistics.bean.vo.AnalyseInfoVo;
import com.center.medical.statistics.bean.vo.EveryProjectVo;
import com.center.medical.statistics.dao.EveryProjectMapper;
import com.center.medical.statistics.service.EveryProjectService;
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
 * 每日体检项目统计(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
@Slf4j
@Service("everyProjectService")
@RequiredArgsConstructor
public class EveryProjectServiceImpl extends ServiceImpl<EveryProjectMapper, Peispatient> implements EveryProjectService {

    private final EveryProjectMapper everyProjectMapper;
    private final PeispatientexamitemMapper peispatientexamitemMapper;
    private final SectionResultMainMapper sectionResultMainMapper;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<EveryProjectVo> getList(PageParam<EveryProjectVo> page, EveryProjectParam param) {
        //结束时间为空 给一个默认的时间
        if (ObjectUtils.isEmpty(param.getEndTime())){
            param.setEndTime(DateUtil.endOfDay(new Date()));
        }
        IPage<EveryProjectVo> iPage = everyProjectMapper.getList(page, param);

        //设置属性
        List<EveryProjectVo> list = iPage.getRecords();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (EveryProjectVo vo : list) {
            String every0 = vo.getEvery0();
            if (null != every0) {
                //星期几
                int dayForWeek=0;
                Date d = null;
                try {
                    d = format.parse(every0.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.setTime(d);
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setEvery7(dayForWeek);

            }
        }
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 右侧体检者数据
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<AnalyseInfoVo> analyseInfo(PageParam<AnalyseInfoVo> page, AnalyseInfoParam param) {
        IPage<AnalyseInfoVo> iPage = everyProjectMapper.analyseInfo(page, param);
        List<AnalyseInfoVo> list = iPage.getRecords();
        //设置属性
        for (AnalyseInfoVo vo : list) {
            String idKs = vo.getIdKs();
            String iId = vo.getIdExamfeeitem();
            String patientcode = vo.getPatientcode();
            if("19".equals(idKs)){
                //检验科
                List<Peispatientexamitem> peisPatientExamItem = peispatientexamitemMapper.selectList(new LambdaQueryWrapper<Peispatientexamitem>()
                        .eq(Peispatientexamitem::getPatientcode,patientcode).eq(Peispatientexamitem::getIdExamfeeitem, iId));
                //审核时间
                if(CollectionUtil.isNotEmpty(peisPatientExamItem) && peisPatientExamItem.get(0).getAuditDate()!=null){
                    vo.setAuditTime(peisPatientExamItem.get(0).getAuditDate());
                }
            }else{
                //科室检查结果主表
                SectionResultMain srm = sectionResultMainMapper.selectOne(new LambdaQueryWrapper<SectionResultMain>()
                                .eq(SectionResultMain::getPatientcode, patientcode).eq(SectionResultMain::getDepId,idKs));
                //审核时间
                if(srm!=null && srm.getAuditTime()!=null){
                    vo.setAuditTime(srm.getAuditTime());
                }
            }

        }
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 导出每日项目体检量
     * @param param
     * @return
     */
    @Override
    public List<EveryProjectVo> exportData(EveryProjectParam param) {
        //结束时间为空 给一个默认的时间
        if (ObjectUtils.isEmpty(param.getEndTime())){
            param.setEndTime(DateUtil.endOfDay(new Date()));
        }
        List<EveryProjectVo> list = everyProjectMapper.exportData(param);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //设置属性
        for (int i = 0; i < list.size(); i++) {
            EveryProjectVo vo = list.get(i);
            //序号
            vo.setRownum(i+1);
            String every0 = vo.getEvery0();
            if (null != every0) {
                //星期几
                int dayForWeek=0;
                Date d = null;
                try {
                    d = format.parse(every0.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.setTime(d);
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setEvery7(dayForWeek);

            }
        }
        return list;
    }


    /**
     * 导出体检人员清单
     * @param param
     * @return
     */
    @Override
    public List<AnalyseInfoVo> exportInfo(AnalyseInfoParam param) {
        List<AnalyseInfoVo> list = everyProjectMapper.exportInfo(param);
        //设置属性
        for (int i = 0; i < list.size(); i++) {
            AnalyseInfoVo vo = list.get(i);
            String idKs = vo.getIdKs();
            String iId = vo.getIdExamfeeitem();
            String patientcode = vo.getPatientcode();
            //序号
            vo.setRownum(i+1);

            if("19".equals(idKs)){
                //检验科
                Peispatientexamitem peisPatientExamItem = peispatientexamitemMapper.selectOne(new LambdaQueryWrapper<Peispatientexamitem>()
                        .eq(Peispatientexamitem::getPatientcode,patientcode).eq(Peispatientexamitem::getIdExamfeeitem, iId));
                //审核时间
                if(peisPatientExamItem!=null && peisPatientExamItem.getAuditDate()!=null){
                    vo.setAuditTime(peisPatientExamItem.getAuditDate());
                }
            }else{
                //科室检查结果主表
                SectionResultMain srm = sectionResultMainMapper.selectOne(new LambdaQueryWrapper<SectionResultMain>()
                        .eq(SectionResultMain::getPatientcode, patientcode).eq(SectionResultMain::getDepId,idKs));
                //审核时间
                if(srm!=null && srm.getAuditTime()!=null){
                    vo.setAuditTime(srm.getAuditTime());
                }
            }
        }
        return list;
    }
}

