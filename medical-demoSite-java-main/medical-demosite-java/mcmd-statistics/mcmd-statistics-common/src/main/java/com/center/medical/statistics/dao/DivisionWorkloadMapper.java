package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.DivisionWorkParam;
import com.center.medical.statistics.bean.vo.DivisionWorkVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科室工作量(Peispatientfeeitem)表数据库访问层
 *
 * @author ay
 * @since 2023-04-18 19:47:26
 */
public interface DivisionWorkloadMapper extends BaseMapper<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    IPage<DivisionWorkVo> getList(PageParam<DivisionWorkVo> page, @Param("param") DivisionWorkParam param);


    /**
     * 导出科室工作量统计
     * @param param
     * @return
     */
    List<DivisionWorkVo> exportData(@Param("param") DivisionWorkParam param);
}
