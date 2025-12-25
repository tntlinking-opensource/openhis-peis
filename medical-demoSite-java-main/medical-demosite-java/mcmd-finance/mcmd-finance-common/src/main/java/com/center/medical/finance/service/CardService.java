package com.center.medical.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.param.CardConsumeParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.param.SCsaOrUpParam;
import com.center.medical.finance.bean.param.SendCardParam;
import com.center.medical.finance.bean.vo.ChangeDataVo;
import com.center.medical.finance.bean.vo.MedicalCardVo;
import com.center.medical.finance.bean.vo.SendCardVo;

import java.util.List;

/**
 * 体检卡(Card)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:30
 */
public interface CardService extends IService<Card> {

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param Card查询参数
     * @return 分页数据
     */
    IPage<SendCardVo> getList(PageParam<SendCardVo> page, SendCardParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Card getInfoById(String id);


    /**
     * 通过卡号获取记录
     *
     * @param cardNo
     */
    Card getInfoByNo(String cardNo);

    /**
     * 卡类型改变后获取卡前缀等字段
     *
     * @param typeId
     * @return
     */
    ChangeDataVo getChangeData(String typeId);

    /**
     * 新增发卡保存
     *
     * @param param
     * @return
     */
    Boolean saOrUp(SCsaOrUpParam param);

    /**
     * 修改领取人
     *
     * @param ids
     * @param id
     * @return
     */
    Boolean updateGotMan(List<String> ids, String id);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    Boolean removeCard(List<String> ids);

    /**
     * 体检卡绑定手机号
     *
     * @param ids
     * @param id
     * @return
     */
    Boolean saveBind(List<String> ids, String id);

    /**
     * 获取导出数据
     *
     * @param param
     * @return
     */
    List<SendCardVo> getExportData(SendCardParam param);

    /**
     * 体检卡搜索
     *
     * @param key
     * @return
     */
    List<MedicalCardVo> getMedicalCardAutoComData(String key);

    /**
     * 保存卡消费
     *
     * @param param 消费信息
     * @return 返回卡消费记录ID
     */
    String saveOrUpdateFee(CardConsumeParam param);

}

