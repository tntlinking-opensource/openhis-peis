package com.center.medical.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.center.medical.bean.model.PacsResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * PACS-数据(PacsResult)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:02
 */
public interface PacsResultService extends IService<PacsResult> {

    /**
     * 分页查询[PACS-数据]列表
     *
     * @param page  分页参数
     * @param param PacsResult查询参数
     * @return 分页数据
     */
    IPage<PacsResult> getList(PageParam<PacsResult> page, PacsResult param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    PacsResult getInfoById(String id);

    /**
     * 根据 entity 条件，删除记录 ,对接瑞林萨尔健康管理系统
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DataSource(value = DataSourceType.RILIN)
    default boolean removeRilin(Wrapper<PacsResult> queryWrapper) {
        return SqlHelper.retBool(getBaseMapper().delete(queryWrapper));
    }

    /**
     * 插入（批量）,对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatchRilin(Collection<PacsResult> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}

