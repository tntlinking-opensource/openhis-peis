package com.center.medical.center.qingdao.profession.service;

import lombok.SneakyThrows;

import java.util.List;

public interface ExportService {

    @SneakyThrows
    void export(List<String> patientCode);
}
