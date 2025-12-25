package com.center.medical.data.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.BaseAdiconExamItemCode;
import com.center.medical.data.bean.vo.AdiconSelectVo;
import com.center.medical.data.dao.BaseAdiconExamItemCodeMapper;
import com.center.medical.data.service.BaseAdiconExamItemCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 艾迪康项目代码表(BaseAdiconExamItemCode)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-08 18:33:28
 */
@Slf4j
@Service("baseAdiconExamItemCodeService")
@RequiredArgsConstructor
public class BaseAdiconExamItemCodeServiceImpl extends ServiceImpl<BaseAdiconExamItemCodeMapper, BaseAdiconExamItemCode> implements BaseAdiconExamItemCodeService {

    private final BaseAdiconExamItemCodeMapper baseAdiconExamItemCodeMapper;

    /**
     * 分页查询[艾迪康项目代码表]列表
     *
     * @param page  分页参数
     * @param param BaseAdiconExamItemCode查询参数
     * @return 分页数据
     */
    @Override
    public IPage<BaseAdiconExamItemCode> getList(PageParam<BaseAdiconExamItemCode> page, BaseAdiconExamItemCode param) {
        return baseAdiconExamItemCodeMapper.getList(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    @Override
    public BaseAdiconExamItemCode getInfoById(String id) {
        return baseAdiconExamItemCodeMapper.getInfoById(id);
    }


    /**
     * 通过输入码查询艾迪康项目代码表
     *
     * @param key
     * @return
     */
    @Override
    public List<AdiconSelectVo> getAdiconSelectData(String key) {
        return baseAdiconExamItemCodeMapper.getAdiconSelectData(key);
    }
}

