package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Customerfollow;
import com.center.medical.sellcrm.bean.param.CustomerfollowPram;
import com.center.medical.sellcrm.dao.CustomerfollowMapper;
import com.center.medical.sellcrm.service.CustomerfollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 客户跟踪表(Customerfollow)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-21 09:02:48
 */
@Slf4j
@Service("customerfollowService")
@RequiredArgsConstructor
public class CustomerfollowServiceImpl extends ServiceImpl<CustomerfollowMapper, Customerfollow> implements CustomerfollowService {

    private final CustomerfollowMapper customerfollowMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询阶段跟踪列表
     *
     * @param page  分页参数
     * @param param Customerfollow查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Customerfollow> getPage(PageParam<Customerfollow> page, CustomerfollowPram param) {
        //决策管理权限
        boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!greatLeader) {
            Boolean isLeader = SecurityUtils.isLeader();
            if (!isLeader) {
                //不是领导,查询本销售自己名下的信息
                param.setXsjlid(SecurityUtils.getUserNo());
            } else {
                //是领导,查询本分中心下的信息
                param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
            }
        }
        IPage<Customerfollow> iPage = customerfollowMapper.getPage(page, param);
        List<Customerfollow> records = iPage.getRecords();
        for (Customerfollow record : records) {
            Date ksrq = record.getKsrq();
            //结束时间为空就设为今天
            Date jsrq = record.getJsrq() == null? new Date():record.getJsrq();
            long ks = ksrq.getTime();
            long js = jsrq.getTime();
            double between_days=(double)(js-ks)/(1000*3600*24);
            record.setJxsj(Math.ceil(between_days));
        }
        iPage.setRecords(records);
        return iPage;
    }

    /**
     * 获取指定客户跟踪记录
     *
     * @param cumId 客户ID
     * @return 所有数据
     */
    @Override
    public List<Customerfollow> getListByCumId(String cumId) {
        CustomerfollowPram param = new CustomerfollowPram();
        param.setKhdwmcid(cumId);
        boolean greatLeader = SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE);
        if (!greatLeader) {
            Boolean isLeader = SecurityUtils.isLeader();
            if (!isLeader) {
                //不是领导,查询本销售自己名下的信息
                param.setXsjlid(SecurityUtils.getUserNo());
            } else {
                //是领导,查询本分中心下的信息
                param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
            }
        }
        List<Customerfollow> records = customerfollowMapper.getListByCumId(param);
        for (Customerfollow record : records) {
            Date ksrq = record.getKsrq();
            //结束时间为空就设为今天
            Date jsrq = record.getJsrq() == null? new Date():record.getJsrq();
            long ks = ksrq.getTime();
            long js = jsrq.getTime();
            double between_days=(double)(js-ks)/(1000*3600*24);
            record.setJxsj(Math.ceil(between_days));
        }
        return records;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Customerfollow getInfoById(String id) {
        return customerfollowMapper.getInfoById(id);
    }

    /**
     * 新增/编辑操作
     *
     * @param customerfollow
     * @return
     */
    @Override
    public Boolean saOrUp(Customerfollow customerfollow) {
        Date now = new Date();
        Boolean js = customerfollow.getJdjs();//阶段结束

        if (js) {
            customerfollow.setJsrq(now);
        }
        customerfollow.setXsjlid(SecurityUtils.getUserNo());
        customerfollow.setFzxid(SecurityUtils.getCId());
        customerfollow.setXsjl(SecurityUtils.getLoginUser().getUser().getNickName());
        Integer gjjd = customerfollow.getGjjd();//跟进阶段
        //获取所有客户跟进记录
        List<Customerfollow> cfs = customerfollowMapper.selectList(new LambdaQueryWrapper<Customerfollow>()
                .eq(Customerfollow::getKhdwmcid, customerfollow.getKhdwmcid())
                .orderByDesc(Customerfollow::getGjrq));

        //如果阶段结束，修改本阶段所有结束时间。 如果没勾选阶段结束，阶段与上一次阶段不同，修改上一阶段所有结束时间。
        for (Customerfollow cf : cfs) {
            if (cf.getJsrq() != null) {//遇到结束的跳出循环
                break;
            }
            if (Objects.equals(cf.getGjjd(), gjjd)) {//阶段相同且未结束
                if (js) {
                    cf.setJsrq(now);
                    customerfollowMapper.updateById(cf);
                }
            } else {//阶段不同且未结束
                cf.setJsrq(now);
                customerfollowMapper.updateById(cf);
            }
        }
        //新增跟进阶段记录
        customerfollow.setCreatedate(now);
        customerfollow.setId(String.valueOf(snowflake.nextId()));
        customerfollowMapper.insert(customerfollow);
        return Boolean.TRUE;
    }

}

