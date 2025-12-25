package com.center.medical.pacs.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyun.oss.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.common.utils.*;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.dao.SectionResultMainMapper;
import com.center.medical.abteilung.dao.SectionResultTwoMapper;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.vo.PacsHistoryListVo;
import com.center.medical.common.config.Domain;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.config.ZhongkangConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.utils.file.Base64ToMultipartFile;
import com.center.medical.common.utils.file.MultipartFileUtil;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.olddata.service.OrPeispatientService;
import com.center.medical.outside.constant.BoyingConstant;
import com.center.medical.pacs.bean.dto.PCFromDataDto;
import com.center.medical.pacs.bean.dto.PCGriddataDto;
import com.center.medical.pacs.bean.model.PacsResultMiddel;
import com.center.medical.pacs.bean.param.*;
import com.center.medical.pacs.bean.vo.PacsAllDoctorsVo;
import com.center.medical.pacs.bean.vo.PacsGetItemsDataVo;
import com.center.medical.pacs.bean.vo.PacsGetJlcGridVo;
import com.center.medical.pacs.bean.vo.PacsGetRankDataVo;
import com.center.medical.pacs.dao.PacsCsharpMapper;
import com.center.medical.pacs.service.PacsCsharpService;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.Comboexamitem;
import com.center.medical.sellcrm.dao.ComboexamitemMapper;
import com.center.medical.service.AttachmentService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysConfigMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * QT体检者表(Peispatient)服务实现类
 *
 * @author ay
 * @since 2023-10-08 09:26:32
 */
@Slf4j
@Service("pacsCsharpService")
@RequiredArgsConstructor
public class PacsCsharpServiceImpl extends ServiceImpl<PacsCsharpMapper, Peispatient> implements PacsCsharpService {

    private final PacsCsharpMapper pacsCsharpMapper;
    private final SysConfigMapper sysConfigMapper;
    private final PeispatientMapper peispatientMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PacsResultMapper pacsResultMapper;
    private final SysUserMapper sysUserMapper;
    private final ISysConfigService iSysConfigService;
    private final SysDeptMapper sysDeptMapper;
    private final AttachmentMapper attachmentMapper;
    private final AttachmentConfigMapper attachmentConfigMapper;
    private final SysBranchMapper sysBranchMapper;
    private final PacsItemMapper pacsItemMapper;
    private final SectionResultMainMapper sectionResultMainMapper;
    private final ItemsMapper itemsMapper;
    private final OutsideMainService outsideMainService;
    private final PeisStateMapper peisStateMapper;
    private final ComboexamitemMapper comboexamitemMapper;
    private final SectionResultTwoMapper sectionResultTwoMapper;
    private final PacsBasexamltemSignMapper pacsBasexamltemSignMapper;
    private final SystemConfig systemConfig;
    private final AttachmentService attachmentService;
    private final PacsSectionResultTwoMapper pacsSectionResultTwoMapper;
    private final PacsSectionResultMainMapper pacsSectionResultMainMapper;
    private final ISysBranchService iSysBranchService;
    private final ISysDeptService iSysDeptService;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final OrPeispatientService orPeispatientService;
    private final PeispatientarchiveMapper peispatientarchiveMapper;

    @Autowired
    private LoadProperties loadProperties;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public String getPage(PageParam<PacsGetRankDataVo> page, GetRankDataParam param) {
        Integer union = getDictionaryStatusByType("PACS") ? 1 : 0;
        param.setUnion(union);
        //去空格大写
        if (StringUtils.isNotEmpty(param.getPatientcode())){
            String patientcode = ToolUtil.patientCode(param.getPatientcode(), iSysBranchService.getBranchFlag(null));
            param.setPatientcode(patientcode);
        }
        IPage<PacsGetRankDataVo> iPage = pacsCsharpMapper.getPage(page, param);
        List<PacsGetRankDataVo> records = iPage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            PacsGetRankDataVo vo = records.get(i);
            vo.setOrderNo(i+1);
            vo.setSex(Render.getSex(vo.getIdSex()));
            vo.setExamtype(Render.getTjlx(vo.getIdExamtype()));
            vo.setStatus(vo.getIsAudit() == null || "0".equals(vo.getIsAudit()) ?
                    "<font color='red'>未审</font>" : "<font color='green'>已审</font>");
            vo.setSta(vo.getIsAudit() == null || "0".equals(vo.getIsAudit()) ? "未审" : "已审");
            if (StringUtils.isNotEmpty(vo.getFlag()) && "1".equals(vo.getFlag()) && StringUtils.isNotEmpty(vo.getIdPatientclass())){
                vo.setVip("<font color='red'>"+vo.getIdPatientclass()+"</font>");
            }
//            vo.setJcstatus(Integer.parseInt(vo.getImg())==0?
//                    "<font color='red'>无图</font>" : "<font color='green'>有图</font>");
        }

        Long yj = pacsCsharpMapper.getCountSql(param);
        Long total = iPage.getTotal();

