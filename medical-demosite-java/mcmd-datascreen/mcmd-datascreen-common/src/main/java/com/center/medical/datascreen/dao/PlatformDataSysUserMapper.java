package com.center.medical.datascreen.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author xhp
 * @since 2023-06-05 8:48
 */
@Repository
public interface PlatformDataSysUserMapper extends BaseMapper<SysUser> {
    /**
     * 按角色权限查询用户人数
     * @param roleKey
     * @return
     */
    int selectUserCountByRoleKey(@Param("roleKey") String roleKey);
}
