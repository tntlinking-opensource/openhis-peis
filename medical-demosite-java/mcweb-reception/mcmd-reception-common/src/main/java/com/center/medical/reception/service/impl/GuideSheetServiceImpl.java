package com.center.medical.reception.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.config.BarcodeMarginConfig;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.ReportConstants;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.data.bean.model.Notificationmethod;
import com.center.medical.data.dao.NotificationmethodMapper;
import com.center.medical.data.service.PatienttypeService;
import com.center.medical.reception.bean.param.BillModelDataParam;
import com.center.medical.reception.dao.GuideSheetMapper;
import com.center.medical.reception.service.GuideSheetService;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.sellcrm.dao.SellcustomerMapper;
import com.center.medical.service.PeispatientPhotoService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-04-20 15:01:15
 */
@Slf4j
@Service("guideSheetService")
@RequiredArgsConstructor
public class GuideSheetServiceImpl extends ServiceImpl<GuideSheetMapper, Peispatient> implements GuideSheetService {

    private final GuideSheetMapper guideSheetMapper;
    private final SysBranchMapper sysBranchMapper;
    private final PeispatientMapper peispatientMapper;
    private final SellcustomerMapper sellcustomerMapper;
    private final NotificationmethodMapper notificationmethodMapper;
    private final PeispatientPhotoService peispatientPhotoService;
    private final PatienttypeService patienttypeService;
    private final CreatemealMapper createmealMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    @Autowired
    private LoadProperties loadProperties;
    private final ISysConfigService iSysConfigService;



    /**
     * 导引单数据打印
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getBillModelData(BillModelDataParam param) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<String> id = param.getIds();
        //分中心
        SysBranch branch = sysBranchMapper.selectOne(new LambdaQueryWrapper<SysBranch>()
                .eq(SysBranch::getBranchId,param.getId()));
        for (int i = 0; i < id.size(); i++) {
            // 存储导引单信息
            Map<String, Object> map = new HashMap<String, Object>();
            Peispatient peispatient = peispatientMapper.getInfoById(id.get(i));
            // 判断该体检者是否存在
            if (null == peispatient) {
                map.put("success", false);
                map.put("msg", "导引单打印失败：第 <font color='red'>" + (i + 1) + "</font> 个体检者不存在，已经被删除！");
                list.add(map);
                return list;
            }
            //通知方式
            String informWay="";
            if(peispatient.getIdInformway()!=null) {
                //通知方式（领取方式）
                Notificationmethod method = notificationmethodMapper.getInfoById(peispatient.getIdInformway());
                if(method!=null){
                    informWay= method.getMethodName();//发送方式名称
                }
                //个检/团检：0.个检 1.团检
            }else if(peispatient.getFUsecodehiden()!=null
                    &&peispatient.getFUsecodehiden()==0) {
                //个检没有选择发放方式的，默认个检发
                informWay="个检发";
            }
            map.put("informWay", informWay);
            //体检号
            map.put("patientCode", peispatient.getPatientcode());
            //订单号
            map.put("numorgresv", peispatient.getNumorgresv());
            //会员类型
            map.put("IdPatientclass", patienttypeService.getIdPatientClass(peispatient));
            //体检类别,0.上岗前 1.在岗期间 2.离岗时 3.离岗后 4.应急
            map.put("Medicaltype", peispatient.getMedicaltype());
            //体检套餐名称
            String tjtcmc = createmealMapper.getTjtcmc(peispatient.getIdTjtc());
            //分中心名字
            SysBranch sysBranch = sysBranchMapper.selectOne(new QueryWrapper<SysBranch>().eq("branch_id", SecurityUtils.getCId()));
            map.put("cid",sysBranch.getFzx());
            //婚姻id
            map.put("idMarriage", peispatient.getIdMarriage());
            map.put("tjtcmc", tjtcmc);
            map.put("picture", peispatientPhotoService.getPicture(peispatient));
            map.put("depart", StringUtils.isBlank(peispatient.getOrgDepart())?"":peispatient.getOrgDepart());
            map.put("dydremark", peispatient.getGuidancenote());
            map.put("name", peispatient.getPatientname());
            map.put("sex", ObjectUtils.isEmpty(peispatient.getIdSex())? "" : (peispatient.getIdSex() == 0?"男":"女"));
            map.put("age", null == peispatient.getAge()?0:peispatient.getAge());
            map.put("idcardno", StringUtils.isBlank(peispatient.getIdcardno())?"":peispatient.getIdcardno());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            map.put("medicaldate", null == peispatient.getMedicaldate()? "" : format.format(peispatient.getMedicaldate()));
            //我的客户表
            Sellcustomer sell = sellcustomerMapper.getInfoById(peispatient.getIdOrg());
            map.put("org", null==sell?"":sell.getKhdwmc());
            String[] tjlb = {"健康体检","职业体检","综合","复查"};
            map.put("type", null == peispatient.getIdExamtype()?"": tjlb[Integer.valueOf(peispatient.getIdExamtype())]);
            map.put("fzxAddress", null==branch?"":branch.getAddress());

            // 获取体检者对应的收费项目信息
            List<Map<String, Object>> eaxms = new ArrayList<Map<String,Object>>();
            //所有收费项目按科室、标本类型排列，无餐序视为餐中
            eaxms = guideSheetMapper.getModelItems(peispatient.getPatientcode());
            // 按照餐序排序(餐序排序1 科室排序2 标本类型3)
            sort(eaxms, "cx");
            map.put("success", true);


            List<Map<String, Object>> eaxmList = new ArrayList<Map<String,Object>>();
            /**有餐前项目的科室就放在最前，同时有餐后项目也不能拆成两部分*/
            Map<String,Map<String, Object>>eaxmMap=new HashMap<String, Map<String,Object>>();
            // String idks = "";//科室标志
            int s = eaxms.size();
            // String itemname = "";//同一科室 的所有收费项目名称
            Map<String, Object> ob =null;
            LinkedHashMap<String, String> link=null;
            for (int j = 0; j < s; j++) {
                Map<String, Object> exam=eaxms.get(j);
                //科室ID
                String idks=exam.get("idks")==null?"":exam.get("idks").toString();
                //样本类型ID
                String yblx=exam.get("idLabtype")==null?"":exam.get("idLabtype").toString();
                if(eaxmMap.get(idks)==null){
                    ob=new HashMap<String, Object>();
                    ob.put("ks", exam.get("ks"));
                    ob.put("cx", exam.get("cx"));
                    ob.put("bz", exam.get("bz")==null?"":exam.get("bz").toString());
                    link=new LinkedHashMap<String, String>();
                    link.put(yblx, exam.get("itemname")==null?"":exam.get("itemname").toString());
                    ob.put("yblx", link);
                    eaxmList.add(ob);
                    eaxmMap.put(idks, ob);
                }else{
                    ob=eaxmMap.get(idks);
                    ob.put("bz", ob.get("bz").toString()+(exam.get("BZ")==null?"":exam.get("BZ").toString()));
                    link=(LinkedHashMap<String, String>) ob.get("yblx");
                    if(link.get(yblx)==null){
                        link.put(yblx,exam.get("itemname")==null?"":exam.get("itemname").toString() );
                    }else{
                        link.put(yblx, link.get(yblx)+(exam.get("itemname")==null?"":(","+exam.get("itemname").toString())));
                    }
                }
            }
            StringBuilder itemname=null;
            for(Map<String, Object> ksmap:eaxmList){
                itemname=new StringBuilder();
                link=(LinkedHashMap<String, String>) ksmap.get("yblx");
                for(Map.Entry<String,String> entry:link.entrySet()){
                    if(itemname.length()==0){
                        itemname.append(entry.getValue());
                    }else{
                        itemname.append("</br>");
                        itemname.append(entry.getValue());
                    }
                }
                ksmap.put("itemname",itemname.toString());
            }

