package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.param.DIGriddataParam;
import com.center.medical.reception.bean.param.DivisionInspectionParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * LIS结果(LisPacs数据)(Peispatientexamitem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:50
 */
public interface PeispatientexamitemService extends IService<Peispatientexamitem> {

    /**
     * 分页查询[LIS结果(LisPacs数据)]列表
     *
     * @param page  分页参数
     * @param param Peispatientexamitem查询参数
     * @return 分页数据
     */
    IPage<Peispatientexamitem> getList(PageParam<Peispatientexamitem> page, Peispatientexamitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Peispatientexamitem getInfoById(String id);

    /**
     * 检验科结果分页查询
     *
     * @param page
     * @param divisionInspectionParam
     * @return
     */
    IPage<Peispatientexamitem> searchDivision(PageParam<Peispatientexamitem> page, DivisionInspectionParam divisionInspectionParam);

    /**
     * 设置艾迪康代码,获取检验科体检者收费项目列表数据
     *
     * @param patientCode
     * @return
     */
    List<Peispatientfeeitem> getAdiconGridData(String patientCode);

    /**
     * 获取收费项目表格数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Peispatientexamitem> getgriddata(PageParam<Peispatientexamitem> page, DIGriddataParam param);

    /**
     * 根据 entity 条件，删除记录 ,对接瑞林萨尔健康管理系统
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DataSource(value = DataSourceType.RILIN)
    default boolean removeRilin(Wrapper<Peispatientexamitem> queryWrapper) {
        return SqlHelper.retBool(getBaseMapper().delete(queryWrapper));
    }

    /**
     * 插入（批量）,对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatchRilin(Collection<Peispatientexamitem> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}

