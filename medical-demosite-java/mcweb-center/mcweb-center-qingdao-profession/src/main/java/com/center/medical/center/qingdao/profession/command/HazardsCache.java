package com.center.medical.center.qingdao.profession.command;

import com.center.medical.center.qingdao.profession.entity.dto.Hazards;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 危害因素
 */
@Component
public class HazardsCache {
    private final HashMap<String, Hazards> map = new HashMap<>();
    public void set(List<Hazards> hazards) {
        for (Hazards hazard : hazards) {
            String medicalId = hazard.getMedicalId();
            map.put(medicalId, hazard);
        }
    }

    public List<Hazards> getList(List<String> medicalIds) {
        return map.entrySet().stream().filter(entry -> medicalIds.contains(entry.getKey())).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public String getCode(String jhys) {
        return getList(Arrays.asList(jhys.split(","))).stream().map(Hazards::getDictionaryCode).collect(Collectors.joining(","));
    }

}