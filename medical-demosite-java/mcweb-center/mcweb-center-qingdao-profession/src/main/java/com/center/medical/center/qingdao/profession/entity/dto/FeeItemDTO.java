package com.center.medical.center.qingdao.profession.entity.dto;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class FeeItemDTO implements RowMapper<FeeItemDTO> {
    private String harmIds;
    //收费项目id，如果是职业项目但和基础套餐中不是同个项目，这里是套餐中的项目id
    private String itemId;
    private String depId;
    private String rummagerName;
    private String zyConclusions;
    //收费项目id，如果是职业项目但和基础套餐中不是同个项目，这里是项目原来的id
    private String originalItemId;
    //如果是补检结果，则是补检体检号
    private String patientcode;
    //1是补检 0不是补检
    private int isBujian;
    @Override
    public FeeItemDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        FeeItemDTO feeItemDTO = new FeeItemDTO();
        feeItemDTO.setHarmIds(resultSet.getString("HARM_IDS"));
        feeItemDTO.setItemId(resultSet.getString("ITEM_ID"));
        feeItemDTO.setDepId(resultSet.getString("DEP_ID"));
        feeItemDTO.setRummagerName(resultSet.getString("RUMMAGER_NAME"));
        feeItemDTO.setZyConclusions(resultSet.getString("ZY_CONCLUSIONS"));
        feeItemDTO.setOriginalItemId(resultSet.getString("ITEM_ID"));
        feeItemDTO.setPatientcode(resultSet.getString("PATIENTCODE"));
        feeItemDTO.setIsBujian(resultSet.getInt("IS_BUJIAN"));
        return feeItemDTO;
    }
}