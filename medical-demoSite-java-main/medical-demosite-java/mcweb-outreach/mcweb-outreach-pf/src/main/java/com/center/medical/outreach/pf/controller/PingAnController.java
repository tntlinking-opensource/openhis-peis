package com.center.medical.outreach.pf.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.utils.DesUtil;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.report.service.SectionTotalService;
import com.center.medical.reservation.bean.param.CancelOrderParam;
import com.center.medical.reservation.bean.param.ChangeDateParam;
import com.center.medical.reservation.bean.param.PingAnGetOfferParam;
import com.center.medical.reservation.bean.param.PingAnNumsParam;
import com.center.medical.reservation.bean.vo.ApplyForOrderVo;
import com.center.medical.reservation.bean.vo.PingAnNumsVo;
import com.center.medical.reservation.service.PingAnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 平安好医生(PaPeissortexam)接口
 *
 * 200 正常，400 参数异常，401 权限异常，500 未知错误
 *
 * 接口签名说明
 *
 * 参数名 描述
 * pajkKey 健康互联网分配的唯一 key，用作校验用户是否合法
 * sign 签名算法，用作校验用户是否合法
 * MD5(pajkKey+pajkPwd+timestamp) ，32 位加密
 * pajkPwd 不需要传，线下提供给体检机构
 * timestamp 时间戳，用作校验用户是否合法，有效时间 10 分钟
 *
 * @author ay
 * @since 2023-10-20 13:51:08
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "平安好医生")
@RequestMapping("open/api/reservation/pingAn")
public class PingAnController extends BaseController {
    /**
     * 服务对象
     */
    private final PingAnService pingAnService;
    private final SectionTotalService sectionTotalService;

    private static final String HEADER_JSON_CONTENT_TYPE = "text/plain";
    private static final String HEADER_ENCODING = "UTF-8";
    private static final boolean HEADER_NO_CACHE = true;


    /**
     * 体检预约可用人数
     *
     * @param param  查询条件
     * @return 所有数据
     */
    @GetMapping("/getAvailableNums")
    @ApiOperation(value = "体检预约可用人数", notes = "体检预约可用人数")
    public Object getAvailableNums(PingAnNumsParam param) {
        String hasAuth=checkAuth(param.getAppId());
        if(!"success".equals(hasAuth)){
            return getError(hasAuth);
        }

        if(StringUtils.isEmpty(param.getStartDate())
                ||StringUtils.isEmpty(param.getEndDate())
                ||StringUtils.isEmpty(param.getHospitalSubId()))
        {
            return getError("400");
        }
        try{
            List<PingAnNumsVo> list = pingAnService.getAvailableNums(param);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar start = Calendar.getInstance();
            start.setTime(sdf.parse(param.getStartDate()));
            Calendar end = Calendar.getInstance();
            end.setTime(sdf.parse(param.getEndDate()));

            Map<String, PingAnNumsVo> resultMap = new HashMap<>();
            for (PingAnNumsVo vo : list) {
                resultMap.put(vo.getDate(), vo);
            }
            //循环日期，如果当天没有预约信息，给一个默认的0的预约
            List<PingAnNumsVo> resultList = new ArrayList<>();
            while (!start.after(end)) {
                String dateStr = sdf.format(start.getTime());
                PingAnNumsVo vo = resultMap.get(dateStr);
                if (vo == null) {
                    vo = new PingAnNumsVo();
                    vo.setDate(dateStr);
                    vo.setCanOrder(0);
                    vo.setProvideVipData(0);
                    vo.setVipMaxNum(0);
                    vo.setVipOrderNum(0);
                    vo.setMaxNum(0);
                    vo.setOrderNum(0);
                }
                resultList.add(vo);
                start.add(Calendar.DATE, 1);
            }
            Map<String,Object> map = new HashMap<>();
            map.put("status","200");
            map.put("results",resultList);
            return map;
        }catch (Exception e) {
            log.error("getAvailableNums报错：{}",e.getMessage());
            return getError("500");
        }
    }




