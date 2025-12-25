package com.center.medical.reception.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.OutsidePictrue;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.OutsidePictrueMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.reception.bean.dto.SgGriddataDto;
import com.center.medical.reception.bean.dto.SgItemGriddataDto;
import com.center.medical.reception.bean.model.OutsideCheckin;
import com.center.medical.reception.bean.model.OutsideHand;
import com.center.medical.reception.bean.model.OutsideMain;
import com.center.medical.reception.bean.param.RecordMatchParam;
import com.center.medical.reception.bean.param.SendGovernParam;
import com.center.medical.reception.bean.param.SgSaOrUpParam;
import com.center.medical.reception.bean.vo.SendGovernVo;
import com.center.medical.reception.dao.OutsideCheckinMapper;
import com.center.medical.reception.dao.OutsideHandMapper;
import com.center.medical.reception.dao.OutsideMainMapper;
import com.center.medical.reception.dao.PeispatientexamitemMapper;
import com.center.medical.reception.service.OutsideCheckinService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.service.OutsidePictrueService;
import com.center.medical.service.PeisStateService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * KS外送登记结果主表(OutsideMain)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:28
 */
@Slf4j
@Service("outsideMainService")
@RequiredArgsConstructor
public class OutsideMainServiceImpl extends ServiceImpl<OutsideMainMapper, OutsideMain> implements OutsideMainService {

    private final OutsideMainMapper outsideMainMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final MapperFacade mapperFacade;
    private final OutsideHandMapper outsideHandMapper;
    private final PeispatientexamitemMapper peispatientexamitemMapper;
    private final OutsideCheckinMapper outsideCheckinMapper;
    private final OutsidePictrueMapper outsidePictrueMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final SysDeptMapper sysDeptMapper;
    private final PeisStateService peisStateService;
    private final OutsideCheckinService outsideCheckinService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final OutsidePictrueService outsidePictrueService;


