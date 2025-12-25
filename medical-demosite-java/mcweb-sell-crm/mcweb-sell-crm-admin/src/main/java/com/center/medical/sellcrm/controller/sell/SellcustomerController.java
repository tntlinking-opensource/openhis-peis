package com.center.medical.sellcrm.controller.sell;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.model.Savefilepath;
import com.center.medical.bean.vo.MultUploadResultVo;
import com.center.medical.common.annotation.Log;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.core.domain.entity.SysRole;
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.GlobalException;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.common.utils.poi.ExcelUtil;
import com.center.medical.data.bean.model.ZyHarmClass;
import com.center.medical.data.bean.vo.*;
import com.center.medical.data.service.ZyHarmClassService;
import com.center.medical.finance.bean.model.KdCustomer;
import com.center.medical.finance.service.KdCustomerService;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.SaveCuParam;
import com.center.medical.sellcrm.bean.param.SellcustomerParam;
import com.center.medical.sellcrm.bean.vo.AllOrgVo;
import com.center.medical.system.bean.vo.CenterOrgNameListVo;
import com.center.medical.sellcrm.bean.vo.SCListVo;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.SavefilepathService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysRoleMapper;
import com.center.medical.system.service.BranchService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 客户管理
 *
 * @author ay
 * @since 2022-11-10 18:48:15
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@ApiSort(value = 2)
@Api(tags = "客户关系-客户管理")
@RequestMapping("sell/customer")
public class SellcustomerController extends BaseController {
    /**
     * 服务对象
     */
    private final SellcustomerService sellcustomerService;
    private final SysRoleMapper sysRoleMapper;
    private final SavefilepathService savefilepathService;
    private final BranchService branchService;
    private final MapperFacade mapperFacade;
    private final ZyHarmClassService zyHarmClassService;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;

    private final KdCustomerService kdCustomerService;

