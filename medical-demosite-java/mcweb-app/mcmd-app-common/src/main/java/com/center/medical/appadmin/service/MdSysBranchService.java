package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.MdSysBranch;
import com.center.medical.appadmin.bean.param.MdSysBranchParam;
import com.center.medical.appadmin.bean.param.SysBranchSaOrUpParam;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 分中心维护表(MdSysBranch)服务接口
 *
 * @author ay
 * @since 2024-06-11 16:03:16
 */
public interface MdSysBranchService extends IService<MdSysBranch> {

    /**
     * 分页查询[分中心维护表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<MdSysBranch> getPage(PageParam<MdSysBranch> page, MdSysBranchParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdSysBranch getInfoById(Integer id);

    /**
     * 添加或修改
     * @param param
     * @return
     */
    Boolean saOrUp(SysBranchSaOrUpParam param);

    /**
     * 删除数据
     * @param ids
     * @return
     */
    Boolean deleteByIds(List<Integer> ids);
}

