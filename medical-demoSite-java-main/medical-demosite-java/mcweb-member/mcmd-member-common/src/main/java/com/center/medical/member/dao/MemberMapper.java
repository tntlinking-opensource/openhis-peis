package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.bean.param.ArchiveParam;
import com.center.medical.bean.param.MemberParam;
import com.center.medical.bean.vo.ArchiveVo;
import com.center.medical.bean.vo.MemberVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.IFPageParam;
import com.center.medical.member.bean.vo.InterflowVo;
import com.center.medical.member.bean.vo.MemberListVo;
import com.center.medical.member.bean.vo.MerExportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者档案表(Peispatientarchive)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-24 10:08:11
 */
public interface MemberMapper extends BaseMapper<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    IPage<MemberVo> getPage(PageParam<MemberVo> page, @Param("param") MemberParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(@Param("id") String id);

    /**
     * 根据条件筛选会员列表数据
     *
     * @param param
     */
    List<MerExportVo> getList(@Param("param") MemberParam param);

    /**
     * 分页查询档案列表
     *
     * @param page  分页参数
     * @param param 查询条件
     * @return 所有数据
     */
    IPage<ArchiveVo> getArchivePage(PageParam<Peispatientarchive> page, @Param("param") ArchiveParam param);

    /**
     * 会员管理沟通记录分页查询
     *
     * @param page
     * @param param
     * @return
     */
    IPage<InterflowVo> getIFPage(PageParam<InterflowVo> page, @Param("param") IFPageParam param);

    /**
     * 沟通记录导出数据
     *
     * @param param
     * @return
     */
    List<InterflowVo> getIFExportData(@Param("param") IFPageParam param);

    /**
     * 分页查询平台会员列表数据
     *
     * @param param
     * @return
     */
    List<MemberListVo> getMemberListData( @Param("param") MemberParam param);

    /**
     * 导出会员列表数据
     *
     * @param param
     * @return
     */
    List<MemberListVo> getExportData(@Param("param") MemberParam param);

    /**
     * 查询总数
     *
     * @param param
     * @return
     */
    Long getMemberTotal(@Param("param") MemberParam param);
}
