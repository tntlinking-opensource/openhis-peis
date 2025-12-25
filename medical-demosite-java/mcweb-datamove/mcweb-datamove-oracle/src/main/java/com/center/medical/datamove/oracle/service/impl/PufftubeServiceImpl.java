package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.PufftubeMapper;
import com.center.medical.datamove.oracle.bean.model.Pufftube;
import com.center.medical.datamove.oracle.service.PufftubeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * (Pufftube)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:23:41
 */
@Slf4j
@Service("pufftubeService")
@RequiredArgsConstructor
public class PufftubeServiceImpl extends ServiceImpl<PufftubeMapper, Pufftube> implements PufftubeService {

    private final PufftubeMapper pufftubeMapper;

    /**
     * 分页查询[]列表
     *
     * @param page  分页参数
     * @param param Pufftube查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Pufftube> getPage(PageParam<Pufftube> page, Pufftube param) {
        return pufftubeMapper.getPage(page, param);
    }


}


