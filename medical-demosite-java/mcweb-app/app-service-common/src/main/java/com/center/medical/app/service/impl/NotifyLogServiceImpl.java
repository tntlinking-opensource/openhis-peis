/*
 * Copyright (c) 2023-2999 青岛沃德医疗 All rights reserved.
 *
 * http://www.world.com/
 *
 */

package com.center.medical.app.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.app.bean.enums.RemindType;
import com.center.medical.app.bean.enums.SendType;
import com.center.medical.app.bean.model.NotifyLog;
import com.center.medical.app.bean.param.NotifyTemplateParam;
import com.center.medical.app.common.constants.Constant;
import com.center.medical.app.common.util.Json;
import com.center.medical.app.config.ShopConfig;
import com.center.medical.app.config.WxConfig;
import com.center.medical.app.dao.NotifyLogMapper;
import com.center.medical.app.service.NotifyLogService;
import com.center.medical.app.service.SmsLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author lhd
 * @date 2020-07-01 15:44:27
 */
@Slf4j
@Service
@AllArgsConstructor
public class NotifyLogServiceImpl extends ServiceImpl<NotifyLogMapper, NotifyLog> implements NotifyLogService {

    private final NotifyLogMapper notifyLogMapper;
    private final SmsLogService smsLogService;
    private final ShopConfig shopConfig;
    private final WxConfig wxConfig;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sendMessage(List<NotifyLog> logList) {
        for (NotifyLog notifyLog : logList) {
            if (StrUtil.isBlank(notifyLog.getUserMobile()) || StrUtil.isBlank(notifyLog.getParamContent())) {
                continue;
            }
            NotifyTemplateParam param = Json.parseObject(notifyLog.getParamContent(), NotifyTemplateParam.class);
            // 获取对应map
            Map<String, String> smsParam = getSmsParam(param);
            try {
                // 推送短信和公众号消息
                if (Objects.equals(notifyLog.getRemindType(), RemindType.SMS.value())) {
                    smsLogService.sendMsgSms(notifyLog.getTemplateCode(), param.getUserMobile(), smsParam);
                }
                if (Objects.equals(notifyLog.getRemindType(), RemindType.MP.value()) && StrUtil.isNotBlank(param.getOpenId())) {
                    sendMpMessage(notifyLog.getMpCode(), param);
                }
            } catch (Exception e) {
                log.error(SendType.LAUNCH_REFUND.getDesc() + "：消息推送失败！");
            }
        }
    }

    @Override
    public List<NotifyLog> listUnSendMsgList() {
        return notifyLogMapper.listUnSendMsgList();
    }


    /**
     * 发送微信模板信息
     */
    private void sendMpMessage(String mpCode, NotifyTemplateParam param) {
        if (StrUtil.isBlank(param.getUserId())) {
            return;
        }
        WxMaUniformMessage wxMaUniformMessage = new WxMaUniformMessage();
        wxMaUniformMessage.setMpTemplateMsg(true);
        wxMaUniformMessage.setToUser(param.getOpenId());
        wxMaUniformMessage.setAppid(shopConfig.getWxMp().getAppId());
        //公众号模板消息所要跳转的小程序
        WxMaUniformMessage.MiniProgram miniProgram = new WxMaUniformMessage.MiniProgram();
        miniProgram.setAppid(shopConfig.getWxMiniApp().getAppId());
        miniProgram.setPagePath("pages/user/user");
        wxMaUniformMessage.setMiniProgram(miniProgram);
        //模板id
        wxMaUniformMessage.setTemplateId(mpCode);
        //模板数据集合
        wxMaUniformMessage.setData(getWxMaTemplateDataList(param, param.getShopName()));
        try {
            wxConfig.getWxMaService().getMsgService().sendUniformMsg(wxMaUniformMessage);
        } catch (WxErrorException e) {
            //用户未关注公众号，导致的消息发送失败异常
            if (Objects.nonNull(e) && Objects.nonNull(e.getError()) && e.getError().getErrorCode() == 43004) {
                logger.info("用户（" + param.getUserId() + "）没有关注公众号，导致消息发送失败");
            }
            //其他异常
            else {
                log.error("WxErrorException:", e);
            }
        }
    }


