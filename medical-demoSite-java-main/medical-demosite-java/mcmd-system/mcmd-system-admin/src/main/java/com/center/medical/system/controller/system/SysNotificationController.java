package com.center.medical.system.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.system.bean.param.SysNotificationPageParam;
import com.center.medical.system.bean.vo.SysNotificationPageVo;
import com.center.medical.system.service.SysNotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

/**
 * 系统授权记录(SysAuthLog)接口
 *
 * @author makejava
 * @since 2024-01-17 20:22:27
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "通知记录表")
@RequestMapping("/system/notification")
public class SysNotificationController extends BaseController {
    /**
     * 服务对象
     */
    private final SysNotificationService sysNotificationService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param      查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询通知配置表")
    public R<IPage<SysNotificationPageVo>> getPage(PageParamSimple pageParamSimple, SysNotificationPageParam param) {
        PageParam<SysNotificationPageVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        param.setTarUserId(SecurityUtils.getUserNo());
        return R.ok(this.sysNotificationService.getPage(page, param));
    }


    /**
     * 全部已读
     *
     * @return
     */
    @PostMapping("/allRead")
    @ApiOperation(value = "全部已读", notes = "全部已读")
    public R<Boolean> getMealList() {
        return R.ok(sysNotificationService.allRead());
    }




}


