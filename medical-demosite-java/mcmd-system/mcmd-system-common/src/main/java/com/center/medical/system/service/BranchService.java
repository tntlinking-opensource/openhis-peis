package com.center.medical.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Branch;
import com.center.medical.bean.param.BranchParam;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.system.bean.vo.CenterOrgNameListVo;

import java.util.List;

/**
 * 分中心维护表(Branch)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 15:23:09
 */
public interface BranchService extends IService<Branch> {

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param Branch查询参数
     * @return 分页数据
     */
    IPage<Branch> getList(PageParam<Branch> page, BranchParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id Branch查询参数
     * @return 分页数据
     */
    Branch getInfoById(Integer id);

    /**
     * 新增或者修改
     *
     * @param branch
     * @return
     */
    Boolean saOrUp(Branch branch);

    /**
     * 获取中心名字
     *
     * @return
     */
    String getCenterOrgName();

    /**
     * 获取分中心信息
     *
     * @param page
     * @param key
     * @return
     */
    IPage<Branch> getBranchData(PageParam<Branch> page, String key);

    /**
     * 获取用户分中心ID及兼职分中心ID
     *
     * @return
     */
    List<String> getUserCid(String userNo);

    /**
     * 获取用户分中心及兼职分中心
     *
     * @return
     */
    List<Branch> getUserBranch(String userNo);

    /**
     * 获取当前账号下所有所属组织
     * @param userNo
     * @return
     */
    List<CenterOrgNameListVo> getUserCenterOrgNameList(String userNo);
}

