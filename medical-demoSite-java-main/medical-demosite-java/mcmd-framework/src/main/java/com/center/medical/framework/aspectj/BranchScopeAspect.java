package com.center.medical.framework.aspectj;

import cn.hutool.json.JSONUtil;
import com.center.medical.common.annotation.BranchScope;
import com.center.medical.common.core.domain.BaseEntity;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 分中心权限数据过滤处理
 *
 * @author 路飞
 */
@Slf4j
@Aspect
@Component
public class BranchScopeAspect {

    /**
     * 分中心权限过滤关键字
     */
    public static final String BRANCH_SCOPE = "branchScope";

    @Before("@annotation(controllerScope)")
    public void doBefore(JoinPoint point, BranchScope controllerScope) throws Throwable {
        log.info("分中心权限过滤处理doBefore：point[{}]过滤，alias：{}", point, controllerScope.alias());
//        Object[] args = point.getArgs();
//        log.info("进行分分中心数据隔离处理：{}", JSONUtil.toJsonStr(args));
//        clearDataScope(point);
//        // 获取当前的用户
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        if (StringUtils.isNotNull(loginUser)) {
//            SysUser currentUser = loginUser.getUser();
//            // 如果是超级管理员或者deal为0，则不过滤数据  && !currentUser.isAdmin() && currentUser.isCenter()
//            log.info("是否是分中心管理员：{}", currentUser.isCenter());
//            if (Objects.nonNull(currentUser) && !currentUser.isAdmin() && currentUser.isCenter() && StringUtils.isNotBlank(controllerScope.alias())) {
//
//                handleDataScope(point, controllerScope.alias());
//            }
//        }

    }

    protected void handleDataScope(final JoinPoint joinPoint, String alias) {
        // 获取当前的用户
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (StringUtils.isNotNull(user)) {
            dataScopeFilter(joinPoint, alias, user);
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param alias
     * @param user
     */
    public static void dataScopeFilter(JoinPoint joinPoint, String alias, SysUser user) {
        StringBuilder sqlString = new StringBuilder();

        if (Objects.nonNull(user) && StringUtils.isNotBlank(user.getCid()) && StringUtils.isNotBlank(alias)) {
            log.info("进行分分中心数据隔离处理：{}、{}", user, alias);
            sqlString.append(StringUtils.format(" AND {} = {} ", alias, user.getCid()));
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            Object[] args = joinPoint.getArgs();
            log.info("进行分分中心数据隔离处理：{}", JSONUtil.toJsonStr(args));

            Object[] newArg = new Object[args.length + 1];
            int j = 0;
            for (Object o : args) {
                newArg[j++] = o;
            }
            HashMap<String, String> branch = new HashMap<>();
            branch.put(BRANCH_SCOPE, sqlString.toString());
            newArg[newArg.length-1] = branch;
            args = newArg;
            log.info("进行分分中心数据隔离处理：{}", JSONUtil.toJsonStr(args));
//            for (int i = 0; i < args.length; i++) {
//                joinPoint.getArgs();
//                if (StringUtils.isNotNull(params) && params instanceof BaseParam) {
//                    BaseParam baseParam = (BaseParam) params;
//                    baseParam.getParams().put(BRANCH_SCOPE, sqlString);
////                    log.info("数据过滤处理：args[{}]，baseParam：{}", args, baseParam);
//                    break;
//                }
//            }
        }
    }

    /**
     * 拼接权限sql前先清空params.postScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(BRANCH_SCOPE, "");
        }
    }

    public static void main(String[] args) {
        Object[] arr = new Object[]{"11"};
        System.out.println("1:" + arr.length);
        arr = Arrays.copyOf(arr, arr.length + 1);
        System.out.println("2:" + arr.length);
    }
}
