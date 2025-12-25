package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPaPeissortexam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 平安软件-排检(MdPaPeissortexam)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:31
 */
public interface MdPaPeissortexamMapper extends BaseMapper<MdPaPeissortexam> {

    /**
     * 分页查询[平安软件-排检]列表
     *
     * @param page  分页参数
     * @param param MdPaPeissortexam查询参数
     * @return 分页数据
     */
    IPage<MdPaPeissortexam> getPage(PageParam<MdPaPeissortexam> page, @Param("param") MdPaPeissortexam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdPaPeissortexam getInfoById(@Param("id") String id);

}
