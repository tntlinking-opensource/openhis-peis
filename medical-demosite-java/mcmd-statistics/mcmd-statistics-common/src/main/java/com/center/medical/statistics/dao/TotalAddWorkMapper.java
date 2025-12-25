package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.TotalAddWorkParam;
import com.center.medical.statistics.bean.vo.TotalAddWorkVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)表数据库访问层
 *
 * @author ay
 * @since 2023-04-19 19:06:09
 */
public interface TotalAddWorkMapper extends BaseMapper<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<TotalAddWorkVo> getList(PageParam<TotalAddWorkVo> page, @Param("param") TotalAddWorkParam param);


    /**
     * 导出加项统计
     * @param param
     * @return
     */
    List<TotalAddWorkVo> getExportData(@Param("param") TotalAddWorkParam param);
}
