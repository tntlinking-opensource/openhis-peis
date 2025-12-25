package com.center.medical.finance.controller;

import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.finance.bean.param.ISDataParam;
import com.center.medical.finance.bean.vo.IndexSituationVo;
import com.center.medical.finance.service.IndexSituationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 财务报表-指标情况(Createorder)表控制层
 *
 * @author ay
 * @since 2023-05-15 09:37:34
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "财务报表-指标情况")
@RequestMapping("finance/indexSituation")
public class IndexSituationController extends BaseController {
    /**
     * 服务对象
     */
    private final IndexSituationService indexSituationService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param param 分页参数
     * @return 所有数据
     */
    @GetMapping("/getData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取统计数据", notes = "获取统计数据")
    public R<List<IndexSituationVo>> getData(ISDataParam param) {
        List<IndexSituationVo> vo = indexSituationService.getData(param);
        return R.ok(vo);
    }


    /**
     * 判断有无财务权限
     * @return
     */
    @GetMapping("/hasFinancialAuthority")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断当前登录用户有无财务权限", notes = "判断有无财务权限，true有,false没有")
    public R hasFinancialAuthority() {
        Boolean role = SecurityUtils.hasRole(RoleAuthName.CAIWU);
        return R.ok(role);
    }

}

