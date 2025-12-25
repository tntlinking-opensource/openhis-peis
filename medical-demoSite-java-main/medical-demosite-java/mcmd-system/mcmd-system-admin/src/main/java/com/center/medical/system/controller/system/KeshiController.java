package com.center.medical.system.controller.system;

import com.center.medical.bean.vo.ListWithSrmVo;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.system.bean.param.KeshiParam;
import com.center.medical.system.service.KeshiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 沃德医疗部门总集(所有中心部门的总集)（对应原系统中的qx_department）(Keshi)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-29 11:53:54
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室管理")
@RequestMapping("/system/keshi")
public class KeshiController extends BaseController {

    private final KeshiService deptService;

    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    @GetMapping("/list")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据输入码查询列表数据", notes = "根据条件查询列表数据")
    public R<List<ListWithSrmVo>> getList(KeshiParam param) {
        List<ListWithSrmVo> list = deptService.getList(param);
        return R.ok(list);
    }
}

