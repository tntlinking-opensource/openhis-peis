package com.center.medical.member.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.LimitOperation;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.param.ArchiveMergeParam;
import com.center.medical.bean.param.ArchiveParam;
import com.center.medical.bean.param.MeSaOrUpParam;
import com.center.medical.bean.param.MemberParam;
import com.center.medical.bean.vo.ArchiveVo;
import com.center.medical.bean.vo.MemberVo;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.LimitOperationMapper;
import com.center.medical.dao.PeispatientMapper;
import com.center.medical.dao.PeispatientarchiveMapper;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardMemberMedical;
import com.center.medical.finance.bean.model.CardRecheckRecord;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.dao.CardMapper;
import com.center.medical.finance.dao.CardMemberMedicalMapper;
import com.center.medical.finance.dao.CardRecheckRecordMapper;
import com.center.medical.finance.dao.CardTypeMapper;
import com.center.medical.member.bean.model.Memberintegral;
import com.center.medical.member.bean.param.*;
import com.center.medical.member.bean.vo.InterflowVo;
import com.center.medical.member.bean.vo.MemberListVo;
import com.center.medical.member.bean.vo.MerExportVo;
import com.center.medical.member.dao.MemberMapper;
import com.center.medical.member.dao.MemberintegralMapper;
import com.center.medical.member.service.MemberService;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.dao.CreatecomboMapper;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.system.dao.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 体检者档案表(Peispatientarchive)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-24 10:08:16
 */
