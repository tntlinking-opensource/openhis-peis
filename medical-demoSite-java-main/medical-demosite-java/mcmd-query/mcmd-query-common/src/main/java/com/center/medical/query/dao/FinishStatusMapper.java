package com.center.medical.query.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.FinishStatusParam;
import com.center.medical.query.bean.vo.FCChargeDataVo;
import com.center.medical.query.bean.vo.FinishStatusVo;
import org.apache.ibatis.annotations.Param;

/**
 * 未检项目查询(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-13 16:32:12
 */
public interface FinishStatusMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<FinishStatusVo> getList(PageParam<FinishStatusVo> page, @Param("param") FinishStatusParam param);


    /**
     * 点击获取收费项目信息
     * @param page
     * @param patientcode
     * @return
     */
    IPage<FCChargeDataVo> getChargeData(PageParam<FCChargeDataVo> page,@Param("patientcode") String patientcode);
}
