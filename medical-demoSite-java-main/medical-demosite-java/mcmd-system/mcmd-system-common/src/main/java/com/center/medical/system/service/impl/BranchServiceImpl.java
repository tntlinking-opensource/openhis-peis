package com.center.medical.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.event.SaOrUpBranchEvent;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.param.BranchParam;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.vo.CenterOrgNameListVo;
import com.center.medical.system.dao.BranchMapper;
import com.center.medical.system.dao.SysUserBranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 分中心维护表(Branch)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 15:23:09
 */
@Slf4j
@Service("branchService")
@RequiredArgsConstructor
public class BranchServiceImpl extends ServiceImpl<BranchMapper, Branch> implements BranchService {

    private final BranchMapper branchMapper;
    private final Snowflake snowflake;
    private final ApplicationEventPublisher eventPublisher;
    private final ISysBranchService iSysBranchService;
    private final SysUserBranchMapper sysUserBranchMapper;
    private final SysUserMapper sysUserMapper;

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param Branch查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Branch> getList(PageParam<Branch> page, BranchParam param) {
        return branchMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id
     */
    @Override
    public Branch getInfoById(Integer id) {
        return branchMapper.getInfoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(Branch branch) {
        branch.setUpdateTime(new Date());
        branch.setJm(branch.getJm().toUpperCase());
        if (Objects.isNull(branch.getId())) {
            //新增
            Branch old = this.branchMapper.selectOne(new LambdaQueryWrapper<Branch>()
                    .eq(Branch::getJm, branch.getJm())
                    .eq(Branch::getIsDelete, 0));
            if (old != null) {
                throw new ServiceException("输入的简码：" + branch.getJm() + "在系统中已存在,请重新输入!");
            }
            Branch old1 = this.branchMapper.selectOne(new LambdaQueryWrapper<Branch>()
                    .eq(Branch::getFzx, branch.getFzx())
                    .eq(Branch::getIsDelete, 0));
            if (old1 != null) {
                throw new ServiceException("输入的名称：" + branch.getFzx() + "在系统中已存在,请重新输入!");
            }
            branch.setIsDelete(0);
            branch.setCreateTime(new Date());
            branch.setBranchId(String.valueOf(snowflake.nextId()));
            this.branchMapper.insert(branch);

            //生成相关的关联记录
            eventPublisher.publishEvent(new SaOrUpBranchEvent(branch.getBranchId(), branch.getJm(), branch.getPyjm(), branch.getFzx(), 1));
        } else {
            //修改
            this.branchMapper.updateById(branch);
            //如果填写了拼音简码，生成会员卡类型,每个分中心只能生成一个，生成后不能修改
            if (StringUtils.isNotEmpty(branch.getPyjm())) {
                //生成相关的关联记录
                eventPublisher.publishEvent(new SaOrUpBranchEvent(branch.getBranchId(), branch.getJm(), branch.getPyjm(), branch.getFzx(), 0));
            }
        }
        iSysBranchService.removeBranchCaching(branch.getBranchId());
        return Boolean.TRUE;
    }

    /**
     * 获取中心名字
     *
     * @return
     */
    @Override
    public String getCenterOrgName() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        Branch branch = branchMapper.selectOne(new QueryWrapper<Branch>().eq("branch_id", user.getCid()).eq("is_delete", 0));
        String centerOrgName = "";
        if (Objects.nonNull(branch)) {
            centerOrgName = branch.getCenterorgname();
        }
        return centerOrgName;
    }


    /**
     * 获取中心信息
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<Branch> getBranchData(PageParam<Branch> page, String key) {
        QueryWrapper<Branch> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.like("srm", key.trim().toUpperCase());
        }
        PageParam<Branch> iPage = branchMapper.selectPage(page, queryWrapper.eq("is_delete", 0));
        return iPage;
    }


    /**
     * 获取用户分中心及兼职分中心
     *
     * @return
     */
    @Override
    public List<String> getUserCid(String userNo) {
        List<Branch> userBranch = getUserBranch(userNo);
        if (CollectionUtil.isNotEmpty(userBranch)) {
            return userBranch.stream().map(Branch::getBranchId).collect(Collectors.toList());
        }
        return null;
    }


    /**
     * 获取用户分中心及兼职分中心
     *
     * @return
     */
    @Override
    public List<Branch> getUserBranch(String userNo) {
        if (StringUtils.isBlank(userNo)) {
            userNo = SecurityUtils.getUserNo();
        }
        SysUser user = sysUserMapper.getUserByNo(userNo);
        if (Objects.isNull(user)) {
            return null;
        }
        //获取用户当前分中心及兼职分中心
        String cid = user.getCid();
        List<String> sysUserBranch = sysUserBranchMapper.getByUserNo(userNo);
        if (StringUtils.isNotBlank(cid)) {
            if (!CollectionUtil.contains(sysUserBranch, cid))
                sysUserBranch.add(cid);
        }

        return branchMapper.getListBranch(sysUserBranch);
    }


    /**
     * 获取当前账号下所有所属组织
     * @param userNo
     * @return
     */
    @Override
    public List<CenterOrgNameListVo> getUserCenterOrgNameList(String userNo) {
        return branchMapper.getUserCenterOrgNameList(userNo);
    }
}

