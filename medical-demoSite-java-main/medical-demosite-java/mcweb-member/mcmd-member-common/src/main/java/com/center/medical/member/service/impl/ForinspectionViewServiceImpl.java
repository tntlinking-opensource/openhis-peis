package com.center.medical.member.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.FailTotalVisit;
import com.center.medical.bean.model.HandleNewProjects;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.bean.param.ComboExamItemParam;
import com.center.medical.member.bean.param.ForinspectionViewEditParam;
import com.center.medical.member.bean.param.ForinspectionViewParam;
import com.center.medical.member.bean.vo.ForinspectionViewEditVo;
import com.center.medical.member.bean.vo.ForinspectionViewVo;
import com.center.medical.member.dao.ForinspectionViewMapper;
import com.center.medical.member.service.ForinspectionViewService;
import com.center.medical.member.service.VisitMainService;
import com.center.medical.service.*;
import com.center.medical.system.dao.SysDeptMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 迟补检回访
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表服务实现类
 *
 * @author makejava
 * @since 2023-02-10 09:46:44
 */
@Slf4j
@Service("forinspectionViewService")
@RequiredArgsConstructor
public class ForinspectionViewServiceImpl extends ServiceImpl<ForinspectionViewMapper, VisitMain> implements ForinspectionViewService {

    private final ForinspectionViewMapper forinspectionViewMapper;
    private final MapperFacade mapperFacade;
    private final VisitMainService visitMainService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final FailTotalVisitService failTotalVisitService;
    private final HandleNewProjectsService handleNewProjectsService;
    private final PeispatientService peispatientService;
    private final PeisStateService peisStateService;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final SysDeptMapper sysDeptMapper;


    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param VisitMain查询参数
     * @return 分页数据
     */
    @Override
    public IPage<ForinspectionViewVo> getPage(PageParam<ForinspectionViewVo> page, ForinspectionViewParam param) {
        //去空格大写
        //体检码
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //登记开始时间00点00分
        if (ObjectUtils.isNotEmpty(param.getRegStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getRegStartTime());
            param.setRegStartTime(startTime);
        }
        //登记结束时间23点59分
        if (ObjectUtils.isNotEmpty(param.getRegEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getRegEndTime());
            param.setRegEndTime(endTime);
        }
        //处理开始时间00点00分
        if (ObjectUtils.isNotEmpty(param.getVisitStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getVisitStartTime());
            param.setVisitStartTime(startTime);
        }
        //处理结束时间23点59分
        if (ObjectUtils.isNotEmpty(param.getVisitEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getVisitEndTime());
            param.setVisitEndTime(endTime);
        }
        //预处理开始时间00点00分
        if (ObjectUtils.isNotEmpty(param.getPreStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getPreStartTime());
            param.setPreStartTime(startTime);
        }
        //预处理结束时间23点59分
        if (ObjectUtils.isNotEmpty(param.getPreEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getPreEndTime());
            param.setPreEndTime(endTime);
        }
        //处理结果
        List<String> strs = param.getSflj();
        if (CollectionUtils.isNotEmpty(strs)) {
            //包含-1
            if (strs.contains("-1")) {
                strs.remove("-1");
                //设置拼接
                if (strs.size() > 0) {
                    param.setJoin(join(strs, ","));
                    param.setFlag(1);
                } else {
                    param.setFlag(2);
                }
            } else {
                param.setJoin(join(strs, ","));
                param.setFlag(3);
            }
        }
        //取出属性
        IPage<ForinspectionViewVo> iPage = forinspectionViewMapper.getPage(page, param);
        List<ForinspectionViewVo> records = iPage.getRecords();
        for (ForinspectionViewVo record : records) {
            ComboExamItemParam param1 = new ComboExamItemParam();
            param1.setItemId(record.getItemId());
            param1.setMedicaltype(record.getMedicaltype());
            param1.setJhys(record.getJhys());
            param1.setTjlx(record.getTjlx());
            record.setIszy(isZy(param1));
        }
        //放回属性
        iPage.setRecords(records);
        return iPage;
    }


    public static String join(Collection<String> strs, String sep) {

        if (strs == null || strs.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            builder.append(str + sep);
        }
        return builder.substring(0, builder.length() - sep.length());
    }

