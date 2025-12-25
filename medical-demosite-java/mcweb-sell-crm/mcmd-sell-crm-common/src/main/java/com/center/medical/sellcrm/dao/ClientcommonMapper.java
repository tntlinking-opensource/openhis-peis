package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.Clientcommon;
import com.center.medical.sellcrm.bean.param.ClientcommonParam;
import org.apache.ibatis.annotations.Param;

/**
 * 客户公共池表(Clientcommon)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:27
 */
public interface ClientcommonMapper extends BaseMapper<Clientcommon> {

    /**
     * 分页查询[客户公共池表]列表
     *
     * @param page  分页参数
     * @param param Clientcommon查询参数
     * @return 分页数据
     */
    IPage<Clientcommon> getPage(PageParam<Clientcommon> page, @Param("param") ClientcommonParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Clientcommon getInfoById(@Param("id") String id);

    /**
     * @param page
     * @param xsjlId
     * @return
     */
    IPage<Clientcommon> getListData(PageParam<Clientcommon> page, @Param("xsjlId") Long xsjlId);
}