        HashMap result = new HashMap();
        result.put("data", records);
        result.put("total", total);
        result.put("yj", yj);
        result.put("wj", total-yj);
        String json = JSONUtil.toJsonStr(result);
        return json;
    }



    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return pacsCsharpMapper.getInfoById(id);
    }

    @Override
    public boolean getDictionaryStatusByType(String type) {
        SysConfig dic = sysConfigMapper.checkConfigKeyUnique(type);
        return (dic == null || dic.getConfigValue() == null) ? false : "T".equals(dic.getConfigValue()) ? true : false;
    }

    /**
     * 收费项目表格数据
     * @param param
     * @return
     */
    @Override
    public List<PacsGetItemsDataVo> getItemsData(GetItemsDataVoParam param) {
        Integer union = getDictionaryStatusByType("PACS") ? 1 : 0;
        List<PacsGetItemsDataVo> list = null;
        if (union == 1){
            list = pacsCsharpMapper.getItemsData(param);
        }else {
            list = pacsCsharpMapper.getItemsData2(param);
        }
        if (ObjectUtils.isNotEmpty(list)){
            for (PacsGetItemsDataVo vo : list) {
                Integer fExaminated = vo.getFExaminated();
                if(fExaminated!=null&&fExaminated==1) {
                    vo.setExamed("已检");
                }else {
                    vo.setExamed("未检");
                }
            }
        }
        return list;
    }

    /**
     * 结论词表格数据
     * @param param
     * @return
     */
    @Override
    public List<PacsGetJlcGridVo> getJlcGridData(PacsGetJlcGridParam param) {
        List<PacsGetJlcGridVo> list = pacsCsharpMapper.getJlcGridData(param);
        for (PacsGetJlcGridVo vo : list) {
            Integer isPositive = vo.getIsPositive();
            if(isPositive!=null&&isPositive==1) {
                vo.setPositive("是");
            }else {
                vo.setPositive("否");
            }
        }
        return list;
    }


    /**
     * 选择收费项目
     * @param param
     * @return
     */
    @Override
    public String search(PacsSearchParam param) {
        String patientcode = param.getPatientcode();
        String itemId = param.getId();
        String feeitemId = param.getFeeitemId();
        String ksID = param.getKsID();
        Integer type = param.getType();

        Map<String,Object> result=new HashMap<String, Object>();
        if(StringUtils.isEmpty(patientcode)||StringUtils.isEmpty(itemId)){
            throw new ServiceException("数据异常");
        }
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.getInfoById(feeitemId);
        if(feeitem==null){
            throw new ServiceException("数据异常");
        }
        PacsResult pacsResult = pacsResultMapper.selectOne(new QueryWrapper<PacsResult>()
                .eq("patientcode",patientcode).eq("item_id",feeitem.getIdExamfeeitem()));
        if(pacsResult!=null){//保存过
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HashMap<String,String> main_map = new HashMap<String,String>();
            main_map.put("conclusions",pacsResult.getExamresultsummary());
            main_map.put("description",pacsResult.getExamresultdesc());
            String rummager = pacsResult.getExamdoctor();
            main_map.put("rummager", rummager);
            if(rummager!=null){
                SysUser user = sysUserMapper.selectUserByUserName(rummager);
                if(user!=null){
                    main_map.put("rummagerId",user.getUserNo());
                }
            }
            main_map.put("rummagerTime", pacsResult.getExamdatetime()==null?"":sdf.format(pacsResult.getExamdatetime()));

            main_map.put("writer", pacsResult.getAuditDoctor());
            SysUser user = sysUserMapper.selectUserByUserName(pacsResult.getAuditDoctor());
            if(user!=null){
                main_map.put("writeId",user.getUserNo());
            }

            main_map.put("writeTime", pacsResult.getWriteDate()==null?"":sdf.format(pacsResult.getWriteDate()));
            main_map.put("isAudit", feeitem.getFExaminated()==null?"0":feeitem.getFExaminated().toString());
            result.put("main", main_map);
        }
        String pacsConfig = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
        String imgConf = "0";//图片显示方式
        boolean sortPacsPath = false;//是否需要调整排序方式
        String visitPath = null;//路径配置，优先级别最高
        String mappingPath = null;
        if (StrUtil.isNotEmpty(pacsConfig)) {
            JSONObject jsonObject = JSONUtil.parseObj(pacsConfig);
            String imageType = jsonObject.getStr("imageType");
            if (StrUtil.isNotEmpty(imageType)) {
                imgConf = imageType;
            }
            sortPacsPath = "1".equals(jsonObject.getStr("sortPacsPath"));
            visitPath = jsonObject.getStr("visitPath");
            mappingPath = jsonObject.getStr("mappingPath");
        }

        SysDept dep = sysDeptMapper.getByDeptNo(ksID);

        if("0".equals(imgConf)||!(dep!=null&&"US".equals(dep.getJklx()))) {
            QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
            //0彩超1放射
            if (type==0){
                queryWrapper.orderByAsc("createdate");
            }else {
                queryWrapper.orderByDesc("createdate");
            }
            List<Attachment> atts = attachmentMapper.selectList(queryWrapper
                    .eq("patientcode", patientcode)
                    .eq("dep_id", ksID)
                    .eq("pacs_fee_item_id", feeitemId));
            if(atts.size()>0){
                if(sortPacsPath)sortHuainan(atts,type==0);
                List<Map<String,String>> imgs=new ArrayList<Map<String,String>>();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				String[] arr=new String[atts.size()];
                for(int i=0;i<atts.size();i++){
                    Attachment att=atts.get(i);
                    //可能不是一个config（登记一段时间后回来重新拍）
                    Map<String,String> map=new HashMap<String, String>();
                    Domain domain = iSysConfigService.getSysConfigObject(Constants.DOMAIN_CONFIG, Domain.class);
                    //线上线下地址
                    String prefix = ZhongkangConfig.isOnline()? domain.getRsPfDomain() : domain.getRsLcDomain();
                    map.put("id", att.getId());
                    if(StringUtils.isNotEmpty(visitPath)&&StringUtils.isNotEmpty(mappingPath)) {
                        map.put("src", visitPath+att.getFilePath());
                        map.put("dcmsrc", visitPath+att.getDcmPath());
                        map.put("path", mappingPath+"\\"+att.getFilePath());
                    }else {
                        map.put("src", prefix+att.getFilePath());
                        map.put("dcmsrc", prefix+att.getDcmPath());
                        map.put("path", prefix+att.getFilePath());
                    }
                    map.put("createdate", sdf.format(att.getCreatedate()));
                    map.put("inReport", att.getInReport()==null?"0":att.getInReport().toString());
                    imgs.add(map);
                }
                result.put("imgs", JSONUtil.toJsonStr(imgs));
            }
        }else if("1".equals(imgConf)) {
            //选择项目时显示本科室本体号所有图片，选中进报告的关联到当前项目上
            QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
            //0彩超1放射
            if (type==0){
                queryWrapper.orderByAsc("createdate");
            }else {
                queryWrapper.orderByDesc("createdate");
            }
            List<Attachment> atts = attachmentMapper.selectList(queryWrapper
                    .eq("patientcode", patientcode)
                    .eq("dep_id", ksID)
            );
            if(atts.size()>0){
                if(sortPacsPath)sortHuainan(atts,type==0);
                AttachmentConfig pac = attachmentConfigMapper.getInfoById(atts.get(0).getConfigId());
                List<Map<String,String>> imgs=new ArrayList<Map<String,String>>();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				String[] arr=new String[atts.size()];
                for(int i=0;i<atts.size();i++){
                    Map<String,String> map=new HashMap<String, String>();
                    Attachment att=atts.get(i);
                    map.put("id", att.getId());
                    if(pac!=null){
                        map.put("src", pac.getVisitPath()+att.getFilePath());
                        map.put("dcmsrc", pac.getVisitPath()+att.getDcmPath());
                        map.put("path", pac.getMappingPath()+"\\"+att.getFilePath());
                    }
                    map.put("createdate", sdf.format(att.getCreatedate()));
                    //这个项目进报告的图片显示打钩
                    map.put("inReport"
                            ,(feeitemId.equals(att.getPacsFeeItemId())
                                    &&att.getInReport()!=null
                                    &&att.getInReport().intValue()==1)?"1":"0");
                    imgs.add(map);
                }
                result.put("imgs", JSONUtil.toJsonStr(imgs));
            }
        }
        /**历史*/
        List<PacsHistoryListVo> historys = pacsCsharpMapper.getHistoryList(patientcode,itemId, null);
        if(historys.size()>0){
            PacsHistoryListVo latest = historys.get(0);
            result.put("description", latest.getDescription()==null?"":latest.getDescription());
            result.put("conclusions", latest.getConclusions()==null?"":latest.getConclusions());
        }else if (iSysConfigService.oldSystemOpen()){
            //查询老老系统历史
            String idcardno = peispatientMapper.getByPatientCode(patientcode).getIdcardno();
            List<String> id = peispatientarchiveMapper.getOldInfoByIdCardNo(idcardno);
            List<PacsHistoryListVo> oldHistorys = orPeispatientService.getHistoryList(idcardno,itemId, null,id);
            if(oldHistorys.size()>0){
                PacsHistoryListVo oldHistory = oldHistorys.get(0);
                result.put("description", oldHistory.getDescription()==null?"":oldHistory.getDescription());
                result.put("conclusions", oldHistory.getConclusions()==null?"":oldHistory.getConclusions());
            }else {
                result.put("description", "");
                result.put("conclusions", "");
            }
        }

        result.put("result", "success");
        return JSONUtil.toJsonStr(result);
    }


    /**
     * 淮南中心图片名称按字符串排序会有问题，要同时满足淮南新的和旧的CT设备生成的图片名正常排序。
     */
    void sortHuainan(List<Attachment> pacsAttachments, final boolean asc){
        Collections.sort(pacsAttachments, new Comparator<Attachment>() {
            @Override
            public int compare(Attachment o1, Attachment o2) {
                String path1=o1.getFilePath();
                String path2=o2.getFilePath();
                return asc?fillAll(path1).compareTo(fillAll(path2)):fillAll(path2).compareTo(fillAll(path1));
            }

            public String fill(String str){
                int l=40;
                int length=str.length();
                if(l<=length)return str;
                StringBuilder sb=new StringBuilder();
                int z=l-length;
                for(int i=0;i<z;i++){
                    sb.append("0");
                }
                sb.append(str);
                return sb.toString();
            }

            public String fillAll(String str){
                String[] arr=str.split("\\.");
                StringBuilder sb=new StringBuilder();
                for(String s:arr){
                    sb.append(fill(s));
                }
                return sb.toString();
            }
        });
    }

    /**
     * 监察人、审核人下拉数据
     * @param userId
     * @param ksID
     * @param key
     * @return
     */
    @Override
    public List<PacsAllDoctorsVo> allDoctors(String userId, String ksID, String key) {
        List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        String cid=null;
        if(userId!=null){
            SysUser user = sysUserMapper.getUserByNo(userId);
            cid=user!=null?user.getCid():sysBranchMapper.getDefaultCid();
        }else{
            cid=sysBranchMapper.getDefaultCid();//deprecated//彩超查默认分中心
        }
        List<PacsAllDoctorsVo> list = pacsCsharpMapper.allDoctors(cid,ksID,key);
        return list;
    }

    /**
     * 检查体检者状态
     * @param patientcode
     * @return
     */
    @Override
    public String check(String patientcode) {

        Peispatient tjPatient = peispatientMapper.getByPatientCode(patientcode);
        if(tjPatient==null){
            return "该体检号尚未登记！";
        }
        if(tjPatient.getFRegistered()==null || tjPatient.getFRegistered()!=1){
            return "该体检号尚未登记！";
        }
        if(tjPatient.getJktjzt()!=null && tjPatient.getJktjzt()==1){
            return "本体检者检查结果已被"+(tjPatient.getDoctorfinalNameR()==null?"":tjPatient.getDoctorfinalNameR())+"完成健康总检，不能修改！如有未检项目也不再进行。";
        }
        if(tjPatient.getZytjzt()!=null &&tjPatient.getZytjzt()==1){
            return "本体检者检查结果已被"+(tjPatient.getPatientnameencoded()==null?"":tjPatient.getPatientnameencoded())+"完成职业总检，不能修改！如有未检项目也不再进行。";
        }
        if(tjPatient.getFFinallocked()!=null && tjPatient.getFFinallocked()==1){
            return "本体检者检查结果已被"+(tjPatient.getIdDoctorapply()==null?"":tjPatient.getIdDoctorapply())+"锁定，不能修改！如有未检项目也不再进行。";
        }
        if(tjPatient.getIdGuidenurse()!=null && tjPatient.getIdGuidenurse()==1){
            return "本体检者检查结果已被"+(tjPatient.getParsedassignedsuiteandfi()==null?"":tjPatient.getParsedassignedsuiteandfi())+"锁定，不能修改！如有未检项目也不再进行。";
        }
        if(tjPatient.getFPaused()!=null&&tjPatient.getFPaused().intValue()==1){
            return "该体检号已被禁检！";
        }
        return "";
    }

    /**
     * 保存
     * @param dataMap
     * @param griddata
     * @param username
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saOrUp(PCFromDataDto dataMap, List<PCGriddataDto> griddata, String username) {
        log.info("彩超客户端保存参数,dataMap:{}",dataMap);
        log.info("彩超客户端保存参数,username:{}",username);
//        log.info("彩超客户端保存参数,griddata:{}",griddata);
        String patientcode = dataMap.getPatientcode();
        if(StringUtils.isEmpty(patientcode)){
            return "请选择体检者";
        }
        Peispatient pacsPatient = peispatientMapper.getByPatientCode(patientcode);
        if(pacsPatient==null){
            return "pacs体检者信息不存在！";
        }
        boolean pacsReg=pacsPatient.getIdExamplace()!=null
                &&pacsPatient.getIdExamplace()==1;//是否是pacs登记 页面登记的 体检系统里都是false
        String feeitemId=dataMap.getFeeitemId();
        if(StringUtils.isEmpty(feeitemId)){
            return "请选择收费项目";
        }
        String pacs=dataMap.getPacs();//1与体检系统对接 默认就是1
        String type=dataMap.getType();//1保存2审核
        boolean tjxt="1".equals(pacs)&&!pacsReg;//表软的体检者、pacs登记的体检者在体检软件中没有 基本上到这都是true
        boolean isFinish=!tjxt||"2".equals(type);//审核操作
        log.info("彩超客户端保存参数,isFinish:{}",isFinish);
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.getInfoById(feeitemId);
        if(feeitem==null){
            return "收费项目已被删除";
        }
        Integer shortcode=feeitem.getShortCode();
        String ksId=feeitem.getIdKs();
        String itemId=feeitem.getIdExamfeeitem();
        Items tjItem=itemsMapper.selectById(itemId);
        String code=tjItem.getExamfeeitemCode();//接口代码
        PacsItems items = pacsItemMapper.selectOne(
                new QueryWrapper<PacsItems>()
                        .eq("examfeeitem_code", code)
                        .eq("is_delete", 0)
        );
        if(items==null){
            return "不存在此收费项目,请联系管理员";
        }
        /**体检软件：判断PACS是否已审核、是否已总检*/
        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id",ksId)
                .eq("patientcode",patientcode));

        Peispatient tjPatient=null;
        if("2".equals(type)){//审核  肯定对接体检软件。如果对接体检软件就不再PACS中登记，只在体检软件登记。
			/*if(main!=null&&main.getIsAudit()){
				return "审核失败，该项目已审核,请勿重复审核！";
			}*/
            if(code==null){
                return "审核失败，收费项目的接口代码没有维护！";
            }
            tjPatient = peispatientMapper.getByPatientCode(patientcode);
            if(tjPatient==null){
                return "审核失败,此体检号未登记!";
            }
            if(tjPatient.getFRegistered()==null || tjPatient.getFRegistered()!=1){
                return "审核失败，该体检号尚未登记！";
            }
            if(tjPatient.getJktjzt()!=null && tjPatient.getJktjzt()==1){
                return "审核失败，本体检者检查结果已被"+(tjPatient.getDoctorfinalNameR()==null?"":tjPatient.getDoctorfinalNameR())+"完成健康总检，不能修改！如有未检项目也不再进行。";
            }
            if(tjPatient.getZytjzt()!=null &&tjPatient.getZytjzt()==1){
                return "审核失败，本体检者检查结果已被"+(tjPatient.getPatientnameencoded()==null?"":tjPatient.getPatientnameencoded())+"完成职业总检，不能修改！如有未检项目也不再进行。";
            }
            if(tjPatient.getFFinallocked()!=null && tjPatient.getFFinallocked()==1){
                return "审核失败，本体检者检查结果已被"+(tjPatient.getIdDoctorapply()==null?"":tjPatient.getIdDoctorapply())+"锁定，不能修改！如有未检项目也不再进行。";
            }
            if(tjPatient.getIdGuidenurse()!=null && tjPatient.getIdGuidenurse()==1){
                return "审核失败，本体检者检查结果已被"+(tjPatient.getParsedassignedsuiteandfi()==null?"":tjPatient.getParsedassignedsuiteandfi())+"锁定，不能修改！如有未检项目也不再进行。";
            }
            if(tjPatient.getFPaused()!=null&&tjPatient.getFPaused().intValue()==1){
                return "审核失败，此体检号已被禁检！";
            }
            //@sqlOrder
            tjPatient.setModifydate(new Date());

        }
        /**保存PACS_RESULT*/
        String rummagerTimeStr=dataMap.getRummagerTime();
        SimpleDateFormat sdf= rummagerTimeStr.length()<=10?
                new SimpleDateFormat("yyyy-MM-dd"):
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date rummagerTime;
        try {
            rummagerTime = sdf.parse(rummagerTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            rummagerTime=null;
        }
        SysDept dep = sysDeptMapper.getByDeptNo(ksId);
        String creatId;
        String rummagerName=dataMap.getRummagerName();
        Date writeTime=new Date();
        PacsResult newPacsResult=new PacsResult(patientcode
                , dataMap.getPatientname()
                , items.getExamfeeitemCode()
                , rummagerTime
                , rummagerName
                , dataMap.getDescription()
                , dataMap.getConclusions()
                , "0"//异常
                , null
                , null
                , 0//传输标志
                , username
                , ksId
                , tjItem==null?null:tjItem.getExamfeeitemNameprn()
                , itemId
                , writeTime
                , items.getId());
        creatId=dataMap.getWriteId();
        String auditDoctor = sysUserMapper.getUserByNo(creatId).getUserName();
        newPacsResult.setAuditDoctor(auditDoctor);
        newPacsResult.setIsNewPacs(1);
        PacsResult pacsResult = pacsResultMapper.selectOne(
                new QueryWrapper<PacsResult>()
                .eq("patientcode",patientcode)
                .eq("item_id",itemId));
        if(pacsResult==null){
            pacsResultMapper.insert(newPacsResult);
        }else{
            BeanUtils.copyProperties(newPacsResult, pacsResult
                    , new String[]{"id"});
            pacsResultMapper.updateById(pacsResult);
        }

        if(dep!=null&&"US".equals(dep.getJklx())){
            String imgConf = "";
            String pacsConfig = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
            if (StrUtil.isNotEmpty(pacsConfig)) {
                JSONObject jsonObject = JSONUtil.parseObj(pacsConfig);
                String imageType = jsonObject.getStr("imageType");
                imgConf = StringUtils.isEmpty(imageType)?"0":"1";
            }
            if("0".equals(imgConf)) {
                String imgs=dataMap.getImgs();
                if(StringUtils.isNotEmpty(imgs)){
                    String j=imgs.startsWith("\"")?imgs.substring(1,imgs.length()-1):imgs;
                    JSONArray ja= JSONUtil.parseArray(j);
                    Set<String> imgids=new HashSet<String>();
                    for(Object o:ja){
                        JSONObject m=JSONUtil.parseObj(o.toString());
                        String imgid=m.getStr("id");
                        if(StringUtils.isEmpty(imgid)) {
                            imgid=m.getStr("Id");
                        }
                        imgids.add(imgid);
                        Integer inReport=StringUtils.isEmpty(m.getStr("inReport"))?0:Integer.parseInt(m.getStr("inReport"));
                        if(inReport==0) {
                            inReport=StringUtils.isEmpty(m.getStr("InReport"))?0:Integer.parseInt(m.getStr("InReport"));
                        }
                        Attachment att = attachmentMapper.getInfoById(imgid);
                        att.setInReport(inReport);
                        attachmentMapper.updateById(att);
                    }
                    if(!imgids.isEmpty()){
                        attachmentMapper.delete(new QueryWrapper<Attachment>()
                                .eq("patientcode", patientcode)
                                .eq("dep_id", ksId)
                                .eq("pacs_fee_item_id", feeitemId)
                                .notIn("id",imgids));
                    }else{
                        attachmentMapper.delete(new QueryWrapper<Attachment>()
                                .eq("patientcode", patientcode)
                                .eq("dep_id", ksId)
                                .eq("pacs_fee_item_id", feeitemId));
                    }
                }else{
                    attachmentMapper.delete(new QueryWrapper<Attachment>()
                            .eq("patientcode", patientcode)
                            .eq("dep_id", ksId)
                            .eq("pacs_fee_item_id", feeitemId));
                }
            }else if("1".equals(imgConf)) {
                String imgs=dataMap.getImgs();
                if(StringUtils.isNotEmpty(imgs)){
                    JSONArray ja=JSONUtil.parseArray(imgs);
                    Set<String> imgids=new HashSet<String>();
                    for(Object o:ja){
                        JSONObject m= JSONUtil.parseObj(o.toString());
                        String imgid=m.getStr("id");
                        if(StringUtils.isEmpty(imgid)) {
                            imgid=m.getStr("Id");
                        }
                        imgids.add(imgid);
                        Integer inReport=StringUtils.isEmpty(m.getStr("inReport"))?0:Integer.parseInt(m.getStr("inReport"));
                        if(inReport==0) {
                            inReport=StringUtils.isEmpty(m.getStr("InReport"))?0:Integer.parseInt(m.getStr("InReport"));
                        }
                        Attachment att = attachmentMapper.getInfoById(imgid);
                        if(inReport==1) {
                            att.setPacsFeeItemId(feeitemId);
                            att.setFeeItemId(feeitem==null?null:feeitem.getId());
                        }else {
                            if(att.getPacsFeeItemId()!=null&&att.getPacsFeeItemId().equals(feeitemId)) {
                                att.setPacsFeeItemId(null);
                                att.setFeeItemId(null);
                            }
                        }
                        att.setInReport(inReport);
                        attachmentMapper.updateById(att);
                    }
                    if(!imgids.isEmpty()){
                        attachmentMapper.delete(new QueryWrapper<Attachment>()
                                .eq("patientcode", patientcode)
                                .eq("dep_id", ksId)
                                .eq("pacs_fee_item_id", feeitemId)
                                .notIn("id",imgids));
                    }else{
                        attachmentMapper.delete(new QueryWrapper<Attachment>()
                                .eq("patientcode", patientcode)
                                .eq("dep_id", ksId)
                                .eq("pacs_fee_item_id", feeitemId)
                        );
                    }
                }else{
                    attachmentMapper.delete(new QueryWrapper<Attachment>()
                            .eq("patientcode", patientcode)
                            .eq("dep_id", ksId)
                            .eq("pacs_fee_item_id", feeitemId)
                    );
                }
            }

        }

        /**状态修改*/
        if(isFinish){
            /**PACS体检者收费项目置为已检*/
            feeitem.setFExaminated(1);
            peispatientfeeitemMapper.updateById(feeitem);
            /**如果是彩超，并且是审核，将所有已保存未审核的项目设为已审核*/
            if("US".equals(dep.getJklx())&&"2".equals(type)){
                List<PacsResult> prs = pacsResultMapper.selectList(new QueryWrapper<PacsResult>()
                        .eq("patientcode",patientcode)
                        .eq("dep_id",ksId)
                        .ne("item_id",itemId));
                for(PacsResult pr:prs){
                    Peispatientfeeitem pri = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_patient",patientcode)
                            .eq("id_examfeeitem",pr.getItemId())
                            .eq("change_item", 0));
                    if(pri==null){
                        continue;//退项后  不存在，但已检的pacsresult没有删除
                        //throw new RuntimeException("收费项目数据异常！");
                    }
                    Peispatientfeeitem prti = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                            .eq("id_patient",patientcode)
                            .eq("id_examfeeitem",pr.getItemId())
                            .eq("f_feecharged",1)
                            .isNull("f_transferedhl7")
                            .eq("sfjj", 0)
                            .eq("f_giveup", 0)
                            .eq("change_item", 0));
                    if(prti==null){
                        //continue;
                        //throw new RuntimeException("收费项目数据异常！");
                        continue;//体检软件弃检就跳过
                    }

                    if(pri.getFExaminated()==null||pri.getFExaminated()==0){
                        pri.setFExaminated(1);
                        peispatientfeeitemMapper.updateById(pri);
                    }

                    if(prti.getFExaminated()==null||prti.getFExaminated()==0){
                        prti.setFExaminated(1);
                        prti.setExaminatetime(rummagerTime);
                        peispatientfeeitemMapper.updateById(prti);
                    }
                }
            }
            /**PACS记录科室是否已全部检查*/
            log.info("彩超客户端保存参数,tjxt:{}",tjxt);
            boolean isAudit = false;
            if (tjxt){
                List<Peispatientfeeitem> peispatientfeeitemList = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_ks", ksId)
                        .eq("change_item", 0)
                        .eq("id_patient", patientcode)
                        .eq("f_giveup", 0)
                        .eq("f_feecharged", 1)
                        .isNull("f_transferedhl7")
                        .eq("f_examinated", 0)
                        .eq("sfjj", 0));
                log.info("彩超客户端保存参数,peispatientfeeitemList:{}",peispatientfeeitemList);
                if (CollectionUtil.isEmpty(peispatientfeeitemList)) isAudit = true;
            }else {
                List<Peispatientfeeitem> peispatientfeeitemList = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", patientcode)
                        .eq("id_ks", ksId)
                        .eq("change_item", 0)
                        .eq("f_examinated", 0));
                log.info("彩超客户端保存参数,peispatientfeeitemList:{}",peispatientfeeitemList);
                if (CollectionUtil.isEmpty(peispatientfeeitemList)) isAudit = true;
            }


            log.info("彩超客户端保存参数,isAudit:{}",isAudit);
            if(isAudit){
                /**修改PACS科室已检*/
                if(main==null){
                    main=new SectionResultMain();
                    main.setDepId(ksId);
                    main.setPatientcode(patientcode);
                    main.setIsAudit(1);
                    sectionResultMainMapper.insert(main);
                }else{
                    main.setIsAudit(1);
                    sectionResultMainMapper.updateById(main);
                }

                /**体检软件：本科室检查完毕 ，修改体检软件相关状态*/
                if("2".equals(type)){
                    /**体检项目设为已检*/
                    feeitem.setFExaminated(1);
                    feeitem.setExaminatetime(rummagerTime);
                    peispatientfeeitemMapper.updateById(feeitem);
                    /**体检软件：开检状态 分检完成状态*/
                    if(outsideMainService.getAllSfxmtzjStatus(patientcode)){
                        tjPatient.setFReadytofinal(1);//0:已备单 1:分检完成
                        tjPatient.setReadytofinalDate(new Date());
                        List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientcode);
                        for(Peispatientfeeitem other:other_items){
                            other.setFExaminated(1);//设置未关联科室项目为已检
                        }
                        peispatientfeeitemService.updateBatchById(other_items);

                        PeisState ps = peisStateMapper.getByPatientcode(tjPatient.getPatientcode());
                        if(ps==null){
                            ps=new PeisState(tjPatient.getPatientcode());
                            ps.setScbs(0);
                            peisStateMapper.insert(ps);
                        }else{
                            ps.setScbs(0);
                        }
                    }
                    tjPatient.setFExamstarted(1);
                    peispatientMapper.updateById(tjPatient);
                }
            }else{
                if("2".equals(type)){
                    /**体检项目设为已检*/
                    feeitem.setFExaminated(1);
                    feeitem.setExaminatetime(rummagerTime);
                    peispatientfeeitemMapper.updateById(feeitem);
                    tjPatient.setFExamstarted(1);
                    peispatientMapper.updateById(tjPatient);
                }
            }

            if("2".equals(type)){
                /**科室结果主表*/
                String tjlx=tjPatient.getIdExamtype();//体检类型
                String jhys=tjPatient.getJhys();//接害因素
                String medicaltype=tjPatient.getMedicaltype();//职业检查类型
                StringBuilder[] cons=getConclusions(tjlx, patientcode, ksId, jhys, medicaltype);

                SectionResultMain tjMain = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                        .eq("patientcode",patientcode)
                        .eq("dep_id",ksId));
                if(tjMain==null){
                    tjMain=new SectionResultMain();
                    tjMain.setDepId(ksId);
                    tjMain.setPatientcode(patientcode);
                    tjMain.setShortCode(shortcode);
                    tjMain.setAssociativeTable("PACS_RESULT");
                }
                String rummagerId=dataMap.getRummagerId();
                tjMain.setRummagerId(rummagerId);
                tjMain.setRummagerName(rummagerName);
                tjMain.setWriteTime(writeTime);
                tjMain.setAuditTime(rummagerTime);
                tjMain.setRummagerTime(rummagerTime);
                tjMain.setAuditId(dataMap.getWriteId());
                tjMain.setAuditName(dataMap.getWriteName());
                tjMain.setWriteId(creatId);
