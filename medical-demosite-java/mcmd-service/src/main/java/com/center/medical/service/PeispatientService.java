package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.dto.PatientDto;
import com.center.medical.bean.dto.PatientDuplicate;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.param.OrderUserParam;
import com.center.medical.bean.param.PatientDtoParam;
import com.center.medical.bean.param.ReportSearchCodeParam;
import com.center.medical.bean.param.RequestParam;
import com.center.medical.bean.vo.OrderUserVo;
import com.center.medical.bean.vo.SearchCodeVo;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author 路飞船长
 * @since 2022-12-06 10:15:51
 */
public interface PeispatientService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<Peispatient> getPage(PageParam<Peispatient> page, Peispatient param);


    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);


    /**
     * 体检者表格数据
     *
     * @param page
     * @param ksId
     * @return
     */
    IPage<Peispatient> listData(PageParam<Peispatient> page, String ksId);


    /**
     * 通过体检码获取记录
     *
     * @param patientCode
     * @return
     */
    Peispatient getByPatientCode(String patientCode);

    /**
     * 删除体检号
     *
     * @param patientCode
     * @return
     */
    Boolean deletePeispatient(String patientCode);

    /**
     * 搜索体检号
     *
     * @param param
     * @return
     */
    List<SearchCodeVo> searchCode(ReportSearchCodeParam param);

    /**
     * 查询体检者
     *
     * @param param 筛选条件
     * @return
     */
    List<PatientDto> getPatientDto(PatientDtoParam param);

    List<PatientDuplicate> geDuplicate(String ddh);

    Boolean deduplication(PatientDuplicate item);

    /**
     * 执行去重，delCodes要过滤newPt
     *
     * @param newPt    保留体检者
     * @param upToApp  是否将体检号更新为APP开头
     * @param delCodes 删除的体检号
     * @return
     */
    Boolean execDeduplication(Peispatient newPt, Boolean upToApp, List<String> delCodes);

    /**
     * 根据订单号获取体检者列表
     * @param params 查询条件
     * @return
     */
    IPage<OrderUserVo> getByDdh(PageParam<OrderUserVo> page, OrderUserParam params);

    /**
     * 查询推送给第三方的体检者数据
     * @param params
     * @return
     */
    void pushDataToCoo(RequestParam params);

    /**
     * 根据 ID 查询 ,对接瑞林萨尔健康管理系统
     *
     * @param id 主键ID
     */
    @DataSource(value = DataSourceType.RILIN)
    default Peispatient getByIdRilin(Serializable id) {
        return getBaseMapper().selectById(id);
    }

    /**
     * 批量修改插入 对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatchRilin(Collection<Peispatient> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 检查体检号和手机号是否相符合
     * @param patientcode
     * @param phone
     * @return
     */
    long checkPatientcode(String patientcode, String phone);
}

