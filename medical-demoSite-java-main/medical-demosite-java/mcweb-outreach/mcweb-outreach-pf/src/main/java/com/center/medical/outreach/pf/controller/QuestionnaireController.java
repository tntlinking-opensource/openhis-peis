package com.center.medical.outreach.pf.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Questionnaire;
import com.center.medical.bean.param.QuestionnaireParam;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.IdcardUtil;
import com.center.medical.reception.service.NewReservationService;
import com.center.medical.report.service.QuestionnaireService;
import com.center.medical.service.PeispatientService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.service.BranchService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 查询手机报告列表
 * 该拦截层之作权限数据认证和数据消毒处理，不对任何业务处理
 *
 * @author 路飞船长
 * @since 2023-02-02 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "问卷标准数据接口")
@RequestMapping("/open/api/v2/questionnaire")
public class QuestionnaireController {
    /**
     * 服务对象
     */
    private final MapperFacade mapperFacade;
    private final SystemConfig systemConfig;

    private final QuestionnaireService questionnaireService;
    private final PeispatientService peispatientService;
    private final NewReservationService newReservationService;
    private final BranchService branchService;



    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping("/saOrUp")
    @ApiOperation(value = "添加或更新", notes = "添加或更新")
    @ApiOperationSupport(ignoreParameters = {"questionnaire.id"})
    public R saOrUp(HttpServletRequest request) {
//        Object dataStr = request.getAttribute("dataStr");
//        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_QUESTIONNAIRE_SAORUP;
////        String url = "http://localhost:8080" + Constants.RT_QUESTIONNAIRE_SAORUP;
//        Map map = JSONUtil.toBean((String) dataStr, HashMap.class);
//        String post = HttpUtil.post(url, map);
//        //log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
//        R result = JSONUtil.toBean(post, R.class);
//        //log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
//        if (R.isError(result)) {
//            return R.fail(result.getCode(), result.getMsg());
//        }
//        return R.ok(result.getData());
        Object dataStr = request.getAttribute("dataStr");
        Questionnaire questionnaire = JSON.parseObject((String) dataStr, Questionnaire.class);
        log.info("问卷添加或更新参数,data：{}", questionnaire);
        //更新体检者信息
        Peispatient peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getShortCode, questionnaire.getPatientcode()));
        if (ObjectUtils.isNotEmpty(peispatient)){
            //校验
            String idcardno = questionnaire.getIdcardno();
            if (ObjectUtils.isNotEmpty(questionnaire.getCountreportoccupationxml())
                    && questionnaire.getCountreportoccupationxml() == 1){
                boolean validCard = IdcardUtil.isValidCard(idcardno);
                if (!validCard){
                    throw new ServiceException("身份证格式不正确！");
                }
            }

            //更新一下体检者的基本信息
            peispatient.setPatientname(questionnaire.getPatientname());
            peispatient.setIdcardno(idcardno);
            peispatient.setCountreportoccupationxml(questionnaire.getCountreportoccupationxml());
            peispatient.setBirthdate(cn.hutool.core.util.IdcardUtil.getBirthDate(idcardno));
            peispatient.setIdSex(Integer.valueOf(questionnaire.getIdSex()));
            peispatientService.updateById(peispatient);
        }

        boolean b = questionnaireService.saveOrUpdate(questionnaire);
        return R.ok(b);
    }




    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping("/getHistory")
    @ApiOperation(value = "获取历史问卷", notes = "获取历史问卷")
    public R getHistory(HttpServletRequest request) {
//        Object dataStr = request.getAttribute("dataStr");
//        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_QUESTIONNAIRE_GETHISTORY;
////        String url = "http://localhost:8080" + Constants.RT_QUESTIONNAIRE_GETHISTORY;
//        String paramStr = (String) dataStr;
//        Map<String, Object> paramMap = new HashMap<String, Object>() {{
//            put("phone", paramStr);
//        }};
//        String post = HttpUtil.post(url, paramMap);
//        //log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
//        R result = JSONUtil.toBean(post, R.class);
//        //log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
//        if (R.isError(result)) {
//            return R.fail(result.getCode(), result.getMsg());
//        }
//        return R.ok(JSONUtil.toJsonStr(result.getData()));
        Object dataStr = request.getAttribute("dataStr");
        String phone = (String) dataStr;
        log.info("获取历史问卷,data：{}", phone);
        List<Questionnaire> list = questionnaireService.list(new LambdaQueryWrapper<Questionnaire>()
                .eq(Questionnaire::getPhone, phone).eq(Questionnaire::getType,0)
                .orderByDesc(Questionnaire::getCreatedate));
        return R.ok(JSONUtil.toJsonStr(list));
    }






    /**
     * 新增数据
     *
     * @return 新增结果
     */
    @PostMapping("/details")
    @ApiOperation(value = "获取详情", notes = "获取详情")
    public R details(HttpServletRequest request) {
//        Object dataStr = request.getAttribute("dataStr");
//        String url = systemConfig.getDomain().getPlatformDomain() + Constants.RT_QUESTIONNAIRE_DETAILS;
////        String url = "http://localhost:8080" + Constants.RT_QUESTIONNAIRE_DETAILS;
//        Map map = JSONUtil.toBean((String) dataStr, HashMap.class);
//        String post = HttpUtil.post(url, map);
//        //log.info("请求地址及参数：{}、{}、{}", url, paramMap, post);
//        R result = JSONUtil.toBean(post, R.class);
//        //log.info("从平台端系统返回的数据result：{}、{}", R.isError(result), result.getData());
//        if (R.isError(result)) {
//            return R.fail(result.getCode(), result.getMsg());
//        }
//        return R.ok(JSONUtil.toJsonStr(result.getData()));
        Object dataStr = request.getAttribute("dataStr");
        QuestionnaireParam param = JSON.parseObject((String) dataStr, QuestionnaireParam.class);
        log.info("获取详情,data：{}", param);
        Boolean b = newReservationService.checkPatientcode(param.getPatientcode(),param.getPhone());
        if (b){
            Questionnaire questionnaire = questionnaireService.getOne(new LambdaQueryWrapper<Questionnaire>()
                    .eq(Questionnaire::getPhone, param.getPhone())
                    .eq(Questionnaire::getPatientcode,param.getPatientcode())
                    .eq(Questionnaire::getType,param.getType())
            );
            Peispatient peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getShortCode, param.getPatientcode()));
            //如过问卷详情是空的,就取体检者的数据
            if (ObjectUtils.isEmpty(questionnaire)){
                questionnaire = new Questionnaire();
                questionnaire.setPatientname(peispatient.getPatientname());
                questionnaire.setIdcardno(peispatient.getIdcardno());
                questionnaire.setCountreportoccupationxml(peispatient.getCountreportoccupationxml());
                questionnaire.setIdSex(ObjectUtils.isEmpty(peispatient.getIdSex()) ? null : peispatient.getIdSex().toString());
                questionnaire.setPhone(peispatient.getPhone());
            }
            String hospitalcode = peispatient.getHospitalcode();
            if (StringUtils.isNotEmpty(hospitalcode)){
                Branch branch = branchService.getOne(new LambdaQueryWrapper<Branch>().eq(Branch::getBranchId, hospitalcode));
                questionnaire.setBranchName(branch.getFzx());
                questionnaire.setFzxid(branch.getBranchId());
            }
            questionnaire.setFUsecodehiden(peispatient.getFUsecodehiden());
            return R.ok(JSONUtil.toJsonStr(questionnaire));
        }else {
            return R.fail();
        }

    }


}
