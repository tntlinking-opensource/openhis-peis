package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.UserSaleer;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 用户-销售关联表 (UserSaleer)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:16
 */
public interface UserSaleerMapper extends BaseMapper<UserSaleer> {

    /**
     * 分页查询[用户-销售关联表 ]列表
     *
     * @param page  分页参数
     * @param param UserSaleer查询参数
     * @return 分页数据
     */
    IPage<UserSaleer> getList(PageParam<UserSaleer> page, @Param("param") UserSaleer param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    UserSaleer getInfoById(@Param("id") String id);

    /**
     * 根据用户id获取
     * @param userNo
     * @return
     */
    UserSaleer getInfoByUserId(@Param("userNo") String userNo);
}
