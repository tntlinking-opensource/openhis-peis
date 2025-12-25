package com.center.medical.outreach.pf.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.center.medical.bean.dto.UserExamDataDto;
import com.center.medical.bean.param.UserExamDataParam;
import com.center.medical.common.core.controller.BaseController;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.outreach.bean.param.CooCustomerLoginParam;
import com.center.medical.outreach.bean.param.CooOrderReportParam;
import com.center.medical.reservation.bean.param.AppointmentAvailableParam;
import com.center.medical.reservation.bean.param.ReservationDateParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Api(tags = "测试接口")
@RequestMapping("/open/api")
public class TestController extends BaseController {

    //    public static void main(String[] args) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("code", "80C8A8EFFC1E8552");
//        params.put("flag", "COO_CUSTOMER");
//        params.put("data", "bzSGhUth9g2uPwn9aDxHO0GHWqCIyaZPaPOX68DgHdwzHUlefViy2NCMgCN+YFnimy71C1hZr5j8vMJbCl5Ysxo+IiU9MrRZdeKJer+iuvXn5N3obc5WKBl8UOF5uMbZzLCGA5LbpUh/4RHiOWzc5zu4UR+Zmji44zhh1AoKMto=".replace("+", "%2B"));
////rCIctitFeOgd3/RFHulOY1DTG3PoEpUCG9OKHwufX5KV529JGLNDXr2I+or31sQi
//        // 明确指定编码为UTF-8
//        String post = HttpUtil.post("http://localhost:8081/open/api/v3/cooperate/customer", params);
//        System.out.println(post);
//    }

    public static void main(String[] args) {

        //业务方授权码
        String code = "1B23C201803CA67F";
        //RSA加密公钥
        String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGsO5WxX8R5PQM8HsNKzidzOb3qJbx+8xPqd7hLL8vU0YnbfpbK96W6xRwPEr4hKV16poWpn4hTIP/g5oqEFGFjsma1DBS8YTKcRYTWgB+BdYk5CQkecV9SpX9/sNI3wo8Q3xNUgNEhVWPDw5gZIwl9bYu08qXIfMxGu+QGXAHbwIDAQAB";

        //登录参数
        CooCustomerLoginParam lp = new CooCustomerLoginParam();
        lp.setPassword("lr@8523");
        lp.setUserName("lianren");

        String customerId = "ff80808180bb31990180d5a52add48a8";

        //订单id获取体检者数据参数
        UserExamDataParam uedp = new UserExamDataParam();
        uedp.setFlag("111111111");
        uedp.setOrderId("1805067061281263618");
        uedp.setCallBack("http://mdopenapi.world.com/open/api/test");

        ReservationDateParam reservationDateParam  = new ReservationDateParam();
        reservationDateParam.setBranchId("4");
        reservationDateParam.setStartDate(DateUtil.beginOfDay(new Date()));
        reservationDateParam.setEndDate(DateUtil.offsetDay(new Date(), 7));

        //获取预约时间段
        AppointmentAvailableParam aap = new AppointmentAvailableParam();

        //查询报告列表
        CooOrderReportParam param = new CooOrderReportParam();
        param.setCustomerId("1872115395004207104");
        param.setPhone("18661794203");
        param.setOrderNum("1872121340281819138");

        //加密后将+替换
        String s1 =
//                "V6bdioHOEyui88fPZtOGOL30V92Q6JVfTnMwCjr7lqwSsabJPQZpg16Z5HmL7ZmshuCWYF%2BUD50HYJArJkRGeyd0iOESf/PcQELAzo0UFl0QrBWZ4bchoMkf0ZXfg3ipLCrJSUDSGMtBuCBraiGOBFG%2BCzMJI6y3zmbqJiKwjjI=";
                RSAUtil.publicEncrypt(code+JSONUtil.toJsonStr(param), key).replace("+", "%2B");
//                    RSAUtil.publicEncrypt(code+JSONUtil.toJsonStr(aap), key).replace("+", "%2B");

        // 请求的URL
//        String url = "http://123.249.111.34:8081/open/api/v3/login";
//        String url = "http://XXX.XXX.XXX.XXX:8081/open/api/v3/cooperate/reservationTimeList";//reservation/apply; getUserData; cooperate/getUserResults; cooperate/orderList"; login; cooperate/customer;
        String url = "http://medicalapi_out.world.com/open/api/v3/cooperate/report/list";
        // 创建HttpRequest对象，设置请求方法为POST
        HttpRequest request = HttpUtil.createPost(url);
        // 添加token到请求头部
        request.header("Authorization", "pGCurp/rur/H1eJpgWLNxdfDYKK9AZnAqxA19e41+ynDYW3mWsk2KtSOl25NCVom");
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("flag", "COO_CUSTOMER");
        params.put("data", s1);

        request.form(params);
        // 发送请求并获取响应
        HttpResponse response = request.execute();
        // 获取响应内容并输出（根据实际情况可能需要进一步处理响应，比如判断状态码等）
        String result = response.body();
        System.out.println(result);
    }

    /**
     * 测试
     *
     * @param sign
     * @param records
     * @return
     */
    @PostMapping("/test")
    @ApiOperation(value = "测试", notes = "测试")
    public String test(@RequestParam("sign") String sign, @RequestParam("records") List<UserExamDataDto> records) {
        System.out.println("sign:"+sign);
        System.out.println("records:"+records);
        return "数据收到了";
    }

}

