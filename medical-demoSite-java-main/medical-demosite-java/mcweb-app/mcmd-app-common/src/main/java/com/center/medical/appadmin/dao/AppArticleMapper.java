package com.center.medical.appadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.appadmin.bean.model.AppArticle;
import com.center.medical.appadmin.bean.param.AppArticlePageParam;
import com.center.medical.appadmin.bean.param.GetArticleListParam;
import com.center.medical.appadmin.bean.vo.AppArticlePageVo;
import com.center.medical.appadmin.bean.vo.GetArticleListVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 小程序文章表(AppArticle)数据库访问层
 *
 * @author ay
 * @since 2024-06-15 09:08:15
 */
public interface AppArticleMapper extends BaseMapper<AppArticle> {

    /**
     * 分页查询[小程序文章表]列表
     *
     * @param page  分页参数
     * @param param AppArticle查询参数
     * @return 分页数据
     */
    IPage<AppArticlePageVo> getPage(PageParam<AppArticlePageVo> page, @Param("param") AppArticlePageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppArticle getInfoById(@Param("id") String id);

    /**
     * 小程序获取文章列表
     * @param page
     * @param param
     * @return
     */
    IPage<GetArticleListVo> getArticleList(PageParam<GetArticleListVo> page, @Param("param") GetArticleListParam param);
}
