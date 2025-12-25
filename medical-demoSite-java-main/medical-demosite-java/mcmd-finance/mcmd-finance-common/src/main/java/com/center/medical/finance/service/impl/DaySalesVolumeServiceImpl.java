package com.center.medical.finance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.PeispatientChargeMain;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BusinessCat;
import com.center.medical.data.dao.BusinessCatMapper;
import com.center.medical.finance.bean.dto.DSVOptionDto;
import com.center.medical.finance.bean.param.ActivityMealListParam;
import com.center.medical.finance.bean.param.GetActivityMealParam;
import com.center.medical.finance.bean.param.StatementsParam;
import com.center.medical.finance.bean.vo.*;
import com.center.medical.finance.bean.vo.*;
import com.center.medical.finance.dao.DaySalesVolumeMapper;
import com.center.medical.finance.service.DaySalesVolumeService;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.system.dao.SysBranchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 体检者费用主表(PeispatientChargeMain)表服务实现类
 *
 * @author ay
 * @since 2023-06-20 17:50:32
 */
@Slf4j
@Service("daySalesVolumeService")
@RequiredArgsConstructor
public class DaySalesVolumeServiceImpl extends ServiceImpl<DaySalesVolumeMapper, PeispatientChargeMain> implements DaySalesVolumeService {

    private final DaySalesVolumeMapper daySalesVolumeMapper;
    private final SysBranchMapper sysBranchMapper;
    private final CreatemealMapper createmealMapper;
    private final BusinessCatMapper businessCatMapper;

    /**
     * 当日线下业绩
     * @param param
     * @return
     */
    @Override
    public DaySalesVolumeListVo getDaySalesVolume(StatementsParam param) {
        //查询分中心
        List<SysBranch> sysBranches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>()
                .eq(SysBranch::getIsShow, 1)
                .orderByAsc(SysBranch::getBranchSort));
        DaySalesVolumeListVo vo = new DaySalesVolumeListVo();
        //总数据
        List<DaySalesVolumeVo> list = daySalesVolumeMapper.getDaySalesVolume(param);
        //没有的数据补0
        List<DaySalesVolumeVo> listVo = daySalesVolumeSupplement(list,sysBranches);
        vo.setTotal(listVo);
        //选项数据
        List<String> ids = param.getIds();
        if (CollectionUtil.isNotEmpty(ids)){
            List<List<DSVOptionDto>> Option = new ArrayList<>();
            for (String id : ids) {
                //查询数据
                List<DSVOptionDto> list1 = daySalesVolumeMapper.findOption(param.getTime(),id);
                //没有的补0
                List<DSVOptionDto> options = optionSupplement(list1,sysBranches,id);
                Option.add(options);
            }
            vo.setOption(Option);
        }
        return vo;
    }

    /**
     * 选项数据补全
     * @param list
     * @param sysBranches
     * @param id
     * @return
     */
    private List<DSVOptionDto> optionSupplement(List<DSVOptionDto> list, List<SysBranch> sysBranches, String id) {
        BusinessCat businessCat = businessCatMapper.getInfoById(Long.valueOf(id));
        List<DSVOptionDto> listVo = new ArrayList<>();
        List<String> branchNameList = sysBranches.stream().map(b -> b.getFzx()).collect(Collectors.toList());
        for (String name : branchNameList) {
            boolean found = false;
            for (DSVOptionDto dto : list) {
                if (dto.getFzx().equals(name)) {
                    //找到了
                    listVo.add(dto);
                    found = true;
                    break;
                }
            }
            if (!found) {
                //没找到加0
                DSVOptionDto vo = new DSVOptionDto(name,0.0,businessCat.getTypeName());
                listVo.add(vo);
            }
        }
        return listVo;
    }

    /**
     * 没有的数据补0
     * @param list
     * @param sysBranches
     * @return
     */
    private List<DaySalesVolumeVo> daySalesVolumeSupplement(List<DaySalesVolumeVo> list, List<SysBranch> sysBranches) {
        List<DaySalesVolumeVo> listVo = new ArrayList<>();
        List<String> branchNameList = sysBranches.stream().map(b -> b.getFzx()).collect(Collectors.toList());
        for (String name : branchNameList) {
            boolean found = false;
            for (DaySalesVolumeVo dto : list) {
                if (dto.getFzx().equals(name)) {
                    //找到了
                    listVo.add(dto);
                    found = true;
                    break;
                }
            }
            if (!found) {
                //没找到加0
                DaySalesVolumeVo vo = new DaySalesVolumeVo(name,"0");
                listVo.add(vo);
            }
        }
        return listVo;
    }


    /**
     * 活动套餐-获取活动套餐种类
     * @return
     */
    @Override
    public IPage<ActivityMealVo> getActivityMeal(PageParam<FIPageVo> page, GetActivityMealParam param) {
        return daySalesVolumeMapper.getActivityMeal(page,param);
    }


    /**
     * 活动套餐-列表
     * @param param
     * @return
     */
    @Override
    public List<List<ActivityMealListVo>> getActivityMealList(ActivityMealListParam param) {
        //查询分中心
        List<SysBranch> sysBranches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>()
                .eq(SysBranch::getIsShow, 1)
                .orderByAsc(SysBranch::getBranchSort));
        List<List<ActivityMealListVo>> vo = new ArrayList<>();
        List<String> ids = param.getIds();
        //最多选三个活动套餐
        if (ObjectUtils.isNotEmpty(ids)){
            for (String id : ids) {
                //查询数据
                List<ActivityMealListVo> activityMealList = daySalesVolumeMapper.getActivityMealList(id,param.getNow());
                //没查寻到的数据设置为0
                List<ActivityMealListVo> list = supplement(activityMealList,sysBranches,id);
                vo.add(list);
            }
        }
        return vo;
    }

    /**
     * 没查寻到的数据设置为0
     * @param activityMealList
     * @param sysBranches
     * @return
     */
    private List<ActivityMealListVo> supplement(List<ActivityMealListVo> activityMealList, List<SysBranch> sysBranches,String id) {
        List<ActivityMealListVo> list = new ArrayList<>();
        List<String> branchNameList = sysBranches.stream().map(b -> b.getFzx()).collect(Collectors.toList());
        Createmeal createmeal = createmealMapper.getInfoById(id);
        for (String name : branchNameList) {
            boolean found = false;
            for (ActivityMealListVo dto : activityMealList) {
                if (dto.getFzx().equals(name)) {
                    //找到了
                    list.add(dto);
                    found = true;
                    break;
                }
            }
            if (!found) {
                //没找到加0
                ActivityMealListVo vo = new ActivityMealListVo(name,id,createmeal.getTjtcmc(),"0","0");
                activityMealList.add(vo);
            }
        }
        return activityMealList;


    }
}

