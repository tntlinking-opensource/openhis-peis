package com.center.medical.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.vo.FunctionVo;
import com.center.medical.bean.vo.InterfaceVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.member.bean.param.ELSaOrUpParam;
import com.center.medical.member.bean.param.ElReportParam;
import com.center.medical.member.bean.param.SaveMergeParam;
import com.center.medical.member.bean.vo.ElReportVo;
import com.center.medical.member.bean.vo.EleInfoListVo;
import com.center.medical.member.service.ElectronicReportService;
import com.center.medical.service.PeispatientarchiveService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 会员管理-档案合并(Peispatientarchive)表控制层
 *
 * @author ay
 * @since 2023-02-27 11:31:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "会员管理-档案合并")
@RequestMapping("/member/electronicReport")
public class ElectronicReportController extends BaseController {
    /**
     * 服务对象
     */
    private final ElectronicReportService electronicReportService;
    private final MapperFacade mapperFacade;
    private final PeispatientarchiveService peispatientarchiveService;


    /**
     * 【会员管理-档案合并】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【会员管理-档案合并】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/member/electronicReport/page", "07.会员系统->客服管理-会员管理-档案合并->分页查询", null),
                new InterfaceVo("详情", "GET", "/member/electronicReport/{id}", "07.会员系统->客服管理-会员管理-档案合并->详情", null),
                new InterfaceVo("编辑-保存", "Post", "/member/electronicReport/saveOrUpdate", "07.会员系统->客服管理-会员管理-档案合并->编辑-保存", null),
                new InterfaceVo("合并档案页面数据", "GET", "/member/electronicReport/merge", "07.会员系统->客服管理-会员管理-档案合并->合并档案页面数据", null),
                new InterfaceVo("合并档案", "Post", "/member/electronicReport/saveMerge", "07.会员系统->客服管理-会员管理-档案合并->合并档案", null)
        );
        return R.ok(new FunctionVo("07.会员系统", "会员管理-档案合并", interfaceVos, "07.会员系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询体检者档案表")
    public R<IPage<ElReportVo>> getPage(PageParamSimple pageParamSimple, ElReportParam param) {
        // TODO: 2023/2/27   用到了 appUser表 未完成
        PageParam<ElReportVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.electronicReportService.getList(page, param));
    }



    /**
     * 新增数据
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "编辑-保存", notes = "编辑-保存")
    @Log(title = "体检者档案表", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"peispatientarchive.id"})
    public R saveOrUpdate(@RequestBody ELSaOrUpParam param) {
        return R.toResult(this.electronicReportService.saOrUp(param));
    }


    /**
     * 合并档案页面数据
     *
     * @param ids
     * @return
     */
    @GetMapping("/merge")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "合并档案页面数据", notes = "合并档案页面数据")
    @ApiImplicitParam(name = "ids", value = "id集合")
    public R merge(@RequestParam List<String> ids) {
        Map<String, Object> data = new HashMap<>();
        //体检者档案表
        List<Peispatientarchive> patientarchives = peispatientarchiveService.list(new QueryWrapper<Peispatientarchive>()
                .in("id", ids));
        List<Map<String, String>> patientnameList = new ArrayList<Map<String, String>>();
        Set<String> patientnameSet = new HashSet<String>();
        List<Map<String, String>> phoneList = new ArrayList<Map<String, String>>();
        Set<String> phoneSet = new HashSet<String>();
        List<Map<String, String>> idcardnoList = new ArrayList<Map<String, String>>();
        Set<String> idcardnoSet = new HashSet<String>();
        List<Map<String, String>> archiveList = new ArrayList<Map<String, String>>();
        //遍历
        for (Peispatientarchive archive : patientarchives) {
            String archiveId = archive.getId();
            String phone = archive.getPhone() == null ? "" : archive.getPhone().trim();
            //姓名
            String patientname = archive.getPatientname() == null ? "" : archive.getPatientname().trim();
            //身份证号
            String idcardno = archive.getIdcardno() == null ? "" : archive.getIdcardno().trim();
            // TODO: 2023/2/27 AppUser表，未完成
//			getBeanByCriteria(AppUser.class, Restrictions.eq("daid",archiveId))!=null
            if (true) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("id", archive.getId());
                m.put("text", archive.getPatientarchiveno());
                m.put("phone", phone);
                m.put("patientname", patientname);
                m.put("idcardno", idcardno);
                archiveList.add(m);
            }
            if (StringUtils.isNotEmpty(patientname) && !patientnameSet.contains(patientname)) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("id", patientname);
                m.put("text", patientname);
                patientnameList.add(m);
                patientnameSet.add(patientname);
            }
            if (StringUtils.isNotEmpty(phone) && !phoneSet.contains(phone)) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("id", phone);
                m.put("text", phone);
                phoneList.add(m);
                phoneSet.add(phone);
            }
            if (StringUtils.isNotEmpty(idcardno) && !idcardnoSet.contains(idcardno)) {
                Map<String, String> m = new HashMap<String, String>();
                m.put("id", idcardno);
                m.put("text", idcardno);
                idcardnoList.add(m);
                idcardnoSet.add(idcardno);
            }
        }
        //用户可以选择要保留的 姓名 电话  身份证号  ，并可以自由录入
        data.put("patientnames", patientnameList);
        data.put("phoneList", phoneList);
        data.put("idcardnoList", idcardnoList);
        data.put("archiveList", archiveList);
        data.put("isChooseArchive", archiveList.size() > 1);
        //如果只有一条关联app的档案，默认就保留这一条，如果有多条，由用户选择保留哪一条
        if (archiveList.size() == 1) {
            String id = archiveList.get(0).get("id");
            data.put("id", id);
        }
        return R.ok(data);
    }


    /**
     * 合并档案
     *
     * @param param 实体对象
     * @return 新增结果
     */
    @PostMapping("/saveMerge")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "合并档案", notes = "合并时，选择电话、姓名、身份证号。")
    @Log(title = "体检者档案表", businessType = BusinessType.INSERT)
    public R saveMerge(@RequestBody SaveMergeParam param) {
        Boolean b = electronicReportService.saveMerge(param);
        return R.toResult(b);
    }


    /**
     * 档案下体检者明细数据
     * @param pageParamSimple
     * @param patientarchiveno
     * @return
     */
    @GetMapping("/getEleInfoListData")
    @ApiImplicitParam(name = "patientarchiveno", value = "档案号")
    @ApiOperation(value = "档案下体检者明细数据", notes = "档案下体检者明细数据")
    public R<IPage<EleInfoListVo>> getEleInfoListData(PageParamSimple pageParamSimple, String patientarchiveno) {
        PageParam<EleInfoListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<EleInfoListVo> iPage = electronicReportService.getEleInfoListData(page,patientarchiveno);
        return R.ok(iPage);
    }

}

