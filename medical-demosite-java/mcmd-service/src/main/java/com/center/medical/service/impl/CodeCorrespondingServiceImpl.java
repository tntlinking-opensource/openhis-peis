package com.center.medical.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.CodeCorresponding;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.dao.CodeCorrespondingMapper;
import com.center.medical.service.CodeCorrespondingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 新老系统体检号对应关系(CodeCorresponding)服务实现类
 *
 * @author ay
 * @since 2023-08-26 13:54:12
 */
@Slf4j
@Service("codeCorrespondingService")
@RequiredArgsConstructor
public class CodeCorrespondingServiceImpl extends ServiceImpl<CodeCorrespondingMapper, CodeCorresponding> implements CodeCorrespondingService {

    private final CodeCorrespondingMapper codeCorrespondingMapper;

    /**
     * 分页查询[新老系统体检号对应关系]列表
     *
     * @param page  分页参数
     * @param param CodeCorresponding查询参数
     * @return 分页数据
     */
    @Override
    public IPage<CodeCorresponding> getPage(PageParam<CodeCorresponding> page, CodeCorresponding param) {
        return codeCorrespondingMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public CodeCorresponding getInfoById(String id) {
        return codeCorrespondingMapper.getInfoById(id);
    }

    /**
     * 批量插入体检号
     * @param codeCorrespondingList
     */
    @Override
    public void saOrUpList(List<CodeCorresponding> codeCorrespondingList) {
        saveOrUpdateBatch(codeCorrespondingList);
    }
}

