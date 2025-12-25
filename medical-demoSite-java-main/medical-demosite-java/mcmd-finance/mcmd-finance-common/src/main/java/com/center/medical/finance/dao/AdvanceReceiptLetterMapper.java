package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.ReceiptLetterParam;
import com.center.medical.finance.bean.vo.ReceiptLetterVo;
import com.center.medical.sellcrm.bean.model.Peisorgreservation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 体检者团体任务(Peisorgreservation)数据库访问层
 *
 * @author ay
 * @since 2024-02-19 15:50:20
 */
public interface AdvanceReceiptLetterMapper extends BaseMapper<Peisorgreservation> {

    /**
     * 分页查询[体检者团体任务]列表
     *
     * @param page  分页参数
     * @param param Peisorgreservation查询参数
     * @return 分页数据
     */
    IPage<ReceiptLetterVo> getPage(PageParam<ReceiptLetterVo> page, @Param("param") ReceiptLetterParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peisorgreservation getInfoById(@Param("id") String id);

    /**
     * 导出应收预收函证数据
     * @param param
     * @return
     */
    List<ReceiptLetterVo> getExportData(@Param("param") ReceiptLetterParam param);
}