    //TODO 权限设置

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页对象
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "分页列表", notes = "分页查询我的客户", position = 1)
    public R<IPage<Sellcustomer>> getPage(PageParamSimple pageParamSimple, SellcustomerParam param) {
        PageParam<Sellcustomer> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sellcustomerService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/info/{id}")
    @PreAuthorize("@ss.hasPermi('customer:customerList:view')")
    @ApiOperation(value = "详情", notes = "根据id查我的客户详情", position = 2)
    public R<Sellcustomer> info(@PathVariable String id) {
        Sellcustomer sellcustomer = sellcustomerService.getInfoById(id);
        //金蝶名称
        if (ObjectUtils.isNotEmpty(sellcustomer.getJindieId())){
            KdCustomer kdCustomer = kdCustomerService.getInfoById(sellcustomer.getJindieId());
            if (ObjectUtils.isNotEmpty(kdCustomer)){
                sellcustomer.setJindieName(kdCustomer.getAccountName());
            }
        }
        return R.ok(sellcustomer);
    }

    /**
     * 新增我的客户
     *
     * @param sellcustomer 实体对象
     * @return 新增结果
     */
    @PostMapping
    @PreAuthorize("@ss.hasPermi('customer:customerList:add')")
    @ApiOperation(value = "新增", notes = "新增我的客户", position = 3)
    @Log(title = "客户关系-客户管理-新增", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"sellcustomer.id"})
    public R insert(@RequestBody Sellcustomer sellcustomer) {
        String id = this.sellcustomerService.saOrUp(sellcustomer);
        List<String> filePaths = sellcustomer.getFilePaths();
        if (ObjectUtils.isNotEmpty(filePaths)) {
            String str = String.join(",", filePaths);
            //上传文件路径数据保存或修改
            savefilepathService.SaveFilePath(new Savefilepath(id, str, 2));
        }
        return R.toResult(Boolean.TRUE);
    }

    /**
     * 更新我的客户信息
     *
     * @param sellcustomer 实体对象
     * @return 修改结果
     */
    @PutMapping
    @PreAuthorize("@ss.hasPermi('customer:customerList:edit')")
    @ApiOperation(value = "更新", notes = "更新我的客户信息", position = 4)
    @Log(title = "客户关系-客户管理-更新", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Sellcustomer sellcustomer) {
        String id = this.sellcustomerService.saOrUp(sellcustomer);
        List<String> filePaths = sellcustomer.getFilePaths();
        if (ObjectUtils.isNotEmpty(filePaths)) {
            String str = String.join(",", filePaths);
            //上传文件路径数据保存或修改
            savefilepathService.SaveFilePath(new Savefilepath(id, str, 2));

        }
        return R.toResult(Boolean.TRUE);
    }


    /**
     * 验证输入的客户名称是否重复
     *
     * @param cusName
     * @return
     */
    @GetMapping("/onBlur")
    @ApiOperation(value = "客户名称是否重复", notes = "验证输入的客户名称是否重复", position = 5)
    @ApiImplicitParam(name = "cusName", value = "客户名称")
    public R onBlur(@RequestParam(name = "cusName") String cusName) {
        cusName = cusName.replaceAll(" ", "");
        String s = sellcustomerService.onBlur(cusName);
        return R.ok(s);
    }


    /**
     * 删除我的客户
     *
     * @param ids 主键集合
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermi('customer:customerList:remove')")
    @ApiOperation(value = "删除", notes = "删除我的客户", position = 6)
    @ApiImplicitParam(name = "ids", value = "要删除的对象id集合，多个以英文逗号（,）隔开")
    @Log(title = "客户关系-客户管理-删除", businessType = BusinessType.DELETE)
    public R delete(@PathVariable List<String> ids) {
        return R.toResult(this.sellcustomerService.delete(ids));
    }


//    /**
//     * 多文件进行下载
//     *
//     * @param res
//     * @param path
//     */
//    @GetMapping("/downLoad")
//    @ApiOperation(value = "文件下载", notes = "多文件进行下载", position = 7)
//    @Log(title = "客户关系-客户管理-文件下载", businessType = BusinessType.EXPORT)
//    public void downLoad(HttpServletResponse res, String path) {
//        sellcustomerService.downLoad(path, res);
//    }
//
//
//    /**
//     * 多文件压缩后进行下载
//     *
//     * @param saveFilePathId
//     * @return
//     */
//    @PostMapping("/compressToDownLoad")
//    @ApiOperation(value = "文件压缩下载", notes = "多文件压缩后进行下载", position = 8)
//    @Log(title = "客户关系-客户管理-文件压缩下载", businessType = BusinessType.EXPORT)
//    public R compressToDownLoad(String saveFilePathId) {
//        String upload = sellcustomerService.compressToDownLoad(saveFilePathId);
//        return R.ok(upload);
//    }

    /**
     * 文件上传（多个）
     */
    @PostMapping("/uploads")
    @PreAuthorize("@ss.hasPermi('customer:customerList:upload')")
    @ApiOperation(value = "文件上传", notes = "文件上传（多个）", position = 9)
    @Log(title = "客户关系-客户管理-文件上传", businessType = BusinessType.IMPORT)
    public R<MultUploadResultVo> uploadFiles(List<MultipartFile> files) throws Exception {
        // 上传文件路径
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
        List<String> urlList = new ArrayList<>();
        List<String> successList = new ArrayList<String>();
        List<String> failList = new ArrayList<String>();
        StringBuilder resultMsg = new StringBuilder();
        for (MultipartFile file : files) {
            String fileName = FileUtil.mainName(file.getOriginalFilename());
            try {
                Attachment attachment = new Attachment();
                String extName = FileUtil.extName(file.getOriginalFilename());
                attachment.setFileType("客户文件");
                attachment.setType(AttachmentType.FILE.value());
                attachment.setBranchId(SecurityUtils.getCId());
                attachment.setCreatedate(new Date());
                attachmentService.uploadFile(file, attachment, baseDir, extName, null, false);
                log.info("上传结果：{}、{}", attachment.getId(), attachment);
                urlList.add(attachment.getFilePath());
                successList.add(file.getOriginalFilename());
                resultMsg.append("文件<font color='green'>" + fileName + "</font>上传成功！</br>");
            } catch (Exception e) {
                failList.add(file.getOriginalFilename());
                resultMsg.append("文件<font color='red'>" + fileName + "</font>上传失败！</br>");
                log.error("客户关系-客户管理-文件上传, 文件<font color='red'>" + fileName + "</font>上传失败，{}", e);
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
     * 文件下载
     *
     * @return String
     * @Title: downCl
     * @author xuhp
     * @since 2017年9月15日 V 1.0
     */
    @GetMapping("/downLoad")
    @PreAuthorize("@ss.hasPermi('customer:customerList:download')")
    @ApiOperation(value = "文件下载", notes = "文件下载", position = 10)
    @ApiImplicitParam(name = "id", value = "客户ID")
    @Log(title = "客户关系-客户管理-文件下载", businessType = BusinessType.OTHER)
    public void downLoad(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) {
        if (StringUtils.isBlank(id)) {
            throw new ServiceException("请选择一个订单！");
        }
        Sellcustomer sellcustomer = sellcustomerService.getById(id);
        if (Objects.isNull(sellcustomer)) {
            throw new ServiceException("所选客户已不存在或已被删除！");
        }
        List<Savefilepath> fileList = savefilepathService.getListByGgid(id);
        if (CollectionUtil.isEmpty(fileList)) {
            throw new ServiceException("所选客户还未上传过文件！");
        }
        List<String> clList = fileList.stream().map(Savefilepath::getFilepath).collect(Collectors.toList());
        log.info("客户关系-客户管理-文件下载路径：{}、{}", fileList, clList);
        try {
            attachmentService.downloadZipFromOSS("customerFile_" + sellcustomer.getId() + ".zip", clList);
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            final File tempZipFile = new File("customerFile_" + sellcustomer.getId() + ".zip");
            org.apache.commons.io.FileUtils.copyFile(tempZipFile, bos);
            bos.close();
            if (tempZipFile.exists()) {
                tempZipFile.delete();
            }
        } catch (Exception e) {
            throw new GlobalException("文件下载失败！");
        }

    }


    /**
     * 导入客户信息
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PreAuthorize("@ss.hasPermi('customer:customerList:import')")
    @PostMapping("/beachSave")
    @ApiOperation(value = "导入", notes = "导入客户信息", position = 10)
    @ApiImplicitParam(name = "file", value = "导入的文件")
    @Log(title = "客户关系-客户管理-导入", businessType = BusinessType.IMPORT)
    public R<String> beachSave(MultipartFile file) throws Exception {
        if (Objects.isNull(file)) {
            throw new GlobalException("请选择上传文件！");
        }
        String extName = FileUtil.extName(file.getOriginalFilename());
        if (!extName.toLowerCase().endsWith("xls") && !extName.toLowerCase().endsWith("xlsx")) {
            throw new GlobalException("请选择正确的文件类型，必须是以.xls或.xlsx结尾！");
        }
        ExcelUtil<Sellcustomer> util = new ExcelUtil<>(Sellcustomer.class);
        List<Sellcustomer> list = util.importExcel(file.getInputStream());
        if (CollectionUtil.isEmpty(list)) {
            throw new GlobalException("文件导入失败：该文件没数据，不能进行导入！");
        }
        String operName = SecurityUtils.getUsername();
        return sellcustomerService.importSellcustomer(list, operName) ? R.ok("上传成功") : R.fail("上传失败！");
    }

    /**
     * 我的客户导出
     *
     * @param response
     * @param param    筛选条件
     */
    @PostMapping("/export")
    @PreAuthorize("@ss.hasPermi('customer:customerList:export')")
    @ApiOperation(value = "导出客户列表", notes = "我的客户列表导出", position = 11)
    @Log(title = "客户关系-客户管理-导出我的客户", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, SellcustomerParam param) {
        try {
            List<SysRole> sysRoles = sysRoleMapper.selectRolePermissionByUserId(getUserId());
            sellcustomerService.export(response, param, sysRoles, "客户列表");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 我的客户导出
     *
     * @param response
     * @param param    筛选条件
     */
    @GetMapping("/exportFormal")
    @PreAuthorize("@ss.hasPermi('customer:formalCustomers:export')")
    @ApiOperation(value = "导出正式客户", notes = "我的客户导出", position = 11)
    @Log(title = "客户关系-客户管理-导出正式客户", businessType = BusinessType.EXPORT)
    public void exportFormal(HttpServletResponse response, SellcustomerParam param) {
        try {
            List<SysRole> sysRoles = sysRoleMapper.selectRolePermissionByUserId(getUserId());
            sellcustomerService.export(response, param, sysRoles, "正式客户");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 客户释放/客户流失
     *
     * @param ids 操作的对象主键id集合
     * @return
     */
    @PostMapping("/setBan")
    @PreAuthorize("@ss.hasAnyPermi('customer:customerList:release,customer:customerList:loss')")
    @ApiOperation(value = "客户释放/客户流失", notes = "客户释放/客户流失", position = 12)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要操作的对象主键id集合，多个以英文逗号隔开"),
            @ApiImplicitParam(name = "state", value = "操作标识：0.客户流失 1.客户释放")
    })
    @Log(title = "客户关系-客户管理-客户释放/客户流失", businessType = BusinessType.UPDATE)
    public R<Boolean> setBan(@RequestParam(name = "ids", required = true) List<String> ids, @RequestParam(name = "state", required = true) Integer state) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要操作的对象");
        }
        return R.ok(sellcustomerService.customerRelease(ids, state));
    }

    /**
     * 客户升级
     *
     * @param ids 操作的对象主键id集合
     * @return
     */
    @PutMapping("/upgrade")
    @PreAuthorize("@ss.hasPermi('customer:customerList:upgrade')")
    @ApiOperation(value = "客户升级", notes = "客户升级", position = 13)
    @ApiImplicitParam(name = "ids", value = "要操作的对象主键id集合，多个以英文逗号隔开")
    @Log(title = "客户关系-客户管理-客户升级", businessType = BusinessType.UPDATE)
    public R upgrade(@RequestParam(name = "ids", required = true) List<String> ids
            ,@RequestParam(name = "centerorgname", required = false) String centerorgname) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new ServiceException("请选择要操作的对象");
        }
        if (StringUtils.isBlank(centerorgname)){
            centerorgname = branchService.getCenterOrgName();
        }
        return R.ok(sellcustomerService.upgrade(ids, centerorgname));
    }


    /**
     * 为已经是正式客户的但没有生成网站账号的客户生成账号
     *
     * @param ids
     * @return
     */
    @PostMapping("/generateAccount")
    @ApiOperation(value = "生成账号", notes = "为已经是正式客户的但没有生成网站账号的客户生成账号", position = 14)
    @ApiImplicitParam(name = "ids", value = "要操作的对象主键id集合，多个以英文逗号隔开")
    @Log(title = "客户关系-客户管理-生成账号", businessType = BusinessType.INSERT)
    public R generateAccount(@RequestBody List<String> ids) {
        if (CollectionUtil.isEmpty(ids)){
            throw new ServiceException("请先选择数据！");
        }
        String result = sellcustomerService.generateAccount(ids);
        return R.ok(result);
    }


    /**
     * 获取所有客户
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getAllOrg")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取所有客户", notes = "获取所有客户")
    @ApiImplicitParam(name = "key", value = "客户单位名称或者输入码")
    public R<IPage<AllOrgVo>> getAllOrg(PageParamSimple pageParamSimple, @RequestParam(name = "key", defaultValue = "") String key) {
        PageParam<AllOrgVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sellcustomerService.getAllOrg(page, key));
    }

    /**
     * 根据输入码获取客户列表
     *
     * @param key
     * @return
     */
    @GetMapping("/getKhdwmcData")
    @ApiOperation(value = "根据输入码获取客户列表", notes = "判断是否为领导,根据输入码获取客户列表", position = 20)
    @ApiImplicitParam(name = "key", value = "客户单位输入码")
    public R getKhdwmcData(PageParamSimple pageParamSimple, String key) {
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


    /**
     * 新增客户/流失客户-返回年份
     *
     * @return
     */
    @GetMapping("/getAllYear")
    @ApiOperation(value = "返回年份", notes = "新增客户/流失客户-返回年份", position = 4)
    public R getAllYear() {
        List list = sellcustomerService.getAllYear();
        return R.ok(list);
    }

    /**
     * 根据年份获取本分中心下的正式客户
     *
     * @param pageParamSimple
     * @param param
     * @return
     */
    @GetMapping("/getListData")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "根据年份获取客户", notes = "根据年份获取本分中心下的正式客户", position = 21)
    public R<IPage<Sellcustomer>> getYearListData(PageParamSimple pageParamSimple, SellcustomerParam param) {
        PageParam<Sellcustomer> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sellcustomerService.getYearListData(page, param));
    }


    /**
     * 获取区域代码
     *
     * @param zoneCode
     * @param level
     * @return
     */
    @GetMapping("/getZoneData")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "获取区域代码", notes = "获取区域代码", position = 22)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zoneCode", value = "区域代码"),
            @ApiImplicitParam(name = "level", value = "区域级别：1.省 2.市 3.区 4.街道")
    })
    public R<List<ZoneVo>> getZoneData(String zoneCode, String level) {
        List<ZoneVo> list = sellcustomerService.getZoneData(zoneCode, level);
        return R.ok(list);
    }


    /**
     * 获取危害因素类别数据
     *
     * @return
     */
    @GetMapping("/getWhyslbData")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "获取危害因素类别数据", notes = "获取危害因素类别数据", position = 23)
    public R<List<ZyHarmClass>> getWhyslbData() {
        List<ZyHarmClass> list = zyHarmClassService.list(new QueryWrapper<ZyHarmClass>().eq("is_delete", 0));
        return R.ok(list);
    }


    /**
     * 获取行业类别代码
     *
     * @param indusTypeCode
     * @param level
     * @return
     */
    @GetMapping("/getIndusData")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "获取行业类别代码", notes = "获取行业类别代码", position = 24)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "indusTypeCode", value = "行业类别代码"),
            @ApiImplicitParam(name = "level", value = "区域级别：1.省 2.市 3.区 4.街道")
    })
    public R<List<IndusDataVo>> getIndusData(String indusTypeCode, String level) {
        List<IndusDataVo> list = sellcustomerService.getIndusData(indusTypeCode, level);
        return R.ok(list);
    }

    /**
     * 获取经济类型
     *
     * @return
     */
    @GetMapping("/getEconomyCode")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "获取经济类型316", notes = "获取经济类型", position = 25)
    public R<List<EconomyCodeVo>> getEconomyCode() {
        List<EconomyCodeVo> list = sellcustomerService.getEconomyCode();
        return R.ok(list);
    }


    /**
     * 获取经济类型
     *
     * @return
     */
    @GetMapping("/getCrptSizeCode")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "获取经济类型317", notes = "获取经济类型", position = 26)
    public R<List<CrptSizeCodeVo>> getCrptSizeCode() {
        List<CrptSizeCodeVo> list = sellcustomerService.getCrptSizeCode();
        return R.ok(list);
    }


    /**
     * 获取所属区域
     *
     * @return
     */
    @GetMapping("/getUnitArea")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "获取所属区域", notes = "获取所属区域", position = 27)
    public R<List<UnitAreaVo>> getUnitArea() {
        List<UnitAreaVo> list = sellcustomerService.getUnitArea();
        return R.ok(list);
    }


    /**
     * 客户跟进
     *
     * @param param
     * @return
     */
    @PostMapping("/saveCustomerFollowData")
    @PreAuthorize("@ss.hasPermi('customer:customerList:follow')")
    @ApiOperation(value = "客户跟进", notes = "客户跟进", position = 28)
    @Log(title = "客户关系-客户跟进", businessType = BusinessType.INSERT)
    @ApiOperationSupport(ignoreParameters = {"teamremind.id"})
    public R saveCustomerFollowData(@RequestBody SaveCuParam param) {
        return R.toResult(this.sellcustomerService.saveCustomerFollowData(param));
    }


    /**
     * 客户跟进-详情
     *
     * @param sellCusId
     * @return
     */
    @GetMapping("/customerFollow")
    @PreAuthorize("@ss.hasPermi('customer:phaseTraacking:query')")
    @ApiOperation(value = "客户跟进-详情", notes = "客户跟进-详情", position = 29)
    @ApiImplicitParam(name = "sellCusId", value = "id")
    public R<Sellcustomer> customerFollow(String sellCusId) {
        Sellcustomer sc = sellcustomerService.getInfoById(sellCusId);
        return R.ok(sc);
    }


    /**
     * 客户升级-判断是否为正式客户
     *
     * @param isZskhId
     * @return
     */
    @GetMapping("/isZskh")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "客户升级-判断是否为正式客户", notes = "客户升级-判断是否为正式客户", position = 30)
    @ApiImplicitParam(name = "isZskhId", value = "id集合")
    public R isZskh(@RequestParam List<String> isZskhId) {
        String s = sellcustomerService.isZskh(isZskhId);
        return R.ok(s);
    }


    /**
     * 获取当前登录用户分中心下正式客户管理信息
     *
     * @param pageParamSimple
     * @param key
     * @return
     */
    @GetMapping("/getListDatas")
