package com.center.medical.query.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.query.bean.param.TotalAddParam;
import com.center.medical.query.bean.vo.TotalAddVo;

import java.util.List;

/**
 * 加项情况查询(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-04-08 09:31:51
 */
public interface TotalAddService extends IService<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<TotalAddVo> getList(PageParam<TotalAddVo> page, TotalAddParam param);


    /**
     * 导出加项查询数据
     *
     * @param param
     * @return
     */
    List<TotalAddVo> getExportData(TotalAddParam param);

    /**
     * 获取分页合计
     * @param param
     * @return
     */
    Double getPageTotal(TotalAddParam param);
}

