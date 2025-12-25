package com.center.medical.finance.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.LimitOperation;
import com.center.medical.bean.param.CardConsumeParam;
import com.center.medical.common.config.RsaConfig;
import com.center.medical.common.constant.Constants;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.core.domain.R;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.bean.BeanUtils;
import com.center.medical.common.utils.http.HttpUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.rsa.RSAUtil;
import com.center.medical.finance.bean.param.*;
import com.center.medical.dao.FamilyCardChargeMapper;
import com.center.medical.dao.LimitOperationMapper;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardType;
import com.center.medical.finance.bean.param.*;
import com.center.medical.finance.bean.vo.ChangeDataVo;
import com.center.medical.finance.bean.vo.MedicalCardVo;
import com.center.medical.finance.bean.vo.SendCardVo;
import com.center.medical.finance.dao.CardMapper;
import com.center.medical.finance.dao.CardTypeMapper;
import com.center.medical.finance.service.CardService;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.dao.CreatecomboMapper;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.system.service.CodeConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 体检卡(Card)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:30
 */
@Slf4j
@Service("cardService")
@RequiredArgsConstructor
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {

    private final CardMapper cardMapper;
    private final CardTypeMapper cardTypeMapper;
    private final MapperFacade mapperFacade;
    private final CreatemealMapper createmealMapper;
    private final CreatecomboMapper createcomboMapper;
    private final LimitOperationMapper limitOperationMapper;
    private final FamilyCardChargeMapper familyCardChargeMapper;
    private final CodeConfigService codeConfigService;

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param Card查询参数
     * @return 分页数据
     */
    @Override
    public IPage<SendCardVo> getList(PageParam<SendCardVo> page, SendCardParam param) {
        param.setCard(Card.JTK);
        // 没有决策管理或财务权限
        if (SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE) == false && SecurityUtils.hasRole(RoleAuthName.CAIWU) == false) {
            // 查询当前用户的数据
            param.setUserNo(SecurityUtils.getUserNo());
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
        return cardMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public Card getInfoById(String id) {
        return cardMapper.getInfoById(id);
    }


    /**
     * 通过卡号获取记录
     *
     * @param cardNo
     * @return
     */
    @Override
    public Card getInfoByNo(String cardNo) {
        return cardMapper.getInfoByNo(cardNo);
    }

    /**
     * 卡类型改变后获取卡前缀等字段
     *
     * @param typeId
     * @return
     */
    @Override
    public ChangeDataVo getChangeData(String typeId) {
        //卡类型
        CardType cty = cardTypeMapper.getInfoById(typeId);
        String a = cardMapper.getCardNo(typeId);
        ChangeDataVo vo = new ChangeDataVo();
        if (StringUtils.isNotEmpty(a)) {
            if (cty != null) {
                //卡前缀
                int len = cty.getCardPrefix() != null ? cty.getCardPrefix().length() : 0;
                vo.setCardPrefix(cty.getCardPrefix());
                vo.setCardLogo(cty.getCardLogo());
                vo.setBalanceLimit(cty.getCardMoney());
                vo.setCardState(cty.getMemo());
                vo.setZdkh(a);
                int max = Integer.valueOf(a.substring(len));
                if (cty.getCid() != null) {//新卡  系统自动生成的卡类型 4位前缀10位卡号
                    String beginCardNum = String.format("%0" + (10 - len) + "d", max + 1);
                    vo.setQskh(beginCardNum);
                } else {//旧卡   2位前缀8位卡号
                    String beginCardNum = String.format("%0" + (8 - len) + "d", max + 1);
                    vo.setQskh(beginCardNum);
                }
                vo.setType(cty.getType());
            }
        } else {
            if (cty != null) {
                int len = cty.getCardPrefix() != null ? cty.getCardPrefix().length() : 0;
                vo.setCardPrefix(cty.getCardPrefix());
                vo.setCardLogo(cty.getCardLogo());
                vo.setBalanceLimit(cty.getCardMoney());
                vo.setCardState(cty.getMemo());
                vo.setZdkh("无");
                if (cty.getCid() != null) {//新卡  系统自动生成的卡类型 4位前缀10位卡号
                    String beginCardNum = String.format("%0" + (10 - len) + "d", 1);
                    vo.setQskh(beginCardNum);
                } else {//旧卡   2位前缀8位卡号
                    String beginCardNum = String.format("%0" + (8 - len) + "d", 1);
                    vo.setQskh(beginCardNum);
                }
                vo.setType(cty.getType());
            }
        }
        return vo;
    }

    /**
     * 新增发卡保存
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(SCsaOrUpParam param) {
        String name = SecurityUtils.getUsername();
        //快速赋值
        Card odis = mapperFacade.map(param, Card.class);

        //获取起始卡号
        String qs = String.valueOf(param.getQskh());
        String kqz = param.getCardPrefix() == null ? "" : String.valueOf(param.getCardPrefix());
        String kh = qs;
        int b = Integer.valueOf(kh);
        //数量
        int a = param.getSl();
        List list = new ArrayList();
        //卡类型ID
        String typeId = odis.getTypeId();
        CardType ct = cardTypeMapper.getInfoById(typeId);
        Double tcPrice = null;//面值（活动专属卡和团检专属卡即套餐优惠价，其他卡就是初始金额）
        //团检专属卡类型id
        if (Card.GROUP.equals(typeId)) {
            //普通套餐表
            Createmeal meal = createmealMapper.getInfoById(odis.getTcId());
            if (meal == null) {
                //最小套餐
                Createcombo combo = createcomboMapper.getInfoById(odis.getTcId());
                //折后价格
                tcPrice = combo.getZhjg();
            } else {
                tcPrice = meal.getZhjg();
            }
        } else if (Card.ACT.equals(typeId)) {
            //活动专属卡类型id
            Createcombo combo = createcomboMapper.getInfoById(odis.getTcId());
            //折后价格
            tcPrice = combo.getZhjg();
        } else {
            //卡内剩余的金额或者积分
            tcPrice = odis.getBalanceLimit();
        }
        for (int i = 0; i < a; i++) {
            Card card = new Card();
            //复制
            BeanUtils.copyProperties(odis, card);
            //活动卡没有前缀，录多少位起始卡号就是多少位，其他卡不足8位补0
            String n = null;
            ////活动专属卡类型id
            if (Card.ACT.equals(typeId)) {
                int length = qs.length();
                n = String.format("%0" + (length - kqz.length()) + "d", (b + i));
            } else {
                n = ct.getCid() == null ?
                        String.format("%0" + (8 - kqz.length()) + "d", (b + i))
                        :
                        String.format("%0" + (10 - kqz.length()) + "d", (b + i))
                ;
            }
            Card ca = cardMapper.selectOne(new QueryWrapper<Card>()
                    .eq("card_no", kqz + n).eq("is_delete", 0));
            if (ObjectUtils.isNotEmpty(ca)) {
                throw new ServiceException("保存失败！存在相同的卡号:" + kqz + n);
            } else {
                // TODO: ??? 分页时查询的userNo，添加的时候却添加的userName
//                card.setCreateId(SecurityUtils.getUsername());
                card.setCreateId(SecurityUtils.getUserNo());
                card.setCardNo(kqz + n);
                //卡密码
                String generatePassword = param.getGeneratePassword();
                if ("true".equals(generatePassword)) {
                    card.setPassword(ToolUtil.generatePassword());
                }
                //发卡时，状态是未售
                card.setSellStatus(0);
                card.setIsDelete(0);
                card.setTcPrice(tcPrice);
                list.add(card);

                //记录开卡充值
                LimitOperation limitOperation = new LimitOperation();
                limitOperation.setCardId(card.getCardNo());
                limitOperation.setCardType(card.getTypeId());
                //记录增加或减少的金额或者积分
                limitOperation.setLimit(card.getBalanceLimit());
                limitOperation.setMemotext("发卡");
                limitOperation.setOperationTime(new Date());
                limitOperation.setOperationId(SecurityUtils.getUsername());
                limitOperation.setIsAdd(0);
                limitOperation.setHandleMoney(card.getBalanceLimit());
                limitOperation.setIsDelete(0);
                limitOperationMapper.insert(limitOperation);
            }
        }
        this.saveBatch(list);
        return Boolean.TRUE;
    }


    /**
     * 修改领取人
     *
     * @param ids
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateGotMan(List<String> ids, String id) {
        for (int i = 0, l = ids.size(); i < l; i++) {
            Card card = cardMapper.getInfoById(ids.get(i));
            if (ObjectUtils.isEmpty(card)) {
                throw new ServiceException("第" + (i + 1) + "个卡不存在");
            }
            //领取人ID
            card.setGetterId(id);
            cardMapper.updateById(card);
        }
        return Boolean.TRUE;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeCard(List<String> ids) {
        for (int i = 0; i < ids.size(); i++) {
            //体检卡
            Card zyoc = cardMapper.getInfoById(ids.get(i));
            if (null != zyoc) {
                // 判断是否消费过,卡额度操作表
                Long count = limitOperationMapper.selectCount(new QueryWrapper<LimitOperation>()
                        .eq("card_id", zyoc.getCardNo())
                        .eq("is_delete", 0));
                // TODO: 2023/2/24 开卡时就已经插入了一条记录,如果>0就是哪都删不了
//                if (count > 0) {
                if (count > 1) {
                    throw new ServiceException("卡号为" + zyoc.getCardNo() + "已经被使用，不可删除");
                }
                //将isDelete设置为1，为删除
                zyoc.setIsDelete(1);
                this.updateById(zyoc);
            } else {
                throw new ServiceException("第" + (i + 1) + "个卡号不存在");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 体检卡绑定手机号
     *
     * @param cardIds
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveBind(List<String> cardIds, String userId) {
        if (CollectionUtils.isEmpty(cardIds) || StringUtils.isEmpty(userId)) {
            throw new ServiceException("绑定失败，用户异常。");
        }
        for (String cardId : cardIds) {
            Card card = cardMapper.getInfoById(cardId);
            if (ObjectUtils.isEmpty(card)) {
                throw new ServiceException("绑定失败，体检卡已被删除!");
            }
            // TODO: 2023/2/24 涉及AppUserCard，未完成
//            if(get(AppUserCard.class, Restrictions.eq("cardId",cardId))!=null){
//                throw new ServiceException("绑定失败，卡号为"+card.getCardNo()+"的体检卡已经被绑定!");
//            }
//            AppUserCard newBean = new AppUserCard();
//            newBean.setCardId(cardId);
//            newBean.setUserId(userId);
//            cardServiceDao.save(newBean);
        }
        return Boolean.TRUE;
    }

    /**
     * 获取导出数据
     *
     * @param param
     * @return
     */
    @Override
    public List<SendCardVo> getExportData(SendCardParam param) {
        param.setCard(Card.JTK);
        // 没有决策管理和财务权限
        if (SecurityUtils.hasRole(RoleAuthName.JUECE_MANAGE) == false && SecurityUtils.hasRole(RoleAuthName.CAIWU) == false) {
            // 查询当前用户的数据
            param.setUserNo(SecurityUtils.getUserNo());
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
        return cardMapper.getExportData(param);
    }

    /**
     * 体检卡搜索
     *
     * @param key
     * @return
     */
    @Override
    public List<MedicalCardVo> getMedicalCardAutoComData(String key) {
        return cardMapper.getMedicalCardAutoComData(key);
    }

    /**
     * 保存卡消费
     *
     * @param param 消费信息
     * @return 返回卡消费记录ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public String saveOrUpdateFee(CardConsumeParam param) {
//        LimitOperation odis = mapperFacade.map(param, LimitOperation.class);
//        //获取卡信息
//        Card card = cardMapper.getInfoByNo(odis.getCardId());
//        if (Objects.isNull(card)) {
//            throw new ServiceException("找不到卡信息，请输入正确的卡号！");
//        }
//        log.info("卡消费，消费前卡信息：{}", card);
//
//        Double mon = 0.00;
//        Double lim = odis.getLimit();
//        Double a = 0.00;
//        switch (param.getKkfs()) {
//            case 1:
//                //1.体检卡
//                mon = card.getBalanceLimit();
//                a = Arith.add(mon, lim);
//                if (lim < 0) {
//                    //消费
//                    if (a < 0) {
//                        throw new ServiceException("扣费失败：体检卡可用金额不足！");
//                    }
//                }
//                card.setBalanceLimit(a);
//                break;
//            case 2:
//                //2.会员卡积分
//                mon = card.getBalanceJf();
//                a = Arith.add(mon, lim);
//                if (lim < 0) {
//                    //消费
//                    if (a < 0) {
//                        throw new ServiceException("扣费失败：会员卡可用积分不足！");
//                    }
//                }
//                odis.setMemberCardno(odis.getCardId());
//                card.setBalanceJf(a);
//                break;
//            case 5:
//                //5.会员卡余额
//                mon = card.getBalanceLimit();
//                a = Arith.add(mon, lim);
//                if (lim < 0) {
//                    //消费
//                    if (a < 0) {
//                        throw new ServiceException("扣费失败：会员卡可用金额不足！");
//                    }
//                }
//                odis.setMemberCardno(odis.getCardId());
//                card.setBalanceLimit(a);
//                break;
//            case 6:
//                //6.家庭卡余额
//                mon = card.getBalanceMoney();
//                a = Arith.add(mon, lim);
//                if (lim < 0) {
//                    //消费
//                    if (a < 0) {
//                        throw new ServiceException("扣费失败：家庭卡可用金额不足！");
//                    }
//                }
//                //家庭卡余额充值记录
//                FamilyCardCharge fccye = new FamilyCardCharge();
//                fccye.setIdPayway(param.getPayMode());
//                fccye.setStartJf(card.getBalanceJf());
//                fccye.setStartMoney(card.getBalanceMoney());
//                fccye.setCardno(param.getCardId());
//                fccye.setChargeTime(new Date());
//                fccye.setChargerUsername(param.getUserName());
//                fccye.setNote(param.getMemotext());
//                fccye.setType(param.getIsAdd());
//                fccye.setMoney(lim);
//                fccye.setJf(0.00);
//                fccye.setEndJf(card.getBalanceJf());
//                fccye.setEndMoney(a);
//                fccye.setPatientcode(param.getChargeId());
//                fccye.setConsumetype(param.getConsumetype());
//                //插入
//                familyCardChargeMapper.insert(fccye);
//                card.setBalanceMoney(a);
//                break;
//            case 7:
//                //7.家庭卡积分
//                mon = card.getBalanceJf();
//                a = Arith.add(mon, lim);
//                if (lim < 0) {
//                    //消费
//                    if (a < 0) {
//                        throw new ServiceException("扣费失败：家庭卡可用积分不足！");
//                    }
//                }
//                //家庭卡积分操作记录
//                FamilyCardCharge fcc = new FamilyCardCharge();
//                fcc.setIdPayway(param.getPayMode());
//                fcc.setStartJf(card.getBalanceJf());
//                fcc.setStartMoney(card.getBalanceMoney());
//                fcc.setCardno(param.getCardId());
//                fcc.setChargeTime(new Date());
//                fcc.setChargerUsername(param.getUserName());
//                fcc.setNote(param.getMemotext());
//                fcc.setType(param.getIsAdd());
//                fcc.setMoney(0.00);
//                fcc.setJf(lim);
//                fcc.setEndJf(a);
//                fcc.setEndMoney(card.getBalanceMoney());
//                fcc.setPatientcode(param.getChargeId());
//                fcc.setConsumetype(param.getConsumetype());
//                //插入
//                familyCardChargeMapper.insert(fcc);
//                card.setBalanceJf(a);
//                break;
//            default:
//                throw new ServiceException("请提供卡操作具体行为！");
//        }
//
//        odis.setIsAdd(lim < 0 ? 1 : 0);
//        //获取当前用户名
//        //String userName=toolUtil.userName();
//        String operationId = odis.getOperationId();
//        // 判断是否是从内网获取数据，操作人
//        if (StringUtils.isBlank(operationId)) {
//            //获取当前登录用户名称
//            operationId = SecurityUtils.getUserNo();
//        }
//        // 判断是否为空
//        if (StringUtils.isBlank(odis.getId())) {
//            //TODO wait 判断体检卡是否绑定app会员卡
//            //if (get(AppUserCard.class, Restrictions.eq("cardId", card.getId())) != null) {
//            //    throw new Exception("保存失败:体检卡已绑定");
//            //}
//            odis.setOperationId(operationId);
//            odis.setHandleMoney(a);
//            //设置isDelete字段为0
//            odis.setIsDelete(0);
//            Date now = new Date();
//            odis.setOperationTime(now);
//            odis.setCardType(card.getTypeId());
//            log.info("卡消费，消费记录信息：{}", odis);
//            limitOperationMapper.insert(odis);
//            if (StringUtils.isNotBlank(odis.getId())) {
//                log.info("卡消费，消费后卡信息：{}", card);
//                cardMapper.updateById(card);
//            } else {
//                throw new ServiceException("扣费失败！");
//            }
//        }
//        return odis.getId();
        // TODO: wait 先用老系统的卡支付,这些暂时先注释掉
        //老系统体检卡正负号和新系统正负号不同，所以要修改一下
        log.info("打印一下卡消费参数:{}", param);
        if (param.getKkfs() == 1) {
            //体检卡
            if (param.getIsAdd() == 1) {
                //体检卡消费传正数
                param.setLimit(Math.abs(param.getLimit()));
            } else if (param.getIsAdd() == 2) {
                //体检卡退费传负数
                param.setLimit(-Math.abs(param.getLimit()));
            }
        }

        RsaConfig rsaConfig = codeConfigService.getRsaConfig("11", Constants.RESERVATION_CARD_FLAG);
        log.info("rsaConfig里面的数据:{}", rsaConfig);
        if (ObjectUtils.isEmpty(rsaConfig)) {
            throw new ServiceException("请先配置会员卡体检卡RSA非对称加密配置!");
        }
        if (param.getKkfs() == 1) {
            //体检卡
            OldMemCardParam oldMemCardParam = mapperFacade.map(param, OldMemCardParam.class);
            oldMemCardParam.setAuthCode(rsaConfig.getAuthCode());
            oldMemCardParam.setConsumeType(param.getConsumetype());
            log.info("体检卡发送的参数:{}", oldMemCardParam);
            //公钥加密
            String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(oldMemCardParam), rsaConfig.getPublicKey());
            RsaParam rsaParam = new RsaParam(data, rsaConfig.getAuthCode());
            //发送post请求
            String s = HttpUtils.sendPost(rsaConfig.getAdress() + "expense.action", JSONUtil.toJsonStr(rsaParam));
            log.info("体检卡的返回信息:{}", s);
            R r = JSONUtil.toBean(s, R.class);
            if (500 == r.getCode()) {
                throw new ServiceException(r.getMsg());
            }
        } else if (param.getKkfs() == 2) {
            //会员卡积分
            OldPaCardNoParam oldPaCardNoParam = mapperFacade.map(param, OldPaCardNoParam.class);
            oldPaCardNoParam.setPatientcardno(param.getCardId());
            oldPaCardNoParam.setPatientcode(param.getChargeId());
            oldPaCardNoParam.setAuthCode(rsaConfig.getAuthCode());
            oldPaCardNoParam.setBranchCenter(SecurityUtils.getCId());
            log.info("会员卡发送的参数:{}", oldPaCardNoParam);
            //公钥加密
            String data = RSAUtil.publicEncrypt(JSONUtil.toJsonStr(oldPaCardNoParam), rsaConfig.getPublicKey());
            RsaParam rsaParam = new RsaParam(data, rsaConfig.getAuthCode());
            //发送post请求
            String s = HttpUtils.sendPost(rsaConfig.getAdress() + "memberExpense.action", JSONUtil.toJsonStr(rsaParam));
            log.info("会员卡的返回信息:{}", s);
            R r = JSONUtil.toBean(s, R.class);
            if (500 == r.getCode()) {
                throw new ServiceException(r.getMsg());
            }
        }
        return null;
    }
}

