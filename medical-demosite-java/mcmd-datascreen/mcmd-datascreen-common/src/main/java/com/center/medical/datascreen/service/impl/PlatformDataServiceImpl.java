package com.center.medical.datascreen.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Branch;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.dao.BusinessCatMapper;
import com.center.medical.datascreen.bean.dto.PlatformDataGroupPatientListDto;
import com.center.medical.datascreen.bean.dto.PlatformDataPersonPatientPageDto;
import com.center.medical.datascreen.bean.dto.PlatformDataSelectItemsListDto;
import com.center.medical.datascreen.bean.dto.PlatformDataSelectMealPageDto;
import com.center.medical.datascreen.bean.param.DatascreenBaseTimeAndBranchParam;
import com.center.medical.datascreen.bean.param.PlatformDataBaseParam;
import com.center.medical.datascreen.bean.param.PlatformDataBaseTimeNullableParam;
import com.center.medical.datascreen.bean.param.PlatformDataSelectItemsListParam;
import com.center.medical.datascreen.bean.vo.*;
import com.center.medical.datascreen.bean.vo.*;
import com.center.medical.datascreen.constant.DatascreenConstant;
import com.center.medical.datascreen.dao.PlatformDataMapper;
import com.center.medical.datascreen.dao.PlatformDataSysUserMapper;
import com.center.medical.datascreen.service.PlatformDataService;
import com.center.medical.datascreen.utils.DatascreenUtil;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.dao.CreatecomboMapper;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.system.dao.BranchMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xhp
 * @since 2023-05-30 14:46
 */
@Service
@RequiredArgsConstructor
public class PlatformDataServiceImpl implements PlatformDataService {
    private final PlatformDataMapper platformDataMapper;
    private final CreatemealMapper createMealMapper;
    private final CreatecomboMapper createcomboMapper;
    private final PlatformDataSysUserMapper platformDataSysUserMapper;
    private final ISysConfigService iSysConfigService;
    private final BusinessCatMapper businessCatMapper;
    private final BranchMapper branchMapper;
    /**
     *体检中心概况
     */
    @Override
    public PlatformDataOverviewVo getOverview(PlatformDataBaseParam param){
        PlatformDataOverviewVo platformDataOverviewVo=new PlatformDataOverviewVo();
        DatascreenBaseTimeAndBranchParam datascreenBaseTimeAndBranchParam=BeanUtil.toBean(param,DatascreenBaseTimeAndBranchParam.class);
        platformDataOverviewVo.setTotalNumber(platformDataMapper.selectTotalNumber(datascreenBaseTimeAndBranchParam,null));
        platformDataOverviewVo.setTotalAmount(platformDataMapper.selectTotalAmount(datascreenBaseTimeAndBranchParam,null));
        Double discount=platformDataMapper.selectDiscountRate(datascreenBaseTimeAndBranchParam,null);
        platformDataOverviewVo.setDiscountRate(DatascreenUtil.getDiscountRateStr(discount));
        return platformDataOverviewVo;
    }

    /**
     * 团检体检人数
     * @param param
     * @return
     */
    @Override
    public List<PlatformDataNumberListVo> selectGroupNumberList(PlatformDataBaseParam param){
        return platformDataMapper.selectNumberList(param,DatascreenConstant.GROUP);
    }

    /**
     * 个检体检人数
     * @param param
     * @return
     */
    @Override
    public List<PlatformDataNumberListVo>  selectPersonNumberList(PlatformDataBaseParam param){
        return platformDataMapper.selectNumberList(param, DatascreenConstant.PERSON);
    }

