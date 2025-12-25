package com.center.medical.sellcrm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.sellcrm.bean.model.CreatecomboType;
import com.center.medical.sellcrm.bean.vo.TypeListVo;
import org.apache.ibatis.annotations.Param;

/**
 * 最小套餐分类(CreatecomboType)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-14 19:21:18
 */
public interface CreatecomboTypeMapper extends BaseMapper<CreatecomboType> {

    /**
     * 分页查询[最小套餐分类]列表
     *
     * @param page  分页参数
     * @param param CreatecomboType查询参数
     * @return 分页数据
     */
    IPage<CreatecomboType> getList(PageParam<CreatecomboType> page, @Param("param") CreatecomboType param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CreatecomboType getInfoById(@Param("id") String id);

    /**
     * 获取最小套餐分类
     *
     * @param page
     * @return
     */
    IPage<TypeListVo> getTypeList(PageParam<TypeListVo> page);
}
