package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.dto.SummaryAndPicturesDto;
import com.center.medical.bean.model.Describe;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KS科室描述、检查结果表(Describe)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:34
 */
public interface DescribeMapper extends BaseMapper<Describe> {

    /**
     * 分页查询[KS科室描述、检查结果表]列表
     *
     * @param page  分页参数
     * @param param Describe查询参数
     * @return 分页数据
     */
    IPage<Describe> getList(PageParam<Describe> page, @Param("param") Describe param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Describe getInfoById(@Param("id") String id);

    /**
     * 获取小结及图片数据
     * @param patientcode
     * @param deptNo
     * @return
     */
    List<SummaryAndPicturesDto> getSummaryAndPictures(@Param("patientcode") String patientcode, @Param("deptNo") String deptNo);

    /**
     * 获取老的小结及图片数据
     * @param patientcode
     * @param deptNo
     * @return
     */
    List<SummaryAndPicturesDto> getOldSummaryAndPictures(@Param("patientcode") String patientcode, @Param("deptNo") String deptNo);
}
