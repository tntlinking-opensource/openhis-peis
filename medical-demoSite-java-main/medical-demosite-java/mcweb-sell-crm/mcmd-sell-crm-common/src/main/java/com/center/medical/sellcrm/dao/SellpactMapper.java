package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Sellpact;
import com.center.medical.sellcrm.bean.param.SellpactLoseCustParam;
import com.center.medical.sellcrm.bean.param.SellpactParam;
import com.center.medical.sellcrm.bean.vo.SellpactVo;
import org.apache.ibatis.annotations.Param;

/**
 * 销售合同维护表(Sellpact)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 11:51:25
 */
public interface SellpactMapper extends BaseMapper<Sellpact> {

    /**
     * 分页查询[销售合同维护表]列表
     *
     * @param page  分页参数
     * @param param Sellpact查询参数
     * @return 分页数据
     */
    IPage<Sellpact> getPage(PageParam<Sellpact> page, @Param("param") SellpactParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Sellpact getInfoById(@Param("id") String id);

    /**
     * 分页查询流失客户
     * @param page
     * @param sellpactLoseCustParam
     * @return
     */
    IPage<SellpactVo> getList(PageParam<SellpactVo> page, @Param("param")SellpactLoseCustParam sellpactLoseCustParam);

    /**
     * 通过主键查找流失客户
     * @param id
     * @return
     */
    SellpactVo getloseCustInfoById(@Param("id") String id);
}
