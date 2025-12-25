package com.center.medical.olddata.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.olddata.bean.model.MdPeispatientexamitem;
import com.center.medical.olddata.dao.MdPeispatientexamitemMapper;
import com.center.medical.olddata.service.MdPeispatientexamitemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * LIS结果(LisPacs数据)(MdPeispatientexamitem)服务实现类
 *
 * @author ay
 * @since 2024-06-05 15:19:05
 */
@Slf4j
@Service("mdPeispatientexamitemService")
@RequiredArgsConstructor
public class MdPeispatientexamitemServiceImpl extends ServiceImpl<MdPeispatientexamitemMapper, MdPeispatientexamitem> implements MdPeispatientexamitemService {

    private final MdPeispatientexamitemMapper mdPeispatientexamitemMapper;

    /**
     * 分页查询[LIS结果(LisPacs数据)]列表
     *
     * @param page  分页参数
     * @param param MdPeispatientexamitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdPeispatientexamitem> getPage(PageParam<MdPeispatientexamitem> page, MdPeispatientexamitem param) {
        return mdPeispatientexamitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdPeispatientexamitem getInfoById(String id) {
        return mdPeispatientexamitemMapper.getInfoById(id);
    }

}

