package com.center.medical.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.CodeCorresponding;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 新老系统体检号对应关系(CodeCorresponding)服务接口
 *
 * @author ay
 * @since 2023-08-26 13:54:12
 */
public interface CodeCorrespondingService extends IService<CodeCorresponding> {

    /**
     * 分页查询[新老系统体检号对应关系]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<CodeCorresponding> getPage(PageParam<CodeCorresponding> page, CodeCorresponding param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    CodeCorresponding getInfoById(String id);

    /**
     * 批量插入体检号
     * @param codeCorrespondingList
     */
    void saOrUpList(List<CodeCorresponding> codeCorrespondingList);
}

