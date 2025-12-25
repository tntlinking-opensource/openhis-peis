package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.PeisState;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ProfessionUploadResultParam;
import com.center.medical.statistics.bean.vo.ProfessionUploadResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 云平台上传结果
 * @author xhp
 * @since 2023-11-28 10:14
 */
public interface ProfessionUploadResultService extends IService<PeisState> {
    /**
     * 分页查询
     * @param page
     * @param param
     * @return
     */
    IPage<ProfessionUploadResultVo> getPage(PageParam page, @Param("param") ProfessionUploadResultParam param);

    /**
     * 重新上传
     * @param patientcodes
     */
    void updateStatus(List<String> patientcodes);

    /**
     * 获取导出数据
     * @param param
     * @return
     */
    List<ProfessionUploadResultVo> getList(ProfessionUploadResultParam param);
}
