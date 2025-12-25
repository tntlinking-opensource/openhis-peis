package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.TotalAddWorkParam;
import com.center.medical.statistics.bean.vo.TotalAddWorkVo;
import com.center.medical.statistics.dao.TotalAddWorkMapper;
import com.center.medical.statistics.service.TotalAddWorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 体检者收费项目表(Peispatientfeeitem)表服务实现类
 *
 * @author ay
 * @since 2023-04-19 19:06:09
 */
@Slf4j
@Service("totalAddWorkService")
@RequiredArgsConstructor
public class TotalAddWorkServiceImpl extends ServiceImpl<TotalAddWorkMapper, Peispatientfeeitem> implements TotalAddWorkService {

    private final TotalAddWorkMapper totalAddWorkMapper;

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<TotalAddWorkVo> getList(PageParam<TotalAddWorkVo> page, TotalAddWorkParam param) {
        return totalAddWorkMapper.getList(page, param);
    }


    /**
     * 导出加项统计
     * @param param
     * @return
     */
    @Override
    public List<TotalAddWorkVo> getExportData(TotalAddWorkParam param) {
        List<TotalAddWorkVo> list = totalAddWorkMapper.getExportData(param);
        for (int i = 0; i < list.size(); i++) {
            TotalAddWorkVo vo = list.get(i);
            vo.setRownum(i+1);
        }
        return list;
    }
}

