package com.center.medical.query.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.OriginalParam;
import com.center.medical.query.bean.param.StatusQueryParam;
import com.center.medical.query.bean.vo.OriginalVo;
import com.center.medical.query.bean.vo.StatusQueryVo;

import java.util.List;

/**
 * 项目检况查询(Peispatientfeeitem)表服务接口
 *
 * @author ay
 * @since 2023-04-13 17:32:56
 */
public interface ItemsStatusQueryService extends IService<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<StatusQueryVo> getList(PageParam<StatusQueryVo> page, StatusQueryParam param);



    /**
     * 导出迟检、拒检、弃检、补检统计
     * @param param
     * @return
     */
    List<StatusQueryVo> getExportData(StatusQueryParam param);

    /**
     * 分页查询科室拒检查询
     * @param page
     * @param param
     * @return
     */
    IPage<OriginalVo> getOriginalRejection(PageParam<OriginalVo> page, OriginalParam param);
}

