package com.center.medical.framework.aspectj;

import cn.hutool.core.collection.CollectionUtil;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.annotation.PostScope;
import com.center.medical.common.core.domain.BaseEntity;
import com.center.medical.common.core.domain.entity.SysPost;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.core.domain.entity.SysUserPost;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.system.dao.SysPostMapper;
import com.center.medical.system.dao.SysUserPostMapper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 岗位权限数据过滤处理
 *
 * @author 路飞
 */
@Slf4j
@Aspect
@Component
public class PostScopeAspect {

    @Resource
    private SysPostMapper sysPostMapper;
    @Resource
    private SysUserPostMapper sysUserPostMapper;

    /**
     * 岗位权限过滤关键字
     */
    public static final String POST_SCOPE = "postScope";

    @Before("@annotation(controllerScope)")
    public void doBefore(JoinPoint point, PostScope controllerScope) throws Throwable {
        log.info("数据过滤处理doBefore：point[{}]过滤，alias：{}", point, controllerScope.alias());
        clearDataScope(point);
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员或者deal为0，则不过滤数据
            if (Objects.nonNull(currentUser) && !currentUser.isAdmin() && StringUtils.isNotBlank(controllerScope.alias())) {
                //获取当前用户关联的岗位
                List<SysUserPost> sysUserPosts = sysUserPostMapper.selectByUserId(currentUser.getUserId());
                //获取关联岗位的下属岗位的所有用户
                List<SysPost> sysPosts = sysPostMapper.selectPostByParentId(sysUserPosts.stream().map(SysUserPost::getPostId).collect(Collectors.toList()));
                //根据部门获取用户
                List<Long> userIds = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(sysPosts)) {
                    List<Long> postIds = sysPosts.stream().map(SysPost::getPostId).collect(Collectors.toList());
                    List<SysUserPost> sysUserPostList = sysUserPostMapper.selectByPostIds(postIds);
                    if (CollectionUtil.isNotEmpty(sysUserPostList)) {
                        userIds = sysUserPostList.stream().map(SysUserPost::getUserId).collect(Collectors.toList());
                    }
                }
                userIds.add(currentUser.getUserId());

                handleDataScope(point, controllerScope.alias(), userIds);
            }
        }

    }

    protected void handleDataScope(final JoinPoint joinPoint, String alias, List<Long> userIds) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser)) {
            dataScopeFilter(joinPoint, alias, userIds);
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param alias
     * @param userIds
     */
    public static void dataScopeFilter(JoinPoint joinPoint, String alias, List<Long> userIds) {
        StringBuilder sqlString = new StringBuilder();

        if (CollectionUtil.isNotEmpty(userIds)) {
//            log.info("数据过滤处理：根据用户编号[{}]过滤，用户id集合：{}", alias, userIds);
            sqlString.append(StringUtils.format(" AND {} IN {}{}{} ", alias, "(", Joiner.on(",").join(userIds), ")"));
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                Object params = args[i];
                if (StringUtils.isNotNull(params) && params instanceof BaseParam) {
                    BaseParam baseParam = (BaseParam) params;
                    baseParam.getParams().put(POST_SCOPE, sqlString);
//                    log.info("数据过滤处理：args[{}]，baseParam：{}", args, baseParam);
                    break;
                }
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.postScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(POST_SCOPE, "");
        }
    }

    public static void main(String[] args) {
        Object[] arr = new Object[]{"11"};
        System.out.println("1:" + arr.length);
        arr = Arrays.copyOf(arr, arr.length + 1);
        System.out.println("2:" + arr.length);
    }
}
