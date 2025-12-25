package com.center.medical.appadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.IndexImg;
import com.center.medical.appadmin.bean.param.IndexImgParam;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 主页轮播图(IndexImg)数据库访问层
 *
 * @author ay
 * @since 2024-03-19 12:00:05
 */
public interface IndexImgMapper extends BaseMapper<IndexImg> {

    /**
     * 分页查询[主页轮播图]列表
     *
     * @param page  分页参数
     * @param param IndexImg查询参数
     * @return 分页数据
     */
    @DataSource(value = DataSourceType.APPLET)
    IPage<IndexImg> getPage(PageParam<IndexImg> page, @Param("param") IndexImgParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键imgId
     * @return 详情信息
     */
    @DataSource(value = DataSourceType.APPLET)
    IndexImg getInfoById(@Param("id") String id);

}
