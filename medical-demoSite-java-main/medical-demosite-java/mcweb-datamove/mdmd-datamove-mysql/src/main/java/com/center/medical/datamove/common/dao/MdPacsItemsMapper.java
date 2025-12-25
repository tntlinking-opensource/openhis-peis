package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPacsItems;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * PACS-收费项目(MdPacsItems)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:34
 */
public interface MdPacsItemsMapper extends BaseMapper<MdPacsItems> {

    /**
     * 分页查询[PACS-收费项目]列表
     *
     * @param page  分页参数
     * @param param MdPacsItems查询参数
     * @return 分页数据
     */
    IPage<MdPacsItems> getPage(PageParam<MdPacsItems> page, @Param("param") MdPacsItems param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPacsItems getInfoById(@Param("id") String id);

}
