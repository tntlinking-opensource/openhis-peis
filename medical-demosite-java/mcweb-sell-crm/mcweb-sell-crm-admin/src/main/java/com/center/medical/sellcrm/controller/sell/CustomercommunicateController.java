package com.center.medical.sellcrm.controller.sell;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.Customercommunicate;
import com.center.medical.sellcrm.bean.param.CustomercommunicateParam;
import com.center.medical.sellcrm.service.CustomercommunicateService;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户关系-预检跟踪记录
 *
 * @author 路飞船长
 * @since 2022-11-21 09:03:14
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 7)
@Api(tags = "客户关系-预检跟踪记录")
@RequestMapping("sell/customercommunicate")
public class CustomercommunicateController extends BaseController {
    /**
     * 服务对象
     */
    private final CustomercommunicateService customercommunicateService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple          分页参数
     * @param customercommunicateParam 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询客户沟通表", position = 1)
    public R<IPage<Customercommunicate>> getPage(PageParamSimple pageParamSimple, CustomercommunicateParam customercommunicateParam) {
        PageParam<Customercommunicate> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.customercommunicateService.getPage(page, customercommunicateParam));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查客户沟通表详情", position = 2)
    public R<Customercommunicate> selectOne(@PathVariable String id) {
        return R.ok(this.customercommunicateService.getInfoById(id));
    }


}

