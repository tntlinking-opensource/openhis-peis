package com.center.medical.sellcrm.controller.sell;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.model.CreateOrderQtxz;
import com.center.medical.bean.vo.MultUploadResultVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.Notificationmethod;
import com.center.medical.data.service.NotificationmethodService;
import com.center.medical.reception.bean.param.ImportPatientBatchParam;
import com.center.medical.reception.service.OrderService;
import com.center.medical.reception.service.SyncCommonService;
import com.center.medical.sellcrm.bean.dto.CreatemealExportXyDto;
import com.center.medical.sellcrm.bean.dto.CreateorderDto;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.bean.param.*;
import com.center.medical.sellcrm.bean.vo.*;
import com.center.medical.sellcrm.service.*;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.CreateOrderQtxzService;
import com.center.medical.service.OrderHistoryStatisticsService;
import com.center.medical.system.bean.vo.GetLeaderTelVo;
import com.center.medical.system.bean.vo.KdzlVo;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysUserService;
import com.center.medical.workflow.bean.model.WorkflowItem;
import com.center.medical.sellcrm.bean.param.NeedChooseParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 客户订单(Createorder)控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 19:04:21
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售-订单")
@RequestMapping("sell/createorder")
public class CreateorderController extends BaseController {
    /**
     * 服务对象
     */
    private final CreateorderService createorderService;
    private final MapperFacade mapperFacade;
    private final SellcustomerService sellcustomerService;
    private final SellpactService sellpactService;
    private final CreatemealService createmealService;
    private final MealanditemService mealanditemService;
    private final OrderandcomboService orderandcomboService;
    private final BranchService branchService;
    private final NotificationmethodService notificationmethodService;
    private final ISysUserService sysUserService;
    private final CreateOrderQtxzService createOrderQtxzService;
    private final OrdersummaryService ordersummaryService;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final OrderService orderService;
    private final OrderHistoryStatisticsService orderHistoryStatisticsService;
    private final SyncCommonService syncCommonService;
    private final SysUserMapper sysUserMapper;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询订单表", position = 1)
    public R<IPage<Createorder>> getPage(PageParamSimple pageParamSimple, CreateorderParam param) {
        PageParam<Createorder> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.createorderService.getPage(page, param));
    }

    /**
     * 根据订单代码获取已审核通过的订单名称列表数据
     *
     * @param key 订单代码
     */
    @GetMapping("/getListByKey")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取已审核通过的订单名称列表数据", notes = "根据订单代码获取已审核通过的订单名称列表数据", position = 2)
    @ApiImplicitParam(name = "key", value = "订单代码")
    public R<List<CreateorderVo>> getListByKey(@RequestParam("key") String key) {
        return R.ok(this.createorderService.getListByKey(key));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查订单表详情", position = 3)
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Createorder> selectOne(@PathVariable String id) {
        return R.ok(this.createorderService.getInfoById(id));
    }


    /**
     * 通过参数获取订单信息
     *
     * @param param 查询参数
     * @return 单条数据
     */
    @GetMapping("/getOrderInfo")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "通过参数获取订单信息", notes = "通过参数获取订单信息", position = 3)
    public R<IPage<CreateorderDto>> getOrderInfo(PageParamSimple pageParamSimple, OrderParam param) {
        PageParam<CreateorderDto> page = mapperFacade.map(pageParamSimple, PageParam.class);
        if (StringUtils.isNotEmpty(param.getDdh())) {
            param.setDdh(param.getDdh().trim());
        }
        if (StringUtils.isNotEmpty(param.getXsjlId())) {
            param.setXsjlId(param.getXsjlId().trim());
        }
        return R.ok(this.createorderService.getOrderInfo(page,param));
    }


    /**
     * 根据套餐id删除关联的收费项目
     *
     * @return 删除结果
     */
    @DeleteMapping("/removeItemsData")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "根据套餐id删除关联的收费项目", notes = "根据套餐id删除关联的收费项目", position = 4)
    @Log(title = "根据套餐id删除关联的收费项目", businessType = BusinessType.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tcrowId", value = "删除收费项目时传递过来的套餐id"),
            @ApiImplicitParam(name = "sfxmId", value = "获取要删除的收费项目的id--拼接串 ")})
    public R removeItemsData(@RequestParam("tcrowId") String tcrowId, @RequestParam("sfxmId") List<String> sfxmId) {
        return R.toResult(this.createorderService.removeItemsData(tcrowId, sfxmId));
    }


    /**
     * 获取客户单位名称
     *
     * @param key
     * @return
     */
    @GetMapping("/getKhdwmcData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取客户单位名称", notes = "获取客户单位名称", position = 5)
    public R getKhdwmcData(PageParamSimple pageParamSimple,String key) {
        PageParam<Sellcustomer> page = mapperFacade.map(pageParamSimple, PageParam.class);
        QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<Sellcustomer>()
                .orderByDesc("createDate")
                .ne("khzt", "2")
                .eq("khzt", "1")
                .eq("is_delete", 0);
        //搜索条件
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(key.trim())) {
            queryWrapper.like("khdwsrm", key.trim());
        }
        //判断是否为领导
        boolean isLeader = SecurityUtils.isLeader();
        if (SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE)) {
            //决策管理查所有的数据
        }else if (isLeader) {
            //是领导,查下级的数据
            List<String> lowerLevelIds = sysUserMapper.getLowerLevelId(SecurityUtils.getUserNo());
            lowerLevelIds.add(SecurityUtils.getUserNo());
            queryWrapper.in("xsjlid", lowerLevelIds);
        } else {
            //不为领导,查询自己下对应的客户
            queryWrapper.eq("xsjlid", SecurityUtils.getUserNo());
        }
        PageParam<Sellcustomer> page1 = sellcustomerService.page(page, queryWrapper);
        return R.ok(page1);
    }


    /**
     * 合同编号
     *
     * @param khdwdhId
     * @param key
     * @return
     */
    @GetMapping("/getHtbhData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "合同编号", notes = "合同编号", position = 6)
    public R getHtbhData(String khdwdhId, String key) {
        List<Sellpact> list = new ArrayList<>();
        String userNo = SecurityUtils.getUserNo();
        //获取当前分中心id
        String fzxId = SecurityUtils.getCId();
        //判断是否为领导
        boolean isLeader = SecurityUtils.isLeader();
        if (!isLeader) {
            //不为领导
            if (khdwdhId != null) {
                list = sellpactService.list(new QueryWrapper<Sellpact>().orderByDesc("createDate")
                        .like("htbh", key.trim().toUpperCase()).eq("xsjlid", userNo)
                        .eq("is_delete", 0).eq("khdwmcid", khdwdhId));
            } else {
                list = sellpactService.list(new QueryWrapper<Sellpact>().orderByDesc("createDate")
                        .like("htbh", key.trim().toUpperCase()).eq("xsjlid", userNo)
                        .eq("is_delete", 0));
            }
        } else {
            //是领导
            if (khdwdhId != null) {
                list = sellpactService.list(new QueryWrapper<Sellpact>().orderByDesc("createDate")
                        .like("htbh", key.trim().toUpperCase()).eq("fzxid", fzxId).eq("is_delete", 0)
                        .eq("khdwmcid", khdwdhId));
            } else {
                list = sellpactService.list(new QueryWrapper<Sellpact>().orderByDesc("createDate")
                        .like("htbh", key.trim().toUpperCase()).eq("fzxid", fzxId).eq("is_delete", 0));
            }
        }
        return R.ok(list);
    }


    /**
     * 获取普通套餐与最小套餐的数据
     *
     * @param param
     * @return
     */
    @GetMapping("/getTcData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取普通套餐与最小套餐的数据", notes = "获取普通套餐与最小套餐的数据(size传100，老系统默认100)", position = 7)
    public R<IPage<Createmeal>> getTcData(PageParamSimple pageParamSimple, CreateorderParam param) {
        PageParam<Createmeal> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Createmeal> list = createmealService.getTcData(page, param);
        return R.ok(list);
    }

    /**
     * 材料上传（多个）
     */
    @PostMapping("/uploads")
    @ApiOperation(value = "材料上传", notes = "材料上传（多个）", position = 8)
    @Log(title = "销售-订单-材料上传（多个）", businessType = BusinessType.OTHER)
    public R uploadFiles(List<MultipartFile> files) {
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
        List<String> urlList = new ArrayList<>();
        List<String> successList = new ArrayList<String>();
        List<String> failList = new ArrayList<String>();
        StringBuilder resultMsg = new StringBuilder();
        for (MultipartFile file : files) {
            String fileName = FileUtil.mainName(file.getOriginalFilename());
            try {
                // 上传文件路径
                Attachment attachment = new Attachment();
                String extName = FileUtil.extName(file.getOriginalFilename());
                attachment.setFileType("销售订单材料");
                attachment.setType(AttachmentType.FILE.value());
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
                log.error("通用上传请求（多个）, 文件<font color='red'>" + fileName + "</font>上传失败，{}", e);
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
     * 材料路径保存
     *
     * @param id   订单号
     * @param urls 材料路径：多个以英文
     * @return
     */
    @PutMapping("/saveClUrl")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "材料路径保存", notes = "材料路径保存", position = 9)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID"),
            @ApiImplicitParam(name = "urls", value = "材料路径集合")
    })
    @Log(title = "销售-订单-材料路径保存", businessType = BusinessType.OTHER)
    public R<Boolean> saveClUrl(@RequestParam String id, @RequestParam List<String> urls) {
        if (StringUtils.isEmpty(id)) {
            throw new ServiceException("请选择一个订单！");
        }
        return R.toResult(createorderService.saveClUrl(id, urls));
    }


    /**
     * 材料下载
     *
     * @return String
     * @Title: downCl
     * @author xuhp
     * @since 2017年9月15日 V 1.0
     */
    @GetMapping("/downCl")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "材料下载", notes = "材料下载", position = 10)
    @ApiImplicitParam(name = "id", value = "订单ID")
    @Log(title = "销售-订单-材料下载", businessType = BusinessType.OTHER)
    public void downCl(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) throws IOException {
        if (StringUtils.isBlank(id)) {
            throw new ServiceException("请选择一个订单！");
        }
        Createorder order = createorderService.getById(id);
        if (Objects.isNull(order)) {
            throw new ServiceException("所选订单已不存在，请检查后重试！");
        }
        String clUrls = order.getClurls();
        if (StringUtils.isBlank(clUrls)) {
            throw new ServiceException("所选订单还未上传过材料！");
        }
        String[] clArr = clUrls.split("\\|");
        List<String> clList = new ArrayList<String>();
        for (String cl : clArr) {
            clList.add(cl);
        }
        log.info("销售-订单-材料下载路径：{}、{}", clArr, clList);
        String result = "";
        attachmentService.downloadZipFromOSS("createOrder_" + order.getId() + ".zip", clList);
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        final File tempZipFile = new File("createOrder_" + order.getId() + ".zip");
        org.apache.commons.io.FileUtils.copyFile(tempZipFile, bos);
        bos.close();
        if (tempZipFile.exists()) {
            tempZipFile.delete();
        }
    }


    /**
     * 获取收费项目
     *
     * @param pageParamSimple
     * @param tcId            套餐id
     * @param tcstate         套餐状态(0:普通套餐、1/2：最小套餐)
     * @return
     */
    @GetMapping("/getItemData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取收费项目", notes = "获取收费项目", position = 11)
    public R getItemData(PageParamSimple pageParamSimple, String tcId, String tcstate) {
        PageParam<ItemDataVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<ItemDataVo> iPage = mealanditemService.getItemData(page, tcId, tcstate);
        return R.ok(iPage);
    }


    /**
     * 订单里面为套餐增加收费项目
     *
     * @param addItemsParam
     * @return
     */
    @PostMapping("/addItems")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "订单里面为套餐增加收费项目", notes = "订单里面为套餐增加收费项目", position = 12)
    public R addItems(AddItemsParam addItemsParam) {
        Boolean b = createorderService.addItems(addItemsParam);
        return R.toResult(b);
    }


    /**
     * 编辑默认加载订单关联的套餐
     *
     * @param pageParamSimple
     * @param ddId
     * @param isCopy
     * @return
     */
    @GetMapping("/getTjtcData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "编辑默认加载订单关联的套餐", notes = "编辑默认加载订单关联的套餐", position = 13)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddId", value = "订单id"),
            @ApiImplicitParam(name = "isCopy", value = "isCopy不等于1时，显示oacid(关联表ID)，spzt(审批状态)")
    })
    public R<IPage<Orderandcombo>> getTjtcData(PageParamSimple pageParamSimple, String ddId, Integer isCopy) {
        PageParam<Orderandcombo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Orderandcombo> ipage = orderandcomboService.getTjtcData(page, ddId, isCopy);
        return R.ok(ipage);
    }


    /**
     * 假删掉选择的订单
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/remove/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "假删掉选择的订单", notes = "假删掉选择的订单", position = 14)
    @Log(title = "订单表", businessType = BusinessType.DELETE)
    public R remove(@PathVariable List<String> ids) {
        return R.toResult(createorderService.removeOrders(ids));
    }


    /**
     * 提交订单
     *
     * @param ddcomId
     * @return
     */
    @PutMapping("/commit")
    @RepeatSubmit(interval = 5000, message = "正在提交中，请勿重复点击！")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "提交订单", notes = "提交订单", position = 15)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ddcomId", value = "获取要提交的订单id"),
            @ApiImplicitParam(name = "approverIds", value = "所有选中的审批人id，多个")
    })
    public R commit(@RequestParam List<String> ddcomId , @RequestParam(required = false) List<String> approverIds) {
        return createorderService.commit(ddcomId, true , approverIds);
    }


    /**
     * 导出
     *
     * @param response
     * @param param
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出", position = 16)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "导出", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, CreateorderParam param) {
        PageParam<Createorder> pages = new PageParam<>();
        pages.setSize(999999999);
        IPage<Createorder> page = createorderService.getPage(pages, param);
        List<Createorder> list = page.getRecords();
        ExcelUtil<Createorder> util = new ExcelUtil<Createorder>(Createorder.class);
        util.exportExcel(response, list, "销售订单数据");
    }


    /**
     * 导出协议套餐ys
     *
     * @param response
     * @param tcId
     */
    @PostMapping("/exportXyTc")
    @ApiOperation(value = "导出协议套餐", notes = "导出协议套餐", position = 17)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @Log(title = "导出", businessType = BusinessType.EXPORT)
    public void exportXyTc(HttpServletResponse response, @RequestParam String tcId) {
        if (ObjectUtils.isEmpty(tcId)) {
            throw new GlobalException("请选择要导出的套餐！");
        }
        List<CreatemealExportXyDto> list = createmealService.getExportXyData(null, tcId);
        ExcelUtil<CreatemealExportXyDto> util = new ExcelUtil<>(CreatemealExportXyDto.class);
        util.exportExcel(response, list, "体检协议套餐套餐信息");
    }


    /**
     * 判断当前登录者是否为领导
     *
     * @return
     */
    @GetMapping("/isLeader")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "判断当前登录者是否为领导", notes = "判断当前登录者是否为领导：返回值为true则表示是领导，反之则不是", position = 18)
    public R isLeader() {
        //获取当前登录用户是否为领导
        Boolean isLeader = SecurityUtils.isLeader();
        //判断是否为领导状态
        return R.ok(isLeader);
    }


    /**
     * 获取分中心信息
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getBranchData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取分中心信息", notes = "获取分中心信息", position = 19)
    public R<IPage<Branch>> getBranchData(PageParamSimple pageParamSimple, String key) {
        PageParam<Branch> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<Branch> iPage = branchService.getBranchData(page, key);
        return R.ok(iPage);
    }


    /**
     * 订单撤回
     *
     * @param ddid
     * @return
     */
    @PutMapping("/undo/{ddid}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "订单撤回", notes = "订单撤回", position = 20)
    @ApiImplicitParam(name = "ddid", value = "订单id")
    public R undo(@PathVariable List<String> ddid) {
        return R.toResult(createorderService.undo(ddid));
    }


    /**
     * 获取审核订单下关联的套餐
     *
     * @param pageParamSimple
     * @param approveTjtcDataParam
     * @return
     */
    @GetMapping("/getApproveTjtcData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$获取审核订单下关联的套餐", notes = "获取审核订单下关联的套餐", position = 21)
    public R<Map> getApproveTjtcData(PageParamSimple pageParamSimple, ApproveTjtcDataParam approveTjtcDataParam) {
        PageParam<Orderandcombo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        Map map = orderandcomboService.getApproveTjtcData(page, approveTjtcDataParam);
        return R.ok(map);
    }


    /**
     * 获取套餐下关联的收费项目
     *
     * @param approveTjtcDataParam
     * @return
     */
    @GetMapping("/getTjtcAndItemData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取套餐下关联的收费项目", notes = "获取套餐下关联的收费项目", position = 22)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apprddId", value = "审核订单id,用于展示包含的套餐")
    })
    public R<List<getTjtcAndItemVo>> getTjtcAndItemData(ApproveTjtcDataParam approveTjtcDataParam) {
        List<getTjtcAndItemVo> list = mealanditemService.getTjtcAndItemData(approveTjtcDataParam);
        return R.ok(list);
    }


    /**
     * 订单审核
     *
     * @param checkOrderParam
     * @return
     */
    @PutMapping("/checkOrder")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "订单审核", notes = "订单审核", position = 23)
    @Log(title = "订单审核", businessType = BusinessType.UPDATE)
    public R checkOrder(CheckOrderParam checkOrderParam) {
        Boolean b = false;
        if ("1".equals(checkOrderParam.getFchange())) {
            //如果是变更审核
            b = createorderService.checkChange(checkOrderParam);
        } else {
            //如果是普通审核
            b = createorderService.checkOrder(checkOrderParam);
        }
        return R.toResult(b);
    }


    /**
     * 判断要编辑的订单是否为“提交”或“审核通过”状态
     *
     * @param isTjOrShtgId
     * @return
     */
    @GetMapping("/isTjOrShtg")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "编辑判断", notes = "判断要编辑的订单是否为“提交”或“审核通过”状态", position = 24)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isTjOrShtgId", value = "判断是否为提交或审核通过状态")
    })
    public R isTjOrShtg(@RequestParam("isTjOrShtgId") String isTjOrShtgId) {
        Boolean b = createorderService.isTjOrShtg(isTjOrShtgId);
        return R.ok(b);
    }


    /**
     * 判断对于已提交与审核通过的订单不能删除
     *
     * @param isRemoveId
     * @return
     */
    @GetMapping("/isRemove")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "删除判断", notes = "判断对于已提交与审核通过的订单不能删除", position = 25)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isRemoveId", value = "判断对于已提交的和审核通过的订单不能进行删除")
    })
    public R isRemove(@RequestParam("isRemoveId") List<String> isRemoveId) {
        String text = createorderService.isRemove(isRemoveId);
        return R.ok(text);
    }


    /**
     * 判断选择的记录是否是【提交】和【审核通过】的,这两种状态不能再提交
     *
     * @param isCommitAndSptgId
     * @return
     */
    @GetMapping("/isCommitAndSptg")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "提交判断", notes = "判断选择的记录是否是【提交】和【审核通过】的,这两种状态不能再提交", position = 26)
    public R isCommitAndSptg(@RequestParam("isCommitAndSptgId") List<String> isCommitAndSptgId) {
        String text = createorderService.isCommitAndSptg(isCommitAndSptgId);
        return R.ok(text);
    }


    /**
     * 判断对于订单状态为：草稿、已撤回、审核通过、审核未通过--状态的订单不能再进行撤回
     *
     * @param isChId
     * @return
     */
    @GetMapping("/isCh")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "撤回判断", notes = "判断对于订单状态为：草稿、已撤回、审核通过、审核未通过--状态的订单不能再进行撤回", position = 27)
    public R isCh(@RequestParam("isChId") List<String> isChId) {
        String text = createorderService.isCh(isChId);
        return R.ok(text);
    }

    /**
     * 获取客户单位电话
     *
     * @param khdwdhId
     * @return
     */
    @GetMapping("/getKhdwdh")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取客户单位电话", notes = "获取客户单位电话", position = 28)
    public R getKhdwdh(String khdwdhId) {
        Sellcustomer sellcustomer = sellcustomerService.getInfoById(khdwdhId);
        return R.ok(ObjectUtils.isEmpty(sellcustomer) ? "" : sellcustomer.getKhdh());
    }


    /**
     * 返回客户从未使用过的套餐和客户单位电话
     *
     * @param khdwdhId
     * @param ids
     * @return
     */
    @GetMapping("/getKhdwdhAndTcs")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "返回客户从未使用过的套餐和客户单位电话", notes = "返回客户从未使用过的套餐和客户单位电话", position = 29)
    public R getKhdwdhAndTcs(@RequestParam(name = "khdwdhId") String khdwdhId, @RequestParam(name = "ids", required = false) List<String> ids) {
        List<Createmeal> createmealList = createmealService.getKhdwdhAndTcs(khdwdhId, ids);
        return R.ok(createmealList);
    }


    /**
     * 获取开单助理
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getKdzl")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取开单助理", notes = "获取开单助理", position = 30)
    public R<IPage<KdzlVo>> getKdzl(PageParamSimple pageParamSimple, String key) {
        PageParam<KdzlVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<KdzlVo> iPage = createorderService.getKdzl(page, key);
        return R.ok(iPage);
    }

    /**
     * 获取通知方式
     *
     * @return
     */
    @GetMapping("/getOrderNotifycationMethods")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取通知方式", notes = "获取通知方式", position = 31)
    public R getOrderNotifycationMethods() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> map = null;
        //通知方式
        Notificationmethod m1 = notificationmethodService.getOne(new QueryWrapper<Notificationmethod>().eq("method_name", "电子版报告"));
        if (m1 != null) {
            map = new HashMap<String, String>();
            map.put("id", m1.getId());
            map.put("text", m1.getMethodName());
            result.add(map);
        }
        m1 = notificationmethodService.getOne(new QueryWrapper<Notificationmethod>().eq("method_name", "团检发"));
        if (m1 != null) {
            map = new HashMap<String, String>();
            map.put("id", m1.getId());
            map.put("text", m1.getMethodName());
            result.add(map);
        }
        m1 = notificationmethodService.getOne(new QueryWrapper<Notificationmethod>().eq("method_name", "团检按个发"));
        if (m1 != null) {
            map = new HashMap<String, String>();
            map.put("id", m1.getId());
            map.put("text", m1.getMethodName());
            result.add(map);
        }
        return R.ok(result);
    }


    /**
     * 保存或编辑订单数据
     *
     * @param param
     * @return
     */
    @PostMapping("/saveOrUpdate")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "保存或编辑订单数据", notes = "保存或编辑订单数据", position = 32)
    public R saveOrUpdate(@RequestBody Createorder param) {
        Boolean b = createorderService.saOrUp(param);
        if (StringUtils.isNotBlank(param.getId()))syncCommonService.syncOrderData(Arrays.asList(param.getId()));
        return R.ok(b);
    }


    /**
     * 检查数据
     *
     * @param param
     * @return
     */
    @GetMapping("/checkDate")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "检查数据", notes = "检查数据", position = 33)
    public R checkDate(CheckDateParam param) {
        if (StringUtils.isEmpty(param.getFzxs()) || StringUtils.isEmpty(param.getJhjqc()) || StringUtils.isEmpty(param.getJhjqd())) {
            return R.ok();
        }
        List<CheckDateVo> list = createorderService.checkDate(param);
        return R.ok(list);
    }


    /**
     * 隐藏展示操作
     *
     * @param paused
     * @param ids
     * @param ddId
     * @return
     */
    @PutMapping("/showOrHide")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "隐藏展示操作", notes = "隐藏展示操作", position = 34)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paused", value = " 0 展示 1 隐藏"),
            @ApiImplicitParam(name = "ids", value = "套餐ids集合"),
            @ApiImplicitParam(name = "ddId", value = "订单id")
    })
    public R showOrHide(@RequestParam Integer paused, @RequestParam List<String> ids, @RequestParam String ddId) {
        Boolean b = createorderService.showOrHide(paused, ids, ddId);
        return R.toResult(b);
    }


    /**
     * 判断选择的记录是否为提交状态
     *
     * @param isCheckId
     * @return
     */
    @GetMapping("/isCheck")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "判断选择的记录是否为提交状态", notes = "提交状态返回error，不是提交状态返回success", position = 35)
    @ApiImplicitParam(name = "isCheckId", value = "订单id")
    public R isCheck(String isCheckId) {
        String state = "error";
        //获取实体
        Createorder createOrder = createorderService.getInfoById(isCheckId);
        if (ObjectUtils.isNotEmpty(createOrder)) {
            if (createOrder.getSpzt() != 3) {
                state = "success";
            }
        }
        return R.ok(state);
    }

    /**
     * 获取当前季度最低折扣
     *
     * @return
     */
    @GetMapping("/getSeasonZk")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取当前季度最低折扣", notes = "获取当前季度最低折扣", position = 36)
    public R getSeasonZk() {
        Double seasonZk = createorderService.getSeasonZk();
        return R.ok(seasonZk);
    }


    /**
     * 订单反审核
     *
     * @param id
     * @return
     */
    @GetMapping("/undoOrder")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$订单反审核", notes = "订单反审核", position = 37)
    public R undoOrder(String id) {
        Boolean b = createorderService.undoOrder(id);
        return R.toResult(b);
    }


    /**
     * 导出套餐
     *
     * @param response
     * @param tcIds
     */
    @PostMapping("/exportTc")
    @ApiOperation(value = "$导出套餐", notes = "导出套餐", position = 38)
