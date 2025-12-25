package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.TjbbXtjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS血糖检测体检报表(TjbbXtjc)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:26
 */
public interface TjbbXtjcMapper extends BaseMapper<TjbbXtjc> {

    /**
     * 分页查询[KS血糖检测体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbXtjc查询参数
     * @return 分页数据
     */
    IPage<TjbbXtjc> getPage(PageParam<TjbbXtjc> page, @Param("param") TjbbXtjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TjbbXtjc getInfoById(@Param("id") String id);

}
