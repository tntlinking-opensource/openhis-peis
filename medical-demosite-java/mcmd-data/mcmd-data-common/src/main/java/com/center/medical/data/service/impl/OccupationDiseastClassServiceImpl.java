package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.data.bean.model.OccupationDiseast;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.data.dao.OccupationDiseastMapper;
import com.center.medical.data.bean.model.OccupationDiseastClass;
import com.center.medical.data.dao.OccupationDiseastClassMapper;
import com.center.medical.data.service.OccupationDiseastClassService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * JC职业病种分类(OccupationDiseastClass)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:16
 */
@Slf4j
@Service("occupationDiseastClassService")
@RequiredArgsConstructor
public class OccupationDiseastClassServiceImpl extends ServiceImpl<OccupationDiseastClassMapper, OccupationDiseastClass> implements OccupationDiseastClassService {

    private final OccupationDiseastClassMapper occupationDiseastClassMapper;
    private final OccupationDiseastMapper occupationDiseastMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[JC职业病种分类]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseastClass查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OccupationDiseastClass> getList(PageParam<OccupationDiseastClass> page, OccupationDiseastClass param) {
        return occupationDiseastClassMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public OccupationDiseastClass getInfoById(String id) {
        return occupationDiseastClassMapper.getInfoById(id);
    }

    /**
     * 保存或更新
     * @param odis
     * @return
     */
    @Override
    public String saveOrUpdateOccu(OccupationDiseastClass odis) {
        //获取当前用户
        String name = SecurityUtils.getLoginUser().getUsername();
        // 判断是否为空
        if(StringUtils.isBlank(odis.getId())) {
            //判断是否存在重复的职业名称,排除删除数据有相同名称的影响
            OccupationDiseastClass harmNew = occupationDiseastClassMapper.selectOne(new QueryWrapper<OccupationDiseastClass>()
                    .eq("occupation_diseast_class_name", odis.getOccupationDiseastClassName())
                    .and(wrapper -> wrapper.isNull("is_delete").or().eq("is_delete", 0)));
            if (ObjectUtils.isNotEmpty(harmNew)) {
                throw new ServiceException( "保存失败！存在相同的名称");
            }
            else {
                //保存
                //设置isDelete字段为0
                odis.setDbUser(name);
                odis.setIsDelete(0);
                odis.setCreatedate(new Date());
                odis.setId(String.valueOf(snowflake.nextId()));
                this.save(odis);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            OccupationDiseastClass harmNew = occupationDiseastClassMapper.selectOne(new QueryWrapper<OccupationDiseastClass>().eq("id", odis.getId())
                    .eq("is_delete", 0));
            if(ObjectUtils.isNotEmpty(harmNew)){
                // 判断名称是否重复
                OccupationDiseastClass harmNews = occupationDiseastClassMapper.selectOne(new QueryWrapper<OccupationDiseastClass>().ne("id", odis.getId())
                        .eq("occupation_diseast_class_name", odis.getOccupationDiseastClassName()).eq("is_delete", 0));
                if(ObjectUtils.isEmpty(harmNews)){
                    // 更新实体类
                    odis.setDbUser(name);
                    odis.setModifydate(new Date());
                    BeanUtils.copyBeanProp(harmNew,odis);
                    this.updateById(harmNew);
                }else{
                    throw new ServiceException("更新失败："+odis.getOccupationDiseastClassName() +"名称重复");
                }
            }else{
                throw new ServiceException("对象已删除，请刷新页面");
            }
        }

        return "success";
    }


    @Override
    public String removeOccupa(String ids) {
        String id[] = ids.split(",");
        for(int i=0;i<id.length;i++){
            OccupationDiseastClass odc = occupationDiseastClassMapper.selectOne(new QueryWrapper<OccupationDiseastClass>()
                    .eq("id", id[i]).eq("is_delete", 0));
            if(ObjectUtils.isNotEmpty(odc)){
                //判断职业病名称分类是否被引用
                List<OccupationDiseast> od = occupationDiseastMapper.selectList(new QueryWrapper<OccupationDiseast>().eq("occupation_diseast_class", odc.getId()));
                if(CollectionUtils.isNotEmpty(od)) {
                    throw new ServiceException("无法删除！【"+odc.getOccupationDiseastClassName()+"】在别处已被占用");
                }else{
                    //将isDelete设置为1，为删除
                    odc.setIsDelete(1);
                    odc.setModifydate(new Date());
                    this.updateById(odc);
                }
            }else{
                throw new ServiceException("删除失败");
            }
        }
        return "success";
    }


}

