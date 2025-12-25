package com.center.medical.pacs.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.bean.vo.PacsHistoryListVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Items;
import com.center.medical.pacs.bean.param.GetItemsDataVoParam;
import com.center.medical.pacs.bean.param.GetRankDataParam;
import com.center.medical.pacs.bean.param.PacsGetJlcGridParam;
import com.center.medical.pacs.bean.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QT体检者表(Peispatient)数据库访问层
 *
 * @author ay
 * @since 2023-10-08 09:26:32
 */
public interface PacsCsharpMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<PacsGetRankDataVo> getPage(PageParam<PacsGetRankDataVo> page, @Param("param") GetRankDataParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 获取分拣已审的个数
     * @param param
     * @return
     */
    Long getCountSql(@Param("param") GetRankDataParam param);

    /**
     * 收费项目表格数据
     * @param param
     * @return
     */
    List<PacsGetItemsDataVo> getItemsData(@Param("param") GetItemsDataVoParam param);

    /**
     * 收费项目表格数据2,pacs未开启
     * @param param
     * @return
     */
    List<PacsGetItemsDataVo> getItemsData2(@Param("param") GetItemsDataVoParam param);

    /**
     * 结论词表格数据
     * @param param
     * @return
     */
    List<PacsGetJlcGridVo> getJlcGridData(@Param("param") PacsGetJlcGridParam param);

    /**
     * 获取历史数据
     * @param patientcode
     * @param itemId
     * @param ksID
     * @return
     */
    List<PacsHistoryListVo> getHistoryList(@Param("patientcode") String patientcode, @Param("itemId") String itemId, @Param("ksID")  String ksID);

    /**
     * 监察人、审核人下拉数据
     * @param cid
     * @param ksID
     * @param key
     * @return
     */
    List<PacsAllDoctorsVo> allDoctors(@Param("cid") String cid,@Param("ksID") String ksID,@Param("key") String key);

    /**
     * 获取体检者收费项目
     * @param code
     * @param patientcode
     * @return
     */
    Peispatientfeeitem getTjItemByCode(@Param("code") String code,@Param("patientcode") String patientcode);

    /**
     * 获取检查项目
     * @param patientcode
     * @param examfeeitemCode
     * @return
     */
    List<Items> getItems(@Param("patientcode") String patientcode,@Param("examfeeitemCode") String examfeeitemCode);
}
