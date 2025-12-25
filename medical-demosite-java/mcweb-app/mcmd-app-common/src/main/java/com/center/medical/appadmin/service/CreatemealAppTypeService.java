package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.CreatemealAppType;
import com.center.medical.appadmin.bean.param.CMAppTypeParam;
import com.center.medical.appadmin.bean.param.CMAppTypeSaOrUpParam;
import com.center.medical.appadmin.bean.vo.GetTypeListVo;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 小程序套餐类型(CreatemealAppType)服务接口
 *
 * @author ay
 * @since 2024-06-12 17:31:42
 */
public interface CreatemealAppTypeService extends IService<CreatemealAppType> {

    /**
     * 分页查询[小程序套餐类型]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CreatemealAppType> getPage(PageParam<CreatemealAppType> page, CMAppTypeParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CreatemealAppType getInfoById(String id);

    /**
     * 删除
     * @param ids
     * @return
     */
    Boolean deleteIds(List<String> ids);

    /**
     * 添加或修改
     * @param createmealAppType
     * @return
     */
    Boolean saOrUp(CMAppTypeSaOrUpParam createmealAppType);

    /**
     * 获取类型
     * @param name
     * @return
     */
    List<GetTypeListVo> getTypeList(String name);
}

