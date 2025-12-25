package com.center.medical.abteilung.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.dto.SrFormdataDto;
import com.center.medical.abteilung.bean.dto.SrGriddataDto;
import com.center.medical.abteilung.bean.model.OutsideChargeItem;
import com.center.medical.abteilung.bean.param.SendRegisterParam;
import com.center.medical.abteilung.bean.param.SrSaOrUpParam;
import com.center.medical.abteilung.bean.vo.OutsideVo;
import com.center.medical.abteilung.dao.OutsideChargeItemMapper;
import com.center.medical.abteilung.service.OutsideChargeItemService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.vo.WsxmDataVo;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.reception.bean.model.OutsideMain;
import com.center.medical.reception.dao.OutsideMainMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * KS外送项目表(OutsideChargeItem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:25
 */
@Slf4j
@Service("outsideChargeItemService")
@RequiredArgsConstructor
public class OutsideChargeItemServiceImpl extends ServiceImpl<OutsideChargeItemMapper, OutsideChargeItem> implements OutsideChargeItemService {

    private final OutsideChargeItemMapper outsideChargeItemMapper;
    private final OutsideMainMapper outsideMainMapper;
    private final MapperFacade mapperFacade;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientMapper peispatientMapper;
    private final SysUserMapper sysUserMapper;

    /**
     * 分页查询[KS外送项目表]列表
     *
     * @param page  分页参数
     * @param param OutsideChargeItem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OutsideVo> getPage(PageParam<OutsideChargeItem> page, SendRegisterParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return outsideChargeItemMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public OutsideChargeItem getInfoById(String id) {
        return outsideChargeItemMapper.getInfoById(id);
    }


    /**
     * 新增外送登记-保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(SrSaOrUpParam param) {
        //保存主表
        SrFormdataDto formdata = param.getFormdata();
        OutsideMain ou = mapperFacade.map(formdata, OutsideMain.class);
        if (StringUtils.isBlank(ou.getId())) {
            outsideMainMapper.insert(ou);
        } else {
            OutsideMain om = outsideMainMapper.selectOne(new QueryWrapper<OutsideMain>().eq("patientcode", ou.getPatientcode()));
            if (ObjectUtils.isEmpty(om)) {
                throw new ServiceException("数据被删除，请刷新重试");
            }
            //更新
            outsideMainMapper.updateById(ou);
        }

        //保存子表
        List list = new ArrayList();
        List<SrGriddataDto> ja = param.getGriddata();
        for (int i = 0; i < ja.size(); i++) {
            SrGriddataDto jo = ja.get(i);
            String itemId = jo.getId();
            //外送项目是否被删除
            Peispatientfeeitem zyoc = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", ou.getPatientcode().toUpperCase()).eq("id_examfeeitem", itemId)
                    .eq("f_feecharged", 1)
                    .eq("change_item", 0).isNull("f_transferedhl7")
                    .eq("f_giveup", 0).eq("sfjj", 0));
            if (ObjectUtils.isNotEmpty(zyoc)) {
                //是否已存在同样的外送项目
                OutsideChargeItem charge = outsideChargeItemMapper.selectOne(new QueryWrapper<OutsideChargeItem>().eq("id_charge", itemId)
                        .eq("patientcode", ou.getPatientcode()).eq("is_delete", 0));
                if (ObjectUtils.isEmpty(charge)) {
                    OutsideChargeItem oci = new OutsideChargeItem();
                    oci.setIdCharge(zyoc.getIdExamfeeitem());
                    oci.setIdDepart(zyoc.getIdKs());
                    oci.setPatientcode(ou.getPatientcode());
                    oci.setIsDelete(0);
                    oci.setWsjgId(jo.getWsjgId());
                    list.add(oci);
                } else {
                    throw new ServiceException("外送项目" + zyoc.getExamfeeitemName() + "已存在，请重新选择");
                }
            } else {
                throw new ServiceException("收费项目不存在，请刷新页面");
            }
        }
        //批量插入
        return this.saveBatch(list);
    }


    /**
     * 不分页查询列表
     *
     * @param param
     * @return
     */
    @Override
    public List<OutsideVo> findList(SendRegisterParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return outsideChargeItemMapper.findList(param);
    }


    /**
     * 新增外送登记上方数据
     *
     * @param patientcode
     * @return
     */
    @Override
    public Map<String, Object> getPatientData(String patientcode) {
        Map<String, Object> data = new HashMap<>();
        //外送登记结果主表
        OutsideMain om = outsideMainMapper.selectOne(new QueryWrapper<OutsideMain>().eq("patientcode", patientcode));
        //体检者表
        Peispatient p = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientcode));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (ObjectUtils.isNotEmpty(om)) {
            //性别
            om.setXb(p.getIdSex());
            //外送人姓名
            String sendPeople = om.getSendPeople();
            if (StringUtils.isNotEmpty(sendPeople)) {
                SysUser user = sysUserMapper.selectUserByUserNo(sendPeople);
                if (ObjectUtils.isNotEmpty(user)) {
                    om.setSendPeopleName(user.getUserName());
                }
            }
            //转换发送时间
            if (ObjectUtils.isNotEmpty(om.getSendDate())) {
                om.setSendDateString(sdf.format(om.getSendDate()));
            }
            data.put("outsideMain", om);
        } else if (ObjectUtils.isNotEmpty(p)) {

            data.put("patientcode", patientcode);
            data.put("patientname", p.getPatientname());
            data.put("xb", p.getIdSex());
            data.put("sendDate", sdf.format(new Date()));
            data.put("sendPeople", SecurityUtils.getUserNo());
            data.put("sendPeopleName", SecurityUtils.getUsername());

        }
        return data;
    }


    /**
     * 登记外送项目数据获取
     *
     * @param page
     * @param key
     * @param patientcode
     * @return
     */
    @Override
    public IPage<WsxmDataVo> getPictureWsxmData(PageParam<WsxmDataVo> page, String key, String patientcode) {
        return outsideChargeItemMapper.getPictureWsxmData(page, key, patientcode);
    }
}

