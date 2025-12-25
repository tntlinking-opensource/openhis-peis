package com.center.medical.query.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.dto.EETimeListDto;
import com.center.medical.query.bean.param.ECGroupDataParam;
import com.center.medical.query.bean.param.EEChargeDataParam;
import com.center.medical.query.bean.param.EveryExaminerParam;
import com.center.medical.query.bean.vo.EEChargeDataVo;
import com.center.medical.query.bean.vo.EveryExaminerVo;

import java.util.List;

/**
 * 每日体检明细(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface EveryExaminerService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<EveryExaminerVo> getList(PageParam<EveryExaminerVo> page, EveryExaminerParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 获取分组数据
     *
     * @param param
     * @return
     */
    List<EETimeListDto> getGroupData(ECGroupDataParam param);

    /**
     * 点击获取收费项目信息
     *
     * @param page
     * @param param
     * @return
     */
    IPage<EEChargeDataVo> getChargeData(PageParam<EEChargeDataVo> page, EEChargeDataParam param);

    /**
     * 导出每日体检者构成人员
     *
     * @param param
     * @return
     */
    List<EveryExaminerVo> getExportData(EveryExaminerParam param);
}

