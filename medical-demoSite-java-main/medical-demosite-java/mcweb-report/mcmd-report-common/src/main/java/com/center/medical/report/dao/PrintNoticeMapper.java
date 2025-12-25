package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.model.PrintNotice;
import com.center.medical.report.bean.param.PrintNoticeParam;
import com.center.medical.report.bean.vo.PrintNoticeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:50
 */
public interface PrintNoticeMapper extends BaseMapper<PrintNotice> {


    /**
     * 分页查询职业结果告知书
     * @param page
     * @param param
     * @return
     */
    IPage<PrintNoticeVo> getPrintNoticePage(PageParam<PrintNoticeVo> page, @Param("param") PrintNoticeParam param);

    /**
     * 详情
     * @param id
     * @return
     */
    PrintNotice getInfoById(@Param("id")String id);

    /**
     * 查询所有符合条件的体检者所对应的PDF路径并返回
     * @param idPatientclass
     * @param patientcode
     * @return
     */
    List<String> getAllPdfUrl(@Param("idPatientclass")String idPatientclass,@Param("id") String patientcode);
}
