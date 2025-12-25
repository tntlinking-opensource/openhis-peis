package com.center.medical.abteilung.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.dto.PbFormdataDto;
import com.center.medical.abteilung.bean.dto.PbGriddataDto;
import com.center.medical.abteilung.bean.model.DrugstorePrescribe;
import com.center.medical.abteilung.bean.param.DrugstorePreParam;
import com.center.medical.abteilung.bean.param.PrescribeParam;
import com.center.medical.abteilung.bean.param.TakeDrugParam;
import com.center.medical.abteilung.bean.vo.DrugstorePreVo;
import com.center.medical.abteilung.dao.DrugstorePrescribeMapper;
import com.center.medical.abteilung.service.DrugstorePrescribeService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 开药记录(DrugstorePrescribe)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:18
 */
@Slf4j
@Service("drugstorePrescribeService")
@RequiredArgsConstructor
public class DrugstorePrescribeServiceImpl extends ServiceImpl<DrugstorePrescribeMapper, DrugstorePrescribe> implements DrugstorePrescribeService {

    private final DrugstorePrescribeMapper drugstorePrescribeMapper;
    private final MapperFacade mapperFacade;
    private final PeispatientMapper peispatientMapper;

    /**
     * 分页查询[开药记录]列表
     *
     * @param page  分页参数
     * @param param DrugstorePrescribe查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DrugstorePreVo> getList(PageParam<DrugstorePreVo> page, DrugstorePreParam param) {
        // 开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return drugstorePrescribeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public DrugstorePrescribe getInfoById(String id) {
        return drugstorePrescribeMapper.getInfoById(id);
    }


    /**
     * 分页查询药房管理售药统计
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<DrugstorePreVo> getStatisticsListData(PageParam<DrugstorePreVo> page, DrugstorePreParam param) {
        // 开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return drugstorePrescribeMapper.getStatisticsListData(page, param);
    }

    /**
     * 获取导出数据
     *
     * @param param
     * @return
     */
    @Override
    public List<DrugstorePreVo> getExportData(DrugstorePreParam param) {
        // 开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return drugstorePrescribeMapper.getExportData(param);
    }


    /**
     * 快捷开药-右侧-获得已开药记录
     *
     * @param patientcode
     * @return
     */
    @Override
    public List<DrugstorePreVo> getAddedData(String patientcode) {
        String username = SecurityUtils.getUsername();
        return drugstorePrescribeMapper.getAddedData(patientcode, username);
    }


    /**
     * 快捷开药-保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(PrescribeParam param) {
        PbFormdataDto formobj = param.getFormdata();
        //开药原因
        String reason = formobj.getReason();
        String patientcode = formobj.getPatientcode();

        List<PbGriddataDto> arr = param.getGriddata();
        String username = SecurityUtils.getUsername();
        for (int i = 0; i < arr.size(); i++) {
            PbGriddataDto obj = arr.get(i);
            String state = obj.getState();
            //插入
            if ("added".equals(state)) {
                DrugstorePrescribe dp = mapperFacade.map(obj, DrugstorePrescribe.class);
                //开药原因
                dp.setReason(reason);
                dp.setUsername(username);
                //是否已开药：0.否
                dp.setIsFinished(0);
                dp.setPatientcode(patientcode);
                drugstorePrescribeMapper.insert(dp);
            } else if ("modified".equals(state)) {
                //更新
                DrugstorePrescribe old = drugstorePrescribeMapper.getInfoById(obj.getId());
                if (ObjectUtils.isEmpty(old)) {
                    throw new ServiceException("保存失败，您修改的开药记录已被删除，请刷新重试！");
                }
                if (old.getIsFinished() == 1 || old.getIsFinished() == 2) {
                    throw new ServiceException("药房已处理，不能修改，请刷新重试！");
                }
                DrugstorePrescribe dp = mapperFacade.map(obj, DrugstorePrescribe.class);
                dp.setReason(reason);
                drugstorePrescribeMapper.updateById(dp);
            } else if ("removed".equals(state)) {
                //删除
                DrugstorePrescribe old = drugstorePrescribeMapper.getInfoById(obj.getId());

                if (ObjectUtils.isNotEmpty(old)) {
                    //是否已开药：0.否 1.已成交 2.未成交 3.退药
                    if (old.getIsFinished() == 1 || old.getIsFinished() == 2) {
                        throw new ServiceException("药房已处理，不能删除，请刷新重试！");
                    }
                    drugstorePrescribeMapper.deleteById(obj.getId());
                }
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 药房取药
     *
     * @param param
     * @return
     */
    @Override
    public Boolean takeDrug(TakeDrugParam param) {
        String id = param.getId();
        DrugstorePrescribe old = drugstorePrescribeMapper.getInfoById(id);
        if (ObjectUtils.isEmpty(old)) {
            throw new ServiceException("保存失败，记录已被删除，请刷新重试！");
        }
        String note = param.getNote();
        Integer isFinished = param.getIsFinished();
        //是否已开药：0.否 1.已成交 2.未成交 3.退药
        if (isFinished == 2 && StringUtils.isEmpty(note)) {
            throw new ServiceException("请为未成交的记录填写备注。");
        }
        //更新
        old.setIsFinished(isFinished);
        old.setTransactionPrice(param.getPrice());
        old.setPrescribeNote(note);
        drugstorePrescribeMapper.updateById(old);
        return Boolean.TRUE;
    }

    /**
     * 获取上次体检开的什么药
     *
     * @param patientcode
     * @return
     */
    @Override
    public List<DrugstorePreVo> getLastDrugs(String patientcode) {
        Peispatient peispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientcode));
        //查询同一个档案的体检号
        String string = drugstorePrescribeMapper.getLastDrugs(patientcode, peispatient.getPatientarchiveno());
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        //有的话就拿体检号继续查询
        return getAddedData(string);
    }


    /**
     * 退药
     *
     * @param id
     * @return
     */
    @Override
    public Boolean drugRepercussion(String id) {
        DrugstorePrescribe dp = drugstorePrescribeMapper.getInfoById(id);
        //是否已开药：0.否 1.已成交 2.未成交 3.退药
        dp.setIsFinished(3);
        drugstorePrescribeMapper.updateById(dp);
        return Boolean.TRUE;
    }
}

