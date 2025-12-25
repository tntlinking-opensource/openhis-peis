package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.TjbbXtjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS血糖检测体检报表(TjbbXtjc)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:32
 */
public interface TjbbXtjcMapper extends BaseMapper<TjbbXtjc> {

    /**
     * 分页查询[KS血糖检测体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbXtjc查询参数
     * @return 分页数据
     */
    IPage<TjbbXtjc> getList(PageParam<TjbbXtjc> page, @Param("param") TjbbXtjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TjbbXtjc getInfoById(@Param("id") String id);

}
