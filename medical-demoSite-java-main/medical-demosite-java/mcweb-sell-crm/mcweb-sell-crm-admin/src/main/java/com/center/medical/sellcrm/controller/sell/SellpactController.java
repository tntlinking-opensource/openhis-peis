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
import com.center.medical.common.enums.BusinessType;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.model.Sellpact;
import com.center.medical.sellcrm.bean.param.GetKhdwmcDataParam;
import com.center.medical.sellcrm.bean.param.SellpactLoseCustParam;
import com.center.medical.sellcrm.bean.param.SellpactParam;
import com.center.medical.sellcrm.bean.vo.SellpactVo;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.sellcrm.service.SellpactService;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.SavefilepathService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
 * 销售-合同管理控制层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:57
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "销售-合同管理")
@RequestMapping("sell/sellpact")
public class SellpactController extends BaseController {
    /**
     * 服务对象
     */
    private final SellpactService sellpactService;
    private final MapperFacade mapperFacade;
    private final ISysConfigService iSysConfigService;
    private final ISysBranchService iSysBranchService;
    private final SellcustomerService sellcustomerService;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final SavefilepathService savefilepathService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询销售合同维护表", position = 1)
    public R<IPage<Sellpact>> getPage(PageParamSimple pageParamSimple, SellpactParam param) {
        PageParam<Sellpact> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sellpactService.getPage(page, param));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查销售合同维护表详情", position = 2)
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<Sellpact> selectOne(@PathVariable String id) {
        return R.ok(this.sellpactService.getInfoById(id));
    }

    /**
     * 合同文件上传
     */
    @PostMapping("/uploads")
    @ApiOperation(value = "合同上传", notes = "合同文件上传", position = 3)
    @ApiImplicitParam(name = "files", value = "合同文件集合")
    @Log(title = "客户-销售系统-合同管理-合同文件上传", businessType = BusinessType.OTHER)
    public R<MultUploadResultVo> uploadFiles(List<MultipartFile> files) {
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.PFP.value());
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
                attachment.setFileType("销售合同");
                attachment.setType(AttachmentType.FILE.value());
                attachment.setBranchId(SecurityUtils.getCId());
                attachment.setCreatedate(new Date());
                attachmentService.uploadFile(file, attachment, baseDir, extName, null, false);
                log.info("上传结果：{}、{}", attachment.getId(), attachment);