@Slf4j
@Service("memberService")
@RequiredArgsConstructor
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Peispatientarchive> implements MemberService {

    private final MemberMapper memberMapper;
    private final PeispatientMapper peispatientMapper;
    private final MapperFacade mapperFacade;
    private final SysUserMapper sysUserMapper;
    private final CardMapper cardMapper;
    private final MemberintegralMapper memberintegralMapper;
    private final PeispatientarchiveMapper peispatientarchiveMapper;
    private final CreatecomboMapper createcomboMapper;
    private final CardMemberMedicalMapper cardMemberMedicalMapper;
    private final CardTypeMapper cardTypeMapper;
    private final CardRecheckRecordMapper cardRecheckRecordMapper;
    private final LimitOperationMapper limitOperationMapper;
    private final CreatemealMapper createmealMapper;

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MemberVo> getPage(PageParam<MemberVo> page, MemberParam param) {
        return memberMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientarchive getInfoById(String id) {
        return peispatientarchiveMapper.getInfoById(id);
    }

    /**
     * 导出会员列表数据
     *
     * @param param
     */
    @Override
    public List<MerExportVo> getList(MemberParam param) {
        return memberMapper.getList(param);
    }

    /**
     * 分页查询档案列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    @Override
    public IPage<ArchiveVo> getArchivePage(PageParam<Peispatientarchive> page, ArchiveParam param) {
        return peispatientarchiveMapper.getArchivePage(page, param);
    }

    /**
     * 合并
     *
     * @param mergeParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean merge(ArchiveMergeParam mergeParam) {
        Date now = new Date();
        //取出所有档案
        List<Peispatientarchive> archives = peispatientarchiveMapper.selectList(new LambdaQueryWrapper<Peispatientarchive>()
                .in(Peispatientarchive::getId, mergeParam.getIds())
                .orderByDesc(Peispatientarchive::getCreatedate));
        if (CollectionUtil.isEmpty(archives)) {
            throw new ServiceException("数据已改变，请刷新重试！");
        }
        Peispatientarchive archive = archives.get(0);
        archive.setPatientname(mergeParam.getPatientname());
        archive.setPhone(mergeParam.getPhone());
        archive.setIdcardno(mergeParam.getIdcardno());
        archive.setModifydate(now);
        String id = archive.getPatientarchiveno();
        List<Peispatientarchive> newList = new ArrayList<>();
        newList.add(archive);
        List<String> newPPIdList = new ArrayList<>();

        //更新体检者绑定的档案号
        for (int i = 0; i < archives.size(); i++) {
            Peispatientarchive a = archives.get(i);
            if (Objects.nonNull(a.getIsDelete()) && a.getIsDelete() == 1) {
                throw new ServiceException("数据已改变，请刷新重试！");
            }
            if (i > 0) {
                List<Peispatient> patients = peispatientMapper.selectList(new LambdaQueryWrapper<Peispatient>()
                        .eq(Peispatient::getPatientarchiveno, a.getPatientarchiveno()));
                for (Peispatient patient : patients) {
                    newPPIdList.add(patient.getId());
                }
                a.setIsDelete(1);
                newList.add(a);
            }
        }
        Peispatient peispatient = new Peispatient();
        peispatient.setPatientarchiveno(id);
        peispatientMapper.update(peispatient, new LambdaUpdateWrapper<Peispatient>()
                .in(Peispatient::getId, newPPIdList));

        //更新合并后的档案
        this.saveOrUpdateBatch(newList);

        return Boolean.TRUE;
    }

    /**
     * 会员管理中心会员添加
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean SaOrUp(MeSaOrUpParam param) {
        //体检者档案表
        Peispatientarchive pa = mapperFacade.map(param, Peispatientarchive.class);
        if (StringUtils.isBlank(pa.getId())) {
            // 保存
            Peispatientarchive paNew = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                    .eq("patientcardno", pa.getPatientcardno()));
            if (paNew != null) {
                throw new ServiceException("会员添加失败，存在重复的【" + pa.getPatientcardno() + "】会员卡号");
            } else {
                Peispatientarchive paNews = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                        .eq("idcardno", pa.getIdcardno()));
                if (paNews != null) {
                    throw new ServiceException("会员添加失败，存在重复的【" + pa.getIdcardno() + "】身份证号");
                } else {
                    //用户表
                    SysUser qx = sysUserMapper.selectUserByUserNo(SecurityUtils.getUserNo());
                    pa.setMembercreate(qx.getUserNo());
                    this.save(pa);
                }
            }
        } else {
            // 编辑
            Peispatientarchive paNew = peispatientarchiveMapper.getInfoById(pa.getId());
            if (paNew != null) {
                Long i = peispatientarchiveMapper.selectCount(new QueryWrapper<Peispatientarchive>()
                        .ne("id", pa.getId()).eq("patientcardno", pa.getPatientcardno()));
                if (i > 0) {
                    throw new ServiceException("会员添加失败，存在重复的【" + pa.getPatientcardno() + "】会员卡号");
                } else {
                    Peispatientarchive paNewx = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                            .ne("id", pa.getId()).eq("idcardno", pa.getIdcardno()));
                    if (paNewx != null) {
                        throw new ServiceException("会员添加失败，存在重复的【" + pa.getIdcardno() + "】身份证号");
                    } else {
                        //更新
                        this.updateById(pa);
                    }
                }
            } else {
                // 被删除
                throw new ServiceException("编辑失败！");
            }
        }
        return saveOl(param);
    }

    /**
     * 挂失保存数据
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveReport(SaveReportParam param) {
        Peispatientarchive pa = mapperFacade.map(param, Peispatientarchive.class);
        if (StringUtils.isBlank(pa.getId())) {
            // 保存
            Peispatientarchive paNew = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>().eq("patientcardno", pa.getPatientcardno()));
            if (paNew != null) {
                throw new ServiceException("会员添加失败，存在重复的【" + pa.getPatientcardno() + "】会员卡号");
            } else {
                Peispatientarchive paNews = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>().eq("idcardno", pa.getIdcardno()));
                if (paNews != null) {
                    throw new ServiceException("会员添加失败，存在重复的【" + pa.getIdcardno() + "】身份证号");
                } else {
                    pa.setMembercreate(SecurityUtils.getUsername());
                    this.save(pa);
                }
            }
        } else {
            // 编辑
            Peispatientarchive paNew = peispatientarchiveMapper.getInfoById(pa.getId());
            if (paNew != null) {
                Peispatientarchive paNews = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                        .ne("id", pa.getId()).eq("patientcardno", pa.getPatientcardno()));
                if (paNews != null) {
                    throw new ServiceException("失败，存在重复的【" + pa.getPatientcardno() + "】会员卡号");
                } else {
                    Peispatientarchive paNewx = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                            .ne("id", pa.getId()).eq("idcardno", pa.getIdcardno()));
                    if (paNewx != null) {
                        throw new ServiceException("会员添加失败，存在重复的【" + pa.getIdcardno() + "】身份证号");
                    } else { // 挂失
//                      //体检卡
                        Card cardGs = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", paNew.getPatientcardno()));//需要挂失的卡号对象
                        cardGs.setIsDelete(1);// 将需要挂失的对象设置为假删标识
                        this.updateById(pa);
                        String userNo = SecurityUtils.getUserNo();
                        //获取用户实体
                        SysUser qxUser = sysUserMapper.selectUserByUserNo(userNo);
                        //获取当前登录用户名
                        String name = qxUser.getUserName();
                        Double lim = Double.parseDouble((param.getLimit()).toString());
                        //PeisPatientArchive pa = JsonUtil.toObject(formdata, PeisPatientArchive.class);
                        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", pa.getPatientcardno()));
                        //会员卡积分明细表
                        Memberintegral mi = new Memberintegral();
                        if (card == null) {
                            throw new ServiceException("充值失败！数据不存在。");
                        } else {
                            // 充值
                            Card cardNew = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", card.getCardNo()));
                            if (cardNew != null) {
                                if (card.getBalanceJf() == null) {
                                    card.setBalanceJf(0.0);
                                }
                                double jf = card.getBalanceJf() + lim;
                                double a = lim;
                                card.setBalanceJf(jf);
                                mi.setCardId(card.getCardNo());//卡号
                                mi.setCardType(card.getTypeId());// 卡类型
                                mi.setMemberName(pa.getPatientname());// 姓名
                                mi.setLimit((int) a);//增加或减少的积分
                                mi.setHandleIntegral((int) jf);//操作后的积分
                                mi.setIsAdd(0);// 充值
                                mi.setIsDelete(0);
                                mi.setOperationId(name);// 操作人ID
                                //备注
                                mi.setMemotext(Constants.CHARGE_JF);
                                Date now = new Date();
                                mi.setOperationTime(now);
                                cardMapper.updateById(card);
                                memberintegralMapper.insert(mi);
                            } else {
                                throw new ServiceException("充值失败！");
                            }
                        }
                    }
                }
            } else {
                // 被删除
                throw new ServiceException("编辑失败！");
            }
        }
        return Boolean.TRUE;
    }


    /**
     * 会员管理沟通记录分页查询
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<InterflowVo> getIFPage(PageParam<InterflowVo> page, IFPageParam param) {
        return memberMapper.getIFPage(page, param);
    }

    /**
     * 沟通记录导出
     *
     * @param param
     * @return
     */
    @Override
    public List<InterflowVo> getIFExportData(IFPageParam param) {
        return memberMapper.getIFExportData(param);
    }


    /**
     * 获取卡数据
     *
     * @param cardno
     * @return
     */
    @Override
    public Map<String, Object> getCardData(String cardno) {
        //体检卡
        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_No", cardno));
        String tcjc = null;
        String cards = null;
        String cardIds = null;
        if (ObjectUtils.isNotEmpty(card)) {
            //套餐id
            String tcid = card.getTcId();
            if (StringUtils.isNotEmpty(tcid)) {
                Createcombo combo = createcomboMapper.getInfoById(tcid);
                if (ObjectUtils.isNotEmpty(combo)) {
                    //体检套餐简称
                    tcjc = combo.getTjtcjc();
                }
            }
            //会员卡体检卡关联表
            List<CardMemberMedical> cmms = cardMemberMedicalMapper.selectList(new QueryWrapper<CardMemberMedical>()
                    .eq("member_id", card.getId()));
            if (cmms.size() > 0) {
                List<String> cardnos = new ArrayList<String>();
                List<String> cardids = new ArrayList<String>();
                for (CardMemberMedical cmm : cmms) {
                    cardids.add(cmm.getMedicalId());
                    //体检卡
                    Card medical = cardMapper.getInfoById(cmm.getMedicalId());
                    if (medical != null) {
                        cardnos.add(medical.getCardNo());
                    }
                }
                cards = StringUtils.join(cardnos, ",");
                cardIds = StringUtils.join(cardids, ",");
            }
        }
        Map<String, Object> jo = new HashMap<String, Object>();
        jo.put("card", card);
        jo.put("tcjc", tcjc);
        jo.put("cards", cards);
        jo.put("cardIds", cardIds);
        return jo;
    }

    /**
     * 保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOl(MeSaOrUpParam param) {
        //体检者档案表
        Peispatientarchive form = mapperFacade.map(param, Peispatientarchive.class);
        //身份证号
        String idcardno = form.getIdcardno();
        if (StringUtils.isEmpty(idcardno)) {
            throw new RuntimeException("身份证号不能为空！");
        }
        String patientcardno = form.getPatientcardno();
        if (StringUtils.isNotEmpty(patientcardno)) {
            //体检者档案表
            Peispatientarchive paNews = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                    .ne("idcardno", idcardno).eq("patientcardno", patientcardno));
            if (ObjectUtils.isNotEmpty(paNews)) {
                throw new RuntimeException("会员添加失败，存在重复的【" + patientcardno + "】会员卡号");
            }
            //卡类型
            List<CardType> cts = cardTypeMapper.selectList(new QueryWrapper<CardType>().eq("type", 1));
            List<String> typeIds = new ArrayList<String>();
            for (CardType ct : cts) {
                typeIds.add(ct.getId());
            }
            Card card = cardMapper.selectOne(new QueryWrapper<Card>()
                    .eq("card_no", patientcardno).in("type_id", typeIds).eq("is_delete", 0));
            if (ObjectUtils.isEmpty(card)) {
                throw new ServiceException("该会员卡号不存在！请重新输入。");
            }
            //套餐id
            String tcId = param.getTcId();
            //赠送套餐是否已用
            if (card.getTcChecked() != null
                    && card.getTcChecked() == 1
                    && card.getTcId() != null
                    && !card.getTcId().equals(tcId)
            ) {
                throw new ServiceException("该会员卡号的赠送套餐已被使用，不能修改！");
            }
            //复查额度
            Double recheckMoneyOld = card.getRecheckMoney() == null ? 0.0 : card.getRecheckMoney();
            card.setTcId(tcId);
            Double recheckMoney = param.getRecheckMoney() == null ? 0.0 : Double.valueOf(param.getRecheckMoney());
            card.setRecheckMoney(recheckMoney);
            cardMapper.updateById(card);
            String username = SecurityUtils.getUsername();
            //记录复查金额操作
            if (recheckMoneyOld.doubleValue() != recheckMoney.doubleValue()) {
                double money = recheckMoney - recheckMoneyOld;
                //十周年卡复查金额记录表
                CardRecheckRecord cardRecheckRecord = new CardRecheckRecord(recheckMoneyOld
                        , recheckMoney
                        , money
                        , "会员编辑"
                        , null
                        , patientcardno
                        , new Date()
                        , money < 0 ? 0 : 1
                        , username);
                cardRecheckRecordMapper.insert(cardRecheckRecord);
            }

            String cards = param.getCards();
            String memberId = card.getId();
            //删除
            cardMemberMedicalMapper.delete(new QueryWrapper<CardMemberMedical>().eq("member_id", memberId));
            if (StringUtils.isNotEmpty(cards)) {
                String[] medicalIds = cards.split(",");
                for (String medicalId : medicalIds) {
                    //会员卡体检卡关联表
                    CardMemberMedical cardMemberMedical = new CardMemberMedical(memberId, medicalId);
                    cardMemberMedicalMapper.insert(cardMemberMedical);
                }
            }
        }

        //身份证号必填，用身份证号匹配
        Peispatientarchive old = peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                .eq("idcardno", idcardno));
        if (ObjectUtils.isNotEmpty(old)) {
            if (StringUtils.isNotEmpty(form.getPhone()) && StringUtils.isNotEmpty(form.getPatientcardno())) {
                Long count = peispatientarchiveMapper.selectCount(new QueryWrapper<Peispatientarchive>()
                        .ne("patientcardno", form.getPatientcardno())
                        .ne("id", old.getId()).eq("phone", form.getPhone()));
                if (count > 0) {
                    throw new ServiceException("操作失败，电话" + form.getPhone() + "已绑定其他卡号");
                }
            }
            //更新
            updateById(form);
        } else {
            if (StringUtils.isNotEmpty(form.getPhone()) && StringUtils.isNotEmpty(form.getPatientcardno())) {
                Long count = peispatientarchiveMapper.selectCount(new QueryWrapper<Peispatientarchive>()
                        .ne("patientcardno", form.getPatientcardno())
                        .eq("phone", form.getPhone()));
                if (count > 0) {
                    throw new ServiceException("操作失败，电话" + form.getPhone() + "已绑定其他卡号");
                }
            }
            //插入
            save(form);
        }
        return Boolean.TRUE;
    }


    /**
     * 积分充值-搜索会员卡号
     *
     * @param patientcardno
     * @param phone
     * @return
     */
    @Override
    public Map<String, Object> getMemberData(String patientcardno, String phone) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Peispatientarchive peisPatientArchive = StringUtils.isNotEmpty(patientcardno) ?
                peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                        .eq("patientcardno", patientcardno))
                :
                peispatientarchiveMapper.selectOne(new QueryWrapper<Peispatientarchive>()
                        .isNotNull("patientcardno").eq("phone", phone));
        if (peisPatientArchive != null) {
            //体检卡
            Card card = cardMapper.selectOne(new QueryWrapper<Card>()
                    .eq("card_no", peisPatientArchive.getPatientcardno()));
            //不是家庭卡卡类型
            if (!Card.JTK.equals(card.getTypeId())) {
                map.put("success", true);
                map.put("Patientcardno", peisPatientArchive.getPatientcardno());
                map.put("patientname", peisPatientArchive.getPatientname());
                map.put("balanceJf", card.getBalanceJf());
                map.put("cardState", card.getCardState());
                map.put("phone", peisPatientArchive.getPhone());
                //十周年卡类型
                if (Card.TEN.equals(card.getTypeId())) {
                    //会员卡体检卡关联表
                    List<CardMemberMedical> cmms = cardMemberMedicalMapper.selectList(new QueryWrapper<CardMemberMedical>()
                            .eq("member_id", card.getId()));
                    if (cmms.size() > 0) {
                        List<Map<String, Object>> cards = new ArrayList<Map<String, Object>>();
                        for (CardMemberMedical cmm : cmms) {
                            //体检卡
                            Card medialCard = cardMapper.getInfoById(cmm.getMedicalId());
                            if (card != null) {
                                Map<String, Object> m = new HashMap<String, Object>();
                                m.put("cardno", medialCard.getCardNo());
                                //卡内剩余的金额(会员卡、体检卡)
                                m.put("balanceLimit", medialCard.getBalanceLimit());
                                cards.add(m);
                            }
                        }
                        map.put("cards", cards);
                    }
                }
            } else {
                map.put("success", false);
            }
        } else map.put("success", false);
        return map;
    }


    /**
     * 积分充值
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addJf(AddJfParam param) {
        //取出数据
        JFFormDataParam formdata = param.getFormdata();
        List<JFGridDataParam> griddata = param.getGriddata();

        String name = "";
        // 判断是否是从内网获取数据，操作人
        if (null != formdata.getUserName()) {
            name = formdata.getUserName().toString();
        } else {
            name = SecurityUtils.getUsername();
        }
        //消费传负数
        Double lim = Double.parseDouble((formdata.getLimit()).toString());
        //体检者档案表
        Peispatientarchive pa = mapperFacade.map(formdata, Peispatientarchive.class);
        //会员卡部分
        String memberCardno = pa.getPatientcardno();
        Card cardNew = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", memberCardno));
        if (ObjectUtils.isEmpty(cardNew)) {
            throw new ServiceException("操作失败！卡号不存在。");
        }
        if (cardNew.getBalanceJf() == null) {
            // 如果是null值，赋值0.0可以充值
            cardNew.setBalanceJf(0.0);
        }
        double jf = cardNew.getBalanceJf().doubleValue() + lim;
        if (jf < 0) {
            throw new ServiceException("操作失败:可用积分不足");
        }
        double a = lim;
        //会员卡剩余积分
        cardNew.setBalanceJf(jf);
        //更新
        cardMapper.updateById(cardNew);
        Date now = new Date();

        if (a != 0) {
            //会员卡积分明细表
            Memberintegral mi = new Memberintegral();
            mi.setCardId(cardNew.getCardNo());//卡号
            mi.setCardType(cardNew.getTypeId());// 卡类型
            mi.setMemberName(pa.getPatientname());// 姓名
            mi.setLimit((int) a);//增加或减少的积分
            mi.setHandleIntegral((int) jf);//操作后的积分
            mi.setIsAdd(0);// 充值
            mi.setIsDelete(0);
            mi.setOperationId(name);// 操作人ID
            mi.setBranchCenter(formdata.getBranchCenter().toString());// 分中心ID
            mi.setOperationTime(now);
            mi.setMemotext(formdata.getMemotext() == null ? null : formdata.getMemotext().toString());
            mi.setPatientcode(formdata.getPatientcode());
            memberintegralMapper.insert(mi);
        }

        //绑定体检卡消费
        if (ObjectUtils.isNotEmpty(griddata)) {
            String chargeId = formdata.getPatientcode();
            //分中心ID
            String branchCenter = formdata.getBranchCenter();
            if (StringUtils.isEmpty(branchCenter)) {
                throw new ServiceException("分中心不能为空");
            }
            String patientname = formdata.getPatientname();
            if (StringUtils.isEmpty(patientname)) {
                throw new ServiceException("体检者姓名不能为空");
            }
            for (int i = 0; i < griddata.size(); i++) {
                JFGridDataParam jo = griddata.get(i);
                String cardno = jo.getCardno();
                if (StringUtils.isEmpty(cardno)) {
                    throw new ServiceException("体检卡卡号不能为空");
                }
                //消费传正数
                Double money = jo.getMoney();
                if (money == null) {
                    throw new ServiceException("体检卡消费金额不能为空");
                }
                //体检卡
                Card tjk = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", cardno));
                if (tjk == null) {
                    throw new ServiceException("体检卡卡号" + cardno + "不存在");
                }
                //卡内剩余的金额(会员卡、体检卡)
                Double balanceLimit = tjk.getBalanceLimit() == null ? 0.0 : tjk.getBalanceLimit();
                Double handleMoney = balanceLimit.doubleValue() - money.doubleValue();
                if (handleMoney < 0) {
                    throw new ServiceException("体检卡卡号" + cardno + "剩余金额不足,余额:" + balanceLimit);
                }
                tjk.setBalanceLimit(handleMoney);
                //CW卡额度操作表
                LimitOperation limitOperation = new LimitOperation(cardno
                        , tjk.getTypeId()
                        , money
                        , jo.getMemotext()
                        , now
                        , name
                        , 1
                        , null
                        , handleMoney
                        , branchCenter
                        , chargeId
                        , 0
                        , patientname
                        , memberCardno);
                limitOperationMapper.insert(limitOperation);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 分页查询平台会员列表数据
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<MemberListVo> getMemberListData(PageParam<MemberListVo> page, MemberParam param) {
        Long offset = (page.getCurrent() - 1) * page.getSize();
        Long limit = page.getSize();
        param.setOffset(offset);
        param.setLimit(limit);
        IPage<MemberListVo> iPage = new PageParam<>();
        List<MemberListVo> list = memberMapper.getMemberListData(param);
        //取出属性
        for (MemberListVo record : list) {
            if (record.getTcId() != null) {
                if (Card.TEN.equals(record.getTypeId())) {
                    Createcombo combo = createcomboMapper.getInfoById(record.getTcId());
                    if (combo != null) {
                        //体检套餐名称
                        record.setTcmc(combo.getTjtcmc());
                    }
                    //会员卡体检卡关联表
                    List<CardMemberMedical> cmms = cardMemberMedicalMapper.selectList(new QueryWrapper<CardMemberMedical>()
                            .eq("member_id", record.getId()));
                    List<String> cardnos = new ArrayList<String>();
                    for (CardMemberMedical cmm : cmms) {
                        //体检卡
                        Card medicalCard = cardMapper.getInfoById(cmm.getMedicalId());
                        if (medicalCard != null) {
                            cardnos.add(medicalCard.getCardNo());
                        }
                    }
                    record.setCardnos(cardnos);
                } else {
                    Createmeal meal = createmealMapper.getInfoById(record.getTcId());
                    if (meal != null) {
                        //体检套餐名称
                        record.setTcmc(meal.getTjtcmc());
                    }
                }
            }
        }
        //放回
        iPage.setRecords(list);
        //查询总数
        Long total = memberMapper.getMemberTotal(param);
        iPage.setTotal(total);
        iPage.setCurrent(page.getCurrent());
        iPage.setSize(page.getSize());
        return iPage;
    }


    /**
     * 导出会员列表数据
     *
     * @param param
     * @return
     */
    @Override
    public List<MemberListVo> getExportData(MemberParam param) {
        List<MemberListVo> list = memberMapper.getExportData(param);
        for (MemberListVo record : list) {
            if (record.getTcId() != null) {
                if (Card.TEN.equals(record.getTypeId())) {
                    Createcombo combo = createcomboMapper.getInfoById(record.getTcId());
                    if (combo != null) {
                        //体检套餐名称
                        record.setTcmc(combo.getTjtcmc());
                    }
                    //会员卡体检卡关联表
                    List<CardMemberMedical> cmms = cardMemberMedicalMapper.selectList(new QueryWrapper<CardMemberMedical>()
                            .eq("member_id", record.getId()));
                    List<String> cardnos = new ArrayList<String>();
                    for (CardMemberMedical cmm : cmms) {
                        //体检卡
                        Card medicalCard = cardMapper.getInfoById(cmm.getMedicalId());
                        if (medicalCard != null) {
                            cardnos.add(medicalCard.getCardNo());
                        }
                    }
                    record.setCardnos(cardnos);
                } else {
                    Createmeal meal = createmealMapper.getInfoById(record.getTcId());
                    if (meal != null) {
                        //体检套餐名称
                        record.setTcmc(meal.getTjtcmc());
                    }
                }
            }
        }
        return list;
    }

    /**
     * 中心会员导出数据
     *
     * @param param
     * @return
     */
    @Override
    public List<MerExportVo> getExport(MemberParam param) {
        return memberMapper.getList(param);
    }
}

