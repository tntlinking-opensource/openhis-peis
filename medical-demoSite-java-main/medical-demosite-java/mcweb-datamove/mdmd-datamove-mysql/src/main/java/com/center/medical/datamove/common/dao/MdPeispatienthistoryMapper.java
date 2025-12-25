package com.center.medical.datamove.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.center.medical.datamove.common.bean.model.MdPeispatienthistory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import org.apache.ibatis.annotations.Param;

/**
 * 体检者（history）表(MdPeispatienthistory)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:48:15
 */
public interface MdPeispatienthistoryMapper extends BaseMapper<MdPeispatienthistory> {

    /**
     * 分页查询[体检者（history）表]列表
     *
     * @param page  分页参数
     * @param param MdPeispatienthistory查询参数
     * @return 分页数据
     */
    IPage<MdPeispatienthistory> getPage(PageParam<MdPeispatienthistory> page, @Param("param") MdPeispatienthistory param);


}
