package com.center.medical.appadmin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.appadmin.bean.model.IndexImg;
import com.center.medical.appadmin.bean.param.IndexImgParam;
import com.center.medical.appadmin.dao.IndexImgMapper;
import com.center.medical.appadmin.service.IndexImgService;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主页轮播图(IndexImg)服务实现类
 *
 * @author ay
 * @since 2024-03-19 12:00:06
 */
@Slf4j
@Service("indexImgService")
@RequiredArgsConstructor
public class IndexImgServiceImpl extends ServiceImpl<IndexImgMapper, IndexImg> implements IndexImgService {

    private final IndexImgMapper indexImgMapper;

    /**
     * 分页查询[主页轮播图]列表
     *
     * @param page  分页参数
     * @param param IndexImg查询参数
     * @return 分页数据
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public IPage<IndexImg> getPage(PageParam<IndexImg> page, IndexImgParam param) {
        return indexImgMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键imgId
     * @return 详情信息
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public IndexImg getInfoById(String id) {
        return indexImgMapper.getInfoById(id);
    }

    /**
     * 添加或修改
     * @param indexImg
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public Boolean saOrUp(IndexImg indexImg) {
        return saveOrUpdate(indexImg);
    }


    /**
     * 删除主页轮播图
     * @param imgIds
     * @return
     */
    @Override
    @DataSource(value = DataSourceType.APPLET)
    public Boolean deleteImg(List<String> imgIds) {
        return removeByIds(imgIds);
    }
}

