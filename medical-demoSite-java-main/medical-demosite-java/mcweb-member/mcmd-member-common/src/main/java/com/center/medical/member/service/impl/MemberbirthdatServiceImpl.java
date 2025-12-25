package com.center.medical.member.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.model.SmsRecord;
import com.center.medical.common.config.SmsConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.sms.SDKTestSendTemplateSMS;
import com.center.medical.dao.PeispatientarchiveMapper;
import com.center.medical.data.bean.model.Shortmessage;
import com.center.medical.data.dao.ShortmessageMapper;
import com.center.medical.member.bean.model.Memberbirthdat;
import com.center.medical.member.bean.param.BirtthdaySmsParam;
import com.center.medical.member.bean.param.MemberbirthdayParam;
import com.center.medical.member.bean.vo.MemberbirthdatVo;
import com.center.medical.member.dao.MemberbirthdatMapper;
import com.center.medical.member.service.MemberbirthdatService;
import com.center.medical.service.SmsRecordService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 会员生日提醒回访表(Memberbirthdat)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-24 09:59:56
 */
@Slf4j
@Service("memberbirthdatService")
@RequiredArgsConstructor
public class MemberbirthdatServiceImpl extends ServiceImpl<MemberbirthdatMapper, Memberbirthdat> implements MemberbirthdatService {

    private final MemberbirthdatMapper memberbirthdatMapper;
    private final ShortmessageMapper shortmessageMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final SmsRecordService smsRecordService;
    private final ISysConfigService iSysConfigService;

    /**
     * 分页查询[会员生日提醒回访表]列表
     *
     * @param page  分页参数
     * @param param Memberbirthdat查询参数
     * @return 分页数据
     */
    @Override

