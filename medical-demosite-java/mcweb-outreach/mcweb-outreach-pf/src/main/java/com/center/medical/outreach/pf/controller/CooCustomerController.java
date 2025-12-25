package com.center.medical.outreach.pf.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.dto.*;
import com.center.medical.bean.enums.OrderCheckStatus;
import com.center.medical.bean.model.NewMoReportDetailsParam;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.ReportContent;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.param.OrderUserParam;
import com.center.medical.bean.param.UserExamDataParam;
import com.center.medical.bean.vo.CustomerOrderVo;
import com.center.medical.bean.vo.OrderUserVo;
import com.center.medical.common.config.Domain;
import com.center.medical.common.constant.HttpStatus;
import com.center.medical.common.constant.RequestFlag;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.page.PageParamSimple;
import com.center.medical.reservation.bean.param.*;
import com.center.medical.outreach.bean.Vo.CustomerVo;
import com.center.medical.outreach.bean.Vo.OrderFzxVo;
import com.center.medical.outreach.bean.Vo.UserDataDto;
import com.center.medical.outreach.bean.Vo.UserItemVo;
import com.center.medical.outreach.bean.param.CooOrderReportParam;
import com.center.medical.outreach.bean.param.UserDateParam;
import com.center.medical.reception.bean.dto.GetNewReDataDto;
import com.center.medical.reception.service.NewReservationService;
import com.center.medical.report.service.MobileReportService;
import com.center.medical.reservation.bean.dto.AppointmentDto;
import com.center.medical.reservation.bean.dto.ReservationTimeDto;
import com.center.medical.reservation.bean.model.Reservation;
import com.center.medical.reservation.bean.vo.ReservationDateVo;
import com.center.medical.reservation.service.ReservationOpenApiService;
import com.center.medical.reservation.service.ReservationService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.service.CreateorderService;
import com.center.medical.sellcrm.service.SellcustomerService;
import com.center.medical.service.PeispatientService;
import com.center.medical.service.ReportContentService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 合作单位开放接口
 * <p>
 * 业务流程：单位信息->单位人员->预约->体检状态通知->报告查询
 * <p>
 * 涉及的接口
 * 生成授权令牌access_token：生成、校验、续期、销毁
 * 单位信息：客户单位信息、客户单位订单列表及详情
 * 单位人员：人员列表、人员详情
 * 预约：新增预约、取消预约、预约变更、预约结果通知
 * 体检状态通知：来检通知、检完通知、报告通知
 * 报告查询：获取报告列表、获取报告详情
 * <p>
 * 接口设计规则：每个接口参数需进行RSA非堆成加密后传输，然后携带上客户授权码（用于保证查的只是这个客户下的订单的数据，防止数据越界查询），
 * 校验体检号中的订单号和授权码绑定的订单号是否一致，一致则校验通过，否则校验失败，参数不合法，每个接口需要设置限流
 * <p>
 * 业务标识 com.center.medical.common.enums.BsFlag.COO_CUSTOMER
 *
 * @author 路飞船长
 * @since 2024-11-06 15:14:06
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "合作单位开放接口")
@RequestMapping("/open/api/v3/cooperate")
public class CooCustomerController extends BaseController {

    /**
     * 服务对象
     */
    private final SellcustomerService sellcustomerService;
    private final CreateorderService createorderService;
    private final PeispatientService peispatientService;
    private final MapperFacade mapperFacade;
    private final NewReservationService newReservationService;
    private final ReservationOpenApiService reservationOpenApiService;
    private final ReservationService reservationService;
    private final ISysBranchService sysBranchService;
    private final ReportContentService reportContentService;
    private final ISysConfigService iSysConfigService;
    private final MobileReportService mobileReportService;

    /**
     * 查询订单客户信息
     *
     * @param request
     * @return
     */
    @PostMapping("/customer")
    @ApiOperation(value = "查询订单客户信息", notes = "查询合作单位在系统备的订单客户信息")
    public R<List<CustomerVo>> getCustomer(HttpServletRequest request) {
        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }
        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        List<Sellcustomer> sellcustomers = sellcustomerService.list(new LambdaQueryWrapper<Sellcustomer>().in(Sellcustomer::getId, customerIds)
                .eq(Sellcustomer::getIsDelete, 0).eq(Sellcustomer::getKhzt, 1));
        if (CollectionUtil.isEmpty(sellcustomers)) {
            return R.fail("当前账户在系统创建体检企业客户不存在或者已被删除！");
        }
        List<CustomerVo> customerVoList = sellcustomers.stream().map(item -> {
            return new CustomerVo(item.getId(), item.getKhdwmc(), item.getKhdwlxr(), item.getKhdh(), item.getXsjl());
        }).collect(Collectors.toList());

