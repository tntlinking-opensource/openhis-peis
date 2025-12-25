package com.center.medical.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.UserSaleer;
import com.center.medical.common.annotation.DataScope;
import com.center.medical.common.constant.RoleAuthName;
import com.center.medical.common.constant.UserConstants;
import com.center.medical.common.core.domain.entity.*;
import com.center.medical.common.core.domain.vo.XsryByCodeVo;
import com.center.medical.common.core.domain.vo.XsryDataVo;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.MD5;
import com.center.medical.common.utils.SecurityUtils;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.ToolUtil;
import com.center.medical.common.utils.bean.BeanValidators;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.common.utils.spring.SpringUtils;
import com.center.medical.system.bean.model.KdSaleer;
import com.center.medical.system.bean.model.SysUserBranch;
import com.center.medical.system.bean.model.SysUserDep;
import com.center.medical.system.bean.model.UserHarmClass;
import com.center.medical.system.bean.param.SysUserParam;
import com.center.medical.system.bean.vo.*;
import com.center.medical.system.dao.*;
import com.center.medical.system.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author 路飞
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    private final SysUserMapper userMapper;
    private final SysRoleMapper roleMapper;
    private final SysPostMapper postMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysUserPostMapper userPostMapper;
    private final ISysConfigService configService;
    private final Validator validator;
    private final Snowflake snowflake;
    private final SysDeptMapper sysDeptMapper;
    private final UserHarmClassService userHarmClassService;
    private final SysUserBranchService sysUserBranchService;
    private final SysUserDepService sysUserDepService;
    private final UserSaleerService userSaleerService;
    private final KdSaleerService kdSaleerService;


    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        //只有超级管理员跟分中心管理员能进这个页面
        //中心管理员只能看自己默认分中心的
        Boolean admin = SecurityUtils.hasRole(RoleAuthName.ADMIN);
        if (!admin) {
            //查询兼职分中心和默认分中心的数据
            List<SysUserBranch> list = sysUserBranchService.list(new LambdaQueryWrapper<SysUserBranch>().eq(SysUserBranch::getUserId, SecurityUtils.getUserNo()));
            if (CollectionUtil.isNotEmpty(list)) {
                String cids = list.stream().map(SysUserBranch::getBranchId).collect(Collectors.joining(","));
                user.setUseBranches(cids.split(","));
            } else {
                user.setUseBranches(new String[]{SecurityUtils.getCId()});
            }

        }
        return userMapper.selectUserList(user);
    }


    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = " u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户编号查询用户
     *
     * @param userNo 用户编号
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserNo(String userNo) {
        return userMapper.selectUserByUserNo(userNo);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkUserNameUnique(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        String userNo = ObjectUtils.isNotEmpty(user.getUserNo()) ? user.getUserNo() : snowflake.nextIdStr();
        SysUser sysUser = userMapper.selectUserByUserNo(userNo);
        if (ObjectUtils.isNotEmpty(sysUser)) {
            throw new ServiceException("userNo重复!");
        }
        user.setUserNo(userNo);
        //涉密密码
        if (StringUtils.isNotBlank(user.getReciveCode())) {
            user.setReciveCode(MD5.encode(user.getReciveCode()));
        }
        //输入码
        user.setInputCode(ToolUtil.getHanziPinyinHeadChar(user.getUserName()));
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户与角色管理
        insertUserRole(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与科室关联
        insertUserKs(user);
        // 新增用户职业危害因素
        insertUserHarmClass(user);
        // 新增用户兼职分中心关联
        insertUserBranch(user);
        // 新增用户与金蝶关联
        insertUserSaleer(user);
        return rows;
    }

    /**
     * 新增用户与金蝶关联
     *
     * @param user
     */
    private void insertUserSaleer(SysUser user) {
        if (StringUtils.isNotEmpty(user.getKingdeeAccountNo())) {
            //用userNo查询，因为老系统导过来的数据就是用的userNo
            UserSaleer userSaleer = userSaleerService.getInfoByUserId(user.getUserNo());
            //通过金蝶账户id查询金蝶用户
            KdSaleer kdSaleer = kdSaleerService.getByAccountNo(user.getKingdeeAccountNo());
            if (ObjectUtils.isEmpty(kdSaleer)){
                throw new ServiceException("金蝶销售员不存在！");
            }
            if (userSaleer == null) {
                userSaleer = new UserSaleer();
                userSaleer.setUserId(user.getUserNo());
                userSaleer.setSaleerMd5(kdSaleer.getMd5());
                userSaleerService.save(userSaleer);
            } else {
                userSaleer.setSaleerMd5(kdSaleer.getMd5());
                userSaleerService.updateById(userSaleer);
            }
        }
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);

        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);

        // 删除用户与科室关联
        sysUserDepService.remove(new LambdaQueryWrapper<SysUserDep>().eq(SysUserDep::getUserId, user.getUserNo()));
        // 新增用户与科室关联
        insertUserKs(user);

        // 删除用户职业危害因素关联
        userHarmClassService.remove(new LambdaQueryWrapper<UserHarmClass>().eq(UserHarmClass::getUserId, user.getUserNo()));
        // 新增用户职业危害因素
        insertUserHarmClass(user);

        // 删除用户兼职分中心关联
        sysUserBranchService.remove(new LambdaQueryWrapper<SysUserBranch>().eq(SysUserBranch::getUserId, user.getUserNo()));
        // 新增用户兼职分中心关联
        insertUserBranch(user);

        //新增用户与金蝶关联
        insertUserSaleer(user);

        return userMapper.updateUser(user);
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        this.insertUserRole(user.getUserId(), user.getRoleIds());
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotEmpty(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>(posts.length);
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            userPostMapper.batchUserPost(list);
        }
    }

    /**
     * 新增用户与科室关联
     *
     * @param user 用户对象
     */
    public void insertUserKs(SysUser user) {
        String[] ksIds = user.getKsIds();
        if (StringUtils.isNotEmpty(ksIds)) {
            // 新增用户与科室关联
            List<SysUserDep> list = new ArrayList<>(ksIds.length);
            for (String id : ksIds) {
                SysUserDep item = new SysUserDep();
                item.setUserId(user.getUserNo());
                item.setDepId(id);
                list.add(item);
            }
            sysUserDepService.saveBatch(list);
        }
    }

    /**
     * 新增用户职业危害因素
     *
     * @param user 用户
     */
    public void insertUserHarmClass(SysUser user) {
        if (StringUtils.isNotEmpty(user.getHarmClass())) {
            // 新增用户职业危害因素
            List<UserHarmClass> list = new ArrayList<>(user.getHarmClass().length);
            for (String harmClass : user.getHarmClass()) {
                UserHarmClass userHarmClass = new UserHarmClass();
                userHarmClass.setUserId(user.getUserNo());
                userHarmClass.setClassId(harmClass);
                list.add(userHarmClass);
            }
            userHarmClassService.saveBatch(list);
        }
    }

    /**
     * 新增用户兼职分中心关联
     *
     * @param user 用户
     */
    public void insertUserBranch(SysUser user) {
        if (StringUtils.isNotEmpty(user.getUseBranches())) {
            // 新增用户兼职分中心关联
            List<SysUserBranch> list = new ArrayList<>(user.getUseBranches().length);
            for (String item : user.getUseBranches()) {
                SysUserBranch sysUserBranch = new SysUserBranch();
                sysUserBranch.setUserId(user.getUserNo());
                sysUserBranch.setBranchId(item);
                list.add(sysUserBranch);
            }
            sysUserBranchService.saveBatch(list);
        }
    }


    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotEmpty(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    if (StringUtils.isBlank(user.getCid())) {
                        user.setCid(SecurityUtils.getCId());
                    }
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, user);
                    checkUserAllowed(user);
                    checkUserDataScope(user.getUserId());
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 查询条件获取人员名称数据
     *
     * @param param 查询条件
     * @return
     */
    @Override
    public List<SysUser> getAllData(SysUserParam param) {
        return userMapper.getAllData(param);

    }

    /**
     * 获取当前登录人分中心下的销售部下的非领导的用户
     *
     * @param fzxId
     * @return
     */
    @Override
    public List<XsryDataVo> getXsryDataUser(String fzxId) {
        return userMapper.getXsryDataUser(fzxId);
    }


    /**
     * 查询条件获取销售人员名称数据,用于显示
     *
     * @param page
     * @param fzxId
     * @param userName
     * @return
     */
    @Override
    public IPage<SysUser> getXsryData(PageParam<SysUser> page, String fzxId, String userName) {
        //CID为空就用，当前登录用户所在分中心
        if (StringUtils.isEmpty(fzxId)) {
            fzxId = SecurityUtils.getCId();
        }
        IPage<SysUser> sysUserIPage = userMapper.getXsryData(page, fzxId, userName);
        return sysUserIPage;
    }


    /**
     * 获取审核人姓名集合
     *
     * @return
     */
    @Override
    public List<GetNameDataVo> getNameData() {
        return userMapper.getNameData();
    }


    /**
     * 获取审核人姓名和部门下拉
     *
     * @param key
     * @return
     */
    @Override
    public IPage<GetNameDataVo> getDepData(PageParam<GetNameDataVo> page, String key) {
        IPage<GetNameDataVo> nameData = userMapper.getDepData(page, key);
        List<GetNameDataVo> records = nameData.getRecords();
        for (GetNameDataVo record : records) {
            record.setDepId(getUserDepNames(record.getId()));
        }
        nameData.setRecords(records);
        return nameData;
    }


    /**
     * 获得用户部门名称
     */
    public String getUserDepNames(String userNo) {
        String result = "";
        StringBuilder names = new StringBuilder();
        if (ObjectUtils.isNotEmpty(userNo)) {
            SysUser uds = userMapper.selectUserByUserNo(userNo);
            SysDept dep = sysDeptMapper.selectDeptById(uds.getDeptId());
            if (ObjectUtils.isNotEmpty(dep)) {
                names.append(dep.getDeptName() + ",");
            }
        }
        if (names.length() > 0) {
            result = names.substring(0, names.length() - 1);
        }

        return result;
    }


    /**
     * 承送人名处搜索
     *
     * @param page
     * @param srm
     * @return
     */
    @Override
    public IPage<LqrDataVo> getLqrData(PageParam<LqrDataVo> page, String srm) {
        //分中心id
        String cid = SecurityUtils.getCId();
        //超级管理员可以选所有分中心的
        if (SecurityUtils.hasRole(RoleAuthName.ADMIN)) {
            cid = null;
        }
        //去空格大写
        if (ObjectUtils.isNotEmpty(srm)) {
            srm = srm.trim().toUpperCase();
        }
        return userMapper.getLqrData(page, cid, srm);
    }


    /**
     * 所有用户下拉
     *
     * @param key
     * @return
     */
    @Override
    public List<AllUserDataVo> getAllUserData(String key) {
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        return userMapper.getAllUserData(key);
    }


    /**
     * 获取医师，通过分中心ID和输入码
     *
     * @param cId
     * @param key
     * @return
     */
    @Override
    public List<GetDoctorVo> getDoctor(String cId, String key) {
        return userMapper.getDoctor(cId, key);
    }


    /**
     * 根据cid和key查询用户
     *
     * @param cId
     * @param key
     * @return
     */
    @Override
    public List<AllUserDataVo> getAllUser(String cId, String key) {
        return userMapper.getAllUser(cId, key);
    }

    /**
     * 获取医生数据
     *
     * @param page
     * @param userNo
     * @param username
     * @param harmClass
     * @return
     */
    @Override
    public IPage<DoctorDataVo> getDoctorData(PageParam<DoctorDataVo> page, String userNo, String username, List<String> harmClass) {
        return userMapper.getDoctorData(page, userNo, username, harmClass);
    }

    /**
     * 通过用户编号查询用户表（单表）
     *
     * @param userNo
     * @return
     */
    @Override
    public SysUser getUserByNo(String userNo) {
        return userMapper.getUserByNo(userNo);
    }

    /**
     * 获取所有的用户
     *
     * @param key
     * @return
     */
    @Override
    public List<AllUserDataVo> getAllUserSql2(String key) {
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        return userMapper.getAllUserSql2(key);
    }

    /**
     * 获取补检账号
     *
     * @return
     */
    @Override
    public SysUser getBjzh() {
        return userMapper.getBjzh();
    }

    /**
     * 获取创建人
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<CreatorDataVo> getCreatorData(PageParam<CreatorDataVo> page, String key) {
        return userMapper.getCreatorData(page, key);
    }


    /**
     * 获取备单审批领导电话
     *
     * @param page
     * @param key
     * @return
     */
    @Override
    public IPage<GetLeaderTelVo> getLeaderTel(PageParam<GetLeaderTelVo> page, String key) {
        //去空格
        if (ObjectUtils.isNotEmpty(key)) {
            key = key.trim();
        }
        return userMapper.getLeaderTel(page, key);
    }


    /**
     * 根据名称或输入码获取登录用户名
     *
     * @param key
     * @return
     */
    @Override
    public List<LogUsersVo> getLogUsers(String key) {
        return userMapper.getLogUsers(key);
    }

    /**
     * 彩超登录获取所有有权限的医生
     *
     * @return
     */
    @Override
    public List<PacsCcUserVo> getCcUsers(String cId, String ksID) {
        return userMapper.getCcUsers(ksID, cId);
    }

    /**
     * 通过输入码获取销售人员
     *
     * @param page
     * @param cIds
     * @param code
     * @return
     */
    @Override
    public IPage<XsryByCodeVo> getXsryByCode(PageParam<XsryByCodeVo> page, List<String> cIds, String code) {
        return userMapper.getXsryByCode(page, cIds, code);
    }


    /**
     * 通过用户名称查询
     *
     * @param name
     * @return
     */
    @Override
    public SysUser getUserByUserName(String name) {
        return userMapper.getUserByUserName(name);
    }

    /**
     * 批量更新
     *
     * @param sysUserList
     */
    @Override
    public void updateBatchById(List<SysUser> sysUserList) {
        for (SysUser sysUser : sysUserList) {
            userMapper.updateUser(sysUser);
        }
    }
}
