package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdPufftubeMapper;
import com.center.medical.datamove.common.bean.model.MdPufftube;
import com.center.medical.datamove.common.service.MdPufftubeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 噗噗管(MdPufftube)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:16
 */
@Slf4j
@Service("mdPufftubeService")
@RequiredArgsConstructor
public class MdPufftubeServiceImpl extends ServiceImpl<MdPufftubeMapper, MdPufftube> implements MdPufftubeService {

    private final MdPufftubeMapper mdPufftubeMapper;

    /**
     * 分页查询[噗噗管]列表
     *
     * @param page  分页参数
     * @param param MdPufftube查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPufftube> getPage(PageParam<MdPufftube> page, MdPufftube param) {
        return mdPufftubeMapper.getPage(page, param);
    }


}


