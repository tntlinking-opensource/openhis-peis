package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Riskclientcon;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 高危人员关联表(Riskclientcon)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-22 19:29:10
 */
public interface RiskclientconService extends IService<Riskclientcon> {

    /**
     * 分页查询[高危人员关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Riskclientcon> getPage(PageParam<Riskclientcon> page, Riskclientcon param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Riskclientcon getInfoById(String id);

    /**
     * 批量修改插入 对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatchRilin(Collection<Riskclientcon> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

}

