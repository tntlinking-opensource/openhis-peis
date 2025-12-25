package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.GroupStatusParam;
import com.center.medical.statistics.bean.vo.GroupStatusVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-14 16:40:24
 */
public interface GroupStatusMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<GroupStatusVo> getList(PageParam<GroupStatusVo> page, @Param("param") GroupStatusParam param);


    /**
     * 导出体检者团体状态统计
     * @param param
     * @return
     */
    List<GroupStatusVo> exportData(@Param("param") GroupStatusParam param);
}
