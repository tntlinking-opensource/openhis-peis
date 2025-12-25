package com.center.medical.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.bean.model.SysBranch;
import com.center.medical.bean.param.BaseParam;
import com.center.medical.finance.bean.dto.ISDataDto;
import com.center.medical.finance.bean.dto.OVformDto;
import com.center.medical.finance.bean.dto.PhysicalExaminationDto;
import com.center.medical.finance.dao.IndexSituationMapper;
import com.center.medical.finance.dao.OutputValueReportMapper;
import com.center.medical.finance.service.OutputValueReportService;
import com.center.medical.sellcrm.bean.model.Createorder;
import com.center.medical.system.dao.SysBranchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务报表-产值报表(Createorder)表服务实现类
 *
 * @author ay
 * @since 2023-05-15 09:37:35
 */
@Slf4j
@Service("outputValueReportService")
@RequiredArgsConstructor
public class OutputValueReportServiceImpl extends ServiceImpl<OutputValueReportMapper, Createorder> implements OutputValueReportService {

    private final OutputValueReportMapper outputValueReportMapper;
    private final SysBranchMapper sysBranchMapper;

    private final IndexSituationMapper indexSituationMapper;

    /**
     * 分页查询[订单表]列表
     *
     * @return 分页数据
     */
    @Override
    public List<PhysicalExaminationDto> getList(BaseParam param) {
        List<PhysicalExaminationDto> list = new ArrayList<>();
        //分中心名字集合
        List<SysBranch> sysBranches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>()
                .eq(SysBranch::getIsShow, 1)
                .orderByAsc(SysBranch::getBranchSort));
        List<String> brand = sysBranches.stream()
                .map(SysBranch::getFzx)
                .collect(Collectors.toList());

        //体检产值
        PhysicalExaminationDto physicalExaminationDto = getPhysicalExaminationDto(brand,param);
        list.add(physicalExaminationDto);
        //疫苗产值
        PhysicalExaminationDto vaccinum = getVaccinum(brand,param);
        list.add(vaccinum);
//        //产品产值
//        PhysicalExaminationDto product = getProduct(brand,param);
//        list.add(product);
//        //中药产值
//        PhysicalExaminationDto chineseMedicine = getChineseMedicine(brand,param);
//        list.add(chineseMedicine);

        return list;
    }

