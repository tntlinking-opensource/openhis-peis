package com.center.medical.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.dao.OutsidePictrueMapper;
import com.center.medical.bean.model.OutsidePictrue;
import com.center.medical.service.OutsidePictrueService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * KS外送项目图片结果(OutsidePictrue)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:07
 */
@Slf4j
@Service("outsidePictrueService")
@RequiredArgsConstructor
public class OutsidePictrueServiceImpl extends ServiceImpl<OutsidePictrueMapper, OutsidePictrue> implements OutsidePictrueService {

    private final OutsidePictrueMapper outsidePictrueMapper;

    /**
     * 分页查询[KS外送项目图片结果]列表
     *
     * @param page  分页参数
     * @param param OutsidePictrue查询参数
     * @return 分页数据
     */
    @Override
    public IPage<OutsidePictrue> getList(PageParam<OutsidePictrue> page, OutsidePictrue param) {
        return outsidePictrueMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public OutsidePictrue getInfoById(String id) {
        return outsidePictrueMapper.getInfoById(id);
    }

    ;

}

