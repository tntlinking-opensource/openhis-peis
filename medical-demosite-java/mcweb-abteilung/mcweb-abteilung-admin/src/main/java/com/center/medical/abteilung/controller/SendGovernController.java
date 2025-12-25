package com.center.medical.abteilung.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.*;
import com.center.medical.abteilung.service.OutsideChargeItemService;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.OutsidePictrue;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.service.BasexamltemService;
import com.center.medical.data.service.InspectChargeService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.reception.bean.model.OutsideHand;
import com.center.medical.reception.bean.model.OutsideMain;
import com.center.medical.reception.bean.param.RecordMatchParam;
import com.center.medical.reception.bean.param.SendGovernParam;
import com.center.medical.reception.bean.param.SgSaOrUpParam;
import com.center.medical.reception.bean.vo.PictureDataVo;
import com.center.medical.reception.bean.vo.SendGovernVo;
import com.center.medical.reception.service.OutsideCheckinService;
import com.center.medical.reception.service.OutsideHandService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.OutsidePictrueService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.config.SystemConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 外送管理-外送结果上传表控制层
 *
 * @author ay
 * @since 2023-01-04 09:00:50
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "外送管理-外送结果上传")
@RequestMapping("/outside/sendGovern")
public class SendGovernController extends BaseController {
    /**
     * 服务对象
     */
    private final OutsideChargeItemService outsideChargeItemService;
    private final MapperFacade mapperFacade;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final OutsideMainService outsideMainService;
    private final PeispatientService peispatientService;
    private final OutsideCheckinService outsideCheckinService;
    private final InspectChargeService inspectChargeService;
    private final ItemsService itemsService;
    private final OutsideHandService outsideHandService;
    private final BasexamltemService basexamltemService;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final OutsidePictrueService outsidePictrueService;


    /**
     * 【外送管理-外送结果上传】功能接口总结
     */
    @GetMapping("/getInterfaceList")
    @ApiOperation(value = "接口说明", notes = "获取【外送管理-外送结果上传】这个块业务所有接口及接口说明")
    public R<FunctionVo> getInterfaceList() {
        List<InterfaceVo> interfaceVos = Arrays.asList(
                new InterfaceVo("分页查询", "GET", "/outside/sendGovern/page", "09.科室系统->科室管理-外送管理-外送结果上传->分页查询", null),
                new InterfaceVo("判断是否能修改", "GET", "/outside/sendGovern/canEdit", "09.科室系统->科室管理-外送管理-外送结果上传->判断是否能修改", null),
                new InterfaceVo("判断是否能修改通过体检号", "GET", "/outside/sendGovern/canEditByCode", "09.科室系统->科室管理-外送管理-外送结果上传->判断是否能修改", null),
                new InterfaceVo("详情", "GET", "/outside/sendGovern/{id}", "09.科室系统->科室管理-外送管理-外送结果上传->详情", null),
                new InterfaceVo("项目列表", "GET", "/outside/sendGovern/getItemData", "09.科室系统->科室管理-外送管理-外送结果上传->项目列表", null),
                new InterfaceVo("获取与图片结果关联项目", "GET", "/outside/sendGovern/getPictureData", "09.科室系统->科室管理-外送管理-外送结果上传->获取与图片结果关联项目", null),
                new InterfaceVo("外送项目", "GET", "/outside/sendGovern/getWsxmData", "09.科室系统->科室管理-外送管理-外送结果上传->外送项目", null),
                new InterfaceVo("登记外送项目数据获取", "GET", "/outside/sendGovern/getPictureWsxmData", "09.科室系统->科室管理-外送管理-外送结果上传->登记外送项目数据获取", null),
                new InterfaceVo("新增外送登记结果处理-查询", "GET", "/outside/sendGovern/recordMatch", "09.科室系统->科室管理-外送管理-外送结果上传->新增外送登记结果处理-查询", null),
                new InterfaceVo("重名选择页数据获取", "GET", "/outside/sendGovern/getRecordListData", "09.科室系统->科室管理-外送管理-外送结果上传->重名选择页数据获取", null),
                new InterfaceVo("外送登记信息-查询", "GET", "/outside/sendGovern/getMainListData", "09.科室系统->科室管理-外送管理-外送结果上传->外送登记信息-查询", null),
                new InterfaceVo("项目列表-结果-手动输入结果模块项目展示", "GET", "/outside/sendGovern/getAllOutData", "09.科室系统->科室管理-外送管理-外送结果上传->项目列表-结果-手动输入结果模块项目展示", null),
                new InterfaceVo("根据登记外送项目赋页面其他值", "GET", "/outside/sendGovern/getDjOutData", "09.科室系统->科室管理-外送管理-外送结果上传->根据登记外送项目赋页面其他值", null),
                new InterfaceVo("获取已保存项目", "GET", "/outside/sendGovern/getEditData", "09.科室系统->科室管理-外送管理-外送结果上传->获取已保存项目", null),
                new InterfaceVo("保存", "POST", "/outside/sendGovern/saveOrUpdate", "09.科室系统->科室管理-外送管理-外送结果上传->保存", null),
                new InterfaceVo("删除数据", "DELETE", "/outside/sendGovern/{ids}", "09.科室系统->科室管理-外送管理-外送结果上传->删除数据", null),
                new InterfaceVo("上传图片", "POST", "/outside/sendGovern/upload", "09.科室系统->科室管理-外送管理-外送结果上传->上传图片", null)
        );
        return R.ok(new FunctionVo("09.科室系统", "外送管理-外送结果上传", interfaceVos, "09.科室系统"));
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
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public R<IPage<SendGovernVo>> getPage(PageParamSimple pageParamSimple, SendGovernParam param) {
        PageParam<OutsideMain> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.outsideMainService.getPage(page, param));
    }


