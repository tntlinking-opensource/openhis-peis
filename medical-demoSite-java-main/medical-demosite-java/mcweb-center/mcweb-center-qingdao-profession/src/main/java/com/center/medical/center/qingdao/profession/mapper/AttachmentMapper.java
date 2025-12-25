package com.center.medical.center.qingdao.profession.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.center.qingdao.profession.entity.dto.Attachment;
import com.center.medical.center.qingdao.profession.entity.dto.PageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * JC附件(Attachment)表数据库访问层
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:53
 */
@Mapper
@Component
public interface AttachmentMapper extends BaseMapper<Attachment> {

    /**
     * 分页查询[JC附件]列表
     *
     * @param page  分页参数
     * @param param Attachment查询参数
     * @return 分页数据
     */
    IPage<Attachment> getList(PageParam<Attachment> page, @Param("param") Attachment param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Attachment getInfoById(@Param("id") String id);

}
