package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.model.OutsideChargeItem;
import com.center.medical.abteilung.bean.param.SendRegisterParam;
import com.center.medical.abteilung.bean.vo.OutsideVo;
import com.center.medical.bean.vo.WsxmDataVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS外送项目表(OutsideChargeItem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:25
 */
public interface OutsideChargeItemMapper extends BaseMapper<OutsideChargeItem> {

    /**
     * 分页查询[KS外送项目表]列表
     *
     * @param page  分页参数
     * @param param OutsideChargeItem查询参数
     * @return 分页数据
     */
    IPage<OutsideVo> getList(PageParam<OutsideChargeItem> page, @Param("param") SendRegisterParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    OutsideChargeItem getInfoById(@Param("id") String id);

    /**
     * 不分页查询列表
     * @param param
     * @return
     */
    List<OutsideVo> findList(@Param("param")SendRegisterParam param);

    /**
     * 登记外送项目数据获取
     * @param page
     * @param key
     * @param patientcode
     * @return
     */
    IPage<WsxmDataVo> getPictureWsxmData(PageParam<WsxmDataVo> page,@Param("key") String key,@Param("patientcode") String patientcode);
}
