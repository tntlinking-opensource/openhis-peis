package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.ZyFhclGr;
import com.center.medical.data.bean.param.ZyFhclGrPageParam;
import com.center.medical.data.bean.vo.AllPersonalTypeVo;
import com.center.medical.data.bean.vo.ProtectiveEquipmentVo;
import com.center.medical.data.bean.vo.ZyFhclGrPageVo;


/**
 * JC个人防护用品(MdZyFhclGr)服务接口
 *
 * @author ay
 * @since 2023-07-17 20:54:27
 */
public interface ZyFhclGrService extends IService<ZyFhclGr> {

    /**
     * 分页查询[JC个人防护用品]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<ZyFhclGrPageVo> getPage(PageParam<ZyFhclGrPageVo> page, ZyFhclGrPageParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    ZyFhclGr getInfoById(String id);

    /**
     * 添加或更新
     * @param zyFhclGr
     * @return
     */
    boolean saOrUp(ZyFhclGr zyFhclGr);

    /**
     * 获取防护用品分类
     * @param page
     * @param inputCode
     * @return
     */
    IPage<AllPersonalTypeVo> getAllPersonalType(PageParam<AllPersonalTypeVo> page, String inputCode);

    /**
     * 获取防护用品
     * @param page
     * @param inputCode
     * @return
     */
    IPage<ProtectiveEquipmentVo> getProtectiveEquipment(PageParam<ProtectiveEquipmentVo> page, String inputCode);
}

