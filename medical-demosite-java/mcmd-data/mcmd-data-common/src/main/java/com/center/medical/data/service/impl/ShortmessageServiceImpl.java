package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.center.medical.bean.model.ShortMessageType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.dao.ShortMessageTypeMapper;
import com.center.medical.data.bean.model.Shortmessage;
import com.center.medical.data.dao.ShortmessageMapper;
import com.center.medical.data.service.ShortmessageService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * JC短信信息表(Shortmessage)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:17
 */
@Slf4j
@Service("shortmessageService")
@RequiredArgsConstructor
public class ShortmessageServiceImpl extends ServiceImpl<ShortmessageMapper, Shortmessage> implements ShortmessageService {

    private final ShortmessageMapper shortmessageMapper;
    private final ShortMessageTypeMapper shortMessageTypeMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[JC短信信息表]列表
     *
     * @param page  分页参数
     * @param param Shortmessage查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Shortmessage> getList(PageParam<Shortmessage> page, Shortmessage param) {
        return shortmessageMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Shortmessage getInfoById(String id) {
        return shortmessageMapper.getInfoById(id);
    }

    /**
     * 保存或修改
     * @param sm
     * @return
     */
    @Override
    public Boolean saveOrUpdateShortmessage(Shortmessage sm) {
        // 判断是否为空
        if(StringUtils.isBlank(sm.getId())) {
            //判断是否存在重复的短信名称,排除删除数据有相同名称的影响
            Shortmessage smNew = shortmessageMapper.selectOne(new QueryWrapper<Shortmessage>()
                    .eq("message_name", sm.getMessageName())
                    .and(wrapper -> wrapper.isNull("is_delete").or().eq("is_delete", 0)));
            if (ObjectUtils.isNotEmpty(smNew)) {
                throw new ServiceException("保存失败！"+sm.getMessageName() +" 名称重复");
            } else {
                //保存
                String param = sm.getParams();
                if(StringUtils.isNotEmpty(param)){
                    ShortMessageType type = shortMessageTypeMapper.selectOne(new QueryWrapper<ShortMessageType>().eq("id",sm.getMessageType()));
                    String type_param = type.getParams();
                    if(ObjectUtils.isEmpty(type_param)){
                        throw new ServiceException(type.getTypeName()+"类型不支持参数！");
                    }else{
                        List<String> array0 = Arrays.asList(param.split(","));
                        List<String>  array1 = Arrays.asList(type_param.split(","));
                        for(String str:array0){
                            if(!array1.contains(str)){
                                throw new ServiceException(type.getTypeName()+"类型不支持参数"+str+"！");
                            }
                        }
                    }
                }
                //设置isDelete字段为0
                sm.setIsDelete(0);
                sm.setCreatedate(new Date());
                sm.setId(String.valueOf(snowflake.nextId()));
                this.save(sm);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            Shortmessage smNew = shortmessageMapper.selectOne(new QueryWrapper<Shortmessage>().eq("id", sm.getId())
                    .eq("is_delete", 0));
            if(ObjectUtils.isNotEmpty(smNew)){
                // 判断名称是否重复
                Shortmessage smNews = shortmessageMapper.selectOne(new QueryWrapper<Shortmessage>().ne("id", sm.getId())
                       .eq("message_name", sm.getMessageName()).eq("is_delete", 0));
                if(ObjectUtils.isEmpty(smNews)){
                    String param = sm.getParams();
                    if(StringUtils.isNotEmpty(param)){
                        ShortMessageType type = shortMessageTypeMapper.selectOne(new QueryWrapper<ShortMessageType>().eq("id",sm.getMessageType()));
                        String type_param=type.getParams();
                        if(ObjectUtils.isEmpty(type_param)){
                            throw new ServiceException(type.getTypeName()+"类型不支持参数！");
                        }else{
                            List<String> array0=Arrays.asList(param.split(","));
                            List<String>  array1=Arrays.asList(type_param.split(","));
                            for(String str:array0){
                                if(!array1.contains(str)){
                                    throw new ServiceException(type.getTypeName()+"类型不支持参数"+str+"！");
                                }
                            }
                        }
                    }
                    // 更新实体类
                    sm.setModifydate(new Date());
                    BeanUtils.copyBeanProp(smNew, sm);
                    this.updateById(smNew);
                }else{
                    throw new ServiceException("更新失败："+sm.getMessageName() +"名称重复");
                }
            }else{
                throw new ServiceException("更新失败："+sm.getMessageName() +"名称已被删除");
            }
        }

        return Boolean.TRUE;
    }


    @Override
    public String removeShortmessage(String ids) {
        // 将获取的多个ID分解
        String id[]=ids.split(",");
        for(int i=0;i<id.length;i++){
            Shortmessage sm = shortmessageMapper.selectOne(new QueryWrapper<Shortmessage>().eq("id", id[i]).eq("is_delete", 0));
            if(ObjectUtils.isNotEmpty(sm)){
                //将isDelete设置为1，为删除
                sm.setIsDelete(1);
                sm.setModifydate(new Date());
                this.updateById(sm);
            }else{
                throw new ServiceException("删除失败");
            }
        }
        return "success";
    }
}

