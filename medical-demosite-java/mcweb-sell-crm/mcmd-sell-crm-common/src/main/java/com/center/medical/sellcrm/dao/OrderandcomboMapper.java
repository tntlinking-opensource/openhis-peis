package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.BranchScope;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Orderandcombo;
import com.center.medical.sellcrm.bean.vo.OrderMealVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单与套餐关联表(Orderandcombo)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 18:38:08
 */
public interface OrderandcomboMapper extends BaseMapper<Orderandcombo> {

    /**
     * 分页查询[订单与套餐关联表]列表
     *
     * @param page  分页参数
     * @param param Orderandcombo查询参数
     * @return 分页数据
     */
    IPage<Orderandcombo> getPage(PageParam<Orderandcombo> page, @Param("param") Orderandcombo param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Orderandcombo getInfoById(@Param("id") String id);


    /**
     * 编辑默认加载订单关联的套餐
     *
     * @param page
     * @param ddId
     * @return
     */
    @BranchScope(alias = "f.fzxid")
    IPage<Orderandcombo> getTjtcData(PageParam<Orderandcombo> page, @Param("ddId") String ddId, @Param("isCopy") Integer isCopy);

    /**
     * 团检专属卡-套餐搜索
     * @param id
     * @param key
     * @return
     */
    List<OrderMealVo> getOrderMealData(@Param("id")String id, @Param("key") String key);
}
