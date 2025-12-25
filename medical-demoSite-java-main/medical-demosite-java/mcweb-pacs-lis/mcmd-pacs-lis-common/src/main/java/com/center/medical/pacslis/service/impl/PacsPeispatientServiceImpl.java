package com.center.medical.pacslis.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.enums.MarriageType;
import com.center.medical.bean.model.*;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.CodeUtil;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.*;
import com.center.medical.data.dao.HarmMapper;
import com.center.medical.pacslis.bean.dto.ChartDto;
import com.center.medical.pacslis.bean.dto.GriddataDto;
import com.center.medical.pacslis.bean.dto.PrFormdataDto;
import com.center.medical.pacslis.bean.dto.PrGriddataDto;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.pacslis.bean.model.PacsPeispatientfeeitem;
import com.center.medical.pacslis.bean.param.*;
import com.center.medical.pacslis.bean.vo.ChartVo;
import com.center.medical.pacslis.bean.vo.DivisionVo;
import com.center.medical.pacslis.bean.vo.ItemListVo;
import com.center.medical.pacslis.bean.vo.ReservationUserVo;
import com.center.medical.pacslis.dao.PacsPeispatientMapper;
import com.center.medical.pacslis.dao.PacsPeispatientfeeitemMapper;
import com.center.medical.pacslis.service.PacsPeispatientService;
import com.center.medical.reception.dao.ReviewMapper;
import com.center.medical.sellcrm.service.CreatecomboService;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.service.PeispatientfeeitemService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysDeptMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import com.center.medical.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * PACS-体检者表(PacsPeispatient)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:08
 */
@Slf4j
@Service("pacsPeispatientService")
@RequiredArgsConstructor
public class PacsPeispatientServiceImpl extends ServiceImpl<PacsPeispatientMapper, PacsPeispatient> implements PacsPeispatientService {

    private final PacsPeispatientMapper pacsPeispatientMapper;
    private final ISysBranchService sysBranchService;
    private final HarmMapper harmMapper;
    private final PacsPeispatientfeeitemMapper pacsPeispatientfeeitemMapper;
    private final CreatecomboService createcomboService;
    private final MapperFacade mapperFacade;
    private final PeispatientfeeitemService peispatientfeeitemService;
    private final ReviewMapper reviewMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SysBranchMapper sysBranchMapper;
    private final DictmarriageMapper dictmarriageMapper;
    private final ISysUserService sysUserService;
    private final ISysConfigService iSysConfigService;
    private final PeispatientarchiveService peispatientarchiveService;
    private final PacsInspectChargeMapper pacsInspectChargeMapper;
    private final PacsItemMapper pacsItemMapper;
    private final PacsBasexamltemsMapper pacsBasexamltemsMapper;
    private final ISysBranchService iSysBranchService;
    private final PeispatientMapper peispatientMapper;


    /**
     * 分页查询[PACS-体检者表]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatient查询参数
     * @return 分页数据
     */
    @Override
    public ItemListVo getPage(PageParam<PacsPeispatient> page, ItemListParam param) {
        //设置开始结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            param.setStartTime(DateUtil.beginOfDay(param.getStartTime()));
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            param.setEndTime(DateUtil.endOfDay(param.getEndTime()));
        }
        IPage<PacsPeispatient> page1 = pacsPeispatientMapper.getPage(page, param);

        //图表数据
        List<ChartDto> list = pacsPeispatientMapper.getChart(param.getStartTime(), param.getEndTime());
        Integer jk = 0;
        Integer zy = 0;
        for (ChartDto chartDto : list) {
            //0健康1职业
            if ("0".equals(chartDto.getIdExamtype())) {
                jk = chartDto.getCount();
            } else if ("1".equals(chartDto.getIdExamtype())) {
                zy = chartDto.getCount();
            }
        }
        Integer total = jk + zy;
        //返回数据
        ChartVo chartVo = new ChartVo(jk, zy, total);
        ItemListVo itemListVo = new ItemListVo();
        itemListVo.setPage(page1);
        itemListVo.setChart(chartVo);
        return itemListVo;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public PacsPeispatient getInfoById(String id) {
        return pacsPeispatientMapper.getInfoById(id);
    }


    /**
     * PACS-彩超审核查看分页
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<DivisionVo> getDivPage(PageParam<PacsPeispatient> page, DivisionParam param) {
        //设置开始结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            param.setStartTime(DateUtil.beginOfDay(param.getStartTime()));
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            param.setEndTime(DateUtil.endOfDay(param.getEndTime()));
        }
        //科室id
        param.setKsId("143");
        IPage<DivisionVo> page1 = pacsPeispatientMapper.getDivPage(page, param);
        return page1;
    }


    /**
     * 取得已预约客户
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public PageParam<ReservationUserVo> getReservationUser(PageParam<PacsPeispatient> page, ReservationUserParam param) {
        return pacsPeispatientMapper.getReservationUser(page, param);
    }


    /**
     * 获取当前选中的已预约用户信息
     *
     * @param patientCode
     * @param type
     * @param isChecked
     * @return
     */
    @Override
    public HashMap getCustomerData(String patientCode, String type, String isChecked) {
        HashMap map = new HashMap();
        PacsPeispatient p = getPeispatient(patientCode, isChecked);
        map.put("patientData", p);
        if (ObjectUtils.isEmpty(p)) {
            return map;
        }
        // 接害因素
        map.put("jhysn", StringUtils.isNotEmpty(p.getJhys()) ? harmMapper.getHarmText(p.getJhys().split(",")) : "");
        // 获取右侧收费项目
        map.put("examfeeitemData", this.getExamfeeByPatientCode(p.getPatientcode(), type));

        return map;
    }


