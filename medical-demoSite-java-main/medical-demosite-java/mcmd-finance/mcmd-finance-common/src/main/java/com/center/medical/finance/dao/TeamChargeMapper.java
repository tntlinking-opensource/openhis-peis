package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.TCPageParam;
import com.center.medical.finance.bean.vo.TCExportVo;
import com.center.medical.finance.bean.vo.TCPageVo;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 团体结算(Peisorgreservation)表数据库访问层
 *
 * @author ay
 * @since 2023-04-03 16:32:34
 */
public interface TeamChargeMapper extends BaseMapper<Peisorgreservation> {

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservation查询参数
     * @return 分页数据
     */
    IPage<TCPageVo> getList(PageParam<TCPageVo> page, @Param("param") TCPageParam param);


    /**
     * 导出团体结算数据
     *
     * @param param
     * @return
     */
    List<TCExportVo> getExportData(@Param("param") TCPageParam param);
}
