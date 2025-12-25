package com.center.medical.sellcrm.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.model.Sellpact;
import com.center.medical.sellcrm.bean.model.Teamremind;
import com.center.medical.sellcrm.bean.param.SellpactLoseCustParam;
import com.center.medical.sellcrm.bean.param.SellpactParam;
import com.center.medical.sellcrm.bean.vo.SellpactVo;
import com.center.medical.sellcrm.dao.SellpactMapper;
import com.center.medical.sellcrm.dao.TeamremindMapper;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.sellcrm.service.SellpactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 销售合同维护表(Sellpact)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-22 15:00:11
 */
@Slf4j
@Service("sellpactService")
@RequiredArgsConstructor
public class SellpactServiceImpl extends ServiceImpl<SellpactMapper, Sellpact> implements SellpactService {

    private final SellpactMapper sellpactMapper;
    private final SellcustomerService sellcustomerService;
    private final TeamremindMapper teamremindMapper;
    private final Snowflake snowflake;

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param Sellpact查询参数
     * @return 分页数据
     */
    @Override

    public IPage<Sellpact> getPage(PageParam<Sellpact> page, SellpactParam param) {
        if (SecurityUtils.isLeader()) {
            //是领导,显示本分中心下的数据
            param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
        } else {
            //不是领导,显示销售自己名下的数据
            param.setXsjlid(SecurityUtils.getUserNo());
        }
        return sellpactMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Sellpact getInfoById(String id) {
        return sellpactMapper.getInfoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(Sellpact sellpact) {
        Date now = new Date();
        Sellcustomer customer = sellcustomerService.getInfoById(sellpact.getKhdwmcid());
        if (StringUtils.isBlank(sellpact.getId())) {
            //新增
            sellpact.setIsDelete(0);
            sellpact.setXsjlid(SecurityUtils.getUserNo());
            sellpact.setXsjl(SecurityUtils.getUsername());
            sellpact.setFzxid(SecurityUtils.getCId());
            sellpact.setCreatedate(now);
            sellpactMapper.insert(sellpact);
            if (StringUtils.isNotBlank(sellpact.getId())) {
                //保存成功,保存客户预检跟踪数据
                Teamremind teamRemind = new Teamremind();
                teamRemind.setKhdwmc(sellpact.getKhdwmc());
                teamRemind.setKhlxdh(sellpact.getLxdh());
                teamRemind.setSctjksrq(sellpact.getTjksrq());
                teamRemind.setClzt(0);
                teamRemind.setXsjlid(sellpact.getXsjlid());
                teamRemind.setFzxid(sellpact.getFzxid());
                teamRemind.setXshtwhid(sellpact.getId());
                teamRemind.setKhdwid(sellpact.getKhdwmcid());
                teamRemind.setKhdwlxr(customer.getKhdwlxr());
                teamRemind.setCreatedate(now);
                teamremindMapper.insert(teamRemind);
                log.info("teamRemind:{}", JSONUtil.toJsonStr(teamRemind));
            }

        } else {
            //编辑
            //进行编辑操作
            Sellpact spDb = sellpactMapper.getInfoById(sellpact.getId());
            if (Objects.nonNull(spDb)) {
                sellpact.setModifydate(now);
                sellpactMapper.updateById(sellpact);

                //更新预检跟踪记录
                List<Teamremind> teamreminds = teamremindMapper.selectList(new LambdaQueryWrapper<Teamremind>()
                        .eq(Teamremind::getXshtwhid, sellpact.getId()));
                for (Teamremind teamremind : teamreminds) {
                    teamremind.setKhdwmc(sellpact.getKhdwmc());
                    teamremind.setKhlxdh(sellpact.getLxdh());
                    teamremind.setSctjksrq(sellpact.getTjksrq());
                    teamremind.setModifydate(now);
                    teamremind.setKhdwid(sellpact.getKhdwmcid());
                    teamremind.setKhdwlxr(customer.getKhdwlxr());
                    teamremindMapper.updateById(teamremind);
                }

            } else {
                throw new ServiceException("更新失败，该合同不存在或者已删除！");
            }

        }
        //如果置为已来检 TODO ？这里是干啥用的
        if (Objects.nonNull(sellpact.getIsExamed()) && sellpact.getIsExamed() == 1) {
            List<Teamremind> trs = teamremindMapper.selectList(new LambdaQueryWrapper<Teamremind>()
                    .eq(Teamremind::getKhdwid, sellpact.getKhdwmcid()).orderByDesc(Teamremind::getSctjksrq));
            if (trs.size() > 0) {
                Teamremind tr = trs.get(0);
                tr.setIsExamed(1);
                teamremindMapper.updateById(tr);
            }
        }
        return Boolean.TRUE;
    }


    @Override
    public IPage<SellpactVo> getLoseCustPage(PageParam<SellpactVo> page, SellpactLoseCustParam param) {

        //个分中心看各分中心的 ，决策管理除外
        if (!SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            //获取当前登录用户分中心
            String fzxId = SecurityUtils.getCId();
            param.setFzxId(fzxId);
            param.setXsjlid(SecurityUtils.getUserNo());
        }

        //一年零三个月之前数据，认为是流失客户
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        calendar.add(Calendar.MONTH, -3);
        param.setSxDate(calendar.getTime());

        IPage<SellpactVo> pager = sellpactMapper.getList(page,param);

        return pager;
    }


    /**
     * 通过主键查找流失客户
     * @param id
     * @return
     */
    @Override
    public SellpactVo getloseCustInfoById(String id) {
        return sellpactMapper.getloseCustInfoById(id);
    }
}

