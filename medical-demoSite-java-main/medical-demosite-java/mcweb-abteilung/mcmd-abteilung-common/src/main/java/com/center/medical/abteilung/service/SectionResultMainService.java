package com.center.medical.abteilung.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.center.medical.abteilung.bean.model.SectionResultMain;
import com.center.medical.abteilung.bean.param.DivisionFaircheckParam;
import com.center.medical.abteilung.bean.vo.AdiconGridDataVo;
import com.center.medical.abteilung.bean.vo.DivisionFaircheckVo;
import com.center.medical.abteilung.bean.vo.DivisionInspectionVo;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.param.DivisionInspectionParam;
import com.center.medical.reception.bean.param.SetAdiconParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * KS科室检查结果主表(SectionResultMain)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-23 10:54:18
 */
public interface SectionResultMainService extends IService<SectionResultMain> {

    /**
     * 分页查询[KS科室检查结果主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<SectionResultMain> getPage(PageParam<SectionResultMain> page, SectionResultMain param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    SectionResultMain getInfoById(String id);

    /**
     * 检验科读卡
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    DivisionInspectionVo search(String patientCode, String ksId);

    /**
     * 反审核
     *
     * @param divisionInspectionParam
     * @return
     */
    Boolean reverse(DivisionInspectionParam divisionInspectionParam);

    /**
     * 审核
     *
     * @param divisionInspectionParam
     * @return
     */
    Boolean saOrUp(DivisionInspectionParam divisionInspectionParam);

    /**
     * 保存结伦词
     *
     * @param divisionInspectionParam
     * @return
     */
    Boolean saveJlc(DivisionInspectionParam divisionInspectionParam);

    /**
     * 清除数据
     *
     * @param patientCode
     * @return
     */
    Boolean clear(String patientCode);

    /**
     * 设置艾迪康代码
     *
     * @param testGrid
     * @return
     */
    Boolean setAdicon(List<SetAdiconParam> testGrid);

    /**
     * 一般检查读卡
     *
     * @param divisionFaircheckParam
     * @return
     */
    DivisionFaircheckVo searchFaircheck(DivisionFaircheckParam divisionFaircheckParam);

    /**
     * 根据体检号获取检查结果
     *
     * @param patientCode 体检号
     * @return
     */
    String getHealthSummarize(String patientCode);

    /**
     * 获取检验科艾迪康列表
     * @param patientCode
     * @return
     */
    List<AdiconGridDataVo> getAdiconGridData(String patientCode);

    /**
     * 根据 entity 条件，删除记录 ,对接瑞林萨尔健康管理系统
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DataSource(value = DataSourceType.RILIN)
    default boolean removeRilin(Wrapper<SectionResultMain> queryWrapper) {
        return SqlHelper.retBool(getBaseMapper().delete(queryWrapper));
    }

    /**
     * 插入（批量）,对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatchRilin(Collection<SectionResultMain> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}

