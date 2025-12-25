package com.center.medical.data.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZySummary;
import com.center.medical.data.bean.model.ZyVsSummary;
import com.center.medical.data.dao.ZySummaryMapper;
import com.center.medical.data.dao.ZyVsSummaryMapper;
import com.center.medical.data.service.ZySummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * JC职业病检查结论(ZySummary)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:35
 */
@Slf4j
@Service("zySummaryService")
@RequiredArgsConstructor
public class ZySummaryServiceImpl extends ServiceImpl<ZySummaryMapper, ZySummary> implements ZySummaryService {

    private final ZySummaryMapper zySummaryMapper;
    private final ZyVsSummaryMapper zyVsSummaryMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[JC职业病检查结论]列表
     *
     * @param page  分页参数
     * @param param ZySummary查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ZySummary> getList(PageParam<ZySummary> page, ZySummary param) {
        return zySummaryMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public ZySummary getInfoById(String id) {
        return zySummaryMapper.getInfoById(id);
    }

    /**
     * 保存或修改
     *
     * @param zy
     * @return
     */
    @Override
    public String saveOrUpdateZySummary(ZySummary zy) {
        // 判断是否为空
        if (StringUtils.isBlank(zy.getId())) {
            //判断是否存在重复的职业名称,排除删除数据有相同名称的影响
            ZySummary zyNew = zySummaryMapper.selectOne(new QueryWrapper<ZySummary>()
                    .eq("occupation_summary", zy.getOccupationSummary())
                    .and(wrapper -> wrapper.isNull("is_delete").or().eq("is_delete", 0)));
            if (ObjectUtils.isNotEmpty(zyNew)) {
                throw new ServiceException("保存失败！存在相同的名称");
            } else {
                //保存
                //设置isDelete字段为0
                zy.setIsDelete(0);
                zy.setId(String.valueOf(snowflake.nextId()));
                zy.setCreatedate(new Date());
                this.save(zy);
            }
        } else {
            // 判断是否存在重复数据,排除删除数据的影响
            // 判断是否假删
            ZySummary zyNew = zySummaryMapper.getZySummaryById(zy.getId());
            if (ObjectUtils.isNotEmpty(zyNew)) {
                // 判断名称是否重复
                ZySummary zyNews = zySummaryMapper.selectOne(new QueryWrapper<ZySummary>().ne("id", zy.getId())
                        .eq("occupation_summary", zy.getOccupationSummary()).eq("is_delete", 0));
                if (zyNews == null) {
                    // 更新实体类
                    BeanUtils.copyBeanProp(zyNew, zy);
                    this.updateById(zyNew);
                } else {
                    throw new ServiceException("更新失败：" + zy.getOccupationSummary() + " 名称重复");
                }
            } else {
                throw new ServiceException("对象已经删除，请刷新页面");
            }
        }
        return "success";
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String removeZySummary(String ids) {
        String id[] = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            ZySummary zyoc = zySummaryMapper.getZySummaryById(id[i]);
            if (ObjectUtils.isNotEmpty(zyoc)) {
                Long zvs = zyVsSummaryMapper.selectCount(new QueryWrapper<ZyVsSummary>()
                        .eq("occupation_summary", zyoc.getId()).eq("is_delete", 0));
                if (zvs > 0) {
                    throw new ServiceException("无法删除！【" + zyoc.getOccupationSummary() + "】在'职业体检处理意见'处已被占用");
                } else {
                    //将isDelete设置为1，为删除
                    zyoc.setIsDelete(1);
                    zyoc.setModifydate(new Date());
                    this.updateById(zyoc);
                }
            }
        }
        return "success";
    }

    /**
     * 获取检查结论下拉
     *
     * @param page
     * @param inputCode
     * @return
     */
    @Override
    public IPage<ZySummary> getJcjlData(PageParam<ZySummary> page, String inputCode) {
        QueryWrapper<ZySummary> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(inputCode)) {
            queryWrapper.like("input_code", inputCode.toUpperCase());
        }
        queryWrapper.eq("is_delete", 0);
        PageParam<ZySummary> zyVsSummaryPageParam = zySummaryMapper.selectPage(page, queryWrapper);
        return zyVsSummaryPageParam;
    }
}

