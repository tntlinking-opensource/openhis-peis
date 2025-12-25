package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.param.SendCardParam;
import com.center.medical.finance.bean.vo.MedicalCardVo;
import com.center.medical.finance.bean.vo.SendCardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检卡(Card)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:30
 */
public interface CardMapper extends BaseMapper<Card> {

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param Card查询参数
     * @return 分页数据
     */
    IPage<SendCardVo> getList(PageParam<SendCardVo> page, @Param("param") SendCardParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Card getInfoById(@Param("id") String id);

    /**
     * 获取卡编号
     * @param typeId
     * @return
     */
    String getCardNo(@Param("typeId")String typeId);

    /**
     * 获取导出数据
     * @param param
     * @return
     */
    List<SendCardVo> getExportData(@Param("param")SendCardParam param);

    /**
     * 体检卡搜索
     * @param key
     * @return
     */
    List<MedicalCardVo> getMedicalCardAutoComData(@Param("key")String key);

    /**
     * 通过卡号获取记录
     * @param cardNo
     * @return
     */
    Card getInfoByNo(@Param("cardNo")String cardNo);
}
