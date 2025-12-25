package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdInterfaceAccount;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 各种接口加密信息(MdInterfaceAccount)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:22
 */
public interface MdInterfaceAccountMapper extends BaseMapper<MdInterfaceAccount> {

    /**
     * 分页查询[各种接口加密信息]列表
     *
     * @param page  分页参数
     * @param param MdInterfaceAccount查询参数
     * @return 分页数据
     */
    IPage<MdInterfaceAccount> getPage(PageParam<MdInterfaceAccount> page, @Param("param") MdInterfaceAccount param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdInterfaceAccount getInfoById(@Param("id") String id);

}