    /**
     * 申请预约平安
     *
     * @param data  查询条件
     * @return 所有数据
     */
    @PostMapping("/applyForOrder")
    @ApiOperation(value = "申请预约平安", notes = "申请预约平安")
    public Object applyForOrder(@RequestParam String data) {
        if(StringUtils.isEmpty(data)){
            return getError("400");
        }
        //解析数据
        log.info("申请预约平安:data:"+data);
        String json = null;
        try {
            json= DesUtil.decode(data);
        } catch (Exception e) {
            return getError("400");
        }

        try{
            long a = System.currentTimeMillis();
            ApplyForOrderVo vo = pingAnService.applyForOrder(json,3);
            log.info("本次预约共耗费："+((System.currentTimeMillis()-a)/1000)+"秒");
            Map map = BeanUtil.beanToMap(vo);
            return map;

        }catch (Exception e) {
            log.error("applyForOrder报错：{}",e.getMessage());
            return getError("500");
        }


    }


    /**
     * 取消申请
     * @param param
     * @return
     */
    @GetMapping("/cancelOrder")
    @ApiOperation(value = "取消预约", notes = "取消预约")
    public String cancelOrder(CancelOrderParam param) {
        String hasAuth=checkAuth(param.getAppId());
        if(!"success".equals(hasAuth)){
            return getError(hasAuth);
        };
        if (ObjectUtils.isEmpty(param) || ObjectUtils.isEmpty(param.getHospitalOrderId())){
            return getError("400");
        }
        try{
            pingAnService.cancelOrder(param.getHospitalOrderId(),param.getOrderId(),3);
        }catch (Exception e) {
            log.error("cancelOrder报错：{}",e.getMessage());
            return getError("500");
        }
        return getSuccess(new HashMap<String, String>());
    }


    /**
     * 修改预约
     * @param param
     * @return
     */
    @GetMapping("/changeDate")
    @ApiOperation(value = "修改预约", notes = "修改预约")
    public String changeDate(ChangeDateParam param) {
        String hasAuth=checkAuth(param.getAppId());
        if(!"success".equals(hasAuth)){
            return getError(hasAuth);
        }

        if(StringUtils.isEmpty(param.getHospitalOrderId())
                ||StringUtils.isEmpty(param.getAppointmentTime())){
            return getError("400");
        }
        try{
            pingAnService.changeDate(param.getHospitalOrderId(), param.getOrderId(), param.getAppointmentTime(),3);
        }catch (Exception e) {
            log.error("changeDate报错：{}",e.getMessage());
            return getError("500");
        }
        Map<String, String> map = new HashMap<>();
        map.put("hospitalOrderId", param.getHospitalOrderId());
        return getSuccess(map);
    }


    /**
     * 修改预约
     * @param param
     * @return
     */
    @GetMapping("/authorize")
    @ApiOperation(value = "客户授权查看影像报告", notes = "客户授权查看影像报告")
    public String authorize(CancelOrderParam param) {
        Map<Object, Object> map = new HashMap<>();
        if(StringUtils.isEmpty(param.getHospitalOrderId()) || "null".equals(param.getHospitalOrderId())){
            return getError("400");
        }
        try{
            pingAnService.authorize(param.getHospitalOrderId(), param.getOrderId());
            return getSuccess(new HashMap<String, String>());
        }catch (Exception e) {
            log.error("authorize报错：{}",e.getMessage());
            return getError(e.getMessage());
        }
    }




    /**
     * 获取结构化体检报告数据
     * @param param
     * @return
     */
    @GetMapping("/getOffer")
    @ApiOperation(value = "获取结构化体检报告数据", notes = "获取结构化体检报告数据")
    public String getOffer(PingAnGetOfferParam param) {
        String hasAuth=checkAuth(param.getAppId());
        if(!"success".equals(hasAuth)){
            return getError(hasAuth);
        }
        String hospitalOrderId = param.getHospitalOrderId();
        if(StringUtils.isEmpty(hospitalOrderId)){
            return getError("400");
        }
        try {
            String offer=sectionTotalService.getOffer(hospitalOrderId);
            Map<String,String> map=new HashMap<String, String>();
            map.put("data", offer);
            return getSuccess(map);
        }catch(RuntimeException e){
            e.printStackTrace();
            return getError(e.getMessage());
        }
    }




    private String getError(String errorCode){
        System.out.println("平安好医生报错："+errorCode);
        Map<String,String> result=new HashMap<String, String>();
        result.put("status", errorCode);
        return JSONUtil.toJsonStr(result);
    }


    private String getSuccess(Map<String,String> result){
        result.put("status", "200");
        return JSONUtil.toJsonStr(result);
    }


    private String checkAuth(String appId){
        String configId = "appid402848e35a1ba8d8015a1cd";
        if(StringUtils.isNotEmpty(configId)){
            if(!configId.equals(appId)){
                return "401";
            }
        }
        return "success";
    }




}
