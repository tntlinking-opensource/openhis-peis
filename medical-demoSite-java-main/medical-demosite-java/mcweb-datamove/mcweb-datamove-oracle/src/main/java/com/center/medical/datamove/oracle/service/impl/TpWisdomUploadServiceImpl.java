package com.center.medical.datamove.oracle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.oracle.dao.TpWisdomUploadMapper;
import com.center.medical.datamove.oracle.bean.model.TpWisdomUpload;
import com.center.medical.datamove.oracle.service.TpWisdomUploadService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 长沙-株洲智慧医疗对接(TpWisdomUpload)服务实现类
 *
 * @author ay
 * @since 2023-07-18 09:25:38
 */
@Slf4j
@Service("tpWisdomUploadService")
@RequiredArgsConstructor
public class TpWisdomUploadServiceImpl extends ServiceImpl<TpWisdomUploadMapper, TpWisdomUpload> implements TpWisdomUploadService {

    private final TpWisdomUploadMapper tpWisdomUploadMapper;

    /**
     * 分页查询[长沙-株洲智慧医疗对接]列表
     *
     * @param page  分页参数
     * @param param TpWisdomUpload查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TpWisdomUpload> getPage(PageParam<TpWisdomUpload> page, TpWisdomUpload param) {
        return tpWisdomUploadMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public TpWisdomUpload getInfoById(String id) {
        return tpWisdomUploadMapper.getInfoById(id);
    }

}


