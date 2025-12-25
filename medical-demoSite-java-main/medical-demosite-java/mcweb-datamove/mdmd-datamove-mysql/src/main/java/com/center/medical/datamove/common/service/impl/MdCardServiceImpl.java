package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdCardMapper;
import com.center.medical.datamove.common.bean.model.MdCard;
import com.center.medical.datamove.common.service.MdCardService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 体检卡(MdCard)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:14
 */
@Slf4j
@Service("mdCardService")
@RequiredArgsConstructor
public class MdCardServiceImpl extends ServiceImpl<MdCardMapper, MdCard> implements MdCardService {

    private final MdCardMapper mdCardMapper;

    /**
     * 分页查询[体检卡]列表
     *
     * @param page  分页参数
     * @param param MdCard查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdCard> getPage(PageParam<MdCard> page, MdCard param) {
        return mdCardMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdCard getInfoById(String id) {
        return mdCardMapper.getInfoById(id);
    }

}


