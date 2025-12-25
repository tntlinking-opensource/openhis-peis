package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.vo.CenterListDataVo;

import java.util.List;

/**
 * 分中心管理Service接口
 *
 * @author 路飞
 * @date 2022-10-21
 */
public interface ISysBranchService extends IService<SysBranch> {
    /**
     * 查询分中心管理
     *
     * @param id 分中心管理主键
     * @return 分中心管理
     */
    SysBranch getDetailById(Integer id);

    /**
     * 根据分中心编码查询分中心信息
     *
     * @param branchId 分中心编码（对应原系统的分中心ID）
     * @return 分中心信息
     */
    SysBranch getByBranchId(String branchId);

    /**
     * 删除分中心缓存
     */
    void removeBranchCaching(String branchId);

    /**
     * 查询分中心管理列表
     *
     * @param param 查询参数
     * @return 分中心管理集合
     */
    IPage<SysBranch> getList(PageParam<SysBranch> page, SysBranch param);

    /**
     * 查询新系统开放的分中心管理列表
     *
     * @param flag 是否先从缓存中获取：0否，1是
     * @return
     */
    List<SysBranch> getOpenList(Integer flag);

    /**
     * 查询分中心是否开放
     *
     * @param branchId 分中心ID
     * @return
     */
    Boolean isOpened(String branchId);

    /**
     * 新增分中心管理
     *
     * @param sysBranch 分中心管理
     * @return 结果
     */
    int insertNew(SysBranch sysBranch);

    /**
     * 修改分中心管理
     *
     * @param sysBranch 分中心管理
     * @return 结果
     */
    int upById(SysBranch sysBranch);

    /**
     * 批量删除分中心管理
     *
     * @param ids 需要删除的分中心管理主键集合
     * @return 结果
     */
    int delByIds(List<Integer> ids);

    /**
     * 删除分中心管理信息
     *
     * @param id 分中心管理主键
     * @return 结果
     */
    int delById(Integer id);

    /**
     * 获取默认的分中心 is_default = 1
     *
     * @return
     */
    SysBranch getDefaultBranch();

    /**
     * 根据当前用户获取分中心前缀
     *
     * @return String
     */
    String getBranchPrefix();

    /**
     * 根据当前用户获取分中心的标识：前缀+version
     *
     * @return String
     */
    String getBranchFlag(String branchId);

    /**
     * 登录页面分中心数据
     *
     * @return
     */
    List<CenterListDataVo> centerListData();

    /**
     * 初始化分中心信息到缓存中，如分中心简码（数据同步时用到）
     */
    void init();

    /**
     * 通过分中心名称查询
     *
     * @param name
     * @return
     */
    SysBranch getByBranchName(String name);

    /**
     * 删除分中心开放标识缓存
     */
    void delBranchOpenedFlag();
    /**
     * 删除分中心开放同步标识缓存
     */
    void delBranchOpenedSyncFlag();
}
