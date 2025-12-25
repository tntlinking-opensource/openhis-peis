package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.data.bean.model.StencilMaintain;
import com.center.medical.data.dao.StencilMaintainMapper;
import com.center.medical.data.service.StencilMaintainService;
import com.center.medical.system.dao.SysDeptMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)(StencilMaintain)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:06
 */
@Slf4j
@Service("stencilMaintainService")
@RequiredArgsConstructor
public class StencilMaintainServiceImpl extends ServiceImpl<StencilMaintainMapper, StencilMaintain> implements StencilMaintainService {

    private final StencilMaintainMapper stencilMaintainMapper;
    private final SysDeptMapper sysDeptMapper;
    private final Snowflake snowflake;
    /**
     * 分页查询[模板维护表(用于保存科室的模板（个检用）、团检的模板、对比模板)]列表
     *
     * @param page  分页参数
     * @param param StencilMaintain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<StencilMaintain> getList(PageParam<StencilMaintain> page, StencilMaintain param) {
        return stencilMaintainMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public StencilMaintain getInfoById(String id) {
        return stencilMaintainMapper.getInfoById(id);
    }


    @Override
    public String saveOrUpdateStencilMaintain(StencilMaintain stencilMaintain) {
        if (ObjectUtils.isEmpty(stencilMaintain)) {
            return "数据为空";
        }
        Integer modelType = stencilMaintain.getModelType();
        String returnStr = null;
        if (modelType == 0) {// 科室(个检)
            returnStr = this.edit0(stencilMaintain);
        } else if (modelType == 1) {// 团检
            returnStr = this.edit1(stencilMaintain);
        } else if (modelType == 2) {// 对比
            returnStr = this.edit2(stencilMaintain);
        } else if (modelType == 3) {//单科室头模板
            returnStr = this.edit3(stencilMaintain);
        }
        return returnStr;
    }


    /**
     * 对比模板维护
     *
     */
    private String edit3(StencilMaintain stencilMaintain) {
        try {
            String id = stencilMaintain.getId();
            if (!StringUtils.isEmpty(id)) {// 有ID说明是编辑
                StencilMaintain old = stencilMaintainMapper.getInfoById(id);
                BeanUtils.copyBeanProp(old,stencilMaintain);
                this.updateById(old);
            } else {// 没有ID说明是新增
                if (stencilMaintain.getIsDefault() == 0) {// 如果现在是默认
                    StencilMaintain old = stencilMaintainMapper.selectOne(new QueryWrapper<StencilMaintain>().eq("model_type", 3)
                            .eq("is_default", 0));
                    if (old != null) {
                        old.setIsDefault(1);
                        this.updateById(old);
                    }
                }
                String stencilMaintainId = String.valueOf(snowflake.nextId());
                stencilMaintain.setId(stencilMaintainId);
                stencilMaintain.setCreatedate(new Date());
                this.save(stencilMaintain);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "维护模板失败";
        }
        return "success";
    }

    /**
     * 对比模板维护
     *

     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    protected String edit2(StencilMaintain stencilMaintain) {
        try {
            String stencilMaintainId = String.valueOf(snowflake.nextId());
            String id = stencilMaintain.getId();
            if (!StringUtils.isEmpty(id) && !"null".equals(id)) {// 有ID说明是更新
                StencilMaintain old = stencilMaintainMapper.getInfoById(id);
                BeanUtils.copyBeanProp(old,stencilMaintain);
                old.setModifydate(new Date());
                this.updateById(old);
            } else {// 无ID说明是新增
                Integer isDefault = stencilMaintain.getIsDefault();
                if (isDefault == 0) {// 新增的为默认的
                    StencilMaintain old = stencilMaintainMapper.selectOne(new QueryWrapper<StencilMaintain>().eq("model_type",stencilMaintain.getModelType())
                            .eq("is_default", stencilMaintain.getIsDefault()).eq("suitable_type", stencilMaintain.getSuitableType()));
                    if (old != null) {
                        old.setIsDefault(1);
                        this.updateById(old);
                    }
                    stencilMaintain.setId(stencilMaintainId);
                    stencilMaintain.setCreatedate(new Date());
                    this.save(stencilMaintain);
                } else {
                    stencilMaintain.setId(stencilMaintainId);
                    stencilMaintain.setCreatedate(new Date());
                    this.save(stencilMaintain);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "维护失败";
        }
        return "success";
    }

    /**
     * 团检模板维护
     *
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    protected String edit1(StencilMaintain stencilMaintain) {
        try {
            String stencilMaintainId = String.valueOf(snowflake.nextId());
            String id = stencilMaintain.getId();
            if (!StringUtils.isEmpty(id) && !"null".equals(id)) {// 有ID说明是更新
                StencilMaintain old = stencilMaintainMapper.getInfoById(id);
                BeanUtils.copyBeanProp(old,stencilMaintain);
                old.setModifydate(new Date());
                this.updateById(old);
            } else {// 无ID说明是新增
                Integer isDefault = stencilMaintain.getIsDefault();
                if (isDefault == 0) {// 新增的为默认的
                    StencilMaintain old = stencilMaintainMapper.selectOne(new QueryWrapper<StencilMaintain>()
                            .eq("model_type", stencilMaintain.getModelType()).eq("is_default", stencilMaintain.getIsDefault()).
                            eq("suitable_type", stencilMaintain.getSuitableType()));
                    if (old != null) {
                        old.setIsDefault(1);
                        stencilMaintainMapper.updateById(old);
                    }
                    stencilMaintain.setId(stencilMaintainId);
                    stencilMaintain.setCreatedate(new Date());
                    this.save(stencilMaintain);
                } else {
                    stencilMaintain.setId(stencilMaintainId);
                    stencilMaintain.setCreatedate(new Date());
                    this.save(stencilMaintain);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "维护失败";
        }
        return "success";
    }

    /**
     * 个检模板维护
     *
     * @Title: edit0
     * @param stencilMaintain
     * @return String
     * @author YINZL
     * @since 2016年11月18日 V 1.0
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    protected String edit0(StencilMaintain stencilMaintain) {
        try {
            String stencilMaintainId = String.valueOf(snowflake.nextId());
            String id = stencilMaintain.getId();
            Integer isHead = stencilMaintain.getIsHead();
            if (!StringUtils.isEmpty(id) && !"null".equals(id)) {// 有ID说明是更新
                StencilMaintain old = stencilMaintainMapper.getInfoById(id);
                StencilMaintain old2 = null;
                if (isHead != 0) {
                    if (old.getIsDefault() != stencilMaintain.getIsDefault()) {// 如果是否默认操作不一致，则需要更改科室的字段内容
                        SysDept dep = null;
                        String depId = stencilMaintain.getDepId();
                        if (!StringUtils.isEmpty(depId)
                                && !"null".equals(depId)) {
                            dep = sysDeptMapper.selectDeptById(Long.valueOf(depId));
                            if (dep != null) {
                                if (stencilMaintain.getIsDefault() == 0) {
                                    if (stencilMaintain.getSuitableType() == 0) {// 健康科室个检默认
                                        dep.setReportPathHealth(stencilMaintain
                                                .getModelUrls());

                                    } else {// 职业科室个检默认
                                        dep.setReportPathDisease(stencilMaintain
                                                .getModelUrls());
                                    }
                                    old2 = stencilMaintainMapper.selectOne(new QueryWrapper<StencilMaintain>().eq("dep_id", stencilMaintain.getDepId())
                                            .eq("suitable_type", stencilMaintain.getSuitableType()).eq("is_default", 0));// 在本次更新之前的默认的，若有，就改成非默认
                                    if (old2 != null) {
                                        old2.setIsDefault(1);
                                    }
                                } else {
                                    if (stencilMaintain.getSuitableType() == 0) {// 健康科室个检非默认
                                        dep.setReportPathHealth("");
                                    } else {// 职业科室个检非默认
                                        dep.setReportPathDisease("");
                                    }
                                }
                            }
                            sysDeptMapper.updateDept(dep);
                        }
                    }
                } else {
                    Integer isDefault = stencilMaintain.getIsDefault();
                    if (isDefault == 0) {// 默认
                        old2 = stencilMaintainMapper.selectOne(new QueryWrapper<StencilMaintain>().eq("is_head", 0)
                                .eq("suitable_type", stencilMaintain.getSuitableType()).eq("is_default", 0));
                        if (old2 != null) {
                            try {
                                old2.setIsDefault(1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                BeanUtils.copyBeanProp(old,stencilMaintain);
                if (old != null && old2 != null) {
                    if (old.getId() != old2.getId()) {
                        this.updateById(old2);
                    }
                }
                this.updateById(old);
            } else {// 无ID说明是新增
                Integer isDefault = stencilMaintain.getIsDefault();
                if (isHead != 0) {// 如果不是头模板
                    if (isDefault == 0) {// 新增的为默认科室模板
                        SysDept dep = null;
                        String depId = stencilMaintain.getDepId();
                        if (!StringUtils.isEmpty(depId)
                                && !"null".equals(depId)) {
                            dep = sysDeptMapper.selectDeptById(Long.valueOf(depId));
                            if (dep != null) {
                                Integer suitabletype = stencilMaintain
                                        .getSuitableType();
                                if (suitabletype != null) {
                                    if (suitabletype == 0) {// 如果本次增加的是健康的
                                        dep.setReportPathHealth(stencilMaintain.getModelUrls());
                                    } else {
                                        dep.setReportPathDisease(stencilMaintain.getModelUrls());
                                    }
                                }
                            }
                            StencilMaintain old = stencilMaintainMapper.selectOne(new QueryWrapper<StencilMaintain>()
                                    .eq("dep_id", depId).eq("suitable_type", stencilMaintain.getSuitableType()).eq("is_default", 0));
                            if (old != null) {
                                old.setIsDefault(1);
                                stencilMaintainMapper.updateById(old);
                            }
                            sysDeptMapper.updateDept(dep);
                        }

                    }
                    stencilMaintain.setCreatedate(new Date());
                    stencilMaintain.setId(stencilMaintainId);
                    this.save(stencilMaintain);
                } else {// 如果是头模板
                    if (isDefault == 0) {// 默认头模板
                        StencilMaintain old = stencilMaintainMapper.selectOne(new QueryWrapper<StencilMaintain>()
                                .eq("is_default", 0).eq("is_head", 0).eq("suitable_type", stencilMaintain.getSuitableType()));
                        if (old != null) {
                            old.setIsDefault(1);
                            this.updateById(old);
                        }
                        stencilMaintain.setCreatedate(new Date());
                        stencilMaintain.setId(stencilMaintainId);
                        this.save(stencilMaintain);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "维护失败";
        }
        return "success";
    }
}

