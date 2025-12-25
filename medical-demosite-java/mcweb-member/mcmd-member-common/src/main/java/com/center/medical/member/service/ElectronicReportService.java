package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientarchive;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.ELSaOrUpParam;
import com.center.medical.member.bean.param.ElReportParam;
import com.center.medical.member.bean.param.SaveMergeParam;
import com.center.medical.member.bean.vo.ElReportVo;
import com.center.medical.member.bean.vo.EleInfoListVo;

/**
 * 体检者档案表(Peispatientarchive)表服务接口
 *
 * @author ay
 * @since 2023-02-27 11:31:30
 */
public interface ElectronicReportService extends IService<Peispatientarchive> {

    /**
     * 分页查询[体检者档案表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ElReportVo> getList(PageParam<ElReportVo> page, ElReportParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatientarchive getInfoById(String id);

    /**
     * 合并档案
     *
     * @param param
     * @return
     */
    Boolean saveMerge(SaveMergeParam param);

    /**
     * 编辑-保存
     *
     * @param param
     * @return
     */
    Boolean saOrUp(ELSaOrUpParam param);

    /**
     * 档案下体检者明细数据
     * @param page
     * @param id
     * @return
     */
    IPage<EleInfoListVo> getEleInfoListData(PageParam<EleInfoListVo> page, String patientarchiveno);
}

