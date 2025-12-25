package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.model.UserHarmClass;
import org.apache.ibatis.annotations.Param;

/**
 * 总检医生-危害因素分类关联表 (UserHarmClass)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:43
 */
public interface UserHarmClassMapper extends BaseMapper<UserHarmClass> {

    /**
     * 分页查询[总检医生-危害因素分类关联表 ]列表
     *
     * @param page  分页参数
     * @param param UserHarmClass查询参数
     * @return 分页数据
     */
    IPage<UserHarmClass> getList(PageParam<UserHarmClass> page, @Param("param") UserHarmClass param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    UserHarmClass getInfoById(@Param("id") String id);

}
