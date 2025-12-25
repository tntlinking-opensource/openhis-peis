package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.annotation.BranchScope;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.PTPageParam;
import com.center.medical.member.bean.vo.PreviewingTrackVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-02-10 14:25:16
 */
public interface PreviewingTrackMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    @BranchScope(alias = "a.fzx")
    IPage<PreviewingTrackVo> getList(PageParam<PreviewingTrackVo> page, @Param("param") PTPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 个检预检回访导出
     *
     * @param param
     * @return
     */
    List<PreviewingTrackVo> getExportData(@Param("param") PTPageParam param);
}
