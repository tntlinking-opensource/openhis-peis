package com.center.medical.center.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatientexamitem;
import com.center.medical.pacslis.bean.dto.LisDto;
import com.center.medical.pacslis.bean.dto.MiddleDbDto;

import java.util.List;

/**
 * 虹桥Lis
 */
public interface LisService extends IService<Peispatientexamitem> {
    /**
     * 插入中间库
     * @param middleDbDto
     */
    void save(MiddleDbDto middleDbDto);

    /**
     * 中间库删除
     * 一般情况，只有一个中间库slave
     * 但是西区自己有一个中间库，同时还要用东区的中间库。因为西区的项目，一部分西区做，一部分东区做。
     * 老系统使用一个标软开发的程序，将西区中间库的数据同步到东区中间库。但这个软件只同步新增，不同步删除。如果同一个体检号插入中间库多次，会出现重复项目。
     * 新系统不适用这个程序，直接连接两个中间库。
     * 由于老系统依赖于同步程序，老系统下线前，新系统只对东区中间库做删除操作，老系统下线后，新系统再放开对东区中间库的插入操作。
     * @param middleDbDto
     */
    void delete(MiddleDbDto middleDbDto);

    /**
     * 查询lis结果 如果没有结果应返回new ArrayList<></>()
     * @param patientcode
     * @return
     */
    List<LisDto> selectList(String patientcode);

}
