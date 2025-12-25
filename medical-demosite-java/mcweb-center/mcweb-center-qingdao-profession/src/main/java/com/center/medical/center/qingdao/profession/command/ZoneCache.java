package com.center.medical.center.qingdao.profession.command;

import com.center.medical.center.qingdao.profession.entity.dto.BaseZoneDto;
import com.center.medical.center.qingdao.profession.entity.persistent.BaseZone;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ZoneCache {
    private final Map<String, String> code = new HashMap<>();
    private final Map<String, String> name = new HashMap<>();
    private final Map<String, String> fullName = new HashMap<>();
    private final Map<String, String> qingdaoCode = new HashMap<>();
    private final Map<String, String> qingdaoName = new HashMap<>();
    private final Map<String, String> qingdaoFullName = new HashMap<>();

    public void set(BaseZone zone, BaseZoneDto zoneDto) {
        code.put(zone.getZoneCode(), zone.getZoneCode());
        name.put(zone.getZoneCode(), zone.getZoneName());
        fullName.put(zone.getZoneCode(), zoneDto.getZoneName());
        qingdaoCode.put(zone.getZoneCode(), zone.getQingdaoCode());
        qingdaoName.put(zone.getQingdaoCode(), zone.getZoneName());
        qingdaoFullName.put(zone.getQingdaoCode(), "山东省" + zoneDto.getZoneName());
    }

    public String getName(String code) {
        return name.getOrDefault(code, "");
    }

    public String getFullName(String code) {
        return fullName.getOrDefault(code, "");
    }
    public Map<String, String> getAllFullName() {
        return fullName;
    }


    public String getQingdaoCode(String code) {
        return qingdaoCode.getOrDefault(code, "");
    }


    public String getNameQingDao(String addressCode) {
        return qingdaoFullName.getOrDefault(addressCode, null);
    }
}
