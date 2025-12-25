package com.center.medical.appadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.AppArticleType;
import com.center.medical.appadmin.bean.param.CMAppTypeParam;
import com.center.medical.appadmin.bean.vo.GetTypeListVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小程序文章类型(AppArticleType)数据库访问层
 *
 * @author ay
 * @since 2024-06-15 09:08:15
 */
public interface AppArticleTypeMapper extends BaseMapper<AppArticleType> {

    /**
     * 分页查询[小程序文章类型]列表
     *
     * @param page  分页参数
     * @param param AppArticleType查询参数
     * @return 分页数据
     */
    IPage<AppArticleType> getPage(PageParam<AppArticleType> page, @Param("param") CMAppTypeParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppArticleType getInfoById(@Param("id") String id);

    /**
     * 获取类型
     * @param name
     * @return
     */
    List<GetTypeListVo> getTypeList(@Param("name") String name);
}
