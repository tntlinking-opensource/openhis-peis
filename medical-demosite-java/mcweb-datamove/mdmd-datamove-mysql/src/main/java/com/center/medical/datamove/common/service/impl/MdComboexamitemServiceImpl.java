package com.center.medical.datamove.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.datamove.common.dao.MdComboexamitemMapper;
import com.center.medical.datamove.common.bean.model.MdComboexamitem;
import com.center.medical.datamove.common.service.MdComboexamitemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.center.medical.common.utils.page.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于判断职业小结(MdComboexamitem)服务实现类
 *
 * @author ay
 * @since 2023-07-17 20:45:17
 */
@Slf4j
@Service("mdComboexamitemService")
@RequiredArgsConstructor
public class MdComboexamitemServiceImpl extends ServiceImpl<MdComboexamitemMapper, MdComboexamitem> implements MdComboexamitemService {

    private final MdComboexamitemMapper mdComboexamitemMapper;

    /**
     * 分页查询[用于判断职业小结]列表
     *
     * @param page  分页参数
     * @param param MdComboexamitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<MdComboexamitem> getPage(PageParam<MdComboexamitem> page, MdComboexamitem param) {
        return mdComboexamitemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public MdComboexamitem getInfoById(String id) {
        return mdComboexamitemMapper.getInfoById(id);
    }

}