//    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @ApiImplicitParam(name = "tcIds", value = "选择导出的套餐ID集合")
    @Log(title = "套餐导出", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, @RequestParam String tcIds) throws IOException {
        createmealService.getExportTc(response, tcIds);
    }


    /**
     * 套餐打印预览
     *
     * @param printId
     * @return
     */
    @GetMapping("/printView")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$套餐打印预览", notes = "返回了订单数据和一些id", position = 39)
    @ApiImplicitParam(name = "printId", value = "订单id")
    public R<Createorder> printView(String printId) {
        Createorder createOrder = createorderService.getInfoById(printId);
        return R.ok(createOrder);
    }


    /**
     * 上传名单-保存
     */
    @PostMapping("/saveUpload")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$上传名单-保存", notes = "上传名单-保存", position = 41)
    public R saveUpload(@RequestBody SaveUploadParam param) {
        Boolean b = createorderService.saveUpload(param);
        return R.toResult(b);
    }


    /**
     * 总结-列表数据
     *
     * @param id
     * @return
     */
    @GetMapping("/addWithOrder")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$总结-列表数据", notes = "总结-列表数据，必须是审核通过状态才有订单名称，订单id之类的", position = 42)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R<Ordersummary> addWithOrder(String id) {

        Ordersummary orderSummary = ordersummaryService.getOne(new QueryWrapper<Ordersummary>().eq("ddid", id));
        if (ObjectUtils.isEmpty(orderSummary)) {
            //订单总结表
            orderSummary = new Ordersummary();
            //订单表
            Createorder order = createorderService.getInfoById(id);
            if (order != null && order.getIsDelete() != 3 && order.getSpzt() == 4) {
                orderSummary.setDdid(id);
                orderSummary.setDdmc(order.getDdmc());
                //客户单位名称ID
                String khdwid = order.getKhdwmcid();
                if (khdwid != null) {
                    //我的客户表
                    Sellcustomer sc = sellcustomerService.getInfoById(khdwid);
                    if (sc != null) {
                        //客户单位名称
                        orderSummary.setKhdwmc(sc.getKhdwmc());
                    }
                }
            }
            //获取当前登录用户的姓名
            String username = SecurityUtils.getUsername();
            orderSummary.setXsjl(username);
        }
        return R.ok(orderSummary);
    }


    /**
     * 总结-保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saOrUpSummary")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$总结-保存", notes = "总结-保存", position = 43)
    public R saOrUpSummary(@RequestBody SaOrUpSumParam param) {
        Boolean b = createorderService.saOrUpSummary(param);
        return R.toResult(b);
    }


    /**
     * 变更-变更订单数据
     *
     * @param id
     * @return
     */
    @GetMapping("/change")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$变更-变更订单数据", notes = "变更-变更订单", position = 44)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R change(String id) {
        Map<String, Object> data = new HashMap<>();
        String fchange = "1";
        data.put("fchange", fchange);
        //获取当前季度最低折扣
        Double seasonZk = createorderService.getSeasonZk();
        data.put("seasonZk", seasonZk);
        //订单表
        Createorder createOrder = createorderService.getInfoById(id);
        if (ObjectUtils.isNotEmpty(createOrder)) {
            //用于接收分中心名称的拼串
            String fzxName = "";
            //用于接收分中心id拼串
            String[] fzxId;
            //获取客户单位名称
            String khdwmc = sellcustomerService.getInfoById(createOrder.getKhdwmcid()).getKhdwmc();
            //获取分中心名称
            fzxId = createOrder.getFzxid().split(",");
            for (int i = 0; i < fzxId.length; i++) {
                fzxName += branchService.getInfoById(Integer.valueOf(fzxId[i])).getFzx() + ",";
            }
            data.put("fzxName", fzxName.substring(0, fzxName.length() - 1));
            data.put("khdwmc", khdwmc);
            //使用：编辑时与新增区分开,方便显示之前添加的套餐
            data.put("state", "edit");
            //编辑时获取提醒方式,"首页提醒"方式不能编辑发送短信手机号
            data.put("sytx", createOrder.getTxfs());
            //对于未签订合同的订单,编辑时合同编号不可编辑
            data.put("sfyqdht", createOrder.getSfyqdht());
        }
        data.put("createOrder", createOrder);
        return R.ok(data);
    }


    /**
     * 变更提交
     *
     * @param ids
     * @return
     */
    @PutMapping("/commitChange")
    @RepeatSubmit(interval = 5000, message = "正在提交中，请勿重复点击！")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$变更提交", notes = "变更提交", position = 45)
    @ApiImplicitParam(name = "ids", value = "订单id集合")
    public R commitChange(@RequestParam List<String> ids,@RequestParam(required = false) List<String> approverIds) {
        Boolean b = createorderService.commitChange(ids,approverIds);
        return R.toResult(b);
    }


    /**
     * 变更撤回
     *
     * @param ids
     * @return
     */
    @PutMapping("/undoChange")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$变更撤回", notes = "变更撤回", position = 46)
    @ApiImplicitParam(name = "ids", value = "订单id集合")
    public R undoChange(@RequestParam List<String> ids) {
        Boolean b = createorderService.undoChange(ids);
        return R.toResult(b);
    }


    /**
     * 变更审批-列表数据
     *
     * @param id
     * @return
     */
    @GetMapping("/approveChange")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$变更审批-列表数据", notes = "变更审批-列表数据", position = 47)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R approveChange(String id) {
        //订单表
        Createorder createOrder = createorderService.getInfoById(id);
        if (ObjectUtils.isNotEmpty(createOrder)) {
            //客户单位名称
            String khdwmc = sellcustomerService.getInfoById(createOrder.getKhdwmcid()).getKhdwmc();
            //获取销售经理名称
            SysUser xsjl = sysUserService.getUserByNo(createOrder.getXsjlid());
            String xsjlName = xsjl == null ? "" : xsjl.getNickName();
            createOrder.setKhdwmc(khdwmc);
            createOrder.setXsjl(xsjlName);
            //分中心中文
            List<Branch> list = branchService.list(new LambdaQueryWrapper<Branch>().in(Branch::getBranchId, createOrder.getFzxid().split(",")));
            if (ObjectUtils.isNotEmpty(list)){
                String fzx = list.stream()
                        .map(Branch::getFzx)
                        .collect(Collectors.joining(", "));
                createOrder.setFzx(fzx);
            }
        }
        createOrder.setFchange("1");


        //去年变动成本率
        String orderHistoryRates = orderHistoryStatisticsService.getOrderHistoryStatisticsStr(createOrder.getKhdwmcid());
        createOrder.setOrderHistoryRates(orderHistoryRates);

        //订单的变动成本率
        Double varCostRate = createorderService.getVarCostRate(id);
        createOrder.setVarCostRate(varCostRate);
        return R.ok(createOrder);
    }


    /**
     * 变更审批-列表数据
     *
     * @param id
     * @return
     */
    @PostMapping("/filesDownload")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$下载名单", notes = "下载名单", position = 48)
    @ApiImplicitParam(name = "id", value = "订单id")
    public void filesDownload(HttpServletResponse response, @RequestParam("id") String id) throws IOException {
        //获取关联的文件路径
        Createorder order = createorderService.getInfoById(id);
        if (ObjectUtils.isEmpty(order)) {
            throw new ServiceException("订单不存在");
        }
        if (StringUtils.isBlank(order.getUrls())) {
            throw new ServiceException("名单不存在");
        }
        //下载
        List<String> pathList = Arrays.asList(order.getUrls().split(","));
        attachmentService.downloadZipFromOSS(order.getDdmc() + ".zip", pathList);
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        final File tempZipFile = new File(order.getDdmc() + ".zip");
        org.apache.commons.io.FileUtils.copyFile(tempZipFile, bos);
        bos.close();
        if (tempZipFile.exists()) {
            tempZipFile.delete();
        }
    }


    /**
     * 变更反审
     *
     * @param id
     * @return
     */
    @PutMapping("/unauditChange")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$变更反审", notes = "变更反审", position = 49)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R unauditChange(@RequestParam String id) {
        Boolean b = createorderService.unauditChange(id);
        return R.toResult(b);
    }


    /**
     * 材料通过
     *
     * @param ids
     * @return
     */
    @PutMapping("/clpassOrUmpass")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$材料通过", notes = "材料通过", position = 50)
    @ApiImplicitParam(name = "ids", value = "订单id集合")
    public R clpassOrUmpass(@RequestParam List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new ServiceException("error@请选择一个或多个订单");
        }
        Boolean b = createorderService.clpassOrUmpass(ids, 1, null);
        return R.ok(b);
    }


    /**
     * 材料驳回
     *
     * @param ids
     * @param clspyj
     * @return
     */
    @PutMapping("/rejectSave")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$材料驳回", notes = "材料驳回", position = 51)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "订单id集合"),
            @ApiImplicitParam(name = "clspyj", value = "材料审批意见")
    })
    public R rejectSave(@RequestParam List<String> ids, @RequestParam String clspyj) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new ServiceException("error@请选择一个或多个订单");
        }
        Boolean b = createorderService.clpassOrUmpass(ids, 2, clspyj);
        return R.ok(b);
    }


    /**
     * 修改发放方式-保存
     *
     * @param ids
     * @param idInformway
     * @return
     */
    @PutMapping("/saveInfo")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$修改发放方式-保存", notes = "修改发放方式-保存", position = 52)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "订单id集合"),
            @ApiImplicitParam(name = "idInformway", value = "发放方式id")
    })
    public R saveInfo(@RequestParam List<String> ids, @RequestParam String idInformway) {
        Boolean b = createorderService.saveInfo(ids, idInformway);
        return R.ok(b);
    }


    /**
     * 变更前台须知
     *
     * @param id
     * @return
     */
    @GetMapping("/testQtxz")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$变更前台须知", notes = "变更前台须知", position = 53)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R testQtxz(String id) {
        //线上变更订单前台须知记录
        List<CreateOrderQtxz> coqs = createOrderQtxzService.list(new QueryWrapper<CreateOrderQtxz>()
                .eq("order_id", id));
        if (coqs.size() >= 2) {
            throw new ServiceException("此订单变更前台须知已达两次，不能再变更前台须知，请变更订单。");
        }
        Createorder createorder = createorderService.getInfoById(id);
        if (ObjectUtils.isEmpty(createorder))throw new ServiceException("订单不存在！");

        return R.ok(createorder.getQtxz());
    }


    /**
     * 变更前台须知-保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saveQtxz")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$变更前台须知-保存", notes = "变更前台须知-保存", position = 54)
    public R saveQtxz(@RequestBody QtxzParam param) {
        Boolean b = createorderService.saveQtxz(param.getOrderId(), param.getQtxz());
        return R.toResult(b);
    }


    /**
     * 编辑开单助理数据
     *
     * @param id
     * @return
     */
    @GetMapping("/addKdzl")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$编辑开单助理数据", notes = "编辑开单助理数据", position = 55)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R addKdzl(String id) {
        Map<String, Object> map = new HashMap<>();
        Createorder createOrder = createorderService.getInfoById(id);
        map.put("id", createOrder.getId());
        map.put("ddmc", createOrder.getDdmc());
        map.put("xsjl", createOrder.getXsjl());
        map.put("createdate", createOrder.getCreatedate());
        map.put("kdzl", createOrder.getKdzl());
        return R.ok(createOrder);
    }


    /**
     * 编辑开单助理保存
     *
     * @param id
     * @param kdzlName
     * @return
     */
    @GetMapping("/saveKdzl")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "$编辑开单助理-保存", notes = "编辑开单助理-保存", position = 56)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id"),
            @ApiImplicitParam(name = "kdzlName", value = "开单助理名称")
    })
    public R saveKdzl(String id, String kdzlName) {
        Boolean b = createorderService.saveKdzl(id, kdzlName);
        return R.toResult(b);
    }


    /**
     * 查看套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/viewCombo")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "查看套餐", notes = "查看套餐", position = 57)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R viewCombo(String id) {
        Createorder createorder = createorderService.getInfoById(id);
        Map map = createorderService.getDataForRequest2(createorder);
        return R.ok(map);
    }


    /**
     * 导入名单-列表数据
     *
     * @param id
     * @return
     */
    @GetMapping("/getListData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "导入名单-列表数据", notes = "列表数据  显示订单下所有人员不含复查,判断重复时不判断复查", position = 58)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R<List<COListDataVo>> getListData(String id) {
        Createorder createorder = createorderService.getInfoById(id);
        if (ObjectUtils.isEmpty(createorder.getDdh())) {
            throw new ServiceException("订单id为空!!");
        }
        List<COListDataVo> list = createorderService.getListData(createorder.getDdh());
        return R.ok(list);
    }


    /**
     * 导入名单-保存
     *
     * @param param
     * @return
     */
    @PostMapping("/saOrUpNameList")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "导入名单-保存", notes = "导入名单-保存", position = 59)
    public R saOrUpNameList(@RequestBody SaOrUpNameListParam param) {
        Boolean b = createorderService.saOrUpNameList(param);
        return R.toResult(b);
    }


    /**
     * 导入名单-上传
     *
     * @param param
     * @return
     */
    @PostMapping("/beachSave2")
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "导入名单-上传", notes = "导入名单-上传")
    @Log(title = "导入名单-导入名单", businessType = BusinessType.IMPORT)
    public R beachSave2(ImportPatientBatchParam param) {
        if (Objects.isNull(param.getFile())) {
            throw new GlobalException("请选择上传文件！");
        }
        String fileName = param.getFile().getOriginalFilename();
        //判断文件后缀
        String endSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (!endSuffix.toLowerCase().endsWith("xls") && !endSuffix.toLowerCase().endsWith("xlsx")) {
            throw new GlobalException("请选择正确的文件类型，必须是以.xls或.xlsx结尾！");
        }
        if (StringUtils.isBlank(param.getId())) {
            throw new GlobalException("订单ID不能为空！");
        }
        return orderService.importPatientBatch(param);
    }


    /**
     * 导入名单-保存
     *
     * @param param
     * @return
     */
    @DeleteMapping("/removeAll")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "导入名单-删除", notes = "导入名单-删除", position = 59)
    public R removeAll(RemoveAllParam param) {
        Boolean b = createorderService.removeAll(param);
        return R.toResult(b);
    }


    /**
     * 获取领导电话
     *
     * @param key
     * @return
     */
    @GetMapping("/getLeaderTel")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取备单审批领导电话", notes = "获取备单审批领导电话", position = 60)
    @ApiImplicitParam(name = "key", value = "搜索条件")
    public R<IPage<GetLeaderTelVo>> getLeaderTel(PageParamSimple pageParamSimple, String key) {
        PageParam<GetLeaderTelVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        IPage<GetLeaderTelVo> iPage = sysUserService.getLeaderTel(page, key);
        return R.ok(iPage);
    }


    /**
     * 获取任务分组
     *
     * @param id
     * @return
     */
    @GetMapping("/getGroup")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取体检者任务分组", notes = "获取体检者任务分组", position = 61)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R<List<Peisorgreservationgroup>> getGroup(String id) {
        List<Peisorgreservationgroup> list = createorderService.getGroup(id);
        return R.ok(list);
    }





    /**
     * 获取套餐
     *
     * @param id
     * @return
     */
    @GetMapping("/getPackage")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取套餐", notes = "获取套餐", position = 62)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R<List<GetPackageVo>> getPackage(String id) {
        Createorder createorder = createorderService.getInfoById(id);
        List<GetPackageVo> list = createorderService.getPackage(createorder);
        return R.ok(list);
    }





    /**
     * 获取变动成本率
     *
     * @param id
     * @return
     */
    @GetMapping("/getVarCostRate")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取变动成本率", notes = "获取变动成本率", position = 62)
    @ApiImplicitParam(name = "id", value = "订单id")
    public R<Double> getVarCostRate(String id) {
        Double b = createorderService.getVarCostRate(id);
        return R.ok(b);
    }



    /**
     * 是否需要选择
     *
     * @param param
     * @return 所有数据
     */
    @GetMapping("/needChoose")
    @ApiOperation(value = "是否需要选择", notes = "现在的审批流，同一个层级可能有多个人，" +
            "需要调用一下这个接口，返回数据需要弹窗选择，没有数据就不用选")
    public R<List<WorkflowItem>> needChoose(@Validated NeedChooseParam param) {
        List<WorkflowItem> list = createorderService.needChoose(param);
        return R.ok(list);
    }




    /**
     * 获取客户单位名称
     *
     * @param key
     * @return
     */
    @GetMapping("/getContractUnitName")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取合同单位名称", notes = "获取合同单位名称", position = 5)
    public R getContractUnitName(PageParamSimple pageParamSimple,String key) {
        PageParam<Sellcustomer> page = mapperFacade.map(pageParamSimple, PageParam.class);
        QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<Sellcustomer>()
                .orderByDesc("createDate")
                .ne("khzt", "2")
                .eq("is_delete", 0);
        //搜索条件
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(key.trim())) {
            queryWrapper.like("khdwsrm", key.trim());
        }
        //判断是否为领导
        boolean isLeader = SecurityUtils.isLeader();
        if (!isLeader) {
            //不为领导,查询自己下对应的客户
            queryWrapper.eq("xsjlid", SecurityUtils.getUserNo());
        } else {
            //是领导,获取本分中心下的客户信息
            queryWrapper.eq("fzxid", SecurityUtils.getCId());
        }
        PageParam<Sellcustomer> page1 = sellcustomerService.page(page, queryWrapper);
        return R.ok(page1);
    }
}

