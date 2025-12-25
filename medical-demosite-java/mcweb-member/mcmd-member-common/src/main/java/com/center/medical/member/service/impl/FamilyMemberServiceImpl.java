package com.center.medical.member.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.FamilyCardCharge;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.FamilyCardChargeMapper;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardMemberMedical;
import com.center.medical.finance.bean.model.CardPayway;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.dao.CardMapper;
import com.center.medical.finance.dao.CardMemberMedicalMapper;
import com.center.medical.finance.dao.CardPaywayMapper;
import com.center.medical.finance.dao.CardTypeMapper;
import com.center.medical.member.bean.dto.ArchiveDataDto;
import com.center.medical.member.bean.dto.ChargeGDDto;
import com.center.medical.member.bean.dto.FormdataDto;
import com.center.medical.member.bean.dto.MemberGDDto;
import com.center.medical.member.bean.param.*;
import com.center.medical.member.bean.vo.*;
import com.center.medical.member.dao.FamilyMemberMapper;
import com.center.medical.member.service.FamilyMemberService;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.dao.CreatecomboMapper;
import com.center.medical.service.PeispatientarchiveService;
import com.center.medical.system.dao.SysBranchMapper;
import com.center.medical.system.dao.SysUserMapper;
import com.center.medical.system.service.ISysBranchService;
import com.center.medical.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 体检者档案表(Peispatientarchive)表服务实现类
 *
 * @author ay
 * @since 2023-03-01 10:34:46
 */
@Slf4j
@Service("familyMemberService")
@RequiredArgsConstructor
public class FamilyMemberServiceImpl extends ServiceImpl<FamilyMemberMapper, Peispatientarchive> implements FamilyMemberService {

