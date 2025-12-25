package com.center.medical.pacs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.bean.vo.PacsHistoryListVo;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacs.bean.dto.PCFromDataDto;
import com.center.medical.pacs.bean.dto.PCGriddataDto;
import com.center.medical.pacs.bean.param.*;
import com.center.medical.pacs.bean.vo.*;

import java.util.List;

/**
 * QT体检者表(Peispatient)服务接口
 *
 * @author ay
 * @since 2023-10-08 09:26:32
 */
public interface PacsCsharpService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    String getPage(PageParam<PacsGetRankDataVo> page, GetRankDataParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(String id);

    /**
     * 收费项目表格数据
     * @param param
     * @return
     */
    List<PacsGetItemsDataVo> getItemsData(GetItemsDataVoParam param);

    /**
     * 结论词表格数据
     * @param param
     * @return
     */
    List<PacsGetJlcGridVo> getJlcGridData(PacsGetJlcGridParam param);

    /**
     * 选择收费项目
     * @param param
     * @return
     */
    String search(PacsSearchParam param);

    /**
     * 监察人、审核人下拉数据
     * @param userId
     * @param ksID
     * @param key
     * @return
     */
    List<PacsAllDoctorsVo> allDoctors(String userId, String ksID, String key);

    /**
     * 获取配置
     * @param type
     * @return
     */
    boolean getDictionaryStatusByType(String type);

    /**
     * 检查体检者状态
     * @param patientcode
     * @return
     */
    String check(String patientcode);

    /**
     * 保存
     * @param fromData
     * @param griddata
     * @param username
     * @return
     */
    String saOrUp(PCFromDataDto fromData, List<PCGriddataDto> griddata, String username);

    /**
     * 反审核
     * @param param
     * @return
     */
    String reverse(PascReverseParam param);

    /**
     * 保存图片
     * @param param
     * @return
     */
    String saveImg(PacsSaveImgParam param);

    /**
     * 获取历史数据
     * @param patientcode
     * @param itemId
     * @param ksID
     * @return
     */
    List<PacsHistoryListVo> getHistoryData(String patientcode, String itemId, String ksID);

    /**
     * 发送中间库保存数据
     * @param patientcode
     * @param depId
     */
    void sendSaveResult(String patientcode, String depId);

    /**
     * 接收检查数据
     * @param param
     * @return
     */
    Boolean postCheckExam(PostCheckExamParam param);
}

