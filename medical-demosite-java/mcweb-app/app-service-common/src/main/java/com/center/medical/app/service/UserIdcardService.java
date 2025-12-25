package com.center.medical.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.app.bean.model.UserIdcard;
import com.center.medical.app.common.util.PageParam;

import java.util.List;

/**
 * 用户和身份证关联表(UserIdcard)服务接口
 *
 * @author ay
 * @since 2023-08-23 15:12:06
 */
public interface UserIdcardService extends IService<UserIdcard> {

    /**
     * 分页查询[用户和身份证关联表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<UserIdcard> getPage(PageParam<UserIdcard> page, String param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    UserIdcard getInfoById(Long id);

    /**
     * 添加或修改
     * @param idCards
     * @return
     */
    Boolean saOrUp(List<String> idCards,String userId);
}

