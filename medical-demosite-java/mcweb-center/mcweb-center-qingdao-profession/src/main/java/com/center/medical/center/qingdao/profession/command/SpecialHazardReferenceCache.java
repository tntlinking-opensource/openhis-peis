package com.center.medical.center.qingdao.profession.command;

import com.center.medical.center.qingdao.profession.entity.dto.SpecialHazardReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecialHazardReferenceCache {
    private List<SpecialHazardReference> specialHazardReferences;

    public List<SpecialHazardReference> getSpecialHazardReferences() {
        return specialHazardReferences;
    }

    public void setSpecialHazardReferences(List<SpecialHazardReference> specialHazardReferences) {
        this.specialHazardReferences = specialHazardReferences;
    }
}