    /**
     * 分页查询[KS外送登记结果主表]列表
     *
     * @param page  分页参数
     * @param param OutsideMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SendGovernVo> getPage(PageParam<OutsideMain> page, SendGovernParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return outsideMainMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public OutsideMain getInfoById(String id) {
        return outsideMainMapper.getInfoById(id);
    }


    /**
     * 新增外送登记结果处理-查询
     *
     * @param listdata
     * @return
     */
    @Override
    public HashMap<String, Object> recordMatch(RecordMatchParam listdata) {
        String retu = "";
        QueryWrapper<OutsideMain> and = new QueryWrapper<OutsideMain>().eq("is_delete",0);
        //姓名
        if (ObjectUtils.isNotEmpty(listdata.getPatientname())) {
            and.like("patientname", listdata.getPatientname().trim());
        }
        //开始时间
        if (ObjectUtils.isNotEmpty(listdata.getStartTime())) {
            and.ge("sendDate", DateUtil.beginOfDay(listdata.getStartTime()));
        }
        //结束时间
        if (ObjectUtils.isNotEmpty(listdata.getEndTime())) {
            and.le("sendDate", DateUtil.endOfDay(listdata.getEndTime()));
        }
        //体检码
        if (ObjectUtils.isNotEmpty(listdata.getPatientCode())) {
            and.like("patientcode", listdata.getPatientCode().trim());
        }

        List<OutsideMain> outdata = outsideMainMapper.selectList(and);
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (outdata.size() == 0) {
            // 不存在
            retu = "-1";
            result.put("retu", retu);
        } else if (outdata.size() == 1) {
            // 只有一个
            result.put("retu", outdata);
            // 体检者表
            Peispatient peispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", outdata.get(0).getPatientcode()));

            if (ObjectUtils.isEmpty(listdata.getSkip())){
                if (ObjectUtils.isNotEmpty(peispatient)) {
                    if (peispatient.getZytjzt() != null && peispatient.getZytjzt() == 1) {
                        throw new ServiceException("本体检者检查结果已被" + (peispatient.getPatientnameencoded() == null ? "" : peispatient.getPatientnameencoded()) + "完成总检，不能修改！如有未检项目也不再进行。");
                    } else if (peispatient.getJktjzt() != null && peispatient.getJktjzt() == 1) {
                        throw new ServiceException("本体检者检查结果已被" + (peispatient.getDoctorfinalNameR() == null ? "" : peispatient.getDoctorfinalNameR()) + "完成总检，不能修改！如有未检项目也不再进行。");
                    } else if (peispatient.getFFinallocked() != null && peispatient.getFFinallocked() == 1) {
                        throw new ServiceException("本体检者检查结果已被" + (peispatient.getIdDoctorapply() == null ? "" : peispatient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
                    } else if (peispatient.getIdGuidenurse() != null && peispatient.getIdGuidenurse() == 1) {
                        throw new ServiceException("本体检者检查结果已被" + (peispatient.getParsedassignedsuiteandfi() == null ? "" : peispatient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
                    }
                } else {
                    throw new ServiceException("错误:体检者信息已不存在!");
                }
            }
        } else {
            //多个
            retu = "0";
            result.put("retu", retu);
        }
        return result;
    }


    /**
     * 保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(SgSaOrUpParam param) {
        //取出属性
        String patientcode = param.getPatientcode();

        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientcode));
        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("修改失败，该体检者信息已不存在！");
        }
        if (ObjectUtils.isEmpty(patient.getFPaused()) && patient.getFPaused().intValue() == 1) {
            throw new ServiceException("修改失败，该体检者已被禁检！");
        }

        patient.setModifydate(new Date());
        //短号体检号
        Integer shortCode = patient.getShortCode();
        //任务分组ID
        String idOrgreservationgroup = patient.getIdOrgreservationgroup();
        if (StringUtils.isNotEmpty(idOrgreservationgroup)) {
            //体检者任务分组
            Peisorgreservationgroup org = peisorgreservationgroupMapper.getInfoById(idOrgreservationgroup);
            if (ObjectUtils.isNotEmpty(org) && ObjectUtils.isNotEmpty(org.getFGrouppaused()) && org.getFGrouppaused().intValue() == 1) {
                throw new ServiceException("修改失败，该体检者已被禁检！");
            }
        }

        if (patient.getJktjzt() != null && patient.getJktjzt() == 1) {
            throw new ServiceException("修改失败，本体检者检查结果已被" + (patient.getDoctorfinalNameR() == null ? "" : patient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getZytjzt() != null && patient.getZytjzt() == 1) {
            throw new ServiceException("修改失败，本体检者检查结果已被" + (patient.getPatientnameencoded() == null ? "" : patient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getFFinallocked() != null && patient.getFFinallocked() == 1) {
            throw new ServiceException("修改失败，本体检者检查结果已被" + (patient.getIdDoctorapply() == null ? "" : patient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getIdGuidenurse() != null && patient.getIdGuidenurse() == 1) {
            throw new ServiceException("修改失败，本体检者检查结果已被" + (patient.getParsedassignedsuiteandfi() == null ? "" : patient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }

        /*
         * formdata:图片数据，griddata：手动输入结果数据
         *  ids：图片对应的收费项目id，patientcode：体检者体检号
         */
        if (StringUtils.isBlank(patientcode)) {
            throw new ServiceException("该体检者体检号不存在，不能保存处理结果！");
        }
        //取出属性
        List<SgGriddataDto> featureData = param.getGriddata();
        List<SgItemGriddataDto> pictureData = param.getItemGriddata();
        String[] addImg = StringUtils.isBlank(param.getAddImg()) ? new String[0] : param.getAddImg().split("@@@");
        String[] delImg = StringUtils.isBlank(param.getDelImg()) ? new String[0] : param.getDelImg().split(",");
        //都不为空
        if (pictureData.size() > 0 || featureData.size() > 0 || addImg.length > 0 || delImg.length > 0) {

            if (pictureData.size() > 0 && featureData.size() > 0) {
                //保存手填处理结果
                for (SgGriddataDto map : featureData) {
                    OutsideHand oh = mapperFacade.map(map, OutsideHand.class);
                    //删除
                    if ("removed".equals(map.getState())) {
                        OutsideHand feeItem = outsideHandMapper.getInfoById(map.getId());
                        //不为空就删掉
                        if (ObjectUtils.isNotEmpty(feeItem)) {
                            outsideHandMapper.deleteById(feeItem);
                        } else {
                            // 不存在
                            throw new ServiceException("保存失败：收费项目不存在，已经被删除！");
                        }
                        //LIS结果
                        peispatientexamitemMapper.delete(new QueryWrapper<Peispatientexamitem>()
                                .eq("patientcode", patientcode).eq("id_examFeeItem", feeItem.getIdCharge()).eq("id_examItem", feeItem.getIdCheck()));
                        //修改
                    } else if ("modified".equals(map.getState())) {
                        OutsideHand feeItem = outsideHandMapper.getInfoById(map.getId());
                        if (ObjectUtils.isNotEmpty(feeItem)) {
                            outsideHandMapper.updateById(oh);
                            //LIS结果
                            Peispatientexamitem examItemOld = peispatientexamitemMapper.selectOne(new QueryWrapper<Peispatientexamitem>().eq("patientcode", patientcode)
                                    .eq("id_examFeeItem", feeItem.getIdCharge()).eq("id_examItem", feeItem.getIdCheck()));

                            if (ObjectUtils.isEmpty(examItemOld)) {
                                examItemOld = new Peispatientexamitem();
                                examItemOld.setPatientcode(patientcode);
                                examItemOld.setShortCode(shortCode);
                                examItemOld.setIdExamfeeitem(feeItem.getIdCharge());
                                examItemOld.setIdExamitem(feeItem.getIdCheck());
                                examItemOld.setExamfeeitem(map.getIdFee());
                                examItemOld.setExamitemNameR(map.getCheck());
                                examItemOld.setExamitemvaluesreport(feeItem.getResultHand());
                                peispatientexamitemMapper.insert(examItemOld);

                            } else {
                                examItemOld.setIdExamfeeitem(feeItem.getIdCharge());
                                examItemOld.setIdExamitem(feeItem.getIdCheck());
                                examItemOld.setExamfeeitem(map.getIdFee());
                                examItemOld.setExamitemNameR(map.getCheck());
                                examItemOld.setExamitemvaluesreport(feeItem.getResultHand());
                                peispatientexamitemMapper.updateById(examItemOld);
                            }
                        } else {
                            // 不存在
                            throw new ServiceException("保存失败：" + map.getCheck() + "检查项目不存在，已经被删除！");
                        }

                    } else if ("added".equals(map.getState())) {
                        //添加
                        OutsideHand ock = outsideHandMapper.selectOne(new QueryWrapper<OutsideHand>()
                                .eq("patientcode", patientcode).eq("id_check", map.getIdCheck()));

                        if (ObjectUtils.isEmpty(ock)) {
                            oh.setPatientcode(patientcode);
                            outsideHandMapper.insert(oh);
                        } else {
                            throw new ServiceException("该体检者已存在：" + map.getCheck() + "检查项目处理结果");
                        }
                        Peispatientexamitem examItem = new Peispatientexamitem();
                        examItem.setPatientcode(patientcode);
                        examItem.setShortCode(shortCode);
                        examItem.setIdExamfeeitem(oh.getIdCharge());
                        examItem.setIdExamitem(oh.getIdCheck());
                        examItem.setExamitemvaluesreport(oh.getResultHand());
                        examItem.setExamfeeitem(map.getIdFee());
                        examItem.setExamitemNameR(map.getCheck());
                        peispatientexamitemMapper.insert(examItem);
                    }
                }
                //保存图片相关项目
                for (SgItemGriddataDto map : pictureData) {
                    OutsideCheckin oc = mapperFacade.map(map, OutsideCheckin.class);
                    //删除
                    if ("removed".equals(map.getState())) {
                        OutsideCheckin feeItem = outsideCheckinMapper.getInfoById(map.getId());
                        if (ObjectUtils.isNotEmpty(feeItem)) {
                            outsideCheckinMapper.deleteById(feeItem);
                        } else {
                            // 不存在
                            throw new ServiceException("删除失败：收费项目不存在，已经被删除！");
                        }

                    } else if ("modified".equals(map.getState())) {
                        //修改
                        OutsideCheckin feeItem = outsideCheckinMapper.getInfoById(map.getId());
                        if (ObjectUtils.isNotEmpty(feeItem)) {
                            outsideCheckinMapper.updateById(oc);
                        } else {
                            // 不存在
                            throw new ServiceException("保存失败：" + map.getItemName() + "收费项目不存在，已经被删除！");
                        }
                    } else if ("added".equals(map.getState())) {
                        //添加
                        OutsideCheckin ock = outsideCheckinMapper.selectOne(new QueryWrapper<OutsideCheckin>()
                                .eq("patientcode", patientcode).eq("id_charge", map.getIdCharge()));
                        if (ObjectUtils.isEmpty(ock)) {
                            oc.setPatientcode(patientcode);
                            outsideCheckinService.save(oc);
                        } else {
                            throw new ServiceException("该体检者已存在：" + map.getItemName() + "收费项目处理结果");
                        }
                    }
                }

                //如果图片相关项目为空,保存手填结果
            } else if (pictureData.size() == 0) {
                for (SgGriddataDto map : featureData) {
                    OutsideHand oh = mapperFacade.map(map, OutsideHand.class);
                    //删除
                    if ("removed".equals(map.getState())) {
                        OutsideHand feeItem = outsideHandMapper.getInfoById(map.getId());
                        if (ObjectUtils.isNotEmpty(feeItem)) {
                            outsideHandMapper.deleteById(feeItem);
                        } else {
                            // 不存在
                            throw new ServiceException("保存失败：收费项目不存在，已经被删除！");
                        }
                        //删除LIS结果
                        peispatientexamitemMapper.delete(new QueryWrapper<Peispatientexamitem>().eq("patientcode", patientcode)
                                .eq("id_examFeeItem", feeItem.getIdCharge()).eq("id_examItem", feeItem.getIdCheck()));
                    } else if ("modified".equals(map.getState())) {
                        //修改
                        OutsideHand feeItem = outsideHandMapper.getInfoById(map.getId());
                        if (ObjectUtils.isNotEmpty(feeItem)) {
                            Peispatientexamitem examItemOld = peispatientexamitemMapper.selectOne(new QueryWrapper<Peispatientexamitem>().eq("patientcode", patientcode)
                                    .eq("id_examFeeItem", feeItem.getIdCharge()).eq("id_examItem", feeItem.getIdCheck()));

                            outsideHandMapper.updateById(oh);
                            if (ObjectUtils.isEmpty(examItemOld)) {
                                examItemOld = new Peispatientexamitem();
                                examItemOld.setPatientcode(patientcode);
                                examItemOld.setShortCode(shortCode);
                                examItemOld.setIdExamfeeitem(feeItem.getIdCharge());
                                examItemOld.setIdExamitem(feeItem.getIdCheck());
                                examItemOld.setExamfeeitem(map.getIdFee());
                                examItemOld.setExamitemNameR(map.getCheck());
                                examItemOld.setExamitemvaluesreport(feeItem.getResultHand());
                                peispatientexamitemMapper.insert(examItemOld);

                            } else {
                                examItemOld.setIdExamfeeitem(feeItem.getIdCharge());
                                examItemOld.setIdExamitem(feeItem.getIdCheck());
                                examItemOld.setExamfeeitem(map.getIdFee());
                                examItemOld.setExamitemNameR(map.getCheck());
                                examItemOld.setExamitemvaluesreport(feeItem.getResultHand());
                                peispatientexamitemMapper.updateById(examItemOld);
                            }
                        } else {
                            // 不存在
                            throw new ServiceException("保存失败：" + map.getCheck() + "检查项目不存在，已经被删除！");
                        }
                    } else if ("added".equals(map.getState())) {
                        //添加
                        OutsideHand ock = outsideHandMapper.selectOne(new QueryWrapper<OutsideHand>()
                                .eq("patientcode", patientcode).eq("id_check", map.getIdCheck()));
                        if (ObjectUtils.isEmpty(ock)) {
                            oh.setPatientcode(patientcode);
                            outsideHandMapper.insert(oh);
                        } else {
                            throw new ServiceException("该体检者已存在：" + map.getCheck() + "检查项目处理结果");
                        }
                        //LIS结果
                        Peispatientexamitem examItem = new Peispatientexamitem();
                        examItem.setPatientcode(patientcode);
                        examItem.setShortCode(shortCode);
                        examItem.setIdExamfeeitem(oh.getIdCharge());
                        examItem.setIdExamitem(oh.getIdCheck());
                        examItem.setExamitemvaluesreport(oh.getResultHand());
                        examItem.setExamfeeitem(map.getIdFee());
                        examItem.setExamitemNameR(map.getCheck());
                        peispatientexamitemMapper.insert(examItem);
                    }
                }
                //手填处理结果为空,保存图片相关
            } else {
                for (SgItemGriddataDto map : pictureData) {
                    OutsideCheckin oc = mapperFacade.map(map, OutsideCheckin.class);
                    //删除
                    if ("removed".equals(map.getState())) {
                        OutsideCheckin feeItem = outsideCheckinMapper.getInfoById(map.getId());
                        if (ObjectUtils.isNotEmpty(feeItem)) {
                            outsideCheckinMapper.deleteById(feeItem);
                        } else {
                            // 不存在
                            throw new ServiceException("保存失败：收费项目不存在，已经被删除！");
                        }

                    } else if ("modified".equals(map.getState())) {
                        //修改
                        OutsideCheckin feeItem = outsideCheckinMapper.getInfoById(map.getId());
                        if (ObjectUtils.isNotEmpty(feeItem)) {
                            outsideCheckinMapper.updateById(oc);
                        } else {
                            // 不存在
                            throw new ServiceException("保存失败：" + map.getItemName() + "收费项目不存在，已经被删除！");
                        }
                    } else if ("added".equals(map.getState())) {
                        //添加
                        OutsideCheckin ock = outsideCheckinMapper.selectOne(new QueryWrapper<OutsideCheckin>()
                                .eq("patientcode", patientcode).eq("id_charge", map.getIdCharge()));
                        if (ObjectUtils.isEmpty(ock)) {
                            oc.setPatientcode(patientcode);
                            outsideCheckinMapper.insert(oc);
                        } else {
                            throw new ServiceException("该体检者已存在：" + map.getItemName() + "收费项目处理结果");
                        }
                    }
                }
            }
            OutsideMain om = outsideMainMapper.selectOne(new QueryWrapper<OutsideMain>().eq("patientcode", patientcode));
            if (ObjectUtils.isNotEmpty(om)) {
                String userId = SecurityUtils.getUserNo();
                Date back = new Date();
                om.setBackDate(back);
                om.setStatus(1);
                om.setBackPeople(userId);
                outsideMainMapper.updateById(om);
            }
        } else {
            throw new ServiceException("无保存内容，请检查页面");
        }
        //删除图片
        if (delImg.length > 0) {
            for (int i = 0, l = delImg.length; i < l; i++) {
                String filePath = delImg[i];
                //删除
                outsidePictrueMapper.delete(new QueryWrapper<OutsidePictrue>().eq("patientcode", patientcode)
                        .eq("pictrue_position", filePath));
            }
        }
        //添加图片
        if (addImg.length > 0) {
            //查询最大序号+1
            int maxIndex = outsidePictrueMapper.selectMaxIndex(patientcode) + 1;
            for (int i = 0, l = addImg.length; i < l; i++) {
                String[] array = addImg[i].split("@chargeIds:");
                String ori = array[0];
                int orderIndex = maxIndex + i;
                OutsidePictrue op = new OutsidePictrue();
                op.setPatientcode(patientcode);
                op.setOrderIndex(orderIndex);
                op.setPictruePosition(ori);
                op.setChargeId(array[1]);
                outsidePictrueMapper.insert(op);
            }
        }

        HashSet<String> set = new HashSet<String>();
        List<OutsidePictrue> pics = outsidePictrueMapper.selectList(new QueryWrapper<OutsidePictrue>().eq("patientcode", patientcode));
        for (OutsidePictrue pic : pics) {
            //收费项目ID为空
            if (ObjectUtils.isEmpty(pic.getChargeId())) {
                continue;
            }
            //分割
            String[] chargeIds = pic.getChargeId().split(",");
            StringBuilder chargeId = new StringBuilder();
            for (String cid : chargeIds) {
                //外送项目图片关联表
                Long count = outsideCheckinMapper.selectCount(new QueryWrapper<OutsideCheckin>().eq("id_charge", cid));
                if (count > 0) {
                    chargeId.append(cid + ",");
                    set.add(cid);
                }
            }
            pic.setChargeId(chargeId.length() > 0 ? chargeId.substring(0, chargeId.length() - 1).toString() : null);
            outsidePictrueMapper.updateById(pic);
        }
        //外送手动结果表
        List<OutsideHand> hands = outsideHandMapper.selectList(new QueryWrapper<OutsideHand>().eq("patientcode", patientcode));
        for (OutsideHand hand : hands) {
            set.add(hand.getIdCharge());
        }
        //外送项目图片关联表
        List<OutsideCheckin> checkins = outsideCheckinMapper.selectList(new QueryWrapper<OutsideCheckin>().eq("patientcode", patientcode));
        for (OutsideCheckin check : checkins) {
            //有图片结果或手录结果就设置为已处理，收费项目设为已检
            if (set.contains(check.getIdCharge())) {
                check.setStatus("1");
                //收费项目表
                Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", patientcode).eq("id_examfeeitem", check.getIdCharge())
                        .isNull("f_transferedhl7").eq("sfjj", 0).eq("f_giveup", 0)
                        .eq("change_item", 0).eq("f_feecharged", 1));
                if (ObjectUtils.isNotEmpty(feeitem)) {
                    //已检
                    feeitem.setFExaminated(1);
                    feeitem.setExaminatetime(new Date());
                    peispatientfeeitemMapper.updateById(feeitem);
                }
            } else {
                check.setStatus("0");
                Peispatientfeeitem feeitem = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", patientcode).eq("id_examfeeitem", check.getIdCharge())
                        .eq("f_giveup", 0).eq("sfjj", 0).eq("change_item", 0).eq("f_feecharged", 1));
                if (ObjectUtils.isNotEmpty(feeitem)) {
                    //未检
                    feeitem.setFExaminated(0);
                    peispatientfeeitemMapper.updateById(feeitem);
                }
            }
            outsideCheckinMapper.updateById(check);
        }

        if (getAllSfxmtzjStatus(patientcode)) {
            //0:已备单 1:分检完成
            patient.setFReadytofinal(1);
            peisStateService.setScbs(patient.getPatientcode(), 0);
            patient.setReadytofinalDate(new Date());
            List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientcode);
            for (Peispatientfeeitem other : other_items) {
                //设置未关联科室项目为已检
                other.setFExaminated(1);
            }
            peispatientfeeitemService.updateBatchById(other_items);
        }
        patient.setFExamstarted(1);//检查开始（反审核不用改）
        peispatientMapper.updateById(patient);
        return Boolean.TRUE;
    }

