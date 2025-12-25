package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.Peispatientfeeitem;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.statistics.bean.param.DivisionWorkParam;
import com.center.medical.statistics.bean.vo.DivisionWorkVo;
import com.center.medical.statistics.dao.DivisionWorkloadMapper;
import com.center.medical.statistics.service.DivisionWorkloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 科室工作量(Peispatientfeeitem)表服务实现类
 *
 * @author ay
 * @since 2023-04-18 19:47:26
 */
@Slf4j
@Service("divisionWorkloadService")
@RequiredArgsConstructor
public class DivisionWorkloadServiceImpl extends ServiceImpl<DivisionWorkloadMapper, Peispatientfeeitem> implements DivisionWorkloadService {

    private final DivisionWorkloadMapper divisionWorkloadMapper;

    /**
     * 分页查询[体检者收费项目表]列表
     *
     * @param page  分页参数
     * @param param Peispatientfeeitem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<DivisionWorkVo> getList(PageParam<DivisionWorkVo> page, DivisionWorkParam param) {
        return divisionWorkloadMapper.getList(page, param);
    }


    /**
     * 导出科室工作量统计
     * @param param
     * @return
     */
    @Override
    public List<DivisionWorkVo> exportData(DivisionWorkParam param) {
        List<DivisionWorkVo> list = divisionWorkloadMapper.exportData(param);
        for (int i = 0; i < list.size(); i++) {
            DivisionWorkVo vo = list.get(i);
            //序号
            vo.setRownum(i+1);
        }
        return list;
    }
}

