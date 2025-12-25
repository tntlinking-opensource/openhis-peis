package com.center.medical.center.qingdao.profession.command;

import com.center.medical.center.qingdao.profession.entity.persistent.Nation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NationCache {
    private final Map<String, Nation> hashMap = new ConcurrentHashMap<>(56);

    public void set(List<Nation> nations) {
        for (Nation nation : nations) {
            this.hashMap.put(nation.getId(), nation);
        }
    }

    public Nation get(String id) {
        Nation nation = this.hashMap.getOrDefault(id, null);
        if (nation != null) {
            if (ObjectUtils.isEmpty(nation.getName()) || ObjectUtils.isEmpty(nation.getNumberCode())) {
                return null;
            }
        }
        return nation;
    }
}