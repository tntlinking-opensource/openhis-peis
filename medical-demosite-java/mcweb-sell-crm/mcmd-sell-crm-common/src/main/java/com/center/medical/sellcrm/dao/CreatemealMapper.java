package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.dto.CMSfxmDto;
import com.center.medical.sellcrm.bean.dto.CreatemealExportXyDto;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.bean.param.CreatemealParam;
import com.center.medical.sellcrm.bean.param.CreateorderParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 普通套餐表(Createmeal)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-14 19:21:19
 */
public interface CreatemealMapper extends BaseMapper<Createmeal> {

    /**
     * 分页查询[普通套餐表]列表
     *
     * @param page  分页参数
     * @param param Createmeal查询参数
     * @return 分页数据
     */
    IPage<Createmeal> getPage(PageParam<Createmeal> page, @Param("param") CreatemealParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Createmeal getInfoById(@Param("id") String id);

    /**
     * 判断套餐能否编辑
     *
     * @param tcId
     * @return
     */
    String isEdit(@Param("tcId") String tcId);

    /**
     * 判断套餐能否删除
     *
     * @param tcId
     * @return
     */
    String isRemove(@Param("tcId") String tcId);

    /**
     * 获取普通套餐与最小套餐的数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<Createmeal> getTcData(PageParam<Createmeal> page, @Param("param") CreateorderParam param);

    /**
     * 返回客户从未使用过的套餐和客户单位电话
     *
     * @param khdwdhId
     * @param ids
     * @param seasonZk
     * @return
     */
    List<Createmeal> getKhdwdhAndTcs(@Param("khdwdhId") String khdwdhId, @Param("ids") List<String> ids, @Param("seasonZk") Double seasonZk);

    /**
     * 获取体检套餐名称
     * @param idTjtc
     * @return
     */
    String getTjtcmc(@Param("idTjtc")String idTjtc);

    /**
     * 导出协议套餐
     * @param tcId
     * @param orderId
     * @return
     */
    List<CreatemealExportXyDto> getExportXyData(@Param("tcId")List<String> tcId,@Param("orderId") String orderId);

    /**
     * 普通套餐表查询职业项目
     * @param id
     * @param harmIdsSql
     * @param medicaltype
     * @return
     */
    List<String> findMedicalItems(@Param("id")String id, @Param("harmIds")String[] harmIdsSql,@Param("medicaltype") Integer medicaltype);

    /**
     * 最小套餐查询职业项目
     * @param id
     * @param harmIdsSql
     * @param medicaltype
     * @return
     */
    List<String> findMedicalItems2(@Param("id")String id,@Param("harmIds") String[] harmIdsSql,@Param("medicaltype") Integer medicaltype);

    /**
     * 获取增加健康项目1
     * @param jhyss
     * @param zytjlb
     * @param id
     * @return
     */
    String getJxItemsStr1(@Param("jhyss")String[] jhyss,@Param("zytjlb") String zytjlb,@Param("id") String id);

    /**
     * 获取增加健康项目2
     * @param jhyss
     * @param zytjlb
     * @param id
     * @return
     */
    String getJxItemsStr2(@Param("jhyss")String[] jhyss,@Param("zytjlb") String zytjlb,@Param("id") String id);

    /**
     * 获取增加健康项目通过id
     * @param id
     * @return
     */
    String getJxItemsStrById1(@Param("id") String id);


    /**
     * 获取增加健康项目通过id
     * @param id
     * @return
     */
    String getJxItemsStrById2(@Param("id")String id);

    /**
     * 获取收费项目
     * @param mealIds
     * @return
     */
    List<CMSfxmDto> getSfxm(@Param("mealIds") String mealIds);

    /**
     * 通过套餐id获取成本价
     * @param id
     * @return
     */
    Double getCostpriceByTcid(@Param("id") String id);

    /**
     * 获取相关的收费项目
     * @param id
     * @return
     */
    List<CMSfxmDto> getSfxmByOrder(@Param("id") String id);

    /**
     * 查询平安套餐
     * @param medicalPackage
     * @param idSex
     * @param branchId
     * @param sfyh
     * @return
     */
    List<Createmeal> findPingAn(@Param("medicalPackage") String medicalPackage,@Param("idSex") Integer idSex,@Param("branchId") String branchId,@Param("sfyh") String sfyh);

    /**
     * 获取套餐的分中心名称
     * @param id
     * @return
     */
    String getBranchNames(@Param("id") String id);
}
