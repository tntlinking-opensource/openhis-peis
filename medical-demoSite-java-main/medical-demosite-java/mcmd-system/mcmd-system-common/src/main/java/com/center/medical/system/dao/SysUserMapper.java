package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.domain.dto.InvalidImagesDto;
import com.center.medical.common.core.domain.entity.SysUser;
import com.center.medical.common.core.domain.vo.XsryByCodeVo;
import com.center.medical.common.core.domain.vo.XsryDataVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.SysUserParam;
import com.center.medical.system.bean.param.XsryDataParam;
import com.center.medical.system.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author 路飞
 */
public interface SysUserMapper {
    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 根据条件分页查询已配用户角色列表
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
     * 通过用户编号查询用户
     *
     * @param userNo 用户编号
     * @return 用户对象信息
     */
    public SysUser selectUserByUserNo(String userNo);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    public int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

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
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    public SysUser checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);


    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserByIdAndDel(Long userId);

    /**
     * 获取当前登录人分中心下的销售部下的非领导的用户
     *
     * @param fzxId
     * @return
     */
    List<XsryDataVo> getXsryDataUser(@Param("fzxId") String fzxId);

    /**
     * 查询条件获取人员名称数据
     *
     * @param param 查询条件
     * @return
     */
    List<SysUser> getAllData(@Param("param") SysUserParam param);

    /**
     * 查询条件获取销售人员名称数据,用于显示
     *
     * @param page
     * @param fzxId
     * @param userName
     * @return
     */
    IPage<SysUser> getXsryData(PageParam<SysUser> page, @Param("fzxId") String fzxId, @Param("userName") String userName);

    /**
     * 获取审核人姓名集合
     *
     * @return
     */
    List<GetNameDataVo> getNameData();

    /**
     * 分页获取审核人姓名和部门下拉
     *
     * @param page
     * @param key
     * @return
     */
    IPage<GetNameDataVo> getDepData(PageParam<GetNameDataVo> page, @Param("key") String key);

    /**
     * 承送人名处搜索
     *
     * @param cid
     * @param key
     * @return
     */
    IPage<LqrDataVo> getLqrData(PageParam<LqrDataVo> page, @Param("cid") String cid, @Param("srm") String key);

    /**
     * 所有用户下拉
     *
     * @param key
     * @return
     */
    List<AllUserDataVo> getAllUserData(@Param("key") String key);

    /**
     * 获取医师，通过分中心ID和输入码
     *
     * @param cId
     * @param key
     * @return
     */
    List<GetDoctorVo> getDoctor(@Param("cId") String cId, @Param("key") String key);

    /**
     * 根据cid和key查询用户
     *
     * @param cId
     * @param key
     * @return
     */
    List<AllUserDataVo> getAllUser(@Param("cId") String cId, @Param("key") String key);

    /**
     * 查询20个启用的用户
     *
     * @param key
     * @return
     */
    List<AllUserDataVo> selectEnabledUser(@Param("key") String key);

    /**
     * 当前分中心当前科室id的所有医生
     *
     * @param cId
     * @param ksID
     * @param key
     * @return
     */
    List<AllUserDataVo> allDoctors(@Param("cId") String cId, @Param("ksID") String ksID, @Param("key") String key);

    /**
     * 获取医生数据
     *
     * @param page
     * @param userNo
     * @param username
     * @param harmClass
     * @return
     */
    IPage<DoctorDataVo> getDoctorData(PageParam<DoctorDataVo> page, @Param("userNo") String userNo, @Param("username") String username, @Param("harmClass") List<String> harmClass);

    /**
     * 获取开单助理
     *
     * @param page
     * @param key
     * @return
     */
    IPage<KdzlVo> getKdzl(PageParam<KdzlVo> page, @Param("key") String key);

    /**
     * 客户转移
     *
     * @param param
     * @return
     */
    IPage<XsryVo> getXsry(PageParam<XsryVo> page, @Param("param") XsryDataParam param);

    /**
     * 通过用户编号查询用户表（单表）
     *
     * @param userNo
     * @return
     */
    SysUser getUserByNo(@Param("userNo") String userNo);

    /**
     * 获取所有的用户
     *
     * @param key
     * @return
     */
    List<AllUserDataVo> getAllUserSql2(@Param("key") String key);

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
    IPage<CreatorDataVo> getCreatorData(PageParam<CreatorDataVo> page, @Param("key") String key);

    /**
     * 通过用户名查询对象（单表）
     *
     * @param userName
     * @return
     */
    SysUser getUserByUserName(@Param("userName") String userName);

    /**
     * 获取备单审批领导电话
     *
     * @param page
     * @param key
     * @return
     */
    IPage<GetLeaderTelVo> getLeaderTel(PageParam<GetLeaderTelVo> page, @Param("key") String key);

    /**
     * 根据名称或输入码获取登录用户名
     *
     * @param key
     * @return
     */
    List<LogUsersVo> getLogUsers(@Param("key") String key);

    /**
     * 彩超登录获取所有有权限的医生
     *
     * @return
     */
    List<PacsCcUserVo> getCcUsers(@Param("ksID") String ksID, @Param("cId") String cId);

    /**
     * 通过输入码获取销售人员
     *
     * @param page
     * @param cIds
     * @param code
     * @return
     */
    IPage<XsryByCodeVo> getXsryByCode(PageParam<XsryByCodeVo> page, @Param("cIds") List<String> cIds, @Param("code") String code);

    /**
     * 根据分中心id查询金蝶账号不为空的
     *
     * @param cid
     * @return
     */
    List<SysUser> getAccountNoByCid(@Param("cId") String cid);

    /**
     * 获取失效图片
     * @return
     */
    List<InvalidImagesDto> getInvalidImages();

    /**
     * 获取所有下级的id
     * @param userNo
     * @return
     */
    List<String> getLowerLevelId(@Param("userNo") String userNo);
}
