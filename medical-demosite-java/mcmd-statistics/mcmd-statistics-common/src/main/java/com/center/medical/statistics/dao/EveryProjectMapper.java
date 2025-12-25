package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.AnalyseInfoParam;
import com.center.medical.statistics.bean.param.EveryProjectParam;
import com.center.medical.statistics.bean.vo.AnalyseInfoVo;
import com.center.medical.statistics.bean.vo.EveryProjectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 每日体检项目统计(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface EveryProjectMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<EveryProjectVo> getList(PageParam<EveryProjectVo> page, @Param("param") EveryProjectParam param);


    /**
     * 右侧体检者数据
     * @param page
     * @param param
     * @return
     */
    IPage<AnalyseInfoVo> analyseInfo(PageParam<AnalyseInfoVo> page,@Param("param") AnalyseInfoParam param);


    /**
     * 导出每日项目体检量
     * @param param
     * @return
     */
    List<EveryProjectVo> exportData(@Param("param") EveryProjectParam param);

    /**
     * 导出体检人员清单
     * @param param
     * @return
     */
    List<AnalyseInfoVo> exportInfo( @Param("param") AnalyseInfoParam param);
}
