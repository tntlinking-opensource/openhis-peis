package com.center.medical.olddata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.param.OnlineImportParam;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.OrCreateorder;
import com.center.medical.olddata.bean.param.ImportTjPeopleParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表(Createorder)数据库访问层
 *
 * @author ay
 * @since 2023-07-25 18:20:57
 */
@DataSource(value = DataSourceType.SLAVE)
public interface OrCreateorderMapper extends BaseMapper<OrCreateorder> {

    /**
     * 分页查询[订单表]列表
     *
     * @param page  分页参数
     * @param param Createorder查询参数
     * @return 分页数据
     */
    IPage<OrCreateorder> getPage(PageParam<OrCreateorder> page, @Param("param") OrCreateorder param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    OrCreateorder getInfoById(@Param("id") String id);

    /**
     * 根据订单号获取记录详情
     *
     * @param ddh
     * @return
     */
    OrCreateorder getInfoByDdh(@Param("ddh") String ddh);

    /**
     * 查询未完成的订单
     * @return
     */
    List<OrCreateorder> getIncomplete(@Param("param") ImportTjPeopleParam param);

    /**
     * 获取线上导入数据
     * @param param
     * @return
     */
    List<OrCreateorder> getOnlineImport(@Param("param") OnlineImportParam param);
}
