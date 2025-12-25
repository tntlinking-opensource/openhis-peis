package com.center.medical.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Peispatient;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.member.bean.param.PTPageParam;
import com.center.medical.member.bean.param.PTsaOrUpParam;
import com.center.medical.member.bean.vo.PreviewingTrackVo;

import java.util.List;
import java.util.Map;

/**
 * QT体检者表(Peispatient)表服务接口
 *
 * @author ay
 * @since 2023-02-10 14:25:16
 */
public interface PreviewingTrackService extends IService<Peispatient> {

    /**
    * 分页查询[QT体检者表]列表
    *
    * @param page 分页参数
    * @param param 查询参数
    * @return 分页数据
    */
    IPage<PreviewingTrackVo> getList(PageParam<PreviewingTrackVo> page, PTPageParam param);

    /**
    * 根据主键id获取记录详情
    *
    * @param id 主键id
    * @return 详情信息
    */
    Peispatient getInfoById(String id);

    /**
     * 获取开单医师
     * @param key
     * @return
     */
    List<Map<String, String>> getKdys(String key);

    /**
     * 保存
     * @param param
     * @return
     */
    Boolean saOrUp(PTsaOrUpParam param);

    /**
     * 个检预检回访导出
     * @param param
     * @return
     */
    List<PreviewingTrackVo> getExportData(PTPageParam param);
}

