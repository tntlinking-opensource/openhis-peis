package com.center.medical.report.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.AjaxResult;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.file.FileUploadUtils;
import com.center.medical.common.utils.file.FileUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.report.bean.model.PrintNotice;
import com.center.medical.report.bean.param.PrintNoticeParam;
import com.center.medical.report.bean.vo.PrintNoticeVo;
import com.center.medical.report.service.PrintNoticeService;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 职业结果告知书表控制层
 *
 * @author 路飞船长
 * @since 2022-11-23 17:11:37
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业结果告知书")
@RequestMapping("report/printNotice")
public class PrintNoticeController extends BaseController {
    /**
     * 服务对象
     */
    private final PrintNoticeService printNoticeService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;

    /**
     * 分页查询所有数据
     *
     * @param pageParamSimple 分页参数
     * @param param           查询条件
     * @return 所有数据
     */
    @GetMapping("/page")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "分页查询", notes = "分页查询职业结果告知书")
    public R<IPage<PrintNoticeVo>> getPage(PageParamSimple pageParamSimple, PrintNoticeParam param) {
        PageParam<PrintNoticeVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
        return R.ok(this.printNoticeService.getPrintNoticePage(page, param));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "详情", notes = "根据id查BG报告主表详情")
    @ApiImplicitParam(name = "id", value = "要查询的对象的主键{id}")
    public R<PrintNotice> selectOne(@PathVariable String id) {
        return R.ok(this.printNoticeService.getInfoById(id));
    }


    /**
     * 打印
     *
     * @param param 主键
     * @return 单条数据
     */
    @GetMapping("/print")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "打印", notes = "打印")
    @ApiImplicitParam(name = "param", value = "请求参数")
    public R<PrintNotice> print(PrintNoticeParam param) {
        if(StringUtils.isEmpty(param.getIdPatientclass())) {
            throw new ServiceException("请选择正确的打印类型");
        }
        if (StringUtils.isEmpty(param.getRowsId())) {
            throw new ServiceException("请选择正确的体检号");
        }
        if (StringUtils.isEmpty(param.getAmount())) {
            throw new ServiceException("请确认打印次数");
        }
        boolean b = printNoticeService.print(param);
        return R.toResult(b);
    }





    /**
     * 批量打印
     * @param patientcodes
     * @param idPatientclass
     * @return
     */
    @GetMapping("/printReview")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "批量打印", notes = "批量打印")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcodes", value = "体检号集合"),
            @ApiImplicitParam(name = "idPatientclass", value = "体检者类型ID")
    })
    public R printReview(@RequestParam("patientcodes")List<String> patientcodes,@RequestParam("idPatientclass") String idPatientclass) {
        List<String> sampleUrl = new ArrayList<String>();
        String pdfUrl = "";
        String realPath = FileTypePath.REAL_PATH;
        if(CollectionUtils.isNotEmpty(patientcodes)){
            List<Peispatient> patients = peispatientService.list(new QueryWrapper<Peispatient>()
                    .in("patientcode", patientcodes));
            if("1".equals(idPatientclass)){//复查
                for(Peispatient patient:patients){
                    String reviewPdf = patient.getReviewPdf();
                    if(ObjectUtils.isNotEmpty(reviewPdf)){
                        String path=realPath+reviewPdf;
                        File file = new File(path);
                        if(file.exists()){
                            sampleUrl.add(path);
                        }
                    }
                }
            }else if("2".equals(idPatientclass)){//禁忌症
                for(Peispatient patient:patients){
                    String contraindicated=patient.getContraindicatedPdf();
                    if(ObjectUtils.isNotEmpty(contraindicated)){
                        String path=realPath+contraindicated;
                        File file=new File(path);
                        if(file.exists()){
                            sampleUrl.add(path);
                        }
                    }
                }
            }else if("3".equals(idPatientclass)){//职业病
                for(Peispatient patient:patients){
                    String diseasePdf=patient.getDiseasePdf();
                    if(diseasePdf!=null){
                        String path=realPath+diseasePdf;
                        File file=new File(path);
                        if(file.exists()){
                            sampleUrl.add(path);
                        }
                    }
                }
            }

            int s=sampleUrl.size();
            if(s==1){
                pdfUrl=sampleUrl.get(0).substring(realPath.length());
            }else if(s>1){
                String savePath=realPath+"\\file\\temp\\review\\"
                        +(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                File dir=new File(savePath);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                savePath=savePath+"\\"+(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))+".pdf";
                // TODO: 2022/12/19 批量打印未完成
//                if(WordConvertPDF.mergePdfFiles(sampleUrl.toArray(new String[s]), savePath)){
//                    pdfUrl=savePath.substring(realPath.length());
//                }else{
//                    throw new ServiceException("合并pdf失败!");
//                }
            }else{
                throw new ServiceException("所选数据还没有生成,请先生成再打印。");
            }
        }else{
            throw new ServiceException("请选择要打印的记录！");
        }
        return R.ok();
    }



    /**
     * 预览
     * @param patientcodes
     * @param idPatientclass
     * @return
     */
    @GetMapping("/sample")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "预览", notes = "预览")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcodes", value = "体检号集合"),
            @ApiImplicitParam(name = "idPatientclass", value = "体检者类型ID,1：复查；2：职业禁忌证；3：可疑职业病")
    })
    public R sample(PageParamSimple pageParamSimple,@RequestParam("patientcodes")List<String> patientcodes,@RequestParam("idPatientclass")String idPatientclass) {
        PageParam<Peispatient> page = mapperFacade.map(pageParamSimple, PageParam.class);
        //第一个预览标签
        String report = "";
        //所有要预览的体检号内容PDF路径
        List<String> sampleUrl = new ArrayList<>();
        if (patientcodes != null && patientcodes.size()>0) {
            List<Peispatient> pei = printNoticeService.findAllPeispatientByPatientcode(patientcodes,page);
            //1：复查；2：职业禁忌证；3：可疑职业病
            if (CollectionUtils.isNotEmpty(pei) && pei.size()>0) {
                if ("1".equals(idPatientclass)) {
                    report = pei.get(0).getReviewPdf();
                }else if ("2".equals(idPatientclass)) {
                    report = pei.get(0).getContraindicatedPdf();
                } else if ("3".equals(idPatientclass)) {
                    report = pei.get(0).getDiseasePdf();
                }
                //第一条删除
                pei.remove(0);
            }
            if (ObjectUtils.isNotEmpty(pei) && pei.size()>0) {

                if ("1".equals(idPatientclass)) {
                    for (Peispatient p : pei) {
                        sampleUrl.add(p.getReviewPdf());
                    }

                }else if ("2".equals(idPatientclass)) {
                    for (Peispatient p : pei) {
                        sampleUrl.add(p.getContraindicatedPdf());
                    }

                } else if ("3".equals(idPatientclass)) {
                    for (Peispatient p : pei) {
                        sampleUrl.add(p.getDiseasePdf());
                    }

                }

            }

        }
        Map<String,Object> data = new HashMap<>();
        data.put("report",report);
        data.put("sampleUrl",sampleUrl);
        return R.ok(data);
    }



    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传", notes = "上传")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // TODO: 2022/11/20 文件上传路径 ？
            // 上传文件路径
//			String filePath = ZhongkangConfig.getUploadPath();
            String url = FileTypePath.NET_URL+"inter/conclusion_ol!receive.action";
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(url, file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

}

