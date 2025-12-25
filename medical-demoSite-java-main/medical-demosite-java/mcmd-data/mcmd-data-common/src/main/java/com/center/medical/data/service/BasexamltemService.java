package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Basexamltem;
import com.center.medical.data.bean.model.BasexamltemSign;
import com.center.medical.data.bean.param.BaseExamItemParam;
import com.center.medical.data.bean.vo.ExamsByItemVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * JC检查项目表(Basexamltem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:28
 */
public interface BasexamltemService extends IService<Basexamltem> {

    /**
     * 分页查询[JC检查项目表]列表
     *
     * @param page  分页参数
     * @param param Basexamltem查询参数
     * @return 分页数据
     */
    IPage<Basexamltem> getList(PageParam<Basexamltem> page, BaseExamItemParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Basexamltem getInfoById(String id);

    /**
     * 根据inputCode获取检查项目名称
     *
     * @param page
     * @param inputCode
     * @return
     */
    IPage<Basexamltem> getAllJcid(PageParam<Basexamltem> page, String inputCode);

    /**
     * 新增/编辑操作
     *
     * @param basexamltem
     * @return
     */
    Boolean saOrUp(Basexamltem basexamltem);

    /**
     * 删除操作
     *
     * @param ids
     * @return
     */
    Boolean delete(List<String> ids);

    /**
     * 营养状况下拉数据
     * @return
     */
    List<BasexamltemSign> getCommonStateData();

    /**
     * 获取右侧表格子表格数据
     * @param page
     * @param id
     * @return
     */
    IPage<ExamsByItemVo> getExamsByItemId(PageParam<ExamsByItemVo> page, String id);

    /**
     * 批量修改插入 对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatchRilin(Collection<Basexamltem> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}

