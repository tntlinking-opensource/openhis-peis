package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.data.bean.model.ZyVsSummary;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.data.dao.ZyVsSummaryMapper;
import com.center.medical.data.bean.model.OccupationDiseast;
import com.center.medical.data.dao.OccupationDiseastMapper;
import com.center.medical.data.service.OccupationDiseastService;
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
 * JC职业病名称(OccupationDiseast)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:19
 */
@Slf4j
@Service("occupationDiseastService")
@RequiredArgsConstructor
public class OccupationDiseastServiceImpl extends ServiceImpl<OccupationDiseastMapper, OccupationDiseast> implements OccupationDiseastService {

    private final OccupationDiseastMapper occupationDiseastMapper;
    private final Snowflake snowflake;
    private final ZyVsSummaryMapper zyVsSummaryMapper;

    /**
     * 分页查询[JC职业病名称]列表
     *
     * @param page  分页参数
     * @param param OccupationDiseast查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OccupationDiseast> getList(PageParam<OccupationDiseast> page, OccupationDiseast param) {
        return occupationDiseastMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public OccupationDiseast getInfoById(String id) {
        return occupationDiseastMapper.getInfoById(id);
    }

    /**
     * 保存或更新
     * @param odis
     * @return
     */
    @Override
    public String saveOrUpdateOccu(OccupationDiseast odis) {
        //获取当前用户名
        String name = SecurityUtils.getLoginUser().getUsername();
        // 判断是否为空
        if(StringUtils.isBlank(odis.getId())) {
            //判断是否存在重复的职业病名称,排除删除数据有相同名称的影响
            OccupationDiseast harmNew = occupationDiseastMapper.selectOne(new QueryWrapper<OccupationDiseast>()
                    .eq("occupation_diseast", odis.getOccupationDiseast())
                    .and(wrapper -> wrapper.isNull("is_delete").or().eq("is_delete", 0)));
            if (ObjectUtils.isNotEmpty(harmNew)) {
                throw new ServiceException( "保存失败！存在相同的名称");
            }
            else {
                //保存
                //当前登录用户
                odis.setDbUser(name);
                //设置isDelete字段为0
                odis.setIsDelete(0);
                odis.setId(String.valueOf(snowflake.nextId()));
                odis.setCreatedate(new Date());
                this.save(odis);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删、ID是否重复
            OccupationDiseast harmNew = occupationDiseastMapper.selectOne(new QueryWrapper<OccupationDiseast>().eq("id", odis.getId())
                    .eq("is_delete", 0));
            if(ObjectUtils.isNotEmpty(harmNew)){
                // 判断名称是否重复
                OccupationDiseast harmNews = occupationDiseastMapper.selectOne(new QueryWrapper<OccupationDiseast>().ne("id", odis.getId())
                        .eq("occupation_diseast", odis.getOccupationDiseast()).eq("is_delete", 0));
                if(ObjectUtils.isEmpty(harmNews)){
                    // 更新实体类
                    //当前登录用户
                    odis.setDbUser(name);
                    odis.setModifydate(new Date());
                    BeanUtils.copyBeanProp(harmNew,odis);
                    this.updateById(harmNew);
                }else{
                    throw new ServiceException("更新失败："+odis.getOccupationDiseast() +"名称重复");
                }
            }else{
                throw new ServiceException("更新失败：对象不存在，请刷新页面");
            }
        }
        return "success";
    }


    @Override
    public String removeOccupa(String ids) {
        String flg = "success";
        String id[] = ids.split(",");
        for(int i=0;i<id.length;i++){
            OccupationDiseast zyoc = occupationDiseastMapper.selectOne(new QueryWrapper<OccupationDiseast>().eq("id", id[i]).eq("is_delete", 0));
            if(ObjectUtils.isNotEmpty(zyoc)){
                List<ZyVsSummary> zvs = zyVsSummaryMapper.selectList(new QueryWrapper<ZyVsSummary>().eq("occupation_diseast", zyoc.getId()));
                if(CollectionUtils.isNotEmpty(zvs)){
                    throw new ServiceException("无法删除！"+zyoc.getOccupationDiseast()+"在'职业体检处理意见'处已被占用");
                }else{
                    //将isDelete设置为1，为删除
                    zyoc.setIsDelete(1);
                    zyoc.setModifydate(new Date());
                    this.updateById(zyoc);
                }
            }
        }
        return flg;
    }


    @Override
    public IPage<OccupationDiseast> getZybData(PageParam<OccupationDiseast> page, String inputCode) {
       //设置搜索条件
        QueryWrapper<OccupationDiseast> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(inputCode)) {
            queryWrapper.like("input_code", inputCode.toUpperCase());
        }
        queryWrapper.eq("is_delete", 0);
        PageParam<OccupationDiseast> pageParam = occupationDiseastMapper.selectPage(page, queryWrapper);
        return pageParam;

    }
}