            map.put("items", eaxmList);
            list.add(map);
            if (param.getModel().equals("1")) {
                // 更新体检者打印导引单次数
                peispatient.setGuideSignleCount(peispatient.getGuideSignleCount()==null?1:(peispatient.getGuideSignleCount()+1));
                peispatientMapper.updateById(peispatient);
            }
        }
        return list;
    }

    public void sort(List<Map<String, Object>> targetList, final String sortField) {
        Collections.sort(targetList, new Comparator() {
            public int compare(Object obj1, Object obj2) {
                Map<String, Object> map1 = (Map<String, Object>) obj1;
                Map<String, Object> map2 = (Map<String, Object>) obj2;
                String m1 = null == map1.get(sortField)? "":map1.get(sortField).toString();//餐序为NULL会在最前
                String m2 = null == map2.get(sortField)? "":map2.get(sortField).toString();
                return m1.compareTo(m2);
            }
        });
    }


    /**
     * 根据id获取多位体检者
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getPatientsForID(BillModelDataParam param) {
        Map<String,Object> result=new HashMap<String, Object>();
        try {
            List<String> id = param.getIds();
            List<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
            for(String s:id){
                //体检者表
                Peispatient peispatient = peispatientMapper.getInfoById(s);
                HashMap<String,String>map=new HashMap<String, String>();
                map.put("sex", peispatient.getIdSex() == 0?"男":"女");
                map.put("age", peispatient.getAge()==null?"": String.valueOf(peispatient.getAge()));
                map.put("patientCode", peispatient.getPatientcode().substring(4));
                map.put("patientcode", peispatient.getPatientcode());
                map.put("patientname", peispatient.getPatientname());

                Integer count = peispatientfeeitemService.includesBreakfast(peispatient.getPatientcode());
                map.put("note", count > 0 ? "早餐" : "");

                String intId="";
                String idOrg = peispatient.getIdOrg();
                if(StringUtils.isNotEmpty(idOrg)){
                    //我的客户表
                    Sellcustomer customer = sellcustomerMapper.getInfoById(idOrg);
                    if(customer!=null&&customer.getIntId()!=null){
                        intId += customer.getIntId();
                    }
                }else {
                    intId += "-1";
                }
                map.put("intId", intId);

                data.add(map);
                if (param.getModel().equals("1")) {
                    // 条码打印
                    peispatient.setTmyd((null == peispatient.getTmyd()? 0 : peispatient.getTmyd())+1);
                    peispatientMapper.updateById(peispatient);
                }
            }

            result.put("data", data);
            BarcodeMarginConfig barcodeMarginConfig = iSysConfigService.getSysConfigObject(Constants.BARCODE_MARGIN_CONFIG, BarcodeMarginConfig.class);
            result.put("confirm", ObjectUtils.isNotEmpty(barcodeMarginConfig) ? barcodeMarginConfig : ReportConstants.barcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

