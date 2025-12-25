package com.center.medical.center.qingdao.profession.entity.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Data
@PropertySource(encoding = "UTF-8", value = "classpath:dct.properties")
public class ElectricalAudiometryProperties {
    @Value("${desc}")
    private String desc;
    @Value("${F_List_500}")
    private String fList500;
    @Value("${M_List_500}")
    private String mList500;
    @Value("${F_List_1k}")
    private String fList1K;
    @Value("${M_List_1k}")
    private String mList1K;
    @Value("${F_List_2k}")
    private String fList2K;
    @Value("${M_List_2k}")
    private String mList2K;
    @Value("${F_List_3k}")
    private String fList3K;
    @Value("${M_List_3k}")
    private String mList3K;
    @Value("${F_List_4k}")
    private String fList4K;
    @Value("${M_List_4k}")
    private String mList4K;
    @Value("${F_List_6k}")
    private String fList6K;
    @Value("${M_List_6k}")
    private String mList6K;
    @Value("${air_default}")
    private String airDefault;

    public List<ElectricalAudiometry> getFList500List() {
        return getElectricalAudiometries(fList500);
    }

    public Integer getFList500Value(Integer age) {
        return getValue(age, getFList500List());
    }

    public List<ElectricalAudiometry> getMList500List() {
        return getElectricalAudiometries(mList500);
    }

    public Integer getMList500Value(Integer age) {
        return getValue(age, getMList500List());
    }

    public List<ElectricalAudiometry> getFList1KList() {
        return getElectricalAudiometries(fList1K);
    }

    public Integer getFList1KValue(Integer age) {
        return getValue(age, getFList1KList());
    }

    public List<ElectricalAudiometry> getMList1KList() {
        return getElectricalAudiometries(mList1K);
    }

    public Integer getMList1KValue(Integer age) {
        return getValue(age, getMList1KList());
    }

    public List<ElectricalAudiometry> getFList2KList() {
        return getElectricalAudiometries(fList2K);
    }

    public Integer getFList2KValue(Integer age) {
        return getValue(age, getFList2KList());
    }

    public List<ElectricalAudiometry> getMList2KList() {
        return getElectricalAudiometries(mList2K);
    }

    public Integer getMList2KValue(Integer age) {
        return getValue(age, getMList2KList());
    }

    public List<ElectricalAudiometry> getFList3KList() {
        return getElectricalAudiometries(fList3K);
    }

    public Integer getFList3KValue(Integer age) {
        return getValue(age, getFList3KList());
    }

    public List<ElectricalAudiometry> getMList3KList() {
        return getElectricalAudiometries(mList3K);
    }

    public Integer getMList3KValue(Integer age) {
        return getValue(age, getMList3KList());
    }

    public List<ElectricalAudiometry> getFList4KList() {
        return getElectricalAudiometries(fList4K);
    }

    public Integer getFList4KValue(Integer age) {
        return getValue(age, getFList4KList());
    }

    public List<ElectricalAudiometry> getMList4KList() {
        return getElectricalAudiometries(mList4K);
    }

    public Integer getMList4KValue(Integer age) {
        return getValue(age, getMList4KList());
    }

    public List<ElectricalAudiometry> getFList6KList() {
        return getElectricalAudiometries(fList6K);
    }

    public Integer getFList6KValue(Integer age) {
        return getValue(age, getFList6KList());
    }

    public List<ElectricalAudiometry> getMList6KList() {
        return getElectricalAudiometries(mList6K);
    }

    public Integer getMList6KValue(Integer age) {
        return getValue(age, getMList6KList());
    }

    private Integer getValue(Integer age, List<ElectricalAudiometry> list) {
        return list.stream().filter(data -> age <= data.getAgeEnd() && age >= data.getAgeStart()).findFirst().map(ElectricalAudiometry::getValue).orElse(null);
    }


    private List<ElectricalAudiometry> getElectricalAudiometries(String fList500) {
        List<ElectricalAudiometry> electricalAudiometries = new ArrayList<>();
        String[] strings = fList500.split(",");
        for (String string : strings) {
            String[] strings1 = string.split(":");
            String age = strings1[0];
            String value = strings1[1];
            String[] ageRange = age.split("-");
            electricalAudiometries.add(new ElectricalAudiometry(Integer.valueOf(ageRange[0]), Integer.valueOf(ageRange[1]), Integer.valueOf(value)));
        }
        return electricalAudiometries;
    }

    @Data
    public static class ElectricalAudiometry {
        private Integer ageStart;
        private Integer ageEnd;
        private Integer value;

        public ElectricalAudiometry(Integer ageStart, Integer ageEnd, Integer value) {
            this.ageStart = ageStart;
            this.ageEnd = ageEnd;
            this.value = value;
        }
    }

}