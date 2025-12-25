package com.center.medical.query.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.CCPageParam;
import com.center.medical.query.bean.vo.CCPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * QT体检者表(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface ChargeCollectionMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<CCPageVo> getList(PageParam<CCPageVo> page, @Param("param") CCPageParam param);


    /**
     * 导出自费收费汇总
     *
     * @param param
     * @return
     */
    List<CCPageVo> getExportData(@Param("param") CCPageParam param);

    /**
     * 查询导出数据-总计
     * @param param
     * @param resultList
     * @return
     */
    List<Map<String, String>> getTotalCollectionSql(@Param("param") CCPageParam param, @Param("list") List<String> resultList);
}
