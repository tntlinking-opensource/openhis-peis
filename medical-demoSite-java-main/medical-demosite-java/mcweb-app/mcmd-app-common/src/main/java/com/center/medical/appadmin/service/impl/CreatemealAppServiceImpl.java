package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.dto.MealItemsDto;
import com.center.medical.appadmin.bean.model.CreatemealApp;
import com.center.medical.appadmin.bean.param.*;
import com.center.medical.appadmin.bean.vo.CreatemealAppVo;
import com.center.medical.appadmin.bean.vo.GetMealDetailsVo;
import com.center.medical.appadmin.bean.vo.GetMealListVo;
import com.center.medical.appadmin.dao.CreatemealAppMapper;
import com.center.medical.appadmin.service.CreatemealAppService;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.dao.CreatecomboMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 小程序套餐表(CreatemealApp)服务实现类
 *
 * @author ay
 * @since 2024-06-12 17:31:42
 */
@Slf4j
@Service("createmealAppService")
@RequiredArgsConstructor
public class CreatemealAppServiceImpl extends ServiceImpl<CreatemealAppMapper, CreatemealApp> implements CreatemealAppService {

    private final CreatemealAppMapper createmealAppMapper;
    private final MapperFacade mapperFacade;
    private final CreatecomboMapper createcomboMapper;
    /**
     * 分页查询[小程序套餐表]列表
     *
     * @param page  分页参数
     * @param param CreatemealApp查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CreatemealAppVo> getPage(PageParam<CreatemealAppVo> page, CreatemealAppParam param) {
        return createmealAppMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CreatemealApp getInfoById(String id) {
        return createmealAppMapper.getInfoById(id);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteIds(List<String> ids) {
        //更新为已删除状态
        CreatemealApp createmealApp = new CreatemealApp();
        createmealApp.setIsDelete(1);
        createmealApp.setModifydate(new Date());
        createmealAppMapper.update(createmealApp,new LambdaQueryWrapper<CreatemealApp>()
                .in(CreatemealApp::getId,ids));
        return Boolean.TRUE;
    }

    /**
     * 添加或更新
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(CMAppSaOrUpParam param) {
        CreatemealApp createmealApp = mapperFacade.map(param, CreatemealApp.class);
        if (ObjectUtils.isNotEmpty(createmealApp.getId())){
            //更新
            CreatemealApp createmealApp1 = createmealAppMapper.getInfoById(createmealApp.getId());
            if (ObjectUtils.isEmpty(createmealApp1)){
                throw new ServiceException("该id不存在!");
            }
            createmealApp.setModifydate(new Date());
            createmealAppMapper.updateById(createmealApp);
        }else {
            //添加
            createmealApp.setStatus(0);
            createmealApp.setCreatedate(new Date());
            createmealAppMapper.insert(createmealApp);
        }
        return Boolean.TRUE;
    }


    /**
     * 设为app套餐
     * @param id
     * @return
     */
    @Override
    public Boolean setAppMeal(String id) {
        CreatemealApp createmealApp = new CreatemealApp();
        createmealApp.setTcid(id);
        createmealApp.setStatus(0);
        createmealApp.setIsDelete(0);
        //给一个默认的名称
        createmealApp.setAppTcmc(createcomboMapper.getInfoById(id).getTjtcmc());
        createmealAppMapper.insert(createmealApp);
        return Boolean.TRUE;
    }

    /**
     * 获取套餐列表
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<GetMealListVo> getMealList(PageParam<GetMealListVo> page, GetMealListParam param) {
        return createmealAppMapper.getMealList(page,param);
    }

    /**
     * 获取套餐详情
     * @param param
     * @return
     */
    @Override
    public GetMealDetailsVo getMealDetails(GetMealDetailsParam param) {
        GetMealDetailsVo vo = createmealAppMapper.getMealDetails(param);
        if (ObjectUtils.isNotEmpty(vo)){
            List<MealItemsDto> mealItemsDtoList = createmealAppMapper.getMealItems(vo.getTcid());
            vo.setItemData(mealItemsDtoList);
        }

        return vo;
    }

    /**
     * 上线或下线
     * @param param
     * @return
     */
    @Override
    public Boolean goLive(GoLiveParam param) {
        CreatemealApp createmealApp = new CreatemealApp();
        createmealApp.setStatus(param.getStatus() == 1 ? 1 : -1);
        createmealApp.setModifydate(new Date());
        createmealAppMapper.update(createmealApp,new LambdaQueryWrapper<CreatemealApp>()
                .in(CreatemealApp::getId,param.getIds()));
        return Boolean.TRUE;
    }
}