    private final FamilyMemberMapper familyMemberMapper;
    private final CardMapper cardMapper;
    private final CreatecomboMapper createcomboMapper;
    private final CardMemberMedicalMapper cardMemberMedicalMapper;
    private final FamilyCardChargeMapper familyCardChargeMapper;
    private final PeispatientarchiveService peispatientarchiveService;
    private final MapperFacade mapperFacade;
    private final SysBranchMapper sysBranchMapper;
    private final ISysBranchService iSysBranchService;
    private final CardPaywayMapper cardPaywayMapper;
    private final CardTypeMapper cardTypeMapper;
    private final SysUserMapper sysUserMapper;
    private final ISysConfigService iSysConfigService;

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    @Override
    public IPage<FamilyMemberVo> getList(PageParam<FamilyMemberVo> page, FamilyMemberParam param) {
        //家庭卡卡类型id
        param.setCard(Card.JTK);
        return familyMemberMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Peispatientarchive getInfoById(String id) {
        return familyMemberMapper.getInfoById(id);
    }

    /**
     * 家庭会员-家庭卡消费查询
     *
     * @param param
     * @return
     */
    @Override
    public Card familyCardList(FamilyCardParam param) {
        if (ObjectUtils.isEmpty(param)) {
            throw new ServiceException("请输入至少输入家庭卡号、身份证号、电话号码中的一个");
        }
        param.setCard(Card.JTK);
        List<String> cardIds = familyMemberMapper.familyCardList(param);
        if (CollectionUtils.isEmpty(cardIds)) {
            throw new ServiceException("没有找到匹配的家庭卡");
        }
        Card card = cardMapper.getInfoById(cardIds.get(0));
        return card;
    }

    /**
     * 家庭会员详情数据
     *
     * @param cardno
     * @return
     */
    @Override
    public Map<String, Object> getCardData(String cardno) {
        //体检卡
        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", cardno));
        String tcjc = null;
        String cards = null;
        String cardIds = null;
        if (ObjectUtils.isNotEmpty(card)) {
            //套餐id
            String tcid = card.getTcId();
            if (StringUtils.isNotEmpty(tcid)) {
                //最小套餐
                Createcombo combo = createcomboMapper.getInfoById(tcid);
                if (combo != null) {
                    //体检套餐简称
                    tcjc = combo.getTjtcjc();
                }
            }
            //会员卡体检卡关联表
            List<CardMemberMedical> cmms = cardMemberMedicalMapper.selectList(new QueryWrapper<CardMemberMedical>().eq("member_id", card.getId()));
            if (cmms.size() > 0) {
                List<String> cardnos = new ArrayList<String>();
                List<String> cardids = new ArrayList<String>();
                for (CardMemberMedical cmm : cmms) {
                    //体检卡id
                    cardids.add(cmm.getMedicalId());
                    //体检卡
                    Card medical = cardMapper.getInfoById(cmm.getMedicalId());
                    if (medical != null) {
                        //卡号
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
     * 家庭会员充值保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized Map<String, Object> saveCharge(SaveChargeParam param) {

        String patientcardno = param.getPatientcardno();
        //体检卡
        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", patientcardno));
        //会员卡剩余积分
        Double startJf = card.getBalanceJf() == null ? 0.0 : card.getBalanceJf();
        Double startMoney = card.getBalanceMoney() == null ? 0.0 : card.getBalanceMoney();
        Double jf = param.getJf() == null ? 0.0 : param.getJf();
        Double money = param.getMoney() == null ? 0.0 : param.getMoney();
        Double endJf = jf + startJf;
        Double endMoney = money + startMoney;
        card.setBalanceJf(endJf);
        card.setBalanceMoney(endMoney);
        cardMapper.updateById(card);
        //家庭卡充值记录
        FamilyCardCharge fcc = new FamilyCardCharge();
        fcc.setStartJf(startJf);
        fcc.setStartMoney(startMoney);
        fcc.setCardno(patientcardno);
        fcc.setChargeTime(new Date());
        fcc.setIdPayway(param.getIdPayway());
        fcc.setChargerUsername(param.getChargeUsername());
        fcc.setNote(param.getNote());
        fcc.setType(0);
        fcc.setMoney(money);
        fcc.setJf(jf);
        fcc.setEndJf(endJf);
        fcc.setEndMoney(endMoney);
        //插入
        familyCardChargeMapper.insert(fcc);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", new String[] { money.toString(), jf.toString(), endMoney.toString(), endJf.toString() });
        Peispatientarchive archive = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>()
                .eq("is_main", 1).eq("patientcardno", patientcardno));
        if (ObjectUtils.isNotEmpty(archive)) {
            result.put("phone", archive.getPhone());
        }
        result.put("status", "success");
        return result;
    }

    /**
     * 1.线上发卡，线上保存card信息
     * 2.线下卡办理，线下线上都要保存档案信息，线下保存时为了登记时绑定档案，消费时获取档案卡号。
     * 3.线下档案表保存档案+卡号（含主持卡人+其他家庭成员）。
     * 由于编辑、加载的都是线上的数据，id只能在线上用，线下用身份证号（必填），绑定档案时，如果有身份证号 ，就直接用身份证号绑定。 增加、修改
     * 线下的逻辑是一样的，线下不需要验证。
     * 4.线上保存所有记录。
     * 5.消费时，判断是家庭卡还是普通会员卡，家庭卡的信息都要从线上带出来。
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(FMSaOrUpParam param) {
        // 先修改线下数据
        // 保存主持卡人档案及卡号
        FormdataDto jo = param.getFormdata();
        String cardNo = jo.getCardno();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        // 如果不是编辑
        if (ObjectUtils.isNotEmpty(jo.getArchiveData())) {
            ArchiveDataDto archiveJo = jo.getArchiveData();
            String idcardno = archiveJo.getIdcardno();
            //体检者档案表
            Peispatientarchive archiveOld = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>()
                    .eq("idcardno", idcardno));
            if (ObjectUtils.isEmpty(archiveOld)) {
                archiveOld = mapperFacade.map(archiveJo, Peispatientarchive.class);
                archiveOld.setIsMain(1);
                archiveOld.setSource(1);
                archiveOld.setPatientcardno(cardNo);
                archiveOld.setCountreportoccupationxml(1);
                //生成档案号
                String recordNo = peispatientarchiveService.getArchiveCode();
                archiveOld.setPatientarchiveno(recordNo);
                archiveOld.setInputCode(ToolUtil.getHanziPinyinHeadChar(archiveOld.getPatientname()));
                archiveOld.setAge(IdcardUtil.getAgeByIdCard(idcardno));
                archiveOld.setMembercreate(SecurityUtils.getUserNo());
                archiveOld.setFzx(SecurityUtils.getCId());
                //保存
                peispatientarchiveService.save(archiveOld);
            } else {
                // 编辑

				/*if (archiveOld.getIsMain() != null && archiveOld.getIsMain() == 1) {
					throw new TransactionException("身份证号：" + archiveOld.getIdcardno() + "已是主持人不可以更改");
				}*/
                archiveOld.setIsMain(1);
                if (archiveOld.getPatientarchiveno() != null && !cardNo.equals(archiveOld.getPatientcardno())) {
                    archiveOld.setOldCard(archiveOld.getPatientcardno());
                    archiveOld.setPatientcardno(cardNo);
                } else {
                    archiveOld.setPatientcardno(cardNo);
                }

            }
        }
        // 保存其他家庭成员档案及卡号
        List<MemberGDDto> ja = param.getMemberGriddata();
        if (CollectionUtils.isNotEmpty(ja)) {
            for (int i = 0; i < ja.size(); i++) {
                MemberGDDto memberJo = ja.get(i);
                String idcardnoMem = memberJo.getIdcardno();
                //体检者档案表
                Peispatientarchive ao = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>()
                        .eq("idcardno", idcardnoMem));
                if (ObjectUtils.isEmpty(ao)) {
                    ao = mapperFacade.map(memberJo, Peispatientarchive.class);
                    ao.setSource(1);
                    ao.setIsMain(0);
                    ao.setPatientcardno(cardNo);
                    ao.setCountreportoccupationxml(1);
                    //档案号
                    String recordNo = peispatientarchiveService.getArchiveCode();
                    ao.setFzx(SecurityUtils.getCId());
                    ao.setPatientarchiveno(recordNo);
                    ao.setInputCode(ToolUtil.getHanziPinyinHeadChar(ao.getPatientname()));

                    ao.setAge(IdcardUtil.getAgeByIdCard(idcardnoMem));
                    ao.setMembercreate(SecurityUtils.getUserNo());
                    peispatientarchiveService.save(ao);
                } else {
                    //删除
                    if ("removed".equals(memberJo.getState())) {
                        //一卡通号
                        ao.setPatientcardno(null);
                    } else {
                        /*if (ao.getIsMain() != null && ao.getIsMain() == 1) {
                            throw new TransactionException("身份证号：" + ao.getIdcardno() + "已是主持人不可以更改");
                        }*/
                        //是否主持卡人
                        ao.setIsMain(0);
                        if (ao.getPatientarchiveno() != null && !cardNo.equals(ao.getPatientcardno())) {
                            ao.setOldCard(ao.getPatientcardno());
                            ao.setPatientcardno(cardNo);
                        } else {
                            ao.setPatientcardno(cardNo);
                        }
                    }
                }
            }
        }
        // TODO: ??? 保存线上
//        // 保存线上
//        saveOrUpdateOl(param);
        return Boolean.TRUE;
    }


    /**
     * 保存线上
     *
     * @param param
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveOrUpdateOl(FMSaOrUpParam param) {
        FormdataDto jo = param.getFormdata();

        /* 保存卡信息 */
        String cardNo = jo.getCardno();
        //体检卡
        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_No", cardNo).eq("is_delete", 0));
        if (card == null) {
            throw new ServiceException("卡号" + cardNo + "不存在");
        }
        // 如果已绑定其他主持卡人
        boolean isAdd = ObjectUtils.isNotEmpty(jo.getArchiveData());
        ArchiveDataDto archiveJo = null;
        String idcardno = null;
        if (isAdd) {
            archiveJo = jo.getArchiveData();
            idcardno = archiveJo.getIdcardno();
            //体检者档案表
            Peispatientarchive binded = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>()
                    .eq("patientcardno", cardNo).eq("is_main", 1).ne("idcardno", idcardno));
            if (binded != null) {
                throw new ServiceException("卡号" + cardNo + "已绑定其他档案：" + binded.getIdPatientarchive());
            }
        }
        //密码及确认密码
        String password = jo.getPassword();
        String passwordConfirm = jo.getPasswordConfirm();
        if (!password.equals(passwordConfirm)) {
            throw new ServiceException("两次密码输入不一致");
        }
        card.setPassword(password);

