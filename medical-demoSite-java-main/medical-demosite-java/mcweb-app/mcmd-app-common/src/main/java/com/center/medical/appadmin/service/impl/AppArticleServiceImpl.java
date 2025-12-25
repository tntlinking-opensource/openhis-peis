package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.model.AppArticle;
import com.center.medical.appadmin.bean.param.AppArticlePageParam;
import com.center.medical.appadmin.bean.param.AppArticleParam;
import com.center.medical.appadmin.bean.param.GetArticleListParam;
import com.center.medical.appadmin.bean.param.GoLiveParam;
import com.center.medical.appadmin.bean.vo.AppArticlePageVo;
import com.center.medical.appadmin.bean.vo.GetArticleListVo;
import com.center.medical.appadmin.dao.AppArticleMapper;
import com.center.medical.appadmin.service.AppArticleService;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 小程序文章表(AppArticle)服务实现类
 *
 * @author ay
 * @since 2024-06-15 09:08:16
 */
@Slf4j
@Service("appArticleService")
@RequiredArgsConstructor
public class AppArticleServiceImpl extends ServiceImpl<AppArticleMapper, AppArticle> implements AppArticleService {

    private final AppArticleMapper appArticleMapper;
    private final MapperFacade mapperFacade;

    /**
     * 分页查询[小程序文章表]列表
     *
     * @param page  分页参数
     * @param param AppArticle查询参数
     * @return 分页数据
     */
    @Override
    public IPage<AppArticlePageVo> getPage(PageParam<AppArticlePageVo> page, AppArticlePageParam param) {
        return appArticleMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public AppArticle getInfoById(String id) {
        return appArticleMapper.getInfoById(id);
    }

    /**
     * 添加或修改
     * @param param
     * @return
     */
    @Override
    public Boolean saOrUp(AppArticleParam param) {
        AppArticle appArticle = mapperFacade.map(param, AppArticle.class);
        if (ObjectUtils.isNotEmpty(appArticle.getId())){
            //更新
            AppArticle appArticle1 = appArticleMapper.getInfoById(appArticle.getId());
            if (ObjectUtils.isEmpty(appArticle1)){
                throw new ServiceException("该id不存在!");
            }
            appArticle.setModifydate(new Date());
            appArticleMapper.updateById(appArticle);
        }else {
            //添加
            appArticle.setCreatedate(new Date());
            appArticleMapper.insert(appArticle);
        }
        return Boolean.TRUE;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteIds(List<String> ids) {
        //更新为已删除状态
        AppArticle appArticle = new AppArticle();
        appArticle.setIsDelete(1);
        appArticle.setModifydate(new Date());
        appArticleMapper.update(appArticle,new LambdaQueryWrapper<AppArticle>()
                .in(AppArticle::getId,ids));

        return Boolean.TRUE;
    }


    /**
     * 上线或下线
     * @param param
     * @return
     */
    @Override
    public Boolean goLive(GoLiveParam param) {
        AppArticle appArticle = new AppArticle();
        appArticle.setStatus(param.getStatus());
        appArticle.setModifydate(new Date());
        appArticleMapper.update(appArticle,new LambdaQueryWrapper<AppArticle>()
                .in(AppArticle::getId,param.getIds()));
        return Boolean.TRUE;
    }

    /**
     * 小程序获取文章列表
     * @param page
     * @param param
     * @return
     */
    @Override
    public IPage<GetArticleListVo> getArticleList(PageParam<GetArticleListVo> page, GetArticleListParam param) {
        return appArticleMapper.getArticleList(page,param);
    }
}

