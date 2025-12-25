package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.model.Card;
import com.center.medical.finance.bean.param.SDPageParam;
import com.center.medical.finance.bean.vo.SDPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检卡(Card)表数据库访问层
 *
 * @author ay
 * @since 2023-03-30 18:47:30
 */
public interface SpendDetailMapper extends BaseMapper<Card> {

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param Card查询参数
     * @return 分页数据
     */
    IPage<SDPageVo> getList(PageParam<SDPageVo> page, @Param("param") SDPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Card getInfoById(@Param("id") String id);

    /**
     * 导出体检卡消费明细
     *
     * @param param
     * @return
     */
    List<SDPageVo> getExportData(@Param("param") SDPageParam param);
}
