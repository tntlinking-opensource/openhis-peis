package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.TjbbXzjc;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS血脂检测体检报表(TjbbXzjc)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:25:29
 */
public interface TjbbXzjcMapper extends BaseMapper<TjbbXzjc> {

    /**
     * 分页查询[KS血脂检测体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbXzjc查询参数
     * @return 分页数据
     */
    IPage<TjbbXzjc> getPage(PageParam<TjbbXzjc> page, @Param("param") TjbbXzjc param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    TjbbXzjc getInfoById(@Param("id") String id);

}
