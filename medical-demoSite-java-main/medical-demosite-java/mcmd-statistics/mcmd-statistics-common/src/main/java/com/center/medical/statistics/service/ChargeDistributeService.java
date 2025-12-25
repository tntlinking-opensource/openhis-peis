package com.center.medical.statistics.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.ChargeDistributeParam;
import com.center.medical.statistics.bean.vo.CDGetTotalVo;
import com.center.medical.statistics.bean.vo.ChargeDistributeVo;

import java.util.List;

/**
 * 收费项目分布情况(Peispatientfeeitem)表服务接口
 *
 * @author ay
 * @since 2023-04-18 19:47:26
 */
public interface ChargeDistributeService extends IService<Peispatientfeeitem> {

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ChargeDistributeVo> getList(PageParam<ChargeDistributeVo> page, ChargeDistributeParam param);


    /**
     * 导出体检收费项目分布
     * @param param
     * @return
     */
    List<ChargeDistributeVo> exportData(ChargeDistributeParam param);

    /**
     * 获取总数
     * @param param
     * @return
     */
    CDGetTotalVo getTotal(ChargeDistributeParam param);
}

