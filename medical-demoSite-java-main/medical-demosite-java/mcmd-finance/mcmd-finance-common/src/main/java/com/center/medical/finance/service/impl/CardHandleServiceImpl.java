package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.CHFormDataDto;
import com.center.medical.finance.bean.dto.CHGridDataDto;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.model.CardPayway;
import com.center.medical.finance.bean.param.CHPageParam;
import com.center.medical.finance.bean.param.CHSaOrUpParam;
import com.center.medical.finance.bean.vo.CHPageVo;
import com.center.medical.finance.dao.CardHandleMapper;
import com.center.medical.finance.dao.CardMapper;
import com.center.medical.finance.dao.CardPaywayMapper;
import com.center.medical.finance.service.CardHandleService;
import com.center.medical.finance.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 体检卡办理(Card)表服务实现类
 *
 * @author ay
 * @since 2023-03-30 18:47:31
 */
@Slf4j
@Service("cardHandleService")
@RequiredArgsConstructor
public class CardHandleServiceImpl extends ServiceImpl<CardHandleMapper, Card> implements CardHandleService {

    private final CardHandleMapper cardHandleMapper;
    private final CardMapper cardMapper;
    private final MapperFacade mapperFacade;
    private final CardPaywayMapper cardPaywayMapper;
    private final CardService cardService;

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param Card查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CHPageVo> getList(PageParam<CHPageVo> page, CHPageParam param) {
        return cardHandleMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Card getInfoById(String id) {
        return cardHandleMapper.getInfoById(id);
    }


    /**
     * 导出体检卡管理
     *
     * @param param
     * @return
     */
    @Override
    public List<CHPageVo> getExportData(CHPageParam param) {
        return cardHandleMapper.getExportData(param);
    }


    /**
     * 体检卡办理保存或修改
     *
     * @param param
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saOrUp(CHSaOrUpParam param) {
        //登录用户
        String userId = SecurityUtils.getUserNo();
        Date sellTime = new Date();
        String processId = UUID.randomUUID().toString().replaceAll("-", "");
        CHFormDataDto jo = param.getFormdata();

        Double sellPrice = jo.getSellPrice();
        Double zk = jo.getZk();
        String purchaser = jo.getPurchaser();
        String phone = jo.getPhone();

        List<String> ids = jo.getIds();
        //批量查询体检卡
        List<Card> cards = cardMapper.selectList(new QueryWrapper<Card>().in("id", ids));
        for (Card card : cards) {
            //销售状态：0或null未售 1.已售
            if (card.getSellStatus() != null && card.getSellStatus().intValue() == 1) {
                throw new ServiceException("卡号为" + card.getCardNo() + "的体检卡已经售卡，无法再进行卡办理。");
            }
            //已售
            card.setSellStatus(1);
            card.setSellId(userId);
            card.setSellTime(sellTime);
            card.setSellPrice(sellPrice);
            card.setZk(zk);
            card.setPhone(phone);
            card.setPurchaser(purchaser);
            card.setProcessId(processId);
        }
        cardService.updateBatchById(cards);

        //卡办理收款方式表
        List<CHGridDataDto> griddata = param.getGriddata();
        for (CHGridDataDto cp : griddata) {
            CardPayway fh = mapperFacade.map(cp, CardPayway.class);
            fh.setProcessId(processId);
            fh.setMoneyamountpaiddate(sellTime);
            fh.setIdFeecharger(userId);
            //已收
            fh.setIsCharged(1);
            cardPaywayMapper.insert(fh);
        }
        return Boolean.TRUE;
    }

}

