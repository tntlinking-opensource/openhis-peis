package com.center.medical.finance.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.finance.bean.param.TQPageParam;
import com.center.medical.finance.bean.vo.RemitterVo;
import com.center.medical.finance.bean.vo.TQPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 记账管理-记账结算(Peispatient)表数据库访问层
 *
 * @author ay
 * @since 2023-03-31 14:27:31
 */
public interface TallyQueryMapper extends BaseMapper<Peispatient> {

    /**
     * 分页查询[QT体检者表]列表
     *
     * @param page  分页参数
     * @param param Peispatient查询参数
     * @return 分页数据
     */
    IPage<TQPageVo> getList(PageParam<TQPageVo> page, @Param("param") TQPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Peispatient getInfoById(@Param("id") String id);

    /**
     * 记帐查询数据导出
     *
     * @param param
     * @return
     */
    List<TQPageVo> getExportData(@Param("param") TQPageParam param);

    /**
     * 获取汇款单位名单
     * @param page
     * @param key
     * @return
     */
    IPage<RemitterVo> getRemitter(PageParam<RemitterVo> page,@Param("key") String key);
}
