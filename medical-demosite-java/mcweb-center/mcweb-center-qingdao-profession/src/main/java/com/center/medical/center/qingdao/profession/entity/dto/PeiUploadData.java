package com.center.medical.center.qingdao.profession.entity.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class PeiUploadData implements RowMapper<PeiUploadData> {
    @Excel(name = "编号")
    private Long index;
    @Excel(name = "体检号")
    private String patientCode;
    @Excel(name = "姓名")
    private String patientName;
    @Excel(name = "单位名称（证件）")
    private String employer;
    @Excel(name = "单位名称（登记）")
    private String employers;
    @Excel(name = "体检套餐名称")
    private String tjtcmc;
    @Excel(name = "销售经理")
    private String salesManager;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "信息")
    private String message;
    @Override
    public PeiUploadData mapRow(ResultSet resultSet, int i) throws SQLException {
        PeiUploadData peiUploadData = new PeiUploadData();
        peiUploadData.setPatientCode(resultSet.getString("PATIENTCODE"));
        peiUploadData.setEmployer(resultSet.getString("LICENSE_NAME"));
        peiUploadData.setEmployers(resultSet.getString("KHDWMC"));
        peiUploadData.setSalesManager(resultSet.getString("XSJL"));
        peiUploadData.setMessage(resultSet.getString("JINAN_MSG"));
        peiUploadData.setStatus(resultSet.getString("JINAN_STATUS"));
        peiUploadData.setTjtcmc(resultSet.getString("TJTCMC"));
        peiUploadData.setPatientName(resultSet.getString("PATIENTNAME"));
        return peiUploadData;
    }
}
