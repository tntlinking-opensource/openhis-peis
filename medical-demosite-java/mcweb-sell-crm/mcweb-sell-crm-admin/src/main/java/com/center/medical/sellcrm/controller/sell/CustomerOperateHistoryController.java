package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.CustomerOperateHistory;
import com.center.medical.sellcrm.bean.param.CustomerOperateHistoryParam;
import com.center.medical.sellcrm.service.CustomerOperateHistoryService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放(CustomerOperateHistory)表控制层
 *
 * @author ay
 * @since 2022-11-24 16:18:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 8)
@Api(tags = "客户关系-公共客户状态日志")
@RequestMapping("customerOperateHistory")
public class CustomerOperateHistoryController extends BaseController {
    /**
     * 服务对象
     */
    private final CustomerOperateHistoryService customerOperateHistoryService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple             分页参数
     * @param customerOperateHistoryParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询客户操作历史：记录客户释放、领取、流失、分配、领导释放、线程释放")
    public R<IPage<CustomerOperateHistory>> getPage(PageParamSimple pageParamSimple, CustomerOperateHistoryParam customerOperateHistoryParam) {
        PageParam<CustomerOperateHistory> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.customerOperateHistoryService.getList(page, customerOperateHistoryParam));
    }

}

