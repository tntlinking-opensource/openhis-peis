package com.center.medical.common.utils;

import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全服务工具类
 *
 * @author 路飞
 */
public class SecurityUtils {
    /**
     * 用户ID
     **/
    public static Long getUserId() {
        try {
            return getLoginUser().getUserId();
        } catch (Exception e) {
            throw new ServiceException("获取用户ID异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 用户编号
     **/
    public static String getUserNo() {
        try {
            return getLoginUser().getUser().getUserNo();
        } catch (Exception e) {
            throw new ServiceException("获取用户编号异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 用户所在的分中心ID
     **/
    public static String getCId() {
        try {
            return getLoginUser().getUser().getCid();
        } catch (Exception e) {
            throw new ServiceException("获取用户所在的分中心ID异常", HttpStatus.UNAUTHORIZED);
        }
    }


    /**
     * 用户是否是领导
     **/
    public static Boolean isLeader() {
        try {
            return StringUtils.equals(getLoginUser().getUser().getIsleader(), "1");
        } catch (Exception e) {
            throw new ServiceException("判断用户是否是领导异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取部门ID
     **/
    public static Long getDeptId() {
        try {
            return getLoginUser().getDeptId();
        } catch (Exception e) {
            throw new ServiceException("获取部门ID异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new ServiceException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ServiceException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 判断用户是否拥有相应的角色权限
     **/
    public static Boolean hasRole(String role) {
        //判断是否超级管理员，管理员直接返回true
        return getLoginUser().getUser().getRoles().stream().anyMatch(r -> r.getRoleKey().equals(RoleAuthName.ADMIN))
                || getLoginUser().getUser().getRoles().stream().anyMatch(r -> r.getRoleKey().equals(role));
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public static void main(String[] args){
        System.out.println(SecurityUtils.encryptPassword("123456"));
    }
}
