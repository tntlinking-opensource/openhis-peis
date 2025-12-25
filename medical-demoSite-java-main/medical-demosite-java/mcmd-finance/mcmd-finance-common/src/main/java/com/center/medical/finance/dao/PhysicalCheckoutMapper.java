package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.AccountsInfoParam;
import com.center.medical.finance.bean.param.AnalyseParam;
import com.center.medical.finance.bean.vo.AccountsInfoVo;
import com.center.medical.finance.bean.vo.AnalyseVo;
import com.center.medical.finance.bean.vo.ExportItemsVo;
import com.center.medical.finance.bean.vo.PCItemListVo;
import com.center.medical.sellcrm.bean.model.Createorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单表(Createorder)表数据库访问层
 *
 * @author ay
 * @since 2023-03-11 18:02:00
 */
public interface PhysicalCheckoutMapper extends BaseMapper<Createorder> {

    /**
    * 分页查询[订单表]列表
    *
    * @param page 分页参数
    * @param param Createorder查询参数
    * @return 分页数据
    */
    IPage<AnalyseVo> getList(PageParam<AnalyseVo> page, @Param("param") AnalyseParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Createorder getInfoById(@Param("id") String id);

    /**
     * 查看左中体检人数据
     * @param param
     * @return
     */
    IPage<AccountsInfoVo> getAccountsInfoData(PageParam<AccountsInfoVo> page,@Param("param") AccountsInfoParam param);

    /**
     * 项目列表数据
     * @param patientcode
     * @return
     */
    List<PCItemListVo> getItemListData(@Param("patientcode")String patientcode);

    /**
     * 导出收费项目
     * @param patientCode
     * @return
     */
    List<ExportItemsVo> exportItems(@Param("patientcode")String patientCode);


    /**
     * 导出体检人员上方数据
     * @param param
     * @return
     */
    List<AccountsInfoVo> exportAccountsInfoData(@Param("param")AccountsInfoParam param);

    /**
     * 把老系统登记的体检号，在新系统变成禁检
     * @param ddh
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<String> synchronizedChecked(@Param("ddh") String ddh);


    /**
     * 查看新系统体检人数据
     * @param param
     * @return
     */
    List<AccountsInfoVo> getAccountsInfoList(@Param("param") AccountsInfoParam param);

    /**
     * 查看老系统体检人数据
     * @param param
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<AccountsInfoVo> getAccountsOldList(@Param("param") AccountsInfoParam param);

    /**
     * 获取老系统统计数据
     * @param param
     * @return
     */
    @DataSource(value = DataSourceType.SLAVE)
    List<AccountsInfoVo> exportOldAccountsInfoData(@Param("param") AccountsInfoParam param);
}