    /**
     * 根据体检号查询体检者信息
     *
     * @param patientCode
     * @param isChecked
     * @return
     */
    @Override
    public PacsPeispatient getPeispatient(String patientCode, String isChecked) {
        if (StringUtils.isBlank(patientCode)) {
            return new PacsPeispatient();
        }
        // 匹配体检号
        if ("true".equals(isChecked)) {
            patientCode = ToolUtil.patientCode(patientCode, iSysBranchService.getBranchFlag(null));
        }

        PacsPeispatient peispatient = pacsPeispatientMapper.selectOne(new QueryWrapper<PacsPeispatient>().eq("patientcode", patientCode));

        return peispatient;
    }


    /**
     * 获取右侧收费项目
     *
     * @param patientCode
     * @param type
     * @return
     */
    @Override
    public List<PacsPeispatientfeeitem> getExamfeeByPatientCode(String patientCode, String type) {
        QueryWrapper<PacsPeispatientfeeitem> queryWrapper = new QueryWrapper<>();

        if ("0".equals(type)) {
            // 全部显示
            queryWrapper.orderByAsc("createdate");
        } else if ("1".equals(type)) {
            // 显示除去退项的
            queryWrapper.eq("change_item", 0);
            queryWrapper.orderByAsc("createdate");
        } else if ("2".equals(type)) {
            // 显示退项的
            queryWrapper.eq("change_item", 1);
            queryWrapper.orderByAsc("modifydate");
        }
        List<PacsPeispatientfeeitem> list = pacsPeispatientfeeitemMapper.selectList(queryWrapper.eq("id_patient", patientCode));
        return list;
    }


    /**
     * 团检退项匹配最小套餐
     *
     * @param param
     * @return
     */
    @Override
    public Boolean compareMinTcContent(ComMinParam param) {
        List<String> idss = param.getIds();
        List<GriddataDto> gForm = param.getGriddata();
        Map<String, String> info = new HashMap<String, String>();
        if (gForm.size() > 0) {
            if (idss.size() > 0) {
                String text = "";
                for (int i = 0, l = gForm.size(); i < l; i++) {
                    //体检类型
                    String idExamtype = gForm.get(i).getIdExamtype();
                    // 健康除外
                    if ("0".equals(idExamtype)) {
                        continue;
                    }
                    String result = createcomboService.compareMinTcContent(
                            gForm.get(i).getJhys(), idss,
                            gForm.get(i).getMedicaltype());
                    if (!StringUtils.isBlank(result)) {
                        text += "第" + (i + 1) + "个体检者的" + result + "收费项目存在于最小套餐内>";
                    }
                }
                if (StringUtils.isBlank(text)) {
                    return Boolean.TRUE;
                } else {
                    throw new ServiceException(text);
                }
            } else {
                throw new ServiceException("请选择一条收费项目");

            }
        } else {
            throw new ServiceException("请选择一条体检者");
        }
    }