        return R.ok(customerVoList);
    }

    /**
     * 查询订单客户的订单
     *
     * @param request
     * @return
     */
    @PostMapping("/orderList")
    @ApiOperation(value = "查询订单客户的订单", notes = "查询合作单位在系统备的订单")
    public R<List<CustomerOrderVo>> getOrderList(HttpServletRequest request) {
        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }
        String dataStr = (String) request.getAttribute("dataStr");

        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        if (StringUtils.isNotBlank(dataStr)) {
            //判断传过来的客户ID是否授权
            List<String> paramIds = Arrays.stream(dataStr.split(",")).collect(Collectors.toList());
            if (customerIds.containsAll(paramIds)) {
                //查询指定客户ID的订单
                customerIds = paramIds;
            } else {
                return R.fail("查询的客户不存在或者尚未授权！");
            }
        }
        List<CustomerOrderVo> customerOrderList = createorderService.getOrderByKHIds(customerIds);
        log.info("合作单位开放接口.查询订单客户的订单：{}", customerOrderList);
        return R.ok(customerOrderList);
    }

    /**
     * 根据订单ID查询订单下的用户列表
     *
     * @param request
     * @return
     */
    @PostMapping("/orderUserList")
    @ApiOperation(value = "查询订单ID下的用户列表", notes = "根据订单ID查询订单下的用户列表")
    public R<IPage<OrderUserVo>> getOrderUserList(HttpServletRequest request) {
        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }
        OrderUserParam params = JSONUtil.toBean((String) request.getAttribute("dataStr"), OrderUserParam.class);
        log.info("合作单位开放接口.查询订单ID下的用户列表params：{}", params);
        if (ObjectUtils.isEmpty(params) || StringUtils.isEmpty(params.getOrderId())) {
            //订单ID不能为空
            return R.fail("订单ID不能为空！");
        }
        String orderId = params.getOrderId();
        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getId, orderId)
                .eq(Createorder::getSpzt, OrderCheckStatus.SHTG.value()).eq(Createorder::getIsDelete, 0)
                .in(Createorder::getKhdwmcid, customerIds));
        if (ObjectUtils.isNotEmpty(createorder)) {
            //已授权，分页查询该订单下所有体检者
            PageParamSimple pageParamSimple = params.getPageParam();
            PageParam<OrderUserVo> page = mapperFacade.map(pageParamSimple, PageParam.class);
            IPage<OrderUserVo> orderUserVoPage = peispatientService.getByDdh(page, params);
            log.info("合作单位开放接口.查询订单ID下的用户列表orderUserVoPage：{}", orderUserVoPage);
            return R.ok(orderUserVoPage);
        } else {
            //订单ID绑定的客户尚未授权
            return R.fail("订单ID不存在或者尚未授权！");
        }

    }


    /**
     * 向订单的分中心发起推送根据订单ID查询订单下的用户的体检数据
     *
     * @param request
     * @return
     */
    @PostMapping("/getUserResults")
    @ApiOperation(value = "推送体检者数据", notes = "向订单的分中心发起推送根据订单ID查询订单下的用户的体检数据")
    public R<String> getUserResults(HttpServletRequest request) {
        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }
        UserExamDataParam params = JSONUtil.toBean((String) request.getAttribute("dataStr"), UserExamDataParam.class);
        log.info("合作单位开放接口.查询订单ID下的用户列表params：{}", params);
        if (ObjectUtils.isEmpty(params) || StringUtils.isEmpty(params.getOrderId())) {
            //订单ID不能为空
            return R.fail("订单ID不能为空！");
        }
        if (StringUtils.isEmpty(params.getCallBack())) {
            //订单ID不能为空
            return R.fail("回调地址不能为空！");
        }
        List<String> orderIds = Arrays.stream(params.getOrderId().split(",")).collect(Collectors.toList());
        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        List<Createorder> orders = createorderService.list(new LambdaQueryWrapper<Createorder>().in(Createorder::getId, orderIds)
                .eq(Createorder::getSpzt, OrderCheckStatus.SHTG.value()).eq(Createorder::getIsDelete, 0)
                .in(Createorder::getKhdwmcid, customerIds));
        if (CollectionUtil.isNotEmpty(orders)) {
            Domain domain = iSysConfigService.getDomain();
            //发送请求到线下
            orders.forEach(order -> {
                List<String> fzxids = Arrays.stream(order.getFzxid().split(",")).collect(Collectors.toList());
                Map<String, Object> param = new HashMap<>();
                params.setOrderId(order.getId());
                param.put("params", JSONUtil.toJsonStr(params));
                param.put("online", 1);
                param.put("authCode", authInfo.getAuthCode());
                param.put("branchId", "2");
                param.put("bsFlag" , authInfo.getBsFlag());
                param.put("bsFlagNum", RequestFlag.PUSH_DATA_TO_COO);
                //向各中心下发请求
                String post = HttpUtil.post(domain.getPlatformDomain()+"/open/api/request/sent", param);
                log.info("请求结果：{}，{}", domain.getPlatformDomain()+"/open/api/request/sent", post);
            });

            //根据订单ID获取体检者数据
//            List<UserExamDataDto> userExamDataList = getUserExamData(orders,params);
//            log.info("合作单位开放接口.查询订单ID下的客户体检数据列表userExamDataList：{}", userExamDataList);
//            Map<String, Object> result = new HashMap<>();
            //生成签名：业务方授权码+业务标识+请求标识，拼接字符串后进行MD5（32位大写）加密
//            result.put("sign", MD5.encode(authInfo.getAuthCode() + authInfo.getBsFlag() + params.getFlag()).toUpperCase());
//            result.put("records", userExamDataList);
//            callBack(result, params.getCallBack());
            return R.ok(null, "请求接收成功，结果数据将会在10分钟内推送至回调接口中，请耐心等待。");
        } else {
            //订单ID绑定的客户尚未授权
            return R.fail("订单ID不存在或者尚未授权！");
        }

    }

    /**
     * 根据订单ID获取体检者体检数据
     * @param orders 订单列表
     * @return
     */
    private List<UserExamDataDto> getUserExamData(List<Createorder> orders, UserExamDataParam params) {
        List<UserExamDataDto> userExamDataList = new ArrayList<>();

        // 构造item1的测试值
        UserExamDataDto item1 = new UserExamDataDto();
        item1.setFlag("request_123456");
        item1.setPatientname("张三");
        item1.setPatientcode("PT0001");
        item1.setIdCard("123456789012345678");
        item1.setPhone("13812345678");
        item1.setAddress("北京市朝阳区XX街道XX号");
        item1.setSex(0); // 0表示男性
        item1.setBranchName("西海岸东区中心");
        item1.setCustomerId("CUST_001");
        item1.setOrderId("ORDER_0001");
        item1.setExamType(0); // 0表示健康体检
        item1.setStatus(1); // 1表示已来检
        item1.setDateregister(new Date());
        // 构造检查项目列表数据（ExamItemDataDto列表）
        List<ExamItemDataDto> examItemDataDtoList1 = new ArrayList<>();
        ExamItemDataDto examItem1 = new ExamItemDataDto();
        examItem1.setId("ITEM_001");
        examItem1.setItemName("血常规检查");
        examItem1.setDepartment("检验科");
        examItem1.setResult("各项指标正常");
        examItem1.setConclusion("未见明显异常");
        examItem1.setPositive(0);
        examItem1.setExamDate("2024-12-10 10:00:00");
        examItemDataDtoList1.add(examItem1);
        ExamItemDataDto examItem2 = new ExamItemDataDto();
        examItem2.setId("ITEM_002");
        examItem2.setItemName("心电图检查");
        examItem2.setDepartment("心电图");
        examItem2.setResult("心率正常，波形正常");
        examItem2.setConclusion("心电图未见异常");
        examItem2.setPositive(0);
        examItem2.setExamDate("2024-12-10 11:00:00");
        examItemDataDtoList1.add(examItem2);
        item1.setExamtemList(examItemDataDtoList1);
        // 构造健康总检结果（HealthResultDto）
        HealthResultDto healthResultDto1 = new HealthResultDto();
        healthResultDto1.setSummarize("整体健康状况良好，各项常规检查基本正常。");
        healthResultDto1.setProposal("保持良好生活习惯，定期体检。");
        healthResultDto1.setVerdict("健康");
        healthResultDto1.setPosistive("无阳性发现");
        healthResultDto1.setExamTime("2024-12-10 14:00:00");
        item1.setHealthResult(healthResultDto1);
        userExamDataList.add(item1);

        // 构造item2的测试值
        UserExamDataDto item2 = new UserExamDataDto();
        item2.setFlag("request_7891011");
        item2.setPatientname("李四");
        item2.setPatientcode("PT0002");
        item2.setIdCard("987654321098765432");
        item2.setPhone("13687654321");
        item2.setAddress("上海市浦东新区XX路XX号");
        item2.setSex(1); // 1表示女性
        item2.setBranchName("西海岸东区中心");
        item2.setCustomerId("CUST_002");
        item2.setOrderId("ORDER_0002");
        item2.setExamType(1); // 1表示职业体检
        item2.setStatus(2); // 2表示体检完成
        item2.setDateregister(new Date());
        // 构造检查项目列表数据（ExamItemDataDto列表）
        List<ExamItemDataDto> examItemDataDtoList2 = new ArrayList<>();
        ExamItemDataDto examItem3 = new ExamItemDataDto();
        examItem3.setId("ITEM_003");
        examItem3.setItemName("听力检测");
        examItem3.setDepartment("耳鼻喉科");
        examItem3.setResult("听力正常范围");
        examItem3.setConclusion("听力未见异常");
        examItem3.setPositive(0);
        examItem3.setExamDate("2024-12-10 10:30:00");
        examItemDataDtoList2.add(examItem3);
        ExamItemDataDto examItem4 = new ExamItemDataDto();
        examItem4.setId("ITEM_004");
        examItem4.setItemName("肺功能检测");
        examItem4.setDepartment("肺功能");
        examItem4.setResult("肺功能正常");
        examItem4.setConclusion("肺功能检测正常");
        examItem4.setPositive(0);
        examItem4.setExamDate("2024-12-10 11:30:00");
        examItemDataDtoList2.add(examItem4);
        item2.setExamtemList(examItemDataDtoList2);
        // 构造职业总检结果（OccupResultDto）
        OccupResultDto occupResultDto2 = new OccupResultDto();
        occupResultDto2.setSummarize("职业相关体检项目整体正常，符合岗位健康要求。");
        occupResultDto2.setProposal("持续做好职业健康防护措施。");
        occupResultDto2.setVerdict("符合职业健康标准");
        occupResultDto2.setPosistive("无职业相关阳性发现");
        occupResultDto2.setExamTime("2024-12-10 15:00:00");
        item2.setOccupResult(occupResultDto2);
        userExamDataList.add(item2);

        // 构造一个综合体检的测试数据对象
        UserExamDataDto item = new UserExamDataDto();
        item.setFlag("request_abcdef");
        item.setPatientname("王五");
        item.setPatientcode("PT0003");
        item.setIdCard("345678901234567890");
        item.setPhone("13901234567");
        item.setAddress("广州市天河区XX小区XX栋");
        item.setSex(0); // 0表示男性
        item.setBranchName("西海岸东区中心");
        item.setCustomerId("CUST_003");
        item.setOrderId("ORDER_0003");
        item.setExamType(2); // 2表示综合体检
        item.setStatus(2); // 2表示体检完成
        item.setDateregister(new Date());
        // 构造检查项目列表数据（ExamItemDataDto列表）
        List<ExamItemDataDto> examItemDataDtoList = new ArrayList<>();
        // 内科检查
        ExamItemDataDto examItemInner = new ExamItemDataDto();
        examItemInner.setId("ITEM_005");
        examItemInner.setItemName("内科检查");
        examItemInner.setDepartment("内科");
        examItemInner.setResult("心肺听诊正常，腹部无压痛，肝脾未触及肿大。");
        examItemInner.setConclusion("内科检查未见明显异常");
        examItemInner.setPositive(0);
        examItemInner.setExamDate("2024-12-10 09:00:00");
        examItemDataDtoList.add(examItemInner);
        // 心脏彩超检查
        ExamItemDataDto examItemEcho = new ExamItemDataDto();
        examItemEcho.setId("ITEM_006");
        examItemEcho.setItemName("心脏彩超");
        examItemEcho.setDepartment("彩超");
        examItemEcho.setResult("心脏结构及功能未见异常，各瓣膜未见反流。");
        examItemEcho.setConclusion("心脏彩超正常");
        examItemEcho.setPositive(0);
        examItemEcho.setExamDate("2024-12-10 10:00:00");
        examItemDataDtoList.add(examItemEcho);
        // 电测听检查（职业体检相关）
        ExamItemDataDto examItemAudiometry = new ExamItemDataDto();
        examItemAudiometry.setId("ITEM_007");
        examItemAudiometry.setItemName("电测听");
        examItemAudiometry.setDepartment("电测听");
        examItemAudiometry.setResult("听力阈值在正常范围内。");
        examItemAudiometry.setConclusion("电测听正常，符合职业要求。");
        examItemAudiometry.setPositive(0);
        examItemAudiometry.setExamDate("2024-12-10 11:00:00");
        examItemDataDtoList.add(examItemAudiometry);
        // 肺功能检测（职业体检相关）
        ExamItemDataDto examItemLungFunction = new ExamItemDataDto();
        examItemLungFunction.setId("ITEM_008");
        examItemLungFunction.setItemName("肺功能检测");
        examItemLungFunction.setDepartment("肺功能");
        examItemLungFunction.setResult("肺通气功能正常，弥散功能正常。");
        examItemLungFunction.setConclusion("肺功能检测正常，满足职业健康标准。");
        examItemLungFunction.setPositive(0);
        examItemLungFunction.setExamDate("2024-12-10 12:00:00");
        examItemDataDtoList.add(examItemLungFunction);
        item.setExamtemList(examItemDataDtoList);
        // 构造健康总检结果（HealthResultDto）
        HealthResultDto healthResultDto = new HealthResultDto();
        healthResultDto.setSummarize("综合体检显示身体基本健康，各项重要脏器检查无明显异常。");
        healthResultDto.setProposal("继续保持健康的生活方式，适度运动，合理饮食。");
        healthResultDto.setVerdict("健康");
        healthResultDto.setPosistive("无重大阳性发现");
        healthResultDto.setExamTime("2024-12-10 15:00:00");
        item.setHealthResult(healthResultDto);
        // 构造职业总检结果（OccupResultDto）
        OccupResultDto occupResultDto = new OccupResultDto();
        occupResultDto.setSummarize("职业相关体检项目均正常，符合所从事职业的健康要求。");
        occupResultDto.setProposal("在工作环境中持续做好个人防护，定期进行职业健康复查。");
        occupResultDto.setVerdict("符合职业健康标准");
        occupResultDto.setPosistive("无职业相关阳性发现");
        occupResultDto.setExamTime("2024-12-10 16:00:00");
        item.setOccupResult(occupResultDto);
        userExamDataList.add(item);

        return userExamDataList;

    }

    /**
     * 回调
     *
     * @param result 回调参数
     */
    private void callBack(Map result, String url) {
        String post = HttpUtil.post(url, result);
        log.info("推送结果：{}", post);
    }


    /**
     * 根据体检号和订单号获取体检者数据
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/getUserData")
    @ApiOperation(value = "获取体检者数据", notes = "根据体检号和订单号获取体检者数据")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getUserData(HttpServletRequest request) {

        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }

        UserDateParam userDateParam = JSONUtil.toBean((String) request.getAttribute("dataStr"), UserDateParam.class);
        if (ObjectUtils.isEmpty(userDateParam) || StringUtils.isBlank(userDateParam.getPatientcode())
                || StringUtils.isBlank(userDateParam.getOrderId()) || StringUtils.isBlank(userDateParam.getPhone())) {
            return R.fail("参数不合法！");
        }
        String patientcode = userDateParam.getPatientcode();
        String orderId = userDateParam.getOrderId();
        String phone = userDateParam.getPhone();

        //校验是否授权
        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getId, orderId)
                .eq(Createorder::getSpzt, OrderCheckStatus.SHTG.value()).eq(Createorder::getIsDelete, 0)
                .in(Createorder::getKhdwmcid, customerIds));
        if (ObjectUtils.isEmpty(createorder)) {
            //订单ID绑定的客户尚未授权
            return R.fail("订单ID不存在或者尚未授权！");
        }
        //验证体检号是否有效
        Peispatient peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, patientcode)
                .eq(Peispatient::getNumorgresv, createorder.getDdh()).eq(Peispatient::getPhone, phone).ne(Peispatient::getFPaused, 1));
        if (ObjectUtils.isEmpty(peispatient)) {
            return R.fail("该订单【" + orderId + "】下找不到手机号【" + phone + "】和体检号【" + patientcode + "】的体检者数据！");
        }

        GetNewReDataDto dto = newReservationService.getNewReData(patientcode);
        log.info("合作单位开放接口.获取体检者数据dto：{}", dto);
        UserDataDto userDataDto = mapperFacade.map(dto, UserDataDto.class);
        log.info("合作单位开放接口.获取体检者数据userDataDto：{}", userDataDto);
        List<OrderFzxVo> orderFzxVoList = mapperFacade.mapAsList(dto.getOrderFzxDtoList(), OrderFzxVo.class);
        log.info("合作单位开放接口.获取体检者数据orderFzxVoList：{}", orderFzxVoList);
        userDataDto.setOrderFzxList(orderFzxVoList);
        List<UserItemVo> userItemVoList = mapperFacade.mapAsList(dto.getItems(), UserItemVo.class);
        log.info("合作单位开放接口.获取体检者数据userItemVoList：{}", userItemVoList);
        userDataDto.setUserItemList(userItemVoList);
        userDataDto.setOrderType(dto.getFUsecodehiden());
        userDataDto.setOrderNum(createorder.getId());
        userDataDto.setSex(dto.getIdSex());
        userDataDto.setCardType("");
        log.info("合作单位开放接口.获取体检者数据userDataDto：{}", userDataDto);
        return R.ok(userDataDto);
    }

    public static void main(String[] args) {
        String dataStr = "{\"branchId\":\"123456\",\"endDate\":1735657200000,\"startDate\":1733497200000}";
        ReservationDateParam params = JSONUtil.toBean(dataStr, ReservationDateParam.class);
        System.out.println(JSONUtil.toJsonStr(params));
    }


    /**
     * 获取可预约日期列表
     *
     * @param request
     * @return
     */
    @PostMapping("/reservationDateList")
    @ApiOperation(value = "获取可预约日期列表", notes = "获取可预约日期列表")
    public R<List<ReservationDateVo>> reservationDateList(HttpServletRequest request) {

        ReservationDateParam params = JSONUtil.toBean((String) request.getAttribute("dataStr"), ReservationDateParam.class);
        if (Objects.isNull(params.getStartDate()) || Objects.isNull(params.getEndDate()) || DateUtil.compare(params.getStartDate(), params.getEndDate()) > 0 || DateUtil.compare(params.getEndDate(), new Date()) < 0) {
            return R.fail("日期参数错误！");
        }
        String branchId = params.getBranchId();

        ReservationSettingParam rsParam = new ReservationSettingParam();
        rsParam.setLevelId(1);
        rsParam.setBranchId(branchId);
        rsParam.setStatusList(Arrays.asList(1));
        rsParam.setStartDate(DateUtil.compare(params.getStartDate(), new Date()) < 0 ? DateUtil.beginOfDay(new Date()) : params.getStartDate());
        rsParam.setEndDate(params.getEndDate());
        List<ReservationDateVo> reservationDateList = reservationOpenApiService.getReservationDateList(rsParam);
        log.info("合作单位开放接口.获取可预约日期列表reservationDateList：{}", reservationDateList);
        //从体检者表里根据手机号获取待预约状态登记记录
        return R.ok(reservationDateList);

    }

    /**
     * 获取可预约时间段列表
     *
     * @param request
     * @return
     */
    @PostMapping("/reservationTimeList")
    @ApiOperation(value = "获取可预约时间段列表", notes = "获取指定日期的可预约时间段列表")
    public R getAvailableNums(HttpServletRequest request) {

        String dataStr = (String) request.getAttribute("dataStr");
//        log.info("获取可预约时间段列表,dataStr：{}", dataStr);
        AppointmentAvailableParam param = JSONUtil.toBean(dataStr, AppointmentAvailableParam.class);
//        log.info("获取可预约时间段列表,param：{}", param);
        if (StringUtils.isBlank(param.getBranchId()) || Objects.isNull(param.getReservationDate()) || DateUtil.compare(param.getReservationDate(), new Date()) < 0) {
            return R.fail(HttpStatus.BAD_REQUEST, "参数有误！");
        }
        //判断是否是同一天，是则查询当前时间到今天结束，否则查询一整天
        if (DateUtil.isSameDay(param.getReservationDate(), new Date())) {
            param.setReservationTime(DateUtil.formatTime(DateUtil.date()));
        } else {
            param.setReservationTime("00:00:00");
        }
        param.setLevelId(1);
        List<ReservationTimeDto> reservationTimeList = reservationOpenApiService.getReservationTimeList(param);
        log.info("合作单位开放接口.获取可预约日期列表reservationTimeList：{}", reservationTimeList);
        //从体检者表里根据手机号获取待预约状态登记记录
        return R.ok(reservationTimeList);
    }


    /**
     * 立即预约
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/reservation/apply")
    @ApiOperation(value = "立即预约", notes = "立即预约")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R appointmentNow(HttpServletRequest request) {

        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }
        ReservationApplyParam applyParam = JSONUtil.toBean((String) request.getAttribute("dataStr"), ReservationApplyParam.class);
        log.info("合作单位开放接口.立即预约applyParam：{}", applyParam);

        if (ObjectUtils.isEmpty(applyParam) || StringUtils.isBlank(applyParam.getPatientcode()) || StringUtils.isBlank(applyParam.getOrderNum())) {
            return R.fail("参数错误：订单号、体检号不能为空");
        }
        if (StringUtils.isBlank(applyParam.getMobile())) {
            return R.fail("请填写正确的手机号！");
        }
        if (StringUtils.isBlank(applyParam.getBranchId())) {
            return R.fail("请选择预约的分中心！");
        }
        if (StringUtils.isBlank(applyParam.getTimeId())) {
            return R.fail("请选择预约的时间段！");
        }
        if (StringUtils.isBlank(applyParam.getRealName())) {
            return R.fail("名字不能为空！");
        }
        if (Objects.isNull(applyParam.getCardType())) {
            return R.fail("请选择证件类型！");
        }
        if (StringUtils.isBlank(applyParam.getRealName())) {
            return R.fail("请填写正确的证件号！");
        }
        if (StringUtils.isBlank(applyParam.getRealName())) {
            return R.fail("请填写正确的证件号！");
        }
        if (Objects.isNull(applyParam.getSex())) {
            return R.fail("请选择性别！");
        }

        //校验是否授权
        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getId, applyParam.getOrderNum())
                .eq(Createorder::getSpzt, OrderCheckStatus.SHTG.value()).eq(Createorder::getIsDelete, 0)
                .in(Createorder::getKhdwmcid, customerIds));
        if (ObjectUtils.isEmpty(createorder)) {
            //订单ID绑定的客户尚未授权
            return R.fail("订单ID不存在或者尚未授权！");
        }
        //验证体检号是否有效
        Peispatient peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, applyParam.getPatientcode())
                .eq(Peispatient::getNumorgresv, createorder.getDdh()).eq(Peispatient::getPhone, applyParam.getMobile()).ne(Peispatient::getFPaused, 1));
        if (ObjectUtils.isEmpty(peispatient)) {
            return R.fail("该订单【" + applyParam.getOrderNum() + "】下找不到手机号【" + applyParam.getMobile() + "】和体检号【" + applyParam.getPatientcode() + "】的体检者数据！");
        }

        AppointmentDto data = mapperFacade.map(applyParam, AppointmentDto.class);
        data.setNumorgresv(createorder.getDdh());
        data.setCountreportoccupationxml(applyParam.getCardType());
        //补全信息
        data.setFUsecodehiden(peispatient.getFUsecodehiden());
        data.setComboId(peispatient.getIdTjtc());
        data.setComboName(peispatient.getExamsuiteName());
        data.setIdOrg(peispatient.getIdOrg());
        data.setUserId(authInfo.getAccount());
        data.setSystemId(authInfo.getSourceId());
        data.setStatus(2);

        String reservationNo = newReservationService.appointmentNow(data);
        if (StringUtils.isBlank(reservationNo)) {
            return R.fail("预约失败，请稍后重试！");
        }
        return R.ok(reservationNo, "预约成功！");
    }


    /**
     * 预约取消（预约取消）
     *
     * @param request
     */
    @PostMapping("/reservation/cancel")
    @ApiOperation(value = "预约取消", notes = "预约取消")
    public R<String> cancel(HttpServletRequest request) {
        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }

        String dataStr = (String) request.getAttribute("dataStr");
        //log.info("预约取消,data：{}", dataStr);
        CCReservationCancelParam param = JSONUtil.toBean(dataStr, CCReservationCancelParam.class);
        log.info("合作单位开放接口.预约取消param：{}", param);
        if (ObjectUtils.isEmpty(param) || StringUtils.isBlank(param.getPatientcode())
                || StringUtils.isBlank(param.getOrderNum()) || StringUtils.isBlank(param.getPhone())) {
            return R.fail("参数不合法！");
        }

        //校验是否授权
        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getId, param.getOrderNum())
                .eq(Createorder::getSpzt, OrderCheckStatus.SHTG.value()).eq(Createorder::getIsDelete, 0)
                .in(Createorder::getKhdwmcid, customerIds));
        if (ObjectUtils.isEmpty(createorder)) {
            //订单ID绑定的客户尚未授权
            return R.fail("订单ID不存在或者尚未授权！");
        }
        //验证体检号是否有效
        Peispatient peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, param.getPatientcode())
                .eq(Peispatient::getNumorgresv, createorder.getDdh()).eq(Peispatient::getPhone, param.getPhone()).ne(Peispatient::getFPaused, 1));
        if (ObjectUtils.isEmpty(peispatient)) {
            return R.fail("该订单【" + param.getOrderNum() + "】下找不到手机号【" + param.getPhone() + "】和体检号【" + param.getPatientcode() + "】的体检者数据！");
        }

        ReservationCancelParam rcParam = new ReservationCancelParam();
        rcParam.setFUsecodehiden(peispatient.getFUsecodehiden());
        rcParam.setSystemId(authInfo.getSourceId());
        rcParam.setPhone(param.getPhone());
        rcParam.setFailReason(param.getFailReason());
        rcParam.setModifier(authInfo.getAccount());
        rcParam.setReservationNo(param.getReservationNo());

        //校验
        if (StringUtils.isNotEmpty(rcParam.getPhone()) && StringUtils.isNotEmpty(rcParam.getReservationNo())) {
            long count = reservationService.count(new LambdaQueryWrapper<Reservation>()
                    .eq(Reservation::getReservationNo, rcParam.getReservationNo())
                    .eq(Reservation::getMobile, rcParam.getPhone())
            );
            if (count == 0) {
                throw new ServiceException("该预约号的信息不匹配！");
            }
        }
        if (ObjectUtils.isNotEmpty(rcParam) && StringUtils.isNotEmpty(rcParam.getReservationNo())) {
            Reservation reservation = reservationService.getOne(new LambdaQueryWrapper<Reservation>().eq(Reservation::getReservationNo, rcParam.getReservationNo())
                    .eq(Reservation::getPatientcode, param.getPatientcode()));
            if (ObjectUtils.isEmpty(reservation)) {
                throw new ServiceException("该预约号不存在！");
            }
            if (reservation.getStatus() == 3) {
                throw new ServiceException("该预约信息无法取消！");
            }
            rcParam.setFUsecodehiden(reservation.getFUsecodehiden());
            rcParam.setPcodeOrOrderId(reservation.getFUsecodehiden() == 1 ? reservation.getPatientcode() :
                    "app0715".equals(rcParam.getSystemId()) ? reservation.getPatientcode() :
                            StringUtils.isNotEmpty(reservation.getBizOrderNum()) ? reservation.getBizOrderNum() : reservation.getPatientcode());
        } else {
            throw new ServiceException("数据错误！");
        }

        return reservationOpenApiService.cancel(rcParam);
    }


    /**
     * 根据体检号获取预约详情
     *
     * @param request
     * @return
     */
    @PostMapping("/getReservationByCode")
    @ApiOperation(value = "根据体检号获取预约详情", notes = "根据体检号获取预约详情")
    public R<Reservation> getReservationByCode(HttpServletRequest request) {

        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }

        String dataStr = (String) request.getAttribute("dataStr");
        CCReservationInfoParam param = JSONUtil.toBean(dataStr, CCReservationInfoParam.class);
        log.info("合作单位开放接口.根据体检号获取预约详情param：{}", param);
        if (ObjectUtils.isEmpty(param) || StringUtils.isBlank(param.getPatientcode())
                || StringUtils.isBlank(param.getOrderNum()) || StringUtils.isBlank(param.getPhone())) {
            return R.fail("参数不合法！");
        }

        //校验是否授权
        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getId, param.getOrderNum())
                .eq(Createorder::getSpzt, OrderCheckStatus.SHTG.value()).eq(Createorder::getIsDelete, 0)
                .in(Createorder::getKhdwmcid, customerIds));
        if (ObjectUtils.isEmpty(createorder)) {
            //订单ID绑定的客户尚未授权
            return R.fail("订单ID不存在或者尚未授权！");
        }
        //验证体检号是否有效
        Peispatient peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, param.getPatientcode())
                .eq(Peispatient::getNumorgresv, createorder.getDdh()).eq(Peispatient::getPhone, param.getPhone()).ne(Peispatient::getFPaused, 1));
        if (ObjectUtils.isEmpty(peispatient)) {
            return R.fail("该订单【" + param.getOrderNum() + "】下找不到手机号【" + param.getPhone() + "】和体检号【" + param.getPatientcode() + "】的体检者数据！");
        }

        String patientcode = param.getPatientcode();
        log.info("根据体检号获取预约详情,data：{}", dataStr);
        if (org.apache.commons.lang3.StringUtils.isBlank(patientcode)) {
            R.fail("体检号不能为空！");
        }
        Reservation reservation = reservationOpenApiService.getOne(new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getPatientcode, patientcode)
                .in(Reservation::getStatus, 1)
        );
        if (ObjectUtils.isNotEmpty(reservation)) {
            SysBranch sysBranch = sysBranchService.getOne(new LambdaQueryWrapper<SysBranch>().eq(SysBranch::getBranchId, reservation.getBranchId()));
            reservation.setBranchName(sysBranch.getFzx());
            return R.ok(reservation);
        }
        return R.fail("体检号不存在或者已删除！");
    }

    /**
     * 查询报告列表
     *
     * @param request 请求数据
     * @return
     */
    @PostMapping("/report/list")
    @ApiOperation(value = "查询报告列表", notes = "查询报告列表")
    @ApiImplicitParam(name = "data", value = "请求数据")
    public R getReportListOfOrder(HttpServletRequest request) {

        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }

        String dataStr = (String) request.getAttribute("dataStr");
        CooOrderReportParam param = JSONUtil.toBean(dataStr, CooOrderReportParam.class);
        log.info("合作单位开放接口.查询报告列表param：{}", param);

        //校验是否授权
        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getId, param.getOrderNum())
                .eq(Createorder::getSpzt, OrderCheckStatus.SHTG.value()).eq(Createorder::getIsDelete, 0)
                .in(Createorder::getKhdwmcid, customerIds));
        if (ObjectUtils.isEmpty(createorder)) {
            //订单ID绑定的客户尚未授权
            return R.fail("订单ID不存在或者尚未授权！");
        }

        List<ReportListDto> reportList = mobileReportService.getReportListByOrderId(param.getPhone(), param.getOrderNum());
        return R.ok(reportList);
    }

    /**
     * 查询报告详情
     *
     * @return
     */
    @PostMapping("/report/detail")
    @ApiOperation(value = "查询报告详情", notes = "查询报告详情")
    public R newDetails(HttpServletRequest request) {

        CooCustomerInfo authInfo = (CooCustomerInfo) request.getAttribute("authInfo");
        String customerIdsStr = authInfo.getCodeSetting();
        if (StringUtils.isBlank(customerIdsStr)) {
            return R.fail("当前账户尚未在系统创建体检企业客户！");
        }

        String dataStr = (String) request.getAttribute("dataStr");
        NewMoReportDetailsParam param = JSONUtil.toBean(dataStr, NewMoReportDetailsParam.class);
        log.info("合作单位开放接口.查询报告详情param：{}", param);
        if (ObjectUtils.isEmpty(param) || StringUtils.isBlank(param.getPatientcode())
                || StringUtils.isBlank(param.getOrderNum()) || StringUtils.isBlank(param.getPhone())) {
            return R.fail("参数不合法！");
        }

        //校验是否授权
        List<String> customerIds = Arrays.stream(customerIdsStr.split(",")).collect(Collectors.toList());
        Createorder createorder = createorderService.getOne(new LambdaQueryWrapper<Createorder>().eq(Createorder::getId, param.getOrderNum())
                .eq(Createorder::getSpzt, OrderCheckStatus.SHTG.value()).eq(Createorder::getIsDelete, 0)
                .in(Createorder::getKhdwmcid, customerIds));
        if (ObjectUtils.isEmpty(createorder)) {
            //订单ID绑定的客户尚未授权
            return R.fail("订单ID不存在或者尚未授权！");
        }
        //验证体检号是否有效
        Peispatient peispatient = peispatientService.getOne(new LambdaQueryWrapper<Peispatient>().eq(Peispatient::getPatientcode, param.getPatientcode())
                .eq(Peispatient::getNumorgresv, createorder.getDdh()).eq(Peispatient::getPhone, param.getPhone()).ne(Peispatient::getFPaused, 1));
        if (ObjectUtils.isEmpty(peispatient)) {
            return R.fail("该订单【" + param.getOrderNum() + "】下找不到手机号【" + param.getPhone() + "】和体检号【" + param.getPatientcode() + "】的体检者数据！");
        }

        //查询
        ReportContent reportContent = reportContentService.getOne(new LambdaQueryWrapper<ReportContent>()
                .eq(ReportContent::getId, param.getId())
                .eq(ReportContent::getPatientcode, param.getPatientcode())
        );
        if (ObjectUtils.isEmpty(reportContent)) {
            throw new ServiceException("参数错误");
        }
        String content = reportContent.getContent();
        return R.ok(content);
    }
}

