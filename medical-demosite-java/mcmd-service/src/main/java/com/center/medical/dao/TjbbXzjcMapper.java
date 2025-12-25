package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.TjbbXzjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS血脂检测体检报表(TjbbXzjc)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:15
 */
public interface TjbbXzjcMapper extends BaseMapper<TjbbXzjc> {

    /**
     * 分页查询[KS血脂检测体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbXzjc查询参数
     * @return 分页数据
     */
    IPage<TjbbXzjc> getList(PageParam<TjbbXzjc> page, @Param("param") TjbbXzjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TjbbXzjc getInfoById(@Param("id") String id);

}
