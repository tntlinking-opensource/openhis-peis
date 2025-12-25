package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.report.bean.model.TotalVerdict;
import com.center.medical.report.bean.param.TotalVerdictParam;
import com.center.medical.report.bean.vo.TotalVerdictVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 总检管理-总检结论词(TotalVerdict)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-08 09:24:21
 */
public interface TotalVerdictMapper extends BaseMapper<TotalVerdict> {

    List<TotalVerdictVo> getList(TotalVerdictParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TotalVerdict getInfoById(@Param("id") String id);

    /**
     * 获取健康总检结论词列表数据
     * @param patientno
     * @param dh
     * @return
     */
    List<TotalVerdictVo> getVerdictListData(@Param("patientcode")String patientno,@Param("dh") String dh);
}
