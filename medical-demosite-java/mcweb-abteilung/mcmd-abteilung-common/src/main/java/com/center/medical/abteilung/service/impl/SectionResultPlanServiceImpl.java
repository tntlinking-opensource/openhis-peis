package com.center.medical.abteilung.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.abteilung.bean.dto.ItemsDetailDto;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.model.SectionResultPlan;
import com.center.medical.abteilung.bean.model.SectionResultTwo;
import com.center.medical.abteilung.bean.param.SectionResultPlanParam;
import com.center.medical.abteilung.bean.vo.SectionResultPlanVo;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.dao.SectionResultPlanMapper;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.abteilung.service.SectionResultPlanService;
import com.center.medical.abteilung.service.SectionResultTwoService;
import com.center.medical.bean.model.Describe;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientfeeitemMapper;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.bean.model.InspectCharge;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.InspectChargeMapper;
import com.center.medical.data.service.BasexamltemService;
import com.center.medical.data.service.BasexamltemSignService;
import com.center.medical.data.service.ItemsService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.bean.model.Peisorgreservationgroup;
import com.center.medical.sellcrm.dao.PeisorgreservationgroupMapper;
import com.center.medical.sellcrm.service.ComboexamitemService;
import com.center.medical.service.DescribeService;
import com.center.medical.service.PeisStateService;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 科室批量录入结果(SectionResultPlan)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:19
 */
@Slf4j
@Service("sectionResultPlanService")
@RequiredArgsConstructor
public class SectionResultPlanServiceImpl extends ServiceImpl<SectionResultPlanMapper, SectionResultPlan> implements SectionResultPlanService {

    private final SectionResultPlanMapper sectionResultPlanMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final InspectChargeMapper inspectChargeMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final Snowflake snowflake;
    private final SysUserMapper sysUserMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final SectionResultTwoService sectionResultTwoService;
    private final ComboexamitemService comboexamitemService;
    private final BasexamltemSignService basexamltemSignService;
    private final BasexamltemService basexamltemService;
    private final OutsideMainService outsideMainService;
    private final PeisStateService peisStateService;
    private final DescribeService describeService;
    private final ItemsService itemsService;

