package com.center.medical.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.bean.model.CodeCorresponding;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 新老系统体检号对应关系(CodeCorresponding)数据库访问层
 *
 * @author ay
 * @since 2023-08-26 13:54:11
 */
public interface CodeCorrespondingMapper extends BaseMapper<CodeCorresponding> {

    /**
     * 分页查询[新老系统体检号对应关系]列表
     *
     * @param page  分页参数
     * @param param CodeCorresponding查询参数
     * @return 分页数据
     */
    IPage<CodeCorresponding> getPage(PageParam<CodeCorresponding> page, @Param("param") CodeCorresponding param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CodeCorresponding getInfoById(@Param("id") String id);

}
