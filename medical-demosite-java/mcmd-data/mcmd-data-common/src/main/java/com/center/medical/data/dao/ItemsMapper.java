package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.dto.ComboDataDto;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.bean.param.GetSfxmParam;
import com.center.medical.data.bean.param.GetShowParam;
import com.center.medical.data.bean.param.ItemsParam;
import com.center.medical.data.bean.vo.*;
import com.center.medical.data.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * JC收费项目表(Items)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:04
 */
public interface ItemsMapper extends BaseMapper<Items> {


    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param Items查询参数
     * @return 分页数据
     */
    IPage<Items> getPage(PageParam<Items> page, @Param("param") ItemsParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Items getInfoById(@Param("id") String id);

    List<ItemsDataVO> getItemsData(@Param("param") ItemsParam param);

    /**
     * list页面双击获取收费项目信息
     *
     * @param tcId 套餐ID
     * @return 所有数据
     */
    List<ItemsDataVO> getItemsByTcId(@Param("tcId") String tcId);

    IPage<ItemsVo> getItemVoPage(PageParam<ItemsVo> page, @Param("param") ItemsParam param);

    /**
     * 根据patientCode和ksId获取
     *
     * @param patientCode
     * @param ksId
     * @return
     */
    List<Items> selectWords(@Param("patientCode") String patientCode, @Param("ksId") String ksId);

    /**
     * 获取所有职业必检选检项目
     *
     * @param jhys
     * @param medicaltype
     * @return
     */
    List<Items> getProItems(@Param("jhys") String[] jhys, @Param("medicaltype") String medicaltype);

    /**
     * 创建套餐获取基础数据收费项目
     *
     * @param page
     * @param param
     * @return
     */
    IPage<GetSfxmVo> getSfxm(PageParam<GetSfxmVo> page, @Param("param") GetSfxmParam param);

    /**
     * 按性别 职业体检类型  接害因素 获取最小套餐收费项目 ID 价格
     *
     * @param syxb
     * @param zytjlb
     * @param jhys
     * @return
     */
    List<ComboDataDto> getComboData(@Param("syxb") Integer syxb, @Param("zytjlb") String zytjlb, @Param("jhys") String jhys);

    /**
     * 推荐收费项目-查看套餐下的检查项目
     *
     * @param tcId
     * @return
     */
    List<ItemsByTcVo> getItemsDataByTcId(@Param("tcId") String tcId);

    /**
     * 获取所有收费项目
     *
     * @param key
     * @return
     */
    IPage<AllItemsVo> getAllItemsData(PageParam<AllItemsVo> page, @Param("key") String key);

    /**
     * 返回符合条件的收费项目数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<GetShowVo> getShowData(PageParam<GetShowVo> page, @Param("param") GetShowParam param);

    /**
     * 导出收费项目设置数据
     * @param param
     * @return
     */
    List<Items> getExportData(@Param("param") ItemsParam param);

    /**
     * 导出页面内容
     * @param param
     * @return
     */
    List<ItemsExportAllVo> getExportAllData(@Param("param") ItemsParam param);

    /**
     * 通过收费项目id和分中心id查询数量
     * @param id
     * @param fzxId
     * @return
     */
    int countByIdAndFzx(@Param("id") String id,@Param("fzxIds") String[] fzxIds);
}
