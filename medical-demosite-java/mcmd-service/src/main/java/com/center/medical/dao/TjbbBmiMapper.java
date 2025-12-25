package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.model.TjbbBmi;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * KS体重指数体检报表(TjbbBmi)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:35:21
 */
public interface TjbbBmiMapper extends BaseMapper<TjbbBmi> {

    /**
     * 分页查询[KS体重指数体检报表]列表
     *
     * @param page  分页参数
     * @param param TjbbBmi查询参数
     * @return 分页数据
     */
    IPage<TjbbBmi> getList(PageParam<TjbbBmi> page, @Param("param") TjbbBmi param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    TjbbBmi getInfoById(@Param("id") String id);

}
