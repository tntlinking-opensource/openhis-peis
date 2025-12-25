package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdCard;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检卡(MdCard)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
public interface MdCardMapper extends BaseMapper<MdCard> {

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param MdCard查询参数
     * @return 分页数据
     */
    IPage<MdCard> getPage(PageParam<MdCard> page, @Param("param") MdCard param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdCard getInfoById(@Param("id") String id);

}
