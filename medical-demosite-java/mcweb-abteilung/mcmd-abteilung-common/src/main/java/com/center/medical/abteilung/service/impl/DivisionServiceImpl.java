package com.center.medical.abteilung.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.dao.*;
import com.center.medical.abteilung.bean.dto.*;
import com.center.medical.abteilung.bean.model.*;
import com.center.medical.abteilung.bean.param.*;
import com.center.medical.abteilung.bean.vo.*;
import com.center.medical.abteilung.dao.*;
import com.center.medical.abteilung.service.DivisionService;
import com.center.medical.abteilung.service.SignInInspectionService;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.FileTypePath;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.bean.vo.SfxmVo;
import com.center.medical.data.service.PatienttypeService;
import com.center.medical.reception.bean.vo.GetInspectReportVo;
import com.center.medical.reception.dao.PeispatientexamitemMapper;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.bean.model.Riskclient;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import com.center.medical.sellcrm.dao.ComboexamitemMapper;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.sellcrm.dao.RiskclientMapper;
import com.center.medical.sellcrm.dao.RiskclientconMapper;
import com.center.medical.service.*;
import com.center.medical.system.bean.vo.AllUserDataVo;
import com.center.medical.system.dao.SysConfigMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.BranchService;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * KS科室检查结果主表(SectionResultMain)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:18
 */
@Slf4j
@Service("divisionService")
@RequiredArgsConstructor
public class DivisionServiceImpl extends ServiceImpl<DivisionMapper, SectionResultMain> implements DivisionService {

    private final DivisionMapper divisionMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final SysUserMapper sysUserMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final BasexamltemMapper basexamltemMapper;
    private final BasexamltemSignMapper basexamltemSignMapper;
    private final ItemsMapper itemsMapper;
    private final BasconclusionMapper basconclusionMapper;
    private final PeispatientMapper peispatientMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SectionRemindMapper sectionRemindMapper;
    private final SectionAndRemindMapper sectionAndRemindMapper;
    private final SysConfigMapper sysConfigMapper;
    private final ISysBranchService iSysBranchService;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PatienttypeService patienttypeService;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final MapperFacade mapperFacade;
    private final TempFeeitemMapper tempFeeitemMapper;
    private final SignInInspectionService signInInspectionService;
    private final PeispatientConsultationService peispatientConsultationService;
    private final PeispatientConsultationPicService peispatientConsultationPicService;
    private final WzZysMapper wzZysMapper;
    private final WzZybsMapper wzZybsMapper;
    private final OutsideMainService outsideMainService;
    private final PeisStateService peisStateService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ComboexamitemMapper comboexamitemMapper;
    private final RiskclientconMapper riskclientconMapper;
    private final RiskclientMapper riskclientMapper;
    private final InspectChargeMapper inspectChargeMapper;
    private final DescribeMapper describeMapper;
    private final AttachmentMapper attachmentMapper;
    private final ElectroAudiometerMapper electroAudiometerMapper;
    private final TjregMapper tjregMapper;
    private final LungMapper lungMapper;
    private final OutsidePictrueMapper outsidePictrueMapper;
    private final PeispatientexamitemMapper peispatientexamitemMapper;
    private final HarmMapper harmMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final BranchService branchService;
    private final ISysConfigService iSysConfigService;

    @Autowired
    private LoadProperties loadProperties;


    /**
     * 获取科室列表数据
     *
     * @param patientCode
     * @return
     */
    @Override
    public List<DivisionDto> getListData(String patientCode) {
//        String cId = SecurityUtils.getCId();
        String userNo = SecurityUtils.getUserNo();
        //获取默认分中心和兼职分中心
        List<String> cids = branchService.getUserCid(userNo);
        List<DivisionDto> list = new ArrayList<>();
        //输入码为空
        if (StringUtils.isEmpty(patientCode)) {
            list = divisionMapper.getListData(cids, userNo);
        } else {
            list = divisionMapper.getListDataByCode(cids, userNo, patientCode);
        }
        return list;
    }


