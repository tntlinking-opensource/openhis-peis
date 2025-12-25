package com.center.medical.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.app.bean.model.AttachFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 上传文件记录表(AttachFile)表数据库访问层
 *
 * @author 路飞船长
 * @since 2023-04-06 19:06:49
 */
public interface AttachFileMapper extends BaseMapper<AttachFile> {

    /**
     * 根据id列表获取文件信息列表
     *
     * @param ids
     * @return
     */
    List<AttachFile> getByIds(@Param("ids") List<String> ids);

    /**
     * 保存
     *
     * @param attachFile
     * @return
     */
    Long save(@Param("attachFile") AttachFile attachFile);

    /**
     * 根据文件ID更新文件名称和分组
     *
     * @param attachFile 文件参数
     * @return 是否成功
     */
    Boolean updateFile(@Param("attachFile") AttachFile attachFile);

    /**
     * 根据id列表批量删除文件记录
     *
     * @param ids
     */
    void batchDeleteByIds(@Param("ids") List<String> ids);

}
