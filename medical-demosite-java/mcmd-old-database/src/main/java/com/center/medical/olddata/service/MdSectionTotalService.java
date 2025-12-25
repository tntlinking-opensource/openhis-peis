package com.center.medical.olddata.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdSectionTotal;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * ZJ总检主表(MdSectionTotal)服务接口
 *
 * @author ay
 * @since 2023-11-10 14:27:20
 */
public interface MdSectionTotalService extends IService<MdSectionTotal> {

    /**
     * 分页查询[ZJ总检主表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSectionTotal> getPage(PageParam<MdSectionTotal> page, MdSectionTotal param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSectionTotal getInfoById(String id);

    /**
     * 根据 entity 条件，删除记录 ,对接瑞林萨尔健康管理系统
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DataSource(value = DataSourceType.RILIN)
    default boolean removeRilin(Wrapper<MdSectionTotal> queryWrapper) {
        return SqlHelper.retBool(getBaseMapper().delete(queryWrapper));
    }

    /**
     * 插入（批量）,对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatchRilin(Collection<MdSectionTotal> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}