        //如果是编辑，积分和金额本来就是不能修改的，而且如果这里用页面上的数字修改数据库中的记录会出问题
//		card.setBalanceJf(jo.getDouble("balanceJf"));
        Double balanceMoney = jo.getBalanceMoney();
//		card.setBalanceMoney(balanceMoney);

        card.setSellId(jo.getSellId());
        card.setSellTime(jo.getSellTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        /* 保存主持卡人档案及卡号 */
        if (isAdd) {
            card.setBalanceJf(jo.getBalanceJf());
            card.setBalanceMoney(balanceMoney);

            String archiveId = archiveJo.getId();
            // 加载
            if (StringUtils.isNotEmpty(archiveId)) {
                Peispatientarchive old = peispatientarchiveService.getInfoById(archiveId);
                if (old == null) {
                    throw new ServiceException("主持卡人档案不存在，请刷新重试！");
                }
                if (old.getIsMain() != null && old.getIsMain() == 1) {
                    throw new ServiceException("身份证号：" + old.getIdcardno() + "已是主持人不可以更改");
                }
                old.setIsMain(1);
                if (old.getPatientarchiveno() != null && !cardNo.equals(old.getPatientcardno())) {
                    old.setOldCard(old.getPatientcardno());
                    old.setPatientcardno(cardNo);
                } else {
                    old.setPatientcardno(cardNo);
                }
                peispatientarchiveService.updateById(old);
            } else {
                // 新增
                Peispatientarchive peispatientarchive = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>()
                        .eq("idcardno", idcardno));
                if (ObjectUtils.isNotEmpty(peispatientarchive)) {
                    throw new ServiceException("身份证号为" + idcardno + "的档案已存在，请直接输入身份证号加载。");
                }
                Peispatientarchive main = mapperFacade.map(archiveJo, Peispatientarchive.class);
                main.setIsMain(1);
                main.setSource(1);
                main.setPatientcardno(cardNo);
                main.setCountreportoccupationxml(1);
                // 生成档案号，需要根据REDIS版本自增
                String patientarchiveno = peispatientarchiveService.getArchiveCode();
                main.setFzx(SecurityUtils.getCId());
                main.setPatientarchiveno(patientarchiveno);
                main.setInputCode(ToolUtil.getHanziPinyinHeadChar(main.getPatientname()));
                main.setAge(IdcardUtil.getAgeByIdCard(idcardno));
                // main.setMembercreate(toolUtil.getUserId());
                peispatientarchiveService.save(main);
            }
        }
        //更新卡信息
        cardMapper.updateById(card);