    /**
     * 得到备单人员的信息
     *
     * @param id
     * @param patientCode
     * @return
     */
    @Override
    public HashMap getPatientData(String id, String patientCode) {
        // 保存页面所需要的值
        HashMap map = new HashMap();
        QueryWrapper<PacsPeispatient> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isBlank(patientCode)) {
            // 从登记列表跳转的数据
            queryWrapper.eq("patientcode", patientCode);
            if ("-1".equals(patientCode)) {
                queryWrapper.eq("id", id);
            }
            // 已登记的信息
            PacsPeispatient peispatient = pacsPeispatientMapper.selectOne(queryWrapper);
            map.put("location", 0);
            if (ObjectUtils.isNotEmpty(peispatient)) {
                map.put("patientData", peispatient);
                //增加职业必检列
                List<PacsPeispatientfeeitem> itemData = this.getExamfeeByPatientCode(peispatient.getPatientcode(), "1");
                map.put("examfeeitemData", itemData);// 收费项目
                map.put("jhysn", StringUtils.isNotEmpty(peispatient.getJhys()) ? harmMapper.getHarmText(peispatient.getJhys().split(",")) : "");// 接害因素
            } else {
                throw new ServiceException("体检者" + patientCode + "不存在！");
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


    /**
     * 根据体检号查询不同状态的收费项目
     *
     * @param type
     * @param patientCode
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HashMap getKindItems(String type, String patientCode) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if ("0".equals(type)) {
            // 查询复查项目
            map.put("examfeeitemData", reviewMapper.getRecheckItems(patientCode));
        } else if ("1".equals(type)) {
            // 补检项目
            List<PacsPeispatientfeeitem> peispatientfeeitems = pacsPeispatientfeeitemMapper.selectList(new QueryWrapper<PacsPeispatientfeeitem>()
                    .eq("FRegistered", 1).eq("FFeecharged", 1).eq("idPatient", patientCode).eq("FTransferedhl7", 0));

            List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

            for (PacsPeispatientfeeitem peispatientfeeitem : peispatientfeeitems) {
                Map<String, Object> itemMap = new HashMap<String, Object>();
                itemMap.put("idExamfeeitem", peispatientfeeitem.getIdExamfeeitem());
                itemMap.put("examfeeitemName", peispatientfeeitem.getExamfeeitemName());
                itemMap.put("price", peispatientfeeitem.getPrice());
                itemMap.put("factprice", peispatientfeeitem.getPrice());
                itemMap.put("count", 1);
                itemMap.put("idPayway", 1);
                itemMap.put("FRegistered", 0);
                itemMap.put("changeItem", 0);
                itemMap.put("FMarkFeereturn", 0);
                itemMap.put("FFeecharged", 0);
                itemMap.put("FLabsendtolis", 0);
                itemMap.put("FExaminated", 0);
                itemMap.put("FGiveup", 0);
                itemMap.put("FDelayed", 0);
                itemMap.put("isMintc", 0);
                itemMap.put("idKs", peispatientfeeitem.getIdKs());
                itemMap.put("isbx", 0);
                itemMap.put("bxcount", 0);
                items.add(itemMap);
                // 设置补检项目已补检
                peispatientfeeitem.setFTransferedhl7(1.0);
                pacsPeispatientfeeitemMapper.updateById(peispatientfeeitem);
            }
            map.put("examfeeitemData", items);
            PacsPeispatient pei = pacsPeispatientMapper.selectOne(new QueryWrapper<PacsPeispatient>().eq("patientcode", patientCode));
            if (ObjectUtils.isEmpty(pei)) {
                map.put("noman", 0);
                throw new ServiceException("体检者表数据为空");
            } else map.put("noman", 1);

            // 检查剩余收费项目是否可以分拣完成
            checkFj(pei);
        }
        return map;
    }


    /**
     * 查找剩余的未检收费项目是否是弃检、迟检、退项状态判断是否需要分检完成，无科室的检查完
     *
     * @param pei
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkFj(PacsPeispatient pei) {
        // 体检者不存在
        if (ObjectUtils.isEmpty(pei)) {
            return;
        }
        //获取功能科室id
        List<String> depIds = sysDeptMapper.getFunctionKsIds(1);
        // 查找未检、未弃检、未退项、存在科室的收费项目数量
        List<PacsPeispatientfeeitem> peispatientfeeitems = depIds.size() == 0 ? new ArrayList<PacsPeispatientfeeitem>()
                : pacsPeispatientfeeitemMapper.selectList(new QueryWrapper<PacsPeispatientfeeitem>()
                .in("id_ks", depIds).eq("id_patient", pei.getPatientcode())
                .eq("change_item", 0)
                .eq("f_giveup", 0)
                .isNull("sfjj")
                .isNotNull("id_ks")//不关联科室的（收费项目只能管理功能科室）
                .isNull("f_transferedhl7")// 不存在补检
                .eq("f_examinated", 0));//未检

        if (CollectionUtil.isEmpty(peispatientfeeitems)) {
            //分检完成
            pei.setFReadytofinal(1);
            //上传标示
            pei.setScbs(0);
            //分拣完成时间
            pei.setReadytofinalDate(new Date());
            // 无关联科室已检
            List<PacsPeispatientfeeitem> others = pacsPeispatientfeeitemMapper.selectOthers(pei.getPatientcode());
            for (PacsPeispatientfeeitem peispatientfeeitem : others) {
                peispatientfeeitem.setFExaminated(1);//设置未关联科室项目为已检,反审核时不改回去
                // 更新收费实体类
                pacsPeispatientfeeitemMapper.updateById(peispatientfeeitem);
            }
        } else {
            //分检未完成
            pei.setFReadytofinal(0);
            pei.setScbs(0);
        }
        updateById(pei);
    }


    /**
     * 登记保存右上列表
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(PrSaOrUpParam param) throws Exception {
        // 保存左侧数据（预约）
        String patientCode = null;
        patientCode = saveOrUpdatePatientData(param);
        if (StringUtils.isBlank(patientCode)) {
            throw new ServiceException("保存失败,系统发生异常！");
        }
        return Boolean.TRUE;
    }


    /**
     * @param param
     * @return
     * @Title: 保存或更新体检者信息和创建档案
     */

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String saveOrUpdatePatientData(PrSaOrUpParam param) throws Exception {
        //取出属性
        PrFormdataDto mData = param.getFormData();
        int intReservation = param.getIntReservation();
        List<PrGriddataDto> griddata = param.getGriddata();
        //对象与对象之间的转化，快速赋值
        PacsPeispatient peispatient = mapperFacade.map(mData, PacsPeispatient.class);

        //分中心id
        SysBranch branch = sysBranchMapper.selectOne(new QueryWrapper<SysBranch>().eq("is_default", 1).eq("is_delete", 0));
        peispatient.setHospitalcode(ObjectUtils.isEmpty(branch) ? null : branch.getId() + "");

        peispatient.setFPaused(0);
        //仅第一次生成时设置标志，防止体检系统体检号到这个页面登记被打上标志
        if (ObjectUtils.isEmpty(peispatient.getId())) {
            peispatient.setIdExamplace(1);//pacs页面登记的
        }
        // 其他字段保存
        // 登记员名称
        peispatient.setDoctorreg(SecurityUtils.getUsername());
        // 性别
        peispatient.setSex(mData.getIdSex().equals("0") ? "男" : "女");
        // 婚姻
        peispatient.setMarriage(ObjectUtils.isEmpty(peispatient.getIdMarriage()) ? "" : MarriageType.getName(Integer.valueOf(peispatient.getIdMarriage())));
//        Dictmarriage dictmarriage = dictmarriageMapper.getInfoById(peispatient.getIdMarriage());
//        peispatient.setMarriage(ObjectUtils.isEmpty(dictmarriage)?"":dictmarriage.getMarriageName());

        // 日期多8小时处理
        // 体检日期
        peispatient.setMedicaldate(subTime(peispatient.getMedicaldate()));
        peispatient.setBirthdate(subTime(peispatient.getBirthdate()));
        // 根据总工龄计算参加工作时间
        if (0 != peispatient.getZgl() || ObjectUtils.isEmpty(peispatient.getWorkDate())) {
            peispatient.setWorkDate(getDateForMonth(peispatient.getZgl()));
        } else {
            peispatient.setZgl(getMonthSpace(new Date(), subTime(peispatient.getWorkDate())));
        }
        // 根据接害工龄计算从事该工种工作时间
        if (0 != peispatient.getJhgl() || ObjectUtils.isEmpty(peispatient.getHarmDate())) {
            peispatient.setHarmDate(getDateForMonth(peispatient.getJhgl()));
        } else {
            peispatient.setJhgl(getMonthSpace(new Date(), subTime(peispatient.getHarmDate())));
        }
        // 时间
        peispatient.setDateguidancereturned(subTime(peispatient.getDateguidancereturned()));
        // 判断身份证、性别、年龄是否相符
        if (!StringUtils.isBlank(peispatient.getIdcardno()) && peispatient.getCountreportoccupationxml() != null
                && peispatient.getCountreportoccupationxml() == 1) {
            String card = peispatient.getIdcardno();
            // 如果长度是15位
            if (card.length() == 15) {
                card = card.substring(0, 6) + "19" + card.substring(6) + "x";
            }
            // 年龄匹配
            peispatient.setAge(IdcardUtil.getAgeByIdCard(card));
            // 生日匹配
            peispatient.setBirthdate(IdcardUtil.getBirthDate(card));
            // 匹配性别
            card = card.substring(card.length() - 2, card.length() - 1);
            // 性别是否匹配
            String strSex = (Integer.valueOf(card) & 1) != 0 ? "0" : "1";
            if (!strSex.equals(peispatient.getIdSex())) {
                throw new ServiceException("保存失败：该体检者的身份证号与性别不匹配");
            }
            peispatient.setIdSex(strSex);
            peispatient.setSex((Integer.valueOf(card) & 1) != 0 ? "男" : "女");

        }
        // 判断是否存在团检任务中
        // 判断人员信息是否重复添加
        if (!StringUtils.isBlank(peispatient.getIdOrg())
                && !"3".equals(peispatient.getIdExamtype())
                && peispatient.getIdOrgreservationgroup() != null) {//复查 和没分组的不判断
            QueryWrapper<PacsPeispatient> and = new QueryWrapper<>();

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
                // 姓名
                and.eq("patientname", peispatient.getPatientname());
            }
            // 不与自身体检号比较
            if (!StringUtils.isBlank(peispatient.getPatientcode())) {
                and.ne("patientcode", peispatient.getPatientcode());
            } else if (!StringUtils.isBlank(peispatient.getId())) {
                and.ne("id", peispatient.getId());
            }
        }

        // 中间库状态 -1:保存 0：第一次登记 1：重新登记 2:增项
        int sataus = -1;

        PacsPeispatient pNew = pacsPeispatientMapper.selectOne(new QueryWrapper<PacsPeispatient>()
                .eq("patientcode", peispatient.getPatientcode()));
        // 是否预约
        if (0 == intReservation) {
            // 登记后不可以预约
            if (null != pNew) {
                if (null != pNew.getFRegistered() && 1 == pNew.getFRegistered()) {
                    throw new ServiceException("完成预约失败：该体检者 " + peispatient.getPatientname() + " 已经登记不可以预约！");
                }
                if (null != pNew.getFIsforreserve() && 1 == pNew.getFIsforreserve()) {
                    throw new ServiceException("完成预约失败：该体检者 " + peispatient.getPatientname() + " 已经预约不可以重复预约！");
                }
            } else {
                Date preDate = null;
                synchronized (sysUserService) {
                    preDate = getPreno(new SimpleDateFormat("yyyy-MM-dd").format(peispatient.getDateguidancereturned()));
                }
                //预约时间
                peispatient.setDateguidancereturned(preDate);
            }
            //预约：1.预约
            peispatient.setFIsforreserve(1);
        } else if (1 == intReservation) {
            // 登记后不可以再次登记
            if (ObjectUtils.isNotEmpty(pNew)) {
                if (ObjectUtils.isNotEmpty(pNew.getFRegistered()) && 1 == pNew.getFRegistered()) {
                    throw new ServiceException("完成登记失败：该体检者 " + peispatient.getPatientname() + " 已经登记不可以再次登记！");
                }
            }
            // 判断是否已登记过
            if (ObjectUtils.isNotEmpty(peispatient.getDateregister()) && !StringUtils.isBlank(peispatient.getPatientcode())) {
                // 已登记过
                sataus = 1;
            } else if (ObjectUtils.isEmpty(peispatient.getDateregister()) && ObjectUtils.isEmpty(peispatient.getFRegistered())) {
                // 第一次登记
                sataus = 0;
            }
            // 已登记
            peispatient.setFRegistered(1);
            // 登记日期
            peispatient.setDateregister(new Date());
        } else {
            if (ObjectUtils.isNotEmpty(pNew)) {
                if (ObjectUtils.isNotEmpty(pNew.getFRegistered()) && 1 == pNew.getFRegistered()) {
                    sataus = 2;
                }
            }
        }

        // 绑定档案
        String bindResult = bingIArchive(peispatient, mData);
        String idPatientarchive = "";
        String patientarchiveno = "";
        if (bindResult.indexOf("success") == 0) {
            idPatientarchive = bindResult.substring(8, 40);
            patientarchiveno = bindResult.substring(43);
            mData.setPatientarchiveno(patientarchiveno);
        } else {
            throw new ServiceException("保存失败：该体检者 " + peispatient.getPatientname() + " 档案绑定失败！");
        }

        // 体检号
        String patientCode = "";
        String userId = SecurityUtils.getUserNo();
        peispatient.setScbs(0);
        // 判断是否为空
        if (StringUtils.isBlank(peispatient.getId()) && StringUtils.isBlank(peispatient.getPatientcode())) {
            // 生成体检号
            patientCode = getMaxPatientcodePacs();
            if (isExistByPatientCode(patientCode)) {
                throw new ServiceException("保存失败：生成体检号失败！");
            }
            peispatient.setPatientcode(patientCode);
            peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
            peispatient.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
            peispatient.setIdPatientarchive(idPatientarchive);
            peispatient.setIdDoctorreg(userId);
            this.save(peispatient);
            String id = peispatient.getId();
            // 保存实体类
            if (StringUtils.isBlank(id)) {
                throw new ServiceException("保存失败：保存体检者信息失败！");
            }
            pNew = peispatient;
        } else {
            // 是否存在
            if (ObjectUtils.isEmpty(pNew)) {
                pNew = pacsPeispatientMapper.getInfoById(peispatient.getId());
                if (ObjectUtils.isEmpty(pNew) || StringUtils.isBlank(peispatient.getId())) {
                    throw new ServiceException("保存失败：体检号不存在，不能保存体检者信息！");
                } else {
                    // 备单人员未提前生成体检号，在此生成
                    // 生成体检号
                    patientCode = getMaxPatientcode();
                    if (isExistByPatientCode(patientCode)) {
                        throw new ServiceException("保存失败：生成体检号失败！");
                    }
                }
            } else {
                patientCode = peispatient.getPatientcode();
            }
            peispatient.setIdPatientarchive(idPatientarchive);
            peispatient.setPatientcode(patientCode);
            peispatient.setShortCode(ToolUtil.getShortCodeByLong(patientCode));
            peispatient.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));
            peispatient.setIdDoctorreg(userId);

            // 更新
            this.updateById(peispatient);
        }

