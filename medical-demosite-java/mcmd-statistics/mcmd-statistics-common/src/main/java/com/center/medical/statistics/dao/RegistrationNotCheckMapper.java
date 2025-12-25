package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.RegistrationNotCheckParam;
import com.center.medical.statistics.bean.vo.RegistrationNotCheckVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 登记未检客户统计(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2023-12-18 15:52:57
 */
public interface RegistrationNotCheckMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<RegistrationNotCheckVo> getPage(PageParam<RegistrationNotCheckVo> page, @Param("param") RegistrationNotCheckParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 导出登记未检客户统计
     * @param param
     * @return
     */
    List<RegistrationNotCheckVo> export( @Param("param") RegistrationNotCheckParam param);
}