//    @PreAuthorize("@ss.hasPermi('sell:sellcustomer:list')")
    @ApiOperation(value = "获取当前登录用户分中心下正式客户管理信息", notes = "获取当前登录用户分中心下正式客户管理信息", position = 31)
    @ApiImplicitParam(name = "key", value = "客户单位输入码")
    public R<IPage<SCListVo>> getYearListData(PageParamSimple pageParamSimple, String key) {
        PageParam<SCListVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sellcustomerService.getListDatas(page, key));
    }


    /**
     * 重置密码
     *
     * @param ids
     * @return
     */
    @PutMapping("/resetPassword")
    @PreAuthorize("@ss.hasPermi('customer:customerList:edit')")
    @ApiOperation(value = "重置密码", notes = "重置密码", position = 32)
    @Log(title = "客户关系-客户管理-重置密码", businessType = BusinessType.UPDATE)
    public R resetPassword(@RequestBody List<String> ids) {
        Boolean b = sellcustomerService.resetPassword(ids);
        return R.toResult(b);
    }





    /**
     * 获取所有客户
     *
     * @return
     */
    @GetMapping("/getCenterOrgNameList")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取当前账号下所有所属组织", notes = "获取当前账号下所有所属组织")
    public R<List<CenterOrgNameListVo>> getCenterOrgNameList() {
        List<CenterOrgNameListVo> list = branchService.getUserCenterOrgNameList(SecurityUtils.getUserNo());
        return R.ok(list);
    }
}

