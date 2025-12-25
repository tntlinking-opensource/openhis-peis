package com.center.medical.datascreen.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.enums.UserLevelType;
import com.center.medical.bean.enums.ZySummaryEnum;
import com.center.medical.bean.model.Branch;
import com.center.medical.common.utils.DateUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.datascreen.bean.dto.PlatformDataPersonPatientPageDto;
import com.center.medical.datascreen.bean.param.BranchDataSelectAddItemPageMapperParam;
import com.center.medical.datascreen.bean.param.BranchDataSelectAddItemPageParam;
import com.center.medical.datascreen.bean.param.BranchDataSelectAddItemParam;
import com.center.medical.datascreen.bean.param.DatascreenBaseTimeAndBranchParam;
import com.center.medical.datascreen.bean.vo.*;
import com.center.medical.datascreen.bean.vo.*;
import com.center.medical.datascreen.constant.DatascreenConstant;
import com.center.medical.datascreen.dao.BranchDataMapper;
import com.center.medical.datascreen.dao.PlatformDataMapper;
import com.center.medical.datascreen.service.BranchDataService;
import com.center.medical.datascreen.utils.DatascreenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 中心数据
 * @author xhp
 * @since 2023-06-06 9:07
 */
@Service
@RequiredArgsConstructor
public class BranchDataServiceImpl implements BranchDataService {
    private final BranchDataMapper branchDataMapper;
    private final PlatformDataMapper platformDataMapper;

    /**
     * 中心分区
     * @return
     */
    @Override
    public List<BranchDataBranchVo> selectBranchList(){
        List<Branch> branches=branchDataMapper.selectList(
                new QueryWrapper<Branch>()
                    .eq("is_delete",0)
                    .eq("is_show",1)
                    .orderByAsc("branch_sort")
        );
        return BeanUtil.copyToList(branches,BranchDataBranchVo.class);
    }

    /**
     * 个检体检概况
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<BranchDataPersonPatientPageVo> selectPersonPatientPage(PageParam page, DatascreenBaseTimeAndBranchParam param){
        IPage<PlatformDataPersonPatientPageDto> dtoIPage=platformDataMapper.selectPersonPatientPage(page,param);
        return dtoIPage.convert(
                dto -> {
                    BranchDataPersonPatientPageVo vo= BeanUtil.toBean(dto,BranchDataPersonPatientPageVo.class);
                    String patientcode=dto.getPatientcode();
                    vo.setPayway(platformDataMapper.selectPaywayNamesByPatientcode(patientcode));
                    vo.setDiscountRate(DatascreenUtil.getDiscountRateStr(platformDataMapper.selectDiscountRateByPatientcode(patientcode)));
                    return vo;
                }
        );
    }

    /**
     * 总人数 总共费用等信息
     * @param param
     * @return
     */
    @Override
    public BranchDataTotalNumberVo selectTotalNumber(DatascreenBaseTimeAndBranchParam param, Integer fUsecodehiden){
        BranchDataTotalNumberVo vo=new BranchDataTotalNumberVo();
        vo.setNumber(platformDataMapper.selectTotalNumber(param,fUsecodehiden));
        vo.setAmount(platformDataMapper.selectTotalAmount(param,fUsecodehiden));
        Double discount=platformDataMapper.selectDiscountRate(param,fUsecodehiden);
        vo.setDiscountRate(DatascreenUtil.getDiscountRateStr(discount));
        vo.setCustomerUnitPrice(DatascreenUtil.getCustomerUnitPrice(vo.getAmount(),vo.getNumber()));
        return vo;
    }

    /**
     * 科室工作量
     * @param param
     * @return
     */
    @Override
    public BranchDataSelectDeptVo selectDept(DatascreenBaseTimeAndBranchParam param){
        return branchDataMapper.selectOpenedDeptCount(param);
    }

    /**
     * 项目推广统计
     * @param param
     * @return
     */
    @Override
    public List<DatascreenBaseListVo> selectItemList(DatascreenBaseTimeAndBranchParam param){
        return branchDataMapper.selectItemList(param);
    }

    /**
     *销售部预约
     * @param param
     * @return
     */
    @Override
    public BranchDataSelectReservationVo selectReservation(DatascreenBaseTimeAndBranchParam param){
        BranchDataSelectReservationVo vo=new BranchDataSelectReservationVo();
        vo.setGroup(branchDataMapper.selectReservationCount(param, DatascreenConstant.GROUP,null));
        vo.setVip(branchDataMapper.selectReservationCount(param,null, UserLevelType.vip.value()));
        vo.setGuest(branchDataMapper.selectReservationCount(param,null, UserLevelType.guest.value()));
        return vo;
    }

