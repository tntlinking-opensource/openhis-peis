package com.center.medical.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.pacslis.bean.model.PacsPeispatient;
import com.center.medical.statistics.bean.dto.PQTableDataDto;
import com.center.medical.statistics.bean.param.AnalyseTestParam;
import com.center.medical.statistics.bean.param.PacsQueryParam;
import com.center.medical.statistics.bean.vo.AnalysePacsVo;
import com.center.medical.statistics.bean.vo.ExportTotalVo;
import com.center.medical.statistics.bean.vo.PacsQueryVo;
import com.center.medical.statistics.dao.PacsQueryMapper;
import com.center.medical.statistics.service.PacsQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * PACS-体检者表(PacsPeispatient)服务实现类
 *
 * @author ay
 * @since 2023-09-16 16:37:08
 */
@Slf4j
@Service("pacsQueryService")
@RequiredArgsConstructor
public class PacsQueryServiceImpl extends ServiceImpl<PacsQueryMapper, PacsPeispatient> implements PacsQueryService {

    private final PacsQueryMapper pacsQueryMapper;

    /**
     * 分页查询[PACS-体检者表]列表
     *
     * @param page  分页参数
     * @param param PacsPeispatient查询参数
     * @return 分页数据
     */
    @Override
    public IPage<PacsQueryVo> getPage(PageParam<PacsQueryVo> page, PacsQueryParam param) {
        return pacsQueryMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public PacsPeispatient getInfoById(String id) {
        return pacsQueryMapper.getInfoById(id);
    }

    /**
     * 获取图表数据
     * @param param
     * @return
     */
    @Override
    public Map getTableData(PacsQueryParam param) {
        HashMap result = new HashMap();
        List<PQTableDataDto> l = pacsQueryMapper.getTableData(param);
        List<Object> legend=new ArrayList<Object>();
        List<Object> seriesLine=new ArrayList<Object>();
        List<Map<Object,Object>> seriesPie=new ArrayList<Map<Object,Object>>();
        for(int i=0;i<l.size();i++) {
            PQTableDataDto os = l.get(i);
            legend.add(os.getName());
            seriesLine.add(os.getValue());
            Map<Object,Object> m = new HashMap<Object, Object>();
            seriesPie.add(m);
            m.put("name", os.getName());
            m.put("value", os.getValue());
        }
        result.put("legend",legend);
        result.put("seriesLine",seriesLine);
        result.put("seriesPie",seriesPie);
        return result;
    }


    /**
     * 导出科室工作量统计
     * @param param
     * @return
     */
    @Override
    public List<PacsQueryVo> getExportData(PacsQueryParam param) {
        List<PacsQueryVo> exportData = pacsQueryMapper.getExportData(param);
        for (int i = 0; i < exportData.size(); i++) {
            PacsQueryVo pacsQueryVo = exportData.get(i);
            pacsQueryVo.setRownum(i+1);
        }
        return exportData;
    }


    /**
     * 获取图表数据
     * @param param
     * @return
     */
    @Override
    public Map getPacsDoctorTableData(AnalyseTestParam param) {
        HashMap result = new HashMap();
        //柱状图数据
        List<Object>series=new ArrayList<Object>();
        List<Object> xAxis=new ArrayList<Object>();

        List<PQTableDataDto> l = pacsQueryMapper.getPacsDoctorTableData(param);
        for(int i=0;i<l.size();i++) {
            PQTableDataDto os = l.get(i);
            xAxis.add(os.getName());
            series.add(os.getValue());
        }
        result.put("series", series);
        result.put("xAxis", xAxis);
        return result;
    }


    /**
     * 导出工作量统计
     * @param param
     * @return
     */
    @Override
    public List<AnalysePacsVo> exportWorkData(AnalyseTestParam param) {
        List<AnalysePacsVo> list = pacsQueryMapper.exportWorkData(param);
        //设置属性
        Calendar c = Calendar.getInstance();
        int i = 1;
        for (AnalysePacsVo vo : list) {
            vo.setRownum(i);
            //设置星期几
            if (ObjectUtils.isNotEmpty(vo.getRummagerTime())) {
                c.setTime(vo.getRummagerTime());
                int dayForWeek = 0;
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    dayForWeek = 7;
                } else {
                    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
                }
                vo.setDayForWeek(dayForWeek);
            }
            i++;
        }
        return list;
    }


    /**
     * 导出工作量统计总计
     * @param param
     * @return
     */
    @Override
    public List<ExportTotalVo> exportTotal(AnalyseTestParam param) {
        List<ExportTotalVo> list = pacsQueryMapper.exportTotal(param);
        //设置属性
        int i = 1;
        for (ExportTotalVo vo : list) {
            vo.setRownum(i);
            i++;
        }
        return list;
    }
}

