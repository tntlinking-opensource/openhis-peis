package com.center.medical.center.qingdao.profession.entity.dto;

import com.center.medical.center.qingdao.profession.entity.persistent.Comboexamitem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@EqualsAndHashCode(callSuper = true)
@Data
public class ComboexamitemDto extends Comboexamitem implements RowMapper<ComboexamitemDto> {
    private String examitemCode;
    private String examitemName;
    private String type;
    private String examitemvaluesreport;
    private String posistive;
    private String nodule;

    @Override
    public ComboexamitemDto mapRow(ResultSet resultSet, int i) throws SQLException {
        ComboexamitemDto comboexamitemDto = new ComboexamitemDto();
        comboexamitemDto.setHarmId(resultSet.getString("HARM_ID"));
        comboexamitemDto.setExamId(resultSet.getString("EXAM_ID"));
        comboexamitemDto.setItemId(resultSet.getString("ITEM_ID"));
        comboexamitemDto.setId(resultSet.getString("ID"));
        comboexamitemDto.setComboId(resultSet.getString("COMBO_ID"));
        comboexamitemDto.setMedicalType(resultSet.getString("MEDICAL_TYPE"));
        comboexamitemDto.setKsId(resultSet.getString("KS_ID"));
        comboexamitemDto.setExamitemCode(resultSet.getString("EXAMITEM_CODE"));
        comboexamitemDto.setExamitemName(resultSet.getString("EXAMITEM_NAME"));
        comboexamitemDto.setType(resultSet.getString("type"));
        comboexamitemDto.setExamitemvaluesreport(resultSet.getString("EXAMITEMVALUESREPORT"));
        return comboexamitemDto;
    }
}