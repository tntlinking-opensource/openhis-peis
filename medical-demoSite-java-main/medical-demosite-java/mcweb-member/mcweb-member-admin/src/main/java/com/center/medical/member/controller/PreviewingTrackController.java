package com.center.medical.member.controller;

import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.*;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.member.bean.param.PTPageParam;
import com.center.medical.member.bean.param.PTsaOrUpParam;
import com.center.medical.member.bean.vo.CeShiVO;
import com.center.medical.member.bean.vo.PreviewingTrackVo;
import com.center.medical.member.service.PreviewingTrackService;
import com.center.medical.report.bean.model.SectionTotal;
import com.center.medical.report.service.SectionTotalService;
import com.center.medical.service.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 回访管理-个检预检回访(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-02-10 14:25:16
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "回访管理-个检预检回访")
@RequestMapping("/member/previewingTrack")
public class PreviewingTrackController extends BaseController {
    /**
     * 服务对象
     */
    private final PreviewingTrackService previewingTrackService;
    private final MapperFacade mapperFacade;
    private final PeispatientarchiveService peispatientarchiveService;
    private final AdvanceVisitWriteService advanceVisitWriteService;
    private final PeispatientService peispatientService;
    private final PeispatientChargeMainService peispatientChargeMainService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final PeispatientchargeService peispatientchargeService;
    private final DictpaywayService dictpaywayService;
    private final SectionTotalService sectionTotalService;

    /**
     * 接口说明
     *
     * @return
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【回访管理-个检预检回访】这个业务模块所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/member/previewingTrack/page", "客服系统->客服管理-回访管理-个检预检回访->分页查询", null),
                new InterfaceVo("详情", "GET", "/member/previewingTrack/{id}", "客服系统->客服管理-回访管理-个检预检回访->详情", null),
                new InterfaceVo("保存", "POST", "/member/previewingTrack/saveOrUpdate", "客服系统->客服管理-回访管理-个检预检回访->保存", null),
                new InterfaceVo("获取开单医师", "GET", "/member/previewingTrack/getKdys", "客服系统->客服管理-回访管理-个检预检回访->获取开单医师", null),
                new InterfaceVo("导出", "POST", "/member/previewingTrack/export", "客服系统->客服管理-回访管理-个检预检回访->导出", null)

        );
        return R.ok(new FunctionVo("客服系统", "回访管理-个检预检回访", interfaceVos, "客服系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询QT体检者表")
    public R<IPage<PreviewingTrackVo>> getPage(PageParamSimple pageParamSimple, PTPageParam param) {
        PageParam<PreviewingTrackVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.previewingTrackService.getList(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<CeShiVO> selectOne(@PathVariable String id) {
        //存放数据
        CeShiVO vo = new CeShiVO();
        //体检者表
        Peispatient peispatient = peispatientService.getInfoById(id);
        vo.setPeispatient(peispatient);

        if (ObjectUtils.isNotEmpty(peispatient)) {
            //体检者档案表
            Peispatientarchive peisPatientArchive = peispatientarchiveService.getInfoByNo(peispatient.getPatientarchiveno());
            vo.setPeispatientarchive(peisPatientArchive);

            //主表记录体检者是否来检
            AdvanceVisitWrite advanceVisitWrite = advanceVisitWriteService.getOne(new QueryWrapper<AdvanceVisitWrite>()
                    .eq("patientarchiveno_id", peisPatientArchive.getId()));
            vo.setAdvanceVisitWrite(advanceVisitWrite);

            // 体检者费用主表
            PeispatientChargeMain chargemain = peispatientChargeMainService.getOne(new QueryWrapper<PeispatientChargeMain>()
                    .eq("patientcode", peispatient.getPatientcode()));
            vo.setPeispatientChargeMain(chargemain);

            // 体检项目获取
            List<Peispatientfeeitem> peispatientfeeitem = peispatientfeeitemService.list(new QueryWrapper<Peispatientfeeitem>()
                    .orderByAsc("id").eq("id_patient", peispatient.getPatientcode())
                    .eq("sfjj", 0).eq("f_giveup", 0));
            String str = "";

            for (Peispatientfeeitem pe : peispatientfeeitem) {
                //拼接收费项目名称
                str += pe.getExamfeeitemName() + ",";
            }
            str = str.substring(0, str.length() - 1);
            vo.setExamfeeitemName(str);

            Set<String> paywaylist = new HashSet<String>();

            //体检者缴费表
            List<Peispatientcharge> charges = peispatientchargeService.list(new QueryWrapper<Peispatientcharge>()
                    .eq("patientcode", peispatient.getPatientcode()));
            for (Peispatientcharge charge : charges) {
                //支付方式
                Dictpayway payway = dictpaywayService.getInfoById(charge.getIdPayway());
                if (payway != null) {
                    paywaylist.add(payway.getPaywayName());
                }
            }

            String payways = StringUtils.join(paywaylist, "、");
            vo.setPayways(payways);

            vo.setAge(IdcardUtil.getAgeByIdCard(peisPatientArchive.getIdcardno()));

            //上次体检结果需要关联“本次健康总检结论”
            SectionTotal st = sectionTotalService.getOne(new QueryWrapper<SectionTotal>()
                    .eq("disease_health", 0).eq("patientcode", peispatient.getPatientcode()));
            if (st != null) {
                //结论
                String verdict = st.getVerdict();
                vo.setSignList(verdict);

            }
        }
        return R.ok(vo);
    }

    /**
     * 保存
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "保存", notes = "添加QT体检者表")
    @Log(title = "QT体检者表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatient.id"})
    public R insert(@RequestBody PTsaOrUpParam param) {
        return R.toResult(this.previewingTrackService.saOrUp(param));
    }


    /**
     * 获取开单医师
     *
     * @param key
     * @return
     */
    @GetMapping("/getKdys")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取开单医师", notes = "获取开单医师")
    @ApiImplicitParam(name = "key", value = "输入码模糊查询")
    public R getKdys(String key) {
        List<Map<String, String>> list = previewingTrackService.getKdys(key);
        return R.ok(list);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @Log(title = "个检预检回访", businessType = BusinessType.EXPORT)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiOperation(value = "导出", notes = "个检预检回访导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PTPageParam param) {
        List<PreviewingTrackVo> list = previewingTrackService.getExportData(param);
        ExcelUtil<PreviewingTrackVo> util = new ExcelUtil<PreviewingTrackVo>(PreviewingTrackVo.class);
        util.exportExcel(response, list, "个检预检回访");
    }
}

