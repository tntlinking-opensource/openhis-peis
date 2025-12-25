package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.domain.TreeSelect;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.bean.vo.AllDepartDataVo;
import com.center.medical.system.bean.vo.AllKsVo;
import com.center.medical.system.bean.vo.DeptSimpleVo;

import java.util.List;

/**
 * 部门管理 服务层
 *
 * @author 路飞
 */
public interface ISysDeptService {
    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<SysDept> selectDeptList(SysDept dept);

    /**
     * 查询部门树结构信息
     *
     * @param dept 部门信息
     * @return 部门树信息集合
     */
    List<TreeSelect> selectDeptTreeList(SysDept dept);

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    List<SysDept> buildDeptTree(List<SysDept> depts);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    List<Long> selectDeptListByRoleId(Long roleId);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    SysDept selectDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    boolean hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkDeptExistUser(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    String checkDeptNameUnique(SysDept dept);

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    void checkDeptDataScope(Long deptId);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int insertDept(SysDept dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int updateDept(SysDept dept);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int deleteDeptById(Long deptId);

    /**
     * 根据部门编号查询信息
     *
     * @param deptNo 部门编号
     * @return 部门信息
     */
    SysDept getByDeptNo(String deptNo);

    /**
     * 根据科室名称更新科室编号
     *
     * @param list
     * @return
     */
    int updateDeptList(List<SysDept> list);

    /**
     * 获取所有的科室
     *
     * @param param 筛选条件
     * @return
     */
    List<DeptSimpleVo> getAllDepartment(SysDeptParam param);

    /**
     * 分頁獲取获取所有的科室
     *
     * @param page
     * @param param
     * @return
     */
    IPage<DeptSimpleVo> findAllDepartment(PageParam<DeptSimpleVo> page, SysDeptParam param);

    /**
     * 获取所有科室的名称图片英文名
     *
     * @return
     */
    List<SysDept> seletAllDept();

    /**
     * 根据输入码获取科室
     *
     * @param srm
     * @return
     */
    List<DeptSimpleVo> getKs(String srm);

    /**
     * 根据输入码和用户性名获取科室
     *
     * @param srm
     * @return
     */
    List<DeptSimpleVo> getKsBySrm(String srm);

    /**
     * 更具名称获取部门
     *
     * @param deptName
     * @return
     */
    SysDept selectDeptByName(String deptName);

    /**
     * 获取所有科室
     *
     * @param key
     * @return
     */
    List<AllKsVo> getAllks(String key);

    /**
     * 获取所有部门
     *
     * @param key
     * @return
     */
    List<AllDepartDataVo> getAllDepartData(String key);

    /**
     * 获取所有部门
     *
     * @return
     */
    List<SysDept> selectAllDeptList();

    /**
     * 获取科室的输入码
     * @param depName
     * @return
     */
    String getDepSrm(String depName);
}
