package com.center.medical.abteilung.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.SectionAndRemind;
import com.center.medical.abteilung.bean.param.DLungParam;
import com.center.medical.abteilung.bean.param.DivisionLungParam;
import com.center.medical.abteilung.bean.param.lungSaveJlcParam;
import com.center.medical.abteilung.service.DivisionLungService;
import com.center.medical.abteilung.service.DivisionService;
import com.center.medical.abteilung.service.SectionAndRemindService;
import com.center.medical.bean.model.Lung;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Basconclusion;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.service.ISysBranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 科室列表-肺功能(Lung)表控制层
 *
 * @author ay
 * @since 2023-04-20 18:01:22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "科室列表-肺功能")
@RequestMapping("abteilung/divisionLung")
public class DivisionLungController extends BaseController {
    /**
     * 服务对象
     */
    private final DivisionLungService divisionLungService;
    private final MapperFacade mapperFacade;
    private final ISysBranchService iSysBranchService;
    private final SectionAndRemindService sectionAndRemindService;
    private final DivisionService divisionService;



    /**
     * 获取会员类型
     *
     * @return
     */
    @GetMapping("/search")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "读卡", notes = "肺功能读卡")
    public R search(DivisionLungParam param) {
        //补0
        if ("true".equals(param.getAutoFill())){
            String patientCode = ToolUtil.patientCode(param.getPatientCode(), iSysBranchService.getBranchFlag(null));
            param.setPatientCode(patientCode);
        }else {
            param.setPatientCode(param.getPatientCode().trim().toUpperCase());
        }
        return R.ok(divisionLungService.search(param));
    }

    /**
     * 判断是否存在提醒
     *
     * @return
     */
    @GetMapping("/getRemindStr")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断是否存在提醒", notes = "判断是否存在提醒,true是,false否")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "ksID", value = "科室id")
    })
    public R getRemindStr(String patientcode, String ksID) {
        //科室提醒和科室关联表
        QueryWrapper<SectionAndRemind> con = new QueryWrapper<>();
        con.eq("patientcode", patientcode);
        if (StringUtils.isNotEmpty(ksID)) {
            con.eq("dep_id", ksID);
        }
        //true是,false否
        long count = sectionAndRemindService.count(con);
        if (count > 0) {
            return R.ok("true");
        }
        return R.ok("false");
    }


    /**
     * 结伦词列表数据
     * @param patientCode
     * @param ksId
     * @return
     */
    @GetMapping("/jlcData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "结伦词列表数据", notes = "结伦词列表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientCode", value = "体检号"),
            @ApiImplicitParam(name = "ksId", value = "科室id")
    })
    public R jlcData(String patientCode ,String ksId) {
        return R.ok(divisionLungService.jlcData(patientCode,ksId));
    }


    /**
     * 获取体征词
     * @param key
     * @return
     */
    @GetMapping("/getSign")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取体征词", notes = "获取体征词")
    @ApiImplicitParam(name = "key", value = "体征词,zc、hh、bs、js")
    public R getSign(String key) {
        return R.ok(divisionLungService.getSign(key));
    }


    /**
     * 保存结伦词
     * @param param
     * @return
     */
    @PostMapping("/saveJlc")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "保存结伦词", notes = "保存结伦词")
    public R saveJlc(@RequestBody lungSaveJlcParam param) {
        Boolean b = divisionLungService.saveJlc(param);
        return R.toResult(b);
    }


    /**
     * 结论词搜索
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getConclusion")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "结论词搜索", notes = "总检结论词搜索")
    @ApiImplicitParam(name = "key", value = "输入码input_code")
    public R<IPage<Basconclusion>> getConclusion(PageParamSimple pageParamSimple, String key) {
        PageParam<Basconclusion> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Basconclusion> iPage = divisionService.getConclusion(page, key);
        return R.ok(iPage);
    }

    /**
     * 当前分中心所有医生
     *
     * @param ksID
     * @param key
     * @return
     */
    @GetMapping("/allDoctors")
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "当前分中心当前科室的所有医生", notes = "当前分中心当前科室的所有医生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ksID", value = "科室id"),
            @ApiImplicitParam(name = "key", value = "输入码")
    })
    public R<List<AllUserDataVo>> allDoctors(String ksID, String key) {
        //职业病名称
        String cId = SecurityUtils.getCId();
        List<AllUserDataVo> list = divisionService.allDoctors(cId, ksID, key);
        return R.ok(list);
    }



    /**
     * 保存结伦词
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "审核", notes = "审核")
    public R saveOrUpdate(@RequestBody DLungParam param) {
        Boolean b = divisionLungService.saOrUp(param);
        return R.toResult(b);
    }


    /**
     * 反审核
     * @param patientCode
     * @param ksId
     * @return
     */
    @PutMapping("/reverse")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "反审核", notes = "反审核")
    public R reverse(@RequestParam String patientCode,@RequestParam String ksId) {
        Boolean b = divisionLungService.reverse(patientCode,ksId);
        return R.toResult(b);
    }


    /**
     * 肺功能即时上传
     * @param data 串口数据
     * @return
     */
    @PostMapping("/uploadLungIm")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "肺功能即时上传", notes = "肺功能即时上传")
    public R<Lung> uploadLungIm(@RequestBody String data) {
        Lung lung = divisionLungService.uploadLungIm(data);
        return R.ok(lung);
    }





    /**
     * 肺功能即时上传
     * @param data 串口数据
     * @return
     */
    @PostMapping("/uploadHnLung")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "淮南肺功能即时上传", notes = "淮南肺功能即时上传")
    public R<Lung> uploadHnLung(@RequestBody String data) throws UnsupportedEncodingException {
        Lung lung = divisionLungService.uploadHnLung(data);
        return R.ok(lung);
    }
}

