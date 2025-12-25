package com.center.medical.datamove.oracle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.oracle.bean.model.PeispatientPhoto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者头像(PeispatientPhoto)数据库访问层
 *
 * @author ay
 * @since 2023-07-18 09:23:22
 */
public interface PeispatientPhotoMapper extends BaseMapper<PeispatientPhoto> {

    /**
     * 分页查询[体检者头像]列表
     *
     * @param page  分页参数
     * @param param PeispatientPhoto查询参数
     * @return 分页数据
     */
    IPage<PeispatientPhoto> getPage(PageParam<PeispatientPhoto> page, @Param("param") PeispatientPhoto param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    PeispatientPhoto getInfoById(@Param("id") String id);

}
