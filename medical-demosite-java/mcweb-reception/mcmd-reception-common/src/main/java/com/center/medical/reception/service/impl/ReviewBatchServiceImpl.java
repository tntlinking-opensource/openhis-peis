package com.center.medical.reception.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.utils.*;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.reception.bean.dto.ItemdataDto;
import com.center.medical.reception.bean.dto.RBGriddataDto;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.bean.param.RBatchParam;
import com.center.medical.reception.bean.param.ReviewBatchParam;
import com.center.medical.reception.bean.vo.RBListDataVo;
import com.center.medical.reception.bean.vo.ReviewBatchVo;
import com.center.medical.reception.dao.ReviewBatchMapper;
import com.center.medical.reception.dao.ReviewMapper;
import com.center.medical.reception.dao.ReviewProjectMapper;
import com.center.medical.reception.service.ReviewBatchService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.sellcrm.dao.CreateorderMapper;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.service.ISysBranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-02-02 15:30:33
 */
@Slf4j
@Service("reviewBatchService")
@RequiredArgsConstructor
public class ReviewBatchServiceImpl extends ServiceImpl<ReviewBatchMapper, Peispatient> implements ReviewBatchService {

    private final ReviewBatchMapper reviewBatchMapper;
    private final PeispatientMapper peispatientMapper;
    private final AreaMapper areaMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final Snowflake snowflake;
    private final PeispatientPhotoMapper peispatientPhotoMapper;
    private final CreateorderMapper createorderMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewProjectMapper reviewProjectMapper;
    private final ItemsMapper itemsMapper;
    private final ISysBranchService iSysBranchService;
    private final PeispatientfeeitemService peispatientfeeitemService;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public List<ReviewBatchVo> getItemsListData(ReviewBatchParam param) {
        return reviewBatchMapper.getItemsListData(param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return peispatientMapper.getInfoById(id);
    }

    ;


    /**
     * 职业复查获取数据
     *
     * @param param
     * @return
     */
    @Override
    public List<RBListDataVo> getListData(ReviewBatchParam param) {
        List<RBListDataVo> listData = reviewBatchMapper.getListData(param);
        for (RBListDataVo listDatum : listData) {
            Peispatient reviewPatient = getReviewPatient(listDatum.getInpatientno(), listDatum.getPatientcode(), listDatum.getCreatedate());
            if (reviewPatient != null) {
                listDatum.setReviewCode(reviewPatient.getPatientcode());
                listDatum.setReviewReg(reviewPatient.getFRegistered());
            }
        }
        return listData;
    }


    public Peispatient getReviewPatient(Object inpatientno, Object patientcode, Object createdate) {
        if (inpatientno == null) {
            //如果本次体检是第一次体检
            List<Peispatient> ps = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                    .orderByAsc("createDate")
                    .eq("inpatientno", patientcode));
            return ps.size() > 0 ? ps.get(0) : null;
        } else {
            List<Peispatient> ps = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                    .orderByAsc("createDate")
                    .gt("createDate", createdate)
                    .eq("inpatientno", inpatientno));
            return ps.size() > 0 ? ps.get(0) : null;
        }
    }


    /**
     * 职业复查保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean reviewBatch(RBatchParam param) {
        List<RBGriddataDto> itemsJa = param.getGriddata();

        //key:收费项目id  value：收费项目价格
        Map<String, Double> itemsPrice = new HashMap<String, Double>();
        for (int i = 0; i < itemsJa.size(); i++) {
            RBGriddataDto jo = itemsJa.get(i);
            itemsPrice.put(jo.getId(), jo.getPrice());
        }
        List<ItemdataDto> patientJa = param.getData();
        Calendar c = Calendar.getInstance();

        Date date = c.getTime();

        int year = c.get(Calendar.YEAR);
        String registerR = SecurityUtils.getUserNo();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //籍贯
        Area defaultArea = areaMapper.selectOne(new QueryWrapper<Area>().eq("is_default", 1));
        for (int i = 0; i < patientJa.size(); i++) {
            ItemdataDto jo = patientJa.get(i);
            String patientcode = jo.getPatientcode();
            Peispatient patient = peispatientMapper.getByPatientCode(patientcode);
            Peispatient reviewPatient = getReviewPatient(patient);
            //已完成登记的不修改
            if (reviewPatient != null) {
                if (reviewPatient.getFRegistered() != null & reviewPatient.getFRegistered() == 1) {
                    continue;
                }
                //体检者收费项目
                List<Peispatientfeeitem> pis = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", reviewPatient.getPatientcode())
                        .eq("change_item", 0)
                );
                double money = 0.0;
                for (Peispatientfeeitem pi : pis) {
                    //是否存在体检者收费项目ID
                    if (itemsPrice.containsKey(pi.getIdExamfeeitem())) {
                        //优惠价格
                        pi.setFactprice(itemsPrice.get(pi.getIdExamfeeitem()));
                    }
                    money += pi.getFactprice();
                }
                //更新价格
                peispatientfeeitemService.updateBatchById(pis);

                reviewPatient.setMoneyamount(money);
                //体检者费用
                PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new QueryWrapper<PeispatientChargeMain>()
                        .eq("patientcode", reviewPatient.getPatientcode()));
                if (pcm == null) {
                    PeispatientChargeMain pei = new PeispatientChargeMain(
                            "批量复查"
                            , money
                            , reviewPatient.getMoneyamountpaid()
                            , reviewPatient.getPatientcode());
                    peispatientChargeMainMapper.insert(pei);
                } else {
                    pcm.setMoneyamount(money);
                    peispatientChargeMainMapper.updateById(pcm);
                }
            } else {
                //生成体检者表
                reviewPatient = new Peispatient();
                reviewPatient.setIdOpendoctor(patient.getIdOpendoctor());
                reviewPatient.setDoctorapply(patient.getDoctorapply());
                reviewPatient.setPatientarchiveno(patient.getPatientarchiveno());

                // 获取下一个体检号
                String patientCode = "";
                do {
                    patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
                    //判断体检号是否存在
                } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                        .eq(Peispatient::getPatientcode, patientCode)) > 0);

                reviewPatient.setPatientcode(patientCode);
                reviewPatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
                //体检号生成人  生成时间
                reviewPatient.setTimingstartedat(date);
                reviewPatient.setGuidancenote2(registerR);
                reviewPatient.setIdcardno(patient.getIdcardno());
                if (!StringUtils.isBlank(reviewPatient.getIdcardno())
                        && patient.getCountreportoccupationxml() != null
                        && patient.getCountreportoccupationxml() == 1
                ) {
                    String card = reviewPatient.getIdcardno();
                    // 如果长度是15位
                    if (card.length() == 15) {
                        card = card.substring(0, 6) + "19" + card.substring(6) + "x";
                    }
                    //截取身份证的出生日期
                    reviewPatient.setBirthdate(IdcardUtil.getBirthDate(card));
                    // 年龄匹配
                    reviewPatient.setAge(IdcardUtil.getAgeByIdCard(card));
                    // 匹配性别
                    card = card.substring(card.length() - 2, card.length() - 1);
                    // 性别是否匹配
                    Integer strSex = (Integer.valueOf(card) & 1) != 0 ? 0 : 1;
                    reviewPatient.setIdSex(strSex);

                    Area area = areaMapper.selectOne(new QueryWrapper<Area>()
                            .eq("area_code", reviewPatient.getIdcardno().substring(0, 2)));
                    if (null == area) {
                        area = defaultArea;
                    }
                    reviewPatient.setIdResarea(area.getId());
                    reviewPatient.setResarea(area.getResarea());
                } else {
                    reviewPatient.setBirthdate(patient.getBirthdate());
                    reviewPatient.setIdSex(patient.getIdSex());
                    reviewPatient.setAge(patient.getAge());
                    reviewPatient.setIdResarea(patient.getIdResarea());
                    reviewPatient.setResarea(patient.getResarea());
                }
                reviewPatient.setNumorgresv(patient.getNumorgresv());
                reviewPatient.setPatientname(patient.getPatientname());
                reviewPatient.setInputCode(patient.getInputCode());
                reviewPatient.setIdOrgreservation(patient.getIdOrgreservation());
                reviewPatient.setIdOrg(patient.getIdOrg());
                reviewPatient.setOrgName(patient.getOrgName());
                reviewPatient.setOrgDepart(patient.getOrgDepart());
                reviewPatient.setIdMarriage(patient.getIdMarriage());
                reviewPatient.setMarriage(patient.getMarriage());
                reviewPatient.setNation(patient.getNation());
                reviewPatient.setIdNation(patient.getIdNation());
                reviewPatient.setAddress(patient.getAddress());
                reviewPatient.setPhone(patient.getPhone());
                reviewPatient.setFRegistered(0);
                reviewPatient.setIdExamtype("3");
                reviewPatient.setFExamstarted(0);
                reviewPatient.setFReadytofinal(0);
                savePicture(patient.getPatientcode(), getPicture(patient));
                reviewPatient.setInpatientno(patient.getInpatientno() == null ? patient.getPatientcode() : patient.getInpatientno());
                reviewPatient.setCountreportoccupationxml(patient.getCountreportoccupationxml());
                reviewPatient.setWorkDate(patient.getWorkDate());
                reviewPatient.setHarmDate(patient.getHarmDate());
                reviewPatient.setMedicaltype(patient.getMedicaltype());
                reviewPatient.setTrades(patient.getTrades());
                reviewPatient.setWorktypeId(patient.getWorktypeId());
                reviewPatient.setJhys(patient.getJhys());
                reviewPatient.setIdPatientclass(patient.getIdPatientclass());
                // 根据总工龄计算参加工作时间
                if (null != reviewPatient.getWorkDate()) {
                    reviewPatient.setZgl(ToolUtil.getMonthSpace(date
                            , ToolUtil.subTime(reviewPatient.getWorkDate())));
                }
                // 根据接害工龄计算从事该工种工作时间
                if (null != reviewPatient.getHarmDate()) {
                    reviewPatient.setJhgl(ToolUtil.getMonthSpace(date
                            , ToolUtil.subTime(reviewPatient.getHarmDate())));
                }
                reviewPatient.setMoneyamountpaid(0.0);
                reviewPatient.setFUsecodehiden(1);
                peispatientMapper.insert(reviewPatient);
                String idPayway = Constants.XJ;//现金
                //订单号
                String numorgresv = patient.getNumorgresv();
                if (numorgresv != null) {
                    Createorder order = createorderMapper.selectOne(new QueryWrapper<Createorder>()
                            .eq("ddh", numorgresv));
                    if (order != null) {
                        if (order.getReviewPayway() != null) {
                            idPayway = order.getReviewPayway();
                        }
                    }
                }
                //生成收费项目表
                Review r = reviewMapper.selectOne(new QueryWrapper<Review>().eq("patientcode", patientcode));
                //复查项目
                List<ReviewProject> rps = reviewProjectMapper.selectList(new QueryWrapper<ReviewProject>().eq("reviewId", r.getId()));
                double money = 0.0;
                int j = 0;
                for (; j < rps.size(); j++) {
                    ReviewProject rp = rps.get(j);
                    //体检者收费项目
                    Peispatientfeeitem pi = new Peispatientfeeitem();
                    pi.setIdPatient(patientCode);
                    pi.setIdExamfeeitem(rp.getItemsId());
                    Items item = itemsMapper.getInfoById(rp.getItemsId());
                    pi.setExamfeeitemName(item.getExamfeeitemName());
                    pi.setPrice(item.getUnitprice());
                    pi.setFactprice(itemsPrice.get(rp.getItemsId()));
                    pi.setIdPayway(idPayway);
                    pi.setSamplestatus("0");
                    pi.setFExaminated(0);
                    pi.setIdKs(item.getIdDepart());
                    pi.setChangeItem(0);
                    pi.setShortCode(reviewPatient.getShortCode());
                    pi.setFGiveup(0);
                    pi.setCount(1);
                    pi.setQty(j + 1);
                    pi.setFFeecharged(0);
                    pi.setFMarkFeereturn(0);
                    pi.setFDelayed(0);
                    pi.setSfjx(0);
                    pi.setIsMintc(0);
                    pi.setIsbx(0);
                    peispatientfeeitemMapper.insert(pi);
                    money = Arith.add(money, pi.getFactprice());
                }
                reviewPatient.setMoneyamount(money);
                peispatientMapper.updateById(reviewPatient);

                //补检
                List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                        .eq("f_registered", 1)
                        .eq("f_feecharged", 1)
                        .eq("id_patient", patientCode)
                        .eq("f_transferedhl7", 0));
                if (peispatientfeeitems.size() > 0) {
                    reviewPatient.setIdExamtype("2");
                }
                for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                    Peispatientfeeitem pi = new Peispatientfeeitem();
                    pi.setIdPatient(patientCode);
                    pi.setIdExamfeeitem(peispatientfeeitem.getIdExamfeeitem());
                    pi.setExamfeeitemName(peispatientfeeitem.getExamfeeitemName());
                    pi.setPrice(peispatientfeeitem.getPrice());
                    pi.setFactprice(0.0);
                    pi.setIdPayway(Constants.XJ);
                    pi.setSamplestatus("0");
                    pi.setFExaminated(0);
                    pi.setIdKs(peispatientfeeitem.getIdKs());
                    pi.setChangeItem(0);
                    pi.setShortCode(reviewPatient.getShortCode());
                    pi.setFGiveup(0);
                    pi.setCount(1);
                    pi.setQty(j + 1);
                    pi.setFFeecharged(0);
                    pi.setFMarkFeereturn(0);
                    pi.setFDelayed(0);
                    pi.setSfjx(0);
                    pi.setIsMintc(0);
                    pi.setIsbx(0);
                    peispatientfeeitemMapper.insert(pi);
                }

                //生成收费主表
                peispatientChargeMainMapper.insert(new PeispatientChargeMain(
                        "批量复查"
                        , reviewPatient.getMoneyamount()
                        , reviewPatient.getMoneyamountpaid()
                        , reviewPatient.getPatientcode()));
            }

        }
        return Boolean.TRUE;
    }


    public Peispatient getReviewPatient(Peispatient patient) {
        if (patient.getInpatientno() == null) {
            List<Peispatient> ps = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                    .last("LIMIT 10")
                    .orderByAsc("createDate")
                    .eq("inpatientno", patient.getPatientcode())
            );
            return ps.size() > 0 ? ps.get(0) : null;
        } else {
            List<Peispatient> ps = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                    .last("LIMIT 10")
                    .orderByAsc("createDate")
                    .gt("createDate", patient.getCreatedate())
                    .eq("inpatientno", patient.getInpatientno()));
            return ps.size() > 0 ? ps.get(0) : null;
        }
    }


    public String getPicture(Peispatient patient) {
        if (patient == null) return "";
        PeispatientPhoto photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>()
                .eq("patientcode", patient.getPatientcode()));
        return photo == null || photo.getPicture() == null ? "" : photo.getPicture();
    }

    public void savePicture(String patientcode, String picture) {
        PeispatientPhoto photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>()
                .eq("patientcode", patientcode));
        if (photo == null) {
            peispatientPhotoMapper.insert(new PeispatientPhoto(patientcode, picture));
        } else {
            photo.setPicture(picture);
            peispatientPhotoMapper.updateById(photo);
        }
    }
}

