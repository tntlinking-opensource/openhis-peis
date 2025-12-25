package com.center.medical.report.controller;

import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.report.service.ReviewNoticeService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.ReportContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BG报告主表(Report)表控制层
 *
 * @author ay
 * @since 2023-04-28 09:02:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "职业结果告知书-查询")
@RequestMapping("report/reviewNotice")
public class ReviewNoticeController extends BaseController {
    /**
     * 服务对象
     */
    private final ReviewNoticeService reviewNoticeService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;
    private final ReportContentService reportContentService;

    /**
     * 分页查询所有数据
     *
     * @param patientcode          查询条件
     * @return 所有数据
     */
    @GetMapping("/createReview")
    //@PreAuthorize("@ss.hasPermi('::list')")
    @ApiOperation(value = "生成职业结果告知书", notes = "生成职业结果告知书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patientcode", value = "体检号"),
            @ApiImplicitParam(name = "idPatientclass", value = "1可疑职业病,2职业禁忌症,3复查")
    })
    public R createReview(@RequestParam List<String> patientcode ,@RequestParam String idPatientclass) {
        if (StringUtils.isNotEmpty(patientcode)) {
            for (String str : patientcode) {
                Peispatient patient = peispatientService.getByPatientCode(str);
                if (patient == null) {
                    throw new ServiceException("未找到相关人员:" + str);
                }
                //复查通知单数据
                reviewNoticeService.createReview(str,idPatientclass);
            }
        }else {
            throw new ServiceException("请输入体检号");
        }
        return R.ok(Boolean.TRUE);
    }







}

