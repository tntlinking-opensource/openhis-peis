package com.center.medical.machine.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.file.IOUtils;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.annotation.RepeatSubmit;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.machine.bean.dto.RegisterSelectDTO;
import com.center.medical.machine.bean.model.PackageItemEntity;
import com.center.medical.machine.bean.model.RegisteredEntity;
import com.center.medical.machine.bean.param.ModelParam;
import com.center.medical.machine.bean.vo.ResultVo;
import com.center.medical.machine.service.RegistrationMachineService;
import com.center.medical.machine.service.impl.ReadCardServiceImpl;
import com.center.medical.machine.service.impl.RegistrationMachineServiceImpl;
import com.center.medical.pay.bean.model.PayProperties;
import com.center.medical.service.PeispatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 自助登记机(Peispatient)表控制层
 *
 * @author ay
 * @since 2023-05-23 09:30:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "自助登记")
@RequestMapping("machine/registrationMachine")
public class RegistrationMachineController extends BaseController {
    /**
     * 服务对象
     */
    private final RegistrationMachineService registrationMachineService;
    private final MapperFacade mapperFacade;
    private final PeispatientService peispatientService;
    private final PayProperties properties;

    /**
     * 通过主键查询单条数据
     *
     * @param registeredEntity 主键
     * @return 单条数据
     */
    @PostMapping("/register")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "自助登记-身份验证", notes = "自助登记-身份验证")
    public R<ResultVo> register(@RequestBody RegisteredEntity registeredEntity) {
        ResultVo vo = registrationMachineService.register(registeredEntity);
        return R.ok(vo);
    }


    /**
     * 登记信息
     *
     * @param registeredEntity
     * @return
     */
    @GetMapping("/registerInfo")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "自助登记-个人信息", notes = "自助登记-个人信息")
    public R<Peispatient> registerInfo(RegisteredEntity registeredEntity) {
        Peispatient peispatient = registrationMachineService.registerInfo(registeredEntity);
        return R.ok(peispatient);
    }


    /**
     * 登记的体检信息
     *
     * @param patientCodes
     * @return
     */
    @GetMapping("/registerSelect")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "自助登记-选择登记信息", notes = "登记的体检信息")
    public R registerSelect(@RequestParam List<String> patientCodes) {
        List<RegisterSelectDTO> registerSelect = registrationMachineService.registerSelect(patientCodes);
        return R.ok(registerSelect);
    }


    /**
     * 登记的体检信息
     *
     * @param registeredEntity
     * @return
     */
    @PostMapping("/confirmRegister")
    @RepeatSubmit(interval = 3000, message = "正在确认登记，请稍等...")
    @ApiOperation(value = "自助登记-确认登记", notes = "自助登记-确认登记")
    public R<ResultVo> confirmRegister(@RequestBody RegisteredEntity registeredEntity) {
        ResultVo result = registrationMachineService.confirmRegister(registeredEntity);
        return R.ok(result);
    }


    /**
     * 自助登记-打印
     *
     * @param registeredEntity
     * @return
     */
    @PostMapping("/print")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "自助登记-打印", notes = "自助登记-打印")
    public R print(@RequestBody RegisteredEntity registeredEntity) {
        Map<String, Object> result = registrationMachineService.print(registeredEntity);
        return R.ok(result);
    }


    /**
     * 自助登记-完成
     *
     * @param patientcode
     * @return
     */
    @PostMapping("/complete")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "自助登记-完成", notes = "自助登记-完成")
    @ApiImplicitParam(name = "patientcode", value = "体检号")
    public R complete(@RequestBody String patientcode) {
        Map<String, Object> result = registrationMachineService.complete(patientcode);
        RegistrationMachineServiceImpl.map.remove(patientcode);
        ReadCardServiceImpl.nameList.clear();
        ReadCardServiceImpl.photo.clear();
        return R.ok(result);
    }


    /**
     * 自助登记-打印导引单
     *
     * @param param
     * @return
     */
    @PostMapping("/model")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "自助登记-打印导引单数据", notes = "自助登记-打印导引单数据")
    public R model(@RequestBody ModelParam param) {
        List<Map<String, Object>> result = registrationMachineService.getBillModelData(param);
        return R.ok(result);
    }

    /**
     * 引导单模板、条码展示页数据
     *
     * @param cid
     * @return
     */
    @GetMapping("/all")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "引导单模板、条码展示页数据", notes = "引导单模板、条码展示页数据")
    @ApiImplicitParam(name = "cid", value = "分中心id")
    public R all(String cid) {
        Map<String, Object> result = registrationMachineService.all(cid);
        return R.ok(result);
    }


    /**
     * 检查金额
     *
     * @param patientCode
     * @return
     */
    @GetMapping("/checkAmount")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "检查金额", notes = "检查金额")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    public R<ResultVo> checkAmount(String patientCode) {
        ResultVo result = registrationMachineService.checkAmount(patientCode);
        return R.ok(result);
    }


    /**
     * 分组确认项目
     *
     * @param registeredEntity
     * @return
     */
    @GetMapping("/groupConfirmItems")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "分组确认项目", notes = "分组确认项目")
    public R<ResultVo> groupConfirmItems(RegisteredEntity registeredEntity) {
        ResultVo result = registrationMachineService.groupConfirmItems(registeredEntity);
        return R.ok(result);
    }


    /**
     * 获取项目
     *
     * @param patientCode
     * @return
     */
    @GetMapping("/getItemData")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "获取项目", notes = "获取项目")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    public R<PackageItemEntity> getItemData(String patientCode) {
        RegisteredEntity registeredEntity = new RegisteredEntity();
        registeredEntity.setPatientCode(patientCode);
        PackageItemEntity medicalExaminationItem = registrationMachineService.getMedicalExaminationItem(registeredEntity);
        return R.ok(medicalExaminationItem);
    }


    /**
     * 生成付款码等
     *
     * @param patientCode
     * @return
     */
    @GetMapping("/createRegisterPayBody")
    //@PreAuthorize("@ss.hasPermi('::info')")
    @ApiOperation(value = "生成付款码", notes = "生成付款码等")
    @ApiImplicitParam(name = "patientCode", value = "体检号")
    public R createRegisterPayBody(String patientCode) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(properties.getProxyRegisterUrl());
        uriBuilder.addParameter("patientCode", patientCode);
        uriBuilder.addParameter("machine_id", "syy2");
//        uriBuilder.addParameter("tempIds", org.apache.commons.lang3.StringUtils.isEmpty(tempIds) ? "" : tempIds);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(new HttpGet(uriBuilder.build()));
        HttpEntity entity = response.getEntity();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        IOUtils.copy(entity.getContent(), output);
        String result = new String(output.toByteArray(), StandardCharsets.UTF_8);
        return R.ok(JSON.parse(result));
    }


}

