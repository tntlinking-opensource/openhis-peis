package com.center.medical.data.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;

import com.center.medical.data.bean.model.ZyFhclGr;
import com.center.medical.data.bean.param.ZyFhclGrPageParam;
import com.center.medical.data.bean.vo.AllPersonalTypeVo;
import com.center.medical.data.bean.vo.ProtectiveEquipmentVo;
import com.center.medical.data.bean.vo.ZyFhclGrPageVo;
import org.apache.ibatis.annotations.Param;

/**
 * JC个人防护用品(MdZyFhclGr)数据库访问层
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
public interface ZyFhclGrMapper extends BaseMapper<ZyFhclGr> {

    /**
     * 分页查询[JC个人防护用品]列表
     *
     * @param page  分页参数
     * @param param MdZyFhclGr查询参数
     * @return 分页数据
     */
    IPage<ZyFhclGrPageVo> getPage(PageParam<ZyFhclGrPageVo> page, @Param("param") ZyFhclGrPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyFhclGr getInfoById(@Param("id") String id);

    /**
     * 获取防护用品分类
     * @param page
     * @param inputCode
     * @return
     */
    IPage<AllPersonalTypeVo> getAllPersonalType(PageParam<AllPersonalTypeVo> page,@Param("inputCode") String inputCode);

    /**
     * 获取防护用品
     * @param page
     * @param inputCode
     * @return
     */
    IPage<ProtectiveEquipmentVo> getProtectiveEquipment(PageParam<ProtectiveEquipmentVo> page,@Param("inputCode") String inputCode);
}
