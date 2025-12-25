package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Yblx;
import com.center.medical.data.bean.param.YblxParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 样本类型(Yblx)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-29 11:22:58
 */
public interface YblxMapper extends BaseMapper<Yblx> {

    /**
     * 分页查询[样本类型]列表
     *
     * @param page  分页参数
     * @param param Yblx查询参数
     * @return 分页数据
     */
    IPage<Yblx> getPage(PageParam<Yblx> page, @Param("param") YblxParam param);

    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    List<Yblx> getList(@Param("param") YblxParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    Yblx getInfoById(@Param("id") String id);

}