//                tjMain.setIsAudit(1);
                tjMain.setConclusions(cons[0].toString());
                tjMain.setIsFinish(0);//未上传
                if("1".equals(tjlx)||"3".equals(tjlx)){
                    tjMain.setZyConclusions(tjMain.getConclusions());
                }else if("2".equals(tjlx)){
                    tjMain.setZyConclusions(cons[1]==null?null:cons[1].toString());
                }
                if(tjMain.getId()==null){
                    sectionResultMainMapper.insert(tjMain);
                }else{
                    sectionResultMainMapper.updateById(tjMain);
                }
            }
        }

        /**是否职业*/
        Integer tjlx=null;
        String idExamType=pacsPatient.getIdExamtype();
        if("2".equals(idExamType)){//按接害因素、职业体检类型匹配(对接体检软件)
            String jhys=pacsPatient.getJhys();//接害因素
            String medicaltype=pacsPatient.getMedicaltype();//职业检查类型
            List<Comboexamitem> eis = comboexamitemMapper.selectList(new QueryWrapper<Comboexamitem>()
                    .in("harm_id", jhys.split(","))
                    .eq("medical_type",medicaltype));//不对接体检软件，一定是健康体检
            for(Comboexamitem cei:eis){
                if(cei.getItemId().equals(tjItem.getId())){
                    tjlx=1;
                    break;
                }
            }
            if(tjlx==null){
                tjlx=0;
            }
        }else if("0".equals(idExamType)){
            tjlx=0;
        }else {
            tjlx=1;
        }
        /**保存体征词*/
        pacsSectionResultTwoMapper.delete(new QueryWrapper<PacsSectionResultTwo>().eq("feeitem_id",feeitemId));

        for (PCGriddataDto map : griddata) {
            String tzcid=map.getTzcid();
            // TODO: 2023/10/9 wait Tzcid和tzcid只有首字母大写的区别，get的时候取不出来
//            if(tzcid==null) {
//                tzcid=map.getStr("Tzcid");
//            }
            PacsBasexamltemSign sign = pacsBasexamltemSignMapper.getInfoById(tzcid);
            if(sign==null){
                throw new RuntimeException("体征词"+map.getTzcname()+"");
            }
            pacsSectionResultTwoMapper.insert(new PacsSectionResultTwo(
                    sign.getInspectId()
                    , sign.getId()
                    , sign.getIsPositive()
                    , patientcode
                    , sign.getResultId()
                    , ksId
                    , itemId
                    , feeitem.getShortCode()
                    , feeitemId
                    , tjlx));
        }
        return "success";
    }




    public StringBuilder[] getConclusions(String tjlx,String patientcode,String ksId,String jhys,String medicaltype){
        HashMap<String, Comboexamitem> ceis=null;
        if("2".equals(tjlx)){//按接害因素、职业体检类型匹配
            List<Comboexamitem> eis = comboexamitemMapper.selectList(new QueryWrapper<Comboexamitem>()
                    .in("harm_id", jhys.split(","))
                    .eq("medical_type",medicaltype));
            ceis=new HashMap<String, Comboexamitem>();
            for(Comboexamitem cei:eis){
                ceis.put(cei.getItemId(), cei);
            }
        }
        StringBuilder conclusions=new StringBuilder();
        StringBuilder zyConclusions=null;
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("id_patient",patientcode)
                .eq("id_ks", ksId)
                .eq("f_examinated",1)
                .eq("change_item",0));
        List<PacsResult> prs=new ArrayList<PacsResult>();//所有已检的
        for(Peispatientfeeitem feeitem:feeitems){
            PacsResult pr = pacsResultMapper.selectOne(new QueryWrapper<PacsResult>()
                    .eq("patientcode",patientcode)
                    .eq("item_id",feeitem.getIdExamfeeitem())
            );
            if(pr!=null){
                prs.add(pr);
            }
        }
        String rn = System.getProperty("line.separator");//换行符
