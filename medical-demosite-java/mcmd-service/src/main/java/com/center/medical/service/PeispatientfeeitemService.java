package com.center.medical.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.center.medical.bean.dto.PriceAndFactPriceDto;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.param.ExportItemsParam;
import com.center.medical.bean.vo.*;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-18 19:40:42
 */
public interface PeispatientfeeitemService extends IService<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peispatientfeeitem> getPage(PageParam<Peispatientfeeitem> page, Peispatientfeeitem param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientfeeitem getInfoById(String id);

    /**
     * 查询未缴费项目
     *
     * @param userNo
     * @param patientCode
     * @return
     */
    List<Peispatientfeeitem> selectUnfees(String userNo, String patientCode);

    /**
     * 项目列表
     *
     * @param page
     * @param patientcode
     * @return
     */
    IPage<GetItemDataVo> getItemData(PageParam<GetItemDataVo> page, String patientcode);

    /**
     * 外送项目
     *
     * @param page
     * @param key
     * @param patientcode
     * @return
     */
    IPage<WsxmDataVo> getWsxmData(PageParam<WsxmDataVo> page, String key, String patientcode);

    /**
     * 科室加项右侧数据
     *
     * @param patientcode
     * @return
     */
    List<AddListDataVo> getAddListData(String patientcode);

    /**
     * 添加检查收费项目
     *
     * @param itemList
     * @return
     */
    String saOrUp(List<Peispatientfeeitem> itemList);

    /**
     * getFactPrice
     *
     * @param stringStringHashMap
     * @param size
     * @param j
     * @param isMakeGb
     * @return
     */
    Object[] getFactPrice(HashMap<String, String> stringStringHashMap, int size, int j, Boolean isMakeGb);

    /**
     * 无关联科室已检
     *
     * @param patientcode
     * @return
     */
    List<Peispatientfeeitem> irrelevantInspect(String patientcode);

    /**
     * @param pei void
     * @Title: 查找剩余的未检收费项目是否是弃检、迟检、退项状态判断是否需要分检完成，无科室的检查完
     * @author zhanghj
     * @since 2016年12月6日 V 1.0
     */
    void checkFj(Peispatient pei);

    /**
     * 外送登记项目列表
     *
     * @param page
     * @param patientcode
     * @return
     */
    IPage<OSItemDataVo> getOSItemData(PageParam<OSItemDataVo> page, String patientcode);

    /**
     * 导出体检者收费项目列表
     *
     * @param param
     * @return
     */
    List<ExportItemsVo> exportItems(ExportItemsParam param);

    /**
     * 获取所有尚未获取Lis数据的体检号
     *
     * @return
     */
    List<String> receiveLisDataUser();

    /**
     * 一键体检者收费项目去重
     *
     * @param patientcode 体检号
     * @return
     */
    Boolean deduplication(String patientcode);

    /**
     * 查询该科室未出结果的项目
     * @param patientcode
     * @return
     */
    int getDepartmentResults(String patientcode);

    /**
     * 获取原价和优惠价
     * @param patientCode
     * @return
     */
    PriceAndFactPriceDto getPriceAndFactprice(String patientCode);

    /**
     * 查询未收费项目
     * @param patientcode
     * @return
     */
    Integer unpaidItems(String patientcode);

    /**
     * 判断体检者收费项目是否重复
     * @param patientcode
     * @return
     */
    boolean isRepeat(String patientcode);

    /**
     * 获取未退费的项目数量
     * @param patientcode
     * @return
     */
    int getUnreimbursedProjects(String patientcode);

    /**
     * 批量修改插入 对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatchRilin(Collection<Peispatientfeeitem> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 判断是否包含早餐
     * @param patientcode
     * @return
     */
    Integer includesBreakfast(String patientcode);

    /**
     * 根据 entity 条件，删除记录 ,对接瑞林萨尔健康管理系统
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    @DataSource(value = DataSourceType.RILIN)
    default boolean removeRilin(Wrapper<Peispatientfeeitem> queryWrapper) {
        return SqlHelper.retBool(getBaseMapper().delete(queryWrapper));
    }

    /**
     * 插入（批量）,对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveBatchRilin(Collection<Peispatientfeeitem> entityList) {
        return saveBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}

