package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.report.bean.model.ReportDefaultDoctor;
import com.center.medical.report.bean.param.DefaultDoctorPageParam;
import com.center.medical.report.bean.param.RDDoctorSaOrUpParam;
import com.center.medical.report.bean.vo.ReportDefaultDoctorVo;
import com.center.medical.report.service.ReportDefaultDoctorService;
import com.center.medical.system.service.ISysDeptService;
import com.center.medical.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 报告中科室默认医生(ReportDefaultDoctor)接口
 *
 * @author ay
 * @since 2024-08-21 16:54:18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "报告科室默认医生")
@RequestMapping("/report/reportDefaultDoctor")
public class ReportDefaultDoctorController extends BaseController {
    /**
     * 服务对象
     */
    private final ReportDefaultDoctorService reportDefaultDoctorService;
    private final ISysUserService sysUserService;
    private final ISysDeptService sysDeptService;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple     分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询报告中科室默认医生")
    public R<IPage<ReportDefaultDoctorVo>> getPage(PageParamSimple pageParamSimple, DefaultDoctorPageParam param) {
        PageParam<ReportDefaultDoctorVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.reportDefaultDoctorService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiOperation(value = "详情", notes = "根据id查报告中科室默认医生详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<ReportDefaultDoctor> selectOne(@PathVariable String id) {
        ReportDefaultDoctor reportDefaultDoctor = reportDefaultDoctorService.getInfoById(id);
        SysUser user = sysUserService.getUserByNo(reportDefaultDoctor.getUserId());
        if (ObjectUtils.isNotEmpty(user)){
            reportDefaultDoctor.setUserName(user.getUserName());
        }
        SysDept dept = sysDeptService.getByDeptNo(reportDefaultDoctor.getDepId());
        if (ObjectUtils.isNotEmpty(dept)){
            reportDefaultDoctor.setDeptName(dept.getDeptName());
        }
        return R.ok(reportDefaultDoctor);
    }

    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或更新", notes = "添加或更新")
    @Log(title = "报告中科室默认医生", businessType = BusinessType.INSERT)
    public R insert(@RequestBody @Valid RDDoctorSaOrUpParam param) {
        return R.toResult(this.reportDefaultDoctorService.saOrUp(param));
    }



    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除报告中科室默认医生")
    @Log(title = "报告中科室默认医生", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.reportDefaultDoctorService.removeByIds(ids));
    }

}
