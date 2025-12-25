package com.center.medical.common.core.domain.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.center.medical.common.core.domain.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 登录用户身份权限
 *
 * @author 路飞
 */
@Data
public class LoginUser implements UserDetails {

    private static final long serialVersionUID = 2954421830676286195L;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 用户编号（继承于原体检系统的用户id：userId）
     */
    @ApiModelProperty("用户编号（继承于原体检系统的用户id：userId）")
    private String userNo;

    /**
     * 分中心ID（继承于原体检系统的分中心id：branchId）
     */
    @ApiModelProperty("分中心ID（继承于原体检系统的分中心id：branchId）")
    private String cid;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long deptId;

    /**
     * 用户唯一标识
     */
    @ApiModelProperty("用户唯一标识")
    private String token;

    /**
     * 登录时间
     */
    @ApiModelProperty("登录时间")
    private Long loginTime;

    /**
     * 过期时间
     */
    @ApiModelProperty("过期时间")
    private Long expireTime;

    /**
     * 登录IP地址
     */
    @ApiModelProperty("登录IP地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @ApiModelProperty("登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @ApiModelProperty("浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @ApiModelProperty("操作系统")
    private String os;

    /**
     * 权限列表
     */
    @ApiModelProperty("权限列表")
    private Set<String> permissions;

    /**
     * 用户信息
     */
    @ApiModelProperty("用户信息")
    private SysUser user;

    public LoginUser() {
    }

    public LoginUser(SysUser user, Set<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(Long userId, Long deptId, SysUser user, Set<String> permissions) {
        this.userId = userId;
        this.deptId = deptId;
        this.user = user;
        this.permissions = permissions;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
