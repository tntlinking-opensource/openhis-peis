package com.center.medical.abteilung.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.model.SampleDelivery;
import com.center.medical.abteilung.bean.param.SDSaOrUParam;
import com.center.medical.abteilung.bean.param.SampleDeliveryParam;
import com.center.medical.abteilung.bean.vo.SampleDeVo;
import com.center.medical.abteilung.dao.SampleDeliveryMapper;
import com.center.medical.abteilung.service.SampleDeliveryService;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientfeeitemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * KS样本录入(SampleDelivery)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:06
 */
@Slf4j
@Service("sampleDeliveryService")
@RequiredArgsConstructor
public class SampleDeliveryServiceImpl extends ServiceImpl<SampleDeliveryMapper, SampleDelivery> implements SampleDeliveryService {

    private final SampleDeliveryMapper sampleDeliveryMapper;
    private final MapperFacade mapperFacade;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;

    /**
     * 分页查询[KS样本录入]列表
     *
     * @param page  分页参数
     * @param param SampleDelivery查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SampleDeVo> getList(PageParam<SampleDelivery> page, SampleDeliveryParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return sampleDeliveryMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public SampleDelivery getInfoById(String id) {
        return sampleDeliveryMapper.getInfoById(id);
    }

    /**
     * 录入保存
     *
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(SDSaOrUParam param) {
        List<SampleDelivery> list = new ArrayList();
        SampleDelivery sd = mapperFacade.map(param, SampleDelivery.class);
        // 判断是否有重复的体检号
        Long sds = sampleDeliveryMapper.selectCount(new QueryWrapper<SampleDelivery>().eq("patientcode", sd.getPatientcode()));
        // 体检号重复就保存收费项目
        if (sds > 0) {
            List<String> ids = param.getIds();
            for (int i = 0; i < ids.size(); i++) {
                //体检者收费项目
                Peispatientfeeitem pf = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", sd.getPatientcode()).eq("id", ids.get(i)));
                if (ObjectUtils.isNotEmpty(pf)) {
                    //是否已做  1已做  null或其他未做
                    pf.setGiveupnotelet("1");
                    //样本录入
                    SampleDelivery sdsNews = sampleDeliveryMapper.selectOne(new QueryWrapper<SampleDelivery>().eq("patientcode", sd.getPatientcode())
                            .eq("id_examfeeitem", pf.getIdExamfeeitem()).eq("is_delete", 0));
                    if (ObjectUtils.isEmpty(sdsNews)) {
                        //设置属性
                        sdsNews = sd;
                        sdsNews.setIdExamfeeitem(pf.getIdExamfeeitem());
                        sdsNews.setCreateId(SecurityUtils.getUserNo());
                        sdsNews.setIsDelete(0);
                        list.add(sdsNews);
                    } else {
                        throw new ServiceException("录入数据重复！请重新录入。");
                    }
                } else {
                    throw new ServiceException("收费项目不存在！请刷新页面。");
                }
            }
            //批量插入
            this.saveBatch(list);
        } else {
            List<String> ids = param.getIds();
            for (int i = 0; i < ids.size(); i++) {
                Peispatientfeeitem pf = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", sd.getPatientcode().toUpperCase()).eq("id", ids.get(i)));
                if (ObjectUtils.isEmpty(pf)) {
                    throw new ServiceException("收费项目不存在！请刷新页面");
                } else {
                    pf.setGiveupnotelet("1");
                    SampleDelivery sdsNews = sd;
                    sdsNews.setIdExamfeeitem(pf.getIdExamfeeitem());
                    sdsNews.setCreateId(SecurityUtils.getUserNo());
                    sdsNews.setIsDelete(0);
                    list.add(sdsNews);
                }
            }
            this.saveBatch(list);
        }
        return Boolean.TRUE;
    }

    /**
     * 查询导出数据
     *
     * @param param
     * @return
     */
    @Override
    public List<SampleDeVo> export(SampleDeliveryParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return sampleDeliveryMapper.export(param);
    }
}

