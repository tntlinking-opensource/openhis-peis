package com.center.medical.reception.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.reception.bean.dto.GIGriddataDto;
import com.center.medical.reception.bean.param.GCSaOrUpParam;
import com.center.medical.reception.bean.param.ItemListParam;
import com.center.medical.reception.bean.param.ListDataParam;
import com.center.medical.reception.bean.vo.ItemListVo;
import com.center.medical.reception.bean.vo.ListDataVo;

import java.util.List;
import java.util.Map;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-02-02 15:30:33
 */
public interface ItemListService extends IService<Peispatient> {

    /**
    * 分页查询[QT体检者表]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<ItemListVo> getList(PageParam<ItemListVo> page, ItemListParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Peispatient getInfoById(String id);

    /**
     * 收费项目下拉
     * @param page
     * @param param
     * @return
     */
    IPage<ListDataVo> getListData(PageParam<ListDataVo> page, ListDataParam param);

    /**
     * 团检退项匹配最小套餐
     * @param griddata
     * @param ids
     */
    Boolean compareMinTcContent(List<GIGriddataDto> griddata, List<String> ids);

    /**
     * 右侧保存
     * @param param
     * @return
     */
    Boolean saOrUp(GCSaOrUpParam param);

    /**
     * 禁检、反禁检操作
     * @param paused
     * @param ids
     * @return
     */
    Boolean savePausedStatus(Integer paused, List<String> ids);

    /**
     * 获取右侧收费项目
     * @param patientCode
     * @param type
     * @return
     */
    List<Map<String, Object>> getExamfeeByPatientCode(String patientCode, String type);
}