    /**
     * 分页查询[科室批量录入结果]列表
     *
     * @param page                   分页参数
     * @param sectionResultPlanParam SectionResultPlan查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SectionResultPlanVo> getPage(PageParam<SectionResultPlanVo> page, SectionResultPlanParam sectionResultPlanParam) {
        //处理开始时间00:00:00
        if (ObjectUtils.isNotEmpty(sectionResultPlanParam.getStartTime())) {
            DateTime start = DateUtil.beginOfDay(sectionResultPlanParam.getStartTime());
            sectionResultPlanParam.setStartTime(start);
        }
        //结束时间23:59:59
        if (ObjectUtils.isNotEmpty(sectionResultPlanParam.getEndTime())) {
            DateTime end = DateUtil.endOfDay(sectionResultPlanParam.getEndTime());
            sectionResultPlanParam.setEndTime(end);
        }
        return sectionResultPlanMapper.getPage(page, sectionResultPlanParam);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public SectionResultPlan getInfoById(String id) {
        return sectionResultPlanMapper.getInfoById(id);
    }


    /**
     * 检查体检号
     *
     * @param patientcode
     * @param depId
     * @return
     */
    @Override
    public Boolean checkCode(String patientcode, String depId) {
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                .eq("patientcode", patientcode));
        if (ObjectUtils.isEmpty(patient)) {
            throw new ServiceException("该体检号不存在！");
        }
        // 禁检
        if (ObjectUtils.isNotEmpty(patient.getFPaused()) && patient.getFPaused().intValue() == 1) {
            throw new ServiceException("该体检号已被禁检！");
        }
        //任务分组ID
        String idOrgreservationgroup = patient.getIdOrgreservationgroup();
        if (ObjectUtils.isNotEmpty(idOrgreservationgroup)) {
            Peisorgreservationgroup org = peisorgreservationgroupMapper.selectOne(new QueryWrapper<Peisorgreservationgroup>()
                    .eq("id", idOrgreservationgroup).eq("is_delete", 0));
            if (org != null && org.getFGrouppaused() != null && org.getFGrouppaused().intValue() == 1) {
                throw new ServiceException("该体检号已被禁检！");
            }
        }
        // 体检者收费项目表
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient", patientcode).eq("id_ks", depId).isNull("f_transferedhl7")
                .eq("sfjj", 0).eq("f_giveup", 0).eq("change_item", 0)
        );
        if (CollectionUtils.isEmpty(feeitems)) {
            throw new ServiceException("该体检号没有本科室收费项目！");
        }
        if (!"163".equals(depId)) {
            for (Peispatientfeeitem feeitem : feeitems) {
                //检查项目收费项目关联表
                Long count = inspectChargeMapper.selectCount(new QueryWrapper<InspectCharge>()
                        .eq("charge_id", feeitem.getIdExamfeeitem()).eq("is_delete", 0));
                if (count == 0) {
                    throw new ServiceException("收费项目：" + feeitem.getExamfeeitemName()
                            + "，无检查项目，请联系运维人员进行维护。");
                }
                String s = inspectChargeMapper.getExName(feeitem.getIdExamfeeitem());
                if (StringUtils.isNotEmpty(s)) {
                    throw new ServiceException("检查项目：" + s + "，无默认检查结果，请联系运维人员进行维护。");
                }
            }
        }
        SectionResultMain srm = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode", patientcode).eq("id", depId));
        if (ObjectUtils.isNotEmpty(srm) && srm.getIsAudit() == 1) {
            throw new ServiceException("该体检号已审核!");
        }
        return Boolean.TRUE;
    }


    /**
     * 添加
     *
     * @param sectionResultPlans
     * @return
     */
    @Override
    @Transactional
    public Boolean saOrUp(List<SectionResultPlan> sectionResultPlans) {
        String username = SecurityUtils.getUsername();
        for (SectionResultPlan sectionResultPlan : sectionResultPlans) {
            //设置属性
            sectionResultPlan.setCreater(username);
            sectionResultPlan.setCreatedate(new Date());
            // TODO: 2023/2/7 线程自动审核？
            sectionResultPlan.setStatus(0);
            sectionResultPlan.setErrorMsg(null);
        }
        saveBatch(sectionResultPlans);
        return Boolean.TRUE;
    }

    /**
     * 科室审核
     * @param srp
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(SectionResultPlan srp) {
        if(srp.getStatus()==1) {
            return;
        }
        String patientcode=srp.getPatientcode();
        String depId=srp.getDepId();
        checkCode(patientcode, depId);

        //内外耳科
        if(!"163".equals(depId)) {
            saveCase1(patientcode, depId,srp.getCreater());
        }else if("163".equals(depId)) {
//            saveAudiometry(patientcode, depId,srp.getCreater());
        }else {
            throw new RuntimeException("不支持的科室");
        }
        srp.setStatus(1);
        sectionResultPlanMapper.updateById(srp);
    }

    /**
     * 批量录入科室审核
     * @param inputCode
     * @param ksID
     * @param creater
     */
    private void saveCase1(String inputCode, String ksID, String creater) {
        String xjdata="未见异常；";
        inputCode = inputCode.trim().toUpperCase();
        Peispatient gwry = peispatientMapper.getByPatientCode(inputCode);//体检者
        Date now=new Date();
        String username=creater;
        SysUser sysUser = sysUserMapper.getUserByUserName(username);
        String userId = sysUser.getUserNo();

        SysDept dept = sysDeptMapper.getByDeptNo(ksID);
        Integer shortCode=gwry.getShortCode();
        String flag = "cz";//SectionResultMain 已存在
        //科室结果主表
        SectionResultMain srm = sectionResultMainMapper.selectOne(new LambdaQueryWrapper<SectionResultMain>()
                .eq(SectionResultMain::getPatientcode, inputCode).eq(SectionResultMain::getDepId, ksID));
        if(srm==null){
            flag = "xj";//SectionResultMain 新建
            srm = new SectionResultMain();
        }
        srm.setFileType("1");
        srm.setIsFinish(0);
        srm.setDepId(ksID);//科室Id
        srm.setPatientcode(inputCode);
        srm.setShortCode(shortCode);
        srm.setWriteId(userId);//录入人
        srm.setWriteTime(now);//录入时间
        srm.setRummagerId(userId);//检查人
        srm.setRummagerName(username);
        srm.setAuditName(username);
        srm.setRummagerTime(now);//检查时间
        srm.setAuditId(userId);//审核人ID
        srm.setAuditTime(now);//审核时间
        srm.setIsDanager(0);
        srm.setDanagerLevel(0);//危急值级别
        srm.setIsAudit(1);
        srm.setConclusions(xjdata);//小结
        if(flag.equals("cz")){//已存在的结果主表
        }else{//新建的结果主表
            sectionResultMainMapper.insert(srm);
        }

        String mainId = srm.getId();//主表Id
        //删除结果子表
        List<SectionResultTwo> list = sectionResultTwoMapper.selectList(new LambdaQueryWrapper<SectionResultTwo>()
                .eq(SectionResultTwo::getPatientcode,inputCode ).eq(SectionResultTwo::getDivisionId, ksID));
        if(list!=null&&list.size()>0){
            sectionResultTwoService.removeBatchByIds(list);
        }

        String tjlx=gwry.getIdExamtype();//体检类型
        Map<String, Comboexamitem> ceis = null;//key:检查项目ID value：ComboExamItem
        String jhys=gwry.getJhys();//接害因素
        String medicaltype=gwry.getMedicaltype();//职业检查类型
        if("2".equals(tjlx)){//按接害因素、职业体检类型匹配
            List<Comboexamitem> eis = comboexamitemService.list(new LambdaQueryWrapper<Comboexamitem>()
                    .in(Comboexamitem::getHarmId, jhys.split(","))
                    .eq(Comboexamitem::getMedicalType,medicaltype));
            ceis=new HashMap<String, Comboexamitem>();
            for(Comboexamitem cei:eis){
                ceis.put(cei.getExamId(), cei);
            }
        }
        StringBuilder zy_builder=new StringBuilder();//职业小结
        HashSet<String> zyJcxmIds=new HashSet<String>();//已进职业小结的检查项目ids（防止一个检查项目勾选多个体征词，职业小结出现重复的问题）
        HashSet<String> zySfxmIds=new HashSet<String>();//视力变色力类型 如果检查项目所在职业项目有输入型，且自身是职业项目，且选中，就进小结
        boolean zy_flag=true;//职业未见异常标志
//            if("2".equals(tjlx)){
//            	 for(int i = 0 ;  i < l ; i++){
//                    Map<String,String> map = JsonUtil.parseProperties(jsondatas[i]);
//                    if(StringUtils.isNotBlank(map.get("inputResult"))){//视力变色力  输入型和与输入型同收费项目的选择型的职业项目都进职业小结
//             			zySfxmIds.add(map.get("sfxmId"));
//             		}
//            	 }
//            }
        List<ItemsDetailDto> sl = sectionResultPlanMapper.getItemsDetail(inputCode,ksID);
        //生成描述（页面上文本域中的内容）
        Map<String,String> jcmcs=new HashMap<String, String>();//<检查项目id,描述>
        String jcxmIdFlg=null;
        List<String> mcs=null;
        List<String> inspectDescribes=null;
        Map<String,String> describe=null;
        Map<String,Map<String,String>> describes_map=new HashMap<String, Map<String,String>>();
        for(int i = 0 ;  i < sl.size() ; i++){
            ItemsDetailDto os = sl.get(i);
            String jcxmId =os.getEid();
            if(jcxmIdFlg==null) {
                jcxmIdFlg=jcxmId;
                mcs=new ArrayList<String>();
                describe=new HashMap<String, String>();
                describe.put("feeId", os.getIid());
                describe.put("itemId", jcxmId);
                describe.put("patientcode",inputCode );
                inspectDescribes=new ArrayList<String>();
                describes_map.put(jcxmIdFlg, describe);
            }else if(!jcxmIdFlg.equals(jcxmId)) {
                String jcmc=mcs.size()==0?""
                        :(StringUtils.join(mcs.toArray(),"、")+";");
                jcmcs.put(jcxmId,jcmc);
                describe.put("inspectDescribe", jcmc);
                describe.put("signList", inspectDescribes.size()==0?""
                        : StringUtils.join(inspectDescribes,";"));
                describe=new HashMap<String, String>();
                describe.put("feeId", os.getIid());
                describe.put("itemId", jcxmId);
                describe.put("patientcode",inputCode );
                describes_map.put(jcxmId, describe);
                mcs=new ArrayList<String>();
                inspectDescribes=new ArrayList<String>();
                jcxmIdFlg=jcxmId;
            }
            String tzcname=os.getName();
            String bodyDetail=os.getBodyDetail()==null?null:os.getBodyDetail();
            String text= StringUtils.isEmpty(bodyDetail)?tzcname:bodyDetail;
            mcs.add(text);
            inspectDescribes.add(tzcname);
            if(i==sl.size()-1) {
                String jcmc=mcs.size()==0?""
                        :(StringUtils.join(mcs.toArray(),"、")+";");
                jcmcs.put(jcxmId,jcmc);
                describe.put("inspectDescribe", jcmc);
                describe.put("signList", inspectDescribes.size()==0?""
                        : StringUtils.join(inspectDescribes,";"));
            }
        }

        //彩超生成小结(直接使用体检系统的基础数据)
        if("143".equals(ksID)) {
            Map<String,Map<String,String>> cc=new LinkedHashMap<String, Map<String,String>>();
            for(int i = 0 ;  i < sl.size() ; i++){
                ItemsDetailDto os = sl.get(i);
                String jcxmId =os.getEid();
                String tzc=os.getName();
                String bodyDetail=os.getBodyDetail()==null?"":os.getBodyDetail();
                String nameprn=os.getExamfeeitemNameprn()==null?"":os.getExamfeeitemNameprn();
                Map<String,String> m=cc.get(nameprn);
                if(m==null) {
                    m=new HashMap<String, String>();
                    cc.put(nameprn, m);
                }
                String description=m.get("description");
                String conclusions=m.get("conclusions");
                String zydescription=m.get("zydescription");
                String zyconclusions=m.get("zyconclusions");
                int size=m.get("size")==null?0:Integer.parseInt(m.get("size"));
                if(StringUtils.isNotEmpty(bodyDetail)) {
                    m.put("description", StringUtils.isEmpty(description)
                            ?("["+nameprn+"]所见:\n"+bodyDetail)
                            :(description+"\n"+bodyDetail));
                }
                if(StringUtils.isNotEmpty(tzc)) {
                    m.put("conclusions", StringUtils.isEmpty(conclusions)
                            ?("["+nameprn+"]提示:\n"+(size+1)+tzc)
                            :(conclusions+"\n"+(size+1)+tzc));
                    m.put("size", size+1+"");
                }
                if("1".equals(tjlx)||"3".equals(tjlx)) {
                    m.put("zydescription", m.get("description"));
                    m.put("zyconclusions", m.get("conclusions"));
                }else if("2".equals(tjlx)) {
                    int zysize=m.get("zysize")==null?0:Integer.parseInt(m.get("zysize"));
                    if(ceis.get(jcxmId)!=null) {
                        if(StringUtils.isNotEmpty(bodyDetail)) {
                            m.put("zydescription", StringUtils.isEmpty(zydescription)
                                    ?("["+nameprn+"]所见:\n"+bodyDetail)
                                    :(zydescription+"\n"+bodyDetail));
                        }
                        if(StringUtils.isNotEmpty(tzc)) {
                            m.put("zyconclusions", StringUtils.isEmpty(zyconclusions)
                                    ?("["+nameprn+"]提示:\n"+(zysize+1)+tzc)
                                    :(zyconclusions+"\n"+(zysize+1)+tzc));
                            m.put("zysize", zysize+1+"");
                        }
                    }
                }
            }
            List<String> zkc=new ArrayList<String>();
            List<String> zyc=new ArrayList<String>();
            for(Map.Entry<String,Map<String,String>> entry:cc.entrySet()) {
                Map<String,String> m=entry.getValue();
                String description=m.get("description")==null?"":(m.get("description")+"\n");
                String conclusions=m.get("conclusions")==null?"":m.get("conclusions");
                String zydescription=m.get("zydescription")==null?"":(m.get("zydescription")+"\n");
                String zyconclusions=m.get("zyconclusions")==null?"":m.get("zyconclusions");
                String zk=description+conclusions;
                String zy=zydescription+zyconclusions;
                if(StringUtils.isNotEmpty(zk)) {
                    zkc.add(zk);
                }
                if(StringUtils.isNotEmpty(zy)) {
                    zyc.add(zy);
                }
            }
            srm.setConclusions(StringUtils.join(zkc, "\n"));
            srm.setZyConclusions(StringUtils.join(zyc, "\n"));
        }

        for(int i = 0 ;  i < sl.size() ; i++){
            ItemsDetailDto os = sl.get(i);
            String sfxmId =os.getIid();//收费项目Id
            String jcxmId =os.getEid();
            if(StringUtils.isNotBlank(jcxmId)){//选中
                SectionResultTwo two = new SectionResultTwo();
                two.setChargesId(sfxmId);
                two.setMainId(mainId);
                two.setVerdictId(jcxmId);//检查项目id
                String tczId = os.getSid();
                two.setNodule(tczId);//体征词Id
                BasexamltemSign sign = basexamltemSignService.getInfoById(tczId);//体征词
                Basexamltem item = basexamltemService.getInfoById(jcxmId);//检查项目
                if(sign!=null){
                    two.setPosistive(sign.getIsPositive());//是否阳性
                }
                two.setPatientcode(inputCode);//体检号
                two.setShortCode(shortCode);
                two.setIsDanger(0);
                two.setIntensiveLevel(sign==null?null:sign.getIntensiveLevel());
                two.setIsUnchecked(0);
                two.setDivisionId(ksID);//科室Id
                two.setChargesId(sfxmId);
                two.setInputResult(null);
                String jcmc = jcmcs.get(jcxmId);
                //如果是综合
                if("2".equals(tjlx)){
                    if(ceis.get(jcxmId)!=null){
                        if(sign!=null
                                &&(sign.getIsDefault()==null||sign.getIsDefault()==0||(sign.getIsInSummary()==null||sign.getIsInSummary()==0))
                        ){//如果不是默认 或  体征词进小结s
                            zy_flag=false;//判断职业小结是否未见异常
                        }
                        //如果没有输入，后台不会获取到
//                		if(StringUtils.isNotBlank(map.get("inputResult"))){//视力变色力  输入型和与输入型同收费项目的选择型的职业项目都进职业小结
//                			zy_flag=false;//判断职业小结是否未见异常
//                		}

                        if(!zyJcxmIds.contains(jcxmId)){
//                    		if(StringUtils.isNotBlank(map.get("inputResult"))){//视力变色力
//                    			zy_builder.append(jcmc);
//                    			zyJcxmIds.add(jcxmId);
//                    		}else
                            if(zySfxmIds.contains(sfxmId)){
                                zy_builder.append(jcmc);
                                zyJcxmIds.add(jcxmId);
                            }else if((item.getIsDesc()!=null&&item.getIsDesc()==1)
                                    &&(sign.getIsInSummary()==null||sign.getIsInSummary()==0)
                                    &&((sign.getIntensiveLevel()!=null&&sign.getIntensiveLevel()!=0)
                                    ||(sign.getIsDefault()!=null&&sign.getIsDefault()==1))
                            ){
                                if(item.getIsName()!=null&&item.getIsName()==1
                                        &&(dept.getSjbggs()==null||dept.getSjbggs()!=11)){
                                    zy_builder.append(item.getExamitemName()+":"+jcmc);
                                }else{
                                    zy_builder.append(jcmc);
                                }

                                zyJcxmIds.add(jcxmId);
                            }

                        }

                        two.setTjlx(1);
                    }else{
                        two.setTjlx(0);
                    }
                }else if("0".equals(tjlx)){
                    two.setTjlx(0);
                }else if("1".equals(tjlx)||"3".equals(tjlx)){
                    two.setTjlx(1);
                }

                if(StringUtils.isBlank(jcmc)||"\"null\"".equals(jcmc)){
                    jcmc = "无;";
                }
                two.setMs(jcmc);
                String jlcId = os.getCid()==null?null:os.getCid();
                if(StringUtils.isNotBlank(jlcId)&&!"\"null\"".equals(jlcId)){
                    two.setBasconclusionId(jlcId);//结论词id
                }else{
                    two.setBasconclusionId(null);//结论词id
                }
                sectionResultTwoService.save(two);
            }
            Peispatientfeeitem user = peispatientfeeitemMapper.selectOne(new LambdaQueryWrapper<Peispatientfeeitem>()
                    .eq(Peispatientfeeitem::getIdPatient, inputCode).eq(Peispatientfeeitem::getIdKs, ksID)
                    .eq(Peispatientfeeitem::getIdExamfeeitem, sfxmId)
            );
            if(user!=null){
                if(user.getChangeItem()!=null&&user.getChangeItem()==1) {
                    throw new RuntimeException("审核失败，收费项目"+user.getExamfeeitemName()+"已退项。");
                }
                if(user.getSfjj()!=null&&user.getSfjj()==1) {
                    throw new RuntimeException("审核失败，收费项目"+user.getExamfeeitemName()+"已拒检。");
                }
                if(user.getFGiveup()!=null&&user.getFGiveup()==1) {
                    throw new RuntimeException("审核失败，收费项目"+user.getExamfeeitemName()+"已弃检。");
                }
                user.setFExaminated(1);
                user.setExaminatetime(now);
                peispatientfeeitemMapper.updateById(user);
            }
        }

        if(!"143".equals(ksID)) {
            //职业小结
            if("1".equals(tjlx)||"3".equals(tjlx)){
                srm.setZyConclusions(xjdata);
            }else if("2".equals(tjlx)){
                if(zy_flag){
                    srm.setZyConclusions("未见异常；");
                }else{
                    srm.setZyConclusions(zy_builder.toString());
                }
            }
        }
        sectionResultMainMapper.updateById(srm);

        //修改全检标志、开始检查标志
        if(outsideMainService.getAllSfxmtzjStatus(inputCode)){
            gwry.setFReadytofinal(1);//0:已备单 1:分检完成
            gwry.setReadytofinalDate(new Date());
            peisStateService.setScbs(gwry.getPatientcode(),0);
            List<Peispatientfeeitem> other_items=peispatientfeeitemMapper.getNoCheckItems(inputCode);
            for(Peispatientfeeitem other:other_items){
                other.setFExaminated(1);//设置未关联科室项目为已检
                peispatientfeeitemMapper.updateById(other);
            }
        }
        gwry.setFExamstarted(1);
        peispatientMapper.updateById(gwry);

        //将不在griddata中的检查结果子表 结论词Id设为Null
        List<SectionResultTwo> twolist = sectionResultTwoMapper.selectList(new LambdaQueryWrapper<SectionResultTwo>()
                .eq(SectionResultTwo::getPatientcode, inputCode)
                .eq(SectionResultTwo::getIsUnchecked,0)
                .eq(SectionResultTwo::getDivisionId,ksID));
        if(twolist!=null&&twolist.size()>0){
            for(SectionResultTwo two :twolist){
                two.setBasconclusionId(null);
                sectionResultTwoMapper.updateById(two);
            }
        }

        //descirbe
        describeService.remove(new LambdaQueryWrapper<Describe>().eq(Describe::getPatientcode,inputCode).eq(Describe::getDepId,ksID));
        String depDes=dept==null?null:dept.getDescription();
        for(Map.Entry<String,Map<String,String>> entry:describes_map.entrySet()){
            Map<String, String> describe_map=entry.getValue();
            Describe des=new Describe();
            des.setPatientcode(describe_map.get("patientcode"));
            des.setShortCode(shortCode);
            des.setInspectDescribe(describe_map.get("inspectDescribe")==null?null:describe_map.get("inspectDescribe"));
            des.setSignList(describe_map.get("signList")==null?null:describe_map.get("signList"));
            des.setDepId(ksID);
            String feeId=describe_map.get("feeId");
            des.setFeeId(feeId);
            Items items = itemsService.getInfoById(feeId);
            des.setFeeName(items==null?null:items.getExamfeeitemNameprn());
            String examId=describe_map.get("itemId");
            des.setItemId(examId);
            Basexamltem exam = basexamltemService.getInfoById(examId);
            des.setItemName(exam==null?null:exam.getExamitemNameprn());
            des.setDepDescription(depDes);
            if("1".equals(tjlx)||"3".equals(tjlx)){
                des.setTjlx(1);
            }else if("0".equals(tjlx)){
                des.setTjlx(0);
            }else if("2".equals(tjlx)){
                if(ceis.get(des.getItemId())!=null){
                    des.setTjlx(1);
                }else{
                    des.setTjlx(0);
                }
            }
            describeService.save(des);
        }

    }
}

