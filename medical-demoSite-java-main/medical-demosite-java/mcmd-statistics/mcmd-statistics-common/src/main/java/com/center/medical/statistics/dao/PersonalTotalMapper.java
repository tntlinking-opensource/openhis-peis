package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.PTTotalListParam;
import com.center.medical.statistics.bean.param.PersonalTotalParam;
import com.center.medical.statistics.bean.vo.PTTotalListVo;
import com.center.medical.statistics.bean.vo.PersonalTotalVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface PersonalTotalMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<PersonalTotalVo> getList(PageParam<PersonalTotalVo> page, @Param("param") PersonalTotalParam param);


    /**
     * 导出销售团检统计
     * @param param
     * @return
     */
    List<PersonalTotalVo> getExportData(@Param("param") PersonalTotalParam param);

    /**
     * 查询右边列表
     * @param param
     * @return
     */
    List<PTTotalListVo> getTotalList(@Param("param") PTTotalListParam param);
}
