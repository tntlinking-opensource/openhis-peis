package com.center.medical.abteilung.service.impl;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.*;
import com.center.medical.common.utils.*;
import com.center.medical.abteilung.bean.dto.*;
import com.center.medical.abteilung.bean.param.ModifyStatusParam;
import com.center.medical.abteilung.bean.param.SignInInspectionParam;
import com.center.medical.abteilung.bean.param.UpdateItemsDealParam;
import com.center.medical.abteilung.bean.vo.PopDataVo;
import com.center.medical.abteilung.bean.vo.ViewThirdPartyImagesVo;
import com.center.medical.abteilung.dao.SignInInspectionMapper;
import com.center.medical.abteilung.service.SignInInspectionService;
import com.center.medical.bean.dto.NursingRegistration;
import com.center.medical.bean.dto.NursingRegistrationDto;
import com.center.medical.bean.dto.PeispatientchargeDto;
import com.center.medical.bean.dto.PriceAndFactPriceDto;
import com.center.medical.bean.enums.AttachmentType;
import com.center.medical.bean.enums.FilePathConfigFlag;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.bean.vo.ThirdPartyImagesVo;
import com.center.medical.common.config.LoadProperties;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysConfig;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.bean.model.BaseWorktype;
import com.center.medical.data.bean.model.Harm;
import com.center.medical.data.bean.model.InspectCharge;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.dao.BaseWorktypeMapper;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.data.dao.InspectChargeMapper;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.data.service.InspectChargeService;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.dao.VisitMainMapper;
import com.center.medical.member.service.VisitMainService;
import com.center.medical.reception.bean.model.ReviewProject;
import com.center.medical.reception.dao.ReviewMapper;
import com.center.medical.reception.dao.ReviewProjectMapper;
import com.center.medical.reception.service.OutsideMainService;
import com.center.medical.sellcrm.bean.model.*;
import com.center.medical.sellcrm.dao.*;
import com.center.medical.service.*;
import com.center.medical.system.bean.model.SysDepartment;
import com.center.medical.system.config.SystemConfig;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysConfigMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysUserService;
import com.center.medical.system.service.SysDepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * QT体检者表(Peispatient)表服务实现类
 *
 * @author ay
 * @since 2023-01-15 16:06:44
 */
@Slf4j
@Service("preregistration")
@RequiredArgsConstructor
public class SignInInspectionServiceImpl extends ServiceImpl<SignInInspectionMapper, Peispatient> implements SignInInspectionService {

    private final SignInInspectionMapper signInInspectionMapper;
    private final ISysBranchService sysBranchService;
    private final PeispatientMapper peispatientMapper;
    private final PeispatienthistoryMapper peispatienthistoryMapper;
    private final MapperFacade mapperFacade;
    private final PeisorgreservationMapper peisorgreservationMapper;
    private final PeisorgreservationgroupMapper peisorgreservationgroupMapper;
    private final PeispatientChargeMainMapper peispatientChargeMainMapper;
    private final BaseWorktypeMapper baseWorktypeMapper;
    private final CreateorderMapper createorderMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewProjectMapper reviewProjectMapper;
    private final PeispatientfeeitemMapper peispatientfeeitemMapper;
    private final HarmMapper harmMapper;
    private final PeispatientPhotoMapper peispatientPhotoMapper;
    private final ComboexamitemMapper comboexamitemMapper;
    private final InspectChargeMapper inspectChargeMapper;
    private final SysUserMapper sysUserMapper;
    private final PeispatientchargeService peispatientchargeService;
    private final CreatemealMapper createmealMapper;
    private final ItemsMapper itemsMapper;
    private final MealanditemMapper mealanditemMapper;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ComboanditemMapper comboanditemMapper;
    private final CreatecomboMapper createcomboMapper;
    private final TempFeeitemMapper tempFeeitemMapper;
    private final DictpaywayMapper dictpaywayMapper;
    private final SysDeptMapper sysDeptMapper;
    private final RiskclientMapper riskclientMapper;
    private final SysConfigMapper sysConfigMapper;
    private final NationMapper nationMapper;
    private final AreaMapper areaMapper;
    private final SysBranchMapper sysBranchMapper;
    private final ISysUserService sysUserService;
    private final PeispatientarchiveService peispatientarchiveService;
    private final PeisStateService peisStateService;
    private final VisitMainMapper visitMainMapper;
    private final VisitMainService visitMainService;
    private final FailTotalVisitMapper failTotalVisitMapper;
    private final HandleNewProjectsMapper handleNewProjectsMapper;
    private final ISysBranchService iSysBranchService;
    private final SysDepartmentService sysDepartmentService;
    private final OutsideMainService outsideMainService;
    private final InspectChargeService inspectChargeService;
    private final ISysConfigService sysConfigService;
    private final AttachmentService attachmentService;
    private final SystemConfig systemConfig;
    private final ISysConfigService iSysConfigService;
    private final CodeCorrespondingService codeCorrespondingService;

    private final SurrenderDocumentsService surrenderDocumentsService;
    private final OutsidePictrueService outsidePictrueService;
    private final LoadProperties loadProperties;

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Peispatient> getList(PageParam<Peispatient> page, Peispatient param) {
        return signInInspectionMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatient getInfoById(String id) {
        return signInInspectionMapper.getInfoById(id);
    }

    /**
     * 体检者查询
     *
     * @param patientCode
     * @param autoFill
     * @param key
     * @return
     */
    @Override
    public HashMap getRecheckItems(String patientCode, String autoFill, String key) {
        //体检号补0
        if ("true".equals(autoFill)) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }

        HashMap map = new HashMap();
        // APP是线上体检号前缀
        // TODO: wait 线上体检好不能登记，暂时先注释掉
//        if (key != null && "1".equals(key) && patientCode.indexOf(Constants.ONLINE_PREFIX) != -1) {
//            throw new ServiceException("不是本地号码，不能完成登记。请前往网络登记窗口登记。");
//        }

        Peispatient peispatient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
        if (ObjectUtils.isNotEmpty(peispatient)) {
            if (ObjectUtils.isNotEmpty(peispatient.getFPaused()) && 1 == peispatient.getFPaused()) {
                throw new ServiceException("该体检者已经被禁检");
            } else {
                map.put("success", true);
            }
        } else {
            // 历史体检者
            Peispatienthistory peispatientHistory = peispatienthistoryMapper.selectOne(new QueryWrapper<Peispatienthistory>().eq("patientcode", patientCode));
            if (ObjectUtils.isEmpty(peispatientHistory)) {
                // 错误信息
                throw new ServiceException("该体检者不存在");
            } else {
                peispatient = mapperFacade.map(peispatientHistory, Peispatient.class);
                map.put("success", true);
                map.put("location", 1);
            }
        }

        // 团体信息
        Peisorgreservation vation = peisorgreservationMapper.getInfoById(peispatient.getIdOrgreservation());
        if (ObjectUtils.isNotEmpty(vation)) {
            Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());

            if (ObjectUtils.isNotEmpty(group)) {
                Dictpayway dictpayway = dictpaywayMapper.getInfoById(group.getIdPayway());
                group.setPayway(ObjectUtils.isNotEmpty(dictpayway) ? dictpayway.getPaywayName() : null);
                map.put("group", group);// 分组
                // 是否组类禁检
                if (group.getFGroupstarted() == 0 && group.getFGrouppaused() == 1) {
                    throw new ServiceException("该体检者所在分组已经被禁检");
                }
            }
            map.put("vation", vation);// 团体任务
        }
        // 体检者费用
        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", peispatient.getPatientcode()));
        if (ObjectUtils.isNotEmpty(pcm)) {
            peispatient.setMoneyamount(pcm.getMoneyamount());
            peispatient.setMoneyamountpaid(pcm.getMoneyamountpaid());
        }
        map.put("patientData", peispatient);
        map.put("jhysNames", getHarmStr(peispatient.getJhys()));
        if (StringUtils.isNotEmpty(peispatient.getWorktypeId())) {
            // 工种
            BaseWorktype worktype = baseWorktypeMapper.getInfoById(peispatient.getWorktypeId());
            if (ObjectUtils.isNotEmpty(worktype)) {
                map.put("workType", worktype.getTypeName());
            }
        }
        // 订单号
        String numorgresv = peispatient.getNumorgresv();
        if (ObjectUtils.isNotEmpty(numorgresv)) {
            Createorder order = createorderMapper.getInfoById(numorgresv);
            if (ObjectUtils.isNotEmpty(order)) {
                map.put("orderId", order.getId());
            }
        }

        // 判断是复查
        boolean isReview = false;
        boolean isbc = false;
        boolean hasReviewed = false;
        map.put("examfeeitemData", 0);
        Peispatient fcpatient = getReviewPatient(peispatient);
        if (ObjectUtils.isNotEmpty(fcpatient)) {
            //已经复查  ，弹出是否查看复查体检号
            map.put("examfeeitemData", 1);
            hasReviewed = true;
            map.put("fccode", fcpatient.getPatientcode());
        } else if (!peispatient.getIdExamtype().equals("0")
                && null != peispatient.getFIsrecheck()
                && peispatient.getFIsrecheck() == 1) {
            // 0: 未复查 1: 复查
            // 存在复查
            Review review = reviewMapper.selectOne(new QueryWrapper<Review>()
                    .eq("patientcode", patientCode).ne("is_delete", 1));
            // 不存在复查项目
            if (ObjectUtils.isEmpty(review)) {
//                map.put("examfeeitemData", 0);
            } else {
                if (reviewProjectMapper.selectList(new QueryWrapper<ReviewProject>().eq("review_id", review.getId())).size() == 0) {
                    //没有复查项目不认为是复查
//            		map.put("examfeeitemData", 0);
                } else {
                    if (ObjectUtils.isEmpty(fcpatient)) {
                        // 查询复查项目
                        map.put("examfeeitemData", 1);
                        isReview = true;
                        map.remove("group");
                    }
                }
            }
//            map.put("type", 0);
        }
        // 是否存在补检(客户)
        List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                .eq("f_registered", 1).eq("f_feecharged", 1)
                .eq("id_patient", patientCode).eq("f_transferedhl7", 0));

