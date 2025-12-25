package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.dto.AccountNameDto;
import com.center.medical.finance.bean.model.KdPayway;
import com.center.medical.finance.bean.vo.KingdeeReserWayVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * kingdeepayway(KdPayway)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-02-17 09:56:43
 */
public interface KdPaywayMapper extends BaseMapper<KdPayway> {

    /**
     * 分页查询[kingdeepayway]列表
     *
     * @param page  分页参数
     * @param param KdPayway查询参数
     * @return 分页数据
     */
    IPage<KdPayway> getPage(PageParam<KdPayway> page, @Param("param") KdPayway param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键accountNo
     * @return 详情信息
     */
    KdPayway getInfoById(@Param("id") String id);

    /**
     * 获取银行结算方式列表
     *
     * @param page
     * @param key
     * @return
     */
    IPage<KingdeeReserWayVo> getKingdeeReserWay(PageParam<KingdeeReserWayVo> page, @Param("key") String key);

    /**
     * 获取账户名称
     * @param way
     * @return
     */
    List<AccountNameDto> getAccountName(@Param("way") String way);
}
