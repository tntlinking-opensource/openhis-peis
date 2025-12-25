package com.center.medical.statistics.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.PeisState;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ProfessionUploadResultParam;
import com.center.medical.statistics.bean.vo.ProfessionUploadResultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 云平台上传结果
 * @author xhp
 * @since 2023-11-28 10:06
 */
@Repository
public interface ProfessionUploadResultMapper extends BaseMapper<PeisState> {
    /**
     * 分页查询
     * @param page
     * @param param
     * @return
     */
    IPage<ProfessionUploadResultVo> getPage(PageParam page, @Param("param")ProfessionUploadResultParam param);

    /**
     * 导出
     * @param param
     * @return
     */
    List<ProfessionUploadResultVo> getList( @Param("param")ProfessionUploadResultParam param);
}