        /* 保存其他家庭成员信息 */
        List<MemberGDDto> ja = param.getMemberGriddata();
        if (CollectionUtils.isNotEmpty(ja)) {
            for (int i = 0; i < ja.size(); i++) {
                MemberGDDto memberJo = ja.get(i);
                String idcardnoMem = memberJo.getIdcardno();
                String id = memberJo.getId();
                // 加载
                if (StringUtils.isNotEmpty(id)) {
                    Peispatientarchive ao = peispatientarchiveService.getInfoById(id);
                    if (ObjectUtils.isEmpty(ao)) {
                        throw new ServiceException("家庭成员不存在，请刷新重试。");
                    }
                    if ("removed".equals(memberJo.getState())) {
                        //删除
                        ao.setPatientcardno(null);
                    } else {
                        /*
                         * if (ao.getPatientcardno() != null &&
                         * !cardNo.equals(ao.getPatientcardno())) { throw new
                         * TransactionException( "家庭成员" + ao.getPatientname() +
                         * "已绑定其他会员卡：" + ao.getPatientcardno()); }
                         */
                        //是否主持卡人
                        if (ao.getIsMain() != null && ao.getIsMain() == 1) {
                            throw new ServiceException("身份证号：" + ao.getIdcardno() + "已是主持人不可以更改");
                        }
                        ao.setIsMain(0);
                        if (ao.getPatientarchiveno() != null && !cardNo.equals(ao.getPatientcardno())) {
                            ao.setOldCard(ao.getPatientcardno());
                            ao.setPatientcardno(cardNo);
                        } else {
                            ao.setPatientcardno(cardNo);
                        }
                    }
                    // 新增
                } else {
                    Peispatientarchive idcardnoRepeated = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>()
                            .eq("idcardno", idcardnoMem));
                    if (idcardnoRepeated != null) {
                        throw new ServiceException("身份证号为" + idcardnoMem + "的档案已存在，请直接输入身份证号加载。");
                    }
                    //体检者档案表
                    Peispatientarchive ao = mapperFacade.map(memberJo, Peispatientarchive.class);
                    ao.setSource(1);
                    ao.setIsMain(0);
                    ao.setPatientcardno(cardNo);
                    ao.setCountreportoccupationxml(1);
                    // String prefix =
                    // preregistrationDao.getCenterPrefix(toolUtil.getUserId());
                    // String recordNo = preregistrationDao.createSEQ();
                    // recordNo = prefix+String.format("%8s", recordNo).replace(" ",
                    // "0");
                    // ao.setPatientarchiveno(recordNo);
                    ao.setInputCode(ToolUtil.getHanziPinyinHeadChar(ao.getPatientname()));
                    ao.setAge(IdcardUtil.getAgeByIdCard(idcardnoMem));
                    // ao.setMembercreate(toolUtil.getUserId());
                    peispatientarchiveService.save(ao);
                }
            }
        }
        /* 保存费用信息 */

        List<ChargeGDDto> ca = param.getChargeGriddata();
        Double total = 0.0;
        for (int i = 0; i < ca.size(); i++) {
            ChargeGDDto chargeJo = ca.get(i);

            String state = chargeJo.getState();
            if ("removed".equals(state)) {
                //删除
                cardPaywayMapper.deleteById(chargeJo.getId());
            } else if ("modified".equals(state)) {
                //修改
                CardPayway old = cardPaywayMapper.getInfoById(chargeJo.getId());
                if (old == null) {
                    throw new ServiceException("收费信息已改变，请刷新重试");
                }
                //卡办理收款方式表
                CardPayway cpw = mapperFacade.map(chargeJo, CardPayway.class);
                old.setNote(cpw.getNote());
                old.setIdPayway(cpw.getIdPayway());
                old.setMoneyamountpaid(cpw.getMoneyamountpaid());
                total += cpw.getMoneyamountpaid();
            } else if ("added".equals(state)) {
                CardPayway cpw = mapperFacade.map(chargeJo, CardPayway.class);
                cpw.setMoneyamountpaiddate(new Date());
                cpw.setIdFeecharger(SecurityUtils.getUserNo());
                cpw.setIdCharge(card.getId());
                cpw.setIsCharged(1);
                total += cpw.getMoneyamountpaid();
                cardPaywayMapper.insert(cpw);
            }
        }
        // 两个DOUBLE，都是100.0，不==
        if (isAdd && total.doubleValue() != balanceMoney.doubleValue()) {
            throw new ServiceException("卡余额与收费信息金额不符。");
        }
        return Boolean.TRUE;
    }

    /**
     * 家庭卡消费
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized Map<String, Object> saveConsumex(SaveConParam param) {

        String password = param.getPassword();
        String cardno = param.getCardNo();
        //体检卡
        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", cardno));
        //体检卡密码
        if (ObjectUtils.isEmpty(card.getPassword())) {
            throw new ServiceException("请先设置密码！");
        }
        if (!card.getPassword().equals(password)) {
            throw new ServiceException("密码错误！");
        }

        String type = param.getType();
        //会员卡剩余积分
        Double startJf = card.getBalanceJf() == null ? 0.0 : card.getBalanceJf();
        //剩余金额
        Double startMoney = card.getBalanceMoney() == null ? 0.0 : card.getBalanceMoney();
        Double money = param.getMoney() == null ? 0.0 : param.getMoney();
        Double jf = param.getJf() == null ? 0.0 : param.getJf();
        Double endJf = startJf - jf;
        Double endMoney = startMoney - money;
        if (endJf < 0) {
            throw new ServiceException("积分不足");
        }
        if (endMoney < 0) {
            throw new ServiceException("余额不足");
        }
        card.setBalanceJf(endJf);
        card.setBalanceMoney(endMoney);
        //更新
        cardMapper.updateById(card);

        if (jf > 0) {
            //家庭卡充值记录
            FamilyCardCharge fcc = new FamilyCardCharge();
            fcc.setIdPayway(Card.JF);
            fcc.setStartJf(startJf);
            fcc.setStartMoney(startMoney);
            fcc.setCardno(cardno);
            fcc.setChargeTime(new Date());
            fcc.setChargerUsername(param.getChargeUsername());
            fcc.setNote(param.getNote());
            fcc.setType(Integer.parseInt(type));
            fcc.setMoney(0.0);
            fcc.setJf(jf);
            fcc.setEndJf(endJf);
            fcc.setEndMoney(startMoney);
            fcc.setPatientcode(param.getPatientcode());
            fcc.setConsumetype(param.getConsumetype());
            //插入
            familyCardChargeMapper.insert(fcc);
        }
        if (money > 0) {
            FamilyCardCharge fcc = new FamilyCardCharge();
            fcc.setIdPayway(Card.YE);
            fcc.setStartJf(endJf);
            fcc.setStartMoney(startMoney);
            fcc.setCardno(cardno);
            fcc.setChargeTime(new Date());
            fcc.setChargerUsername(param.getChargeUsername());
            fcc.setNote(param.getNote());
            fcc.setType(Integer.parseInt(type));
            fcc.setMoney(money);
            fcc.setJf(0.0);
            fcc.setEndJf(endJf);
            fcc.setEndMoney(endMoney);
            fcc.setPatientcode(param.getPatientcode());
            fcc.setConsumetype(param.getConsumetype());
            //插入
            familyCardChargeMapper.insert(fcc);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", new String[] { money.toString(), jf.toString(), endMoney.toString(), endJf.toString() });
        Peispatientarchive archive = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>()
                .eq("is_main", 1).eq("patientcardno", cardno));
        if (archive != null) {
            result.put("phone", archive.getPhone());
        }
        result.put("status", "success");
        return result;
    }

    /**
     * 分页家庭卡消费记录
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<FamilyChargeVo> familyChargeData(PageParam<FamilyChargeVo> page, FamilyChargeParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return familyMemberMapper.familyChargeData(page, param);
    }


    /**
     * 导出家庭卡消费记录数据
     *
     * @param param
     * @return
     */
    @Override
    public List<FamilyChargeVo> chargeExportData(FamilyChargeParam param) {
        //开始时间跟结束时间
        if (ObjectUtils.isNotEmpty(param.getStartTime())) {
            DateTime startTime = DateUtil.beginOfDay(param.getStartTime());
            param.setStartTime(startTime);
        }
        if (ObjectUtils.isNotEmpty(param.getEndTime())) {
            DateTime endTime = DateUtil.endOfDay(param.getEndTime());
            param.setEndTime(endTime);
        }
        return familyMemberMapper.chargeExportData(param);
    }

    /**
     * 分页查询家庭卡生日
     *
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<FamilyBirthVo> FamilyBirthPage(PageParam<FamilyBirthVo> page, FamilyBirthParam param) {
        //家庭卡卡类型id
        param.setCard(Card.JTK);
        return familyMemberMapper.FamilyBirthPage(page, param);
    }


    /**
     * 新增家庭卡办理-上方数据
     *
     * @param cardno
     * @return
     */
    @Override
    public Map<String, Object> getAddData(String cardno) {
        //存放返回数据
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(cardno)) {
            //体检卡
            Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", cardno));
            if (ObjectUtils.isNotEmpty(card.getTypeId())) {
                //卡类型
                CardType ct = cardTypeMapper.getInfoById(card.getTypeId());
                if (ObjectUtils.isNotEmpty(ct)) {
                    //类型名称
                    String typeName = ct.getTypeName();
                    map.put("typeName", typeName);
                }
            }
            //体检者档案表
            Peispatientarchive archive = peispatientarchiveService.getOne(new QueryWrapper<Peispatientarchive>()
                    .eq("is_main", 1).eq("patientcardno", cardno)
            );

            map.put("card", card);
            map.put("archive", archive);
            //售卡人ID
            if (ObjectUtils.isNotEmpty(card.getSellId())) {
                SysUser sell = sysUserMapper.getUserByNo(card.getSellId());
                if (ObjectUtils.isNotEmpty(sell)) {
                    map.put("username", sell.getUserName());
                    map.put("userId", card.getSellId());
                }
            }
        } else {
            //用户名
            String username = SecurityUtils.getUsername();
            map.put("username", username);
            String userId = SecurityUtils.getUserNo();
            map.put("userId", userId);
        }
        return map;
    }

    /**
     * 新增家庭卡办理-左下数据
     *
     * @param page
     * @param cardno
     * @return
     */
    @Override
    public IPage<ChargeInfoVo> getChargeInfoData(PageParam<ChargeInfoVo> page, String cardno) {
        Card card = cardMapper.selectOne(new QueryWrapper<Card>().eq("card_no", cardno));
        return familyMemberMapper.getChargeInfoData(page, card.getId());
    }


    /**
     * 获取其他家庭成员信息
     *
     * @param cardno
     * @return
     */
    @Override
    public List<InfoListDataVo> getInfoListData(String cardno) {
        return familyMemberMapper.getInfoListData(cardno);
    }
}

