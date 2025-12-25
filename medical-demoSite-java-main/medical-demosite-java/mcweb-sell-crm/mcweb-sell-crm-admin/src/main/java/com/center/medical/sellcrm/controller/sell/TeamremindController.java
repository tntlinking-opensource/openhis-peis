package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.Teamremind;
import com.center.medical.sellcrm.bean.param.SaveKhgtParam;
import com.center.medical.sellcrm.bean.param.TeamremindParam;
import com.center.medical.sellcrm.service.TeamremindService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 客户关系-预检跟踪
 *
 * @author 路飞船长
 * @since 2022-11-22 14:53:26
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 6)
@Api(tags = "客户关系-预检跟踪")
@RequestMapping("sell/teamremind")
public class TeamremindController extends BaseController {
    /**
     * 服务对象
     */
    private final TeamremindService teamremindService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询客户预检跟踪记录
     *
     * @param pageParamSimple 分页参数
     * @param teamremindParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询客户预检跟踪记录", position = 1)
    public R<IPage<Teamremind>> getPage(PageParamSimple pageParamSimple, TeamremindParam teamremindParam) {
        PageParam<Teamremind> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.teamremindService.getPage(page, teamremindParam));
    }


    /**
     * 保存客户沟通记录
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveKhgtData")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "客户跟进：提交", notes = "保存客户沟通记录", position = 2)
    @Log(title = "客户关系-预检跟踪--保存客户沟通记录", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"teamremind.id"})
    public R saveKhgtData(@RequestBody SaveKhgtParam param) {
        return R.toResult(this.teamremindService.saveKhgtData(param));
    }


    /**
     * 设为已来检
     *
     * @param
     * @return
     */
    @GetMapping("/setExamed/{ids}")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "设为已来检", notes = "设为已来检", position = 3)
    @Log(title = "客户关系-预检跟踪-设为已来检", businessType = BusinessType.UPDATE)
    public R setExamed(@PathVariable List<String> ids) {
        Teamremind teamremind = new Teamremind();
        teamremind.setIsExamed(1);
        teamremind.setModifydate(new Date());
        boolean i = teamremindService.update(teamremind, new UpdateWrapper<Teamremind>().in("id", ids));
        return R.toResult(i);
    }


    /**
     * 获取主页数据
     *
     * @param
     * @return
     */
    @GetMapping("/getHomeData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取主页数据", notes = "获取主页数据", position = 4)
    public R<List<Teamremind>> getHomeData(TeamremindParam teamremindParam) {
        return R.ok(this.teamremindService.getHomeData(teamremindParam));
    }

    
}

