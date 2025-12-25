package com.center.medical.center.qingdao.profession.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.date.DateUtil;
import com.center.medical.center.qingdao.profession.entity.dto.PeiUploadData;
import com.center.medical.center.qingdao.profession.entity.properties.ConfigProperties;
import com.center.medical.center.qingdao.profession.repository.PeisStateQueryRepository;
import com.center.medical.center.qingdao.profession.repository.PeisStateRepository;
import com.center.medical.center.qingdao.profession.service.ExportService;
import com.google.common.collect.Lists;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExportServiceImpl implements ExportService {
    private final ConfigProperties properties;
    private final PeisStateQueryRepository peisStateQueryRepository;

    public ExportServiceImpl(ConfigProperties properties, PeisStateQueryRepository peisStateQueryRepository) {
        this.properties = properties;
        this.peisStateQueryRepository = peisStateQueryRepository;
    }

    @SneakyThrows
    @Override
    public void export(List<String> patientCode) {
        AtomicLong atomicLong = new AtomicLong();
        String pathname = properties.getResultPath() + File.separator + DateUtil.format(new Date(), "yyyyMMdd");
        File file = new File(pathname);
        if (!file.exists()) {
            file.mkdirs();
        }
        String name=pathname + File.separator + properties.getInstitutionName() + "-" + System.currentTimeMillis() + ".xls";
        @Cleanup FileOutputStream outputStream = new FileOutputStream(name);
        List<PeiUploadData> peiUploadData = new ArrayList<>();
        List<List<String>> partition = Lists.partition(patientCode, 500);
        for (List<String> strings : partition) {
            peiUploadData.addAll(peisStateQueryRepository.queryByPatientCodes(strings));
        }
        peiUploadData = peiUploadData.stream().peek(data -> {
            data.setIndex(atomicLong.incrementAndGet());
        }).collect(Collectors.toList());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), PeiUploadData.class, peiUploadData);
        workbook.write(outputStream);
        log.info("导出日志excel:"+file.getAbsolutePath());
    }
}