    @Override
    public boolean getAllSfxmtzjStatus(String inputCode) {
        List<String> depIds = sysDeptMapper.getFunctionKsIds(1);
        if (depIds.size() == 0) {
            return true;
        }
        Long count = peispatientfeeitemMapper.selectCount(new QueryWrapper<Peispatientfeeitem>()
                .in("id_ks", depIds)
                .eq("change_item", 0)
                .eq("id_patient", inputCode)
                .eq("sfjj", 0)//据检
                .eq("f_giveup", 0)//弃检
                .isNull("f_transferedhl7")//补检状态 0: 未补检 1：已补检
                .isNotNull("id_ks")
                .ne("f_examinated", 1) //0：未检；1：已检；
        );
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<String> ids) {

        for (int i = 0; i < ids.size(); i++) {
            //外送登记结果主表
            OutsideMain om = outsideMainMapper.getInfoById(ids.get(i));
            if (ObjectUtils.isNotEmpty(om)) {
                //外送手动结果表
                List<OutsideHand> list = outsideHandMapper.selectList(new QueryWrapper<OutsideHand>().eq("patientcode", om.getPatientcode()));
                if (list.size() > 0) {
                    for (OutsideHand out : list) {
                        outsideHandMapper.deleteById(out);
                    }
                }
                //外送项目图片关联表
                List<OutsideCheckin> list1 = outsideCheckinMapper.selectList(new QueryWrapper<OutsideCheckin>().eq("patientcode", om.getPatientcode()));
                if (list1.size() > 0) {
                    for (OutsideCheckin ouc : list1) {
                        outsideCheckinMapper.deleteById(ouc);
                    }
                }
                //将isDelete设置为1，为删除
//                om.setIsDelete(1);
                om.setStatus(0);
                peispatientexamitemMapper.delete(new QueryWrapper<Peispatientexamitem>().eq("patientcode", om.getPatientcode()));
                outsideMainMapper.updateById(om);

                //删除图片
                outsidePictrueService.remove(new LambdaQueryWrapper<OutsidePictrue>().eq(OutsidePictrue::getPatientcode,om.getPatientcode()));

            }
        }
        return Boolean.TRUE;
    }

    /**
     * 外送登记信息-查询
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SendGovernVo> getMainListData(PageParam<OutsideMain> page, SendGovernParam param) {
        return outsideMainMapper.getMainListData(page,param);
    }
}

