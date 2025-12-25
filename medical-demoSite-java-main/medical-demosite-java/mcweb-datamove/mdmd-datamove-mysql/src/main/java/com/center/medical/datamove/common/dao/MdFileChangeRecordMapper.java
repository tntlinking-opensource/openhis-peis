package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdFileChangeRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 记录有文件改变的文件目录(MdFileChangeRecord)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:46:17
 */
public interface MdFileChangeRecordMapper extends BaseMapper<MdFileChangeRecord> {

    /**
     * 分页查询[记录有文件改变的文件目录]列表
     *
     * @param page  分页参数
     * @param param MdFileChangeRecord查询参数
     * @return 分页数据
     */
    IPage<MdFileChangeRecord> getPage(PageParam<MdFileChangeRecord> page, @Param("param") MdFileChangeRecord param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    MdFileChangeRecord getInfoById(@Param("id") String id);

}
