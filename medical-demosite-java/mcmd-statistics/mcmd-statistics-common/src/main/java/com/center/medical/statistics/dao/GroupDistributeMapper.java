package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.statistics.bean.vo.GroupDistributeVo;
import org.apache.ibatis.annotations.Param;

/**
 * 体检团体分布情况(Createorder)表数据库访问层
 *
 * @author ay
 * @since 2023-04-18 19:31:32
 */
public interface GroupDistributeMapper extends BaseMapper<Createorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    IPage<GroupDistributeVo> getList(PageParam<GroupDistributeVo> page, @Param("param") BaseParam param);



}
