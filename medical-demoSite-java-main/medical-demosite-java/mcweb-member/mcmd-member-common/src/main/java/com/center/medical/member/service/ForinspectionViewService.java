package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.model.VisitMain;
import com.center.medical.member.bean.param.ComboExamItemParam;
import com.center.medical.member.bean.param.ForinspectionViewEditParam;
import com.center.medical.member.bean.param.ForinspectionViewParam;
import com.center.medical.member.bean.vo.ForinspectionViewEditVo;
import com.center.medical.member.bean.vo.ForinspectionViewVo;

import java.util.List;

/**
 * 与迟检、阳性、不合格样本回访表一对多关联，(VisitMain)表服务接口
 *
 * @author makejava
 * @since 2023-02-10 09:46:44
 */
public interface ForinspectionViewService extends IService<VisitMain> {

    /**
     * 分页查询[与迟检、阳性、不合格样本回访表一对多关联，]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ForinspectionViewVo> getPage(PageParam<ForinspectionViewVo> page, ForinspectionViewParam param);

    /**
     * 不满意客户回访导出excel
     *
     * @param param
     * @return
     */
    List<ForinspectionViewVo> export(ForinspectionViewParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ForinspectionViewEditVo getInfoById(String id);

    /**
     * 迟补检回访
     *
     * @param param
     * @return
     */
    Boolean saOrUp(ForinspectionViewEditParam param);


    /**
     * isZy
     *
     * @param param
     * @return
     */
    boolean isZy(ComboExamItemParam param);
}


