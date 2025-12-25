package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.AdvanceVisitWrite;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.AppointReturnParam;
import com.center.medical.member.bean.vo.AppointReturnVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环(AdvanceVisitWrite)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-16 14:03:32
 */
public interface AppointReturnMapper extends BaseMapper<AdvanceVisitWrite> {

    /**
     * 分页查询[主表记录体检者是否来检，如果来检，显示来检时间，便于筛选，在预约来检未检列表中显示来检时间且是否已检为未检的做出标记，在预约来检未检回访操作时将该状态、来检时间重置，以此进行无限循环]列表
     *
     * @param page  分页参数
     * @param param AdvanceVisitWrite查询参数
     * @return 分页数据
     */
    IPage<AppointReturnVo> getPage(PageParam<AppointReturnVo> page, @Param("param") AppointReturnParam param);

    /**
	 * 导出Excel
	 * @param param
	 * @return
	 */
	List<AppointReturnVo> export(@Param("param") AppointReturnParam param) ;

	/**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
	AdvanceVisitWrite getInfoById(@Param("id") String id);

}