    /**
     * 判断是否能修改
     *
     * @param id
     * @return
     */
    @GetMapping("/canEdit")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断是否能修改", notes = "判断是否能修改")
    @ApiImplicitParam(name = "id", value = "id")
    public R canEdit(String id) {
        OutsideMain outsideMain = outsideMainService.getInfoById(id);
        if (ObjectUtils.isNotEmpty(outsideMain)) {
            Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", outsideMain.getPatientcode()));
            if (ObjectUtils.isNotEmpty(peispatient)) {
                if ((peispatient.getJktjzt() != null && peispatient.getJktjzt() == 1)) {
                    throw new ServiceException("本体检者检查结果已被" + (peispatient.getDoctorfinalNameR() == null ? "" : peispatient.getDoctorfinalNameR()) + "完成总检，不能修改！如有未检项目也不再进行。");
                } else if (peispatient.getZytjzt() != null && peispatient.getZytjzt() == 1) {
                    throw new ServiceException("本体检者检查结果已被" + (peispatient.getPatientnameencoded() == null ? "" : peispatient.getPatientnameencoded()) + "完成总检，不能修改！如有未检项目也不再进行。");
                } else if (peispatient.getFFinallocked() != null && peispatient.getFFinallocked() == 1) {
                    throw new ServiceException("本体检者检查结果已被" + (peispatient.getIdDoctorapply() == null ? "" : peispatient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
                } else if (peispatient.getIdGuidenurse() != null && peispatient.getIdGuidenurse() == 1) {
                    throw new ServiceException("本体检者检查结果已被" + (peispatient.getParsedassignedsuiteandfi() == null ? "" : peispatient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
                }
            } else {
                throw new ServiceException("错误:体检者信息已不存在!");
            }
        } else {
            throw new ServiceException("外送记录已不存在，请刷新重试!");
        }
        return R.ok();
    }


    /**
     * 判断是否能修改通过体检号
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/canEditByCode")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "判断是否能修改通过体检号", notes = "判断是否能修改通过体检号")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R canEditByCode(String patientcode) {
        Peispatient peispatient = peispatientService.getOne(new QueryWrapper<Peispatient>().eq("patientcode", patientcode));
        if (ObjectUtils.isNotEmpty(peispatient)) {
            if ((peispatient.getJktjzt() != null && peispatient.getJktjzt() == 1)) {
                throw new ServiceException("本体检者检查结果已被" + (peispatient.getDoctorfinalNameR() == null ? "" : peispatient.getDoctorfinalNameR()) + "完成总检，不能修改！如有未检项目也不再进行。");
            } else if (peispatient.getZytjzt() != null && peispatient.getZytjzt() == 1) {
                throw new ServiceException("本体检者检查结果已被" + (peispatient.getPatientnameencoded() == null ? "" : peispatient.getPatientnameencoded()) + "完成总检，不能修改！如有未检项目也不再进行。");
            } else if (peispatient.getFFinallocked() != null && peispatient.getFFinallocked() == 1) {
                throw new ServiceException("本体检者检查结果已被" + (peispatient.getIdDoctorapply() == null ? "" : peispatient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
            } else if (peispatient.getIdGuidenurse() != null && peispatient.getIdGuidenurse() == 1) {
                throw new ServiceException("本体检者检查结果已被" + (peispatient.getParsedassignedsuiteandfi() == null ? "" : peispatient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
            }
        } else {
            throw new ServiceException("错误:体检者信息已不存在!");
        }
        return R.ok();
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查KS科室检查结果表-结论词、小结详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<OutsideMain> selectOne(@PathVariable String id) {
        return R.ok(outsideMainService.getInfoById(id));
    }


    /**
     * 项目列表
     *
     * @param pageParamSimple
     * @param patientcode
     * @return
     */
    @GetMapping("/getItemData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "项目列表", notes = "在新增外送登记处，下方搜索出来的项目列表")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<IPage<GetItemDataVo>> getItemData(PageParamSimple pageParamSimple, String patientcode) {
        PageParam<GetItemDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<GetItemDataVo> iPage = peispatientfeeitemService.getItemData(page, patientcode);
        return R.ok(iPage);
    }


    /**
     * 获取与图片结果关联项目
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getPictureData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取与图片结果关联项目", notes = "获取与图片结果关联项目")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R<List<PictureDataVo>> getPictureData(String patientcode) {
        List<PictureDataVo> list = outsideCheckinService.getPictureData(patientcode);
        return R.ok(list);
    }


    /**
     * 外送项目
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getWsxmData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "外送项目", notes = "外送项目")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "key", value = "输入码")
    })
    public R<IPage<WsxmDataVo>> getWsxmData(PageParamSimple pageParamSimple, String key, String patientcode) {
        PageParam<WsxmDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<WsxmDataVo> iPage = peispatientfeeitemService.getWsxmData(page, key, patientcode);
        return R.ok(iPage);
    }


    /**
     * 登记外送项目数据获取
     *
     * @param pageParamSimple
     * @param key
     * @param patientcode
     * @return
     */
    @GetMapping("/getPictureWsxmData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "登记外送项目数据获取", notes = "登记外送项目数据获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "key", value = "输入码")
    })
    public R<IPage<WsxmDataVo>> getPictureWsxmData(PageParamSimple pageParamSimple, String key, String patientcode) {
        PageParam<WsxmDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<WsxmDataVo> iPage = outsideChargeItemService.getPictureWsxmData(page, key, patientcode);
        return R.ok(iPage);
    }


    /**
     * 新增外送登记结果处理-查询
     *
     * @param param
     * @return
     */
    @GetMapping("/recordMatch")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "新增外送登记结果处理-查询", notes = "新增外送登记结果处理-查询,返回-1表示不存在，返回单条数据表示只有一个，返回0表示有多个")
    public R recordMatch(RecordMatchParam param) {
        HashMap<String, Object> map = outsideMainService.recordMatch(param);
        return R.ok(map);
    }

    /**
     * 重名选择页数据获取
     *
     * @param pageParamSimple
     * @param name
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/getRecordListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "重名选择页数据获取", notes = "重名选择页数据获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称"),
            @ApiImplicitParam(name = "startDate", value = "开始时间"),
            @ApiImplicitParam(name = "endDate", value = "结束时间")
    })
    public R<PageParam<Peispatient>> getRecordListData(PageParamSimple pageParamSimple, String name, Date startDate, Date endDate) {
        PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        QueryWrapper<Peispatient> and = new QueryWrapper<>();
        //名称
        if (ObjectUtils.isNotEmpty(name)) {
            and.like("patientname", name);
        }
        //开始时间
        if (ObjectUtils.isNotEmpty(startDate)) {
            and.ge("sendDate", DateUtil.beginOfDay(startDate));
        }
        //结束时间
        if (ObjectUtils.isNotEmpty(endDate)) {
            and.le("sendDate", DateUtil.endOfDay(endDate));
        }
        PageParam<Peispatient> page1 = peispatientService.page(page, and);
        return R.ok(page1);
    }


    /**
     * 外送登记信息-查询
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getMainListData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "外送登记信息-查询", notes = "外送登记信息-查询，recordMatch返回多条时，调用这个方法查询多条")
    public R<IPage<SendGovernVo>> getMainListData(PageParamSimple pageParamSimple, SendGovernParam param) {
        PageParam<OutsideMain> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(outsideMainService.getMainListData(page, param));
    }


    /**
     * 项目列表-结果-手动输入结果模块项目展示
     *
     * @param patientcode
     * @param idChargeFee
     * @return
     */
    @GetMapping("/getAllOutData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "项目列表-结果-手动输入结果模块项目展示", notes = "项目列表-结果-手动输入结果模块项目展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "idChargeFee", value = "收费项目id"),
    })
    public R<List<AllOutDataVo>> getAllOutData(String patientcode, String idChargeFee) {
        List<AllOutDataVo> list = inspectChargeService.getAllOutData(patientcode, idChargeFee);
        return R.ok(list);
    }


    /**
     * 根据登记外送项目赋页面其他值
     *
     * @param itemId
     * @return
     */
    @GetMapping("/getDjOutData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "根据登记外送项目赋页面其他值", notes = "根据登记外送项目赋页面其他值")
    @ApiImplicitParam(name = "itemId", value = "项目id")
    public R getDjOutData(String itemId) {
        Items pei = itemsService.getInfoById(itemId);
        HashMap result = new HashMap();
        if (ObjectUtils.isNotEmpty(pei)) {
            result.put("idItemName", pei == null ? null : pei.getId());
            result.put("itemName", pei == null ? null : pei.getExamfeeitemName());
            result.put("ksId", pei == null ? null : pei.getIdDepart());
            result.put("ksName", pei == null ? null : pei.getDepartName());
        }
        return R.ok(result);
    }


    /**
     * 获取已保存项目
     *
     * @param patientcode
     * @return
     */
    @GetMapping("/getEditData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取已保存项目", notes = "获取已保存项目")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R getEditData(String patientcode) {
        ArrayList data = new ArrayList();
        //KS外送手动结果表
        List<OutsideHand> list = outsideHandService.list(new QueryWrapper<OutsideHand>().eq("patientcode", patientcode));
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                HashMap<String, Object> record = new HashMap<String, Object>();
                OutsideHand dic = list.get(i);
                record.put("id", dic.getId());
                Items item = itemsService.getInfoById(dic.getIdCharge());
                Basexamltem be = basexamltemService.getInfoById(dic.getIdCheck());
                record.put("idFee", item == null ? null : item.getExamfeeitemName());
                record.put("Check", be == null ? null : be.getExamitemName());
                if (ObjectUtils.isNotEmpty(dic)) {
                    record.put("resultHand", dic.getResultHand());
                    record.put("idCharge", dic.getIdCharge());
                }
                if (ObjectUtils.isNotEmpty(be)) {
                    record.put("ckdz", be.getFIncludemin());
                    record.put("ckgz", be.getFIncludemax());
                    record.put("ts", be.getNote());
                }
                data.add(record);
            }
        }
        return R.ok(data);
    }


    /**
     * 保存
     *
     * @param sgSaOrUpParam
     * @return
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "保存", notes = "保存")
    public R saOrUp(@RequestBody SgSaOrUpParam sgSaOrUpParam) {
        Boolean b = outsideMainService.saOrUp(sgSaOrUpParam);
        return R.ok(b);
    }


    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "假删")
    @Log(title = "外送管理-外送结果上传-删除", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        Boolean b = outsideMainService.delete(ids);
        return R.toResult(b);
    }


    /**
     * 上传图片
     */
    @PostMapping("/upload")
    //@PreAuthorize("@ss.hasPermi('::upload')")
    @Log(title = "外送管理-外送结果上传-上传图片", businessType = BusinessType.OTHER)
    @ApiOperation(value = "上传图片", notes = "上传图片")
    public R<MultUploadResultVo> upload(List<MultipartFile> files) throws Exception {
        // 上传文件路径
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.SPP.value());
        List<String> urlList = new ArrayList<>();
        List<String> successList = new ArrayList<String>();
        List<String> failList = new ArrayList<String>();
        StringBuilder resultMsg = new StringBuilder();
        for (MultipartFile file : files) {
            String fileName = FileUtil.mainName(file.getOriginalFilename());
            try {
                Attachment attachment = new Attachment();
                String extName = FileUtil.extName(file.getOriginalFilename());
                attachment.setFileType("外送结果图片");
                attachment.setType(AttachmentType.IMAGE.value());
                attachment.setBranchId(SecurityUtils.getCId());
                attachment.setCreatedate(new Date());
                attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
                log.info("上传结果：{}、{}", attachment.getId(), attachment);
                urlList.add(attachment.getFilePath());
                successList.add(file.getOriginalFilename());
                resultMsg.append("文件<font color='green'>" + fileName + "</font>上传成功！</br>");
            } catch (Exception e) {
                failList.add(file.getOriginalFilename());
                resultMsg.append("文件<font color='red'>" + fileName + "</font>上传失败！</br>");
                log.error("外送管理-外送结果上传-上传图片, 文件<font color='red'>" + fileName + "</font>上传失败，{}", e);

            }
        }
        MultUploadResultVo result = new MultUploadResultVo();
        result.setSuccessList(successList);
        result.setFailList(failList);
        result.setUrlList(urlList);
        result.setResultMsg(resultMsg.toString());
        return R.ok(result);
    }


    /**
     * 获取图片
     * @return
     */
    @GetMapping("/look")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取图片", notes = "获取图片")
    @ApiImplicitParam(name = "id", value = "id")
    public R look(String id) {
        List<String> list = new ArrayList<String>();
        OutsideMain outsideMain = outsideMainService.getInfoById(id);
        if (ObjectUtils.isNotEmpty(outsideMain)){
            //外送图片
            List<OutsidePictrue> pics = outsidePictrueService.list(new LambdaQueryWrapper<OutsidePictrue>()
                    .eq(OutsidePictrue::getPatientcode,outsideMain.getPatientcode()));
            if (CollectionUtil.isNotEmpty(pics)){
                for(OutsidePictrue pic:pics){
                    list.add(pic.getPictruePosition());
                }
            }
        }
        return R.ok(list);
    }
}

