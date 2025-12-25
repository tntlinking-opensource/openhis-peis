package com.center.medical.center.qingdao.profession.repository;


import com.center.medical.center.qingdao.profession.entity.dto.BaseZoneDto;

public interface BaseZoneQueryRepository {
    BaseZoneDto getByZoneCode(String zoneCode);
}
