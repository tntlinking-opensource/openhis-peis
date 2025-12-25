package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.param.SysDeptParam;
import com.center.medical.system.bean.vo.AllDepartDataVo;
import com.center.medical.system.bean.vo.AllKsVo;
import com.center.medical.system.bean.vo.DeptSimpleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门管理 数据层
 *
 * @author 路飞
 */
public interface SysDeptMapper {
    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<SysDept> selectDeptList(SysDept dept);

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId            角色ID
     * @param deptCheckStrictly 部门树选择项是否关联显示
     * @return 选中部门列表
     */
    List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") boolean deptCheckStrictly);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    SysDept selectDeptById(Long deptId);

    /**
     * 根据部门编号查询信息
     *
     * @param deptNo 部门编号
     * @return 部门信息
     */
    SysDept getByDeptNo(@Param("deptNo") String deptNo);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    int selectNormalChildrenDeptById(Long deptId);

    /**
     * 是否存在子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int hasChildByDeptId(Long deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int checkDeptExistUser(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int insertDept(SysDept dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int updateDept(SysDept dept);

    /**
     * 修改所在部门正常状态
     *
     * @param deptIds 部门ID组
     */
    void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int deleteDeptById(Long deptId);

    void updateByName(SysDept dept);

    /**
     * 获取所有的科室
     *
     * @param param 筛选条件
     * @return
     */
    List<DeptSimpleVo> getAllDepartment(@Param("param") SysDeptParam param);

    /**
     * 分頁获取所有的科室
     *
     * @param page
     * @param param
     * @return
     */
    IPage<DeptSimpleVo> findAllDepartment(PageParam<DeptSimpleVo> page, @Param("param") SysDeptParam param);

    /**
     * 通过name获取部门
     *
     * @param name
     * @return
     */
    SysDept selectDeptByName(@Param("name") String name);

    /**
     * 查询功能科室或者非功能科室的id
     *
     * @param isFunction
     * @return
     */
    List<String> getFunctionKsIds(@Param("isFunction") int isFunction);

    /**
     * 获取所有科室的名称图片英文名
     *
     * @return
     */
    List<SysDept> seletAllDept();

    /**
     * 根据输入码获取所有的科室
     *
     * @param srm
     * @return
     */
    List<DeptSimpleVo> getKs(@Param("inputCode") String srm);


    /**
     * 根据输入码和用户性名获取科室
     *
     * @param srm
     * @return
     */
    List<DeptSimpleVo> getKsBySrm(@Param("srm") String srm, @Param("username") String username);

    /**
     * 通过多个部门id查询部门
     *
     * @param deptId
     * @return
     */
    List<DeptSimpleVo> selectDeptInId(@Param("deptId") String[] deptId);

    /**
     * 获取所有科室
     *
     * @param key
     * @return
     */
    List<AllKsVo> getAllks(@Param("key") String key);

    /**
     * 获取所有部门
     *
     * @param key
     * @return
     */
    List<AllDepartDataVo> getAllDepartData(@Param("key") String key);

    /**
     * 获取所有部门
     *
     * @return
     */
    List<SysDept> selectAllDeptList();

    /**
     * 通过deptNoList批量查询
     * @param deptNoList
     * @return
     */
    List<SysDept> getByDeptNos(@Param("deptNoList") List<String> deptNoList);

    /**
     * 获取科室的输入码
     * @param depName
     * @return
     */
    String getDepSrm(@Param("depName") String depName);
}
