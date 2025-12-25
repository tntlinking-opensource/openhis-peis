package com.center.medical.app.security.adapter;

import com.center.medical.app.security.common.adapter.DefaultAuthConfigAdapter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/7/16
 */
@Component
public class ResourceServerAdapter extends DefaultAuthConfigAdapter {

    @Override
    public List<String> pathPatterns() {
        return Collections.singletonList("/api/v1/*");
    }
}
