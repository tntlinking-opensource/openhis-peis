package com.center.medical.appadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.appadmin.bean.model.IndexImg;
import com.center.medical.appadmin.bean.param.IndexImgParam;
import com.center.medical.common.utils.page.PageParam;

import java.util.List;

/**
 * 主页轮播图(IndexImg)服务接口
 *
 * @author ay
 * @since 2024-03-19 12:00:06
 */
public interface IndexImgService extends IService<IndexImg> {

    /**
     * 分页查询[主页轮播图]列表
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 分页数据
     */
    IPage<IndexImg> getPage(PageParam<IndexImg> page, IndexImgParam param);

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键imgId
     * @return 详情信息
     */
    IndexImg getInfoById(String id);

    /**
     * 添加或修改
     * @param indexImg
     * @return
     */
    Boolean saOrUp(IndexImg indexImg);

    /**
     * 删除主页轮播图
     * @param imgIds
     * @return
     */
    Boolean deleteImg(List<String> imgIds);
}

