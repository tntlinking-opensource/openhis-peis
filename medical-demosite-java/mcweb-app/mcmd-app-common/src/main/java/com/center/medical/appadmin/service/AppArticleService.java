package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.AppArticle;
import com.center.medical.appadmin.bean.param.AppArticlePageParam;
import com.center.medical.appadmin.bean.param.AppArticleParam;
import com.center.medical.appadmin.bean.param.GetArticleListParam;
import com.center.medical.appadmin.bean.param.GoLiveParam;
import com.center.medical.appadmin.bean.vo.AppArticlePageVo;
import com.center.medical.appadmin.bean.vo.GetArticleListVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 小程序文章表(AppArticle)服务接口
 *
 * @author ay
 * @since 2024-06-15 09:08:15
 */
public interface AppArticleService extends IService<AppArticle> {

    /**
     * 分页查询[小程序文章表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppArticlePageVo> getPage(PageParam<AppArticlePageVo> page, AppArticlePageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppArticle getInfoById(String id);

    /**
     * 添加或修改
     * @param param
     * @return
     */
    Boolean saOrUp(AppArticleParam param);

    /**
     * 删除
     * @param ids
     * @return
     */
    Boolean deleteIds(List<String> ids);

    /**
     * 上线或下线
     * @param param
     * @return
     */
    Boolean goLive(GoLiveParam param);

    /**
     * 小程序获取文章列表
     * @param page
     * @param param
     * @return
     */
    IPage<GetArticleListVo> getArticleList(PageParam<GetArticleListVo> page, GetArticleListParam param);
}