    /**
     * 危急值
     * @param param
     * @return
     */
    @Override
    public BranchDataSelectCriticalValueVo selectCriticalValue(DatascreenBaseTimeAndBranchParam param){
        BranchDataSelectCriticalValueVo vo=new BranchDataSelectCriticalValueVo();
        vo.setHignRisk(branchDataMapper.selectHighRiskCount(param));
        vo.setOccupationalContraindication(branchDataMapper.selectCountByHandlingOpinion(param, ZySummaryEnum.occupationalContraindication.getSerialNo()));
        vo.setOccupationalDiseases(branchDataMapper.selectCountByHandlingOpinion(param, ZySummaryEnum.occupationalDiseases.getSerialNo()));
        return vo;
    }

    /**
     * 回访率
     * @param param
     * @return
     */
    @Override
    public BranchDataSelectFollowUpVo selectFollowUp(DatascreenBaseTimeAndBranchParam param){
        BranchDataSelectFollowUpVo vo=new BranchDataSelectFollowUpVo();
        vo.setCriticalValue(DatascreenUtil.getPieData(branchDataMapper.selectCriticalValueRate(param)));
        vo.setGroup(DatascreenUtil.getPieData(branchDataMapper.selectFollowUpRate(param,DatascreenConstant.GROUP)));
        vo.setPerson(DatascreenUtil.getPieData(branchDataMapper.selectFollowUpRate(param,DatascreenConstant.PERSON)));
        vo.setSatisfaction(DatascreenUtil.getPieData(branchDataMapper.selectSatisfactionRate(param)));
        return vo;
    }

    /**
     * 加项数据统计
     * @param param
     * @return
     */
    @Override
    public BranchDataSelectAddItemVo selectAddItem(BranchDataSelectAddItemParam param){
        BranchDataSelectAddItemVo vo=new BranchDataSelectAddItemVo();
        vo.setNumber(branchDataMapper.selectAddItemNumber(param));
        double amount=branchDataMapper.selectAddItemAmount(param);
        vo.setAmount(amount);
        double amountLastYear=branchDataMapper.selectAddItemAmount(DatascreenUtil.getLastYearParam(param));
        vo.setGrowthRate(DatascreenUtil.getIncreaseRate(amountLastYear,amount));
        String[] days=new String[7];
        for(int i=0;i<days.length;i++){
            days[days.length-i-1]=LocalDateTime.now().minusDays(i).format(DateTimeFormatter.ofPattern("MM月dd日"));
        }
        vo.setDays(days);

        List<BranchDataGrowthRateDataVo> data=new ArrayList<>();
        vo.setData(data);
        for(Integer year:param.getYears()){
            BranchDataGrowthRateDataVo branchDataGrowthRateDataVo=new BranchDataGrowthRateDataVo();
            branchDataGrowthRateDataVo.setYear(year);
            List<DatascreenBaseTimeAndBranchParam> params=DatascreenUtil.getGivenYearParam(param,year);
            String[] value=new String[params.size()];
            branchDataGrowthRateDataVo.setValue(value);
            data.add(branchDataGrowthRateDataVo);

            for(int i=0;i<params.size();i++){
                DatascreenBaseTimeAndBranchParam dayParam=params.get(i);
                double amountDay=branchDataMapper.selectAddItemAmount(dayParam);
                double amountLastYearDay=branchDataMapper.selectAddItemAmount(DatascreenUtil.getLastYearParam(dayParam));
                value[i]=DatascreenUtil.getIncreaseRateNumber(amountLastYearDay,amountDay);
            }
        }

        return vo;
    }

    /**
     * 加项数据统计列表
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<BranchDataSelectAddItemPageVo> selectAddItemPage(PageParam page, BranchDataSelectAddItemPageParam param){
        BranchDataSelectAddItemPageMapperParam mapperParam=new BranchDataSelectAddItemPageMapperParam();
        mapperParam.setBranchId(param.getBranchId());
        List<Integer> years=param.getYears();
        int no=0;
        for(Integer year:years){
            if(year==null)continue;
            Date startTime=DateUtils.toDate(LocalDateTimeUtil.beginOfDay(LocalDateTime.now().withYear(year).minusDays(6)));
            Date endTime=DateUtils.toDate(LocalDateTimeUtil.endOfDay(LocalDateTime.now().withYear(year)));
            if(no==0){
                mapperParam.setStartTime1(startTime);
                mapperParam.setEndTime1(endTime);
            }else if(no==1){
                mapperParam.setStartTime2(startTime);
                mapperParam.setEndTime2(endTime);
            }else if(no==2){
                mapperParam.setStartTime3(startTime);
                mapperParam.setEndTime3(endTime);
            }
            no++;
        }
        return branchDataMapper.selectAddItemPage(page,mapperParam);
    }

    /**
     * 科室工作量列表
     * @param param
     * @return
     */
    @Override
    public List<BranchDataSelectDeptListVo> selectDeptList(DatascreenBaseTimeAndBranchParam param){
        return branchDataMapper.selectDeptList(param);
    }
}
