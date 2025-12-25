package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ChargeDistributeParam;
import com.center.medical.statistics.bean.vo.CDGetTotalVo;
import com.center.medical.statistics.bean.vo.ChargeDistributeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收费项目分布情况(Peispatientfeeitem)表数据库访问层
 *
 * @author ay
 * @since 2023-04-18 19:47:26
 */
public interface ChargeDistributeMapper extends BaseMapper<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<ChargeDistributeVo> getList(PageParam<ChargeDistributeVo> page, @Param("param") ChargeDistributeParam param);


    /**
     * 导出体检收费项目分布
     * @param param
     * @return
     */
    List<ChargeDistributeVo> exportData(@Param("param") ChargeDistributeParam param);

    /**
     * 获取总数
     * @param param
     * @return
     */
    CDGetTotalVo getTotal(@Param("param") ChargeDistributeParam param);
}