        // 右侧收费项目没有数据，只是预约状态
        if (CollectionUtil.isNotEmpty(griddata)) {
            // 保存右侧收费项目
            String result = saveOrUpdateItem(pNew, griddata, intReservation, mData);
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
        return patientCode + " sataus:" + sataus + " idPatientarchive:" + peispatient.getIdPatientarchive();
    }


    /**
     * 保存右侧收费项目
     *
     * @param pei
     * @param featureData
     * @param intReservation
     * @param mForm
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveOrUpdateItem(PacsPeispatient pei, List<PrGriddataDto> featureData, int intReservation, PrFormdataDto mForm) {
        String patientCode = pei.getPatientcode();
        // 已经登记
        if (ObjectUtils.isNotEmpty(pei.getFRegistered()) && 1 == pei.getFRegistered()) {
            if (0 == intReservation) {
                throw new ServiceException("该体检者已经完成登记，不可再次预约！");
            }
            // 如果已经登记过，说明收费项目是存在的，页面点击“保存”按钮，登记未收费项的项目
            else if (2 == intReservation) {
                intReservation = 1;
            }
        }


        if (CollectionUtil.isEmpty(featureData)) {
            throw new ServiceException("发生异常，请重新再试！");
        } else if (featureData.size() == 0) {
            return "success" + " sataus:" + -1;
        }
        Integer shortCode = pei.getShortCode();
        // 不同的收费项目之间的检查项目也不能重复
        int size = featureData.size();
        List<String> strr = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            // 删除、退项并且已退成功、弃检不参与
            if ("removed".equals(featureData.get(i).getState())) {
                continue;
            }

            String changeItem = featureData.get(i).getChangeItem();
            String FMarkFeereturn = featureData.get(i).getFMarkFeereturn();
            String FGiveup = featureData.get(i).getFGiveup();

            if (!((!StringUtils.isBlank(changeItem) && "1".equals(changeItem)
                    && !StringUtils.isBlank(FMarkFeereturn) && "1".equals(FMarkFeereturn))
                    || (!StringUtils.isBlank(FGiveup) && "1".equals(FGiveup)))) {
                strr.add(featureData.get(i).getIdExamfeeitem());
            }

        }
//        String[] str = new String[strr.size()];
//        str = strr.toArray(str);
        String text = compareItemsToExam(strr);
//        if (!StringUtils.isBlank(text)) {
//            return "fail:" + text;
//        }

        // 团检是否存在现金收费(1:现金 0：统收)
        int isTuanToX = 0;
        int bxcount = 0;


        if (ObjectUtils.isNotEmpty(mForm)) {
            // 团检
            if (StringUtils.isNotEmpty(mForm.getOrg()) && mForm.getOrg().equals("1")) {
                isTuanToX = Double.valueOf(StringUtils.isBlank(mForm.getTongFei()) ? "0" : mForm.getTongFei()) > 0 ? 0 : 1;
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

        // 退项价格
        Double tuiPrice = 0d;
        Double tuiYsPrice = 0d;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        // 未收费项目是否需要变为已收（未收项总和=0，自动收费）
        Double noCharge = 0d;


        for (PrGriddataDto data : featureData) {
            PacsPeispatientfeeitem item = mapperFacade.map(data, PacsPeispatientfeeitem.class);
            // 性别
            item.setFFeechargedinttrans(StringUtils.isEmpty(data.getFFeechargedinttrans()) ? 0 : Integer.valueOf(data.getFFeechargedinttrans()));
            // 弃检
            item.setFGiveup(ObjectUtils.isEmpty(data.getFGiveup()) ? 0 : Integer.valueOf(data.getFGiveup()));
            // 迟检
            item.setFDelayed(ObjectUtils.isEmpty(data.getFDelayed()) ? 0.0 : Double.valueOf(data.getFDelayed()));
            // 体检号
            item.setIdPatient(patientCode);
            item.setShortCode(shortCode);
            item.setBxcount(bxcount);
            // 计算实际金额
            // 不是最小套餐，实际金额 == 优惠价格
            Double shiji = 0d;
            if (yuanshi != 0) {
                shiji = Double.valueOf(decimalFormat.format(tcPrice / yuanshi * item.getPrice()));
            }
            if (ObjectUtils.isEmpty(item.getIsMintc()) || 0 == item.getIsMintc()) {
                shiji = item.getFactprice();
            }
            item.setActualprice(shiji);
            if ("removed".equals(data.getState())) {
                if (ObjectUtils.isNotEmpty(data.getId())) {
                    PacsPeispatientfeeitem feeItem = pacsPeispatientfeeitemMapper.getInfoById(data.getId());
                    // 判断是否存在
                    if (ObjectUtils.isNotEmpty(feeItem)) {
                        // 弃检
                        if (feeItem.getFGiveup() == 1) {
                            throw new ServiceException("删除失败：" + data.getExamfeeitemName() + " 收费项目已经弃检，不能被删除！");
                        }
                        // 已检
                        if (ObjectUtils.isNotEmpty(feeItem.getFExaminated()) && feeItem.getFExaminated() == 1) {
                            throw new ServiceException("删除失败：" + data.getExamfeeitemName() + " 收费项目已经检查，不能被删除！");
                        }
                        // 已收费
                        if (ObjectUtils.isNotEmpty(feeItem.getFFeecharged()) && feeItem.getFFeecharged() == 1) {
                            throw new ServiceException("删除失败：" + data.getExamfeeitemName() + " 收费项目已经收费，不能被删除,只能换项或者弃项！");
                        }
                        // 删除实体类
                        pacsPeispatientfeeitemMapper.deleteById(feeItem);
                    } else {
                        // 不存在
//                        throw new Exception("删除失败：<font color='red'>"+map.get("examfeeitemName") +"</font> 收费项目不存在，已经被删除！");
                    }
                }
            } else if ("modified".equals(data.getState())) {
                //更新
                PacsPeispatientfeeitem feeItem = pacsPeispatientfeeitemMapper.getInfoById(data.getId());
                // 判断是否存在
                if (ObjectUtils.isNotEmpty(feeItem)) {
                    // 退项
                    if (ObjectUtils.isNotEmpty(item.getChangeItem()) && item.getChangeItem() == 1) {
                        // 弃检
                        if (feeItem.getFGiveup() == 1) {
                            throw new ServiceException("保存失败：" + data.getExamfeeitemName() + "收费项目已经弃检，不能退项！");
                        }
                        if (ObjectUtils.isNotEmpty(item.getEndtuiprice()) && (ObjectUtils.isNotEmpty(feeItem.getFMarkFeereturn()) || 0 == feeItem.getFMarkFeereturn())) {
                            tuiPrice += item.getEndtuiprice();
                            tuiYsPrice -= item.getPrice();
                        }
                    }
                    // 弃检
                    if (ObjectUtils.isNotEmpty(item.getFGiveup()) && item.getFGiveup() == 1) {
                        // 退项
                        if (ObjectUtils.isNotEmpty(feeItem.getChangeItem()) && feeItem.getChangeItem() == 1) {
                            throw new ServiceException("保存失败：" + data.getExamfeeitemName() + " 收费项目已经退项，不能弃检！");
                        }
                    }
                    // 已检
                    if (ObjectUtils.isNotEmpty(feeItem.getFExaminated()) && feeItem.getFExaminated() == 1) {
                        // 退项
                        if (ObjectUtils.isNotEmpty(item.getChangeItem()) && item.getChangeItem() == 1) {
                            throw new ServiceException("保存失败：" + data.getExamfeeitemName() + " 收费项目已经检查，不能退项！");
                        }
                        // 弃检
                        if (ObjectUtils.isNotEmpty(item.getFGiveup()) && item.getFGiveup() == 1) {
                            throw new ServiceException("保存失败：" + data.getExamfeeitemName() + " 收费项目已经检查，不能弃检！");
                        }

                    }
                    // 迟检(已经迟检的不再保存)
                    if (ObjectUtils.isNotEmpty(item.getFDelayed()) && item.getFDelayed() == 1 &&
                            (ObjectUtils.isNotEmpty(feeItem.getFDelayed()) || feeItem.getFDelayed() == 0)) {
                        Map<String, Object> m = new HashMap<String, Object>();
                        m.put("patientcode", patientCode);
                        m.put("idExamfeeitem", feeItem.getId());
                        m.put("type", 0);

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
                        SysUser user = sysUserService.selectUserByUserNo(SecurityUtils.getUserNo());
                        item.setDoctorregR(ObjectUtils.isEmpty(user) ? "" : user.getUserName());
                    }

                    // 更新实体类
                    pacsPeispatientfeeitemMapper.updateById(item);
                } else {
                    // 不存在
                    throw new ServiceException("保存失败：" + data.getExamfeeitemName() + " 收费项目不存在，已经被删除！");
                }
            } else if ("added".equals(data.getState())) {
                // 已登记
                if (ObjectUtils.isNotEmpty(pei.getFRegistered()) && pei.getFRegistered() == 1) {
                    // 已登记，增项
                    isataus = 1;
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
                    SysUser user = sysUserService.selectUserByUserNo(SecurityUtils.getUserNo());
                    item.setDoctorregR(ObjectUtils.isEmpty(user) ? "" : user.getUserName());
                }

                // 团检是否存在现金收费
                if (1 == intReservation && isTuanToX == 0 && item.getIdPayway().equals("5")) {
                    // 统收不存在加项收费信息保存
                    item.setFFeecharged(1);// 已收费
                    item.setIdFeecharger(SecurityUtils.getUserNo());
                    item.setFeechargetime(new Date());
                }
                // 保存实体类
                int i = pacsPeispatientfeeitemMapper.insert(item);
                if (i == 0) {
                    throw new ServiceException(data.getExamfeeitemName() + "收费项目保存失败！");
                }
            }
        }

        // 检查剩余收费项目是否可以分拣完成
        checkFj(pei);

        return "success" + " sataus:" + isataus;
    }


    /**
     * 获取grid收费项目列表中是否存在相同的检查项目
     *
     * @param strr
     * @return
     */
    public String compareItemsToExam(List<String> strr) {
        if (CollectionUtil.isEmpty(strr)) {
            return "";
        }
        StringBuffer text = new StringBuffer();
        // 调用存储过程
        Map<String, String> map = new HashMap<String, String>();
        List<PacsInspectCharge> inspectCharges = pacsInspectChargeMapper.selectList(new QueryWrapper<PacsInspectCharge>()
                .in("charge_id", strr).eq("is_delete", 0));
        for (PacsInspectCharge inspectCharge : inspectCharges) {
            List<String> items = pacsInspectChargeMapper.getRepeatItems(inspectCharge.getInspectId(), strr);
            if (items.size() <= 1) {
                continue;
            }
            StringBuffer str2 = new StringBuffer();
            for (int i = 0; i < items.size(); i++) {
                //收费项目
                PacsItems items2 = pacsItemMapper.selectOne(new QueryWrapper<PacsItems>()
                        .eq("id", StringUtils.isBlank(items.get(i).toString()) ? "" : items.get(i).toString()).eq("is_delete", 0));

                if (ObjectUtils.isNotEmpty(items2)) {
                    //收费项目名称
                    str2.append(items2.getExamfeeitemName() + "、");
                }
            }
            String res = str2.toString().substring(0, str2.toString().length() - 1);
            //基础检查项
            PacsBasexamltem exanm = pacsBasexamltemsMapper.selectOne(new QueryWrapper<PacsBasexamltem>()
                    .eq("id", inspectCharge.getInspectId()).eq("is_delete", 0));
            //检查项目名称
            String jcxmName = "";
            if (ObjectUtils.isNotEmpty(exanm)) {
                jcxmName = exanm.getExamitemName();
            }
            if (StringUtils.isNotEmpty(map.get(res))) {
                jcxmName = map.get(res) + "、" + jcxmName;
            }
            map.put(res, jcxmName);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            text.append("★ 收费项目: " + entry.getKey() + " 存在相同的检查项目: " + entry.getValue());
        }

        // 不存在重复项
        if (StringUtils.isBlank(text.toString())) {
            return "";
        } else {
            throw new ServiceException(text.toString());
        }
    }


