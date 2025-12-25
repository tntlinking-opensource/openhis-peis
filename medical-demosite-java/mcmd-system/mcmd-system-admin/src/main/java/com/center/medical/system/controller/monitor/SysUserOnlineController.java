package com.center.medical.system.controller.monitor;

import com.center.medical.common.annotation.Log;
import com.center.medical.common.constant.CacheConstants;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.entity.SysUserOnline;
import com.center.medical.common.core.domain.model.LoginUser;
import com.center.medical.common.core.page.TableDataInfo;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.redis.RedisUtil;
import com.center.medical.system.service.ISysUserOnlineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author 路飞
 */
@Api(tags = "在线用户监控")
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {
    @Resource
    private ISysUserOnlineService userOnlineService;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/list")
    @ApiOperation(value = "列表", notes = "列表")
    public TableDataInfo list(String ipaddr, String userName) {
        Collection<String> keys = RedisUtil.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys) {
            LoginUser user = RedisUtil.get(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr())) {
                    userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
            } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                if (StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户-强退用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    @ApiOperation(value = "强退用户", notes = "强退用户")
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        RedisUtil.del(CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        return AjaxResult.success();
    }
}