    /**
     * 迟补检回访导出
     *
     * @param param
     * @return
     */
    public List<ForinspectionViewVo> export(ForinspectionViewParam param) {
        //去空格大写
        //体检码
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //登记开始时间00点00分
        if (ObjectUtils.isNotEmpty(param.getRegStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getRegStartTime());
            param.setRegStartTime(startTime);
        }
        //登记结束时间23点59分
        if (ObjectUtils.isNotEmpty(param.getRegEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getRegEndTime());
            param.setRegEndTime(endTime);
        }
        //处理开始时间00点00分
        if (ObjectUtils.isNotEmpty(param.getVisitStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getVisitStartTime());
            param.setVisitStartTime(startTime);
        }
        //处理结束时间23点59分
        if (ObjectUtils.isNotEmpty(param.getVisitEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getVisitEndTime());
            param.setVisitEndTime(endTime);
        }
        //预处理开始时间00点00分
        if (ObjectUtils.isNotEmpty(param.getPreStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getPreStartTime());
            param.setPreStartTime(startTime);
        }
        //预处理结束时间23点59分
        if (ObjectUtils.isNotEmpty(param.getPreEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getPreEndTime());
            param.setPreEndTime(endTime);
        }
        //处理结果
        List<String> strs = param.getSflj();
        if (CollectionUtils.isNotEmpty(strs)) {
            //包含-1
            if (strs.contains("-1")) {
                //设置拼接
                String join = join(strs, ",");
                param.setJoin(join);
                strs.remove("-1");
                if (strs.size() > 0) {
                    param.setFlag(1);
                } else {
                    param.setFlag(2);
                }
            } else {
                param.setFlag(3);
            }
        }
        //取出属性
        List<ForinspectionViewVo> records = forinspectionViewMapper.export(param);
        for (ForinspectionViewVo record : records) {
            ComboExamItemParam param1 = new ComboExamItemParam();
            param1.setItemId(record.getItemId());
            param1.setMedicaltype(record.getMedicaltype());
            param1.setJhys(record.getJhys());
            param1.setTjlx(record.getTjlx());
            record.setIszy(isZy(param1));
        }
        //放回属性
        return records;
    }


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public ForinspectionViewEditVo getInfoById(String id) {
        return forinspectionViewMapper.getInfoById(id);
    }

