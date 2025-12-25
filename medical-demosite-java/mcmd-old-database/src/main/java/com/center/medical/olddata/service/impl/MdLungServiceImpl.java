package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdLung;
import com.center.medical.olddata.dao.MdLungMapper;
import com.center.medical.olddata.service.MdLungService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * KS肺功能(MdLung)服务实现类
 *
 * @author ay
 * @since 2024-06-05 16:02:44
 */
@Slf4j
@Service("mdLungService")
@RequiredArgsConstructor
public class MdLungServiceImpl extends ServiceImpl<MdLungMapper, MdLung> implements MdLungService {

    private final MdLungMapper mdLungMapper;

    /**
     * 分页查询[KS肺功能]列表
     *
     * @param page  分页参数
     * @param param MdLung查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdLung> getPage(PageParam<MdLung> page, MdLung param) {
        return mdLungMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdLung getInfoById(String id) {
        return mdLungMapper.getInfoById(id);
    }

}