    /**
     * 团检体检概况
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<PlatformDataGroupPatientListVo> selectGroupPatientList(PageParam page, DatascreenBaseTimeAndBranchParam param,Integer fUsecodehiden){
        IPage<PlatformDataGroupPatientListDto> dtoIPage=platformDataMapper.selectGroupPatientList(page,param,fUsecodehiden);
        return dtoIPage.convert(
                platformDataGroupPatientListDto -> {
                    PlatformDataGroupPatientListVo vo= BeanUtil.toBean(platformDataGroupPatientListDto,PlatformDataGroupPatientListVo.class);
                    String patientcode=platformDataGroupPatientListDto.getPatientcode();
                    vo.setPayway(platformDataMapper.selectPaywayNamesByPatientcode(patientcode));
                    vo.setDiscountRate(DatascreenUtil.getDiscountRateStr(platformDataMapper.selectDiscountRateByPatientcode(patientcode)));
                    return vo;
                }
        );
    }

    /**
     * 团检体检概况
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<PlatformDataPersonPatientPageVo> selectPersonPatientPage(PageParam page, DatascreenBaseTimeAndBranchParam param){
        IPage<PlatformDataPersonPatientPageDto> dtoIPage=platformDataMapper.selectPersonPatientPage(page,param);
        return dtoIPage.convert(
                dto -> {
                    PlatformDataPersonPatientPageVo vo= BeanUtil.toBean(dto,PlatformDataPersonPatientPageVo.class);
                    String patientcode=dto.getPatientcode();
                    vo.setPayway(platformDataMapper.selectPaywayNamesByPatientcode(patientcode));
                    return vo;
                }
        );
    }

    /**
     * 分中心列表(团检)
     * @param param
     * @return
     */
    @Override
    public IPage<PlatformDataNumberPageVo>  selectGroupNumberPage(PageParam page, PlatformDataBaseParam param){
        return platformDataMapper.selectNumberPage(page,param,DatascreenConstant.GROUP);
    }

    /**
     * 分中心列表(个检)
     * @param param
     * @return
     */
    @Override
    public IPage<PlatformDataNumberPageVo>  selectPersonNumberPage(PageParam page,PlatformDataBaseParam param){
        return platformDataMapper.selectNumberPage(page,param,DatascreenConstant.PERSON);
    }

    /**
     * 活动产品销售(套餐)
     * @param param
     * @return
     */
    @Override
    public List<PlatformDataSelectMealListVo> selectMealList(DatascreenBaseTimeAndBranchParam param){
        return platformDataMapper.selectMealList(param);
    }

    /**
     * 体检套餐概况
     * @param param
     * @return
     */
    @Override
    public IPage<PlatformDataSelectMealPageVo> selectMealPage(PageParam page,DatascreenBaseTimeAndBranchParam param){
        IPage<PlatformDataSelectMealPageDto> result=platformDataMapper.selectMealPage(page,param);
        return result.convert(
                platformDataSelectMealPageDto -> {
                    PlatformDataSelectMealPageVo vo=BeanUtil.toBean(platformDataSelectMealPageDto,PlatformDataSelectMealPageVo.class);
                    String id=platformDataSelectMealPageDto.getId();
                    Createmeal createmeal=createMealMapper.selectById(id);
                    Double zhjg=null;
                    if(createmeal!=null){
                        zhjg=createmeal.getZhjg();
                    }else{
                        Createcombo createcombo=createcomboMapper.selectById(id);
                        if(createcombo!=null){
                            zhjg=createcombo.getZhjg();
                        }
                    }
                    if(zhjg!=null){
                        int sales=platformDataSelectMealPageDto.getSales();
                        String amount=NumberUtil.toStr(
                                NumberUtil.round(
                                        NumberUtil.mul(new Double(sales),zhjg)
                                        ,2
                                )
                        );
                        vo.setAmount(amount);
                    }
                    return vo;
                }
        );
    }

    /**
     * 成本展示
     * @return
     */
    @Override
    public PlatformDataGetCostVo getCost(){
        PlatformDataGetCostVo platformDataGetCostVo=new PlatformDataGetCostVo();
        //医生人员数量=所有是医生的用户数量
        long doctor=platformDataSysUserMapper.selectCount(
                new QueryWrapper<SysUser>()
                .eq("del_flag","0")
                .eq("status","0")
                .eq("is_doc","1")
        );
        platformDataGetCostVo.setDoctor((int)doctor);
        //护理人员数量=所有有护士角色的用户数量
        int nurse=platformDataSysUserMapper.selectUserCountByRoleKey(RoleAuthName.NURSE);
        platformDataGetCostVo.setNurse(nurse);
        return platformDataGetCostVo;
    }

