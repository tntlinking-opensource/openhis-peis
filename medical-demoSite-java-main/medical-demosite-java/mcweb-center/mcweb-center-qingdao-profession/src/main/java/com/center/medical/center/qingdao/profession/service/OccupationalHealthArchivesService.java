package com.center.medical.center.qingdao.profession.service;

import cn.hutool.http.server.HttpServerResponse;
import com.center.medical.center.qingdao.profession.entity.dto.RDataDTO;
import com.center.medical.center.qingdao.profession.entity.dto.Result;
import com.center.medical.center.qingdao.profession.exception.ErrorRuntimeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface OccupationalHealthArchivesService {

    void uploadCondition(Date startDate, Date endDate);

    void logUpload(String token, String patientCode);

    void uploadByOrder(String orderNo);

    void uploadBatch();

    void uploadBatch(List<String> patientCode);

    void uploadSingle(String patientCode);

    void uploadReviewSingle(String patientCode);

    Result upload(String patientCode, String token) throws JsonProcessingException;

    @SneakyThrows(ErrorRuntimeException.class)
    @Transactional(noRollbackFor = Exception.class)
    Result upload(String patientCode, String prePatientCode, String token) throws JsonProcessingException;

    void uploadSingle(String patientCode, String token, HttpServerResponse response);

    void uploadCondition(String token, Date startDate, Date endDate, HttpServerResponse response);

    void uploadExcel(String token,String patientcode);

    void uploadExcel(int fileIdx);

    void reportCoding(String token);

    RDataDTO getUploadData(String patientCode);
}
