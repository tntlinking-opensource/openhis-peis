package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Review;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.param.RecheckePrintParam;
import com.center.medical.sellcrm.bean.vo.RecheckePrintVo;
import org.apache.ibatis.annotations.Param;

/**
 * ZJ复查表(Review)表数据库访问层
 *
 * @author ay
 * @since 2023-02-08 11:58:35
 */
public interface RecheckePrintMapper extends BaseMapper<Review> {

    /**
    * 分页查询[ZJ复查表]列表
    *
    * @param page 分页参数
    * @param param Review查询参数
    * @return 分页数据
    */
    IPage<RecheckePrintVo> getList(PageParam<RecheckePrintVo> page, @Param("param") RecheckePrintParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Review getInfoById(@Param("id") String id);

}
