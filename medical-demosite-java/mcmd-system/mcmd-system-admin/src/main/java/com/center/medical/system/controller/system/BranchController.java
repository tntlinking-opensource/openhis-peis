package com.center.medical.system.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.param.BranchParam;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.service.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分中心维护表(Branch)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-08 15:23:41
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "分中心维护表")
@RequestMapping("branch")
public class BranchController extends BaseController {
    /**
     * 服务对象
     */
    private final BranchService branchService;


    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param param 查询实体
     * @return 所有数据
     */
    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('sys:branch:list')")
    @ApiOperation(value = "分页查询", notes = "分页查询分中心维护表")
    public R<IPage<Branch>> selectAll(PageParam<Branch> page, BranchParam param) {
        return R.ok(this.branchService.getList(page, param));
    }


    /**
     * 获取用户分中心
     *
     * @return
     */
    @GetMapping("/getUserCid")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取用户分中心", notes = "获取用户分中心")
    public R<List<Branch>> getUserCid(@RequestParam(name = "userNo", required = false) String userNo) {
        List<Branch> list = branchService.getUserBranch(userNo);
        return R.ok(list);
    }

}

