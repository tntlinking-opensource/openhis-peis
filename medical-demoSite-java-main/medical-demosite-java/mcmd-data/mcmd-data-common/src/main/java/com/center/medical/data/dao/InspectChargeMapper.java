package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.vo.AllOutDataVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.InspectCharge;
import com.center.medical.data.bean.vo.ListDatasVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * JC检查项目收费项目关联表(InspectCharge)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:08
 */
public interface InspectChargeMapper extends BaseMapper<InspectCharge> {

    /**
     * 分页查询[JC检查项目收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param InspectCharge查询参数
     * @return 分页数据
     */
    IPage<InspectCharge> getList(PageParam<InspectCharge> page, @Param("param") InspectCharge param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    InspectCharge getInfoById(@Param("id") String id);

    /**
     * 获取拼接的检查项目名称
     *
     * @param idExamfeeitem
     * @return
     */
    String getExName(@Param("idExamfeeitem") String idExamfeeitem);

    /**
     * 项目列表-结果-手动输入结果模块项目展示
     *
     * @param idChargeFee
     * @param idSex
     * @return
     */
    List<AllOutDataVo> getAllOutData(@Param("idChargeFee") String idChargeFee, @Param("idSex") Integer idSex);

    /**
     * 得到相同检查项目的不同收费项目
     *
     * @param inspectId
     * @param itemId
     * @return
     */
    List<String> getRepeatItems(@Param("inspectid") String inspectId, @Param("str") List<String> itemId);

    /**
     * 编辑收费项目-右下表格数据
     *
     * @param page
     * @param id
     * @param type
     * @return
     */
    IPage<ListDatasVo> getAllItemsData(PageParam<ListDatasVo> page, @Param("id") String id, @Param("type") String type);
}