    /**
     * 迟补检回访回访操作
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(ForinspectionViewEditParam param) {
        //不合格样本回访表一对多关联
        VisitMain vm = mapperFacade.map(param, VisitMain.class);
        String patientCode = vm.getPatientcode();
        //不合格样本回访
        FailTotalVisit fv = mapperFacade.map(param, FailTotalVisit.class);
        List<VisitMain> vms = visitMainService.list(new QueryWrapper<VisitMain>().eq("patientcode", patientCode)
                .in("type", new Integer[]{0, 4}));
        // 设置一个变量，弃检时操作
        Boolean fj = false;
        String userId = SecurityUtils.getUserNo();

        for (VisitMain visitMain : vms) {
            //不合格样本回访
            FailTotalVisit fvNews = failTotalVisitService.getOne(new QueryWrapper<FailTotalVisit>()
                    .eq("visit_main_id", visitMain.getId()));
            if (fvNews == null || fvNews.getSflj() != fv.getSflj()) {
                //如果弃检
                if (fv.getSflj() == 0) {
                    Peispatientfeeitem pf = peispatientfeeitemService.getOne(new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_patient", patientCode).eq("id", visitMain.getIdExamfeeitem()));
                    if (pf != null) {
                        pf.setFGiveup(1);//0：弃减状态
                        peispatientfeeitemService.updateById(pf);
                        fj = true;

                        HandleNewProjects handleNewProjects = handleNewProjectsService.getOne(
                                new QueryWrapper<HandleNewProjects>().eq("patientcode", patientCode)
                                        .eq("projects_id", pf.getId()).eq("is_delete", 0).eq("handle_type", 1)
                        );
                        // 不存在
                        if (ObjectUtils.isEmpty(handleNewProjects)) {
                            handleNewProjects = new HandleNewProjects();
                            handleNewProjects.setPatientcode(patientCode);
                            handleNewProjects.setCreateId(userId);
                            handleNewProjects.setModifyId(userId);
                            handleNewProjects.setProjectsId(pf.getId());// 项目ID
                            handleNewProjects.setIsDelete(0);
                            handleNewProjects.setStatus(0);
                            handleNewProjects.setHandleType(1);
                            // 保存实体类
                            boolean cId = handleNewProjectsService.save(handleNewProjects);
                            if (cId == false) {
                                throw new ServiceException("保存失败：" + pf.getExamfeeitemName() + " 收费项目弃检保存失败！");
                            }
                        } else {
                            handleNewProjects.setModifyId(userId);
                            handleNewProjects.setProjectsId(pf.getId());// 项目ID

                            // 更新实体类
                            handleNewProjectsService.updateById(handleNewProjects);
                        }

                    }
                }
                // 如果选补检
                else if (fv.getSflj() == 2) {
                    Peispatientfeeitem pfs = peispatientfeeitemService.getOne(new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_patient", vm.getPatientcode()).eq("id", visitMain.getIdExamfeeitem()));
                    if (pfs != null) {
                        pfs.setFTransferedhl7(0);//补检状态
                        peispatientfeeitemService.updateById(pfs);
                        //检验科加项处理
                        HandleNewProjects handleNewProjects = handleNewProjectsService.getOne(new QueryWrapper<HandleNewProjects>()
                                .eq("patientcode", patientCode).eq("projects_id", pfs.getId())
                                .eq("is_delete", 0).eq("handle_type", 2));
                        // 不存在
                        if (ObjectUtils.isEmpty(handleNewProjects)) {
                            handleNewProjects = new HandleNewProjects();
                            handleNewProjects.setPatientcode(patientCode);
                            handleNewProjects.setCreateId(userId);
                            handleNewProjects.setModifyId(userId);
                            handleNewProjects.setProjectsId(pfs.getId());// 项目ID
                            handleNewProjects.setIsDelete(0);
                            handleNewProjects.setStatus(0);
                            handleNewProjects.setHandleType(2);
                            handleNewProjectsService.save(handleNewProjects);
                        } else {
                            handleNewProjects.setModifyId(userId);
                            handleNewProjects.setProjectsId(pfs.getId());// 项目ID

                            // 更新实体类
                            handleNewProjectsService.updateById(handleNewProjects);
                        }
                    }
                }
                // 如果选择迟捡来检
                else if (fv.getSflj() == 1) {
                    Peispatientfeeitem pfc = peispatientfeeitemService.getOne(new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_patient", patientCode).eq("id", visitMain.getIdExamfeeitem()));
                    if (pfc != null) {
                        //pfc.setFDelayed(1);//迟捡状态
                        pfc.setFExaminated(1);//已检
                        peispatientfeeitemService.updateById(pfc);
                    }
                } else {
                    // 选择其他无任何操作
                }
            }

            // 保存
            if (fvNews == null) {
                FailTotalVisit fvsave = new FailTotalVisit();
                fvsave = fv;
                // 回访保存
                fvsave.setVisitMainId(visitMain.getId());// 设置与VisitMain关联的id
                fvsave.setVisitType(0);// 设置为迟捡回访
                fvsave.setPersoncode(patientCode);// 设置体检号
                fvsave.setVisitId(userId == null ? null : SecurityUtils.getUsername());// 回访人
                fvsave.setId(null);
                failTotalVisitService.save(fvsave);
            } else {
                fvNews.setSflj(fv.getSflj());
                fvNews.setLjsj(fv.getLjsj());
                fvNews.setVisitTime(fv.getVisitTime());
                fvNews.setMemo(fv.getMemo());
                fvNews.setVisitType(0);
                fvNews.setVisitId(userId == null ? null : SecurityUtils.getUsername());
                fvNews.setPreTime(fv.getPreTime());
                fvNews.setPreResult(fv.getPreResult());
                failTotalVisitService.updateById(fvNews);
            }
        }
        // 判断剩下的项目是否分检已完成
        if (fj == true) {
            Peispatient pei = peispatientService.getOne(new QueryWrapper<Peispatient>()
                    .eq("patientcode", vm.getPatientcode()).eq("f_registered", 1));
            if (pei == null) {
                throw new ServiceException("该体检者未登记！");
            }

            if (getAllSfxmtzjStatus(patientCode)) {
                pei.setFReadytofinal(1);
                peisStateService.setScbs(pei.getPatientcode(), 0);
                pei.setReadytofinalDate(new Date());
                peispatientService.updateById(pei);
                List<Peispatientfeeitem> others = peispatientfeeitemMapper.getNoCheckItems(patientCode);

                for (Peispatientfeeitem peispatientfeeitem : others) {
                    peispatientfeeitem.setFExaminated(1);//设置未关联科室项目为已检,反审核时不改回去
                }
                // 批量更新收费实体类
                peispatientfeeitemService.updateBatchById(others);
            } else {
                pei.setFReadytofinal(0);
                peisStateService.setScbs(pei.getPatientcode(), 0);
                peispatientService.updateById(pei);
            }
        }
        return Boolean.TRUE;
    }


    /**
     * isZy
     *
     * @param param
     * @return
     */
    @Override
    public boolean isZy(ComboExamItemParam param) {
        if ("0".equals(param.getTjlx())) {
            return false;
        }
        if (ObjectUtils.isEmpty(param.getJhys()) || ObjectUtils.isEmpty(param.getMedicaltype()) || ObjectUtils.isEmpty(param.getItemId())) {
            return false;
        }
        param.setJhys(param.getJhys());
        String obj = forinspectionViewMapper.isZy(param);
        return Integer.parseInt(obj) > 0;
    }

    private boolean getAllSfxmtzjStatus(String inputCode) {
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

}

