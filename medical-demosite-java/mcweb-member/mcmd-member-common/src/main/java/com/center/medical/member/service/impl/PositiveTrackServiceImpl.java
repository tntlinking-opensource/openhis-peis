package com.center.medical.member.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.FailTotalVisit;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.FailTotalVisitMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientarchiveMapper;
import com.center.medical.member.bean.param.PeispatientParam;
import com.center.medical.member.bean.param.PositiveTracktParam;
import com.center.medical.member.bean.vo.PeispatientEditVo;
import com.center.medical.member.bean.vo.PeispatientVo;
import com.center.medical.member.dao.PositiveTrackMapper;
import com.center.medical.member.service.PositiveTrackService;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.dao.RiskclientMapper;
import com.center.medical.service.FailTotalVisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author makejava
 * @since 2023-02-02 10:12:36
 */
@Slf4j
@Service("positiveTrackService")
@RequiredArgsConstructor
public class PositiveTrackServiceImpl extends ServiceImpl<PositiveTrackMapper, Peispatient> implements PositiveTrackService {

    private final PositiveTrackMapper positiveTrackMapper;
    private final PeispatientMapper peispatientMapper;
    private final FailTotalVisitMapper failTotalVisitMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final RiskclientMapper riskclientMapper;
    private final FailTotalVisitService failTotalVisitService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PeispatientVo> getList(PageParam<Peispatient> page, PeispatientParam param) {

        //体检开始时间
        if (ObjectUtils.isNotEmpty(param.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(param.getStartDate());
            param.setStartDate(startDate);
        }
        //体检结束时间
        if (ObjectUtils.isNotEmpty(param.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(param.getEndDate());
            param.setEndDate(endDate);
        }
        return positiveTrackMapper.getList(page, param);
    }

    /**
     * 导出excel
     *
     * @param param
     * @return
     */
    @Override
    public List<PeispatientVo> export(PeispatientParam param) {
        //姓名
        if (ObjectUtils.isNotEmpty(param.getPatientname())) {
            param.setPatientname(param.getPatientname().trim().toUpperCase());
        }
        //体检号
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //电话
        if (ObjectUtils.isNotEmpty(param.getPhone())) {
            param.setPhone(param.getPhone().trim().toUpperCase());
        }
        //体检类型
        if (ObjectUtils.isNotEmpty(param.getIdExamtype())) {
            param.setIdExamtype(param.getIdExamtype().trim().toUpperCase());
        }
        //客户类型
        if (ObjectUtils.isNotEmpty(param.getKhlx())) {
            param.setKhlx(param.getKhlx().trim().toUpperCase());
        }
        //体检开始时间
        if (ObjectUtils.isNotEmpty(param.getStartDate())) {
            DateTime startDate = DateUtil.beginOfDay(param.getStartDate());
            param.setStartDate(startDate);
        }
        //体检结束时间
        if (ObjectUtils.isNotEmpty(param.getEndDate())) {
            DateTime endDate = DateUtil.endOfDay(param.getEndDate());
            param.setEndDate(endDate);
        }
        //阳性结果
        if (ObjectUtils.isNotEmpty(param.getYxjg())) {
            param.setYxjg(param.getYxjg().trim().toUpperCase());
        }
        //危险程度
        if (ObjectUtils.isNotEmpty(param.getWjzjb())) {
            param.setWjzjb(param.getWjzjb().trim().toUpperCase());
        }
        return positiveTrackMapper.export(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public List<PeispatientEditVo> getInfoById(String id) {
        return positiveTrackMapper.getInfoById(id);
    }

    /**
     * 阳性结果回访
     *
     * @param param
     * @return
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUpPositiveTrackt(PositiveTracktParam param) {
        String patientcode = param.getPatientcode();
        //根据体检号获取体检者
        Peispatient pt = peispatientMapper.getByPatientCode(patientcode);
        String id = param.getId();
        //KF迟捡、阳性、不合格样本回访
        FailTotalVisit fv;
        //id不为空查询，为空新建
        if (StringUtils.isNotEmpty(id)) {
            fv = failTotalVisitMapper.selectOne(new QueryWrapper<FailTotalVisit>().eq("personcode", id));
        } else {
            fv = new FailTotalVisit();
            fv.setVisitType(1);// 设置为阳性结果回访
            fv.setPersoncode(patientcode);// 设置体检号
            //体检者档案表
            Peispatientarchive pa = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>().eq("patientarchiveno",pt.getPatientarchiveno()));
            pa.setRestatus(1);// 设置为已回访状态
            fv.setVisitText("3");// 跟进内容
            fv.setVisitResult(0);// 跟进结果为0，已回访
            peispatientarchiveMapper.updateById(pa);
        }
        fv.setVisitId(SecurityUtils.getUsername());// 回访人
        fv.setMemo(param.getMemo());//回访备注
        fv.setVisitTime(param.getVisitTime());//回访时间
        failTotalVisitService.saveOrUpdate(fv);

        List<Riskclient> rcs = riskclientMapper.selectList(new QueryWrapper<Riskclient>()
                .or(i -> i.isNull("reportstatus").ne("reportstatus", 1))
                .eq("tjid", patientcode));
        for (Riskclient rc : rcs) {
            rc.setReportstatus(1);
            riskclientMapper.updateById(rc);
        }
        return Boolean.TRUE;
    }


}

