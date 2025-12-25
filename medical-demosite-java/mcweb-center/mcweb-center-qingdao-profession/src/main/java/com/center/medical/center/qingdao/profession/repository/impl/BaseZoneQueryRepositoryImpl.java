package com.center.medical.center.qingdao.profession.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.center.medical.center.qingdao.profession.entity.dto.BaseZoneDto;
import com.center.medical.center.qingdao.profession.repository.BaseZoneQueryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
public class BaseZoneQueryRepositoryImpl implements BaseZoneQueryRepository {
    private final JdbcTemplate jdbcTemplate;

    public BaseZoneQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 将某个区域的所有上级区域名称拼接起来（包含本区域）
     * @param zoneCode
     * @return
     */
    @Override
    public BaseZoneDto getByZoneCode(String zoneCode) {
        BaseZoneDto baseZoneDto=new BaseZoneDto();
        //级别最低的地区的代码，是包含上级的代码的
        baseZoneDto.setZoneCode(zoneCode);

        String sql="select pcode zone_code,zone_name from md_base_zone where zone_code=? ";
        BaseZoneDto curBaseZone=jdbcTemplate.queryForObject(
                sql
                , new BeanPropertyRowMapper<>(BaseZoneDto.class)
                , zoneCode
        );
        String pcode=curBaseZone.getZoneCode();
        List<String> zoneNames=new LinkedList<>();
        zoneNames.add(0,curBaseZone.getZoneName());
        while(StrUtil.isNotEmpty(pcode)){
            BaseZoneDto baseZone=jdbcTemplate.queryForObject(
                    sql
                    ,new BeanPropertyRowMapper<>(BaseZoneDto.class)
                    ,pcode
            );
            pcode=null;
            if(baseZone!=null){
                pcode=baseZone.getZoneCode();
                zoneNames.add(0,baseZone.getZoneName());
            }

        }
        baseZoneDto.setZoneName(StringUtils.join(zoneNames,""));

        return baseZoneDto;
    }
}