    /**
     *常见病选中展示项
     * @return
     */
    @Override
    public List<String> getCommonDiseaseItems(){
        String configStr=iSysConfigService.selectConfigByKey(Constants.DATASCREEN_CONFIG);
        JSONObject config= JSONUtil.parseObj(configStr);
        List<String> result=JSONUtil.toList(config.getStr("commonDiseaseItems"),String.class);
        return result;
    }

    /**
     * 常见病占比
     * @return
     */
    @Override
    public List<PlatformDataGetCommonDiseaseDataVo> getCommonDiseaseData(){
        String configStr=iSysConfigService.selectConfigByKey(Constants.DATASCREEN_CONFIG);
        JSONObject config= JSONUtil.parseObj(configStr);
        List<PlatformDataGetCommonDiseaseDataVo> result=JSONUtil.toList(config.getStr("commonDiseaseData"),PlatformDataGetCommonDiseaseDataVo.class);
        return result;
    }

    /**
     * 活动产品销售(项目)
     * @param param
     * @return
     */
    @Override
    public List<PlatformDataSelectItemsListVo> selectItemsList(PlatformDataSelectItemsListParam param){
        List<PlatformDataSelectItemsListDto> result=platformDataMapper.selectItemsList(param);
        List<String> ids=param.getIds();
        if(result.size()<ids.size()){
            for(String id:ids){
                boolean exists=false;
                for(PlatformDataSelectItemsListDto dto:result){
                    if(id.equals(dto.getId())){
                        exists=true;
                        break;
                    }
                }
                if(!exists){
                    PlatformDataSelectItemsListDto platformDataSelectItemsListDto=new PlatformDataSelectItemsListDto();
                    platformDataSelectItemsListDto.setAmount(0.0);
                    platformDataSelectItemsListDto.setNumber(0);
                    platformDataSelectItemsListDto.setName(businessCatMapper.selectById(id).getTypeName());
                    result.add(platformDataSelectItemsListDto);
                }
            }
        }
        return BeanUtil.copyToList(result,PlatformDataSelectItemsListVo.class);
    }

    /**
     * 总营业额
     * @param param
     * @return
     */
    @Override
    public PlatformDataTotalAmountVo selectTotalAmount(PlatformDataBaseTimeNullableParam param){
        PlatformDataTotalAmountVo vo=new PlatformDataTotalAmountVo();
        vo.setAmount(platformDataMapper.selectTotalAmount(BeanUtil.toBean(param,DatascreenBaseTimeAndBranchParam.class)
                ,null));
        return vo;
    }

    /**
     * 分中心经纬度
     * @return
     */
    @Override
    public List<PlatformDataSelectBranchPositionVo> selectBranchPositionList(){
        List<Branch> branches=branchMapper.selectList(
            new QueryWrapper<Branch>()
                    .eq("is_delete",0)
                    .eq("is_show",1)
                    .orderByAsc("branch_sort")
        );
        List<PlatformDataSelectBranchPositionVo> result=BeanUtil.copyToList(branches,PlatformDataSelectBranchPositionVo.class);
        return result;
    }

    /**
     * 总会员
     * @param param
     * @return
     */
    @Override
    public PlatformDataTotalNumberVo selectTotalNumber(PlatformDataBaseTimeNullableParam param){
        PlatformDataTotalNumberVo vo=new PlatformDataTotalNumberVo();
        vo.setNumber(
                platformDataMapper.selectTotalNumber(
                    BeanUtil.toBean(param,DatascreenBaseTimeAndBranchParam.class)
                        ,null)
        );
        return vo;
    }
}
