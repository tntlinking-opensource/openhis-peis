package com.center.medical.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.event.AddBranchEvent;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.utils.DateUtils;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.system.bean.vo.CenterListDataVo;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 分中心管理Service业务层处理
 *
 * @author 路飞
 * @date 2022-10-21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysBranchServiceImpl extends ServiceImpl<SysBranchMapper, SysBranch> implements ISysBranchService {
    private final SysBranchMapper sysBranchMapper;
    private final ISysConfigService iSysConfigService;
    private final Snowflake snowflake;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 查询分中心管理
     *
     * @param id 分中心管理主键
     * @return 分中心管理
     */
    @Override
    public SysBranch getDetailById(Integer id) {
        return sysBranchMapper.selectById(id);
    }

    /**
     * 根据分中心编码查询分中心信息
     *
     * @param branchId 分中心编码（对应原系统的分中心ID）
     * @return 分中心信息
     */
    @Override
    public SysBranch getByBranchId(String branchId) {
        SysBranch branch = RedisUtil.get(CacheConstants.BRANCH_LIST_KEY + branchId);
//        log.info("根据分中心编码查询分中心信息:{}", branch);
        if (Objects.isNull(branch)) {
            branch = sysBranchMapper.selectOne(new LambdaQueryWrapper<SysBranch>()
                    .eq(SysBranch::getBranchId, branchId).eq(SysBranch::getIsDelete, 0));
            if (Objects.nonNull(branch)) {
                RedisUtil.set(CacheConstants.BRANCH_LIST_KEY + branchId, branch, 0);
            }
        }
        return branch;
    }

    /**
     * 清除分中心缓存
     */
    @Override
    public void removeBranchCaching(String branchId) {
        if (StringUtils.isBlank(branchId)) {
            //清除所有缓存
            Set<String> keys = RedisUtil.keys(CacheConstants.BRANCH_LIST_KEY + "*");
            RedisUtil.delByKeys(keys);
        } else {
            RedisUtil.del(CacheConstants.BRANCH_LIST_KEY + branchId);
        }
    }

    /**
     * 查询分中心管理列表
     *
     * @param param 查询参数
     * @return 分中心管理
     */
    @Override
    public IPage<SysBranch> getList(PageParam<SysBranch> page, SysBranch param) {
        //只有超级管理员跟分中心管理员能进这个页面
        //中心管理员只能看自己默认分中心的
        Boolean admin = SecurityUtils.hasRole(RoleAuthName.ADMIN);
        if (!admin) {
            param.setBranchId(SecurityUtils.getCId());
        }
        return sysBranchMapper.getList(page, param);
    }

    /**
     * 查询新系统开放的分中心管理列表
     *
     * @param flag 是否先从缓存中获取：0否，1是
     * @return
     */
    @Override
    public List<SysBranch> getOpenList(Integer flag) {
        List<SysBranch> list = null;
        if (flag == 1) {
            list = RedisUtil.get(CacheConstants.BRANCH_LIST_OPENED_KEY);
        }
        if (CollectionUtil.isEmpty(list)) {
            list = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getIsShow, 1).eq(SysBranch::getIsDelete, 0));
            RedisUtil.set(CacheConstants.BRANCH_LIST_OPENED_KEY, list, 0);
        }
        return list;
    }


    /**
     * 查询分中心是否开放
     *
     * @param branchId 分中心ID
     * @return
     */
    @Override
    public Boolean isOpened(String branchId) {
        Boolean flag = RedisUtil.get(CacheConstants.BRANCH_OPENED_FLAG_KEY + ":" + branchId);
        //log.info("分中心是否开放1：{},{}", branchId, flag);
        if (Objects.isNull(flag)) {
            SysBranch branch = sysBranchMapper.selectOne(new LambdaQueryWrapper<SysBranch>()
                    .eq(SysBranch::getBranchId, branchId).eq(SysBranch::getIsShow, 1).eq(SysBranch::getIsDelete, 0));
            if (Objects.isNull(branch)) {
                flag = Boolean.FALSE;
            } else {
                flag = Boolean.TRUE;
            }
            RedisUtil.set(CacheConstants.BRANCH_OPENED_FLAG_KEY + ":" + branchId, flag, 0);

        }
        //log.info("分中心是否开放2：{},{}", branchId, flag);
        return flag;
    }

    /**
     * 新增分中心管理
     *
     * @param sysBranch 分中心管理
     * @return 结果
     */
    @Override
    public int insertNew(SysBranch sysBranch) {
        sysBranch.setCreateTime(DateUtils.getNowDate());
        String cid = String.valueOf(snowflake.nextId());
        sysBranch.setBranchId(cid);
        int i = sysBranchMapper.insert(sysBranch);
        //插入各分中心关联表
        applicationEventPublisher.publishEvent(new AddBranchEvent(cid));
        return i;
    }

    /**
     * 修改分中心管理
     *
     * @param sysBranch 分中心管理
     * @return 结果
     */
    @Override
    public int upById(SysBranch sysBranch) {
        sysBranch.setUpdateTime(DateUtils.getNowDate());
        this.removeBranchCaching(sysBranch.getBranchId());
        return sysBranchMapper.updateById(sysBranch);
    }

    /**
     * 批量删除分中心管理
     *
     * @param ids 需要删除的分中心管理主键
     * @return 结果
     */
    @Override
    public int delByIds(List<Integer> ids) {
        return sysBranchMapper.deleteBatchIds(ids);
    }

    /**
     * 删除分中心管理信息
     *
     * @param id 分中心管理主键
     * @return 结果
     */
    @Override
    public int delById(Integer id) {
        return sysBranchMapper.deleteById(id);
    }


    /**
     * 获取默认的部门 is_default = 1
     *
     * @return
     */
    @Override
    public SysBranch getDefaultBranch() {
        return sysBranchMapper.selectOne(new QueryWrapper<SysBranch>().eq("is_default", 1).eq("is_delete", 0));
    }

    /**
     * 根据当前用户获取分中心前缀
     *
     * @return String
     */
    @Override
    public String getBranchPrefix() {
        SysBranch branch = getDefaultBranch();
        return branch.getJm().toString().substring(0, 2);
    }

    /**
     * 根据分中心ID获取分中心的标识：前缀+version
     *
     * @return String
     */
    @Override
    public String getBranchFlag(String branchId) {
        // 分中心前缀
        String prefix = "";
        String version = "";
        if (StringUtils.isBlank(branchId)) {
            //当前用户所在分中心
            prefix = StringUtils.isBlank(ZhongkangConfig.getFzxjm()) ? getBranchPrefix() : ZhongkangConfig.getFzxjm();
//            log.info("默认分中心：{}、{}", prefix, ZhongkangConfig.getFzxjm());
            version = iSysConfigService.selectConfigByKey(Constants.VERSION_NO);
        } else {
            prefix = getByBranchId(branchId).getJm().toString().substring(0, 2);
            version = iSysConfigService.selectConfigByKey(Constants.VERSION_NO);
        }
        return prefix + version;
    }


    /**
     * 登录页面分中心数据
     *
     * @return
     */
    @Override
    public List<CenterListDataVo> centerListData() {
        return sysBranchMapper.centerListData();
    }

    /**
     * 初始化分中心信息到缓存中，如分中心简码（数据同步时用到）
     */
    @Override
    public void init() {
        //将分中心简码加载进redis缓存中
        List<SysBranch> list = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>()
                .select(SysBranch::getBranchId, SysBranch::getJm).eq(SysBranch::getIsDelete, 0));
        list.forEach(item -> {
//                log.info("分中心信息：{}", JSONUtil.toJsonStr(item));
            RedisUtil.set(CacheConstants.BRANCH_JM_KEY + item.getBranchId(), item.getJm(), -1);
        });
    }

    /**
     * 通过分中心名称查询
     *
     * @param name
     * @return
     */
    @Override
    public SysBranch getByBranchName(String name) {
        return sysBranchMapper.getByBranchName(name);
    }

    /**
     * 删除分中心开放标识缓存
     */
    @Override
    public void delBranchOpenedFlag() {
        //清除所有缓存
        Set<String> keys = RedisUtil.keys(CacheConstants.BRANCH_OPENED_FLAG_KEY + "*");
        RedisUtil.delByKeys(keys);
    }

    /**
     * 删除分中心开放同步标识缓存
     */
    @Override
    public void delBranchOpenedSyncFlag() {
        //清除所有缓存
        RedisUtil.del(CacheConstants.BRANCH_LIST_OPENED_KEY);
    }
}
