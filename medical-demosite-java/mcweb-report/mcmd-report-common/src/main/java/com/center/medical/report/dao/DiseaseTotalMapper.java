package com.center.medical.report.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.report.bean.param.HealthTotalParam;
import com.center.medical.report.bean.vo.CommonDataVo;
import com.center.medical.report.bean.vo.DtPeispatientVo;
import com.center.medical.report.bean.vo.VerdictDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分页获取分检完成待总检人员数据库访问层
 *
 * @author 路飞船长
 * @since 2022-12-07 18:53:54
 */
public interface DiseaseTotalMapper extends BaseMapper<Peispatient> {

    /**
     * 分页获取分检完成待总检人员
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<DtPeispatientVo> getPage(PageParam<DtPeispatientVo> page, @Param("param") HealthTotalParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 分检-普通 界面 数据
     * @param patientno
     * @return
     */
    List<CommonDataVo> getCommonListData(@Param("patientno")String patientno);

    /**
     * 科室小结 界面 数据
     * @param patientno
     * @param depid
     * @return
     */
    List<VerdictDataVo> getVerdictData(@Param("patientno")String patientno,@Param("depid")String depid);

    /**
     * 如果下了复查处理意见，但没有复查项目，提示并弹出复查界面
     * @param patientcode
     * @return
     */
    int checkHarmTotal(@Param("patientcode") String patientcode);
}