//    /**
//     * 中药产值
//     * @param brand
//     * @param param
//     * @return
//     */
//    private PhysicalExaminationDto getChineseMedicine(List<String> brand,BaseParam param) {
//        PhysicalExaminationDto physicalExaminationDto = new PhysicalExaminationDto();
//        Random r = new Random(param.getStartTime().getTime());
//        physicalExaminationDto.setId(4);
//        physicalExaminationDto.setOptionsName("中药产值(万元)");
//        //总数据及增长率
//        OVformDto oVformDto = new OVformDto();
//        int valueMax = 9000;
//        int valueMin = 6000;
//        int value = r.nextInt(valueMax - valueMin + 1) + valueMin;
//        oVformDto.setValue(value);
//        int growthRateMax = 35;
//        int growthRateMin = 10;
//        int growthRate = r.nextInt(growthRateMax - growthRateMin + 1) + growthRateMin;
//        oVformDto.setGrowthRate(growthRate);
//        physicalExaminationDto.setForm(oVformDto);
//        physicalExaminationDto.setBarOptions(brand);
//        List<Integer> showdataList = new ArrayList<>();
//        int max = 12000;
//        int min = 2000;
//        for (int i = 0; i < brand.size(); i++) {
//            int num = r.nextInt(max - min + 1) + min;
//            showdataList.add(num);
//        }
//        physicalExaminationDto.setShowdata(showdataList);
//
//        if (ObjectUtils.isNotEmpty(param.getEndTime())){
//            Random r2 = new Random(param.getEndTime().getTime());
//            List<Integer> showdataList2 = new ArrayList<>();
//            for (int i = 0; i < brand.size(); i++) {
//                int num = r2.nextInt(max - min + 1) + min;
//                showdataList2.add(num);
//            }
//            physicalExaminationDto.setShowdata2(showdataList2);
//        }
//        return physicalExaminationDto;
//    }
//
//    /**
//     * 产品产值
//     * @param brand
//     * @param param
//     * @return
//     */
//    private PhysicalExaminationDto getProduct(List<String> brand, BaseParam param) {
//        PhysicalExaminationDto physicalExaminationDto = new PhysicalExaminationDto();
//        Random r = new Random(param.getStartTime().getTime());
//        physicalExaminationDto.setId(3);
//        physicalExaminationDto.setOptionsName("产品产值(万元)");
//        //总数据及增长率
//        OVformDto oVformDto = new OVformDto();
//        int valueMax = 9000;
//        int valueMin = 6000;
//        int value = r.nextInt(valueMax - valueMin + 1) + valueMin;
//        oVformDto.setValue(value);
//        int growthRateMax = 35;
//        int growthRateMin = 10;
//        int growthRate = r.nextInt(growthRateMax - growthRateMin + 1) + growthRateMin;
//        oVformDto.setGrowthRate(growthRate);
//        physicalExaminationDto.setForm(oVformDto);
//        physicalExaminationDto.setBarOptions(brand);
//        List<Integer> showdataList = new ArrayList<>();
//        int max = 12000;
//        int min = 2000;
//        for (int i = 0; i < brand.size(); i++) {
//            int num = r.nextInt(max - min + 1) + min;
//            showdataList.add(num);
//        }
//        physicalExaminationDto.setShowdata(showdataList);
//
//        if (ObjectUtils.isNotEmpty(param.getEndTime())){
//            Random r2 = new Random(param.getEndTime().getTime());
//            List<Integer> showdataList2 = new ArrayList<>();
//            for (int i = 0; i < brand.size(); i++) {
//                int num = r2.nextInt(max - min + 1) + min;
//                showdataList2.add(num);
//            }
//            physicalExaminationDto.setShowdata2(showdataList2);
//        }
//        return physicalExaminationDto;
//    }

    /**
     * 获取疫苗产值
     * @param nameList
     * @return
     */
    private PhysicalExaminationDto getVaccinum(List<String> nameList,BaseParam param) {
        PhysicalExaminationDto physicalExaminationDto = new PhysicalExaminationDto();
        physicalExaminationDto.setId(2);
        physicalExaminationDto.setOptionsName("疫苗产值(万元)");
        physicalExaminationDto.setBarOptions(nameList);

        //疫苗产值 开始
        List<ISDataDto> doubleList2 = indexSituationMapper.getVaccinumValue(param.getStartTime());
        List<Double> value1 = dataSort(doubleList2, nameList);
        physicalExaminationDto.setShowdata(value1);

        //疫苗产值 结束
        List<ISDataDto> doubleList3 = indexSituationMapper.getVaccinumValue(param.getEndTime());
        List<Double> value2 = dataSort(doubleList3, nameList);
        physicalExaminationDto.setShowdata2(value2);

        //上方总计数据
        double sum1 = value1.stream().reduce(0.0, Double::sum);
        double sum2 = value2.stream().reduce(0.0, Double::sum);
        //同比增长率
        double yearOverYearGrowthRate = (sum1 - sum2) / sum2 * 100;
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(yearOverYearGrowthRate);
        physicalExaminationDto.setForm(new OVformDto(sum1,formatted));


        return physicalExaminationDto;
    }


    /**
     * 获取体检产值
     * @return
     */
    private PhysicalExaminationDto getPhysicalExaminationDto(List<String> nameList,BaseParam param) {
        PhysicalExaminationDto physicalExaminationDto = new PhysicalExaminationDto();
        physicalExaminationDto.setId(1);
        physicalExaminationDto.setOptionsName("体检产值(不含疫苗)（万元）");
        physicalExaminationDto.setBarOptions(nameList);


        //产值不含疫苗 开始
        List<ISDataDto> doubleList2 = indexSituationMapper.getOutputValue(param.getStartTime(), 0);
        List<Double> value1 = dataSort(doubleList2, nameList);
        physicalExaminationDto.setShowdata(value1);


        //产值不含疫苗 结束
        List<ISDataDto> doubleList3 = indexSituationMapper.getOutputValue(param.getEndTime(), 0);
        List<Double> value2 = dataSort(doubleList3, nameList);
        physicalExaminationDto.setShowdata2(value2);

        //上方总计数据
        double sum1 = value1.stream().reduce(0.0, Double::sum);
        double sum2 = value2.stream().reduce(0.0, Double::sum);
        //同比增长率
        double yearOverYearGrowthRate = (sum1 - sum2) / sum2 * 100;
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(yearOverYearGrowthRate);
        physicalExaminationDto.setForm(new OVformDto(sum1,formatted));


        return physicalExaminationDto;
    }


    /**
     * 数据排序
     * @param list 需要排序的数据
     * @param nameList 排序标准
     * @return
     */
    private List<Double> dataSort(List<ISDataDto> list,List<String> nameList) {
        //找到了就添加值，每找到就添加0
        List<Double> data = new ArrayList<>();
        for (String name : nameList) {
            boolean found = false;
            for (ISDataDto dto : list) {
                if (dto.getFzx().equals(name)) {
                    data.add(dto.getValue());
                    found = true;
                    break;
                }
            }
            if (!found) {
                data.add(0d);
            }
        }
        return data;
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Createorder getInfoById(String id) {
        return outputValueReportMapper.getInfoById(id);
    }

    /**
     * 获取体检产值
     * @param param
     * @return
     */
    @Override
    public PhysicalExaminationDto getInspectOutputValue(BaseParam param) {
        //分中心名字集合
        List<SysBranch> sysBranches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>()
                .eq(SysBranch::getIsShow, 1)
                .orderByAsc(SysBranch::getBranchSort));
        List<String> brand = sysBranches.stream()
                .map(SysBranch::getFzx)
                .collect(Collectors.toList());
        //体检产值
        PhysicalExaminationDto physicalExaminationDto = getPhysicalExaminationDto(brand,param);
        return physicalExaminationDto;
    }

    /**
     * 获取疫苗产值
     * @param param
     * @return
     */
    @Override
    public PhysicalExaminationDto getVaccinesOutputValue(BaseParam param) {
        //分中心名字集合
        List<SysBranch> sysBranches = sysBranchMapper.selectList(new LambdaQueryWrapper<SysBranch>()
                .eq(SysBranch::getIsShow, 1)
                .orderByAsc(SysBranch::getBranchSort));
        List<String> brand = sysBranches.stream()
                .map(SysBranch::getFzx)
                .collect(Collectors.toList());
        //疫苗产值
        PhysicalExaminationDto dto = getVaccinum(brand,param);
        return dto;
    }
}

