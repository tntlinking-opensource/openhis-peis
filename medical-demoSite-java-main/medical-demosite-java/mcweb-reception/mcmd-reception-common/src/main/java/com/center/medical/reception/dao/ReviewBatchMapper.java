package com.center.medical.reception.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.reception.bean.param.ReviewBatchParam;
import com.center.medical.reception.bean.vo.RBListDataVo;
import com.center.medical.reception.bean.vo.ReviewBatchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-02-02 15:30:32
 */
public interface ReviewBatchMapper extends BaseMapper<Peispatient> {

    /**
    * 分页查询[QT体检者表]列表
    *
    * @param param Peispatient查询参数
    * @return 分页数据
    */
    List<ReviewBatchVo> getItemsListData(@Param("param") ReviewBatchParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 职业复查获取数据
     * @param param
     * @return
     */
    List<RBListDataVo> getListData(@Param("param")ReviewBatchParam param);
}
