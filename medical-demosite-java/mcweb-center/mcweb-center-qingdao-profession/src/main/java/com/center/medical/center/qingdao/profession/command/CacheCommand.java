package com.center.medical.center.qingdao.profession.command;

import cn.hutool.json.JSONUtil;
import com.center.medical.center.qingdao.profession.entity.dto.BaseZoneDto;
import com.center.medical.center.qingdao.profession.entity.persistent.BaseZone;
import com.center.medical.center.qingdao.profession.entity.persistent.Nation;
import com.center.medical.center.qingdao.profession.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CacheCommand implements ApplicationRunner {
    private final HazardsCache hazardsCache;
    private final HazardsRepository hazardsRepository;
    private final NationRepository nationRepository;
    private final NationCache nationCache;
    private final BaseZoneRepository baseZoneRepository;
    private final BaseZoneQueryRepository baseZoneQueryRepository;
    private final ZoneCache zoneCache;
    private final DictionaryCache dictionaryCache;
    private final SpecialHazardReferenceDao specialHazardReferenceDao;
    private final SpecialHazardReferenceCache specialHazardReferenceCache;
    private final CheckItemRepository checkItemRepository;
    private final CheckItemReferenceCache checkItemReferenceCache;

    public CacheCommand(HazardsCache hazardsCache, HazardsRepository hazardsRepository, NationRepository nationRepository, NationCache nationCache, BaseZoneRepository baseZoneRepository, BaseZoneQueryRepository baseZoneQueryRepository, ZoneCache zoneCache, DictionaryCache dictionaryCache, SpecialHazardReferenceDao specialHazardReferenceDao, SpecialHazardReferenceCache specialHazardReferenceCache, CheckItemRepository checkItemRepository, CheckItemReferenceCache checkItemReferenceCache) {
        this.hazardsCache = hazardsCache;
        this.hazardsRepository = hazardsRepository;
        this.nationRepository = nationRepository;
        this.nationCache = nationCache;
        this.baseZoneRepository = baseZoneRepository;
        this.baseZoneQueryRepository = baseZoneQueryRepository;
        this.zoneCache = zoneCache;
        this.dictionaryCache = dictionaryCache;
        this.specialHazardReferenceDao = specialHazardReferenceDao;
        this.specialHazardReferenceCache = specialHazardReferenceCache;
        this.checkItemRepository = checkItemRepository;
        this.checkItemReferenceCache = checkItemReferenceCache;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Nation> nations = nationRepository.findAll();
        nationCache.set(nations);
        List<BaseZone> zones = baseZoneRepository.findAll();
        for (BaseZone zone : zones) {
            BaseZoneDto zoneDto = baseZoneQueryRepository.getByZoneCode(zone.getZoneCode());
            log.info("zoneDto = " + JSONUtil.toJsonStr(zoneDto));
            zoneCache.set(zone, zoneDto);
        }
        dictionaryCache.set();
        specialHazardReferenceCache.setSpecialHazardReferences(specialHazardReferenceDao.listAll());
        checkItemReferenceCache.setCheckItemReferences(checkItemRepository.listAll());
    }
}