    public IPage<MemberbirthdatVo> getPage(PageParam<Memberbirthdat> page, MemberbirthdayParam param) {
        param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
        int year = DateUtil.year(new Date());

        if (Objects.nonNull(param.getAge())) {
            // 年龄段转换
            int age = param.getAge();
            // 出生日期(年)段
            int minYear = 0;
            int maxYear = 0;
            // 当前年
            //int year = Calendar.getInstance().get(Calendar.YEAR);
            switch (age) {
                case 0:
                    // 7~17岁
                    minYear = year - 17 + 1;// 离今年最远的年份(虚岁)
                    maxYear = year - 7 + 1;// 离今年最近的年份
                    break;
                case 1:
                    minYear = year - 40 + 1;
                    maxYear = year - 18 + 1;
                    break;
                case 2:
                    minYear = year - 65 + 1;
                    maxYear = year - 41 + 1;
                    break;
                case 3:
                    minYear = year - 66 + 1;
                    break;
                default:
                    break;
            }
            param.setMinYear(minYear);
            param.setMaxYear(maxYear);
        }
        return memberbirthdatMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Memberbirthdat getInfoById(String id) {
        return memberbirthdatMapper.getInfoById(id);
    }

    /**
     * 根据条件查询列表
     *
     * @param param
     * @return
     */
    @Override
    public List<MemberbirthdatVo> getList(MemberbirthdayParam param) {
        param.setBranchIds(Arrays.asList(SecurityUtils.getCId()));
        int year = DateUtil.year(new Date());
        if (Objects.nonNull(param.getAge())) {
            // 年龄段转换
            int age = param.getAge();
            // 出生日期(年)段
            int minYear = 0;
            int maxYear = 0;
            // 当前年
            //int year = Calendar.getInstance().get(Calendar.YEAR);
            switch (age) {
                case 0:
                    // 7~17岁
                    minYear = year - 17 + 1;// 离今年最远的年份(虚岁)
                    maxYear = year - 7 + 1;// 离今年最近的年份
                    break;
                case 1:
                    minYear = year - 40 + 1;
                    maxYear = year - 18 + 1;
                    break;
                case 2:
                    minYear = year - 65 + 1;
                    maxYear = year - 41 + 1;
                    break;
                case 3:
                    minYear = year - 66 + 1;
                    break;
                default:
                    break;
            }
            param.setMinYear(minYear);
            param.setMaxYear(maxYear);
        }
        return memberbirthdatMapper.getList(param);
    }

    /**
     * 保存或修改
     *
     * @param param
     * @return
     */
    @Override
    public String saOrUp(BirtthdaySmsParam param) {
        if (CollectionUtil.isEmpty(param.getIds())) {
            throw new ServiceException("系统发生异常，请联系管理员！");
        }
        Shortmessage message = shortmessageMapper.selectById(param.getMessageName());
        if (Objects.isNull(message)) {
            throw new ServiceException("保存失败，短信模板已被删除！");
        }
        String idTemplate = message.getTemplateId();//网上模板ID
        String appId = message.getAppid();//appID
        String result = "success";
        String notifyType = "3";
        String creater = SecurityUtils.getUsername();
        Date notifyTime = (Objects.isNull(param.getSendTime()) || param.getImmediately()) ? new Date() : param.getSendTime();//通知时间
        if (param.getImmediately()) {
            //立即发送
            List<Memberbirthdat> list = new ArrayList<Memberbirthdat>();
            StringBuilder builder = new StringBuilder();
            boolean isSend = false;
            int i = 0;
            for (String archiveId : param.getIds()) {
                Peispatientarchive pa = peispatientarchiveMapper.getInfoById(archiveId);
                if (Objects.isNull(pa)) {
                    builder.append("第<font color='red'>" + (i + 1) + "</font>条未找到档案数据，发送失败！");
                    continue;
                }
                String phone = pa.getPhone();
                if (StringUtils.isEmpty(phone)) {
                    builder.append("会员<font color='red'>" + pa.getPatientname() + "</font>电话为空，发送失败！");
                    continue;
                }
                SmsRecord sms = smsRecordService.getOne(new LambdaQueryWrapper<SmsRecord>()
                        .eq(SmsRecord::getNotifyType, notifyType)
                        .eq(SmsRecord::getArchiveId, archiveId)
                        .eq(SmsRecord::getNotifyResult, 2));
                if (Objects.isNull(sms)) {
                    sms = new SmsRecord();
                    sms.setNotifyType(notifyType);
                    sms.setArchiveId(archiveId);
                    sms.setCreatedate(new Date());
                } else {
                    sms.setModifydate(new Date());
                }
                sms.setCreater(creater);
                sms.setIsImmediately(param.getImmediately() ? 1 : 0);
                sms.setIdTemplate(param.getMessageName());
                sms.setNotifyTime(notifyTime);
                sms.setNotifyContent(param.getVisitNote());

                try {
                    SmsConfig smsConfig = iSysConfigService.getSysConfigObject(Constants.SMS_CONFIG,SmsConfig.class);
                    String result_one = SDKTestSendTemplateSMS.sendMsg(smsConfig,phone, idTemplate, null,appId);
                    if (!"success".equals(result_one)) {
                        builder.append("会员" + pa.getPatientname() + "<font color='red'>发送失败！</font>," + result_one);
                        sms.setNotifyResult(0);
                    } else {
                        builder.append("会员" + pa.getPatientname() + "<font color='green'>发送成功！</font>");
                        sms.setNotifyResult(3);
                        isSend = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    builder.append("会员" + pa.getPatientname() + "<font color='red'>发送失败！</font>," + e.getMessage());
                    sms.setNotifyResult(0);
                }

                if (isSend) {
                    Memberbirthdat mb = new Memberbirthdat();
                    mb.setHyId(archiveId);
                    mb.setVisitStatus(0);//已回访
                    mb.setVisitMan(SecurityUtils.getUsername());//回访人
                    mb.setVisitTime(notifyTime);// 回访日期，当前日期
                    mb.setVisitType("短信回访");
                    mb.setVisitNote(param.getVisitNote());
                    mb.setVisitText("0");// 跟进内容
                    mb.setCreatedate(new Date());
                    list.add(mb);
                    pa.setRestatus(1);// 设置为已回访状态
                    pa.setModifydate(new Date());
                    peispatientarchiveMapper.updateById(pa);
                }
                smsRecordService.saveOrUpdate(sms);
                builder.append("</br>");

            }
            this.saveBatch(list);
            result = builder.toString();
        } else {
            //定时发送
            for (String archiveId : param.getIds()) {
                Peispatientarchive pa = peispatientarchiveMapper.getInfoById(archiveId);
                if (Objects.isNull(pa)) {
                    throw new ServiceException("未找到档案数据，请刷新重试！");
                }
                String phone = pa.getPhone();
                if (StringUtils.isEmpty(phone)) {
                    throw new ServiceException("会员" + pa.getPatientname() + "没有电话，无法发送！");
                }
                SmsRecord sms = smsRecordService.getOne(new LambdaQueryWrapper<SmsRecord>()
                        .eq(SmsRecord::getNotifyType, notifyType)
                        .eq(SmsRecord::getArchiveId, archiveId)
                        .eq(SmsRecord::getNotifyResult, 2));
                if (Objects.isNull(sms)) {
                    sms = new SmsRecord();
                    sms.setNotifyType(notifyType);
                    sms.setArchiveId(archiveId);
                    sms.setCreatedate(new Date());
                } else {
                    sms.setModifydate(new Date());
                }
                sms.setCreater(creater);
                sms.setIsImmediately(param.getImmediately() ? 1 : 0);
                sms.setIdTemplate(param.getMessageName());
                sms.setNotifyTime(notifyTime);
                sms.setNotifyResult(2);
                smsRecordService.saveOrUpdate(sms);
            }
        }
        return result;
    }


    /**
     * 取消发送
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelSMS(List<String> ids) {
        for (int i = 0, l = ids.size(); i < l; i++) {
            String id = ids.get(i);
            SmsRecord record = smsRecordService.getOne(new QueryWrapper<SmsRecord>()
                    .eq("notify_type", "3").eq("archive_id", id).eq("notify_result", 2));
            if (ObjectUtils.isEmpty(record)) {
                throw new ServiceException("第" + (i + 1) + "条数据的短信通知已发送或已被取消，取消失败！");
            }
            record.setNotifyResult(1);
            smsRecordService.updateById(record);
        }
        return Boolean.TRUE;
    }


}

