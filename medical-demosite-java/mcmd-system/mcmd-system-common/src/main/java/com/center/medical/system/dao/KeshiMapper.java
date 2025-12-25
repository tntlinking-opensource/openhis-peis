package com.center.medical.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.bean.vo.ListWithSrmVo;
import com.center.medical.system.bean.model.Keshi;
import com.center.medical.system.bean.param.KeshiParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科室管理数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-29 11:53:34
 */
public interface KeshiMapper extends BaseMapper<Keshi> {


    /**
     * 根据条件查询列表数据
     *
     * @param param 查询参数
     * @return 列表数据
     */
    List<ListWithSrmVo> getList(@Param("param") KeshiParam param);
}