    /**
     * 获取下一个体检号
     *
     * @param
     * @return
     */
    public String getMaxPatientcode() {
        String patientCode = "";
        do {
            patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
            //判断体检号是否存在
        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, patientCode)) > 0);
        return patientCode;
    }


    /**
     * 获取下一个体检号 pacs登记
     *
     * @return
     */
    private String getMaxPatientcodePacs() {
        // 得到pacs简码
        String prefix = Constants.PACS_PREFIX;
        String patientCode = "";
        do {
            patientCode = CodeUtil.getPatientCode(iSysBranchService.getBranchFlag(null), "");
            //判断体检号是否存在
        } while (peispatientMapper.selectCount(new LambdaQueryWrapper<Peispatient>()
                .eq(Peispatient::getPatientcode, patientCode)) > 0);
        //拼接
        String maxValue = prefix + patientCode;
        ;
        return maxValue;
    }


    /**
     * @param patientCode
     * @return boolean
     * @Title: 判断体检号是否存在
     * @author zhanghj
     * @since 2016-8-11 V 1.0
     */
    private boolean isExistByPatientCode(String patientCode) {
        PacsPeispatient peispatient = pacsPeispatientMapper.selectOne(new QueryWrapper<PacsPeispatient>().eq("patientcode", patientCode));
        return null != peispatient;
    }


    /**
     * @param indate
     * @return Date
     * @Title: 日期-8小时
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
     * @param months
     * @return Date
     * @Title: 根据月份差值获取日期
     */
    private Date getDateForMonth(int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -months);
        return calendar.getTime();
    }


    /**
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     * @Title: 获取两个日期之间月份的差值
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


    public Date getPreno(String preDate) {
//        if(StringUtils.isEmpty(preDate)){
//            return null;
//        }
        // TODO: 2022/12/31 app_preno 这个表没找到,app的表暂时先不处理
//        String sql="SELECT * FROM APP_PRENO WHERE "
//                + "START_TIME >= to_date('" + preDate + " 00:00:00','yyyy-mm-dd hh24:mi:ss')  "
//                + "AND END_TIME <= to_date('" + preDate + " 23:59:59','yyyy-mm-dd hh24:mi:ss') "
//                + "AND OUT_NUM>TAKE_NUM "
//                ;
//        List<PacsPreno> prenos=divisionDao.findEntityBySql(PacsPreno.class, sql);
//        if(prenos.size()==0){
//            return null;
//        }
//        PacsPreno preno=prenos.get(0);
//
//        Calendar now=Calendar.getInstance();//当前时间
//        Calendar pre=Calendar.getInstance();
//
//        if(preno.getTakeTime()==null){//当天第一个人预约
//            pre.setTime(preno.getStartTime());
//            if(pre.before(now)){//如果当前时间晚于开始时间
//                pre.setTime(now.getTime());
//                pre.add(Calendar.MINUTE, 3);
//                if(pre.after(preno.getEndTime())){
//                    return null;
//                }
//            }
//        }else{
//            pre.setTime(preno.getTakeTime());
//            if(pre.before(now)){
//                pre.setTime(now.getTime());
//                pre.add(Calendar.MINUTE, 3);
//                if(pre.after(preno.getEndTime())){
//                    return null;
//                }
//            }else{
//                pre.add(Calendar.MINUTE, 3);
//                if(pre.after(preno.getEndTime())){
//                    return null;
//                }
//            }
//        }
//
//        Date takeTime=pre.getTime();
//        preno.setTakeTime(takeTime);
//        preno.setTakeNum(preno.getTakeNum()+1);
//        executeUpdate(preno);
//        return takeTime;
        return null;
    }


    /**
     * @param peispatient
     * @param mData
     * @return
     * @throws Exception
     * @Title: 绑定档案
     */
    private String bingIArchive(PacsPeispatient peispatient, PrFormdataDto mData) throws Exception {
        String idPatientarchive = peispatient.getIdPatientarchive();
        String patientarchiveno = peispatient.getPatientarchiveno();
        // 判断档案是否存在或者人为取消（重新创建档案）
        if (StringUtils.isBlank(idPatientarchive) || "0".equals(idPatientarchive) || "1".equals(idPatientarchive)) {
            String name = peispatient.getPatientname();
            String phone = peispatient.getPhone();
            String idcardno = peispatient.getIdcardno();

            //设置属性
            mData.setDateregister(new Date());
            mData.setMemberlevel(mData.getIdPatientclass());
            mData.setIsHmd(ObjectUtils.isEmpty(mData.getIsHmd()) ? 0 : mData.getIsHmd());
            mData.setHmdbz(mData.getIsHmdb());

            if ("1".equals(idPatientarchive)) {
                // 生成档案号
                patientarchiveno = peispatientarchiveService.getArchiveCode();
                mData.setPatientarchiveno(patientarchiveno);
                mData.setMembercreate(SecurityUtils.getUserNo());
                mData.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));

                // 保存档案
                idPatientarchive = saveRecord(mData);
                if (StringUtils.isBlank(idPatientarchive)) {
                    throw new ServiceException("保存失败：创建档案失败!");
                }

                return "success:" + idPatientarchive + "no:" + patientarchiveno;
            }
            QueryWrapper<Peispatientarchive> queryWrapper = new QueryWrapper<Peispatientarchive>();
            // 判断是否为空
            if (!StringUtils.isBlank(idcardno)) {
                // 匹配档案
                queryWrapper.eq("idcardno", idcardno);
                List<Peispatientarchive> archives = peispatientarchiveService.list(queryWrapper);
                if (archives.size() > 1) {
                    // 前台弹出档案列表进行选择
                    throw new ServiceException("-1");
                } else if (archives.size() == 1) {
                    Peispatientarchive archive = archives.get(0);
                    // 得到档案ID
                    idPatientarchive = archive.getId();
                    // 档案号
                    patientarchiveno = archive.getPatientarchiveno();
                    // 更新档案黑名单
                    archive.setIshmd(peispatient.getIsHmd());
                    // 最后一次体检时间（登记日期）
                    archive.setDateregister(new Date());
                    // 档案是否团检（0：个人 1：团检）
                    archive.setIsOrg(StringUtils.isBlank(peispatient.getIdOrg()) ? 0 : 1);
                    peispatientarchiveService.updateById(archive);
                    peispatient.setIdPatientclass(archive.getMemberlevel());
                } else if (archives.size() == 0) {
                    // 生成档案号，需要根据REDIS版本自增
                    patientarchiveno = peispatientarchiveService.getArchiveCode();

                    mData.setPatientarchiveno(patientarchiveno);
                    mData.setMembercreate(SecurityUtils.getUserNo());
                    mData.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));

                    // 保存档案
                    idPatientarchive = saveRecord(mData);
                    if (StringUtils.isBlank(idPatientarchive)) {
                        throw new ServiceException("保存失败：创建档案失败!");
                    }
                }
            } else {
                // 判断是否为空
                if (!StringUtils.isBlank(name)) {
                    queryWrapper.eq("patientname", name);
                }
                if (!StringUtils.isBlank(phone)) {
                    queryWrapper.eq("phone", phone);
                }
                List<Peispatientarchive> archives = peispatientarchiveService.list(queryWrapper);
                if (archives.size() > 0) {
                    // 前台弹出档案列表进行选择
                    throw new ServiceException("-1");
                } else {
                    // 生成档案号，需要根据REDIS版本自增
                    patientarchiveno = peispatientarchiveService.getArchiveCode();

                    mData.setPatientarchiveno(patientarchiveno);
                    mData.setMembercreate(SecurityUtils.getUserNo());
                    mData.setInputCode(ToolUtil.getHanziPinyinHeadChar(peispatient.getPatientname()));


                    // 保存档案
                    idPatientarchive = saveRecord(mData);
                    if (StringUtils.isBlank(idPatientarchive)) {
                        throw new ServiceException("保存失败：创建档案失败！");
                    }
                }
            }
        } else {
            // 已经选择档案号
            Peispatientarchive archive = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>().eq("id", idPatientarchive));
            if (ObjectUtils.isEmpty(archive)) {
                throw new ServiceException("保存失败：选择的档案不存在，已经被删除!");
            }
            idPatientarchive = archive.getId();
            // 更新档案黑名单
            archive.setIshmd(peispatient.getIsHmd());
            archive.setHmdbz(peispatient.getIsHmdb());
            // 最后一次体检时间（登记日期）
            archive.setDateregister(new Date());
            // 档案是否团检（0：个人 1：团检）
            archive.setIsOrg(StringUtils.isBlank(peispatient.getIdOrg()) ? 0 : 1);
            peispatientarchiveService.updateById(archive);
            peispatient.setIdPatientclass(archive.getMemberlevel());
        }

        return "success:" + idPatientarchive + "no:" + patientarchiveno;
    }


    // 分中心前缀
    public String getCenterPrefix(String curUserId) {
        Map data = new HashMap();
        List<SysBranch> list = sysBranchMapper.getCenterPrefix(curUserId);
        return list.get(0).getJm().substring(0, 2);
    }


    /**
     * @param mData
     * @return String
     * @Title: 保存档案
     */
    private String saveRecord(PrFormdataDto mData) {
        Peispatientarchive patientArchive = mapperFacade.map(mData, Peispatientarchive.class);

        // 最后一次体检时间（登记日期）
        patientArchive.setDateregister(new Date());

        // 档案是否团检（0：个人 1：团检）
        patientArchive.setIsOrg(ObjectUtils.isEmpty(patientArchive) ? 0 : 1);
        patientArchive.setNote(null);

        // 会员级别
        patientArchive.setMemberlevel(ObjectUtils.isEmpty(mData.getIdPatientclass()) ? "0" : mData.getIdPatientclass().toString());
        // 积分
        patientArchive.setJf(0D);
        patientArchive.setFzx(getCurentUserCenter());
        // 保存实体，成功返回id，失败返回null
        return peispatientarchiveService.save(patientArchive) ? patientArchive.getId() : null;
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
}

