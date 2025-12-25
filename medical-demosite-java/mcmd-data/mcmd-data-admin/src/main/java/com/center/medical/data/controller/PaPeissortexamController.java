package com.center.medical.data.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.PaPeissortexam;
import com.center.medical.data.bean.param.PaPeiEParam;
import com.center.medical.data.service.PaPeissortexamService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 平安软件-排检(PaPeissortexam)表控制层
 *
 * @author 路飞船长
 * @since 2022-11-08 14:36:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "平安软件-排检")
@RequestMapping("paPeissortexam")
public class PaPeissortexamController extends BaseController {
    /**
     * 服务对象
     */
    private final PaPeissortexamService paPeissortexamService;

    /**
     * 分页查询所有数据
     *
     * @param page  分页对象
     * @param param 查询参数
     * @return 所有数据
     */
    @GetMapping("/list")
//    @PreAuthorize("@ss.hasPermi('basis:scheduling:list')")
    @ApiOperation(value = "分页查询", notes = "分页查询平安软件-排检")
    public R<IPage<PaPeissortexam>> selectAll(PageParam<PaPeissortexam> page, PaPeiEParam param) {
        return R.ok(this.paPeissortexamService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @PreAuthorize("@ss.hasPermi('basis:scheduling:info')")
    @ApiOperation(value = "详情", notes = "根据id查平安软件-排检详情")
    public R<PaPeissortexam> selectOne(@PathVariable String id) {
        return R.ok(this.paPeissortexamService.getInfoById(id));
    }

    /**
     * 新增数据
     *
     * @param paPeissortexam 实体对象
     * @return 新增结果
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('basis:scheduling:add')")
    @ApiOperation(value = "添加", notes = "添加平安软件-排检")
    @Log(title = "平安软件-排检", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"paPeissortexam.id"})
    public R insert(@RequestBody PaPeissortexam paPeissortexam) {
        return R.toResult(this.paPeissortexamService.saOrUp(paPeissortexam));
    }

    /**
     * 修改数据
     *
     * @param paPeissortexam 实体对象
     * @return 修改结果
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('basis:scheduling:edit')")
    @ApiOperation(value = "更新", notes = "更新平安软件-排检")
    @Log(title = "平安软件-排检", businessType = BusinessType.UPDATE)
    public R update(@RequestBody PaPeissortexam paPeissortexam) {
        return R.toResult(this.paPeissortexamService.saOrUp(paPeissortexam));
    }

    /**
     * 删除数据
     *
     * @param ids 主键结合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermi('basis:scheduling:remove')")
    @ApiOperation(value = "删除", notes = "删除平安软件-排检")
    @Log(title = "平安软件-排检", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        if (ids.size() == 0) {
            throw new ServiceException("请选择要删除的记录！");
        }
        List<PaPeissortexam> list = this.paPeissortexamService.listByIds(ids);
        List<PaPeissortexam> canRmList = list.stream().filter(item -> DateUtil.compare(item.getSortDate(), new Date(), "yyyy-MM-dd") > 0).collect(Collectors.toList());
        List<String> idList = canRmList.stream().map(PaPeissortexam::getId).collect(Collectors.toList());
        if (Objects.equals(idList.size(), ids.size())) {
            return R.toResult(this.paPeissortexamService.removeByIds(idList));
        } else {
            this.paPeissortexamService.removeByIds(idList);
            return R.ok(null, "过期预约设置不可删除！");
        }
    }
}

