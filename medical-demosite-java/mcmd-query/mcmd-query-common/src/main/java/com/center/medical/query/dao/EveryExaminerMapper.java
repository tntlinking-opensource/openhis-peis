package com.center.medical.query.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageAdapter;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.dto.EETimeListDto;
import com.center.medical.query.bean.dto.EETimeListDto1;
import com.center.medical.query.bean.param.ECGroupDataParam;
import com.center.medical.query.bean.param.EEChargeDataParam;
import com.center.medical.query.bean.param.EveryExaminerParam;
import com.center.medical.query.bean.vo.EEChargeDataVo;
import com.center.medical.query.bean.vo.EveryExaminerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 每日体检明细(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface EveryExaminerMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param pageAdapter 分页参数
     * @param param       Peispatient查询参数
     * @return 分页数据
     */
    List<EveryExaminerVo> getList(@Param("pageAdapter") PageAdapter pageAdapter, @Param("param") EveryExaminerParam param);

    /**
     * 查询[QT体检者表]列表总数
     *
     * @param param Peispatient查询参数
     * @return 总数
     */
    Long countList(@Param("param") EveryExaminerParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 统计某段时间所有体检者
     *
     * @param param
     * @return
     */
    List<EETimeListDto> getTimeList(@Param("param") ECGroupDataParam param);

    /**
     * 根据类型查询 1团体体检、2团体疫苗、3团体核酸、4个人体检、5个人疫苗、6个人核酸。
     *
     * @param param
     * @param type
     * @return
     */
    List<EETimeListDto> getSqlNew1(@Param("param") ECGroupDataParam param, @Param("type") int type);

    /**
     * 根据类型查询 1团体体检、2团体疫苗、3团体核酸、4个人体检、5个人疫苗、6个人核酸。
     *
     * @param param
     * @param type
     * @return
     */
    List<EETimeListDto1> getSqlNew2(@Param("param") ECGroupDataParam param, @Param("type") int type);

    /**
     * 根据类型查询 1团体体检、2团体疫苗、3团体核酸、4个人体检、5个人疫苗、6个人核酸。
     *
     * @param param
     * @param type
     * @return
     */
    List<EETimeListDto1> getSqlNew3(@Param("param") ECGroupDataParam param, @Param("type") int type);

    /**
     * 统计某段时间团体体检者(按团体分组)
     *
     * @param param
     * @return
     */
    List<EETimeListDto> getGroupSql(@Param("param") ECGroupDataParam param);

    /**
     * 点击获取收费项目信息
     *
     * @param page
     * @param param
     * @return
     */
    IPage<EEChargeDataVo> getChargeData(PageParam<EEChargeDataVo> page, @Param("param") EEChargeDataParam param);

    /**
     * 导出每日体检者构成人员
     *
     * @param param
     * @return
     */
    List<EveryExaminerVo> getExportData(@Param("param") EveryExaminerParam param);
}