//        map.put("type", 1);
        if (peispatientfeeitems.size() > 0) {
            map.put("examfeeitemData", 1);
            isbc = true;
        } else {
//            map.put("examfeeitemData", 0);
        }
        if (hasReviewed) {
            map.put("type", 3);
        } else if (isReview && isbc) {
            map.put("type", 2);
        } else if (isReview && !isbc) {
            map.put("type", 0);
        } else if (!isReview && isbc) {
            map.put("type", 1);
            //查询是否是老系统体检号，是的话需要返回备注
            CodeCorresponding corresponding = codeCorrespondingService.getOne(new LambdaQueryWrapper<CodeCorresponding>()
                    .eq(CodeCorresponding::getNewCode, peispatient.getPatientcode()));
            if (ObjectUtils.isNotEmpty(corresponding)) {
                map.put("oldCodeNote", "补检,老系统体检号" + corresponding.getOldCode());
            }
        }
        return map;
    }


    public String getHarmStr(Object obj) {
        if (obj != null) {
            String[] jhyss = obj.toString().split(",");
            List<Harm> harms = harmMapper.selectList(new QueryWrapper<Harm>().in("id", jhyss));
            if (harms.size() == 0) {
                return StringUtils.EMPTY;
            } else {
                StringBuilder builder = new StringBuilder();
                for (Harm harm : harms) {
                    builder.append(harm.getHarmName() + "、");
                }
                return builder.substring(0, builder.length() - 1);
            }
        } else {
            return StringUtils.EMPTY;
        }
    }


    /**
     * 获取复查体检者
     *
     * @param patient
     * @return
     */
    public Peispatient getReviewPatient(Peispatient patient) {
        if (StringUtils.isEmpty(patient.getInpatientno())) {
            List<Peispatient> ps = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                    .orderByAsc("createDate")
                    .eq("inpatientno", patient.getPatientcode())
            );
            return ps.size() > 0 ? ps.get(0) : null;
        } else {
            List<Peispatient> ps = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                    .orderByAsc("createDate")
                    .gt("createDate", patient.getCreatedate())
                    .eq("inpatientno", patient.getInpatientno())
            );
            return ps.size() > 0 ? ps.get(0) : null;
        }
    }


    /**
     * 得到备单人员的信息
     *
     * @param patientCode
     * @param id
     * @return
     */
    @Override
    public HashMap getPatientData(String patientCode, String id) {
        patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        // 保存页面所需要的值
        HashMap map = new HashMap();
        // id存在说明是备单人员(预约)
        if (!StringUtils.isBlank(patientCode)) {
            // 从登记列表跳转的数据
            QueryWrapper<Peispatient> queryWrapper = new QueryWrapper<>();
            if ("-1".equals(patientCode)) {
                queryWrapper.eq("id", id);
            } else {
                queryWrapper.eq("patientcode", patientCode);
            }
            // 已登记的信息
            Peispatient peispatient = peispatientMapper.selectOne(queryWrapper);
            map.put("location", 0);
            // 体检者表不存在
            if (ObjectUtils.isEmpty(peispatient)) {

                // 设置条件
                QueryWrapper<Peispatienthistory> queryWrapper1 = new QueryWrapper<>();
                if ("-1".equals(patientCode)) {
                    queryWrapper1.eq("id", id);
                } else {
                    queryWrapper1.eq("patientcode", patientCode);
                }

                // 历史体检者
                Peispatienthistory peispatientHistory = peispatienthistoryMapper.selectOne(queryWrapper1);
                if (ObjectUtils.isEmpty(peispatientHistory)) {
                    // 错误信息
                    throw new ServiceException("体检者不存在");
                } else {
                    peispatient = mapperFacade.map(peispatientHistory, Peispatient.class);
                    map.put("location", 1);
                }
            }
            Peisorgreservationgroup group = null;
            Peisorgreservation vation = null;
            if (ObjectUtils.isNotEmpty(peispatient)) {
                PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new QueryWrapper<PeispatientChargeMain>()
                        .eq("patientcode", peispatient.getPatientcode()));
                if (ObjectUtils.isNotEmpty(pcm)) {
                    peispatient.setMoneyamount(pcm.getMoneyamount());
                    peispatient.setMoneyamountpaid(pcm.getMoneyamountpaid());
                    map.put("version", pcm.getVersion());
                }
                map.put("patientData", peispatient);
                map.put("picture", getPicture(peispatient));
                String ddId = "";
                if (!StringUtils.isBlank(peispatient.getIdOrgreservation())) {
                    vation = peisorgreservationMapper.getInfoById(peispatient.getIdOrgreservation());
                    // 体检套餐
                    if (ObjectUtils.isNotEmpty(vation)) {
                        group = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());
                        if (ObjectUtils.isNotEmpty(group) && group.getFGroupstarted() == 0 && group.getFGrouppaused() == 1) {
                            map.put("limit", 1);
                        }
                    }
                    // 备单没有保存相对应的收费项目
                    ddId = null == vation ? "" : vation.getDdh();//订单ID
                }
                if (StringUtils.isEmpty(ddId)) {
                    String numorgresv = peispatient.getNumorgresv();
                    if (ObjectUtils.isNotEmpty(numorgresv)) {
                        Createorder order = createorderMapper.selectOne(new QueryWrapper<Createorder>()
                                .eq("ddh", numorgresv));
                        if (ObjectUtils.isNotEmpty(order)) {
                            ddId = order.getId();
                        }
                    }
                }

                List<Map<String, Object>> itemData = getExamfeeByPatientCode(peispatient.getPatientcode(), "1");
                if (CollectionUtils.isEmpty(itemData)) {
                    // 关联备单分组的体检套餐下的收费项目
                    if (ObjectUtils.isNotEmpty(group)) {
                        itemData = getExamfeeitemData(peispatient.getIdTjtc(), ddId, group.getId());
                    }
                }
                //增加职业必检列
                if (CollectionUtils.isNotEmpty(itemData)) {

                    if ("1".equals(peispatient.getIdExamtype()) || "3".equals(peispatient.getIdExamtype())) {
                        for (Map<String, Object> im : itemData) {
                            im.put("zybj", "1");
                        }
                    } else if ("2".equals(peispatient.getIdExamtype())) {
                        List<Comboexamitem> eis = comboexamitemMapper.selectList(new QueryWrapper<Comboexamitem>()
                                .in("harm_id", peispatient.getJhys().split(",")).eq("medical_type", peispatient.getMedicaltype()));
                        HashMap<String, Comboexamitem> ceis = new HashMap<String, Comboexamitem>();
                        for (Comboexamitem cei : eis) {
                            ceis.put(cei.getExamId(), cei);
                        }
                        for (Map<String, Object> im : itemData) {
                            Object idExamfeeitem = im.get("idExamfeeitem");
                            List<InspectCharge> ics = inspectChargeMapper.selectList(new QueryWrapper<InspectCharge>().eq("charge_id", idExamfeeitem));
                            String zybj = "0";
                            for (InspectCharge ic : ics) {
                                if (ceis.get(ic.getInspectId()) != null) {
                                    zybj = "1";
                                    break;
                                }
                            }
                            im.put("zybj", zybj);
                        }
                    } else {
                        for (Map<String, Object> im : itemData) {
                            im.put("zybj", "0");
                        }
                    }
                }

                map.put("examfeeitemData", itemData);// 收费项目
                map.put("addItemData", getRegAddItemData(patientCode));//加项项目
                map.put("group", group);// 分组
                map.put("jhysn", StringUtils.isNotEmpty(peispatient.getJhys()) ? harmMapper.getHarmText(peispatient.getJhys().split(",")) : "");// 接害因素
                map.put("idOrder", ddId);//订单ID
                if (StringUtils.isNotEmpty(peispatient.getWorktypeId())) {
                    // 工种
                    BaseWorktype worktype = baseWorktypeMapper.getInfoById(peispatient.getWorktypeId());

                    if (ObjectUtils.isNotEmpty(worktype)) {
                        map.put("workType", worktype.getTypeName());
                        peispatient.setWorkTypeName(worktype.getTypeName());
                    }

                }
                //接害因素
                if (StringUtils.isNotEmpty(peispatient.getJhys())){
                    List<Harm> harm = harmMapper.selectList(new QueryWrapper<Harm>().in("id", peispatient.getJhys().split(",")));
                    List<String> HarmNames = harm.stream()
                            .map(Harm::getHarmName)
                            .collect(Collectors.toList());
                    peispatient.setHarmNames(String.join(",", HarmNames));
                }

                //体检类别
                if (StringUtils.isNotEmpty(peispatient.getMedicaltype())){
                    String tjlxName = Render.getMedicalType(peispatient.getMedicaltype());
                    peispatient.setMedicaltypeName(tjlxName);
                }

                SysUser qxUsers = sysUserMapper.selectUserByUserNo(peispatient.getIdOpendoctor());
                map.put("sellname", null == qxUsers ? "" : qxUsers.getUserName());// 开单医师名称
                // 查找收费信息退费金额
                List<Peispatientcharge> charges = peispatientchargeService.list(new QueryWrapper<Peispatientcharge>()
                        .eq("patientcode", peispatient.getPatientcode()).eq("is_delete", 0));
                Double tMoney = 0.00;
                for (Peispatientcharge peispatientCharge : charges) {
                    //==null按0.0处理
                    if (peispatientCharge.getMoneyamountpaid() != null && peispatientCharge.getMoneyamountpaid() < 0) {
                        tMoney += peispatientCharge.getMoneyamountpaid();
                    }
                }
                map.put("tMoney", tMoney);
                map.put("success", true);
            } else {
                throw new ServiceException("体检者不存在！");
            }

            return map;
        } else {
            if (!StringUtils.isBlank(id)) {
                return getPatientData(id, "-1");
            }
            // 错误信息
            throw new ServiceException("系统发生异常，请联系管理员！");
        }
    }


    public String getPicture(Peispatient patient) {
        if (ObjectUtils.isEmpty(patient)) {
            return "";
        }
        PeispatientPhoto photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>().eq("patientcode", patient.getPatientcode()));
        return photo == null || photo.getPicture() == null ? "" : photo.getPicture();
    }


    /**
     * 获取体检者收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    @Override
    public List<Map<String, Object>> getExamfeeByPatientCode(String patientCode, String type) {
        QueryWrapper<Peispatientfeeitem> and = new QueryWrapper<>();

        if ("0".equals(type)) {
            // 全部显示
        } else if ("1".equals(type)) {
            // 显示除去退项的
            and.eq("change_item", 0);
        } else if ("2".equals(type)) {
            // 显示退项的
            and.eq("change_item", 1);
        }
        //体检者收费项目表
        List<Peispatientfeeitem> list = peispatientfeeitemMapper.selectList(and.orderByAsc("qty").eq("id_patient", patientCode));
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", patientCode));
        //体检套餐ID
        String idtjtc = patient.getIdTjtc();

        Createmeal cm = null;
        Integer bxcount = null;
        if (StringUtils.isNotEmpty(idtjtc)) {
            cm = createmealMapper.getInfoById(idtjtc);
            bxcount = cm == null || cm.getKxsl() == null ? 0 : cm.getKxsl();
        }

        for (Peispatientfeeitem peispatientfeeitem : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            //查询收费项目
            Items item = itemsMapper.getInfoById(peispatientfeeitem.getIdExamfeeitem());
            if (ObjectUtils.isEmpty(item)){
                throw new ServiceException("收费项目:"+peispatientfeeitem.getExamfeeitemName()+"不存在！");
            }
            map.put("fReportalone", item.getFReportalone());
            map.put("id", peispatientfeeitem.getId());
            map.put("idExamfeeitem", peispatientfeeitem.getIdExamfeeitem());
            map.put("examfeeitemName", peispatientfeeitem.getExamfeeitemName());
            map.put("price", peispatientfeeitem.getPrice());
            map.put("factprice", peispatientfeeitem.getFactprice());
            Double endPrice = peispatientfeeitem.getFactprice();
            endPrice = (endPrice == null ? 0 : -endPrice);
            if (null != peispatientfeeitem.getChangeItem() && 1 == peispatientfeeitem.getChangeItem()) {
                endPrice = peispatientfeeitem.getEndtuiprice() == null ? 0d : peispatientfeeitem.getEndtuiprice();
            }
            map.put("endtuiprice", endPrice);
            map.put("actualprice", peispatientfeeitem.getActualprice() == null ? 0d : peispatientfeeitem.getActualprice());
            map.put("count", peispatientfeeitem.getCount());
            map.put("idPayway", peispatientfeeitem.getIdPayway());
            map.put("sfjx", peispatientfeeitem.getSfjx());
            map.put("jxys", peispatientfeeitem.getJxys());
            SysUser qxUsers = sysUserMapper.selectUserByUserNo(peispatientfeeitem.getJxys());
            map.put("name", null == qxUsers ? "" : qxUsers.getUserName());
            map.put("fRegistered", peispatientfeeitem.getFRegistered());
            map.put("changeItem", peispatientfeeitem.getChangeItem());
            map.put("fMarkFeereturn", peispatientfeeitem.getFMarkFeereturn());
            map.put("fFeecharged", peispatientfeeitem.getFFeecharged());
            map.put("fLabsendtolis", peispatientfeeitem.getFLabsendtolis());
            map.put("fExaminated", peispatientfeeitem.getFExaminated());
            map.put("fGiveup", peispatientfeeitem.getFGiveup());
            map.put("fDelayed", peispatientfeeitem.getFDelayed());
            map.put("fTransferedhl7", peispatientfeeitem.getFTransferedhl7());
            map.put("isMintc", peispatientfeeitem.getIsMintc());
            map.put("idKs", peispatientfeeitem.getIdKs());
            //科室名称
            SysDepartment sysDepartment = sysDepartmentService.getInfoById(peispatientfeeitem.getIdKs());
            map.put("ksmc", ObjectUtils.isNotEmpty(sysDepartment) ? sysDepartment.getName() : "");
            map.put("feechargetime", peispatientfeeitem.getFeechargetime());
            map.put("bxcount", bxcount);
            map.put("idDoctorreg", peispatientfeeitem.getIdDoctorreg());
            map.put("doctorregR", peispatientfeeitem.getDoctorregR());
            //体检者收费项目ID
            Items it = itemsMapper.getInfoById(peispatientfeeitem.getIdExamfeeitem());
            if (ObjectUtils.isNotEmpty(it)) {
                map.put("fFeechargedinttrans", it.getXb());// 性别
            }
            map.put("createdate", peispatientfeeitem.getCreatedate());
            map.put("qjr", peispatientfeeitem.getQjr());
            map.put("cjr", peispatientfeeitem.getCjr());
            map.put("bjr", peispatientfeeitem.getBjr());
            map.put("qjsj", peispatientfeeitem.getQjsj());
            map.put("cjsj", peispatientfeeitem.getCjsj());
            map.put("bjsj", peispatientfeeitem.getBjsj());
            map.put("qty", peispatientfeeitem.getQty());
            map.put("jjr", peispatientfeeitem.getJjr());
            map.put("jjrqm", peispatientfeeitem.getJjrqm());
            map.put("jjsj", peispatientfeeitem.getJjsj());
            map.put("sfjj", peispatientfeeitem.getSfjj());
            map.put("feeitemdesc", peispatientfeeitem.getFeeitemdesc());
            map.put("samplemsgfromlis", peispatientfeeitem.getSamplemsgfromlis());
            map.put("giveupnotelet", peispatientfeeitem.getGiveupnotelet());
            //备选
            if (peispatientfeeitem.getIsbx() != null
                    && peispatientfeeitem.getIsbx() == 1) {
                if (ObjectUtils.isNotEmpty(cm)) {
                    //普通套餐与收费项目关联表
                    Mealanditem mai = mealanditemMapper.selectOne(new QueryWrapper<Mealanditem>()
                            .eq("tcid", idtjtc).eq("sfxmid", peispatientfeeitem.getIdExamfeeitem()));
                    if (ObjectUtils.isNotEmpty(mai)) {
                        map.put("isbx", mai.getSfbx());
                        map.put("group", mai.getItemGroup());
                        map.put("groupType", mai.getGroupType());
                    }
                }
            }
            items.add(map);
        }
        return items;
    }


    /**
     * 获取体检者收费项目
     *
     * @param tcid
     * @param idOrder
     * @param idGroup
     * @return
     */
    @Override
    public List getExamfeeitemData(String tcid, String idOrder, String idGroup) {
        // 获取套餐下的收费项目
        List<HashMap<String, String>> items = getExamfeeitem(tcid);
        Dictpayway payWay = null;
        String idOrgreservationgroup = "";
        String orgreservationgroupname = "";
        if (StringUtils.isBlank(idOrder)) {
            payWay = dictpaywayMapper.selectOne(new QueryWrapper<Dictpayway>().eq("payway_name", "现金"));
        } else {
            //体检者团体任务
            Peisorgreservation vation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", idOrder));
            if (ObjectUtils.isNotEmpty(vation)) {
                //体检者任务分组
                List<Peisorgreservationgroup> groups = peisorgreservationgroupMapper.selectList(new QueryWrapper<Peisorgreservationgroup>()
                        .eq("id_examsuite", tcid).eq("id_orgreservation", vation.getId()));

                payWay = CollectionUtils.isEmpty(groups)? null : dictpaywayMapper.getInfoById(groups.get(0).getIdPayway());
                // 获取套餐对应的分组
                idOrgreservationgroup = CollectionUtils.isEmpty(groups) ? null : groups.get(0).getId();
                orgreservationgroupname = CollectionUtils.isEmpty(groups) ? null : groups.get(0).getOrgreservationgroupname();
            }
        }
        List list = new ArrayList();
        Boolean isMakeGb = false;
        int size = items.size();
        for (int j = 0; j < size; j++) {
            HashMap<String, String> mm = items.get(j);
            HashMap map = new HashMap();
            // 收费项目id
            map.put("idExamfeeitem", mm.get("idExamfeeitem"));
            // 收费项目名称
            map.put("examfeeitemName", mm.get("examfeeitemName"));
            // 原始价格
            map.put("price", mm.get("price"));

            Object[] oa = getFactPrice(mm, size, j, isMakeGb);
            if (!isMakeGb) {
                isMakeGb = (Boolean) oa[1];
            }
            map.put("factprice", oa[0]);

            // 优惠价格
            // map.put("factprice", mm.get("factprice"));
            // 数量
            map.put("count", 1);
            // 收费方式
            map.put("idPayway", payWay == null ? null : payWay.getId());
            // 科室
            map.put("idKs", mm.get("idKs"));
            //是否加项
            map.put("sfjx", 0);
            //换项
            map.put("changeItem", 0);
            // 弃检
            map.put("fGiveup", 0);
            // 迟检
            map.put("fDelayed", 0);
            //是否是最小套餐：0不是 1是
            map.put("isMintc", 1);
            // 是否备选
            map.put("isbx", mm.get("isbx"));
            // 备选数量
            map.put("bxcount", mm.get("bxcount"));
            // 登记人ID
            map.put("idDoctorreg", SecurityUtils.getUserNo());
            SysUser user = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
            //登记员(冗余)
            map.put("doctorregR", null == user ? "" : user.getUserName());
            //任务分组ID
            map.put("idOrgreservationgroup", idOrgreservationgroup);
            //分组名称
            map.put("orgreservationgroupname", orgreservationgroupname);
            //下载标志  0或NULL未下载   1已下载 2更新
            map.put("fFeechargedinttrans", mm.get("idSex"));
            //序号
            map.put("qty", mm.get("qty"));
            map.put("group", mm.get("group"));
            map.put("groupType", mm.get("groupType"));
            list.add(map);
        }

        return list;
    }


    /**
     * 获取登记页面最小套餐收费项目明细
     *
     * @param tcid
     * @return
     */
    @Override
    public List<HashMap<String, String>> getExamfeeitem(String tcid) {
        List list = new ArrayList();
        // 判断是否为空
        if (StringUtils.isBlank(tcid)) {
            return list;
        }
        // 根据套餐id获取最小套餐下的收费项目
        List<Comboanditem> minPackagesList = comboanditemMapper.selectList(new QueryWrapper<Comboanditem>()
                .orderByAsc("createdate").eq("tcid", tcid).eq("is_delete", 0));
        // 判断是否存在
        if (CollectionUtils.isNotEmpty(minPackagesList) && minPackagesList.size() > 0) {
            // 最小套餐
            Createcombo createCombo = createcomboMapper.getInfoById(tcid);
            int size = minPackagesList.size();
            for (int i = 0; i < size; i++) {
                Comboanditem cai = minPackagesList.get(i);
                Items items = itemsMapper.getInfoById(cai.getSfxmid());
                if (ObjectUtils.isNotEmpty(items)) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    // 收费项目id
                    map.put("idExamfeeitem", items.getId());
                    // 收费项目名称
                    map.put("examfeeitemName", items.getExamfeeitemName());
                    // 原始价格
                    map.put("price", String.valueOf(items.getUnitprice()));
                    map.put("factprice", (i + 1) == size ? (null == createCombo.getZhjg() ? 0 : createCombo.getZhjg()) : 0);
                    // 科室
                    map.put("idKs", items.getIdDepart());
                    // 是否备选
                    map.put("isbx", StringUtils.isBlank(minPackagesList.get(i).getSfbx()) ? "0" : minPackagesList.get(i).getSfbx());
                    // 备选数量
                    map.put("bxcount", createCombo.getSl());
                    // 折后价格
                    map.put("zhjg", createCombo.getZhjg());
                    map.put("idSex", items.getXb());
                    map.put("qty", i + 1 + "");
                    list.add(map);
                }
            }
        }

        // 普通套餐
        //按是否备选排序(是否备选不会为空)
        List<Mealanditem> mealItems = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>()
                .orderByAsc("item_sort").eq("tcid", tcid));
        // 判断是否存在
        if (ObjectUtils.isNotEmpty(mealItems) && mealItems.size() > 0) {
            Createmeal createMeal = createmealMapper.getInfoById(tcid);
            int size = mealItems.size();
            // 可选数量
            Long kxsl = Long.valueOf(createMeal.getKxsl());
            // 分组金额
            Double groupPrice = createMeal.getGroupPrice();
            double bxjg = 0.0;//所有备选项目价格
            if (ObjectUtils.isNotEmpty(kxsl) && ObjectUtils.isNotEmpty(groupPrice)) {
                bxjg += kxsl * groupPrice;//所有组间选价格（更新前的老套餐没有）
            }
            Set<Integer> groups = new HashSet<Integer>();//组内选组去重
            for (int i = 0; i < size; i++) {
                Mealanditem mai = mealItems.get(i);
                // 是否必选：0或null.否 1.是
                if ("1".equals(mai.getSfbx())) {
                    //分组类型：0.组内选 1.组间选 2.组任选
                    Integer groupType = mai.getGroupType();
                    if (groupType == null) {//兼容老套餐
                        bxjg = Arith.add(bxjg, mai.getPrice());
                    } else if (groupType == 0) {
                        Integer group = mai.getItemGroup();//组内选，同一组所有项目价格相同
                        if (groups.contains(group)) {

                        } else {
                            groups.add(group);
                            bxjg = Arith.add(bxjg, mai.getPrice());
                        }
                    }
                }
            }
            //折后价格 - 所有备选项目价格
            double fbxjg = createMeal.getZhjg() - bxjg;
            for (int i = 0; i < size; i++) {
                Items items = itemsMapper.getInfoById(mealItems.get(i).getSfxmid());

                if (ObjectUtils.isNotEmpty(items)) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    // 收费项目id
                    map.put("idExamfeeitem", items.getId());
                    // 收费项目名称
                    map.put("examfeeitemName", items.getExamfeeitemName());
                    // 原始价格
                    map.put("price", String.valueOf(items.getUnitprice()));
                    map.put("factprice", (i + 1) == size ? createMeal.getZhjg() : 0);
                    // 科室
                    map.put("idKs", items.getIdDepart());
                    // 是否备选
                    Integer isbx = Objects.isNull(mealItems.get(i).getSfbx()) ? 0 : mealItems.get(i).getSfbx();
                    map.put("isbx", isbx);
                    // 备选数量
                    map.put("bxcount", String.valueOf(createMeal.getKxsl()));
                    // 折后价格
                    if (isbx == 1) {//如果是备选
                        map.put("zhjg", mealItems.get(i).getPrice());
                    } else {
                        map.put("zhjg", fbxjg);
                    }
                    // map.put("zhjg", createMeal.getZhjg());
                    map.put("idSex", items.getXb());
                    map.put("qty", mealItems.get(i).getItemSort() == null ? null : mealItems.get(i).getItemSort().toString());
                    map.put("group", mealItems.get(i).getItemGroup() == null ? null : mealItems.get(i).getItemGroup().toString());
                    map.put("groupType", mealItems.get(i).getGroupType() == null ? null : mealItems.get(i).getGroupType().toString());
                    list.add(map);
                }
            }
        }

        return list;
    }


    public List<Map<String, Object>> getRegAddItemData(String patientcode) {
        // 科室临时加项表
        List<TempFeeitem> tfs = tempFeeitemMapper.selectList(new QueryWrapper<TempFeeitem>()
                .eq("patientcode", patientcode).eq("is_delete", 0)
        );

        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        for (TempFeeitem tf : tfs) {
            map = new HashMap<String, Object>();
            map.put("tempId", tf.getId());
            map.put("idExamfeeitem", tf.getItemId());
            //收费项目表
            Items it = itemsMapper.getInfoById(tf.getItemId());
            map.put("examfeeitemName", it.getExamfeeitemName());
            map.put("price", it.getUnitprice());
            map.put("factprice", tf.getPrice());
            map.put("idPayway", "1");
            map.put("sfjx", "1");
            SysUser qxUsers = sysUserMapper.selectUserByUserName(tf.getDoctorUsername());
            map.put("jxys", qxUsers.getUserNo());
            map.put("name", qxUsers.getUserName());
            map.put("f_registered", 0);
            map.put("changeItem", 0);
            map.put("fMarkFeereturn", 0);
            map.put("f_feecharged", 0);
            map.put("fLabsendtolis", 0);
            map.put("fExaminated", 0);
            map.put("fGiveup", 0);
            map.put("fDelayed", 0);
            map.put("isMintc", 0);
            map.put("idKs", it.getIdDepart());
            map.put("fFeechargedinttrans", it.getXb());// 性别
            map.put("createdate", tf.getCreatedate());
            map.put("feeitemdesc", tf.getRemarks());
            map.put("count", 1);
            items.add(map);
        }
        return items;
    }


    public Object[] getFactPrice(HashMap<String, String> map, int size, int index, boolean isMakeGb) {
        Object[] result = {null, false};
        if ("1".equals(map.get("isbx"))) {
            result[0] = map.get("zhjg");
        } else {
            boolean isFunction = false;
            String idKs = map.get("idKs");
            if (StringUtils.isNotEmpty(idKs)) {
                SysDept dept = sysDeptMapper.getByDeptNo(idKs);
                //是否为功能部门0为非功能科室1为功能科室
                if (dept != null && dept.getIsFunction() != null && "1".equals(dept.getIsFunction())) {
                    isFunction = true;
                }
            }

            // 【个检报告工本费】
//             if (map.get("examfeeitemName").equals("个检报告工本费")) {
            if (!isFunction && !isMakeGb) {//不是功能科室
                result[0] = map.get("zhjg");
                result[1] = true;
            } else {
                // 套餐中不存在【个检报告工本费】就把折扣价放在最后一个收费项目
                if ((index + 1) == size && !isMakeGb) {
                    result[0] = map.get("zhjg");
                } else {
                    result[0] = "0";
                }
            }
        }
        return result;
    }


    /**
     * 检查危急值
     *
     * @param patientcode
     * @return
     */
    @Override
    public String checkDanger(String patientcode) {
        // 高危人员管理表
        List<Riskclient> riskclients = riskclientMapper.selectList(new LambdaQueryWrapper<Riskclient>().eq(Riskclient::getTjid, patientcode).isNull(Riskclient::getYwclr));
        if (CollectionUtils.isEmpty(riskclients)) {
            return "0";
        } else {
            // 业务处理人用户名等于空
            return "1";
        }
    }

    /**
     * 交单
     *
     * @param patientCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean surrender(List<String> patientCode) {
        for (String code : patientCode) {
            Peispatient patient = peispatientMapper.getByPatientCode(code);
            if (ObjectUtils.isEmpty(patient)) {
                throw new ServiceException("交单失败，体检者信息不存在！");
            }
            patient.setCountreportcompare(1);
            peispatientMapper.updateById(patient);
            //添加交单记录
            SurrenderDocuments surrenderDocuments = surrenderDocumentsService.getByPatientCode(code);
            if (ObjectUtils.isEmpty(surrenderDocuments)) {
                surrenderDocuments = new SurrenderDocuments(patient.getHospitalcode(), patient.getPatientname(), patient.getPatientcode());
            }
            surrenderDocuments.setSubmitId(SecurityUtils.getUserNo());
            surrenderDocuments.setPresenter(SecurityUtils.getUsername());
            surrenderDocuments.setSubmitdate(new Date());
            surrenderDocumentsService.saveOrUpdate(surrenderDocuments);
        }
        return Boolean.TRUE;
    }


    /**
     * 保存或更新体检者信息和创建档案
     *
     * @param param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdatePatientData(SignInInspectionParam param) throws Exception {
        Peispatient peispatient = mapperFacade.map(param.getFormdata(), Peispatient.class);
        if (StringUtils.isNotEmpty(peispatient.getIdcardno())) {
            // TODO wait 等待完善其他证件号的校验
            switch (peispatient.getCountreportoccupationxml()) {
                case 1:
                    if (!IdcardUtil.isValidCard(peispatient.getIdcardno())) {
                        throw new ServiceException("身份证号输入不合法");
                    }
                default:

            }
            peispatient.setIdcardno(peispatient.getIdcardno().toUpperCase());
        }
        PeispatientPhoto photo = null;
        if (StringUtils.isNotEmpty(peispatient.getPatientcode())) {
            photo = peispatientPhotoMapper.selectOne(new QueryWrapper<PeispatientPhoto>()
                    .eq("patientcode", peispatient.getPatientcode()));
        }

        if (StringUtils.isBlank(peispatient.getHospitalcode())) {
            //获取默认的cid
            peispatient.setHospitalcode(sysBranchMapper.getDefaultCid());
        }
        peispatient.setFPaused(0);
        peispatient.setDateregister(null);//页面上会多8小时
        // 其他字段保存
        SiFormdataDto mData = param.getFormdata();
        // 登记员名称
        peispatient.setDoctorreg(SecurityUtils.getUsername());
        // 套餐名称
        Createmeal createMeal = createmealMapper.getInfoById(mData.getIdTjtc());
        String tcName = "";
        if (ObjectUtils.isNotEmpty(createMeal)) {
            tcName = createMeal.getTjtcmc();
        } else {
            //最小套餐
            Createcombo createCombo = createcomboMapper.getInfoById(mData.getIdTjtc());
            if (ObjectUtils.isNotEmpty(createCombo))
                tcName = createCombo.getTjtcmc();
        }
        peispatient.setExamsuiteName(tcName);
        // 开单医师
        SysUser qxUsers = null;
        if (null == mData.getIdOpendoctor() || StringUtils.isBlank(mData.getIdOpendoctor())) {
            peispatient.setIdOpendoctor(SecurityUtils.getUserNo());
            qxUsers = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
        } else {
            qxUsers = sysUserMapper.selectUserByUserNo(mData.getIdOpendoctor());
        }
        peispatient.setDoctorapply(null == qxUsers ? "" : qxUsers.getUserName());
        // 订单号
        String orderId = mData.getDdh();
        Createorder createOrder = createorderMapper.getInfoById(orderId);
        peispatient.setNumorgresv(null == createOrder ? "-1" : createOrder.getDdh());
        // 团体id
        String idOrg = mData.getIdOrg();
        peispatient.setIdOrg(idOrg.equals("null") ? "" : idOrg);
        peispatient.setOrgName(mData.getOrgName().equals("null") ? "" : mData.getOrgName());
        Peisorgreservation vation = peisorgreservationMapper.selectOne(new QueryWrapper<Peisorgreservation>().eq("ddh", orderId));
        // 任务ID
        peispatient.setIdOrgreservation(null == vation ? "" : vation.getId());
        // 备单(不再备单列表的团体客户)
        peispatient.setFIsforprepare(null == vation ? 0 : 1);
        // 分组ID
        peispatient.setIdOrgreservationgroup(mData.getIdOrgreservationgroup());
        // 婚姻
        peispatient.setMarriage(MarriageType.getName(mData.getIdMarriage()));
        // 民族
        Nation nation = nationMapper.getInfoById(mData.getIdNation());
        peispatient.setNation(null == nation ? "" : nation.getName());
        // 日期多8小时处理
        // 体检日期
        peispatient.setMedicaldate(subTime(peispatient.getMedicaldate()));
        peispatient.setBirthdate(subTime(peispatient.getBirthdate()));
        // 根据总工龄计算参加工作时间
        if (0 != peispatient.getZgl() || null == peispatient.getWorkDate()) {
            peispatient.setWorkDate(getDateForMonth(peispatient.getZgl()));
        } else {
            peispatient.setZgl(getMonthSpace(new Date(), subTime(peispatient.getWorkDate())));
        }
        // 根据接害工龄计算从事该工种工作时间
        if (0 != peispatient.getJhgl() || null == peispatient.getHarmDate()) {
            peispatient.setHarmDate(getDateForMonth(peispatient.getJhgl()));
        } else {
            peispatient.setJhgl(getMonthSpace(new Date(), subTime(peispatient.getHarmDate())));
        }
        // 判断身份证、性别、年龄是否相符
        if (!StringUtils.isBlank(peispatient.getIdcardno()) && peispatient.getCountreportoccupationxml() != null
                && peispatient.getCountreportoccupationxml() == 1) {
            String card = peispatient.getIdcardno();
            // 如果长度是15位
            if (card.length() == 15) {
                card = card.substring(0, 6) + "19" + card.substring(6) + "x";
            }
            //生日匹配
            peispatient.setBirthdate(IdcardUtil.getBirthDate(card));
            // 年龄匹配
            peispatient.setAge(IdcardUtil.getAgeByIdCard(card));
            // 匹配性别
            card = card.substring(card.length() - 2, card.length() - 1);
            // 性别是否匹配
            Integer strSex = (Integer.valueOf(card) & 1) != 0 ? 0 : 1;
            if (!strSex.equals(peispatient.getIdSex())) {
                throw new ServiceException("保存失败：该体检者的身份证号与性别不匹配");
            }
            peispatient.setIdSex(strSex);

            //导入名单的时候或者完成登记,用areacode匹配区域
            String areaCode = peispatient.getIdcardno().substring(0, 2);
            //籍贯表
            Area area = areaMapper.selectOne(new QueryWrapper<Area>().eq("areaCode", areaCode));
            if (ObjectUtils.isNotEmpty(area)) {
                peispatient.setIdResarea(area.getId());
                peispatient.setResarea(area.getResarea());
            }
        }
        // 判断是否存在团检任务中
        // 判断人员信息是否重复添加
        if (!StringUtils.isBlank(peispatient.getIdOrg())
                && !"3".equals(peispatient.getIdExamtype())
                && StringUtils.isNotEmpty(peispatient.getIdOrgreservationgroup())) {//复查 和没分组的不判断
            Peisorgreservationgroup group = peisorgreservationgroupMapper.getInfoById(peispatient.getIdOrgreservationgroup());
            //可重复
            if (!(group != null && group.getIdPatientclass2() != null && group.getIdPatientclass2().intValue() == 1)) {
                QueryWrapper<Peispatient> and = new QueryWrapper<>();
                // 身份证号
                if (!StringUtils.isBlank(peispatient.getIdcardno())) {
                    and.eq("idcardno", peispatient.getIdcardno());
                } else {
                    // 性别
                    and.eq("idSex", peispatient.getIdSex());
                    // 年龄
                    and.eq("age", peispatient.getAge());
                    // 电话
                    if (!StringUtils.isBlank(peispatient.getPhone())) {
                        and.eq("phone", peispatient.getPhone());
                    } else {
                        and.isNull("phone");
                    }
                    // 部门
                    if (!StringUtils.isBlank(peispatient.getOrgDepart())) {
                        and.eq("orgDepart", peispatient.getOrgDepart());
                    } else {
                        and.isNull("orgDepart");
                    }
                    // 工种
                    if (!StringUtils.isBlank(peispatient.getTrades())) {
                        and.eq("trades", peispatient.getTrades());
                    } else {
                        and.isNull("trades");
                    }
                    // 姓名
                    and.eq("patientname", peispatient.getPatientname());
                }
                // 不是复查
                if (!"3".equals(peispatient.getIdExamtype())) {
                    and.eq("idExamtype", peispatient.getIdExamtype());
                }
                // 不与自身体检号比较
                if (!StringUtils.isBlank(peispatient.getPatientcode())) {
                    and.ne("patientcode", peispatient.getPatientcode());
                } else if (!StringUtils.isBlank(peispatient.getId())) {
                    and.ne("id", peispatient.getId());
                }
                Createorder order = createorderMapper.getInfoById(null == vation ? "" : vation.getDdh());
                if (ObjectUtils.isNotEmpty(order)) {
                    Peispatient peispatientNew = peispatientMapper.selectOne(
                            and.eq("numorgresv", Integer.valueOf(order.getDdh()))
                                    .eq("idTjtc", peispatient.getIdTjtc()));
                    // 判断是否存在已经导入
                    if (ObjectUtils.isNotEmpty(peispatientNew)) {
                        throw new ServiceException("该体检者 " + peispatient.getPatientname() + " 在备单中已经存在，不能再进行操作！");
                    }
                }
            }
        }

        // 中间库状态 -1:保存 0：第一次登记 1：重新登记 2:增项
        int sataus = -1;

        Peispatient pNew = peispatientMapper.getByPatientCode(peispatient.getPatientcode());
        // 是否预约
        if (0 == param.getIntReservation()) {
            // 登记后不可以预约
            if (ObjectUtils.isNotEmpty(pNew)) {
                if (ObjectUtils.isNotEmpty(pNew.getFRegistered()) && 1 == pNew.getFRegistered()) {
                    throw new ServiceException("完成预约失败：该体检者 " + peispatient.getPatientname() + " 已经登记不可以预约！");
                }
            }
            peispatient.setFIsforreserve(1);
        } else if (1 == param.getIntReservation()) {
            // 登记后不可以再次登记
            if (ObjectUtils.isNotEmpty(pNew)) {
                if (null != pNew.getFRegistered() && 1 == pNew.getFRegistered()) {
                    throw new ServiceException("完成登记失败：该体检者 " + peispatient.getPatientname() + " 已经登记不可以再次登记！");
                }
            }
            if (vation != null && vation.getFFinished() != null && vation.getFFinished().intValue() == 1) {
                throw new ServiceException("完成登记失败：该体检者的订单已结束，不可以登记！");
            }
            // 判断是否已登记过
            if (
                //null != peispatient.getDateregister() &&
                    !StringUtils.isBlank(peispatient.getPatientcode())) {
                // 已登记过
                sataus = 1;
            } else if (null == peispatient.getDateregister() && null == peispatient.getFRegistered()) {
                // 第一次登记
                sataus = 0;
            }
            // 登记日期
            peispatient.setDateregister(new Date());
            //体检时间  完成登记时，体检时间等于登记时间
            peispatient.setMedicaldate(peispatient.getDateregister());

            //记录当前复查次数
            int counterreportprinted = 0;
            if (StringUtils.isNotEmpty(peispatient.getInpatientno())) {
                counterreportprinted = peispatientMapper.selectList(new QueryWrapper<Peispatient>()
                        .eq("inpatientno", peispatient.getInpatientno())
                        .eq("f_registered", 1)//完成登记操作，不用判断时间,登记前肯定是未登记状态，不用判断不是当前号码
                ).size() + 1
                        + ("3".equals(peispatientMapper.selectOne(new QueryWrapper<Peispatient>()
                        .eq("patientcode", peispatient.getInpatientno())).getIdExamtype()
                ) ? 1 : 0)
                ;
            } else if ("3".equals(peispatient.getIdExamtype())) {//如果手动调成复查，算作第一次
                counterreportprinted = 1;
            }
            peispatient.setCounterreportprinted(counterreportprinted);
            peispatient.setFRegistered(1);
        } else {
            if (null != pNew) {
                if (null != pNew.getFRegistered() && 1 == pNew.getFRegistered()) {
                    sataus = 2;
                }
            }
        }

        // 总检锁定、体检状态开始，项目不可操作
        if (null != pNew && (((null != pNew.getFFinallocked() && pNew.getFFinallocked() == 1)
                || (null != pNew.getJktjzt() && pNew.getJktjzt() > 0))
                || ((null != pNew.getIdGuidenurse() && pNew.getIdGuidenurse() == 1)
                || (null != pNew.getZytjzt() && pNew.getZytjzt() > 0)))) {
            throw new ServiceException("保存失败：该体检者 " + peispatient.getPatientname() + " 总检已经锁定或者开始，不可以操作！");
        }

        // 绑定档案
        String bindResult = bingIArchive(peispatient, mData, false);
        String patientarchiveno = "";
        if (bindResult.indexOf("success") == 0) {
            patientarchiveno = bindResult.substring(43);
            mData.setPatientarchiveno(patientarchiveno);
        } else {
            throw new ServiceException("保存失败：该体检者 " + peispatient.getPatientname() + " 档案绑定失败！");
        }

        // 体检号
        String patientCode = "";
        String userId = SecurityUtils.getUserNo();
        peisStateService.setScbs(peispatient.getPatientcode(), 0);
        // 判断是否为空
        if (StringUtils.isBlank(peispatient.getId()) && StringUtils.isBlank(peispatient.getPatientcode())) {
            //生成体检号
            do {
                patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
                //判断体检号是否存在
            } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                    .eq(Peispatient::getPatientcode, patientCode)) > 0);
            if (isExistByPatientCode(patientCode)) {
                throw new ServiceException("保存失败：生成体检号失败！");
            }
            peispatient.setPatientcode(patientCode);
            peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
            peispatient.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
            peispatient.setPatientarchiveno(patientarchiveno);
            peispatient.setIdDoctorreg(userId);
            // 登记后预付不修改数据库的值
            if (param.getIntReservation() == 1) {
                peispatient.setPrepayment(null == pNew ? 0.0 : pNew.getPrepayment());
            }

            peispatientMapper.insert(peispatient);
            String id = peispatient.getId();

            // 体检者费用主表
            PeispatientChargeMain peispatientChargeMain = new PeispatientChargeMain();
            peispatientChargeMain.setNote(SecurityUtils.getUsername() + "保存于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";");
            peispatientChargeMain.setMoneyamount(peispatient.getMoneyamount());
            peispatientChargeMain.setMoneyamountpaid(peispatient.getMoneyamountpaid());
            peispatientChargeMain.setPatientcode(peispatient.getPatientcode());
            peispatientChargeMain.setVersion(new Date().getTime());
            peispatientChargeMainMapper.insert(peispatientChargeMain);
            // 保存实体类
            if (StringUtils.isBlank(id)) {
                throw new ServiceException("保存失败：保存体检者信息失败！");
            }
            pNew = peispatient;
        } else {
            // 是否存在
            if (null == pNew) {
                pNew = peispatientMapper.getInfoById(peispatient.getId());
                if (null == pNew || StringUtils.isBlank(peispatient.getId())) {
                    throw new ServiceException("保存失败：体检号不存在，不能保存体检者信息！");
                } else {
                    // 备单人员未提前生成体检号，在此生成
                    //生成体检号
                    do {
                        patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
                        //判断体检号是否存在
                    } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                            .eq(Peispatient::getPatientcode, patientCode)) > 0);
                    if (isExistByPatientCode(patientCode)) {
                        throw new ServiceException("保存失败：生成体检号失败！");
                    }
                }
            } else {
                patientCode = peispatient.getPatientcode();
                //如果以线上体检号前缀开头，重新生成本地体检号
                if (patientCode.indexOf(Constants.ONLINE_PREFIX) != -1) {
                    // 如果已app开头，转换体检号并重新赋值
                    patientCode = CodeUtil.appConvert(iSysBranchService.getBranchFlag(null), "", patientCode);
                    //更新体检者收费表（收费项目表在下面更新）
                    List<Peispatientcharge> charges = peispatientchargeService.list(new QueryWrapper<Peispatientcharge>()
                            .eq("patientcode", peispatient.getPatientcode()));
                    Integer shortCode = ToolUtil.getShortCodeByLong(patientCode);
                    for (Peispatientcharge charge : charges) {
                        charge.setPatientcode(patientCode);
                        charge.setShortCode(shortCode);
                        peispatientchargeService.updateById(charge);
                    }
                    if (photo != null) {
                        photo.setPatientcode(patientCode);
                    }
                    PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", peispatient.getPatientcode()));
                    if (pcm == null) {
                        throw new ServiceException("保存失败：体检者消费主表不存在！");
                    } else {
                        pcm.setPatientcode(patientCode);
                    }
                }
            }
            peispatient.setPatientarchiveno(patientarchiveno);
            peispatient.setPatientcode(patientCode);
            peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
            peispatient.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
            peispatient.setIdDoctorreg(userId);
            // 登记后预付不修改数据库的值
            if (param.getIntReservation() == 1) {
                peispatient.setPrepayment(null == pNew ? 0.0 : pNew.getPrepayment());
            }

            // 更新
            peispatientMapper.updateById(peispatient);
        }
        //如果是补检  登记
        if (param.getIntReservation() == 1 && pNew.getInsuranceno() != null) {
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .eq("f_registered", 1).eq("f_feecharged", 1)
                    .eq("id_patient", pNew.getInsuranceno()).eq("f_transferedhl7", 0));
            for (Peispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                peispatientfeeitem.setFTransferedhl7(1);
                peispatientfeeitemService.updateById(peispatientfeeitem);
                // 设置已来检
                VisitMain main = visitMainMapper.selectOne(new QueryWrapper<VisitMain>().eq("patientcode", pNew.getInsuranceno())
                        .eq("id_examfeeitem", peispatientfeeitem.getId()).eq("is_delete", 0));
                if (ObjectUtils.isNotEmpty(main)) {
                    main.setIsInspect(1);
                    main.setInspectTime(new Date());
                    visitMainMapper.updateById(main);
                }
            }

        }

        PeispatientChargeMain pcm = peispatientChargeMainMapper.getByPatientCode(peispatient.getPatientcode());
        if (pcm != null) {
            pcm.setMoneyamount(peispatient.getMoneyamount());
            pcm.setMoneyamountpaid(peispatient.getMoneyamountpaid());
        } else {
            throw new ServiceException("保存失败：未找到收费信息主表。");
        }

        if (photo == null) {
            if (StringUtils.isNotEmpty(param.getPicture())) {
                // 体检者头像
                PeispatientPhoto peispatientPhoto = new PeispatientPhoto();
                peispatientPhoto.setPatientcode(peispatient.getPatientcode());
                peispatientPhoto.setPicture(param.getPicture());
                peispatientPhotoMapper.insert(peispatientPhoto);
            }
        } else {
            photo.setPicture(param.getPicture());
            peispatientPhotoMapper.updateById(photo);
        }

        // 右侧收费项目没有数据，只是预约状态
        if (ObjectUtils.isNotEmpty(param.getGriddata())) {
            // 保存右侧收费项目
            String result = saveOrUpdateItem(pNew, param.getGriddata(), param.getIntReservation(), param.getFormdata(), param.getNoticeJyk(), param.getPath());
            if (result.indexOf("success") != 0) {
                if (result.indexOf("fail") == 0) {
                    throw new ServiceException(result);
                } else {
                    throw new ServiceException("保存失败：保存收费项目发生问题！");
                }
            } else {
                int isataus = Integer.valueOf(result.substring(result.indexOf("sataus") + 7, result.length()));
                // 不是第一次登记和重新登记
                if (sataus != 0 && sataus != 1 && sataus != -1) {
                    if (isataus == 1) {
                        sataus = 2;
                    } else {
                        sataus = isataus;
                    }
                }
            }
        }
        return patientCode + " sataus:" + sataus + " patientarchiveno:" + peispatient.getPatientarchiveno();
    }


    public boolean getDictionaryStatusByType(String type) {
        SysConfig dic = sysConfigMapper.checkConfigKeyUnique(type);
        return (dic == null || dic.getConfigType() == null) ? false : "T".equals(dic.getConfigType()) ? true : false;
    }

    /**
     * 日期-8小时
     *
     * @param indate
     * @return
     */
    private Date subTime(Date indate) {
        Date dat = null;
        if (indate != null) {
            Long time = indate.getTime() - 28800000;
            dat = new Date(time);
        }
        return dat;
    }


    /**
     * 根据月份差值获取日期
     *
     * @param months
     * @return
     */
    private Date getDateForMonth(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -months);

        return calendar.getTime();
    }

    /**
     * 获取两个日期之间月份的差值
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    private int getMonthSpace(Date date1, Date date2)
            throws ParseException {
        int result = 0;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(date1);
        c2.setTime(date2);

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

        return result == 0 ? 0 : Math.abs(result);
    }


    /**
     * @param peispatient
     * @param map
     * @return 将archive的会员级别覆盖至peispatient
     * @throws Exception String
     * @Title: 绑定档案
     * @author zhanghj
     * @since 2016-9-24 V 1.0
     */
    private String bingIArchive(Peispatient peispatient,
                                SiFormdataDto map, boolean isBatch) throws Exception {
        String patientarchiveno = peispatient.getPatientarchiveno();
        // 判断档案是否存在或者人为取消（重新创建档案）
        if (StringUtils.isBlank(patientarchiveno)
                || "0".equals(patientarchiveno)
                || "1".equals(patientarchiveno)) {
            String name = peispatient.getPatientname();
            String phone = peispatient.getPhone();
            String idcardno = peispatient.getIdcardno();

            map.setDateregister(new Date());
            map.setMemberlevel(map.getIdPatientclass());
            map.setIshmd("null".equals(map.getIshmd()) ? 0 : map.getIshmd());
            map.setHmdbz(map.getHmdbz());
            if ("1".equals(patientarchiveno)) {//创建新档案
                // 生成档案号
                patientarchiveno = peispatientarchiveService.getArchiveCode();
                map.setPatientarchiveno(patientarchiveno);
                map.setMembercreate(SecurityUtils.getUserNo());
                map.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
                // 保存档案
                patientarchiveno = saveRecord(map);
                if (StringUtils.isBlank(patientarchiveno)) {
                    throw new ServiceException("保存失败：创建档案失败!");
                }

                return "success.no:" + patientarchiveno;
            }
            QueryWrapper<Peispatientarchive> and = new QueryWrapper<>();
            // 判断是否为空
            if (!StringUtils.isBlank(idcardno)) {
                // 匹配档案
                and.eq("idcardno", idcardno);
                List<Peispatientarchive> archives = peispatientarchiveService.list(and);
                if (archives.size() > 1) {
                    // 前台弹出档案列表进行选择
                    throw new ServiceException("-1");
                } else if (archives.size() == 1) {//匹配到唯一档案
                    Peispatientarchive archive = archives.get(0);
                    // 档案号
                    patientarchiveno = archive.getPatientarchiveno();
                    // 更新档案黑名单
                    archive.setIshmd(peispatient.getIsHmd());
                    // 最后一次体检时间（登记日期）
                    archive.setDateregister(new Date());
                    // 档案是否团检（0：个人 1：团检）
                    archive.setIsOrg(StringUtils.isBlank(peispatient.getIdOrg()) ? 0 : 1);
                    //保存、登记时要修改电话
                    archive.setPhone(peispatient.getPhone());

                    String archiveClass = archive.getMemberlevel();
                    String patientClass = peispatient.getIdPatientclass();//必填字段
                    if (archiveClass == null) {
                        archive.setMemberlevel(patientClass);
                    } else {
                        int i = archiveClass.compareTo(patientClass);
                        if (i < 0) {////体检者的等级如果修改大于档案中的登记，就修改档案中的等级
                            archive.setMemberlevel(patientClass);
                        } else if (i > 0) {//如果小于档案中的等级，就直接调取档案中的登记，覆盖体检者的等级。
                            //2019.7.4 需要能编辑VIP等级，去掉
                            //peispatient.setIdPatientclass(archiveClass);
                        }
                    }
                    peispatientarchiveService.updateById(archive);
                } else if (archives.size() == 0) {//未匹配到  创建新档案
                    // 生成档案号
                    patientarchiveno = peispatientarchiveService.getArchiveCode();
                    map.setPatientarchiveno(patientarchiveno);
                    map.setMembercreate(SecurityUtils.getUserNo());
                    map.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
                    // 保存档案
                    patientarchiveno = saveRecord(map);
                    if (StringUtils.isBlank(patientarchiveno)) {
                        throw new ServiceException("保存失败：创建档案失败!");
                    }
                }
            } else {
                // 判断是否为空
                if (!StringUtils.isBlank(name)) {
                    and.eq("patientname", name);
                }
                if (!StringUtils.isBlank(phone)) {
                    and.eq("phone", phone);
                }
                List<Peispatientarchive> archives = peispatientarchiveService.list(and);
                if (!isBatch//如果是批量登记，只匹配有身份证的，没有身份证的生成新档案。如果要匹配，需要到前台登记匹配
                        && archives.size() > 0) {
                    // 前台弹出档案列表进行选择
                    throw new ServiceException("-1");
                } else {
                    // 生成档案号
                    patientarchiveno = peispatientarchiveService.getArchiveCode();
                    map.setPatientarchiveno(patientarchiveno);
                    map.setMembercreate(SecurityUtils.getUserNo());
                    map.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
                    // 保存档案
                    patientarchiveno = saveRecord(map);
                    if (StringUtils.isBlank(patientarchiveno)) {
                        throw new ServiceException("保存失败：创建档案失败！");
                    }
                }
            }
        } else {
            // 已经选择档案号
            Peispatientarchive archive = peispatientarchiveService.getInfoByNo(patientarchiveno);
            if (null == archive) {
                throw new ServiceException("保存失败：选择的档案不存在，已经被删除!");
            }
            patientarchiveno = archive.getPatientarchiveno();
            // 更新档案黑名单
            archive.setIshmd(peispatient.getIsHmd());
            archive.setHmdbz(peispatient.getIsHmdb());
            // 最后一次体检时间（登记日期）
            archive.setDateregister(new Date());
            // 档案是否团检（0：个人 1：团检）
            archive.setIsOrg(StringUtils.isBlank(peispatient.getIdOrg()) ? 0 : 1);
            //保存、登记时要修改电话
            archive.setPhone(peispatient.getPhone());

            String archiveClass = archive.getMemberlevel();
            String patientClass = peispatient.getIdPatientclass();//必填字段
            if (archiveClass == null) {
                archive.setMemberlevel(patientClass);
            } else {
                int i = archiveClass.compareTo(patientClass);
                if (i < 0) {//体检者的等级如果修改大于档案中的登记，就修改档案中的等级
                    archive.setMemberlevel(patientClass);
                } else if (i > 0) {//如果小于档案中的等级，就直接调取档案中的登记，覆盖体检者的等级。
                    //peispatient.setIdPatientclass(archiveClass);
                }
            }
            peispatientarchiveService.updateById(archive);
        }

        return "success.no:" + patientarchiveno;
    }


    /**
     * @Title: 保存档案
     */
    private String saveRecord(SiFormdataDto map) {
        // 最后一次体检时间（登记日期）
        map.setDateregister(new Date());
        // 档案是否团检（0：个人 1：团检）
        map.setIsOrg(null == map.getIdOrg() ? "0" : "1");
        map.setNote(null);
        // 转化实体类
        Peispatientarchive patientArchive = mapperFacade.map(map, Peispatientarchive.class);
        // 会员级别
        patientArchive.setMemberlevel(null == map.getIdPatientclass() ? "0" : map.getIdPatientclass().toString());
        // 积分
        patientArchive.setJf(0D);
        patientArchive.setFzx(getCurentUserCenter());

        // 保存实体，成功返回id，失败返回null
        boolean save = peispatientarchiveService.save(patientArchive);
        return save ? patientArchive.getPatientarchiveno() : null;
    }

    /**
     * @return String
     * @Title: 获取当前登录用户所在的分中心
     * @author zhanghj
     * @since 2016-9-22 V 1.0
     */
    public String getCurentUserCenter() {
        //获取当前登录用户分中心id
        String fzxId = sysUserService.selectUserByUserNo(SecurityUtils.getUserNo()).getCid();
        return fzxId;
    }


    /**
     * @param patientCode
     * @return boolean
     * @Title: 判断体检号是否存在
     * @author zhanghj
     * @since 2016-8-11 V 1.0
     */
    private boolean isExistByPatientCode(String patientCode) {
        Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
        return null != peispatient;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdateItem(Peispatient pei, List<SiGriddataDto> griddata, int intReservation, SiFormdataDto formdata, String noticeJyk, String path) throws Exception {
        String patientCode = pei.getPatientcode();
        pei.setModifydate(new Date());
        // 已经登记
        if (null != pei.getFRegistered() && 1 == pei.getFRegistered()) {
            if (0 == intReservation) {
                throw new ServiceException("该体检者已经完成登记，不可再次预约！");
            }
            // 如果已经登记过，说明收费项目是存在的，页面点击“保存”按钮，登记未收费项的项目
            else if (2 == intReservation) {
                intReservation = 1;
            }
        }

        //如果是补检，自动将所有没有处理的处理成补检来检
        if (intReservation == 1 && pei.getInsuranceno() != null) {
            List<VisitMain> vms = visitMainMapper.selectList(new QueryWrapper<VisitMain>()
                    .eq("patientcode", pei.getInsuranceno()).in("type", new Integer[]{0, 4}));
            String visitId = SecurityUtils.getUsername();
            Date visitTime = new Date();
            for (VisitMain vm : vms) {
                // KF迟捡、阳性、不合格样本回访
                FailTotalVisit ftv = failTotalVisitMapper.selectOne(new QueryWrapper<FailTotalVisit>().eq("visit_main_id", vm.getId()));
                if (ObjectUtils.isEmpty(ftv)) {
                    ftv = new FailTotalVisit();
                    ftv.setVisitMainId(vm.getId());
                    ftv.setVisitType(0);
                    ftv.setPersoncode(vm.getPatientcode());
                    ftv.setVisitId(visitId);
                    ftv.setSflj(5);
                    ftv.setVisitTime(visitTime);
                    failTotalVisitMapper.insert(ftv);
                }
            }
        }

        List<SiGriddataDto> featureData = griddata;
        if (CollectionUtils.isEmpty(featureData)) {
            throw new ServiceException("发生异常，请重新再试！");
        } else if (featureData.size() == 0) {
            return "success" + " sataus:" + -1;
        }
        Integer shortCode = pei.getShortCode();
        // 不同的收费项目之间的检查项目也不能重复
        List<String> strr = featureData.stream().filter(itemsDto -> {
            // 删除、退项并且已退成功、弃检不参与
            if ("removed".equals(itemsDto.getState())) return false;
            //退项,弃检
            if (!((Objects.nonNull(itemsDto.getChangeItem()) && itemsDto.getChangeItem() == 1
                    && Objects.nonNull(itemsDto.getFMarkFeereturn()) && itemsDto.getFMarkFeereturn() == 1)
                    || Objects.nonNull(itemsDto.getFGiveup()) && itemsDto.getFGiveup() == 1
            )) return true;//收费项目ID
            return false;

        }).map(SiGriddataDto::getIdExamfeeitem).collect(Collectors.toList());

        //是否存在相同的检查项目
        String text = inspectChargeService.compareItemsToExam(strr);
        if (!StringUtils.isBlank(text)) {
            throw new ServiceException(text);
        }

        // 团检是否存在现金收费(1:现金 0：统收)
        int isTuanToX = 0;
        int bxcount = 0;
        boolean hasTen = false;
        SiFormdataDto mForm = null;
        if (ObjectUtils.isNotEmpty(formdata)) {
            mForm = formdata;
        }
        if (null != mForm) {
            hasTen = !StringUtils.isBlank(mForm.getHasTen()) && mForm.getHasTen().equals("1");
            // 团检
            if (!StringUtils.isBlank(mForm.getOrg()) && mForm.getOrg().equals("1")) {
                isTuanToX = (Objects.isNull(mForm.getTongFei()) ? 0.00 : mForm.getTongFei()) > 0 ? 0 : 1;
            } else {
                isTuanToX = 1;
            }
            // 备选数量
            bxcount = Integer.valueOf(StringUtils.isBlank(mForm.getBxcount()) ? "0" : mForm.getBxcount());
        } else {
            isTuanToX = 1;
        }

        int isataus = -1;
        // 存在套餐
        Double tcPrice = 0d;
        Double yuanshi = 0d;
        if (!StringUtils.isBlank(pei.getIdTjtc())) {
            Createcombo combo = createcomboMapper.getInfoById(pei.getIdTjtc());
            Createmeal createMeal = createmealMapper.getInfoById(pei.getIdTjtc());
            if (null != combo && null == createMeal) {
                yuanshi = combo.getTcysjg();
                tcPrice = combo.getZhjg();
            } else if (null == combo && null != createMeal) {
                yuanshi = createMeal.getTcysjg();
                tcPrice = createMeal.getZhjg();
            }
            yuanshi = null == yuanshi ? 0 : yuanshi;
            tcPrice = null == tcPrice ? 0 : tcPrice;
        }

        // 退项价格
        Double tuiPrice = 0d;
        Double tuiYsPrice = 0d;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        // 未收费项目是否需要变为已收（未收项总和=0，自动收费）
        Double noCharge = 0d;
        // 是否已登记
//        Boolean reg = false;
        String jykId = getInspectionId();
        for (SiGriddataDto map : featureData) {
            //快速赋值
            Peispatientfeeitem item = mapperFacade.map(map, Peispatientfeeitem.class);
            // 性别
            item.setFFeechargedinttrans(null == map.getFFeechargedinttrans() ? 0 : Integer.valueOf(map.getFFeechargedinttrans().toString()));
            // 弃检
            item.setFGiveup(ObjectUtils.isEmpty(map.getFGiveup()) ? 0 : Integer.valueOf(map.getFGiveup()));
            // 迟检
            item.setFDelayed(ObjectUtils.isEmpty(map.getFDelayed()) ? 0 : Integer.valueOf(map.getFDelayed()));
            // 体检号
            item.setIdPatient(patientCode);
            item.setShortCode(shortCode);
            item.setBxcount(bxcount);

            //弃检需要判断是否是职业必查项目
            if (item.getFGiveup() == 1) {
                Peispatient peispatient = peispatientMapper.getByPatientCode(patientCode);
                if (ObjectUtils.isNotEmpty(peispatient) && ObjectUtils.isNotEmpty(peispatient.getJhys())) {
                    Integer count = signInInspectionMapper.getCareerInspect(patientCode, peispatient.getJhys(), peispatient.getMedicaltype(), item.getId());
                    if (count > 0) {
                        throw new ServiceException(item.getExamfeeitemName() + "是职业必查项目，不能弃检！");
                    }
                }
            }


            // 计算实际金额
            // 不是最小套餐，实际金额==优惠价格
            Double shiji = 0d;
            if (yuanshi != 0) {
                shiji = Double.valueOf(decimalFormat.format(tcPrice / yuanshi * item.getPrice()));
            }
            if (null == item.getIsMintc() || 0 == item.getIsMintc()) {
                shiji = item.getFactprice();
            }

            item.setActualprice(shiji);
            if ("removed".equals(map.getState())) {
                if (null != map.getId()) {
                    Peispatientfeeitem feeItem = peispatientfeeitemMapper.getInfoById(map.getId());
                    // 判断是否存在
                    if (null != feeItem) {
                        // 弃检
                        if (feeItem.getFGiveup() == 1) {
                            throw new ServiceException("删除失败：" + map.getExamfeeitemName() + "收费项目已经弃检，不能被删除");
                        }
                        // 已检
                        if (null != feeItem.getFExaminated() && feeItem.getFExaminated() == 1) {
                            throw new ServiceException("删除失败：" + map.getExamfeeitemName() + "收费项目已经检查，不能被删除！");
                        }
                        // 已收费
                        if (feeItem.getFFeecharged() != null && feeItem.getFFeecharged() == 1) {
                            throw new ServiceException("删除失败：" + map.getExamfeeitemName() + "收费项目已经收费，不能被删除,只能换项或者弃项！");
                        }
                        // 删除实体类
                        peispatientfeeitemService.removeById(feeItem);
                    } else {
                        // 不存在
//                        throw new Exception("删除失败：<font color='red'>"+map.get("examfeeitemName") +"</font> 收费项目不存在，已经被删除！");
                    }
                }
            } else if ("modified".equals(map.getState())) {
                Peispatientfeeitem feeItem = peispatientfeeitemMapper.getInfoById(map.getId());
                // 判断是否存在
                if (null != feeItem) {
                    // 退项
                    if (null != item.getChangeItem() && item.getChangeItem() == 1) {
                        // 弃检
                        if (feeItem.getFGiveup() == 1) {
                            throw new ServiceException("保存失败：" + map.getExamfeeitemName() + "收费项目已经弃检，不能退项！");
                        }
                        if (null != item.getEndtuiprice() && (null == feeItem.getFMarkFeereturn() || 0 == feeItem.getFMarkFeereturn())) {
                            tuiPrice += item.getEndtuiprice();
                            tuiYsPrice -= item.getPrice();
                        }
                    }
                    // 弃检
                    if (null != item.getFGiveup() && item.getFGiveup() == 1) {
                        // 退项
                        if (null != feeItem.getChangeItem() && feeItem.getChangeItem() == 1) {
                            throw new ServiceException("保存失败：" + map.getExamfeeitemName() + "收费项目已经退项，不能弃检！");
                        }
                    }
                    // 已检
                    if (null != feeItem.getFExaminated() && feeItem.getFExaminated() == 1) {
                        // 退项
                        if (null != item.getChangeItem() && item.getChangeItem() == 1) {
                            throw new ServiceException("保存失败：" + map.getExamfeeitemName() + "收费项目已经检查，不能退项！");
                        }
                        // 弃检
                        if (null != item.getFGiveup() && item.getFGiveup() == 1) {
                            throw new ServiceException("保存失败：" + map.getExamfeeitemName() + "收费项目已经检查，不能弃检！");
                        }
//                        reg = true;
                    }
                    // 迟检(已经迟检的不再保存)
                    if (null != item.getFDelayed() && item.getFDelayed() == 1 &&
                            (null == feeItem.getFDelayed() || feeItem.getFDelayed() == 0)) {
                        item.setCjr(SecurityUtils.getUsername());
                        item.setCjsj(new Date());

                        // 更新迟捡、阳性、不合格样本人员表
                        VisitMain vm = new VisitMain();
                        vm.setPatientcode(patientCode);
                        vm.setIdExamfeeitem(feeItem.getId());
                        vm.setType(0);
                        vm.setExamfeeitemName(map.getExamfeeitemName());
                        visitMainService.saveChijian(vm);

                        /**迟检检验科项目时 默认插入不用提示*/
                        if (jykId.equals(feeItem.getIdKs())) {
                            //检验科加项处理
                            HandleNewProjects handleNewProjects = handleNewProjectsMapper.selectOne(new QueryWrapper<HandleNewProjects>()
                                    .eq("patientcode", patientCode).eq("projects_id", item.getId())
                                    .eq("is_delete", 0).eq("handle_type", 3));
                            // 不存在
                            if (null == handleNewProjects) {
                                handleNewProjects = new HandleNewProjects();
                                handleNewProjects.setPatientcode(patientCode);
                                handleNewProjects.setCreateId(SecurityUtils.getUserNo());
                                handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                                handleNewProjects.setProjectsId(feeItem.getId());// 项目ID
                                handleNewProjects.setIsDelete(0);
                                handleNewProjects.setStatus(0);
                                handleNewProjects.setHandleType(3);
                                // 保存实体类
                                handleNewProjectsMapper.insert(handleNewProjects);
                                String cId = handleNewProjects.getId();

                                if (StringUtils.isBlank(cId)) {
                                    throw new Exception("保存失败" + map.getExamfeeitemName() + "收费项目弃检保存失败！");
                                }
                            } else {
                                handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                                handleNewProjects.setProjectsId(feeItem.getId());// 项目ID
                                // 更新实体类
                                handleNewProjectsMapper.updateById(handleNewProjects);
                            }
                        }
                    }
                    // 反迟检
                    if (null != feeItem.getFDelayed() && feeItem.getFDelayed() == 1) {
                        LambdaUpdateWrapper<Peispatientfeeitem> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(Peispatientfeeitem::getId, feeItem.getId())
                                .set(Peispatientfeeitem::getCjr, null)
                                .set(Peispatientfeeitem::getCjsj, null);
                        peispatientfeeitemService.update(updateWrapper);

                        VisitMain visitMain = visitMainMapper.selectOne(new QueryWrapper<VisitMain>()
                                .eq("patientcode", patientCode).eq("id_examfeeitem", feeItem.getId())
                                .eq("type", 0).eq("is_delete", 0));

                        if (ObjectUtils.isNotEmpty(visitMain)) {
                            visitMain.setIsDelete(1);
                            visitMainMapper.updateById(visitMain);
                        }
                        HandleNewProjects handleNewProjects = handleNewProjectsMapper.selectOne(new QueryWrapper<HandleNewProjects>()
                                .eq("patientcode", patientCode)
                                .eq("projects_id", item.getId())
                                .eq("is_delete", 0)
                                .eq("handle_type", 3));
                        if (ObjectUtils.isNotEmpty(handleNewProjects)) {
                            if (handleNewProjects.getIsHandle() != null && handleNewProjects.getIsHandle() == 1) {
                                throw new ServiceException("保存失败：" + map.getExamfeeitemName() + "收费项目的弃检已被检验科处理，请反处理后再反迟检！");
                            }
                            handleNewProjectsMapper.deleteById(handleNewProjects);
                        }
                    }
                    // 收费项目登记
                    if (intReservation == 1) {
                        item.setFRegistered(1);
                        // 登记日期
                        item.setRegistertime(new Date());
                    }
                    // 判断是否存在登记员ID
                    if (StringUtils.isBlank(item.getIdDoctorreg())) {
                        item.setIdDoctorreg(SecurityUtils.getUserNo());
                        SysUser user = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
                        item.setDoctorregR(null == user ? "" : user.getUserName());
                    }
                    /**HandleNewProjects*/
                    // 加项医师是检化验科表中插入数据--> 检验科加项处理表
                    if (null != item.getSfjx() && 1 == item.getSfjx()) {
                        HandleNewProjects handleNewProjects = handleNewProjectsMapper.selectOne(new QueryWrapper<HandleNewProjects>()
                                .eq("patientcode", patientCode)
                                .eq("projects_id", item.getId())
                                .eq("is_delete", 0).eq("handle_type", 0));
                        // 不存在
                        if (null == handleNewProjects) {
                            handleNewProjects = new HandleNewProjects();
                            handleNewProjects.setPatientcode(patientCode);
                            handleNewProjects.setCreateId(SecurityUtils.getUserNo());
                            handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                            handleNewProjects.setProjectsId(feeItem.getId());// 项目ID
                            handleNewProjects.setAddDoctorId(feeItem.getJxys());
                            handleNewProjects.setIsDelete(0);
                            handleNewProjects.setStatus(0);
                            handleNewProjects.setHandleType(0);
                            // 保存实体类

                            handleNewProjectsMapper.insert(handleNewProjects);
                            String cId = handleNewProjects.getId();

                            if (StringUtils.isBlank(cId)) {
                                throw new ServiceException("保存失败" + map.getExamfeeitemName() + "收费项目加项医师保存失败！");
                            }
                        } else {
                            handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                            handleNewProjects.setProjectsId(feeItem.getId());// 项目ID
                            handleNewProjects.setAddDoctorId(feeItem.getJxys());

                            // 更新实体类
                            handleNewProjectsMapper.updateById(handleNewProjects);
                        }
                    }
                    // 弃检
                    if (null != item.getFGiveup() && item.getFGiveup() == 1) {
                        //本次操作弃检的
                        if (feeItem.getFGiveup() == null || feeItem.getFGiveup() != 1) {
                            item.setQjr(SecurityUtils.getUsername());
                            item.setQjsj(new Date());
                            //弃检也要签字，暂时放在拒检人签名里面
                            item.setJjrqm(path);
                        }

                        HandleNewProjects handleNewProjects = handleNewProjectsMapper.selectOne(new QueryWrapper<HandleNewProjects>()
                                .eq("patientcode", patientCode)
                                .eq("projects_id", item.getId())
                                .eq("is_delete", 0).eq("handle_type", 1));
                        // 不存在
                        if (null == handleNewProjects) {
                            handleNewProjects = new HandleNewProjects();
                            handleNewProjects.setPatientcode(patientCode);
                            handleNewProjects.setCreateId(SecurityUtils.getUserNo());
                            handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                            handleNewProjects.setProjectsId(feeItem.getId());// 项目ID
                            // handleNewProjects.setAddDoctorId(feeItem.getJxys());
                            handleNewProjects.setIsDelete(0);
                            handleNewProjects.setStatus(0);
                            handleNewProjects.setHandleType(1);
                            // 保存实体类

                            handleNewProjectsMapper.insert(handleNewProjects);
                            String cId = handleNewProjects.getId();

                            if (StringUtils.isBlank(cId)) {
                                throw new ServiceException("保存失败：" + map.getExamfeeitemName() + "收费项目弃检保存失败！");
                            }
                        } else {
                            handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                            handleNewProjects.setProjectsId(feeItem.getId());// 项目ID
                            // handleNewProjects.setAddDoctorId(feeItem.getJxys());

                            // 更新实体类
                            handleNewProjectsMapper.updateById(handleNewProjects);
                        }
                    }
                    //反弃检
                    if (null != feeItem.getFGiveup() && feeItem.getFGiveup() == 1
                            && (item.getFGiveup() == null || item.getFGiveup() != 1)) {

                        LambdaUpdateWrapper<Peispatientfeeitem> updateWrapper = new LambdaUpdateWrapper<>();
                        updateWrapper.eq(Peispatientfeeitem::getId, feeItem.getId())
                                .set(Peispatientfeeitem::getQjr, null)
                                .set(Peispatientfeeitem::getQjsj, null);
                        peispatientfeeitemService.update(updateWrapper);

                        HandleNewProjects handleNewProjects = handleNewProjectsMapper.selectOne(new QueryWrapper<HandleNewProjects>()
                                .eq("patientcode", patientCode)
                                .eq("projects_id", item.getId())
                                .eq("is_delete", 0).eq("handle_type", 1));
                        if (ObjectUtils.isNotEmpty(handleNewProjects)) {
                            if (handleNewProjects.getIsHandle() != null && handleNewProjects.getIsHandle() == 1) {
                                throw new ServiceException("保存失败：" + map.getExamfeeitemName() + "收费项目的弃检已被检验科处理，请反处理后再反弃检！");
                            }
                            handleNewProjectsMapper.deleteById(handleNewProjects);
                        }
                    }
                    if (1 == intReservation
                            && item.getSamplemsgfromlis() != null
                            && "1".equals(item.getSamplemsgfromlis())) {
                        item.setFFeecharged(1);// 已收费
                        item.setIdFeecharger(SecurityUtils.getUserNo());
                        item.setFeechargetime(new Date());
                    }
                    /**HandleNewProjects结束*/
                    // 更新实体类
                    peispatientfeeitemService.updateById(item);
                    // 是否自动收费
                    if (null == feeItem.getFFeecharged() || 0 == feeItem.getFFeecharged()) {
                        noCharge += feeItem.getFactprice();
                    }
                } else {
                    // 不存在
                    throw new Exception("保存失败：" + map.getExamfeeitemName() + "收费项目不存在，已经被删除！");
                }
            } else if ("added".equals(map.getState())) {
                Peispatientfeeitem old = peispatientfeeitemMapper.selectOne(new QueryWrapper<Peispatientfeeitem>()
                        .eq("id_patient", item.getIdPatient())
                        .eq("id_examfeeitem", item.getIdExamfeeitem())
                        .eq("change_item", 0));
                if (old != null) {
                    throw new ServiceException("体检者已拥有收费项目【" + old.getExamfeeitemName() + "】,不能再添加此收费项目,请点击【刷新】查看。");
                }

                // 已登记
                if (null != pei.getFRegistered() && pei.getFRegistered() == 1) {
                    // 已登记，增项
                    isataus = 1;
                }
                // 收费项目登记
                if (intReservation == 1) {
                    item.setFRegistered(1);
                    // 登记日期
                    item.setRegistertime(new Date());
//                    reg = true;
                }

                // 判断是否存在登记员ID
                if (StringUtils.isBlank(item.getIdDoctorreg())) {
                    item.setIdDoctorreg(SecurityUtils.getUserNo());
                    SysUser user = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
                    item.setDoctorregR(null == user ? "" : user.getUserName());
                }

                // 团检是否存在现金收费
                if (1 == intReservation && isTuanToX == 0 && item.getIdPayway().equals("5")) {
                    // 统收不存在加项收费信息保存
                    item.setFFeecharged(1);// 已收费
                    item.setIdFeecharger(SecurityUtils.getUserNo());
                    item.setFeechargetime(new Date());
                }
                if (1 == intReservation
                        && item.getSamplemsgfromlis() != null
                        && "1".equals(item.getSamplemsgfromlis())) {
                    item.setFFeecharged(1);// 已收费
                    item.setIdFeecharger(SecurityUtils.getUserNo());
                    item.setFeechargetime(new Date());
                }

                // 保存实体类
                boolean b = peispatientfeeitemService.save(item);
                String result = item.getId();
                if (!b) {
                    throw new ServiceException(map.getExamfeeitemName() + "收费项目保存失败！");
                }

                String tempId = map.getTempId();
                if (StringUtils.isNotEmpty(tempId)) {
                    // 科室临时加项表
                    TempFeeitem tf = tempFeeitemMapper.getInfoById(tempId);
                    tf.setFeeitemId(result);
                    tf.setIsDelete(1);
                }
                // 是否自动收费
                noCharge += item.getFactprice();
                // 总检开始，不可以加项
                if (null != item.getSfjx() && 1 == item.getSfjx()) {
                    if (((null != pei.getFFinallocked() && pei.getFFinallocked() == 1)
                            || (null != pei.getJktjzt() && pei.getJktjzt() > 0))
                            || ((null != pei.getIdGuidenurse() && pei.getIdGuidenurse() == 1)
                            || (null != pei.getZytjzt() && pei.getZytjzt() > 0))) {
                        throw new ServiceException("保存失败：该体检者总检已经锁定或者开始，不能加项！");
                    } else {
                        // 检验科加项医师处理
                        HandleNewProjects handleNewProjects = new HandleNewProjects();
                        handleNewProjects.setPatientcode(patientCode);
                        handleNewProjects.setCreateId(SecurityUtils.getUserNo());
                        handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                        handleNewProjects.setProjectsId(result);// 项目ID
                        handleNewProjects.setAddDoctorId(item.getJxys());
                        handleNewProjects.setIsDelete(0);
                        handleNewProjects.setStatus(0);
                        handleNewProjects.setHandleType(0);
                        // 保存实体类
                        handleNewProjectsMapper.insert(handleNewProjects);
                        String cId = handleNewProjects.getId();

                        if (StringUtils.isBlank(cId)) {
                            throw new ServiceException("保存失败：" + map.getExamfeeitemName() + "收费项目加项医师保存失败！");
                        }
                    }
                } else if ("1".equals(noticeJyk)) {
                    // 检验科加项医师处理
                    HandleNewProjects handleNewProjects = new HandleNewProjects();
                    handleNewProjects.setPatientcode(patientCode);
                    handleNewProjects.setCreateId(SecurityUtils.getUserNo());
                    handleNewProjects.setModifyId(SecurityUtils.getUserNo());
                    handleNewProjects.setProjectsId(result);// 项目ID
                    handleNewProjects.setAddDoctorId(item.getJxys());
                    handleNewProjects.setIsDelete(0);
                    handleNewProjects.setStatus(0);
                    handleNewProjects.setHandleType(0);
                    // 保存实体类
                    handleNewProjectsMapper.insert(handleNewProjects);
                    String cId = handleNewProjects.getId();

                    if (StringUtils.isBlank(cId)) {
                        throw new ServiceException("保存失败：" + map.getExamfeeitemName() + " 收费项目加项医师保存失败！");
                    }
                }
            }
        }

        PeispatientChargeMain pcm = peispatientChargeMainMapper.selectOne(new QueryWrapper<PeispatientChargeMain>().eq("patientcode", patientCode));
        if (ObjectUtils.isEmpty(pcm)) {
            throw new ServiceException("未找到体检者收费主表。");
        }
        // 团检未加项客户增加收费信息
        if (1 == intReservation && isTuanToX == 0) {
            pei.setMoneyamountpaid((null == pei.getMoneyamountpaid() ? 0 : pei.getMoneyamountpaid()) + Double.valueOf(mForm.getTongFei()));
            pcm.setMoneyamountpaid(pei.getMoneyamountpaid());
            pcm.setNote(SecurityUtils.getUsername() + "团检自动收费于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";");
//            setVip(pei);

            PeispatientchargeDto peispatientchargeDto = new PeispatientchargeDto();
            peispatientchargeDto.setMoneyamount(formdata.getTongFei());
            peispatientchargeDto.setMoneyamountpaid(formdata.getTongFei());
            if (!peispatientchargeService.autoSaveOrUpdate(peispatientchargeDto)) {
                throw new ServiceException("收费信息保存失败！");
            }

        }
        //十周年收费
        if (1 == intReservation && hasTen) {
            pei.setMoneyamountpaid((null == pei.getMoneyamountpaid() ? 0
                    : pei.getMoneyamountpaid()) + Double.valueOf(mForm.getTenFei()));
            pcm.setMoneyamountpaid(pei.getMoneyamountpaid());
            pcm.setNote(SecurityUtils.getUsername() + "十周年卡自动收费于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";");

//            setVip(pei);
            String result = autoSaveOrUpdateTen(patientCode, formdata);
            if (!result.equals("success")) {
                throw new ServiceException("收费信息保存失败！");
            }
        }


        // 重新计算体检者原价和应收
        PriceAndFactPriceDto dto = peispatientfeeitemService.getPriceAndFactprice(patientCode);
        pei.setMoneyamount(dto.getFactprice());
        pei.setPersonpricelimit(dto.getPrice());

        String newNote = SecurityUtils.getUsername() + "保存应付金额于" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ";";
        pcm.setMoneyamount(pei.getMoneyamount());
        pcm.setNote(newNote);
        // }

        // 检查剩余收费项目是否可以分拣完成
        peispatientfeeitemService.checkFj(pei);
        // 优惠价总价为0自动收费
        /*if (reg && noCharge == 0) {
            preregistrationDao.autoChargeForZero(pei.getPatientcode());
        }*/

        return "success" + " sataus:" + isataus;
    }

    public String getInspectionId() {
        return sysDeptMapper.selectDeptByName("检验科").getDeptNo();
    }


    public String autoSaveOrUpdateTen(String patientCode, SiFormdataDto formdata) throws Exception {
        // 解析
        Map<String, String> m = JSON.parseObject(JSON.toJSONString(formdata), new TypeReference<Map<String, Object>>() {
        });
        // 拼接字符串
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        //收费方式 活动赠送
        Dictpayway pay = dictpaywayMapper.getInfoById(Constants.HDZS);
        map.put("idPayway", pay.getId());
        map.put("payway", pay.getPaywayName());
        //费用在页面上计算
        map.put("moneyamount", m.get("tenFei"));
        map.put("moneyamountpaid", m.get("tenFei"));
        map.put("f_feecharged", 1);
        map.put("feechargetime", new Date());
        //保存卡号
        map.put("cardno", m.get("havePrivate"));
        map.put("isTotalcharge", 1);
        //都是个检
        map.put("tong", 0);
        int numIndex = 1;
        List<Peispatientcharge> charge = peispatientchargeService.list(new QueryWrapper<Peispatientcharge>().orderByDesc("numIndex")
                .eq("patientcode", patientCode).isNotNull("numIndex"));
        if (charge.size() > 0) {
            if (charge.get(0).getNumIndex() != null) {
                numIndex = charge.get(0).getNumIndex() + 1;
            }
        }
        map.put("numIndex", numIndex);
        map.put("state", "added");
        list.add(map);

        //TODO ??? 十周年卡不用了
        String result = null;//saveOrUpdates(patientCode, list, "", null, false);

        return result;
    }

    /**
     * 拒检
     *
     * @param ids
     * @param path
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean jujian(List<String> ids, String path) throws IOException {
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>().in("id", ids));
        Peispatient patient = peispatientMapper.selectOne(new QueryWrapper<Peispatient>().eq("patientcode", feeitems.get(0).getIdPatient()));
        if (patient == null || patient.getFRegistered() != 1) {
            throw new ServiceException("审核失败，该体检号尚未登记！");
        }
        if (patient.getJktjzt() != null && patient.getJktjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getDoctorfinalNameR() == null ? "" : patient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getZytjzt() != null && patient.getZytjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getPatientnameencoded() == null ? "" : patient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getFFinallocked() != null && patient.getFFinallocked() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getIdDoctorapply() == null ? "" : patient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getIdGuidenurse() != null && patient.getIdGuidenurse() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getParsedassignedsuiteandfi() == null ? "" : patient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        patient.setModifydate(new Date());
        String jjr = SecurityUtils.getUsername();
        Date jjsj = new Date();
        for (Peispatientfeeitem fee : feeitems) {
            fee.setJjr(jjr);
            fee.setJjsj(jjsj);
            fee.setJjrqm(path);
            fee.setSfjj(1);
        }
        peispatientfeeitemService.updateBatchById(feeitems);
        peispatientfeeitemService.checkFj(patient);
        return Boolean.TRUE;
    }

    /**
     * 反拒检
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean fanjujian(List<String> ids) {
        List<Peispatientfeeitem> feeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>().in("id", ids));
        Peispatient patient = peispatientMapper.getByPatientCode(feeitems.get(0).getIdPatient());
        if (patient.getJktjzt() != null && patient.getJktjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getDoctorfinalNameR() == null ? "" : patient.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getZytjzt() != null && patient.getZytjzt() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getPatientnameencoded() == null ? "" : patient.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getFFinallocked() != null && patient.getFFinallocked() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getIdDoctorapply() == null ? "" : patient.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (patient.getIdGuidenurse() != null && patient.getIdGuidenurse() == 1) {
            throw new ServiceException("审核失败，本体检者检查结果已被" + (patient.getParsedassignedsuiteandfi() == null ? "" : patient.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        patient.setModifydate(new Date());

        for (Peispatientfeeitem fee : feeitems) {
            fee.setJjr(null);
            fee.setJjsj(null);
            fee.setJjrqm(null);
            fee.setSfjj(0);
        }
        peispatientfeeitemService.checkFj(patient);
        peispatientfeeitemService.updateBatchById(feeitems);
        return Boolean.TRUE;
    }


    /**
     * 补检，反补检
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateItemsDeal(UpdateItemsDealParam param) {
        Peispatient pei = peispatientMapper.getByPatientCode(param.getPatientCode());
        // 体检者不存在
        if (ObjectUtils.isEmpty(pei)) {
            throw new ServiceException("该体检者体检号不存在，不能退项！");
        }
        if (pei.getJktjzt() != null && pei.getJktjzt() == 1) {
            throw new ServiceException("操作失败，本体检者已被" + (pei.getDoctorfinalNameR() == null ? "" : pei.getDoctorfinalNameR()) + "完成健康总检，不能修改！如有未检项目也不再进行。");
        }
        if (pei.getZytjzt() != null && pei.getZytjzt() == 1) {
            throw new ServiceException("操作失败，本体检者已被" + (pei.getPatientnameencoded() == null ? "" : pei.getPatientnameencoded()) + "完成职业总检，不能修改！如有未检项目也不再进行。");
        }
        if (pei.getFFinallocked() != null && pei.getFFinallocked() == 1) {
            throw new ServiceException("操作失败，本体检者已被" + (pei.getIdDoctorapply() == null ? "" : pei.getIdDoctorapply()) + "锁定，不能修改！如有未检项目也不再进行。");
        }
        if (pei.getIdGuidenurse() != null && pei.getIdGuidenurse() == 1) {
            throw new ServiceException("操作失败，本体检者已被" + (pei.getParsedassignedsuiteandfi() == null ? "" : pei.getParsedassignedsuiteandfi()) + "锁定，不能修改！如有未检项目也不再进行。");
        }

        //@sqlOrder
        pei.setModifydate(new Date());
        List<UIDGriddataDto> featureData = param.getGriddata();

        if (CollectionUtils.isEmpty(featureData)) {
            throw new ServiceException("发生异常，请重新再试！");
        } else if (featureData.size() == 0) {
            throw new ServiceException("请选择一条记录");
        }

        // 解析data判断操作类型 type:0->补检,type:1->迟检,type:2->弃检
        UIDDataDto dealData = param.getData();

        int type = Integer.valueOf(dealData.getType());
        int value = Integer.valueOf(dealData.getValue());//1反补检0补检
        String userId = SecurityUtils.getUserNo();
        Set<String> mainIds = new HashSet<String>();
        for (UIDGriddataDto map : featureData) {
            //体检者收费项目
            Peispatientfeeitem feeItem = peispatientfeeitemMapper.getInfoById(map.getId());
            // 判断是否存在
            if (ObjectUtils.isNotEmpty(feeItem)) {
                switch (type) {
                    case 0: // 补检
                        if (value == 0) {
                            if (null != feeItem.getFTransferedhl7() && feeItem.getFTransferedhl7() == 0) {
                                throw new ServiceException(map.getExamfeeitemName() + "收费项目已为待补检！");
                            }
                            if (ObjectUtils.isNotEmpty(pei) && ObjectUtils.isNotEmpty(pei.getJhys())) {
                                Integer count = signInInspectionMapper.getCareerInspect(pei.getPatientcode(), pei.getJhys(), pei.getMedicaltype(), feeItem.getId());
                                if (count > 0) {
                                    throw new ServiceException(feeItem.getExamfeeitemName() + "是职业必查项目，不能弃检！");
                                }
                            }

                            feeItem.setBjr(SecurityUtils.getUsername());
                            feeItem.setBjsj(new Date());
                            //补检状态 0: 未补检 1：已补检
                            feeItem.setFTransferedhl7(0);
                            peispatientfeeitemService.updateById(feeItem);

                            // 回访表VisitMain增加信息
                            VisitMain visitMain = new VisitMain();
                            visitMain.setPatientcode(param.getPatientCode());
                            visitMain.setIdExamfeeitem(feeItem.getId());
                            visitMain.setType(4);
                            visitMain.setIsDelete(0);
                            visitMainMapper.insert(visitMain);
                            String visitRes = visitMain.getId();

                            if (StringUtils.isBlank(visitRes)) {
                                throw new ServiceException(map.getExamfeeitemName() + "收费项目操作失败！");
                            }
                            mainIds.add(visitRes);
                            //检验科加项处理
                            HandleNewProjects handleNewProjects = handleNewProjectsMapper.selectOne(new QueryWrapper<HandleNewProjects>()
                                    .eq("patientcode", param.getPatientCode())
                                    .eq("projects_id", feeItem.getId())
                                    .eq("is_delete", 0)
                                    .eq("handle_type", 2));
                            // 不存在
                            if (null == handleNewProjects) {
                                handleNewProjects = new HandleNewProjects();
                                handleNewProjects.setPatientcode(param.getPatientCode());
                                handleNewProjects.setCreateId(userId);
                                handleNewProjects.setModifyId(userId);
                                handleNewProjects.setProjectsId(feeItem.getId());// 项目ID
                                handleNewProjects.setIsDelete(0);
                                handleNewProjects.setStatus(0);
                                handleNewProjects.setHandleType(2);
                                handleNewProjectsMapper.insert(handleNewProjects);
                            } else {
                                handleNewProjects.setModifyId(userId);
                                handleNewProjects.setProjectsId(feeItem.getId());// 项目ID
                                // 更新实体类
                                handleNewProjectsMapper.updateById(handleNewProjects);
                            }
                            //直接处理成补检
                            //迟捡、阳性、不合格样本回访
                            FailTotalVisit fvsave = new FailTotalVisit();
                            fvsave.setVisitMainId(visitRes);// 设置与VisitMain关联的id
                            fvsave.setVisitType(0);// 设置为迟捡回访
                            fvsave.setPersoncode(pei.getPatientcode());// 设置体检号
                            fvsave.setVisitId(SecurityUtils.getUsername());// 回访人
                            fvsave.setVisitTime(new Date());
                            fvsave.setMemo("护理登记补检");
                            fvsave.setSflj(2);
                            failTotalVisitMapper.insert(fvsave);
                        } else if (value == 1) {
                            if (ObjectUtils.isEmpty(feeItem.getFTransferedhl7())) {
                                throw new ServiceException(map.getExamfeeitemName() + "收费项目不需要反补检！");
                            }
                            //更新补检人、补检时间、补检状态为null
                            LambdaUpdateWrapper<Peispatientfeeitem> updateWrapper = new LambdaUpdateWrapper<>();
                            updateWrapper.eq(Peispatientfeeitem::getId, feeItem.getId())
                                    .set(Peispatientfeeitem::getBjr, null)
                                    .set(Peispatientfeeitem::getBjsj, null)
                                    .set(Peispatientfeeitem::getFTransferedhl7, null);
                            peispatientfeeitemService.update(updateWrapper);


                            //与迟检、阳性、不合格样本回访表
                            List<VisitMain> main = visitMainMapper.selectList(new QueryWrapper<VisitMain>()
                                    .eq("id_examfeeitem", feeItem.getId())
                                    .eq("patientcode", param.getPatientCode()).eq("is_delete", 0));
                            if (CollectionUtils.isNotEmpty(main)) {
                                for (VisitMain visitMain : main) {
                                    visitMain.setIsDelete(1);
                                }
                                visitMainService.updateBatchById(main);
                            }

                            //检验科加项处理
                            HandleNewProjects handleNewProjects = handleNewProjectsMapper.selectOne(new QueryWrapper<HandleNewProjects>()
                                    .eq("patientcode", param.getPatientCode())
                                    .eq("projects_id", feeItem.getId())
                                    .eq("is_delete", 0)
                                    .eq("handle_type", 2));
                            if (handleNewProjects != null) {
                                //是否已处理：0.未处理 1.已处理
                                if (handleNewProjects.getIsHandle() != null && handleNewProjects.getIsHandle() == 1) {
                                    throw new ServiceException("保存失败：" + feeItem.getExamfeeitemName() + " 收费项目的补检已被检验科处理，请反处理后再反补检！");
                                }
                                handleNewProjectsMapper.deleteById(handleNewProjects);
                            }
                        }
                        break;

                    case 1:
                        break;
                }

            } else {
                throw new ServiceException(map.getExamfeeitemName() + "收费项目不存在，已经被删除！");
            }
        }

        /**修改总检标志*/
        if (value == 0) {//补检
            if (outsideMainService.getAllSfxmtzjStatus(param.getPatientCode())) {
                pei.setFReadytofinal(1);//0:已备单 1:分检完成
                peisStateService.setScbs(pei.getPatientcode(), 0);
                pei.setReadytofinalDate(new Date());
                peispatientMapper.updateById(pei);
                List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(param.getPatientCode());
                for (Peispatientfeeitem other : other_items) {
                    //设置未关联科室项目为已检
                    other.setFExaminated(1);
                }
                //批量更新
                peispatientfeeitemService.updateBatchById(other_items);
            }
        } else {//反补检
            pei.setFReadytofinal(0);
            peisStateService.setScbs(pei.getPatientcode(), 0);
            peispatientMapper.updateById(pei);
        }
        return Boolean.TRUE;
    }


    /**
     * 获取自助项目弹窗数据
     *
     * @param patientCode
     * @return
     */
    @Override
    public List<PopDataVo> getPopData(String patientCode) {
        //获取自助项目id
        NursingRegistration nursingRegistration = sysConfigService.getSysConfigObject(Constants.NURSING_REGISTRATION_POPID, NursingRegistration.class);
        if (ObjectUtils.isNotEmpty(nursingRegistration)) {
            List<NursingRegistrationDto> param = nursingRegistration.getParam();
            List<String> idList = param.stream()
                    .map(NursingRegistrationDto::getId) // 提取 id 字段
                    .collect(Collectors.toList()); // 转为 List<String> 集合
            //已检的就不出现在里面
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(new QueryWrapper<Peispatientfeeitem>()
                    .orderByAsc("qty")
                    .eq("id_patient", patientCode)
                    .in("id_examfeeitem", idList)
                    .eq("f_examinated", 0)
                    .eq("change_item", 0)
                    .ne("f_giveup", 1)
                    .isNull("f_transferedhl7") //不存在补检
            );
            //只需要id和名称
            List<PopDataVo> popDataVos = mapperFacade.mapAsList(peispatientfeeitems, PopDataVo.class);
            return popDataVos;
        } else {
            return null;
        }
    }


    /**
     * 设置获取自助项目弹窗数据
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setPopData(List<String> ids) {
        List<Peispatientfeeitem> list = new ArrayList<>();
        for (String id : ids) {
            Peispatientfeeitem peispatientfeeitem = peispatientfeeitemMapper.getInfoById(id);
            if (ObjectUtils.isEmpty(peispatientfeeitem)) {
                throw new ServiceException(id + "id错误!");
            }
            //设为已检
            peispatientfeeitem.setFExaminated(1);
            list.add(peispatientfeeitem);
        }
        peispatientfeeitemService.updateBatchById(list);

        //判断是否能分拣完成
        if (CollectionUtils.isNotEmpty(list)){
            //非功能科室自动已检
            String patientCode = list.get(0).getIdPatient();
            if (outsideMainService.getAllSfxmtzjStatus(patientCode)) {
                Peispatient gwry = peispatientMapper.getByPatientCode(patientCode);
                gwry.setFReadytofinal(1);//0:已备单 1:分检完成
                peisStateService.setScbs(gwry.getPatientcode(), 0);
                gwry.setReadytofinalDate(new Date());
                List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientCode);
                for (Peispatientfeeitem other : other_items) {
                    other.setFExaminated(1);//设置未关联科室项目为已检
                }
                peispatientfeeitemService.updateBatchById(other_items);
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 自助项目弃检
     *
     * @param ids
     * @return
     */
    @Override
    public Boolean popGiveUp(List<String> ids, String path) {
        List<Peispatientfeeitem> list = new ArrayList<>();
        for (String id : ids) {
            Peispatientfeeitem peispatientfeeitem = peispatientfeeitemMapper.getInfoById(id);
            if (ObjectUtils.isEmpty(peispatientfeeitem)) {
                throw new ServiceException(id + "id错误!");
            }
            //设为弃检
            peispatientfeeitem.setFGiveup(1);
            //弃检图片暂时放在拒检图片里面
            peispatientfeeitem.setJjrqm(path);
            peispatientfeeitem.setQjsj(new Date());
            peispatientfeeitem.setQjr(SecurityUtils.getUsername());
            list.add(peispatientfeeitem);
        }
        peispatientfeeitemService.updateBatchById(list);

        if (CollectionUtils.isNotEmpty(list)){
            //非功能科室自动已检
            String patientCode = list.get(0).getIdPatient();
            Peispatient pei = peispatientMapper.getByPatientCode(patientCode);
            peispatientfeeitemService.checkFj(pei);
        }

        return Boolean.TRUE;
    }


    /**
     * 上传第三方报告
     *
     * @param file
     * @param feeItemId
     * @param patientcode
     * @return
     */
    @Override
    public Boolean uploadThirdReports(MultipartFile file, String feeItemId, String patientcode, String cId) {
        if (ObjectUtils.isEmpty(file)) {
            throw new ServiceException("文件不能为空");
        }
//        //删除原文件
//        deleteThirdReports(feeItemId, patientcode);
        //淮南第三方报告要求只能上传一个
        if (StringUtils.equals(loadProperties.name, "hn")) {
            Long count = attachmentService.count(new LambdaQueryWrapper<Attachment>()
                    .eq(Attachment::getPatientcode, patientcode)
                    .eq(Attachment::getFeeItemId, feeItemId)
                    .eq(Attachment::getFileType, "第三方报告")
            );
            if (count > 0) {
                throw new ServiceException("一个项目只能上传一个第三方报告!");
            }
        }
        //添加新文件
        String baseDir = systemConfig.getFilePathConfig(FilePathConfigFlag.MFP.value());
        Attachment attachment = new Attachment();
        String originalFilename = file.getOriginalFilename();
        String extName = FileUtil.extName(originalFilename);
        attachment.setFileType("第三方报告");
        attachment.setType(AttachmentType.FILE.value());
        attachment.setBranchId(cId);
        attachment.setCreatedate(new Date());
        attachment.setStatus(0);
        attachment.setFeeItemId(feeItemId);
        attachment.setPatientcode(patientcode);
        attachmentService.save(attachment);
        try {
            attachmentService.uploadFile(file, attachment, baseDir, extName, null, true);
        } catch (IOException e) {
            throw new ServiceException("第三方报告上传保存失败！");
        }
        log.info("上传结果：{}、{}", attachment.getId(), attachment.getFilePath());


        //-----------------pdf拆分成图片操作-----------------
        try {
            // 加载 PDF 文档
            PDDocument document = PDDocument.load(file.getInputStream());
            // 创建 PDF 渲染器
            PDFRenderer renderer = new PDFRenderer(document);
            // 遍历每一页，将其转换为图片
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                // 渲染当前页为 BufferedImage
                BufferedImage image = renderer.renderImageWithDPI(i, 100, ImageType.RGB);
//                BufferedImage image = renderer.renderImage(i, 0.5f); // 缩放比例为 0.5
                // 生成唯一的文件名
                String filename = i + ".png";
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "PNG", baos);
                byte[] imageBytes = baos.toByteArray();
                MultipartFile multipartFile = new MockMultipartFile(filename, filename, "image/png", imageBytes);
                //保存图片
                upImage(multipartFile, feeItemId, patientcode, i, baseDir, cId , attachment.getFilePath());
            }
//            // 关闭文档
//            document.close();
        } catch (Exception e) {
            log.error("pdf拆分成图片操作:", e);
        }

        //查看有无外送图片记录，没有的话插入一条外送图片记录，要不然进不了总检
        String idExamfeeitem = peispatientfeeitemService.getInfoById(feeItemId).getIdExamfeeitem();
        long count = outsidePictrueService.count(new LambdaQueryWrapper<OutsidePictrue>()
                .eq(OutsidePictrue::getPatientcode, patientcode)
                .eq(OutsidePictrue::getChargeId, idExamfeeitem)
        );
        if (count == 0L){
            OutsidePictrue outsidePictrue = new OutsidePictrue();
            outsidePictrue.setPatientcode(patientcode);
            outsidePictrue.setChargeId(idExamfeeitem);
            outsidePictrueService.save(outsidePictrue);
        }

        //是否能分拣完成
        Peispatient pei = peispatientMapper.getByPatientCode(patientcode);
        peispatientfeeitemService.checkFj(pei);


        return Boolean.TRUE;
    }


    private void upImage(MultipartFile multipartFile, String feeItemId, String patientcode, int i, String baseDir, String cId,String filePath) {
        Attachment attachmentImage = new Attachment();
        String extNameImage = "png";
        attachmentImage.setFileType("第三方报告图片");
        attachmentImage.setType(AttachmentType.IMAGE.value());
        attachmentImage.setBranchId(cId);
        attachmentImage.setCreatedate(new Date());
        attachmentImage.setStatus(0);
        attachmentImage.setFeeItemId(feeItemId);
        attachmentImage.setPatientcode(patientcode);
        attachmentImage.setFileSort(i + "");
        attachmentImage.setInReport(1);
        attachmentImage.setMemo(filePath);
        try {
            attachmentService.uploadFile(multipartFile, attachmentImage, baseDir, extNameImage, null, true);
        } catch (IOException e) {
            throw new ServiceException("第三方报告上传保存失败！");
        }
    }




    /**
     * 删除第三方系统报告
     *
     * @param feeItemId
     * @param patientcode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteThirdReports(String feeItemId, String patientcode , String filePath) {
        //删除原文件pdf
        if (StringUtils.isEmpty(filePath)) {
            throw new ServiceException("请输入文件地址！");
        }
        attachmentService.deleteFile(filePath);

        //删除pdf生成的图片
        List<Attachment> AttachmentImages = attachmentService.list(new LambdaQueryWrapper<Attachment>()
                .eq(Attachment::getPatientcode, patientcode)
                .eq(Attachment::getFeeItemId, feeItemId)
                .eq(Attachment::getType, AttachmentType.IMAGE.value())
                .eq(Attachment::getFileType, "第三方报告图片")
                .eq(Attachment::getMemo, filePath)
        );
        if (ObjectUtils.isNotEmpty(AttachmentImages)) {
            for (Attachment attachmentImage : AttachmentImages) {
                attachmentService.deleteFile(attachmentImage.getFilePath());
            }
        }

        //如果没有pdf文件的话，删除外送图片记录
        Long count = attachmentService.count(new LambdaQueryWrapper<Attachment>()
                .eq(Attachment::getPatientcode, patientcode)
                .eq(Attachment::getFeeItemId, feeItemId)
                .eq(Attachment::getFileType, "第三方报告")
        );
        if (count == 0L){
            outsidePictrueService.remove(new LambdaQueryWrapper<OutsidePictrue>()
                    .eq(OutsidePictrue::getPatientcode, patientcode)
                    .eq(OutsidePictrue::getChargeId, peispatientfeeitemService.getInfoById(feeItemId).getIdExamfeeitem())
            );
        }

        return Boolean.TRUE;
    }

    /**
     * 修改已检或未检
     * @param param
     * @return
     */
    @Override
    public Boolean modifyProjectStatus(ModifyStatusParam param) {
        LambdaUpdateWrapper<Peispatientfeeitem> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.in(Peispatientfeeitem::getId, param.getIds())
                .set(Peispatientfeeitem::getFExaminated,param.getFExaminated());
        //已检保存操作人，未检清空
        if (param.getFExaminated()==1){
            lambdaUpdateWrapper.set(Peispatientfeeitem::getIdExamdoctor,SecurityUtils.getUserNo())
                    .set(Peispatientfeeitem::getExamdoctorNameR,SecurityUtils.getUsername());
        }else {
            lambdaUpdateWrapper.set(Peispatientfeeitem::getIdExamdoctor,null)
                    .set(Peispatientfeeitem::getExamdoctorNameR,null);
        }
        peispatientfeeitemService.update(null, lambdaUpdateWrapper);

        //点已检以后，非功能科室未检项目自动已检
        if (param.getFExaminated()==1){
            List<Peispatientfeeitem> peispatientfeeitems = peispatientfeeitemMapper.selectList(new LambdaUpdateWrapper<Peispatientfeeitem>().in(Peispatientfeeitem::getId, param.getIds()));
            if (CollectionUtils.isNotEmpty(peispatientfeeitems)){
                String patientCode = peispatientfeeitems.get(0).getIdPatient();
                if (outsideMainService.getAllSfxmtzjStatus(patientCode)) {
                    Peispatient gwry = peispatientMapper.getByPatientCode(patientCode);
                    gwry.setFReadytofinal(1);//0:已备单 1:分检完成
                    peisStateService.setScbs(gwry.getPatientcode(), 0);
                    gwry.setReadytofinalDate(new Date());
                    List<Peispatientfeeitem> other_items = peispatientfeeitemMapper.getNoCheckItems(patientCode);
                    for (Peispatientfeeitem other : other_items) {
                        other.setFExaminated(1);//设置未关联科室项目为已检
                    }
                    peispatientfeeitemService.updateBatchById(other_items);
                }
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 查看第三方图片
     * @param patientCode
     * @return
     */
    @Override
    public List<ViewThirdPartyImagesVo> viewThirdPartyImages(String patientCode) {
        List<ThirdPartyImagesVo> thirdPartyReport = attachmentService.getThirdPartyReport(patientCode);
        //数据转换一下
        List<ViewThirdPartyImagesVo> list = thirdPartyReport.stream()
                .collect(Collectors.groupingBy(ThirdPartyImagesVo::getId))
                .entrySet().stream()
                .map(entry -> {
                    ViewThirdPartyImagesVo vo = new ViewThirdPartyImagesVo();
                    vo.setId(entry.getKey());
                    vo.setPatientcode(entry.getValue().get(0).getPatientcode());
                    vo.setExamfeeitemName(entry.getValue().get(0).getExamfeeitemName());
                    vo.setPdfUrl(entry.getValue().get(0).getPdfUrl());
                    // 合并所有具有相同 id 的 filePath
                    List<String> filePaths = entry.getValue().stream()
                            .map(ThirdPartyImagesVo::getFilePath)
                            .collect(Collectors.toList());

                    vo.setFilePath(filePaths);
                    return vo;
                })
                .collect(Collectors.toList());
        return list;
    }
}

