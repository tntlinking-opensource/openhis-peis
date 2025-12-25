package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.app.bean.model.UserIdcard;
import com.center.medical.app.common.util.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 用户和身份证关联表(UserIdcard)数据库访问层
 *
 * @author ay
 * @since 2023-08-23 15:12:05
 */
public interface UserIdcardMapper extends BaseMapper<UserIdcard> {

    /**
     * 分页查询[用户和身份证关联表]列表
     *
     * @param page  分页参数
     * @param param UserIdcard查询参数
     * @return 分页数据
     */
    IPage<UserIdcard> getPage(PageParam<UserIdcard> page, @Param("param") String param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    UserIdcard getInfoById(@Param("id") Long id);

}
