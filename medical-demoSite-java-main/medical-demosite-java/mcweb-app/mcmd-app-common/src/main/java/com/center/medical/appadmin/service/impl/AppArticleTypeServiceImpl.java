package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.model.AppArticle;
import com.center.medical.appadmin.bean.model.AppArticleType;
import com.center.medical.appadmin.bean.param.AppArticleTypeParam;
import com.center.medical.appadmin.bean.param.CMAppTypeParam;
import com.center.medical.appadmin.bean.vo.GetTypeListVo;
import com.center.medical.appadmin.dao.AppArticleTypeMapper;
import com.center.medical.appadmin.service.AppArticleService;
import com.center.medical.appadmin.service.AppArticleTypeService;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 小程序文章类型(AppArticleType)服务实现类
 *
 * @author ay
 * @since 2024-06-15 09:08:15
 */
@Slf4j
@Service("appArticleTypeService")
@RequiredArgsConstructor
public class AppArticleTypeServiceImpl extends ServiceImpl<AppArticleTypeMapper, AppArticleType> implements AppArticleTypeService {

    private final AppArticleTypeMapper appArticleTypeMapper;
    private final AppArticleService appArticleService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[小程序文章类型]列表
     *
     * @param page  分页参数
     * @param param AppArticleType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppArticleType> getPage(PageParam<AppArticleType> page, CMAppTypeParam param) {
        return appArticleTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppArticleType getInfoById(String id) {
        return appArticleTypeMapper.getInfoById(id);
    }

    /**
     * 添加或修改
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(AppArticleTypeParam param) {
        AppArticleType appArticleType = mapperFacade.map(param, AppArticleType.class);
        if (ObjectUtils.isNotEmpty(appArticleType.getId())){
            //更新
            AppArticleType appArticleType1 = appArticleTypeMapper.getInfoById(appArticleType.getId());
            if (ObjectUtils.isEmpty(appArticleType1)){
                throw new ServiceException("该id不存在!");
            }
            appArticleType.setModifydate(new Date());
            appArticleTypeMapper.updateById(appArticleType);
        }else {
            //添加
            appArticleType.setCreatedate(new Date());
            appArticleTypeMapper.insert(appArticleType);
        }
        return Boolean.TRUE;
    }


    /**
     * 删除分类
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteIds(List<String> ids) {
        //当前分类下没有套餐才能删除
        for (String id : ids) {
            long count = appArticleService.count(new LambdaQueryWrapper<AppArticle>()
                    .eq(AppArticle::getType, id).eq(AppArticle::getIsDelete,0));
            if (count>0){
                throw new ServiceException("请先删除对应的套餐后再删除分类!");
            }
        }
        //更新为已删除状态
        AppArticleType appArticleType = new AppArticleType();
        appArticleType.setIsDelete(1);
        appArticleType.setModifydate(new Date());
        appArticleTypeMapper.update(appArticleType,new LambdaQueryWrapper<AppArticleType>()
                .in(AppArticleType::getId,ids));

        return Boolean.TRUE;
    }

    /**
     * 获取类型
     * @param name
     * @return
     */
    @Override
    public List<GetTypeListVo> getTypeList(String name) {
        return appArticleTypeMapper.getTypeList(name);
    }
}

