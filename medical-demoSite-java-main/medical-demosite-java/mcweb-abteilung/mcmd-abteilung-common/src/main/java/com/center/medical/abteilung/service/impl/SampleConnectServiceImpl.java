package com.center.medical.abteilung.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.model.SampleConnect;
import com.center.medical.abteilung.bean.param.SampleSaOrUpParam;
import com.center.medical.abteilung.bean.vo.SampleConnectVo;
import com.center.medical.abteilung.dao.SampleConnectMapper;
import com.center.medical.abteilung.service.SampleConnectService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * KS样本交接(SampleConnect)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:33
 */
@Slf4j
@Service("sampleConnectService")
@RequiredArgsConstructor
public class SampleConnectServiceImpl extends ServiceImpl<SampleConnectMapper, SampleConnect> implements SampleConnectService {

    private final SampleConnectMapper sampleConnectMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;

    /**
     * 分页查询[KS样本交接]列表
     *
     * @param page  分页参数
     * @param param SampleConnect查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SampleConnectVo> getList(PageParam<SampleConnectVo> page, BaseParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return sampleConnectMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public SampleConnect getInfoById(String id) {
        return sampleConnectMapper.getInfoById(id);
    }


    /**
     * 样本交接展现数据
     *
     * @param page
     * @param patientCode
     * @return
     */
    @Override
    public HashMap<String, Object> getItemData(PageParam<SampleConnectVo> page, String patientCode) {
        Peispatient pt = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientCode));
        if (ObjectUtils.isEmpty(pt)) {
            throw new ServiceException("体检号不存在！请重新输入。");
        }
        HashMap<String, Object> record = new HashMap<String, Object>();
        record.put("patientcode", pt.getPatientcode());
        record.put("patientname", pt.getPatientname());
        record.put("idSex", pt.getIdSex());
        List<Peispatientfeeitem> pf = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>().eq("id_patient", pt.getPatientcode()));
        String str = "";
        if (pf.size() != 0) {
            for (Peispatientfeeitem peispatientfeeitem : pf) {
                str += peispatientfeeitem.getExamfeeitemName() + ",";
            }
            str = str.substring(0, str.length() - 1);
        }
        record.put("examfeeitemName", str);
        return record;
    }


    /**
     * 保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(SampleSaOrUpParam param) {
        List<SampleConnect> list = new ArrayList();
        List<String> gridMap = param.getPatientCode();
        //承接人
        String recipient = param.getRecipient();
        //交接时间
        Date deliveryTime = param.getDeliveryTime();
        for (String map : gridMap) {
            SampleConnect sampleConnect = new SampleConnect();
            /** 交接人 */
            String deliveryName = SecurityUtils.getUserNo();
            sampleConnect.setDeliveryName(deliveryName);
            sampleConnect.setRecipient(recipient);
            sampleConnect.setPatientcode(map);
            /** 交接时间 */
            sampleConnect.setDeliveryTime(deliveryTime);
            list.add(sampleConnect);
        }
        return this.saveBatch(list);
    }
}

