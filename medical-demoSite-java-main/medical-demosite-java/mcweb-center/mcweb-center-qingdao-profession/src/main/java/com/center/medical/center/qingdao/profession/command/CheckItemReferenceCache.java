package com.center.medical.center.qingdao.profession.command;

import com.center.medical.center.qingdao.profession.entity.dto.CheckItemReference;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class CheckItemReferenceCache {
    private List<CheckItemReference> checkItemReferences;
}
