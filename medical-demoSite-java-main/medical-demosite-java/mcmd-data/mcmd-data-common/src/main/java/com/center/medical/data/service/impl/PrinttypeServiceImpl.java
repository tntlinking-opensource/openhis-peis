package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.bean.model.Printtype;
import com.center.medical.data.bean.param.PrinttypePrama;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.data.dao.PrinttypeMapper;
import com.center.medical.data.service.PrinttypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 销售打印分类设置(Printtype)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-18 09:11:15
 */
@Slf4j
@Service("printtypeService")
@RequiredArgsConstructor
public class PrinttypeServiceImpl extends ServiceImpl<PrinttypeMapper, Printtype> implements PrinttypeService {

    private final PrinttypeMapper printtypeMapper;
    private final Snowflake snowflake;
    private final ItemsMapper itemsMapper;

    /**
     * 分页查询[销售打印分类设置]列表
     *
     * @param page  分页参数
     * @param param Printtype查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Printtype> getPage(PageParam<Printtype> page, PrinttypePrama param) {
        return printtypeMapper.getPage(page, param);
    }

    /**
     * 根据输入码销售打印分类列表
     *
     * @param param 查询条件
     * @return 所有数据
     */
    public List<Printtype> getList(PrinttypePrama param) {
        return printtypeMapper.getList(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Printtype getInfoById(String id) {
        return printtypeMapper.getInfoById(id);
    }

    /**
     * 新增/更新操作
     *
     * @param printtype
     * @return
     */
    @Override
    public Boolean saOrUp(Printtype printtype) {
        Date now = new Date();
        if (StringUtils.isBlank(printtype.getId())) {
            //新增
            // 判断名称是否重复
            Long count = printtypeMapper.selectCount(new LambdaQueryWrapper<Printtype>()
                    .eq(Printtype::getPrintName, printtype.getPrintName())
                    .eq(Printtype::getShunxu, printtype.getShunxu())
                    .eq(Printtype::getSeq, printtype.getSeq())
                    .eq(Printtype::getIsDelete, 0));
            if (count > 0) {
                // 重复
                throw new ServiceException("保存失败！存在相同的名称或序号或顺序");
            }
            // 保存检查项目
            printtype.setIsDelete(0);
            printtype.setId(String.valueOf(snowflake.nextId()));
            printtype.setCreatedate(now);
            printtypeMapper.insert(printtype);
        } else {
            //编辑
            Printtype ptBd = printtypeMapper.selectOne(new LambdaQueryWrapper<Printtype>()
                    .eq(Printtype::getId, printtype.getId())
                    .eq(Printtype::getIsDelete, 0));
            if (Objects.isNull(ptBd)) {
                // 不存在
                throw new ServiceException("对象已删除，请刷新页面");
            } else {
                // 判断名称是否重复
                if (printtypeMapper.selectCount(new LambdaQueryWrapper<Printtype>()
                        .ne(Printtype::getId, printtype.getId())
                        .eq(Printtype::getPrintName, printtype.getPrintName())
                        .eq(Printtype::getShunxu, printtype.getShunxu())
                        .eq(Printtype::getSeq, printtype.getSeq())
                        .eq(Printtype::getIsDelete, 0)) > 0) {
                    // 重复
                    throw new ServiceException("更新失败：" + printtype.getPrintName() + "名称重复,或是序号或顺序重复");
                }
                // 保存检查项目
                printtype.setIsDelete(0);
                printtype.setModifydate(now);
                printtypeMapper.updateById(printtype);
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
    public Boolean delete(List<String> ids) {
        //分类是否已经被收费项目使用
        Long count = itemsMapper.selectCount(new LambdaQueryWrapper<Items>()
                .in(Items::getXsdyfl, ids)
                .eq(Items::getIsDelete, 0));
        if (count > 0) {
            //包含了已被使用中分类无法删除
            throw new ServiceException("删除失败：要删除的分类包含已经被收费项目关联，不能删除");
        }
        Printtype printtype = new Printtype();
        printtype.setIsDelete(1);
        printtype.setModifydate(new Date());
        printtypeMapper.update(printtype, new LambdaUpdateWrapper<Printtype>()
                .in(Printtype::getId, ids));
        return Boolean.TRUE;
    }

}

