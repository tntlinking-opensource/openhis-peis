package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.dto.GetTempFeeitemDto;
import com.center.medical.bean.model.TempFeeitem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.GetTempFeeitemParam;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科室临时加项表(TempFeeitem)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:45
 */
public interface TempFeeitemMapper extends BaseMapper<TempFeeitem> {

    /**
     * 分页查询[科室临时加项表]列表
     *
     * @param page  分页参数
     * @param param TempFeeitem查询参数
     * @return 分页数据
     */
    IPage<TempFeeitem> getList(PageParam<TempFeeitem> page, @Param("param") TempFeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TempFeeitem getInfoById(@Param("id") String id);

    /**
     * 获取加项收费数据
     * @param param
     * @return
     */
    List<GetTempFeeitemDto> getTempFeeitem(@Param("param") GetTempFeeitemParam param);
}
