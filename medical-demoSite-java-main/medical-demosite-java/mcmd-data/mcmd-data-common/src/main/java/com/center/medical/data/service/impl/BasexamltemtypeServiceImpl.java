package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.Basexamltemtype;
import com.center.medical.data.bean.param.ExamItemTypePrama;
import com.center.medical.data.dao.BasexamltemMapper;
import com.center.medical.data.dao.BasexamltemtypeMapper;
import com.center.medical.data.service.BasexamltemtypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * JC检查项目类型表(Basexamltemtype)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:51
 */
@Slf4j
@RequiredArgsConstructor
@Service("basexamltemtypeService")
public class BasexamltemtypeServiceImpl extends ServiceImpl<BasexamltemtypeMapper, Basexamltemtype> implements BasexamltemtypeService {

    private final BasexamltemtypeMapper basexamltemtypeMapper;
    private final Snowflake snowflake;
    private final BasexamltemMapper basexamltemMapper;

    /**
     * 分页查询[JC检查项目类型表]列表
     *
     * @param page  分页参数
     * @param param Basexamltemtype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Basexamltemtype> getPage(PageParam<Basexamltemtype> page, ExamItemTypePrama param) {
        return basexamltemtypeMapper.getPage(page, param);
    }

    /**
     * 根据输入码查询查询JC检查项目类型列表
     *
     * @param param Basexamltemtype查询参数
     * @return 列表数据
     */
    public List<Basexamltemtype> getList(ExamItemTypePrama param) {
        return basexamltemtypeMapper.getList(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Basexamltemtype getInfoById(String id) {
        return basexamltemtypeMapper.getInfoById(id);
    }

    /**
     * 新增/更新操作
     *
     * @param basexamltemtype
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(Basexamltemtype basexamltemtype) {
        Date now = new Date();
        // TODO: wait 数据同步
        if (StringUtils.isBlank(basexamltemtype.getId())) {
            //新增
            // 判断名称是否重复
            Long count = basexamltemtypeMapper.selectCount(new LambdaQueryWrapper<Basexamltemtype>()
                    .eq(Basexamltemtype::getExamitemtypeName, basexamltemtype.getExamitemtypeName())
                    .eq(Basexamltemtype::getIsDelete, 0));
            if (count > 0) {
                // 重复
                throw new ServiceException("保存失败：" + basexamltemtype.getExamitemtypeName() + "检查项目类型已经重复");
            }
            // 保存检查项目
            basexamltemtype.setIsDelete(0);
            basexamltemtype.setId(String.valueOf(snowflake.nextId()));
            basexamltemtype.setCreatedate(now);
            basexamltemtypeMapper.insert(basexamltemtype);
        } else {
            //编辑
            Basexamltemtype etBd = basexamltemtypeMapper.selectOne(new LambdaQueryWrapper<Basexamltemtype>()
                    .eq(Basexamltemtype::getId, basexamltemtype.getId())
                    .eq(Basexamltemtype::getIsDelete, 0));
            if (Objects.isNull(etBd)) {
                // 不存在
                throw new ServiceException("更新失败：" + basexamltemtype.getExamitemtypeName() + "不存在，已经被删除");
            } else {
                // 判断名称是否重复
                if (basexamltemtypeMapper.selectCount(new LambdaQueryWrapper<Basexamltemtype>()
                        .ne(Basexamltemtype::getId, basexamltemtype.getId())
                        .eq(Basexamltemtype::getExamitemtypeName, basexamltemtype.getExamitemtypeName())
                        .eq(Basexamltemtype::getIsDelete, 0)) > 0) {
                    // 重复
                    throw new ServiceException("保存失败：" + basexamltemtype.getExamitemtypeName() + "检查项目类型已经重复");
                }
                // 保存检查项目
                basexamltemtype.setIsDelete(0);
                basexamltemtype.setVersion(Objects.isNull(etBd.getVersion()) ? 1 : etBd.getVersion() + 1);
                basexamltemtype.setModifydate(now);
                basexamltemtypeMapper.updateById(basexamltemtype);

            }
        }
        return Boolean.TRUE;
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<String> ids) {
        //检查项目类型是否已经被检查项目使用
        Long count = basexamltemMapper.selectCount(new LambdaQueryWrapper<Basexamltem>()
                .in(Basexamltem::getIdExamitemtype, ids)
                .eq(Basexamltem::getIsDelete, 0));
        if (count > 0) {
            //包含了已被使用中分类无法删除
            throw new ServiceException("删除失败：包含检查项目类型已经被检查项目关联，不能删除");
        }
        Basexamltemtype basexamltemtype = new Basexamltemtype();
        basexamltemtype.setIsDelete(1);
        basexamltemtype.setModifydate(new Date());
        basexamltemtypeMapper.update(basexamltemtype, new LambdaUpdateWrapper<Basexamltemtype>()
                .in(Basexamltemtype::getId, ids));
        return Boolean.TRUE;
    }

}