    private List<WxMaTemplateData> getWxMaTemplateDataList(NotifyTemplateParam param, String shopName) {
        Map<Integer, List<WxMaTemplateData>> mpMap = new HashMap<>(24);
        List<WxMaTemplateData> dataList = new ArrayList<>();
        String priceStr = param.getPrice() + "元";
        String firstData = "亲爱的" + param.getNickName() + ",您在商城购买的宝贝还未付款哦！为确保您心爱的宝贝早日启程、顺利抵达，记得尽早付款哦！";
        dataList.add(new WxMaTemplateData("first", firstData));
        dataList.add(new WxMaTemplateData("keyword1", param.getOrderNumber()));
        dataList.add(new WxMaTemplateData("keyword2", DateUtil.format(param.getCreateTime(), DatePattern.NORM_DATETIME_PATTERN)));
        dataList.add(new WxMaTemplateData("keyword3", param.getProdName()));
        dataList.add(new WxMaTemplateData("remark", "请您及时付款以免订单过期。"));
        mpMap.put(SendType.PRESS_PAY.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "你好，你的订单已付款成功。"));
        dataList.add(new WxMaTemplateData("keyword1", param.getOrderNumber()));
        dataList.add(new WxMaTemplateData("keyword2", param.getProdName()));
        dataList.add(new WxMaTemplateData("keyword3", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)));
        dataList.add(new WxMaTemplateData("keyword4", priceStr));
        dataList.add(new WxMaTemplateData("remark", "感谢您的支持！"));
        mpMap.put(SendType.PAY_SUCCESS.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "您的退款申请商家已同意"));
        dataList.add(new WxMaTemplateData("keyword1", param.getOrderNumber()));
        dataList.add(new WxMaTemplateData("keyword2", param.getProdName()));
        dataList.add(new WxMaTemplateData("keyword3", priceStr));
        dataList.add(new WxMaTemplateData("remark", "请及时登录商城，查看退款信息"));
        mpMap.put(SendType.AGREE_REFUND.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "你的退款申请被商家驳回，可与商家协商沟通"));
        dataList.add(new WxMaTemplateData("keyword1", param.getOrderNumber()));
        dataList.add(new WxMaTemplateData("keyword2", priceStr));
        dataList.add(new WxMaTemplateData("keyword3", param.getRejectMessage()));
        dataList.add(new WxMaTemplateData("remark", "请及时登录商城，查看退款信息"));
        mpMap.put(SendType.REFUSE_REFUND.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "尊敬的客户，您的订单提货成功啦。"));
        dataList.add(new WxMaTemplateData("keyword1", param.getProdName()));
        dataList.add(new WxMaTemplateData("keyword2", priceStr));
        dataList.add(new WxMaTemplateData("keyword3", param.getProdNum().toString()));
        dataList.add(new WxMaTemplateData("keyword4", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)));
        dataList.add(new WxMaTemplateData("remark", "商城感谢您的支持！"));
        mpMap.put(SendType.WRITE_OFF.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "商品已在送往客官的路上"));
        dataList.add(new WxMaTemplateData("keyword1", param.getProdName()));
        dataList.add(new WxMaTemplateData("keyword2", "已发货"));
        dataList.add(new WxMaTemplateData("keyword3", param.getDvyName()));
        dataList.add(new WxMaTemplateData("keyword4", param.getDvyFlowId()));
        dataList.add(new WxMaTemplateData("remark", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)));
        mpMap.put(SendType.DELIVERY.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "您参加的拼团因为有效期内未成团，我们将在3个工作日内为您安排自动退款"));
        dataList.add(new WxMaTemplateData("keyword1", param.getProdName()));
        dataList.add(new WxMaTemplateData("keyword2", priceStr));
        dataList.add(new WxMaTemplateData("keyword3", "商城"));
        dataList.add(new WxMaTemplateData("remark", "点击进入订单详情页，查看退款进度！"));
        mpMap.put(SendType.GROUP_FAIL.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "恭喜你，拼团已经成功！"));
        dataList.add(new WxMaTemplateData("keyword1", param.getProdName()));
        dataList.add(new WxMaTemplateData("keyword2", priceStr));
        dataList.add(new WxMaTemplateData("remark", "请登录商城，查看订单发货信息"));
        mpMap.put(SendType.GROUP_SUCCESS.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "恭喜你，开团已经成功！"));
        dataList.add(new WxMaTemplateData("keyword1", param.getProdName()));
        dataList.add(new WxMaTemplateData("keyword2", priceStr));
        dataList.add(new WxMaTemplateData("remark", "快去小程序将参团链接分享给好友进行参团吧！"));
        mpMap.put(SendType.GROUP_START.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "会员升级通知"));
        dataList.add(new WxMaTemplateData("keyword1", param.getLevelName()));
        dataList.add(new WxMaTemplateData("keyword2", "成功！"));
        dataList.add(new WxMaTemplateData("remark", "感谢您的支持！"));
        mpMap.put(SendType.MEMBER.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "您有退款单即将超时，请及时联系商家处理"));
        dataList.add(new WxMaTemplateData("keyword1", shopName));
        dataList.add(new WxMaTemplateData("keyword2", "1笔"));
        dataList.add(new WxMaTemplateData("remark", "如有疑问请及时与客服联系！"));
        mpMap.put(SendType.REFUND_OUT_TIME.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "亲，物流显示您的订单买家已经确认收货！"));
        dataList.add(new WxMaTemplateData("keyword1", param.getOrderNumber()));
        dataList.add(new WxMaTemplateData("keyword2", param.getProdName()));
        dataList.add(new WxMaTemplateData("keyword3", "已确认收货"));
        dataList.add(new WxMaTemplateData("remark", "如有疑问请及时与客服联系！"));
        mpMap.put(SendType.RECEIPT_ORDER.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "店铺有新的退款申请，请及时处理"));
        dataList.add(new WxMaTemplateData("keyword1", priceStr));
        dataList.add(new WxMaTemplateData("keyword2", param.getRejectMessage()));
        dataList.add(new WxMaTemplateData("keyword3", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)));
        dataList.add(new WxMaTemplateData("keyword4", "退款金额原路返回"));
        dataList.add(new WxMaTemplateData("keyword5", param.getRemark()));
        dataList.add(new WxMaTemplateData("remark", "请您尽快请登录商家后台操作处理。"));
        mpMap.put(SendType.LAUNCH_REFUND.getValue(), dataList);

        dataList = new ArrayList<>();
        dataList.add(new WxMaTemplateData("first", "你的买家已退货，请及时处理"));
        dataList.add(new WxMaTemplateData("keyword1", param.getOrderNumber()));
        dataList.add(new WxMaTemplateData("keyword2", param.getRemark()));
        dataList.add(new WxMaTemplateData("remark", "如有疑问请及时与客服联系！"));
        mpMap.put(SendType.RETURN_REFUND.getValue(), dataList);
        return mpMap.get(param.getSendType());
    }

    private Map<String, String> getSmsParam(NotifyTemplateParam param) {
        Map<String, String> smsParam = new HashMap<>(20);
        smsParam.put("orderNumber", param.getOrderNumber());
        smsParam.put("shopName", param.getShopName());
        smsParam.put("prodName", param.getProdName());
        if (StrUtil.isBlank(param.getNickName())) {
            smsParam.put("nickName", "用户");
        } else {
            smsParam.put("nickName", param.getNickName());
        }
        smsParam.put("levelName", param.getLevelName());
        smsParam.put("cancelTime", param.getCancelTime());
        smsParam.put("price", String.valueOf(param.getPrice()));
        smsParam.put("dvyFlowId", param.getDvyFlowId());
        smsParam.put("stationName", param.getStationName());
        smsParam.put("dvyName", param.getDvyName());
        smsParam.put("dvyTime", DateUtil.format(param.getDvyTime(),
                DatePattern.NORM_DATETIME_PATTERN));
        smsParam.put("day", String.valueOf(Constant.MAX_REFUND_APPLY_TIME));
        smsParam.put("hour", param.getHour().toString());
        smsParam.put("date", DateUtil.format(param.getDvyTime(),
                DatePattern.NORM_DATETIME_PATTERN));
        return smsParam;
    }

    public static void main(String[] args) {
        int i = 0;
        i = i++;
        System.out.println(i);
    }

}
