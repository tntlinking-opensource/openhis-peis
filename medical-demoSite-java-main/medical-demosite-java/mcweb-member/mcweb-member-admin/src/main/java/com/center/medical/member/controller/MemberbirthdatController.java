package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.model.Memberbirthdat;
import com.center.medical.member.bean.param.BirtthdaySmsParam;
import com.center.medical.member.bean.param.MemberbirthdayParam;
import com.center.medical.member.bean.vo.MemberbirthdatVo;
import com.center.medical.member.service.MemberbirthdatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 会员管理-生日关怀(Memberbirthdat)控制层
 *
 * @author 路飞船长
 * @since 2022-11-24 10:00:24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员管理-生日关怀")
@RequestMapping("member/birthday")
public class MemberbirthdatController extends BaseController {

    /**
     * 服务对象
     */
    private final MemberbirthdatService memberbirthdatService;
    private final MapperFacade mapperFacade;


    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【会员管理-中心会员】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("短信回访-短消息类型", "GET", "/shortmessage/getMessageTypeData", "07.会员系统->会员管理-生日关怀->短信模板-获取回访", null),
                new InterfaceVo("短信回访-短消息名称", "GET", "/shortmessage/getComboData", "07.会员系统->会员管理-生日关怀->短信回访-短消息名称", null),
                new InterfaceVo("短信查看", "GET", "/report/healthPhoneInform/getSmsData", "07.会员系统->会员管理-生日关怀->短信查看", null),
                new InterfaceVo("分页查询", "GET", "/member/birthday/getPage", "07.会员系统->会员管理-生日关怀->分页查询", null),
                new InterfaceVo("添加短信回访", "POST", "/member/birthday/saOrUp", "07.会员系统->会员管理-生日关怀->添加短信回访", null),
                new InterfaceVo("取消发送", "PUT", "/member/birthday/cancelSMS", "07.会员系统->会员管理-生日关怀->取消发送", null)
        );
        return R.ok(new FunctionVo("07.会员系统", "会员管理-会员卡发放", interfaceVos, "07.会员系统"));
    }


    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询会员生日提醒回访列表数据(三天之内过生日的)")
    public R<IPage<MemberbirthdatVo>> getPage(PageParamSimple pageParamSimple, MemberbirthdayParam param) {
        PageParam<Memberbirthdat> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.memberbirthdatService.getPage(page, param));
    }



    /**
     * 修改数据
     *
     * @param param 发送参数内容
     * @return 操作结果
     */
    @PostMapping("/saOrUp")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "添加短信回访", notes = "添加短信回访")
    @Log(title = "会员生日提醒回访", businessType = BusinessType.UPDATE)
    public R<String> update(@RequestBody BirtthdaySmsParam param) {
        return R.ok(this.memberbirthdatService.saOrUp(param));
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除会员生日提醒回访表")
    @Log(title = "会员生日提醒回访", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.memberbirthdatService.removeByIds(ids));
    }


    /**
     * 导出会员生日提醒回访
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出会员生日提醒回访")
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "会员生日提醒回访", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, MemberbirthdayParam param) {
        List<MemberbirthdatVo> list = this.memberbirthdatService.getList(param);
        ExcelUtil<MemberbirthdatVo> util = new ExcelUtil<MemberbirthdatVo>(MemberbirthdatVo.class);
        util.exportExcel(response, list, "会员生日提醒回访数据");
    }


    /**
     * 取消发送
     * @param ids
     * @return
     */
    @PutMapping("/cancelSMS")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "取消发送", notes = "取消发送")
    @Log(title = "会员生日提醒回访", businessType = BusinessType.UPDATE)
    public R cancelSMS(@RequestParam List<String> ids) {
        return R.toResult(memberbirthdatService.cancelSMS(ids));
    }


}

