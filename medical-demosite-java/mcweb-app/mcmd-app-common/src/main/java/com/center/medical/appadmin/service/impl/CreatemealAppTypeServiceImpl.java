package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.model.CreatemealApp;
import com.center.medical.appadmin.bean.model.CreatemealAppType;
import com.center.medical.appadmin.bean.param.CMAppTypeParam;
import com.center.medical.appadmin.bean.param.CMAppTypeSaOrUpParam;
import com.center.medical.appadmin.bean.vo.GetTypeListVo;
import com.center.medical.appadmin.dao.CreatemealAppTypeMapper;
import com.center.medical.appadmin.service.CreatemealAppService;
import com.center.medical.appadmin.service.CreatemealAppTypeService;
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
 * 小程序套餐类型(CreatemealAppType)服务实现类
 *
 * @author ay
 * @since 2024-06-12 17:31:42
 */
@Slf4j
@Service("createmealAppTypeService")
@RequiredArgsConstructor
public class CreatemealAppTypeServiceImpl extends ServiceImpl<CreatemealAppTypeMapper, CreatemealAppType> implements CreatemealAppTypeService {

    private final CreatemealAppTypeMapper createmealAppTypeMapper;
    private final CreatemealAppService createmealAppService;

    private final MapperFacade mapperFacade;

    /**
     * 分页查询[小程序套餐类型]列表
     *
     * @param page  分页参数
     * @param param CreatemealAppType查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CreatemealAppType> getPage(PageParam<CreatemealAppType> page, CMAppTypeParam param) {
        return createmealAppTypeMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CreatemealAppType getInfoById(String id) {
        return createmealAppTypeMapper.getInfoById(id);
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteIds(List<String> ids) {
        //当前分类下没有套餐才能删除
        for (String id : ids) {
            long count = createmealAppService.count(new LambdaQueryWrapper<CreatemealApp>()
                    .eq(CreatemealApp::getType, id).eq(CreatemealApp::getIsDelete,0));
            if (count>0){
                throw new ServiceException("请先删除对应的套餐后再删除分类!");
            }
        }
        //更新为已删除状态
        CreatemealAppType createmealAppType = new CreatemealAppType();
        createmealAppType.setIsDelete(1);
        createmealAppType.setModifydate(new Date());
        createmealAppTypeMapper.update(createmealAppType,new LambdaQueryWrapper<CreatemealAppType>()
                .in(CreatemealAppType::getId,ids));

        return Boolean.TRUE;
    }

    /**
     * 添加或修改
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(CMAppTypeSaOrUpParam param) {
        CreatemealAppType createmealAppType = mapperFacade.map(param, CreatemealAppType.class);
        if (ObjectUtils.isNotEmpty(createmealAppType.getId())){
            //更新
            CreatemealAppType createmealAppType1 = createmealAppTypeMapper.getInfoById(createmealAppType.getId());
            if (ObjectUtils.isEmpty(createmealAppType1)){
                throw new ServiceException("该id不存在!");
            }
            createmealAppType.setModifydate(new Date());
            createmealAppTypeMapper.updateById(createmealAppType);
        }else {
            //添加
            createmealAppType.setCreatedate(new Date());
            createmealAppTypeMapper.insert(createmealAppType);
        }
        return Boolean.TRUE;
    }

    /**
     * 获取类型
     * @param name
     * @return
     */
    @Override
    public List<GetTypeListVo> getTypeList(String name) {
        return createmealAppTypeMapper.getTypeList(name);
    }
}

