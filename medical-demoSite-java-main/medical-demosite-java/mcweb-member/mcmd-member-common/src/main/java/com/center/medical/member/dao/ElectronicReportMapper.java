package com.center.medical.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.ElReportParam;
import com.center.medical.member.bean.vo.ElReportVo;
import com.center.medical.member.bean.vo.EleInfoListVo;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者档案表(Peispatientarchive)表数据库访问层
 *
 * @author ay
 * @since 2023-02-27 11:31:30
 */
public interface ElectronicReportMapper extends BaseMapper<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param Peispatientarchive查询参数
     * @return 分页数据
     */
    IPage<ElReportVo> getList(PageParam<ElReportVo> page, @Param("param") ElReportParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(@Param("id") String id);

    /**
     * 档案下体检者明细数据
     * @param page
     * @param id
     * @return
     */
    IPage<EleInfoListVo> getEleInfoListData(PageParam<EleInfoListVo> page,@Param("patientarchiveno") String patientarchiveno);
}
