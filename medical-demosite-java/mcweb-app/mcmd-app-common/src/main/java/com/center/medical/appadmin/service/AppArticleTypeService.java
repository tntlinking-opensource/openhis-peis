package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.AppArticleType;
import com.center.medical.appadmin.bean.param.AppArticleTypeParam;
import com.center.medical.appadmin.bean.param.CMAppTypeParam;
import com.center.medical.appadmin.bean.vo.GetTypeListVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 小程序文章类型(AppArticleType)服务接口
 *
 * @author ay
 * @since 2024-06-15 09:08:15
 */
public interface AppArticleTypeService extends IService<AppArticleType> {

    /**
     * 分页查询[小程序文章类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<AppArticleType> getPage(PageParam<AppArticleType> page, CMAppTypeParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    AppArticleType getInfoById(String id);

    /**
     * 添加或修改
     * @param param
     * @return
     */
    Boolean saOrUp(AppArticleTypeParam param);

    /**
     * 删除分类
     * @param ids
     * @return
     */
    Boolean deleteIds(List<String> ids);

    /**
     * 获取类型
     * @param name
     * @return
     */
    List<GetTypeListVo> getTypeList(String name);
}

