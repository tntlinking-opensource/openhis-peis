package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.param.DataExceptionPeiParam;
import com.center.medical.sellcrm.bean.vo.DataExceptionPeiVo;
import com.center.medical.sellcrm.service.TeamremindService;
import com.center.medical.service.PeispatientAbnormalIgnoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "数据异常")
@RequestMapping("sell/dataException")
public class DataExceptionController extends BaseController {
    /**
     * 服务对象
     */
    private final TeamremindService teamremindService;
    private final MapperFacade mapperFacade;
    private final PeispatientAbnormalIgnoreService peispatientAbnormalIgnoreService;

    /**
     * 分页查询客户预检跟踪记录
     *
     * @param pageParamSimple 分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "获取数据异常体检者数据", notes = "获取数据异常体检者数据")
    public R<IPage<DataExceptionPeiVo>> getPage(PageParamSimple pageParamSimple, DataExceptionPeiParam param) {
        PageParam<DataExceptionPeiVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.teamremindService.getDataExceptionPeiPage(page, param));
    }


    /**
     * 审核(图像检查,视力检查)
     *
     * @param param
     * @return
     */
    @PostMapping("/ignore")
    @ApiOperation(value = "忽略数据", notes = "忽略数据")
    public R ignore(@RequestParam List<String> ids) {
        Boolean b = peispatientAbnormalIgnoreService.ignore(ids);
        return R.ok(b);
    }


    
}

