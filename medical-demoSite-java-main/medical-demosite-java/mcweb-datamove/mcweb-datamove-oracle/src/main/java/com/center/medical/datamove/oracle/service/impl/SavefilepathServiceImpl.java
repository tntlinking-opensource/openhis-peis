package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.SavefilepathMapper;
import com.center.medical.datamove.oracle.bean.model.Savefilepath;
import com.center.medical.datamove.oracle.service.SavefilepathService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 存放文件路径表(Savefilepath)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:24:38
 */
@Slf4j
@Service("savefilepathService")
@RequiredArgsConstructor
public class SavefilepathServiceImpl extends ServiceImpl<SavefilepathMapper, Savefilepath> implements SavefilepathService {

    private final SavefilepathMapper savefilepathMapper;

    /**
     * 分页查询[存放文件路径表]列表
     *
     * @param page  分页参数
     * @param param Savefilepath查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Savefilepath> getPage(PageParam<Savefilepath> page, Savefilepath param) {
        return savefilepathMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Savefilepath getInfoById(String id) {
        return savefilepathMapper.getInfoById(id);
    }

}