                urlList.add(attachment.getFilePath());
                successList.add(file.getOriginalFilename());
                resultMsg.append("合同文件<font color='green'>" + fileName + "</font>上传成功！</br>");

            } catch (Exception e) {
                failList.add(file.getOriginalFilename());
                resultMsg.append("合同文件<font color='red'>" + fileName + "</font>上传失败！</br>");
                log.error("合同文件上传, 文件<font color='red'>" + fileName + "</font>上传失败，{}", e);
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
     * 合同文件下载
     *
     * @param request
     * @param response
     * @param id       合同ID
     * @throws IOException
     */
    @GetMapping("/download")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "合同下载", notes = "合同文件下载", position = 4)
    @ApiImplicitParam(name = "id", value = "合同ID")
    @Log(title = "销售-合同管理-合同下载", businessType = BusinessType.OTHER)
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id) throws IOException {
        if (StringUtils.isBlank(id)) {
            throw new ServiceException("请选择一份合同！");
        }
        Sellpact sellpact = sellpactService.getById(id);
        if (Objects.isNull(sellpact)) {
            throw new ServiceException("所选合同已不存在，请检查后重试！");
        }
        //获取合同文件
        List<Savefilepath> fileList = savefilepathService.getListByGgid(id);
        if (CollectionUtil.isEmpty(fileList)) {
            throw new ServiceException("所选合同还未上传过合同文件！");
        }
        List<String> pathList = fileList.stream().map(Savefilepath::getFilepath).collect(Collectors.toList());
        log.info("销售-订单-合同下载路径：{}、{}", id, fileList);
        String result = "";
        attachmentService.downloadZipFromOSS("sellPact_" + sellpact.getId() + ".zip", pathList);
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        final File tempZipFile = new File("sellPact_" + sellpact.getId() + ".zip");
        org.apache.commons.io.FileUtils.copyFile(tempZipFile, bos);
        bos.close();
        if (tempZipFile.exists()) {
            tempZipFile.delete();
        }
    }

    /**
     * 新增数据
     *
     * @param sellpact 实体对象
     * @return 新增结果
     */
    @PostMapping
    //@PreAuthorize("@ss.hasPermi('::add')")
    @ApiOperation(value = "添加", notes = "添加销售合同", position = 5)
    @ApiImplicitParam(name = "filePath", value = "合同文件地址：多个以英文逗号[,]隔开")
    @Log(title = "客户-销售系统-合同管理-添加", businessType = BusinessType.INSERT)
    public R insert(@RequestBody Sellpact sellpact) {
        Boolean result = this.sellpactService.saOrUp(sellpact);
        log.info("添加合同：{}", sellpact);
        if (result && StringUtils.isNotBlank(sellpact.getId()) && StringUtils.isNotBlank(sellpact.getFilePath())) {
            savefilepathService.SaveFilePath(new Savefilepath(sellpact.getId(), sellpact.getFilePath(), 1));
        }
        return R.toResult(result);
    }

    /**
     * 修改数据
     *
     * @param sellpact 实体对象
     * @return 修改结果
     */
    @PutMapping
    //@PreAuthorize("@ss.hasPermi('::edit')")
    @ApiOperation(value = "更新", notes = "更新销售合同", position = 6)
    @ApiImplicitParam(name = "filePath", value = "合同文件地址：多个以英文逗号[,]隔开")
    @Log(title = "销售合同维护表", businessType = BusinessType.UPDATE)
    public R update(@RequestBody Sellpact sellpact) {
        Boolean result = this.sellpactService.saOrUp(sellpact);
        log.info("更新合同：{}", sellpact);
        if (result && StringUtils.isNotBlank(sellpact.getId()) && StringUtils.isNotBlank(sellpact.getFilePath())) {
            savefilepathService.SaveFilePath(new Savefilepath(sellpact.getId(), sellpact.getFilePath(), 1));
        }
        return R.toResult(result);
    }

    /**
     * 删除数据
     *
     * @param ids 删除的对象主键{id}集合，多个以英文逗号（,）隔开
     * @return 删除结果
     */
    @DeleteMapping("/{ids}")
    //@PreAuthorize("@ss.hasPermi('::remove')")
    @ApiOperation(value = "删除", notes = "删除销售合同", position = 7)
    @Log(title = "销售合同维护表", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "ids", value = "要删除的对象主键{id}集合，多个以英文逗号（,）隔开")
    public R delete(@PathVariable List<String> ids) {
        //TODO wait 需要删除合同文件
        return R.toResult(this.sellpactService.removeByIds(ids));
    }

    /**
     * 生成合同号(分中心-年份-序列)
     */
    @GetMapping("/getHthData")
    @ApiOperation(value = "生成合同号", notes = "生成合同号(分中心-年份-序列)", position = 8)
    public R<String> getHthData() {
        Calendar calendar = Calendar.getInstance();
        //获取序列值
        String sequence = iSysConfigService.getContractNum();
        //获取当前登录者的分中心名称
        String fzxname = iSysBranchService.getByBranchId(SecurityUtils.getCId()).getFzx();
        //取首字母转换为大写
        String fzxName = ToolUtil.getHanziPinyinHeadChar(fzxname);
        String htbh = fzxName + "-" + calendar.get(Calendar.YEAR) + "-" + sequence;
        return R.ok(htbh);
    }


    /**
     * 流失客户-分页查询
     *
     * @param pageParamSimple
     * @param sellpactLoseCustParam
     * @return
     */
    @GetMapping("/loseCust/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "流失客户-分页查询", notes = "分页查询销售合同维护表", position = 9)
    public R<IPage<SellpactVo>> getLoseCustPage(PageParamSimple pageParamSimple, SellpactLoseCustParam sellpactLoseCustParam) {
        PageParam<SellpactVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.sellpactService.getLoseCustPage(page, sellpactLoseCustParam));
    }


    /**
     * 流失客户-通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/loseCust/{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "流失客户-详情", notes = "根据id查销售合同维护表详情", position = 10)
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<SellpactVo> selectloseCustOne(@PathVariable String id) {
        return R.ok(this.sellpactService.getloseCustInfoById(id));
    }


    /**
     * 根据分中心获取关联的客户信息
     *
     * @param sellpactLoseCustParam
     * @return
     */
    @GetMapping("/loseCust/getKhdwmcData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "流失客户-根据分中心获取关联的客户信息", notes = "流失客户-根据分中心获取关联的客户信息", position = 711)
    public R getKhdwmcData(GetKhdwmcDataParam sellpactLoseCustParam) {
        String fzxId = SecurityUtils.getCId();
        //条件构造器
        QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<Sellcustomer>().orderByDesc("createdate")
                .eq("khzt", "1")
                .eq("fzxid", fzxId).eq("is_delete", 0);
        //有搜索条件
        if (ObjectUtils.isNotEmpty(sellpactLoseCustParam.getKey())) {
            queryWrapper.like("khdwsrm", sellpactLoseCustParam.getKey().trim().toUpperCase());
        }
        List<Sellcustomer> list = sellcustomerService.list(queryWrapper);
        return R.ok(list);
    }


    /**
     * 获取客户单位名称
     *
     * @param key
     * @return
     */
    @GetMapping("/getKhdwmcData")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "获取客户单位名称", notes = "获取客户单位名称", position = 12)
    public R getKhdwmcData(String key) {
        //获取当前分中心id
        String fzxId = SecurityUtils.getCId();
        //判断是否为领导
        boolean isLeader = SecurityUtils.isLeader();
        //去空格大写
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim().toUpperCase();
        }
        //条件构造器
        QueryWrapper<Sellcustomer> queryWrapper = new QueryWrapper<Sellcustomer>()
                .orderByDesc("createDate")
                .ne("khzt", "2")
                .eq("is_delete", 0);
        //key不为空
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.like("khdwsrm", key);
        }
        //判断是否为领导
        if (!isLeader) {
            //不为领导,查询自己下对应的客户
            queryWrapper.eq("xsjlid", SecurityUtils.getUserNo());
        } else {
            //是领导,获取本分中心下的客户信息
            queryWrapper.eq("fzxid", fzxId);

        }
        //查询
        List<Sellcustomer> list = sellcustomerService.list(queryWrapper);
        return R.ok(list);
    }

}

