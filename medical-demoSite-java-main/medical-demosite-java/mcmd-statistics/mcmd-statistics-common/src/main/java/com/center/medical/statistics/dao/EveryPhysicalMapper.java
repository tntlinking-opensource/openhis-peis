package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.vo.EPTotalVo;
import com.center.medical.statistics.bean.vo.EveryPhysicalVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 每日体检量统计(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface EveryPhysicalMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<EveryPhysicalVo> getList(PageParam<EveryPhysicalVo> page, @Param("param") BaseParam param);

    /**
     * 导出每日体检量统计
     * @param param
     * @return
     */
    List<EveryPhysicalVo> exportData(@Param("param") BaseParam param);

    /**
     * 合计数据3 健康领取,职业领取
     * @param param
     * @return
     */
    EPTotalVo getTotal3(@Param("param") BaseParam param);

    /**
     * 合计数据1 登记，开始
     * @param param
     * @return
     */
    EPTotalVo getTotal1(@Param("param") BaseParam param);

    /**
     * 合计数据2 健康，职业总检
     * @param param
     * @return
     */
    EPTotalVo getTotal2(@Param("param") BaseParam param);
}
