package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.PeispatientParam;
import com.center.medical.member.bean.vo.PeispatientEditVo;
import com.center.medical.member.bean.vo.PeispatientVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-02 10:12:36
 */
public interface PositiveTrackMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<PeispatientVo> getList(PageParam<Peispatient> page, @Param("param") PeispatientParam param);

	/**
	 * 导出Excel
	 * @param param
	 * @return
	 */
    List<PeispatientVo> export(@Param("param") PeispatientParam param) ;

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    List<PeispatientEditVo> getInfoById(@Param("id") String id);

}
