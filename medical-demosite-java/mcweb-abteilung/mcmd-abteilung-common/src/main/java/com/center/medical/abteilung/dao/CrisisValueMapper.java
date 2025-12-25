package com.center.medical.abteilung.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.abteilung.bean.param.CrisisValueParam;
import com.center.medical.abteilung.bean.vo.CrisisValueVo;
import com.center.medical.abteilung.bean.vo.GetKsVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Riskclient;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 高危人员管理表(Riskclient)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-22 19:29:10
 */
public interface CrisisValueMapper extends BaseMapper<Riskclient> {

    /**
     * 分页查询[高危人员管理表]列表
     *
     * @param page  分页参数
     * @param param Riskclient查询参数
     * @return 分页数据
     */
    IPage<CrisisValueVo> getPage(PageParam<CrisisValueVo> page, @Param("param") CrisisValueParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Riskclient getInfoById(@Param("id") String id);

    /**
     * 导出数据
     * @param param
     * @return
     */
    List<CrisisValueVo> getExportData(@Param("param") CrisisValueParam param);

    /**
     * 获取科室及体检结果
     * @param patientcode
     * @param key
     * @return
     */
    List<GetKsVo> getKs(@Param("patientcode") String patientcode,@Param("key") String key);
}