//        String rn=divisionDao.getUniqueResult("SELECT CHR(10) FROM DUAL ").toString() ;//换行符
        for(PacsResult pr:prs){
            if(pr.getExamresultdesc()!=null){
                conclusions.append("["+pr.getItemName()+"]所见："+rn);
                conclusions.append(pr.getExamresultdesc()+rn);
            }
            if(pr.getExamresultsummary()!=null){
                conclusions.append("["+pr.getItemName()+"]提示："+rn);
                conclusions.append(pr.getExamresultsummary()+rn);
            }
        }
        if(ceis!=null){
            zyConclusions=new StringBuilder();
            for(PacsResult pr:prs){
                PacsItems pacsitem = pacsItemMapper.getInfoById(pr.getPacsItemId());
                if(pacsitem==null||pacsitem.getExamfeeitemCode()==null)continue;
                List<Items> list = pacsCsharpMapper.getItems(patientcode,pacsitem.getExamfeeitemCode());

                if(list.size()==0)continue;
                Items tjitem = list.get(0);
                if(tjitem==null)continue;
                if(ceis.get(tjitem.getId())!=null){
                    if(pr.getExamresultdesc()!=null){
                        zyConclusions.append("["+pr.getItemName()+"]所见："+rn);
                        zyConclusions.append(pr.getExamresultdesc()+rn);
                    }
                    if(pr.getExamresultsummary()!=null){
                        zyConclusions.append("["+pr.getItemName()+"]提示："+rn);
                        zyConclusions.append(pr.getExamresultsummary()+rn);
                    }
                }
            }
        }
        return new StringBuilder[]{conclusions,zyConclusions};
    }

    /**
     * 反审核
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String reverse(PascReverseParam param) {
        String patientcode = param.getPatientcode();
        String username = param.getUsername();
        String feeitemId = param.getFeeitemId();
        if(StringUtils.isEmpty(patientcode)){
            return "请选择体检者";
        }
        if(StringUtils.isEmpty(feeitemId)){
            return "请选择收费项目";
        }
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.getInfoById(feeitemId);
        if(feeitem==null){
            return "收费项目已被删除";
        }
        if(feeitem.getFExaminated()==null||feeitem.getFExaminated()!=1){
            return "反审核失败，项目未审核，不能反审核！";
        }
        String ksId=feeitem.getIdKs();
        String itemId=feeitem.getIdExamfeeitem();
        Peispatient tjPatient = peispatientMapper.getByPatientCode(patientcode);
        if(tjPatient==null){
            return "反审核失败,此体检号未登记!";
        }
        if(tjPatient.getFRegistered()==null || tjPatient.getFRegistered()!=1){
            return "反审核失败，该体检号尚未登记！";
        }
        if(tjPatient.getJktjzt()!=null && tjPatient.getJktjzt()==1){
            return "反审核失败，本体检者检查结果已被"+(tjPatient.getDoctorfinalNameR()==null?"":tjPatient.getDoctorfinalNameR())+"完成健康总检，不能修改！如有未检项目也不再进行。";
        }
        if(tjPatient.getZytjzt()!=null &&tjPatient.getZytjzt()==1){
            return "反审核失败，本体检者检查结果已被"+(tjPatient.getPatientnameencoded()==null?"":tjPatient.getPatientnameencoded())+"完成职业总检，不能修改！如有未检项目也不再进行。";
        }
        if(tjPatient.getFFinallocked()!=null && tjPatient.getFFinallocked()==1){
            return "反审核失败，本体检者检查结果已被"+(tjPatient.getIdDoctorapply()==null?"":tjPatient.getIdDoctorapply())+"锁定，不能修改！如有未检项目也不再进行。";
        }
        if(tjPatient.getIdGuidenurse()!=null && tjPatient.getIdGuidenurse()==1){
            return "反审核失败，本体检者检查结果已被"+(tjPatient.getParsedassignedsuiteandfi()==null?"":tjPatient.getParsedassignedsuiteandfi())+"锁定，不能修改！如有未检项目也不再进行。";
        }
        PacsResult pr = pacsResultMapper.selectOne(new QueryWrapper<PacsResult>()
                        .eq("patientcode",patientcode)
                        .eq("item_id",itemId));
        if(pr!=null && pr.getAuditDoctor()!=null){
            if(!username.equals(pr.getAuditDoctor())){
                return "只有审核人"+("【"+pr.getAuditDoctor()+"】")+"可以反审核！";
            }
        }
        //@sqlOrder
        tjPatient.setModifydate(new Date());
        feeitem.setFExaminated(0);
        peispatientfeeitemMapper.updateById(feeitem);

        SectionResultMain main = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("dep_id",ksId)
                .eq("patientcode",patientcode));
        if(main!=null){
            main.setIsAudit(0);
            sectionResultMainMapper.updateById(main);
        }
        tjPatient.setFReadytofinal(0);
        peispatientMapper.updateById(tjPatient);
        PeisState ps = peisStateMapper.getByPatientcode(tjPatient.getPatientcode());
        if(ps==null){
            ps=new PeisState(tjPatient.getPatientcode());
            ps.setScbs(0);
            peisStateMapper.insert(ps);
        }else{
            ps.setScbs(0);
        }
        feeitem.setFExaminated(0);
        peispatientfeeitemMapper.updateById(feeitem);
        SectionResultMain tjMain = sectionResultMainMapper.selectOne(new QueryWrapper<SectionResultMain>()
                .eq("patientcode",patientcode)
                .eq("dep_id",ksId));
        if(tjMain!=null){//弃检反弃检后可能为null？
            String tjlx=tjPatient.getIdExamtype();//体检类型
            String jhys=tjPatient.getJhys();//接害因素
            String medicaltype=tjPatient.getMedicaltype();//职业检查类型
            StringBuilder[] cons=getConclusions(tjlx, patientcode, ksId, jhys, medicaltype);
            tjMain.setIsFinish(0);
            tjMain.setConclusions(cons[0].toString());
            tjMain.setZyConclusions(cons[1]==null?null:cons[1].toString());
            sectionResultMainMapper.updateById(tjMain);
        }
        return "success";
    }

    /**
     * 保存图片
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveImg(PacsSaveImgParam param) {
        String patientcode = param.getPatientcode();
        String data = param.getBase64();
        String feeitemId = param.getFeeitemId();
        if(StringUtils.isEmpty(patientcode)){
            return "请选择体检者";
        }
        Peispatient pp = peispatientMapper.getByPatientCode(patientcode);
        if(pp==null){
            return "PACS体检者信息不存在";
        }
        if(StringUtils.isEmpty(feeitemId)){
            return "请选择收费项目";
        }
        Peispatientfeeitem feeitem = peispatientfeeitemMapper.getInfoById(feeitemId);
        if(feeitem==null){
            return "收费项目已被删除";
        }
        Integer shortcode=feeitem.getShortCode();
        String ksId=feeitem.getIdKs();
        String itemId=feeitem.getIdExamfeeitem();
        Items items = itemsMapper.selectById(itemId);
        String code=items.getExamfeeitemCode();//接口代码


        Map<String,String > result=new HashMap<String, String>();
        // 解码 base64 字符串为二进制数据
        try {
            MultipartFile file = Base64Util.base64ToMultipart(data);
            //保存单张图片
            String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value()) + "/" + iSysDeptService.getByDeptNo(ksId).getInputCode();
            String extName = FileUtil.extName(file.getOriginalFilename());
            String pacsConfig = iSysConfigService.selectConfigByKey(Constants.PACS_CONFIG);
            int inReport = 0;
            if (StrUtil.isNotEmpty(pacsConfig)) {
                JSONObject jsonObject = JSONUtil.parseObj(pacsConfig);
                Integer inReportConfig = jsonObject.getInt("inReport");
                if (inReportConfig != null) {
                    inReport = inReportConfig.intValue();
                }
            }
            //保存附件
            Attachment att=new Attachment(patientcode
                    , ksId
                    , null
                    , null
                    , 1
                    , null
                    , feeitemId
                    , shortcode
                    , feeitemId
                    , null
                    , inReport);
            att.setPatientcode(patientcode);
            att.setFileType("彩超客户端");
            attachmentMapper.insert(att);
            attachmentService.uploadFile(file, att, baseDir, extName, null, true);

            Domain domain = iSysConfigService.getSysConfigObject(Constants.DOMAIN_CONFIG, Domain.class);
            //线上线下地址
            result.put("src", ZhongkangConfig.isOnline()?domain.getRsPfDomain():domain.getRsLcDomain() + att.getFilePath());
            result.put("id", att.getId());
            result.put("inReport", att.getInReport().toString());
        } catch (IOException e) {
            log.error("上传保存失败！");
            throw new com.center.medical.common.exception.ServiceException("上传保存失败！");
        }
        return JSONUtil.toJsonStr(result);
    }



    /**
     * 获取历史数据
     * @param patientcode
     * @param itemId
     * @param ksID
     * @return
     */
    @Override
    public List<PacsHistoryListVo> getHistoryData(String patientcode, String itemId, String ksID) {
        return pacsCsharpMapper.getHistoryList(patientcode,itemId, ksID);
    }


    /**
     * 发送中间库保存数据
     * @param patientcode
     * @param depId
     */
    @Override
    public void sendSaveResult(String patientcode, String depId) {
        //不是霸州直接跳过
        if (!StringUtils.equals(loadProperties.name, "bazhou")) return;
        //不是从中间库抓取过来的数据也直接跳过
        Long count = peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode,patientcode).eq(Peispatient::getIdExamplace,1.0));//用idExamplace作为从中间库抓取的标识
        if (count == 0) return;

        //查询并组装数据
        SysDept dept = iSysDeptService.getByDeptNo(depId);
        String jklx=dept.getJklx()==null?"":dept.getJklx();
        List<PacsResultMiddel> pacsResults=new ArrayList<PacsResultMiddel>();
        List<PacsResult> list = pacsResultMapper.selectList(new LambdaQueryWrapper<PacsResult>()
                .eq(PacsResult::getPatientcode,patientcode).eq(PacsResult::getDepId,depId));
        for(PacsResult pacs:list){
            PacsResultMiddel result = new PacsResultMiddel(pacs.getPatientcode(),pacs.getPatientname(),pacs.getExamfeeitemCode()
                    ,pacs.getExamdatetime(),pacs.getExamdoctor(),pacs.getExamresultdesc(),pacs.getExamresultsummary(),pacs.getFResulttransfered(),pacs.getUsername());
            Peispatientfeeitem fee = peispatientfeeitemService.getOne(new LambdaQueryWrapper<Peispatientfeeitem>()
                    .eq(Peispatientfeeitem::getIdPatient,patientcode).eq(Peispatientfeeitem::getIdExamfeeitem,pacs.getPacsItemId()));
            if (ObjectUtils.isNotEmpty(fee)){
                List<Attachment> listment = attachmentService.list(new LambdaQueryWrapper<Attachment>()
                        .orderByDesc(Attachment::getCreatedate).eq(Attachment::getPatientcode,patientcode)
                        .eq(Attachment::getDepId,depId).eq(Attachment::getFeeItemId,fee.getId()));
                String path = "";
                for(Attachment ment:listment){
                    //转换成可以直接访问的路径
                    String filePath = "\\\\XXX.XXX.XXX.XXX\\" +  ment.getFilePath().replace("/", "\\");
                    path = path + filePath +";";
                }
                result.setImageFullPath(path);
            }else {
                result.setImageFullPath("");
            }
            result.setTransfterTarget(jklx);
            result.setExamResultIsNormal("正常");
            pacsResults.add(result);
        }

        //发送到中间库里插入或更新
        log.info("saveResult请求数据：{}", pacsResults);
        String urlPrefix = iSysConfigService.getDomain().getLisDomain();
