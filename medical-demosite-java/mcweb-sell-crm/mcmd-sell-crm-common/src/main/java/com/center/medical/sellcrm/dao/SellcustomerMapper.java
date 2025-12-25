package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.FormalPageParam;
import com.center.medical.sellcrm.bean.param.SellcustomerParam;
import com.center.medical.sellcrm.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 我的客户表(Sellcustomer)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:21
 */
public interface SellcustomerMapper extends BaseMapper<Sellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param Sellcustomer查询参数
     * @return 分页数据
     */
    IPage<Sellcustomer> getPage(PageParam<Sellcustomer> page, @Param("param") SellcustomerParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Sellcustomer getInfoById(@Param("id") String id);

    /**
     * 团体名称下拉
     *
     * @param key
     * @return
     */
    List<GetOrgsVo> getOrgs(@Param("key") String key);

    /**
     * 获取所有的客户
     *
     * @param key
     * @return
     */
    IPage<AllOrgVo> getAllOrg(PageParam<AllOrgVo> page, @Param("key") String key);

    /**
     * 获取自己分中心下的公司下拉
     *
     * @param page
     * @param key
     * @param fzxId
     * @return
     */
    IPage<AllOrgVo> getFzxOrg(PageParam<AllOrgVo> page, @Param("key") String key, @Param("fzxId") String fzxId);

    /**
     * 获取记账单位
     *
     * @param page
     * @param key
     * @return
     */
    IPage<JzOrgVo> getJzOrg(PageParam<JzOrgVo> page, @Param("key") String key);

    /**
     * 分页查询正式客户
     *
     * @param page
     * @param param
     * @return
     */
    IPage<FormalPageVo> getFormalPage(PageParam<FormalPageVo> page, @Param("param") FormalPageParam param);

    /**
     * 导出正式客户
     *
     * @param param
     * @return
     */
    List<FormalPageVo> getExportData(@Param("param") FormalPageParam param);

    /**
     * 获取当前登录用户分中心下正式客户管理信息
     *
     * @param page
     * @param key
     * @return
     */
    IPage<SCListVo> getListDatas(PageParam<SCListVo> page, @Param("key") String key, @Param("cId") String cId);
}
