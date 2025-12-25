package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.dto.SpecialSymbolsDataDto;
import com.center.medical.bean.model.Attachment;
import com.center.medical.bean.vo.ThirdPartyImagesVo;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * JC附件(Attachment)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:53
 */
public interface AttachmentMapper extends BaseMapper<Attachment> {

    /**
     * 分页查询[JC附件]列表
     *
     * @param page  分页参数
     * @param param Attachment查询参数
     * @return 分页数据
     */
    IPage<Attachment> getList(PageParam<Attachment> page, @Param("param") Attachment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Attachment getInfoById(@Param("id") String id);

    /**
     * 根据id列表获取文件信息列表
     *
     * @param ids
     * @return
     */
    List<Attachment> getByIds(@Param("ids") List<String> ids);

    /**
     * 根据id列表批量删除文件记录
     *
     * @param ids
     */
    void batchDeleteByIds(@Param("ids") List<String> ids);

    /**
     * 查询第三方系统报告
     * @param patientcode
     * @param feeItemId
     * @return
     */
    List<String> queryThirdReports(@Param("patientcode") String patientcode,@Param("feeItemId") String feeItemId);

    /**
     * 查询包含特殊符号的数据
     * @param symbol
     * @return
     */
    List<SpecialSymbolsDataDto> getSpecialSymbolsData(@Param("symbol") String symbol);

    /**
     * 查询第三方系统图片
     * @param patientcode
     * @return
     */
    List<ThirdPartyImagesVo> getThirdPartyReport(@Param("patientcode") String patientcode);
}
