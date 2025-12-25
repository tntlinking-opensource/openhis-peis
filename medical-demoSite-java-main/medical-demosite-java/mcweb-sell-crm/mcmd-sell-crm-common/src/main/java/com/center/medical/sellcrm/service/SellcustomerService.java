package com.center.medical.sellcrm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.core.domain.entity.SysRole;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.vo.*;
import com.center.medical.sellcrm.bean.model.Sellcustomer;
import com.center.medical.sellcrm.bean.param.FormalPageParam;
import com.center.medical.sellcrm.bean.param.SaveCuParam;
import com.center.medical.sellcrm.bean.param.SellcustomerParam;
import com.center.medical.sellcrm.bean.vo.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * 我的客户表(Sellcustomer)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:21
 */
public interface SellcustomerService extends IService<Sellcustomer> {

    /**
     * 分页查询[我的客户表]列表
     *
     * @param page  分页参数
     * @param param Sellcustomer查询参数
     * @return 分页数据
     */
    IPage<Sellcustomer> getPage(PageParam<Sellcustomer> page, SellcustomerParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Sellcustomer getInfoById(String id);

    /**
     * 新增/编辑操作
     *
     * @param sellcustomer
     * @return
     */
    String saOrUp(Sellcustomer sellcustomer);

    /**
     * 删除我的客户表
     *
     * @param ids 要删除的对象id集合，多个以英文逗号（,）隔开
     * @return
     */
    Boolean delete(List<String> ids);

//    /**
//     * 多文件压缩
//     *
//     * @param saveFilePathId
//     * @return
//     */
//    String compressToDownLoad(String saveFilePathId);


//    /**
//     * 多文件压缩后进行下载
//     *
//     * @param path
//     * @param res
//     */
//    void downLoad(String path, HttpServletResponse res);

    /**
     * 客户释放/客户流失
     *
     * @param ids   操作的对象主键id集合
     * @param state 操作标识：0.客户流失 1.客户释放
     * @return
     */
    Boolean customerRelease(List<String> ids, Integer state);

    /**
     * 客户升级
     *
     * @param ids 操作的对象主键id集合
     * @return
     */
    Boolean upgrade(List<String> ids, String centerOrgName);

    /**
     * 为已经是正式客户的但没有生成网站账号的客户生成账号
     *
     * @param ids
     * @return
     */
    String generateAccount(List<String> ids);

    /**
     * 验证输入的客户名称是否在该销售名下存在重复
     *
     * @param cusName
     * @return
     */
    String onBlur(String cusName);

    /**
     * 我的客户导出
     *
     * @param param 筛选条件
     */
    void export(HttpServletResponse response, SellcustomerParam param, List<SysRole> sysRoles, String fileName);

    /**
     * 获取所有的公司客户名称
     *
     * @param page
     * @param key
     * @return
     */
    IPage<Sellcustomer> getAllCompany(PageParam<Sellcustomer> page, String key);

    /**
     * 导入客户信息
     *
     * @param list     客户信息列表
     * @param operName 操作者名称
     * @return
     */
    Boolean importSellcustomer(List<Sellcustomer> list, String operName);

    /**
     * 获取所有客户
     *
     * @param page
     * @param key
     * @return
     */
    IPage<AllOrgVo> getAllOrg(PageParam<AllOrgVo> page, String key);

    /**
     * 进行领导分配
     *
     * @param clientIds 公共池记录ID集合
     * @param xsIds     销售ID列表
     * @return
     */
    boolean allocation(List<String> clientIds, List<String> xsIds);


    /**
     * 判断领导分配的记录里面是否存在销售之前领取过的记录
     *
     * @param clientIds 公共池记录ID集合
     * @param xsIds     销售ID列表
     * @return
     */
    boolean distribution(List<String> clientIds, List<String> xsIds);

    /**
     * 判断领导分配的记录里面是否存在销售之前领取过的记录
     *
     * @param clientIds 公共池记录ID集合
     * @param xsIds     销售ID列表
     * @return
     */
    boolean isAllocation(List<String> clientIds, List<String> xsIds);

    /**
     * 获取所有的年份
     *
     * @return
     */
    List getAllYear();

    /**
     * 根据年份获取本分中心下的正式客户
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Sellcustomer> getYearListData(PageParam<Sellcustomer> page, SellcustomerParam param);

    /**
     * 团体名称下拉
     *
     * @param key
     * @return
     */
    List<GetOrgsVo> getOrgs(String key);

    /**
     * 获取区域代码
     *
     * @param zoneCode
     * @param level
     * @return
     */
    List<ZoneVo> getZoneData(String zoneCode, String level);

    /**
     * 获取行业类别代码
     *
     * @param indusTypeCode
     * @param level
     * @return
     */
    List<IndusDataVo> getIndusData(String indusTypeCode, String level);

    /**
     * 获取经济类型316
     *
     * @return
     */
    List<EconomyCodeVo> getEconomyCode();

    /**
     * 获取经济类型317
     *
     * @return
     */
    List<CrptSizeCodeVo> getCrptSizeCode();

    /**
     * 获取所属区域
     *
     * @return
     */
    List<UnitAreaVo> getUnitArea();

    /**
     * 获取记账单位
     *
     * @param page
     * @param key
     * @return
     */
    IPage<JzOrgVo> getJzOrg(PageParam<JzOrgVo> page, String key);

    /**
     * 分页查询正式客户
     *
     * @param page
     * @param param
     * @return
     */
    IPage<FormalPageVo> getFormalPage(PageParam<FormalPageVo> page, FormalPageParam param);

    /**
     * 导出正式客户
     *
     * @param param
     * @return
     */
    List<FormalPageVo> getExportData(FormalPageParam param);

    /**
     * 客户跟进
     *
     * @param param
     * @return
     */
    Boolean saveCustomerFollowData(SaveCuParam param);

    /**
     * 客户升级-判断是否为正式客户
     *
     * @param isZskhId
     * @return
     */
    String isZskh(List<String> isZskhId);

    /**
     * 获取当前登录用户分中心下正式客户管理信息
     *
     * @param page
     * @param key
     * @return
     */
    IPage<SCListVo> getListDatas(PageParam<SCListVo> page, String key);

    /**
     * 重置密码
     * @param ids
     * @return
     */
    Boolean resetPassword(List<String> ids);

    /**
     * 批量修改插入 对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatchRilin(Collection<Sellcustomer> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 处理重复intId的
     * @param indIds
     * @return
     */
    Boolean intIdRepeated(List<String> indIds);
}

