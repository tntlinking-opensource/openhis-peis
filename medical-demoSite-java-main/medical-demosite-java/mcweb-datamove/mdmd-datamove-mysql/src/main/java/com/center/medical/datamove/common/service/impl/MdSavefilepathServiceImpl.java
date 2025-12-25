package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdSavefilepathMapper;
import com.center.medical.datamove.common.bean.model.MdSavefilepath;
import com.center.medical.datamove.common.service.MdSavefilepathService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 存放文件路径表(MdSavefilepath)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:48:29
 */
@Slf4j
@Service("mdSavefilepathService")
@RequiredArgsConstructor
public class MdSavefilepathServiceImpl extends ServiceImpl<MdSavefilepathMapper, MdSavefilepath> implements MdSavefilepathService {

    private final MdSavefilepathMapper mdSavefilepathMapper;

    /**
     * 分页查询[存放文件路径表]列表
     *
     * @param page  分页参数
     * @param param MdSavefilepath查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdSavefilepath> getPage(PageParam<MdSavefilepath> page, MdSavefilepath param) {
        return mdSavefilepathMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdSavefilepath getInfoById(String id) {
        return mdSavefilepathMapper.getInfoById(id);
    }

}