    /**
     * 查看列队数据(职业性问诊单独用getpatientdata)
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<RankDataVo> getRankData(PageParam<Peispatientexamitem> page, RankDataParam param) {
        param.setUserName(SecurityUtils.getUsername());
        //体检码去空格大写
        if (ObjectUtils.isNotEmpty(param.getPatientcode())) {
            param.setPatientcode(param.getPatientcode().trim().toUpperCase());
        }
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return divisionMapper.getRankData(page, param);
    }


    /**
     * 获取审核之后的数据
     *
     * @return
     */
    @Override
    public NkCheckedVo getNkCheckedData(String inputCode, String ksID) {
        //返回对象
        NkCheckedVo nkVo = new NkCheckedVo();
        // Peispatient user = this.get(Peispatient.class,
        // Restrictions.eq("patientcode", inputCode));
        // String idExamtype = user.getIdExamtype();//体检类型
        // [{id:0,text:"健康体检"},{id:1,text:"职业体检"},{id:2,text:"综合"}]
        List<NkCheckedDataVo> data = new ArrayList<>();
        String ms = "";
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksID).eq("patientcode", inputCode));
        // ,Restrictions.eq("isAudit",true ));
        if (ObjectUtils.isEmpty(main)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //健康小结
        String xj = main.getConclusions() == null ? "" : main
                .getConclusions();
        // 检查人Id
        String jcr = main.getRummagerId();
        String jcrName = null;
        if (jcr != null) {
            SysUser rummager = sysUserMapper.selectUserByUserNo(jcr);
            if (rummager != null) {
                // 检查人名称
                jcrName = rummager.getUserName();
            }
        }
        //录入人ID
        String lrr = main.getWriteId();
        String lrrName = null;
        if (lrr != null) {
            SysUser rummager = sysUserMapper.selectUserByUserNo(lrr);
            if (rummager != null) {
                //录入人姓名
                lrrName = rummager.getUserName();
            }
        }
        // 检查时间
        String jcsj = main.getRummagerTime() == null ? "" : sdf.format(main
                .getRummagerTime());
        String lrsj = main.getWriteTime() == null ? "" : sdf.format(main
                .getWriteTime());
        // 是否审核

        Boolean sfsh = ObjectUtils.isNotEmpty(main.getIsAudit()) ? main.getIsAudit() == 1 ? true : false : false;

        if (main != null) {
            //科室检查结果表
            List<SectionResultTwo> list = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                            .eq("main_id", main.getId())
//                            .isNotNull("nodule")
//                            .or().isNotNull("input_result")
            );
            if (list != null && list.size() > 0) {
                for (SectionResultTwo two : list) {
                    // 检查项目Id
                    String jcxmId = two.getVerdictId();
                    if (StringUtils.isBlank(jcxmId)) {
                        continue;
                    }
                    // 体征词Id
                    String tzcId = two.getNodule();
                    // 是否弃检
                    Integer qij = two.getIsUnchecked();
                    //检查项目表
                    Basexamltem jcxm = basexamltemMapper.getInfoById(jcxmId);
                    // 检查项目名称
                    String jxcmmc = jcxm == null ? "" : jcxm.getExamitemName();
                    // 检查项目体证词关联表
                    BasexamltemSign tzc = basexamltemSignMapper.getInfoById(tzcId);
                    // 收费项目id
                    String sfxmId = two.getChargesId();
                    if (StringUtils.isBlank(sfxmId)) {
                        continue;
                    }

                    Items items = itemsMapper.getInfoById(sfxmId);
                    //收费项目名称
                    String sfxmmc = ObjectUtils.isNotEmpty(items) ? items.getExamfeeitemName() : "";
                    // 体征词名称
                    String tzcmc = tzc == null ? "" : tzc.getName();
                    ms = two.getMs();
                    // 自由输入结果
                    String inputResult = two.getInputResult();
                    //是否危急值
                    String wjzjb = two.getIsDanger() == null ? "" : two
                            .getIsDanger().toString();

                    NkCheckedDataVo vo = new NkCheckedDataVo();
                    vo.setQij(qij);
                    vo.setWjzjb(wjzjb);
                    vo.setSfxmmc(sfxmmc);
                    vo.setJxcmmc(jxcmmc);
                    vo.setTzcmc(tzcmc);
                    vo.setMs(ms);
                    vo.setInputResult(inputResult);
                    data.add(vo);
                }
            }
        }

        nkVo.setJcr(jcr);
        nkVo.setJcsj(jcsj);
        nkVo.setXj(xj);
        nkVo.setSfsh(sfsh);
        nkVo.setData(data);
        nkVo.setJcrName(jcrName);
        nkVo.setLrrName(lrrName);
        nkVo.setLrsj(lrsj);
        nkVo.setLrr(lrr);
        nkVo.setData(data);

        return nkVo;
    }


    /**
     * 结伦词列表数据
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @Override
    public List<HashMap<String, String>> jlcData(String patientcode, String ksID) {
        // 科室检查结果表
        List<SectionResultTwo> twos = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                .orderByDesc("createDate").eq("patientcode", patientcode).eq("division_id", ksID));
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        for (SectionResultTwo two : twos) {
            HashMap<String, String> map = new HashMap<String, String>();
            // 在搜索结论词与小结时必须先以本ID查询到所有的相关结论词，
            // 然后再按照检查项目体证词关联表ID找到所对应的体证词（小结）与结论词ID
            Basexamltem ltem = basexamltemMapper.getInfoById(two.getVerdictId());
            if (ObjectUtils.isNotEmpty(ltem)) {
                map.put("jcxm", ltem.getExamitemName());
            }
            // 检查项目体证词关联表
            BasexamltemSign sign = basexamltemSignMapper.getInfoById(two.getNodule());
            if (sign != null) {
                map.put("tzc", sign.getName());
            }
            map.put("jlcId", two.getBasconclusionId());
            Basconclusion con = basconclusionMapper.getInfoById(two.getBasconclusionId());

            if (ObjectUtils.isNotEmpty(con)) {
                map.put("jlcName", con.getName());
            }
            map.put("tzcId", two.getNodule());
            //检验科用
            map.put("verdictId", two.getVerdictId());
            map.put("chargesId", two.getChargesId());

            result.add(map);
        }
        return result;
    }


    /**
     * C13读卡数据
     *
     * @param parammap
     * @param tjzsfxm
     * @return
     */
    @Override
    public List getC13data(HashMap<String, String> parammap, List<Peispatientfeeitem> tjzsfxm) {
        String ksId = parammap.get("ksId");
        String tjh = parammap.get("tjh");// 体检号
        String tjlx = parammap.get("tjlx");// 体检类型
//        List<HashMap<String, List<HashMap<String, String>>>> data = new ArrayList<HashMap<String, List<HashMap<String, String>>>>();
        // 体检号和科室号都不为空时才有数据
        List<GetC13dataDto> result = new ArrayList<>();
        if (StringUtils.isNotBlank(ksId) && StringUtils.isNotBlank(tjh)) {
            //查询数据
            result = divisionMapper.C13NkdataDto(tjlx, tjh, ksId);
        }
        return result;
    }


    /**
     * 获取结论词
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @Override
    public List<HashMap<String, String>> getJlcData(String patientcode, String ksID) {
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        if (StringUtils.isNotBlank(patientcode)) {
            patientcode = patientcode.trim().toUpperCase();
            List<SectionResultTwo> list = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                    .orderByDesc("createdate").eq("patientcode", patientcode)
                    .eq("division_id", ksID).isNotNull("basconclusion_id")
            );
            if (list != null && list.size() > 0) {
                //科室检查结果表
                for (SectionResultTwo srt : list) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", srt.getId());
                    String tzcId = srt.getNodule();
                    map.put("tzcId", tzcId);
                    // 检查项目体证词关联表
                    BasexamltemSign sign = basexamltemSignMapper.getInfoById(tzcId);
                    map.put("tzc", sign == null ? "" : sign.getName());
                    //在搜索结论词与小结时必须先以本ID查询到所有的相关结论词，
                    // 然后再按照检查项目体证词关联表ID找到所对应的体证词（小结）与结论词ID
                    String jcxmId = srt.getVerdictId();
                    if (StringUtils.isNotEmpty(jcxmId)) {
                        Basexamltem b = basexamltemMapper.getInfoById(jcxmId);
                        map.put("jcxm", b == null ? "" : b.getExamitemName());
                    } else {
                        map.put("jcxm", "");
                    }
                    //结论词Id
                    String jlcId = srt.getBasconclusionId();
                    if (StringUtils.isNotBlank(jlcId)) {
                        Basconclusion conclusion = basconclusionMapper.getInfoById(jlcId);
                        map.put("jlcId", jlcId);
                        map.put("name", conclusion == null ? "" : conclusion.getName());
                    } else {
                        map.put("jlcId", "");
                        map.put("name", "");
                    }
                    String sfxmId = srt.getChargesId();
                    map.put("sfxmId", sfxmId);
                    map.put("jcxmId", srt.getVerdictId());
                    data.add(map);
                }
            }
        }

        return data;
    }

    /**
     * 查看档案 体检者列表数据
     *
     * @param page
     * @param patientcode
     * @return
     */
    @Override
    public IPage<ArchiveDataVo> getArchiveData(PageParam<ArchiveDataVo> page, String patientcode) {
        IPage<ArchiveDataVo> ipage = new Page<>();
        if (StringUtils.isNotEmpty(patientcode)) {
            //去空格大写
            patientcode = patientcode.trim().toUpperCase();
            Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
            //通过档案号查询
            if (patient != null && StringUtils.isNotBlank(patient.getPatientarchiveno())) {
                if (iSysConfigService.oldSystemOpen()) {
                    List<ArchiveDataVo> list1 = divisionMapper.getArchiveDataMysql(patient.getPatientarchiveno());
                    //查询老系统oracle数据,可能会有多个档案号
                    List<String> id = peispatientarchiveMapper.getOldInfoByIdCardNo(patient.getIdcardno());
                    //通过档案查询oracle数据
                    if (ObjectUtils.isNotEmpty(id)) {
                        List<ArchiveDataVo> list2 = divisionMapper.getArchiveDataOracle(id);
                        list1.addAll(list2);
                    }
                    //排序
                    Comparator<ArchiveDataVo> comparator = new Comparator<ArchiveDataVo>() {
                        @Override
                        public int compare(ArchiveDataVo obj1, ArchiveDataVo obj2) {
                            // 处理空值情况
                            if (obj1.getMedicaldate() == null && obj2.getMedicaldate() == null) {
                                return 0;
                            } else if (obj1.getMedicaldate() == null) {
                                return 1;
                            } else if (obj2.getMedicaldate() == null) {
                                return -1;
                            }
                            // 默认比较规则
                            return obj2.getMedicaldate().compareTo(obj1.getMedicaldate());
                        }
                    };
                    Collections.sort(list1, comparator);
                    //设置分页返回数据
                    ipage = ToolUtil.getPages((int) page.getCurrent(), (int) page.getSize(), list1);
                } else {
                    ipage = divisionMapper.getArchiveData(page, patient.getPatientarchiveno());
                }

            }
        }
        return ipage;
    }


    /**
     * 查看档案右侧数据,科室医师小结等
     *
     * @param page
     * @param patientcode
     * @return
     */
    @Override
    public IPage<ResultDataVo> getResultData(PageParam<ResultDataVo> page, String patientcode) {
        IPage<ResultDataVo> ipage = new Page<>();
        if (StringUtils.isNotEmpty(patientcode)) {
            //去空格大写
            patientcode = patientcode.trim().toUpperCase();
            String str = iSysBranchService.getBranchFlag(null) + "3";
            log.info("体检系统前缀:{}", str);
            if (patientcode.startsWith(str)) {
                //新系统体检号
                ipage = divisionMapper.getResultData(page, patientcode);
            } else {
                //老系统体检号
                List<ResultDataVo> list = divisionMapper.getResultDataOracle(patientcode);
                //设置分页返回数据
                ipage = ToolUtil.getPages((int) page.getCurrent(), (int) page.getSize(), list);
            }

        }
        return ipage;
    }


    /**
     * 我要提醒-获取科室
     *
     * @param patientcode
     * @return
     */
    @Override
    public List<RemindKsVo> getRemindKs(String patientcode) {
        SysUser sysUser = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
        List<RemindKsVo> list = new ArrayList<>();
        //有体检码没体检码分开查询
        if (StringUtils.isBlank(patientcode)) {
            list = divisionMapper.getRemindKs(sysUser.getUserNo(), sysUser.getCid());
        } else {
            list = divisionMapper.getRemindKsByCode(sysUser.getUserNo(), sysUser.getCid(), patientcode);
        }
        return list;
    }


    /**
     * 我要提醒-保存科室提醒
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveRemind(SaveRemindParam param) {

        String id = param.getId();
        String remindId = null;
        String depIds = param.getDepIds();
        String content = param.getContent();
        String patientcode = null;
        if (StringUtils.isEmpty(id)) {
            //id为空插入
            String depId = param.getDepId();
            patientcode = param.getPatientcode();
            SysDept dept = sysDeptMapper.getByDeptNo(depId);
            SysUser user = SecurityUtils.getLoginUser().getUser();

            Date remindTime = new Date();
            //科室提醒主表
            SectionRemind sr = new SectionRemind(patientcode
                    , content
                    , depId
                    , dept.getDeptName()
                    , user.getUserNo()
                    , user.getUserName()
                    , remindTime
                    , depIds);
            int i = sectionRemindMapper.insert(sr);
            remindId = sr.getId();
            if (StringUtils.isEmpty(remindId)) {
                throw new ServiceException("保存失败");
            }
        } else {
            //有id修改
            SectionRemind sr = sectionRemindMapper.getInfoById(id);
            if (ObjectUtils.isEmpty(sr)) {
                throw new ServiceException("提醒已被删除，请刷新重试");
            }
            remindId = id;
            patientcode = sr.getPatientcode();
            sr.setRemindContent(content);
            sr.setDepIds(depIds);
            sectionRemindMapper.updateById(sr);
            //删除中间表
            sectionAndRemindMapper.delete(new QueryWrapper<SectionAndRemind>().eq("remind_id", remindId));
        }
        for (String remindDepId : depIds.split(",")) {
            // 科室提醒和科室关联表
            SectionAndRemind sar = new SectionAndRemind(patientcode, remindDepId, remindId);
            sectionAndRemindMapper.insert(sar);
        }
        return Boolean.TRUE;
    }

    /**
     * 查询并拼接提醒内容
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @Override
    public String getContent(String patientcode, String ksID) {
        QueryWrapper<SectionAndRemind> con = new QueryWrapper<>();
        con.eq("patientcode", patientcode);
        if (StringUtils.isNotEmpty(ksID)) {
            con.eq("dep_id", ksID);
        }
        //科室提醒和科室关联表
        List<SectionAndRemind> sars = sectionAndRemindMapper.selectList(con.orderByDesc("createdate"));
        if (sars.size() > 0) {
            StringBuilder builder = new StringBuilder();
            Set<String> mainIds = new HashSet<String>();
            for (SectionAndRemind sar : sars) {
                if (mainIds.contains(sar.getRemindId())) {
                    continue;
                } else {
                    mainIds.add(sar.getRemindId());
                }
                //科室提醒主表
                SectionRemind sr = sectionRemindMapper.getInfoById(sar.getRemindId());
                //拼接
                if (ObjectUtils.isNotEmpty(sr)) {
                    builder.append(sr.getDepName() + ":");
                    builder.append("\n\t");
                    builder.append(sr.getRemindContent().replaceAll("\n", "\n\t"));
                    builder.append("\n");
                }
            }
            return builder.toString();
        }
        return "";
    }


    /**
     * 科室详情（无图像检查）
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @Override
    public Map case1(String patientcode, String ksID, String gridSeclect, String autoFill) {
        Map<String, Object> data = new HashMap<>();

        //检查人姓名，检查人id
        String jcrxm = SecurityUtils.getUsername();
        String jcr = SecurityUtils.getUserNo();
        String create_url = FileTypePath.CREATE_IP;
        String lrr = SecurityUtils.getUsername();
        String lrrId = SecurityUtils.getUserNo();
        Date lrsj = new Date();

        data.put("jcrxm", jcrxm);
        data.put("jcr", jcr);
        data.put("create_url", create_url);
        data.put("lrr", lrr);
        data.put("lrrId", lrrId);
        data.put("lrsj", lrsj);


        //查询参数配置表
        SysConfig sysConfig = new SysConfig();
        sysConfig.setConfigName("科室下结论");
        sysConfig.setConfigType("y");
        SysConfig resource = sysConfigMapper.selectConfig(sysConfig);
        String showConclusion = "";
        if (resource != null) {
            showConclusion = "1";
        } else {
            showConclusion = "0";
        }
        data.put("showConclusion", showConclusion);

        //东华原CD210中医体质辨识
        String cd210 = ("197".equals(ksID) && "1".equals(Constants.CD210)) ? "1" : "0";
        //东华原DAS1000动脉硬化
        String das1000 = ("17".equals(ksID) && "1".equals(Constants.Das1000)) ? "1" : "0";
        data.put("cd210", cd210);
        data.put("das1000", das1000);

        String nk = "6";
        String zyxwz = "124";
        String sjnk = "125";
        String wk = "139";
        String flag = "";
        if (nk.equals(ksID)) {
            SysDept zyxwz_dep = sysDeptMapper.getByDeptNo(zyxwz);
            if (zyxwz_dep != null) {
                String zyxwz_title = zyxwz_dep.getDeptName();
                data.put("zyxwz_title", zyxwz_title);
            }
            SysDept sjnk_dep = sysDeptMapper.getByDeptNo(sjnk);
            if (sjnk_dep != null) {
                String sjnk_title = sjnk_dep.getDeptName();
                data.put("sjnk_title", sjnk_title);
            }
            String showNkQuestion = "1";
            data.put("showNkQuestion", showNkQuestion);
        } else if (sjnk.equals(ksID)) {
            SysDept zyxwz_dep = sysDeptMapper.getByDeptNo(zyxwz);
            if (zyxwz_dep != null) {
                String zyxwz_title = zyxwz_dep.getDeptName();
                data.put("zyxwz_title", zyxwz_title);
            }
            SysDept nk_dep = sysDeptMapper.getByDeptNo(nk);
            if (nk_dep != null) {
                String nk_title = nk_dep.getDeptName();
                data.put("nk_title", nk_title);
            }
        } else if (wk.equals(ksID)) {
            String showWkQuestion = "1";
            data.put("showWkQuestion", showWkQuestion);
        }


        SysDept depart = sysDeptMapper.getByDeptNo(ksID);
        String ksName = depart.getDeptName();
        //数据报告格式
        String sjbggs = depart.getSjbggs().toString();
        //接口类型
        String jklx = depart.getJklx();
        data.put("ksName", ksName);
        data.put("sjbggs", sjbggs);
        data.put("jklx", jklx);

        if (StringUtils.isNotBlank(patientcode)) {
            //自动补全
            if (gridSeclect == null || "false".equals(gridSeclect)) {
                if (autoFill != null && "true".equals(autoFill)) {
                    patientcode = ToolUtil.patientCode(patientcode, iSysBranchService.getBranchFlag(null));
                } else {
                    patientcode = patientcode.trim().toUpperCase();
                }
            }
            //体检者表
            Peispatient user = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                    .eq("patientcode", patientcode).eq("f_registered", 1));
            if (user == null) {
                throw new ServiceException("error@该体检号尚未登记!");
            }
            data.put("peispatient", user);

            String picture = peispatientPhotoService.getPicture(user);
            String archive = sectionResultMainMapper.getArchiveData(patientcode, ksID);
            data.put("picture", picture);
            data.put("archive", archive);

            if (user.getFPaused() != null && user.getFPaused().intValue() == 1) {
                throw new ServiceException("error@该体检号已被禁检!");
            }
            String idOrgreservationgroup = user.getIdOrgreservationgroup();
            if (idOrgreservationgroup != null) {
                //体检者任务分组
                Peisorgreservationgroup org = peisorgreservationgroupMapper.getInfoById(idOrgreservationgroup);
                //组禁用
                if (org != null && org.getFGrouppaused() != null && org.getFGrouppaused().intValue() == 1) {
                    throw new ServiceException("error@该体检号已被禁检!");
                }
            }
            String isVIP = patienttypeService.getIdPatientClass(user);
            data.put("isVIP", isVIP);

            List<Peispatientfeeitem> list1 = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientcode)
                    .eq("id_ks", ksID)
                    .eq("f_giveup", 0)
                    .eq("change_item", 0).isNull("f_transferedhl7")
                    .eq("sfjj", 0));
            if (list1 != null && list1.size() > 0) {
                boolean charged = false;// 判断是否有已缴费的费用项目
                boolean paid = true;// 是否全部缴费
                StringBuilder unpaid = new StringBuilder();
                List<Peispatientfeeitem> tjzsfxm = new ArrayList<Peispatientfeeitem>();
                for (Peispatientfeeitem feeitem : list1) {
                    if (feeitem.getFFeecharged() != null && feeitem.getFFeecharged() == 1) {
                        charged = true;
                        tjzsfxm.add(feeitem);
                    } else {
                        paid = false;
                        if (unpaid.length() == 0) {
                            unpaid.append("该体检号存在未缴费的费用项目：" + feeitem.getExamfeeitemName());
                        } else {
                            unpaid.append("," + feeitem.getExamfeeitemName());
                        }
                    }
                }

                if (charged) {
                    if (!paid) {
                        flag = unpaid.toString();
                    }
                    //科室检查结果主表
                    SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                            .eq("patientcode", patientcode).eq("dep_id", ksID));
                    if (main != null && main.getIsAudit() != null && main.getIsAudit() == 1) {
                        flag = "audit";// 已审核 不能修改
                    }
                    //把检查结果放进去
                    if (main != null) {
                        SysUser sysUser = sysUserMapper.selectUserByUserNo(main.getWriteId());
                        main.setWriteName(ObjectUtils.isNotEmpty(sysUser) ? sysUser.getUserName() : "");
                        data.put("sectionResultMain", main);
                    }
                    String tjlx = user.getIdExamtype();// 体检类型
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("ksId", ksID);// 科室Id
                    map.put("tjh", patientcode);// 体检号
                    map.put("tjlx", tjlx);// 体检类型
                    //获取内科页面收费项目检查项目数据
                    List nkdata = getNkdata(map, tjzsfxm);
                    List griddata = jlcData(patientcode, ksID);
                    data.put("nkdata", nkdata);
                    data.put("griddata", griddata);
                } else {
                    flag = "该体检号尚未缴费!";
                }
            } else {
                flag = "该体检号没有本科室收费项目!";
            }
        }
        data.put("flag", flag);
        return data;
    }


    //获取内科页面收费项目检查项目数据
    private List getNkdata(HashMap<String, String> parammap, List<Peispatientfeeitem> tjzsfxm) {
        String ksId = parammap.get("ksId");
        String tjh = parammap.get("tjh");// 体检号
        String tjlx = parammap.get("tjlx");// 体检类型
        List<HashMap<String, List<HashMap<String, String>>>> data = new ArrayList<HashMap<String, List<HashMap<String, String>>>>();
        if (StringUtils.isNotBlank(ksId) && StringUtils.isNotBlank(tjh)) {// 体检号和科室号都不为空时才有数据
            List<GetC13dataDto> list = divisionMapper.getNkdata(tjlx, tjh, ksId);
            //添加序号，将123456789转换为147258369
            list = transformList(list);
            return list;
        }
        return null;
    }


    public static List<GetC13dataDto> transformList(List<GetC13dataDto> list) {
        List<GetC13dataDto> transformedLst = new ArrayList<>(list.size());
        LinkedHashMap<String, ArrayList<GetC13dataDto>> map = new LinkedHashMap<>();
        int j = 1;
        for (GetC13dataDto item : list) {
            item.setRownum(j++);
            //检查项目名称一致的视为一个分组
            String jcxmmc = item.getJcxmmc();
            if (!map.containsKey(jcxmmc)) {
                map.put(jcxmmc, new ArrayList<>());
            }
            map.get(jcxmmc).add(item);
        }

        //循环map，将123456789转换为147258369
        for (Map.Entry<String, ArrayList<GetC13dataDto>> entry : map.entrySet()) {
            List<GetC13dataDto> value = entry.getValue();
            //用三个数组来处理
            List<GetC13dataDto> lst1 = new ArrayList<>();
            List<GetC13dataDto> lst2 = new ArrayList<>();
            List<GetC13dataDto> lst3 = new ArrayList<>();
            for (int i = 0; i < value.size(); i++) {
                double ceil = Math.ceil(value.size() / 3.0);
                if (i < Math.ceil(value.size() / 3.0)) {
                    lst1.add(value.get(i));
                } else if (i >= Math.ceil(value.size() / 3.0) && i < Math.ceil(value.size() / 3.0 * 2)) {
                    lst2.add(value.get(i));
                } else {
                    lst3.add(value.get(i));
                }
            }

            //重新排列返回给前端正确的顺序
            List<GetC13dataDto> newList = new ArrayList<>();
            int size = Math.max(Math.max(lst1.size(), lst2.size()), lst3.size());
            for (int i = 0; i < size; i++) {
                if (i < lst1.size()) {
                    newList.add(lst1.get(i));
                }
                if (i < lst2.size()) {
                    newList.add(lst2.get(i));
                }
                if (i < lst3.size()) {
                    newList.add(lst3.get(i));
                }
            }
            //放到总数据里面
            transformedLst.addAll(newList);
        }


        return transformedLst;
    }


    /**
     * 科室加项左侧数据，创建套餐获取基础数据收费项目
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<SfxmVo> getSfxm(PageParam<SfxmVo> page, SfxmParam param) {
        //去空格
        if (ObjectUtils.isNotEmpty(param.getExamfeeitemName())) {
            param.setExamfeeitemName(param.getExamfeeitemName().trim());
        }
        if (ObjectUtils.isNotEmpty(param.getSfxmsrm())) {
            param.setSfxmsrm(param.getSfxmsrm().trim().toUpperCase());
        }
        if (ObjectUtils.isNotEmpty(param.getSyxb())) {
            param.setSyxb(param.getSyxb().trim());
        }
        if (ObjectUtils.isNotEmpty(param.getTjlx())) {
            param.setTjlx(param.getTjlx().trim());
        }
        return divisionMapper.getSfxm(page, param);
    }


    /**
     * 科室加项保存数据
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean divAddSaOrUp(DivAddParam param) {
        List<DivAddGriddata> ja = param.getGriddata();
        String patientcode = param.getPatientcode();
        //体检者表
        Peispatient pei = peispatientMapper.getByPatientCode(patientcode);
        if (((null != pei.getFFinallocked() && pei.getFFinallocked() == 1)
                || (null != pei.getJktjzt() && pei.getJktjzt() > 0))
                || ((null != pei.getIdGuidenurse() && pei.getIdGuidenurse() == 1)
                || (null != pei.getZytjzt() && pei.getZytjzt() > 0))) {
            throw new ServiceException("保存失败：该体检者总检已经锁定或者开始，不能加项！");
        }
        //体检者收费项目表
        List<Peispatientfeeitem> pis = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientcode).eq("change_item", 0));
        Set<String> itemIds = new HashSet<String>();
        for (Peispatientfeeitem pi : pis) {
            itemIds.add(pi.getIdExamfeeitem());
        }
        for (int i = 0; i < ja.size(); i++) {
            //科室临时加项表
            DivAddGriddata jo = ja.get(i);
            TempFeeitem pojo = mapperFacade.map(jo, TempFeeitem.class);
            //体检者收费项目表
            Peispatientfeeitem old = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                    .eq("id_patient", patientcode).eq("id_examfeeitem", pojo.getItemId()).eq("change_item", 0));
            if (ObjectUtils.isNotEmpty(old)) {
                throw new ServiceException("体检者已拥有收费项目【" + old.getExamfeeitemName() + "】"
                        + ",不能再添加此收费项目,请点击【刷新】查看。");
            }
            //删除
            if ("removed".equals(jo.getState())) {
                TempFeeitem tfi = tempFeeitemMapper.getInfoById(pojo.getId());
                if (tfi.getFeeitemId() != null) {
                    throw new ServiceException("收费项目【"
                            + jo.getExamfeeitemName() + "】已完成加项，"
                            + "不能删除，请点击【刷新】查看。");
                }
                //设为删除状态
                tfi.setIsDelete(1);
                tempFeeitemMapper.updateById(tfi);
            } else if ("modified".equals(jo.getState())) {
                //更新
                TempFeeitem tfi = tempFeeitemMapper.getInfoById(pojo.getId());
                if (tfi.getIsDelete() == 1) {
                    throw new ServiceException("保存失败，收费项目【"
                            + jo.getExamfeeitemName() + "】已被删除，请点击【刷新】查看。");
                }
                if (itemIds.contains(pojo.getItemId())) {
                    throw new ServiceException("保存失败，收费项目【"
                            + jo.getExamfeeitemName() + "】已存在，请点击【刷新】查看。");
                }
                itemIds.add(pojo.getItemId());
                //更新
                tempFeeitemMapper.updateById(pojo);
            } else {
                //添加
                if (itemIds.contains(pojo.getItemId())) {
                    throw new ServiceException("保存失败，收费项目【"
                            + jo.getExamfeeitemName() + "】已存在，请点击【刷新】查看。");
                }
                itemIds.add(pojo.getItemId());
                pojo.setIsDelete(0);
                pojo.setPatientcode(patientcode);
                pojo.setId(null);
                tempFeeitemMapper.insert(pojo);
            }
        }
        //获取grid收费项目列表中是否存在相同的检查项目
//        String compareResult = signInInspectionService.compareItemsToExam(itemIds.toArray(new String[itemIds.size()]));
//        if(StringUtils.isNotEmpty(compareResult.trim())) {
//            throw new ServiceException(compareResult);
//        }
        return Boolean.TRUE;
    }

    /**
     * 职业性问诊体检者列表数据
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<PatientDataVo> getPatientData(PageParam<PatientDataVo> page, PatientDataParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return divisionMapper.getPatientData(page, param);
    }


    /**
     * 职业性问诊-职业病史列表数据
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<RemindPatientVo> getRemindPatient(PageParam<RemindPatientVo> page, RemindPatientParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        //取出属性
        IPage<RemindPatientVo> iPage = divisionMapper.getRemindPatient(page, param);
        List<RemindPatientVo> list = iPage.getRecords();
        for (RemindPatientVo vo : list) {
            //提醒内容
            vo.setContent(getContent(vo.getPatientcode(), param.getKsID()));
        }
        //放回ipage
        iPage.setRecords(list);
        return iPage;
    }


    /**
     * 职业性问诊-审核
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean commonjobinquirySave(ComSaveParam param) {
        //QT体检者表
        Peispatient newPeispatient = mapperFacade.map(param.getFormdata(), Peispatient.class);
        Peispatient oldPeispatient = peispatientMapper.getInfoById(newPeispatient.getId());
        //职业问诊
        PeispatientConsultation pc = peispatientConsultationService.getOne(new QueryWrapper<PeispatientConsultation>()
                .eq("patientcode", oldPeispatient.getPatientcode()));
        if (ObjectUtils.isEmpty(oldPeispatient)) {
            throw new ServiceException("审核失败，该体检号尚未登记！");
        }
        if (pc != null && pc.getIsAudit() != null
                && pc.getIsAudit() == 1) {
            throw new ServiceException("审核失败，该体检号已审核,请勿重复审核！");
        }
        if (oldPeispatient.getFRegistered() != 1) {
            throw new ServiceException("审核失败，该体检号尚未登记！");
        }
        if ((oldPeispatient.getZytjzt() != null && oldPeispatient.getZytjzt() == 1)) {
            throw new ServiceException("审核失败，本体检者检查结果已被"
                    + (oldPeispatient.getPatientnameencoded() == null ? ""
                    : oldPeispatient.getPatientnameencoded())
                    + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (oldPeispatient.getIdGuidenurse() != null
                && oldPeispatient.getIdGuidenurse() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被"
                    + (oldPeispatient.getParsedassignedsuiteandfi() == null ? ""
                    : oldPeispatient.getParsedassignedsuiteandfi())
                    + "锁定，不能修改！如有未检项目也不再进行。");
        }

        //职业问诊签名图片
        PeispatientConsultationPic pcp = peispatientConsultationPicService.getOne(new QueryWrapper<PeispatientConsultationPic>()
                .eq("patientcode", oldPeispatient.getPatientcode()));
        if (ObjectUtils.isNotEmpty(pcp)) {
            //修改
            pcp.setSignPicture(param.getUrl());
            peispatientConsultationPicService.updateById(pcp);
        } else {
            //保存
            PeispatientConsultationPic pic = new PeispatientConsultationPic(oldPeispatient.getPatientcode(), param.getUrl(), new Date());
            peispatientConsultationPicService.save(pic);
        }

        String patientCode = oldPeispatient.getPatientcode();
        //短号体检号
        Integer shortCode = oldPeispatient.getShortCode();
        //体检者收费项目表
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientCode)
                .eq("id_ks", param.getKsID())
                .isNull("f_transferedhl7")
                .eq("f_giveup", 0)
                .eq("sfjj", 0)
                .eq("change_item", 0));
        if (feeitems.size() == 0) {
            throw new ServiceException("审核失败，该体检号没有本科室收费项目！");
        }
        boolean charge_flag = false;
        for (Peispatientfeeitem feeitem : feeitems) {
            if (feeitem.getFFeecharged() != null
                    && feeitem.getFFeecharged() == 1) {
                //已收费
                charge_flag = true;
                break;
            }
        }
        if (!charge_flag) {
            throw new ServiceException("审核失败，该体检号尚未缴费！");
        }
        //档案ID
        String patientarchiveno = oldPeispatient.getPatientarchiveno();// 档案号
        String userId = SecurityUtils.getUserNo();// 当前用户ID

        // 修改体检者表
        if (pc == null) {
            //职业问诊
            pc = new PeispatientConsultation();
            pc.setPatientcode(oldPeispatient.getPatientcode());
            peispatientConsultationService.save(pc);
        }
        //赋值到职业问诊
        ComFormDateDto formdata = param.getFormdata();
        PeispatientConsultation newPc = mapperFacade.map(formdata, PeispatientConsultation.class);
        pc.setEverOfDisease(newPc.getEverOfDisease());
        pc.setEverOfDiseaseRemark(newPc
                .getEverOfDiseaseRemark());
        pc.setCcnl(newPc.getCcnl());
        pc.setJq(newPc.getJq());
        pc.setZq(newPc.getZq());
        pc.setTjnl(newPc.getTjnl());
        pc.setFamilyNumber(newPc.getFamilyNumber());
        pc.setLc(newPc.getLc());
        pc.setZc(newPc.getZc());
        pc.setSc(newPc.getSc());
        pc.setYwrc(newPc.getYwrc());
        pc.setJt(newPc.getJt());
        pc.setAbstainSmokeNote(newPc.getAbstainSmokeNote());
        pc.setEverydaySmokeN(newPc.getEverydaySmokeN());
        pc.setSmokeYear(newPc.getSmokeYear());
        pc.setNoKissTheCup(newPc.getNoKissTheCup());
        pc.setBetweenKissTheCup(newPc
                .getBetweenKissTheCup());
        pc.setEvermoreKiss(newPc.getEvermoreKiss());
        pc.setAbstainLostKiss(newPc.getAbstainLostKiss());
        pc.setKissYearN(newPc.getKissYearN());
        pc.setKissAmount(newPc.getKissAmount());
        pc.setKissType(newPc.getKissType());
        pc.setFamilyOfDisease(newPc.getFamilyOfDisease() == null ? null : newPc.getFamilyOfDisease().trim());//家族病史去空格
        //症状可能有重复的,去重一下
        String[] symptoms = newPc.getSymptom().split(",");
        Set<String> uniqueSymptoms = new LinkedHashSet<>();
        for (String symptom : symptoms) {
            uniqueSymptoms.add(symptom);
        }
        pc.setSymptom(String.join(",", uniqueSymptoms));
        pc.setIsAudit(1);
        pc.setOther(newPc.getOther() == null ? null : newPc.getOther().trim());//其他去空格
        oldPeispatient.setFExamstarted(1);
        oldPeispatient.setZgl(newPeispatient.getZgl());
        oldPeispatient.setJhgl(newPeispatient.getJhgl());
        //更新
        peispatientMapper.updateById(oldPeispatient);
        peispatientConsultationService.updateById(pc);

        //有几个值可能为空，需要单独设置
        LambdaUpdateWrapper<PeispatientConsultation> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(PeispatientConsultation::getId, pc.getId())
                .set(PeispatientConsultation::getCcnl,formdata.getCcnl())
                .set(PeispatientConsultation::getJq,formdata.getJq())
                .set(PeispatientConsultation::getZq,formdata.getZq())
                .set(PeispatientConsultation::getTjnl,formdata.getTjnl())
                .set(PeispatientConsultation::getFamilyNumber,formdata.getFamilyNumber())
                .set(PeispatientConsultation::getLc,formdata.getLc())
                .set(PeispatientConsultation::getZc,formdata.getZc())
                .set(PeispatientConsultation::getSc,formdata.getSc())
                .set(PeispatientConsultation::getYwrc,formdata.getYwrc())
                .set(PeispatientConsultation::getJt,formdata.getJt());
        peispatientConsultationService.update(null, lambdaUpdateWrapper);

        //插入检查结果主表(仅用于统计)
        PersonDataDto person_map = param.getPersonData();
        String rummagerId = person_map.getJcr();
        Date rummagerTime = person_map.getJcsj();
        //科室检查结果主表
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", oldPeispatient.getPatientcode()).eq("dep_id", param.getKsID()));
        Date examinatetime = rummagerTime;
        if (ObjectUtils.isEmpty(main)) {
            main = new SectionResultMain();
            //已审核
            main.setIsAudit(1);
            //检查人
            main.setRummagerId(rummagerId);
            main.setRummagerTime(examinatetime);
            main.setRummagerName(person_map.getJcrxm());
            //录入人
            main.setWriteId(person_map.getLrrId());
            main.setWriteTime(person_map.getLrsj());
            main.setWriteName(person_map.getLrr());
            //审核人
            main.setAuditId(SecurityUtils.getUserNo());
            main.setAuditName(SecurityUtils.getUsername());
            main.setAuditTime(person_map.getLrsj());

            main.setPatientcode(patientCode);
            main.setShortCode(shortCode);
            main.setDepId(param.getKsID());
            main.setIsFinish(0);

            sectionResultMainMapper.insert(main);
        } else {
            main.setIsAudit(1);
            main.setIsFinish(0);
            //检查人
            main.setRummagerId(rummagerId);
            main.setRummagerTime(examinatetime);
            main.setRummagerName(person_map.getJcrxm());
            //录入人
            main.setWriteId(person_map.getLrrId());
            main.setWriteTime(person_map.getLrsj());
            main.setWriteName(person_map.getLrr());
            //审核人
            main.setAuditId(SecurityUtils.getUserNo());
            main.setAuditName(SecurityUtils.getUsername());
            main.setAuditTime(person_map.getLrsj());
            sectionResultMainMapper.updateById(main);
        }


        // 修改职业史表
        List<ComGridDataDto> zy_gridValue = param.getGriddata();

        for (ComGridDataDto map : zy_gridValue) {
            //添加
            if ("added".equals(map.getState())) {
                //职业史
                WzZys wz = mapperFacade.map(map, WzZys.class);
                wz.setIdPatientarchive(patientarchiveno);
                wz.setCreateId(userId);
                wz.setDjls(oldPeispatient.getPatientcode());
                wzZysMapper.insert(wz);
            }
            //修改
            if ("modified".equals(map.getState())) {
                WzZys newWz = mapperFacade.map(map, WzZys.class);
                newWz.setModifyId(userId);
                wzZysMapper.updateById(newWz);
            }
            //删除
            if ("removed".equals(map.getState())) {
                wzZysMapper.deleteById(map.getId());
            }
        }

        // 修改职业病史表
        List<ComDataDto> zyb_gridValue = param.getData();
        for (ComDataDto map : zyb_gridValue) {
            //添加
            if ("added".equals(map.getState())) {
                //职业病史
                WzZybs wzb = mapperFacade.map(map, WzZybs.class);
                wzb.setIdPatientarchive(patientarchiveno);
                wzb.setCreateId(userId);
                wzZybsMapper.insert(wzb);
            }
            //修改
            if ("modified".equals(map.getState())) {
                WzZybs newWzb = mapperFacade.map(map, WzZybs.class);
                newWzb.setModifyId(userId);
                wzZybsMapper.updateById(newWzb);
            }
            //删除
            if ("removed".equals(map.getState())) {
                wzZybsMapper.deleteById(map.getId());
            }
        }

        // 体检者收费项目
        String doctorId = SecurityUtils.getUserNo();
        String doctorName = SecurityUtils.getUsername();
        Date examTime = new Date();
        //更新
        for (Peispatientfeeitem feeitem : feeitems) {
            feeitem.setIdPatientexamdepart(param.getKsID());
            feeitem.setIdExamdoctor(doctorId);
            feeitem.setExamdoctorNameR(doctorName);
            feeitem.setExaminatetime(examTime);
            feeitem.setFExaminated(1);
            feeitem.setExaminatetime(examinatetime);
            peispatientfeeitemMapper.updateById(feeitem);
        }

        // 体检者表
        if (outsideMainService.getAllSfxmtzjStatus(oldPeispatient.getPatientcode())) {
            //0:已备单 1:分检完成
            oldPeispatient.setFReadytofinal(1);
            oldPeispatient.setReadytofinalDate(new Date());
            peisStateService.setScbs(oldPeispatient.getPatientcode(), 0);

            List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(oldPeispatient.getPatientcode());
            for (Peispatientfeeitem other : other_items) {
                other.setFExaminated(1);//设置未关联科室项目为已检
            }
            peispatientfeeitemService.updateBatchById(other_items);
        }
        peispatientMapper.updateById(oldPeispatient);
        return Boolean.TRUE;
    }


    /**
     * 职业性问诊-反审核
     *
     * @param id
     * @param ksID
     * @return
     */
    @Override
    public Boolean commonjobinquiryReverse(String id, String ksID) {
        //体检者表
        Peispatient peispatient = peispatientMapper.getInfoById(id);

        if (ObjectUtils.isEmpty(peispatient)) {
            throw new ServiceException("反审核失败，该体检号尚未登记！");
        }
        //职业问诊
        PeispatientConsultation pc = peispatientConsultationService.getOne(new QueryWrapper<PeispatientConsultation>()
                .eq("patientcode", peispatient.getPatientcode()));
        //职业性问诊-是否已审核 0未审核，1已审核，2反审核
        if (pc == null || pc.getIsAudit() == null || pc.getIsAudit() != 1) {
            throw new ServiceException("反审核失败，该体检号尚未审核！");
        }
        //职业体检状态：0.总检开始 1.总检完成 2.报告已打印 3.报告一审通过 4.报告一审不通过 5.二审通过 " +
        //            "6.二审不通过 7.终审通过 8.终审不通过 9.报告已交接 10.报告已通知 11.报告已领取
        if (peispatient.getZytjzt() != null && peispatient.getZytjzt() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被"
                    + (peispatient.getPatientnameencoded() == null ? ""
                    : peispatient.getPatientnameencoded())
                    + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        //职业锁定完成
        if (peispatient.getIdGuidenurse() != null
                && peispatient.getIdGuidenurse() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被"
                    + (peispatient.getParsedassignedsuiteandfi() == null ? ""
                    : peispatient.getParsedassignedsuiteandfi())
                    + "锁定，不能修改！如有未检项目也不再进行。");
        }
        // 设置为反审核
        pc.setIsAudit(2);
        peispatient.setFReadytofinal(0);
        //更新
        peispatientMapper.updateById(peispatient);
        peispatientConsultationService.updateById(pc);
        peisStateService.setScbs(peispatient.getPatientcode(), 0);

        //体检者收费项目表
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", peispatient.getPatientcode())
                .eq("id_ks", ksID)
                .eq("f_giveup", 0)
                .eq("sfjj", 0)
                .eq("change_item", 0));
        for (Peispatientfeeitem feeitem : feeitems) {
            //0：未检；1：已检
            feeitem.setFExaminated(0);
            peispatientfeeitemService.updateById(feeitem);
        }

        return Boolean.TRUE;
    }


    /**
     * 当前分中心所有医生
     *
     * @param cId
     * @param ksID
     * @param key
     * @return
     */
    @Override
    public List allDoctors(String cId, String ksID, String key) {
        //去空格大写
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim().toUpperCase();
        }
        //当前分中心当前科室id的所有医生
        List<AllUserDataVo> list = sysUserMapper.allDoctors(cId, ksID, key);
        return list;
    }


    /**
     * 总检结论词搜索
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<Basconclusion> getConclusion(PageParam<Basconclusion> page, String key) {
        //条件构造器
        QueryWrapper<Basconclusion> con = new QueryWrapper<>();
        con.eq("is_delete", 0);
        if (StringUtils.isNotBlank(key)) {
            key = key.trim().toUpperCase();
            con.like("input_code", key);
        }
        //分页查询
        IPage<Basconclusion> iPage = basconclusionMapper.selectPage(page, con);
        return iPage;
    }


    /**
     * 结论词保存(图像检查)
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateJlc(saOrUpJlcParam param) {
        List<saOrUpJlcDto> list = param.getGriddata();
        //体检码去空格大写
        String patientcode = param.getPatientcode().trim().toUpperCase();
        //科室检查结果主表
        SectionResultMain stm = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientcode).eq("dep_id", param.getKsID()));
        //没有就新建一个插入
        if (ObjectUtils.isEmpty(stm)) {
            stm = new SectionResultMain();
            stm.setDepId(param.getKsID());//科室ID
            stm.setPatientcode(patientcode);//体检号
            stm.setCreatedate(new Date());
            stm.setModifydate(new Date());
            sectionResultMainMapper.insert(stm);
        }
        //主表id
        String mainId = stm.getId();
        //删除结论词、小结
        sectionResultTwoMapper.delete(new QueryWrapper<SectionResultTwo>()
                .eq("patientcode", patientcode)
                .eq("division_id", param.getKsID()));
        if (list != null && list.size() > 0) {
            for (saOrUpJlcDto c : list) {
                //结论词Id
                String jlc = c.getJlcId();
                if (StringUtils.isNotBlank(jlc)) {
                    //删除跳过
                    if (c.getState() != null && c.getState().equals("removed")) {
                        continue;
                    }
                    SectionResultTwo srt = new SectionResultTwo();
                    srt.setMainId(mainId);
                    //结论词ID
                    srt.setBasconclusionId(jlc);
                    //科室ID
                    srt.setDivisionId(param.getKsID());
                    //体征词Id
                    if (StringUtils.isNotBlank(c.getTzcId())) {
                        srt.setNodule(c.getTzcId());
                    } else {
                        srt.setNodule(null);
                    }
                    //检查项目id
                    if (StringUtils.isNotBlank(c.getJcxmId())) {
                        srt.setVerdictId(c.getJcxmId());
                    } else {
                        srt.setVerdictId(null);
                    }
                    //收费项目id
                    if (StringUtils.isNotBlank(c.getSfxmId())) {
                        srt.setChargesId(c.getSfxmId());
                    } else {
                        srt.setChargesId(null);
                    }

                    srt.setPatientcode(patientcode);
                    srt.setCreatedate(new Date());
                    srt.setModifydate(new Date());
                    srt.setMs(c.getMs());
                    sectionResultTwoMapper.insert(srt);
                }
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 小结(图像检查)
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean autosave(AutoSaveParam param) {
        //取出属性
        String patientcode = param.getPatientcode();
        String ksID = param.getKsID();
        String lrr = param.getLrr();
        Date lrsj = param.getLrsj();
        String jcr = param.getJcr();
        Date jcsj = param.getJcsj();
        Integer zdwjz = param.getZdwjz();
        String xjdata = param.getXjdata();
        List<JsonDataDto> jsondata = param.getJsondata();
        List<saOrUpJlcDto> griddata = param.getGriddata();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //体检号去空格大写
        patientcode = patientcode.trim().toUpperCase();
        //体检者
        Peispatient gwry = peispatientMapper.getByPatientCode(patientcode);
        if (gwry == null || gwry.getFRegistered() != 1) {
            throw new ServiceException("审核失败，该体检号尚未登记！");
        }
        if (gwry.getJktjzt() != null && gwry.getJktjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getDoctorfinalNameR() == null ? "" : gwry.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (gwry.getZytjzt() != null && gwry.getZytjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getPatientnameencoded() == null ? "" : gwry.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (gwry.getFFinallocked() != null && gwry.getFFinallocked() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getIdDoctorapply() == null ? "" : gwry.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (gwry.getIdGuidenurse() != null && gwry.getIdGuidenurse() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getParsedassignedsuiteandfi() == null ? "" : gwry.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        Integer shortCode = gwry.getShortCode();
        String flag = "cz";//SectionResultMain 已存在
        //科室结果主表
        SectionResultMain srm = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientcode)
                .eq("dep_id", ksID));
        if (ObjectUtils.isEmpty(srm)) {
            flag = "xj";//SectionResultMain 新建
            srm = new SectionResultMain();
        } else if (srm.getIsAudit() != null && srm.getIsAudit() == 1) {
            throw new ServiceException("保存失败！本体检者已被审核！");
        }
        srm.setIsFinish(0);
        //科室Id
        srm.setDepId(ksID);
        srm.setPatientcode(patientcode);
        srm.setShortCode(shortCode);

        //录入人姓名
        if (StringUtils.isNotEmpty(lrr)) {
            srm.setWriteId(lrr);
            SysUser lrrer = sysUserMapper.selectUserByUserNo(lrr);
            if (lrrer != null) {
                srm.setWriteName(lrrer.getUserName());
            }
            //录入时间
            srm.setWriteTime(lrsj);
        }

        //检查人
        if (StringUtils.isNotEmpty(jcr)) {
            srm.setRummagerId(jcr);
            SysUser rummager = sysUserMapper.selectUserByUserNo(jcr);
            if (rummager != null) {
                srm.setRummagerName(rummager.getUserName());
            }
            srm.setRummagerTime(jcsj);
        }

        //审核人,当前登录用户
        srm.setAuditName(SecurityUtils.getUsername());
        srm.setAuditId(SecurityUtils.getUserNo());
        srm.setAuditTime(jcsj);


        Integer wjz = zdwjz;
        if (wjz >= 1) {
            srm.setIsDanager(1);
            srm.setDanagerLevel(wjz);//危急值级别
        } else {
            srm.setIsDanager(0);
            srm.setDanagerLevel(0);//危急值级别
        }
        srm.setIsAudit(0);
        srm.setConclusions(xjdata);//小结
        if (flag.equals("cz")) {//已存在的结果主表
            this.updateById(srm);
        } else {//新建的结果主表
            this.save(srm);
        }

        //主表Id
        String mainId = srm.getId();

        //删除结果子表
        List<SectionResultTwo> list = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                .eq("patientcode", patientcode)
                .eq("division_id", ksID));
        if (list != null && list.size() > 0) {
            for (SectionResultTwo two : list) {
                sectionResultTwoMapper.deleteById(two);
            }
        }

        //体检类型
        String tjlx = gwry.getIdExamtype();
        //key:检查项目ID value：ComboExamItem
        Map<String, Comboexamitem> ceis = null;
        //接害因素
        String jhys = gwry.getJhys();
        //职业检查类型
        String medicaltype = gwry.getMedicaltype();
        //按接害因素、职业体检类型匹配
        if ("2".equals(tjlx)) {
            //用于判断职业小结
            List<Comboexamitem> eis = comboexamitemMapper.selectList(new QueryWrapper<Comboexamitem>()
                    .in("harm_id", jhys.split(","))
                    .eq("medical_type", medicaltype));
            ceis = new HashMap<String, Comboexamitem>();
            for (Comboexamitem cei : eis) {
                ceis.put(cei.getExamId(), cei);
            }
        }


        StringBuilder zy_builder = new StringBuilder();//职业小结
        //已进职业小结的检查项目ids（防止一个检查项目勾选多个体征词，职业小结出现重复的问题）
        HashSet<String> zyJcxmIds = new HashSet<String>();

        if (CollectionUtils.isNotEmpty(jsondata)) {//便利检查项目FORM
            Integer l = jsondata.size();
            for (int i = 0; i < l; i++) {
                //把对象转map
                JsonDataDto map = jsondata.get(i);
                //视力变色力无弃检  危急值
                String _sfqj = StringUtils.isEmpty(map.getSfqj()) ? "0" : map.getSfqj();
                //收费项目Id
                String sfxmId = map.getSfxmId();
                //未弃检
                if (_sfqj.equals("0")) {
                    String jcxmId = map.getJcxmId();
                    if (StringUtils.isNotBlank(jcxmId)) {
                        //选中
                        SectionResultTwo two = new SectionResultTwo();
                        two.setChargesId(sfxmId);
                        two.setMainId(mainId);
                        two.setVerdictId(jcxmId);//检查项目id
                        String tczId = map.getTzcId();
                        two.setNodule(map.getTzcId());//体征词Id
                        //体征词
                        BasexamltemSign sign = basexamltemSignMapper.getInfoById(tczId);
                        if (sign != null) {
                            //是否阳性
                            two.setPosistive(sign.getIsPositive());
                        }
                        two.setPatientcode(patientcode);//体检号
                        two.setShortCode(shortCode);

                        two.setIsDanger(map.getWjz());
                        two.setIntensiveLevel(sign == null ? null : sign.getIntensiveLevel());
                        two.setIsUnchecked(0);
                        //科室Id
                        two.setDivisionId(ksID);
                        two.setChargesId(sfxmId);
                        two.setInputResult(map.getInputResult());
                        String jcmc = map.getJcms();
                        //如果是综合
                        if ("2".equals(tjlx)
                                && !zyJcxmIds.contains(jcxmId)
                        ) {
                            if (ceis.get(jcxmId) != null) {
                                zy_builder.append(jcmc);
                                zyJcxmIds.add(jcxmId);
                                two.setTjlx(1);
                            } else {
                                two.setTjlx(0);
                            }
                        } else if ("0".equals(tjlx)) {
                            two.setTjlx(0);
                        } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                            two.setTjlx(1);
                        }

                        if (StringUtils.isBlank(jcmc) || "\"null\"".equals(jcmc)) {
                            jcmc = "无;";
                        }
                        two.setMs(jcmc);
                        String jlcId = map.getJlcId();
                        if (StringUtils.isNotBlank(jlcId) && !"\"null\"".equals(jlcId)) {
                            two.setBasconclusionId(jlcId);//结论词id
                        } else {
                            two.setBasconclusionId(null);//结论词id
                        }
                        sectionResultTwoMapper.insert(two);
                    }
                } else {//弃检

                }
            }
        }

        //保存手动添加的结论词
        ArrayList<String> array = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(griddata)) {

            for (saOrUpJlcDto d : griddata) {
                String jlcId = d.getJlcId();
                //如果有结论词
                if (StringUtils.isNotBlank(jlcId)) {
                    array.add(jlcId);
                    //结论词、小结
                    SectionResultTwo two = sectionResultTwoMapper.selectOne(new QueryWrapper<SectionResultTwo>()
                            .eq("patientcode", patientcode)
                            .eq("basconclusion_id", jlcId)
                            .eq("is_unchecked", 0)
                            .eq("division_id", ksID));
                    if (two != null) {
                        continue;
                    } else {
                        two = new SectionResultTwo();
                        two.setMainId(mainId);
                        //检查项目id
                        two.setVerdictId(d.getJcxmId());
                        String tzcId = d.getTzcId();
                        //体征词id
                        two.setNodule(tzcId);
                        if (StringUtils.isNotEmpty(tzcId)) {
                            //JC检查项目体证词关联表
                            BasexamltemSign sign = basexamltemSignMapper.getInfoById(tzcId);
                            if (sign != null) {
                                //是否阳性
                                two.setPosistive(sign.getIsPositive());
                            }
                        }
                        //体检号
                        two.setPatientcode(patientcode);
                        two.setShortCode(shortCode);
                        two.setIsUnchecked(0);
                        two.setBasconclusionId(jlcId);
                        //科室Id
                        two.setDivisionId(ksID);
                        //手动添加的健康
                        two.setTjlx(0);
                        sectionResultTwoMapper.insert(two);
                    }
                }
            }
        }
        //将不在griddata中的检查结果子表 结论词Id设为Null
        List<SectionResultTwo> twolist = null;
        //griddata中有结伦词的
        if (array.size() > 0) {
            twolist = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                    .eq("patientcode", patientcode)
                    .notIn("basconclusion_id", array)
                    .eq("is_unchecked", 0)
                    .eq("division_id", ksID));
        } else {
            twolist = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                    .eq("patientcode", patientcode)
                    .eq("is_unchecked", 0)
                    .eq("division_id", ksID));
        }
        if (twolist != null && twolist.size() > 0) {
            for (SectionResultTwo two : twolist) {
                two.setBasconclusionId(null);
                sectionResultTwoMapper.updateById(two);
            }
        }

        return Boolean.TRUE;
    }


    /**
     * 审核(图像检查)
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean shenhe(AutoSaveParam param) {
        log.info("打印一下审核(图像检查)：{}",param);
        //取出属性
        String ksID = param.getKsID();
        String lrr = param.getLrr();
        Date lrsj = param.getLrsj();
        String jcr = param.getJcr();
        Date jcsj = param.getJcsj();
        Integer zdwjz = param.getZdwjz();
        String xjdata = param.getXjdata();
        //妇科 其它:无; 小结里不显示
        if ("13".equals(ksID)){
            xjdata = xjdata.replace("其它:无;","");
        }
        List<JsonDataDto> jsondata = param.getJsondata();
        List<saOrUpJlcDto> griddata = param.getGriddata();
        List<ASaveDataDto> describes_map = param.getData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String patientcode = param.getPatientcode().trim().toUpperCase();
        String flag = "cz";
        //科室结果主表
        SectionResultMain srm = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientcode)
                .eq("dep_id", ksID));
        if (srm == null) {
            //SectionResultMain 新建
            flag = "xj";
            srm = new SectionResultMain();
        }
        //如果是已审核，直接返回成功
        if (ObjectUtils.isNotEmpty(srm.getIsAudit()) && srm.getIsAudit() == 1) {
            return Boolean.TRUE;
        }

        //体检者
        Peispatient gwry = peispatientMapper.getByPatientCode(patientcode);
        if (gwry == null || gwry.getFRegistered() != 1) {
            throw new ServiceException("审核失败，该体检号尚未登记！");
        }
        if (gwry.getJktjzt() != null && gwry.getJktjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getDoctorfinalNameR() == null ? "" : gwry.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (gwry.getZytjzt() != null && gwry.getZytjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getPatientnameencoded() == null ? "" : gwry.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (gwry.getFFinallocked() != null && gwry.getFFinallocked() == 1) {
            new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getIdDoctorapply() == null ? "" : gwry.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (gwry.getIdGuidenurse() != null && gwry.getIdGuidenurse() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (gwry.getParsedassignedsuiteandfi() == null ? "" : gwry.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        //@sqlOrder
        gwry.setModifydate(new Date());

        SysDept dept = sysDeptMapper.getByDeptNo(ksID);
        Integer shortCode = gwry.getShortCode();


        //处理SectionResultMain表
        srm.setIsFinish(0);
        //科室Id
        srm.setDepId(ksID);
        srm.setPatientcode(patientcode);
        srm.setShortCode(shortCode);
        //录入人
        srm.setWriteId(lrr);
        srm.setWriteTime(lrsj);
        SysUser writeer = sysUserMapper.selectUserByUserNo(lrr);
        if (writeer != null) {
            srm.setWriteName(writeer.getUserName());
        }
        //检查人
        srm.setRummagerId(jcr);
        srm.setRummagerTime(jcsj);
        SysUser rummager = sysUserMapper.selectUserByUserNo(jcr);
        if (rummager != null) {
            srm.setRummagerName(rummager.getUserName());
        }
        //审核人
        srm.setAuditName(SecurityUtils.getUsername());
        srm.setAuditId(SecurityUtils.getUserNo());
        srm.setAuditTime(new Date());
        Date rummagerTime = jcsj;

        Integer wjz = zdwjz;
        if (wjz >= 1) {
            srm.setIsDanager(1);
            srm.setDanagerLevel(wjz);//危急值级别
        } else {
            srm.setIsDanager(0);
            srm.setDanagerLevel(0);//危急值级别
        }
        srm.setIsAudit(1);
        srm.setConclusions(xjdata);//小结
        if (flag.equals("cz")) {//已存在的结果主表
            this.updateById(srm);
        } else {//新建的结果主表
            this.save(srm);
        }

        String mainId = srm.getId();//主表Id
        //删除结果子表
        List<SectionResultTwo> list = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                .eq("patientcode", patientcode)
                .eq("division_id", ksID));
        if (list != null && list.size() > 0) {
            for (SectionResultTwo two : list) {
                sectionResultTwoMapper.deleteById(two);
            }
        }

        //体检类型
        String tjlx = gwry.getIdExamtype();
        Map<String, Comboexamitem> ceis = null;//key:检查项目ID value：ComboExamItem
        String jhys = gwry.getJhys();//接害因素
        String medicaltype = gwry.getMedicaltype();//职业检查类型
        if ("2".equals(tjlx)) {//按接害因素、职业体检类型匹配
            List<Comboexamitem> eis = comboexamitemMapper.selectList(new QueryWrapper<Comboexamitem>()
                    .in("harm_id", jhys.split(","))
                    .eq("medical_type", medicaltype));
            ceis = new HashMap<String, Comboexamitem>();
            for (Comboexamitem cei : eis) {
                ceis.put(cei.getExamId(), cei);
            }
        }
        //高危项目和小结
        String gwxms = "";
        //是否高危
        Boolean sfgw = false;
        HashMap<String, String> sfxmMap = new HashMap<String, String>();//key 存放收费项目id value -->阳性小结 即体征词
        StringBuilder zy_builder = new StringBuilder();//职业小结
        HashSet<String> zyJcxmIds = new HashSet<String>();//已进职业小结的检查项目ids（防止一个检查项目勾选多个体征词，职业小结出现重复的问题）
        HashSet<String> zySfxmIds = new HashSet<String>();//视力变色力类型 如果检查项目所在职业项目有输入型，且自身是职业项目，且选中，就进小结
        boolean zy_flag = true;//职业未见异常标志
        if (CollectionUtils.isNotEmpty(jsondata)) {
            //便利检查项目FORM
            Integer l = jsondata.size();
            if ("2".equals(tjlx)) {
                for (int i = 0; i < l; i++) {
                    JsonDataDto map = jsondata.get(i);
                    if (StringUtils.isNotBlank(map.getInputResult())) {//视力变色力  输入型和与输入型同收费项目的选择型的职业项目都进职业小结
                        zySfxmIds.add(map.getSfxmId());
                    }
                }
            }
            for (int i = 0; i < l; i++) {
                JsonDataDto map = jsondata.get(i);
                String _sfqj = StringUtils.isEmpty(map.getSfqj()) ? "0" : map.getSfqj();//视力变色力无弃检  危急值
                String sfxmId = map.getSfxmId();//收费项目Id
                if (_sfqj.equals("0")) {//未弃检
                    String jcxmId = map.getJcxmId();
                    if (StringUtils.isNotBlank(jcxmId)) {
                        //选中
                        SectionResultTwo two = new SectionResultTwo();
                        two.setChargesId(sfxmId);
                        two.setMainId(mainId);
                        two.setVerdictId(jcxmId);//检查项目id
                        String tczId = map.getTzcId();
                        two.setNodule(map.getTzcId());//体征词Id
                        //体征词
                        BasexamltemSign sign = basexamltemSignMapper.getInfoById(tczId);
                        //检查项目
                        Basexamltem item = basexamltemMapper.getInfoById(jcxmId);
                        if (sign != null) {
                            //是否阳性
                            two.setPosistive(sign.getIsPositive());
                        }
                        two.setPatientcode(patientcode);//体检号
                        two.setShortCode(shortCode);
                        Integer _wjz = map.getWjz();
                        //危急值
                        if (ObjectUtils.isNotEmpty(_wjz) && _wjz >= 1) {
                            sfgw = true;
                            //检查项目名称
                            String jcxmmc = item.getExamitemName();
                            String tzcmc = sign == null ? item.getExamitemName() : sign.getName();
                            if (sfxmMap.isEmpty()) {
                                sfxmMap.put(sfxmId, tzcmc);
                            } else {
                                if (sfxmMap.containsKey(sfxmId)) {
                                    sfxmMap.put(sfxmId, sfxmMap.get(sfxmId) + ";" + tzcmc);
                                } else {
                                    sfxmMap.put(sfxmId, tzcmc);
                                }
                            }
                            Items it = itemsMapper.getInfoById(sfxmId);
                            //高危人员关联表
                            Riskclientcon rlc = new Riskclientcon();
                            rlc.setGwxm(jcxmmc);//高危项目
                            rlc.setWjzxj(tzcmc);//危急值小结
                            rlc.setDivisionId(ksID);//科室Id
                            rlc.setWjzjb(_wjz);//危急值级别
                            rlc.setCheckTime(lrsj);//检查时间
                            rlc.setDoctorId(jcr);//医生id
                            rlc.setPatientcode(patientcode);//体检号
                            rlc.setSfxm(it.getExamfeeitemName());
                            riskclientconMapper.insert(rlc);

                        }
                        two.setIsDanger(map.getWjz());
                        two.setIntensiveLevel(sign == null ? null : sign.getIntensiveLevel());
                        two.setIsUnchecked(0);
                        //科室Id
                        two.setDivisionId(ksID);
                        two.setChargesId(sfxmId);
                        two.setInputResult(map.getInputResult());
                        String jcmc = map.getJcms();
                        //如果是综合
                        if ("2".equals(tjlx)
//                        		&&!zyJcxmIds.contains(jcxmId)
                        ) {
                            if (ceis.get(jcxmId) != null) {
                                if (sign != null
                                        && (sign.getIsDefault() == null || sign.getIsDefault() == 0 || (sign.getIsInSummary() == null || sign.getIsInSummary() == 0))
                                ) {//如果不是默认 或  体征词进小结s
                                    zy_flag = false;//判断职业小结是否未见异常
                                }
                                //如果没有输入，后台不会获取到
                                if (StringUtils.isNotBlank(map.getInputResult())) {//视力变色力  输入型和与输入型同收费项目的选择型的职业项目都进职业小结
                                    zy_flag = false;//判断职业小结是否未见异常
                                }

                                if (!zyJcxmIds.contains(jcxmId)) {
                                    if (StringUtils.isNotBlank(map.getInputResult())) {//视力变色力
                                        zy_builder.append(jcmc);
                                        zyJcxmIds.add(jcxmId);
                                    } else if (zySfxmIds.contains(map.getSfxmId())) {
                                        zy_builder.append(jcmc);
                                        zyJcxmIds.add(jcxmId);
                                    } else if ((item.getIsDesc() != null && item.getIsDesc() == 1)
                                            && (sign.getIsInSummary() == null || sign.getIsInSummary() == 0)
                                            && ((sign.getIntensiveLevel() != null && sign.getIntensiveLevel() != 0)
                                            || (sign.getIsDefault() != null && sign.getIsDefault() == 1))
                                    ) {
                                        if (item.getIsName() != null && item.getIsName() == 1
                                                && (dept.getSjbggs() == null || dept.getSjbggs() != 11)) {
                                            zy_builder.append(item.getExamitemName() + ":" + jcmc);
                                        } else {
                                            zy_builder.append(jcmc);
                                        }

                                        zyJcxmIds.add(jcxmId);
                                    }

                                }

                                two.setTjlx(1);
                            } else {
                                two.setTjlx(0);
                            }
                        } else if ("0".equals(tjlx)) {
                            two.setTjlx(0);
                        } else if ("1".equals(tjlx) || "3".equals(tjlx)) {
                            two.setTjlx(1);
                        }

                        if (StringUtils.isBlank(jcmc) || "\"null\"".equals(jcmc) || "null".equals(jcmc)) {
                            jcmc = "无;";
                        }

                        two.setMs(jcmc);
                        String jlcId = map.getJlcId();
                        if (StringUtils.isNotBlank(jlcId) && !"\"null\"".equals(jlcId)) {
                            two.setBasconclusionId(jlcId);//结论词id
                        } else {
                            two.setBasconclusionId(null);//结论词id
                        }
                        sectionResultTwoMapper.insert(two);
                    }
                    //体检者收费项目表
                    Peispatientfeeitem user = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_patient", patientcode).eq("id_ks", ksID)
                            .eq("id_examfeeitem", sfxmId).eq("f_giveup", 0).eq("change_item", 0)
                    );
                    if (user != null) {
                        if (user.getChangeItem() != null && user.getChangeItem() == 1) {
                            throw new ServiceException("审核失败，收费项目" + user.getExamfeeitemName() + "已退项。");
                        }
                        if (user.getSfjj() != null && user.getSfjj() == 1) {
                            throw new ServiceException("审核失败，收费项目" + user.getExamfeeitemName() + "已拒检。");
                        }
                        if (user.getFGiveup() != null && user.getFGiveup() == 1) {
                            throw new ServiceException("审核失败，收费项目" + user.getExamfeeitemName() + "已弃检。");
                        }
                        user.setFExaminated(1);
                        user.setExaminatetime(rummagerTime);
                        peispatientfeeitemService.updateById(user);
                    }
                } else {//弃检
                    String jcxmId = map.getJcxmId();
                    //体检者收费项目表
                    Peispatientfeeitem user = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_patient", patientcode).eq("id_ks", ksID)
                            .eq("id_examfeeitem", sfxmId)
                    );
                    if (user != null) {
                        if (user.getChangeItem() != null && user.getChangeItem() == 1) {
                            throw new ServiceException("审核失败，收费项目" + user.getExamfeeitemName() + "已退项。");
                        }
                        if (user.getSfjj() != null && user.getSfjj() == 1) {
                            throw new ServiceException("审核失败，收费项目" + user.getExamfeeitemName() + "已拒检。");
                        }
                        if (user.getFGiveup() != null && user.getFGiveup() == 1) {
                            throw new ServiceException("审核失败，收费项目" + user.getExamfeeitemName() + "已弃检。");
                        }
                    }
                    if (StringUtils.isNotBlank(jcxmId) && !"\"null\"".equals(jcxmId)) {//选中或弃检
                        SectionResultTwo two = new SectionResultTwo();
                        two.setMainId(mainId);
                        two.setVerdictId(jcxmId);//检查项目id
                        two.setNodule(map.getTzcId());//体征词Id
                        two.setChargesId(map.getSfxmId());
                        two.setPatientcode(patientcode);//体检号
                        two.setShortCode(shortCode);
                        two.setIsUnchecked(1);
                        two.setDivisionId(ksID);//科室Id
                        sectionResultTwoMapper.insert(two);
                    }
                }
            }
        }

        // TODO: 2024/5/20 职业小结更改为和健康小结一致的 
        //职业小结
        srm.setZyConclusions(xjdata);
//        if ("1".equals(tjlx) || "3".equals(tjlx)) {
//            srm.setZyConclusions(xjdata);
//        } else if ("2".equals(tjlx)) {
//            if (zy_flag) {
//                srm.setZyConclusions("未见异常；");
//            } else {
//                srm.setZyConclusions(zy_builder.toString());
//            }
//        }
        updateById(srm);

        //高危人员
        if (sfgw) {
            //高危人员管理表
            List<Riskclient> oldrc = riskclientMapper.selectList(new QueryWrapper<Riskclient>().eq("tjid", patientcode));
            if (CollectionUtils.isNotEmpty(oldrc)) {
                riskclientMapper.deleteBatchIds(oldrc);
            }
            String gwrymc = gwry == null ? "" : gwry.getPatientname();//名称
            String nl = (String) (gwry == null ? "" : gwry.getAge() + "");//年龄
//            String xb = gwry==null?"":gwry.getIdSex().toString();//性别
            String lxdh = gwry == null ? "" : gwry.getPhone();//联系电话
            Riskclient rc = new Riskclient();
            rc.setCid(gwry.getHospitalcode());
            rc.setTjid(patientcode);
            rc.setGwrymc(gwrymc);//高危人员名称
            rc.setNl(nl);
            rc.setXb(gwry.getIdSex());
            rc.setLxdh(lxdh);
            rc.setGwxm(gwxms);
            rc.setTjlx(Integer.valueOf(gwry.getIdExamtype()));//体检类型
            rc.setTjzt(0);
            rc.setCid(SecurityUtils.getCId());
            rc.setIdOrg(gwry.getIdOrg());
            rc.setIdOpendoctor(gwry.getIdOpendoctor());
            rc.setTirq(jcsj);//检查时间
            riskclientMapper.insert(rc);
            String riskId = rc.getId();
            //高危人员关联表
            List<Riskclientcon> oldrlist = riskclientconMapper.selectList(new QueryWrapper<Riskclientcon>()
                    .eq("patientcode", patientcode)
                    .ne("riskid", "").isNotNull("riskid")
                    .eq("division_id", ksID));
            if (oldrlist != null && oldrlist.size() > 0) {
                for (int i = 0, l = oldrlist.size(); i < l; i++) {
                    Riskclientcon rlc = oldrlist.get(i);
                    riskclientconMapper.deleteById(rlc);
                }
            }
            //高危人员关联表
            List<Riskclientcon> rlist = riskclientconMapper.selectList(new QueryWrapper<Riskclientcon>()
                    .isNull("riskid")
                    .eq("patientcode", patientcode));
            if (rlist != null && rlist.size() > 0) {
                for (int i = 0, l = rlist.size(); i < l; i++) {
                    Riskclientcon rlc = rlist.get(i);
                    rlc.setRiskid(riskId);//高危人员表id
                    riskclientconMapper.updateById(rlc);
                }
            }
        }

        //设置体检者收费项目阳性小结
        if (!sfxmMap.isEmpty()) {
            Set<Map.Entry<String, String>> set = sfxmMap.entrySet();
            Iterator<Map.Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String sfxmId = entry.getKey();
                String yxxj = entry.getValue();
                //体检者收费项目表
                Peispatientfeeitem user = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", patientcode)
                        .eq("id_ks", ksID)
                        .eq("id_examfeeitem", sfxmId));
                if (user != null) {
                    user.setPositivesummary(yxxj);
                    peispatientfeeitemService.updateById(user);
                }
            }
        }

        //修改体检者收费项目的弃检状态
        List<Peispatientfeeitem> userlist = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientcode)
                .eq("id_ks", ksID)
                .eq("sfjj", 0)
                .eq("f_giveup", 0)
                .eq("change_item", 0)
        );
        for (Peispatientfeeitem user : userlist) {
            Integer tzczsl = 0;
            //收费项目Id
            String sfxmId = user.getIdExamfeeitem();
            //检查项目收费项目关联表
            List<InspectCharge> iclist = inspectChargeMapper.selectList(new QueryWrapper<InspectCharge>().eq("charge_id", sfxmId));
            if (iclist != null && iclist.size() > 0) {
                ArrayList<String> ids = new ArrayList<String>();
                for (InspectCharge ic : iclist) {
                    String jcxmId = ic.getInspectId();
                    //检查项目体证词关联表
                    List<BasexamltemSign> besList = basexamltemSignMapper.selectList(new QueryWrapper<BasexamltemSign>().eq("inspect_id", jcxmId).eq("is_delete", 0));
                    Integer tzcsl = besList == null ? 0 : besList.size();
                    tzczsl = tzczsl + tzcsl;
                    ids.add(jcxmId);
                }
                //结论词、小结
                List<SectionResultTwo> srtlist = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                        .in("verdict_id", ids).eq("charges_id", sfxmId)
                        .eq("patientcode", patientcode).eq("is_unchecked", 1));
                if (srtlist != null) {
                    Integer srtsl = srtlist.size();
                    if (srtsl > 0 && srtlist.size() >= tzczsl) {
                        user.setFGiveup(1);
                        peispatientfeeitemService.updateById(user);
                    }

                }
            }
        }
        //修改全检标志、开始检查标志
        if (outsideMainService.getAllSfxmtzjStatus(patientcode)) {
            gwry.setFReadytofinal(1);//0:已备单 1:分检完成
            gwry.setReadytofinalDate(new Date());
            peisStateService.setScbs(gwry.getPatientcode(), 0);
            //根据获inputCode取体检者收费项目表
            List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientcode);
            for (Peispatientfeeitem other : other_items) {
                //设置未关联科室项目为已检
                other.setFExaminated(1);
            }
            peispatientfeeitemService.updateBatchById(other_items);
        }
        gwry.setFExamstarted(1);
        peispatientMapper.updateById(gwry);

        //保存手动添加的结论词
        ArrayList<String> array = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(griddata)) {

            for (saOrUpJlcDto d : griddata) {
                String jlcId = d.getJlcId();
                if (StringUtils.isNotBlank(jlcId) && !"\"null\"".equals(jlcId)) {//如果有结论词
                    array.add(jlcId);
                    //结论词、小结
                    Long i = sectionResultTwoMapper.selectCount(new QueryWrapper<SectionResultTwo>()
                            .eq("patientcode", patientcode)
                            .eq("basconclusion_id", jlcId)
                            .eq("is_unchecked", 0)
                            .eq("division_id", ksID));
                    if (i > 0) {
                        continue;
                    } else {
                        SectionResultTwo two = new SectionResultTwo();
                        two.setMainId(mainId);
                        //检查项目id
                        two.setVerdictId(d.getJcxmId());
                        //体征词id
                        String tzcId = d.getTzcId();
                        two.setNodule(tzcId);
                        if (StringUtils.isNotEmpty(tzcId)) {
                            BasexamltemSign sign = basexamltemSignMapper.getInfoById(tzcId);
                            if (sign != null) {
                                two.setPosistive(sign.getIsPositive());
                            }
                        }
                        two.setPatientcode(patientcode);//体检号
                        two.setShortCode(shortCode);
                        two.setIsUnchecked(0);
                        two.setBasconclusionId(jlcId);
                        two.setDivisionId(ksID);//科室Id
                        two.setTjlx(0);//手动添加的健康
                        sectionResultTwoMapper.insert(two);
                    }
                }
            }
        }
        //将不在griddata中的检查结果子表 结论词Id设为Null
        List<SectionResultTwo> twolist = null;
        if (array.size() > 0) {
            //griddata中有结伦词的
            twolist = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                    .eq("patientcode", patientcode)
                    .notIn("basconclusion_id", array)
                    .eq("is_unchecked", 0)
                    .eq("division_id", ksID));
        } else {
            twolist = sectionResultTwoMapper.selectList(new QueryWrapper<SectionResultTwo>()
                    .eq("patientcode", patientcode)
                    .eq("is_unchecked", 0)
                    .eq("division_id", ksID));
        }
        if (twolist != null && twolist.size() > 0) {
            for (SectionResultTwo two : twolist) {
                two.setBasconclusionId(null);
                sectionResultTwoMapper.updateById(two);
            }
        }
        //科室描述、检查结果表
        describeMapper.delete(new QueryWrapper<Describe>().eq("patientcode", patientcode).eq("dep_id", ksID));


        String depDes = dept == null ? null : dept.getDescription();
        for (ASaveDataDto entry : describes_map) {

            Describe des = new Describe();
            des.setPatientcode(entry.getPatientcode());
            des.setShortCode(shortCode);
            des.setInspectDescribe(entry.getInspectDescribe() == null ? null : entry.getInspectDescribe());
            des.setSignList(entry.getSignList() == null ? null : entry.getSignList());
            des.setDepId(ksID);
            String feeId = entry.getFeeId();
            des.setFeeId(feeId);
            //收费项目表
            Items items = itemsMapper.getInfoById(feeId);
            des.setFeeName(items == null ? null : items.getExamfeeitemNameprn());
            String examId = entry.getItemId();
            des.setItemId(examId);
            //JC检查项目表
            Basexamltem exam = basexamltemMapper.getInfoById(examId);
            des.setItemName(exam == null ? null : exam.getExamitemNameprn());
            des.setDepDescription(depDes);
            if ("1".equals(tjlx) || "3".equals(tjlx)) {
                des.setTjlx(1);
            } else if ("0".equals(tjlx)) {
                des.setTjlx(0);
            } else if ("2".equals(tjlx)) {
                if (ceis.get(des.getItemId()) != null) {
                    des.setTjlx(1);
                } else {
                    des.setTjlx(0);
                }
            }
            describeMapper.insert(des);
        }
        return Boolean.TRUE;
    }


    /**
     * 反审核(图像检查)
     *
     * @param patientcode
     * @param ksID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean caseReverse(String patientcode, String ksID) {
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientcode);
        if (peispatient == null) {
            throw new ServiceException("反审核失败，该体检号尚未登记！");
        }
        if (peispatient.getJktjzt() != null && peispatient.getJktjzt() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被" + (peispatient.getDoctorfinalNameR() == null ? "" : peispatient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getZytjzt() != null && peispatient.getZytjzt() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被" + (peispatient.getPatientnameencoded() == null ? "" : peispatient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getFFinallocked() != null && peispatient.getFFinallocked() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被" + (peispatient.getIdDoctorapply() == null ? "" : peispatient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (peispatient.getIdGuidenurse() != null && peispatient.getIdGuidenurse() == 1) {
            throw new ServiceException("反审核失败，本体检者检查结果已被" + (peispatient.getParsedassignedsuiteandfi() == null ? "" : peispatient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        //KS科室检查结果主表
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id", ksID)
                .eq("patientcode", patientcode));
        if (main == null || main.getIsAudit() == null || main.getIsAudit() != 1) {
            throw new ServiceException("反审核失败，该体检号未审核！");
        }
        if (!SecurityUtils.getUserNo().equals(main.getAuditId())) {
            throw new ServiceException("反审核失败，只有审核人:" + main.getAuditName() + "才能反审核！");
        }

        peispatient.setFReadytofinal(0);
        peispatientMapper.updateById(peispatient);
        peisStateService.setScbs(peispatient.getPatientcode(), 0);
        main.setIsAudit(0);
        sectionResultMainMapper.updateById(main);
        //体检者收费项目表
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientcode)
                .eq("id_ks", ksID)
                .eq("f_giveup", 0)
                .eq("sfjj", 0)
                .eq("change_item", 0));
        for (Peispatientfeeitem feeitem : feeitems) {
            feeitem.setFExaminated(0);
            peispatientfeeitemService.updateById(feeitem);
        }
        return Boolean.TRUE;
    }

    /**
     * 普通预览科室报告
     *
     * @param param
     * @return
     */
    @Override
    public DiagnosticVo diagnosticReport(DiagnosticParam param) {
        DiagnosticVo vo = new DiagnosticVo();
        //普通预览科室报告 模板头数据
        if (ObjectUtils.isNotEmpty(param.getShowHeaders()) && param.getShowHeaders() == 1) {
            DiagnosticHeadDto head = divisionMapper.diagnosticHead(param);
            vo.setHead(head);
        }

        //数据报告格式
        SysDept sysDept = sysDeptMapper.getByDeptNo(param.getKsID());
        vo.setSjbggs(sysDept.getSjbggs());

        //科室检查主表
        DepartmentReportDto sectionResultMain = sectionResultMainMapper.departmentReport(param.getPatientcode(), param.getKsID());
        vo.setSectionResultMain(sectionResultMain);
        //科室描述、检查结果表
        List<Describe> describe = describeMapper.selectList(new LambdaQueryWrapper<Describe>()
                .eq(Describe::getPatientcode, param.getPatientcode()).eq(Describe::getDepId, param.getKsID()));
        vo.setDescribes(describe);

        //职业问诊
        if ("124".equals(param.getKsID())) {
            //体检者表
            Peispatient peispatient = peispatientMapper.getByPatientCode(param.getPatientcode());
            //职业问诊
            PeispatientConsultation peispatientConsultation = peispatientConsultationService.getOne(new LambdaQueryWrapper<PeispatientConsultation>()
                    .eq(PeispatientConsultation::getPatientcode, param.getPatientcode()));
            vo.setPeispatientConsultation(peispatientConsultation);
            //职业史
            List<WzZys> wzZys = wzZysMapper.selectList(new LambdaQueryWrapper<WzZys>()
                    .eq(WzZys::getIdPatientarchive, peispatient.getPatientarchiveno()));
            for (WzZys map : wzZys) {
                //有无有害因素
                String occupationHarm = map.getOccupationHarm();
                if (StringUtils.isNotBlank(occupationHarm)) {
                    //查询危害因素
                    List<Harm> harms = harmMapper.selectList(new QueryWrapper<Harm>().in("id", occupationHarm.split(",")));
                    StringBuilder harmName = new StringBuilder();
                    for (int i = 0; i < harms.size(); i++) {
                        if (i != 0) {
                            harmName.append(",");
                        }
                        harmName.append(harms.get(i).getHarmName());
                    }
                    map.setHarmName(harmName.toString());
                }
            }
            vo.setWzZys(wzZys);
            //职业病史
            List<WzZybs> wzZybs = wzZybsMapper.getByPatientarchiveno(peispatient.getPatientarchiveno());
            vo.setWzZybs(wzZybs);
        }

        //肺功能
        if ("77".equals(param.getKsID())) {
            Lung lung = lungMapper.selectOne(new LambdaQueryWrapper<Lung>()
                    .eq(Lung::getDepId, param.getKsID()).eq(Lung::getPatientcode, param.getPatientcode()));
            vo.setLung(lung);
        }
        return vo;
    }


    //字符串分割成集合
    private static List<String> splitStrArrayList(String str) {
        List<String> stringList = new ArrayList<>();
        if (str != null) {
            String[] strs = str.split(",");
            stringList.addAll(Arrays.asList(strs));
            return stringList;
        }
        return null;
    }


    /**
     * 图片科室预览报告
     *
     * @param param
     * @return
     */
    @Override
    public PicReportVo picReport(DiagnosticParam param) {
        PicReportVo vo = new PicReportVo();
        // 模板头数据
        if (ObjectUtils.isNotEmpty(param.getShowHeaders()) && param.getShowHeaders() == 1) {
            DiagnosticHeadDto head = divisionMapper.diagnosticHead(param);
            vo.setHead(head);
        }

        //数据报告格式
        SysDept sysDept = sysDeptMapper.getByDeptNo(param.getKsID());
        vo.setSjbggs(sysDept.getSjbggs());

        //附件
        List<Attachment> list = attachmentMapper.selectList(new QueryWrapper<Attachment>()
                .eq("patientcode", param.getPatientcode())
                .eq("dep_id", param.getKsID())
        );
        vo.setAttachment(list);

        //科室检查主表
        DepartmentReportDto sectionResultMain = sectionResultMainMapper.departmentReport(param.getPatientcode(), param.getKsID());
        vo.setSectionResultMain(sectionResultMain);

        //电测听
        if ("163".equals(param.getKsID())) {

            ElectroAudiometer ele = electroAudiometerMapper.selectOne(new QueryWrapper<ElectroAudiometer>()
                    .eq("patientcode", param.getPatientcode()));
            if (param.getDh() == 1) {
                //职业
                List<Map> mapList = new ArrayList<>();
                Peispatient patient = peispatientMapper.getByPatientCode(param.getPatientcode());
                int age = patient.getAge().intValue();
                Integer sex = patient.getIdSex();
                String sexPre = ("0".equals(sex) ? "M" : "F") + "_List_";

                Map<String, String> dctPro = Constants.DCT;
                Map<String, String> row = new HashMap<String, String>();
                row.put("series", "气导左耳");
                row.put("500HZ", getString(ele.getAirLeft500()));
                row.put("1KHZ", getString(ele.getAirLeft1000()));
                row.put("2KHZ", getString(ele.getAirLeft2000()));
                row.put("3KHZ", getString(ele.getAirLeft3000()));
                row.put("4KHZ", getString(ele.getAirLeft4000()));
                row.put("6KHZ", getString(ele.getAirLeft6000()));
                row.put("500HZC", getCorrectValue(ele.getAirLeft500(), sexPre + "500", dctPro, age));
                row.put("1KHZC", getCorrectValue(ele.getAirLeft1000(), sexPre + "1k", dctPro, age));
                row.put("2KHZC", getCorrectValue(ele.getAirLeft2000(), sexPre + "2k", dctPro, age));
                row.put("3KHZC", getCorrectValue(ele.getAirLeft3000(), sexPre + "3k", dctPro, age));
                row.put("4KHZC", getCorrectValue(ele.getAirLeft4000(), sexPre + "4k", dctPro, age));
                row.put("6KHZC", getCorrectValue(ele.getAirLeft6000(), sexPre + "6k", dctPro, age));
                mapList.add(row);

                row = new HashMap<String, String>();
                row.put("series", "气导右耳");
                row.put("500HZ", getString(ele.getAirRight500()));
                row.put("1KHZ", getString(ele.getAirRight1000()));
                row.put("2KHZ", getString(ele.getAirRight2000()));
                row.put("3KHZ", getString(ele.getAirRight3000()));
                row.put("4KHZ", getString(ele.getAirRight4000()));
                row.put("6KHZ", getString(ele.getAirRight6000()));
                row.put("500HZC", getCorrectValue(ele.getAirRight500(), sexPre + "500", dctPro, age));
                row.put("1KHZC", getCorrectValue(ele.getAirRight1000(), sexPre + "1k", dctPro, age));
                row.put("2KHZC", getCorrectValue(ele.getAirRight2000(), sexPre + "2k", dctPro, age));
                row.put("3KHZC", getCorrectValue(ele.getAirRight3000(), sexPre + "3k", dctPro, age));
                row.put("4KHZC", getCorrectValue(ele.getAirRight4000(), sexPre + "4k", dctPro, age));
                row.put("6KHZC", getCorrectValue(ele.getAirRight6000(), sexPre + "6k", dctPro, age));
                mapList.add(row);

                vo.setElectroAudiometerWork(mapList);
            }
            vo.setElectroAudiometer(ele);
        }
        //一般检查
        if ("20".equals(param.getKsID())) {
            Tjreg tjreg = tjregMapper.selectOne(new QueryWrapper<Tjreg>()
                    .eq("patientcode", param.getPatientcode()));
            vo.setTjreg(tjreg);
        }
        return vo;
    }


    /**
     * 根据公式计算 校正值
     *
     * @param original
     * @param key
     * @param dctPro
     * @param age
     * @return
     */
    public String getCorrectValue(Double original, String key, Map<String, String> dctPro, int age) {
        Double correct = null;
        if (original != null) {
            String value = dctPro.get(key);
            String[] valueArr = value.split(",");
            for (String itemStr : valueArr) {
                String[] itemArr = itemStr.split(":");
                String[] ageArr = itemArr[0].split("-");
                if (age >= Integer.parseInt(ageArr[0].trim())
                        && age <= Integer.parseInt(ageArr[1].trim())) {
                    correct = original - Double.parseDouble(itemArr[1]);
                }
            }
        }
        return correct == null ? null : correct.toString();

    }


    /**
     * 转换成字符串类型
     *
     * @param obj
     * @return
     */
    static String getString(Object obj) {
        return obj == null ? "" : obj.toString();
    }


    /**
     * 检验科科室报告
     *
     * @param param
     * @return
     */
    @Override
    public InspectReportVo inspectReport(DiagnosticParam param) {
        InspectReportVo vo = new InspectReportVo();
        //普通预览科室报告 模板头数据
        if (ObjectUtils.isNotEmpty(param.getShowHeaders()) && param.getShowHeaders() == 1) {
            DiagnosticHeadDto head = divisionMapper.diagnosticHead(param);
            vo.setHead(head);
        }

        //数据报告格式
        SysDept sysDept = sysDeptMapper.getByDeptNo(param.getKsID());
        vo.setSjbggs(sysDept.getSjbggs());

        // 外送图片
        List<OutsidePictrue> outsidePictrue = outsidePictrueMapper.selectList(new LambdaQueryWrapper<OutsidePictrue>()
                .eq(OutsidePictrue::getPatientcode, param.getPatientcode()));
        List<String> testPic = outsidePictrue.stream().map(OutsidePictrue::getPictruePosition).collect(Collectors.toList());
        vo.setTestPic(testPic);
        //科室检查主表
        SectionResultMain sectionResultMain = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", param.getPatientcode())
                .eq("dep_id", param.getKsID()));
        vo.setConclusions(sectionResultMain.getConclusions());
        //LIS结果
        List<GetInspectReportVo> peispatientexamitems = peispatientexamitemMapper.getInspectReportVo(param.getPatientcode(), param.getDh());
        vo.setPeispatientexamitems(peispatientexamitems);
        return vo;
    }
}

