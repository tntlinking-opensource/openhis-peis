package com.center.medical.framework.interceptor;

import com.center.medical.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ip认证拦截器
 *
 * @author 路飞
 */
@Slf4j
@Component
public class IpAuthInterceptor implements HandlerInterceptor {

    @Resource
    private ISysConfigService iSysConfigService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
//        if (handler instanceof HandlerMethod) {
//            String ip = IpUtils.getIpAddr(request);
//            log.info("请求来源的Ip={}", IpUtils.getIpAddr(request));
//            if (StringUtils.isNotBlank(ip)) {
//                //判断IP是否已授权
//                String str = iSysConfigService.selectConfigByKey(Constants.IP_CONFIG);
//                if (StringUtils.isNotBlank(str)) {
//                    IpConfig ipConfig = JSONUtil.toBean(str, IpConfig.class);
//                    //log.info("已授权的IP列表：{}", ipConfig);
//                    if (Objects.nonNull(ipConfig.getSyncAuthIp()) && StringUtils.isNotBlank(ipConfig.getSyncAuthIp())) {
//                        if (Arrays.asList(ipConfig.getSyncAuthIp().split(",")).contains(ip)) {
//                            log.info("IP授权认证通过");
//                            return true;
//                        } else {
//                            log.error("IP授权认证不通过， 权限不足，无法调用该接口！");
//                            AjaxResult ajaxResult = AjaxResult.error("权限不足，无法调用该接口！");
//                            ServletUtils.renderString(response, JSON.toJSONString(ajaxResult));
//                            return false;
//                        }
//                    }
//                }
//                return true;
//            }
//            return false;
//        } else {
//            return true;
//        }
    }

}