//        String urlPrefix = "http://localhost:8092";
        String url = urlPrefix + "/open/api/pacs/saveResult";
        String msg = HttpUtils.sendPost(url, JSONUtil.toJsonStr(pacsResults));
        log.info("saveResult返回数据：{}", msg);

    }


    /**
     * 接收检查数据
     * @param param
     * @return
     */
    @Override
    public Boolean postCheckExam(PostCheckExamParam param) {
        log.info("成功接收到检查数据:{}",param);
        String patientid = param.getPatientID();
        patientid = ToolUtil.patientCode(patientid, iSysBranchService.getBranchFlag(null));
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientid);
        if (ObjectUtils.isEmpty(peispatient)){
            return Boolean.FALSE;
        }
        //设置科室
        String depId = null;
        if (StringUtils.isEmpty(param.getExamDepartment())){
            depId = "8";//经颅多普勒没设置科室
        }else if ("骨密度".equals(param.getExamDepartment())){
            depId = "161";
        }else if ("动脉硬化".equals(param.getExamDepartment())){
            depId = "17";
        }
        //上传图片
        try {
            uploadFile(param.getReport(), patientid,depId);
        }catch (Exception e){
            log.info("霸州悦琦上传失败");
            log.info(String.valueOf(e));
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Attachment uploadFile(String base64, String patientcode,String depId) throws IOException {
        log.info("霸州悦琦-开始上传：{}、{}", base64, patientcode);
        Attachment attachment = new Attachment();
        attachment.setPatientcode(patientcode);
        //pdf转jpg.base64
        if (!base64.startsWith("data:image")){
            base64="data:image/jpeg;base64,"+ base64;
        }
        Base64ToMultipartFile file = MultipartFileUtil.base64ConvertMultipartFile(base64);
        String extName = file.getExtension();
        String baseDir1 = systemConfig.getFilePathConfig(FilePathConfigFlag.IPP.value()) + "/" + iSysDeptService.getByDeptNo(BoyingConstant.DEPT_ID_ELECTROCARDIOGRAM).getInputCode();
        String baseDir = baseDir1 + "/" + patientcode;
        attachment.setFileType("科室图像");
        attachment.setType(AttachmentType.IMAGE.value());
        SysBranch branch = iSysBranchService.getDefaultBranch();
        String branchId = branch.getBranchId();
        attachment.setBranchId(branchId);
        attachment.setCreatedate(new Date());
        attachment.setDepId(depId);
        attachment.setFileSort("1");
        attachment.setFileType("科室图像");
        attachment.setMemo("霸州悦琦设备回传");
        attachment.setShortCode(CodeUtil.getShortCodeByLong(patientcode));
        attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
        log.info("霸州悦琦-上传结果：{}、{}", attachment.getId(), attachment);

        return attachment;
    }
}

