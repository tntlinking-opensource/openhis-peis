package com.center.medical.report.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PeisState;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeisStateMapper;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.ZyVsSummary;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.ZyVsSummaryMapper;
import com.center.medical.report.bean.param.ZySaveParam;
import com.center.medical.report.bean.param.ZyVsSummaryListParam;
import com.center.medical.report.bean.vo.ZyChooseVo;
import com.center.medical.report.bean.vo.ZyGridDataVo;
import com.center.medical.report.bean.vo.ZyVsSummaryListVo;
import com.center.medical.report.dao.ZyFinalSummaryMapper;
import com.center.medical.report.service.ZyFinalSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.center.medical.report.bean.model.ZyFinalSummary;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 职业最终结论(ZyFinalSummary)服务实现类
 *
 * @author makejava
 * @since 2024-12-18 09:28:44
 */
@Slf4j
@Service("zyFinalSummaryService")
@RequiredArgsConstructor
public class ZyFinalSummaryServiceImpl extends ServiceImpl<ZyFinalSummaryMapper, ZyFinalSummary> implements ZyFinalSummaryService {

    private final ZyFinalSummaryMapper zyFinalSummaryMapper;
    private final ZyVsSummaryMapper zyVsSummaryMapper;
    private final HarmMapper harmMapper;
    private final PeisStateMapper peisStateMapper;

    /**
     * 分页查询[职业最终结论]列表
     *
     * @param page  分页参数
     * @param param ZyFinalSummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZyFinalSummary> getPage(PageParam<ZyFinalSummary> page, ZyFinalSummary param) {
        return zyFinalSummaryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ZyFinalSummary getInfoById(String id) {
        return zyFinalSummaryMapper.getInfoById(id);
    }

    /**
     * 获取职业最终结论
     * @param patientcode
     * @return
     */
    @Override
    public List<ZyGridDataVo> getGridData(String patientcode) {
        return zyFinalSummaryMapper.getGridData(patientcode);
    }

    /**
     * 选择危害因素
     * @param patientcode
     * @return
     */
    @Override
    public List<ZyChooseVo> getPendingHarmsList(String patientcode) {
        return zyFinalSummaryMapper.getPendingHarmsList(patientcode);
    }

    /**
     * 获取职业最终结论
     * @param param
     * @return
     */
    @Override
    public List<ZyVsSummaryListVo> getZyVsSummaryList(ZyVsSummaryListParam param) {
        if (CollectionUtil.isEmpty(param.getOccupationSummary())){
            param.setOccupationSummary(Arrays.asList("1","4","5","3"));
        }
        return zyFinalSummaryMapper.getZyVsSummaryList(param);
    }


    /**
     * 添加
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saOrUp(ZySaveParam param) {
        String patientcode = param.getPatientcode();
        List<String> zyVsSummaryIds = param.getIds();
        List<ZyFinalSummary> zyFinalSummariesOld = zyFinalSummaryMapper.selectList(new QueryWrapper<ZyFinalSummary>()
                        .eq("patientcode",patientcode).eq("is_delete",0)
        );
        //所有接害因素id,用于判断重复和是否全部处理
        Set<String> harmIds = new HashSet<>();
        for(ZyFinalSummary zyFinalSummary:zyFinalSummariesOld){
            String zyVsSummaryId=zyFinalSummary.getProgessional();
            ZyVsSummary zyVsSummary = zyVsSummaryMapper.getInfoById(zyVsSummaryId);
            harmIds.add(zyVsSummary.getOccupationDiagnosis());
        }
        String msg = "";
        for(String zyVsSummaryId:zyVsSummaryIds){
            ZyVsSummary zyVsSummary = zyVsSummaryMapper.getInfoById(zyVsSummaryId);
            String harmId = zyVsSummary.getOccupationDiagnosis();
            if(harmIds.contains(harmId)){
                Harm harm = harmMapper.getInfoById(harmId);
                throw new ServiceException("保存失败！危害因素【"+harm.getHarmName()+"】重复！");
            }
            harmIds.add(harmId);
            ZyFinalSummary zyFinalSummary=new ZyFinalSummary();
            zyFinalSummary.setIsDelete(0);
            zyFinalSummary.setMemo("结论附表录入");
            zyFinalSummary.setPatientcode(patientcode);
            zyFinalSummary.setProgessional(zyVsSummaryId);
            zyFinalSummaryMapper.insert(zyFinalSummary);
        }
        List<ZyChooseVo> pendingHarms = getPendingHarmsList(patientcode);
        Set<String> unHandledHarmNames=new HashSet<>();
        for(ZyChooseVo harm:pendingHarms){
            if(!harmIds.contains(harm.getHarmId())){
                unHandledHarmNames.add(harm.getHarmName());
            }
        }
        if(unHandledHarmNames.size()>0){
            msg = "保存成功！还存在未下最终结论的危害因素："+ StringUtils.join(unHandledHarmNames,",");
        }else{
            msg = "保存成功！";
            updateJinanStatus(patientcode,"修改最终结论，重新上传");
        }
        return msg;
    }


    /**
     * 修改上传状态，重新上传
     * @param patientcode
     */
    @Transactional
    public void updateJinanStatus(String patientcode,String msg){
        if(StrUtil.isEmpty(patientcode))throw new ServiceException("体检号不能为空！");
        PeisState peisState = peisStateMapper.getByPatientcode(patientcode);
        if(peisState!=null){
            peisState.setJinanMsg(msg);
            peisState.setJinanStatus(2);
            peisStateMapper.updateById(peisState);
        }
    }

    /**
     * 删除
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<String> ids) {
        if(CollectionUtil.isEmpty(ids))throw new ServiceException("请填写ids!"); ;
        List<ZyFinalSummary> zyFinalSummaries = zyFinalSummaryMapper.selectList(new QueryWrapper<ZyFinalSummary>()
                .in("id",ids).eq("is_delete",0));
        for(ZyFinalSummary zyFinalSummary:zyFinalSummaries){
            zyFinalSummary.setIsDelete(1);
            zyFinalSummaryMapper.updateById(zyFinalSummary);
        }
        return Boolean.TRUE;
    }
}

