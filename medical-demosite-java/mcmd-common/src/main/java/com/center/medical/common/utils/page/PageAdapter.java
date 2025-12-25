package com.center.medical.common.utils.page;

import cn.hutool.core.util.PageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author: 路飞
 * @date: 2022/7/4 15:57
 * @description: 分页适配
 */
@Data
public class PageAdapter{

    private int begin;

    private int size;

    public PageAdapter(Page page) {
        int[] startEnd = PageUtil.transToStartEnd((int) page.getCurrent() - 1, (int) page.getSize());
        this.begin = startEnd[0];
        this.size = (int)page.getSize();
    }
}
