package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.vo.AllOutDataVo;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.InspectCharge;
import com.center.medical.data.bean.vo.ListDatasVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * JC检查项目收费项目关联表(InspectCharge)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:08
 */
public interface InspectChargeService extends IService<InspectCharge> {

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param InspectCharge查询参数
     * @return 分页数据
     */
    IPage<InspectCharge> getList(PageParam<InspectCharge> page, InspectCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    InspectCharge getInfoById(String id);

    /**
     * 项目列表-结果-手动输入结果模块项目展示
     *
     * @param patientcode
     * @param idChargeFee
     * @return
     */
    List<AllOutDataVo> getAllOutData(String patientcode, String idChargeFee);


    /**
     * 获取grid收费项目列表中是否存在相同的检查项目
     *
     * @param itemId
     * @return
     */
    String compareItemsToExam(List<String> itemId);

    /**
     * 编辑收费项目-右下表格数据
     *
     * @param page
     * @param id
     * @param type
     * @return
     */
    IPage<ListDatasVo> getAllItemsData(PageParam<ListDatasVo> page, String id, String type);

    /**
     * 批量修改插入 对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatchRilin(Collection<InspectCharge> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}

