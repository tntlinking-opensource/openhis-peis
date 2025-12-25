package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.core.domain.vo.XsryByCodeVo;
import com.center.medical.common.core.domain.vo.XsryDataVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.SysUserParam;
import com.center.medical.system.bean.vo.*;

import java.util.List;

/**
 * 用户 业务层
 *
 * @author 路飞
 */
public interface ISysUserService {
    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser user);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 通过用户编号查询用户
     *
     * @param userNo 用户编号
     * @return 用户对象信息
     */
    public SysUser selectUserByUserNo(String userNo);

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    public String selectUserRoleGroup(String userName);

    /**
     * 根据用户ID查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    public String selectUserPostGroup(String userName);

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkUserNameUnique(SysUser user);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkEmailUnique(SysUser user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    public void checkUserAllowed(SysUser user);

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    public void checkUserDataScope(Long userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public boolean registerUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserAuth(Long userId, Long[] roleIds);

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserStatus(SysUser user);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUserProfile(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    public boolean updateUserAvatar(String userName, String avatar);

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    public int resetPwd(SysUser user);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(String userName, String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] userIds);

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    /**
     * 查询条件获取人员名称数据
     *
     * @param param 查询条件
     * @return
     */
    List<SysUser> getAllData(SysUserParam param);

    /**
     * 获取当前登录人分中心下的销售部下的非领导的用户
     *
     * @param fzxId
     * @return
     */
    List<XsryDataVo> getXsryDataUser(String fzxId);

    /**
     * 查询条件获取销售人员名称数据,用于显示
     *
     * @param page
     * @param fzxId
     * @param userName
     * @return
     */
    IPage<SysUser> getXsryData(PageParam<SysUser> page, String fzxId, String userName);

    /**
     * 获取审核人姓名集合
     *
     * @return
     */
    List<GetNameDataVo> getNameData();

    /**
     * 获取审核人姓名和部门下拉
     *
     * @param key
     * @return
     */
    IPage<GetNameDataVo> getDepData(PageParam<GetNameDataVo> page, String key);

    /**
     * 承送人名处搜索
     *
     * @param page
     * @param srm
     * @return
     */
    IPage<LqrDataVo> getLqrData(PageParam<LqrDataVo> page, String srm);


    /**
     * 所有用户下拉
     *
     * @param key
     * @return
     */
    List<AllUserDataVo> getAllUserData(String key);

    /**
     * 获取医师，通过分中心ID和输入码
     *
     * @param cId
     * @param key
     * @return
     */
    List<GetDoctorVo> getDoctor(String cId, String key);


    /**
     * 根据cid和key查询用户
     *
     * @param key
     * @return
     */
    List<AllUserDataVo> getAllUser(String cId, String key);

    /**
     * 获取医生数据
     *
     * @param userNo
     * @param username
     * @param harmClass
     * @return
     */
    IPage<DoctorDataVo> getDoctorData(PageParam<DoctorDataVo> page, String userNo, String username, List<String> harmClass);

    /**
     * 通过用户编号查询用户表（单表）
     *
     * @param userNo
     * @return
     */
    SysUser getUserByNo(String userNo);

    /**
     * 获取所有的用户
     *
     * @param key
     * @return
     */
    List<AllUserDataVo> getAllUserSql2(String key);

    /**
     * 获取补检账号
     *
     * @return
     */
    SysUser getBjzh();

    /**
     * 获取创建人
     *
     * @param page
     * @param key
     * @return
     */
    IPage<CreatorDataVo> getCreatorData(PageParam<CreatorDataVo> page, String key);

    /**
     * 获取备单审批领导电话
     *
     * @param page
     * @param key
     * @return
     */
    IPage<GetLeaderTelVo> getLeaderTel(PageParam<GetLeaderTelVo> page, String key);

    /**
     * 根据名称或输入码获取登录用户名
     *
     * @param key
     * @return
     */
    List<LogUsersVo> getLogUsers(String key);

    /**
     * 彩超登录获取所有有权限的医生
     *
     * @return
     */
    List<PacsCcUserVo> getCcUsers(String cId, String ksID);

    /**
     * 通过输入码获取销售人员
     *
     * @param page
     * @param cIds
     * @param code
     * @return
     */
    IPage<XsryByCodeVo> getXsryByCode(PageParam<XsryByCodeVo> page, List<String> cIds, String code);

    /**
     * 通过用户名称查询
     *
     * @param name
     * @return
     */
    SysUser getUserByUserName(String name);

    /**
     *
     * @param sysUserList
     */
    void updateBatchById(List<SysUser> sysUserList);
}
