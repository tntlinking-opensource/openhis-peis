package com.center.medical.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.center.medical.bean.model.Fylx;
import com.center.medical.common.annotation.DataSource;
import com.center.medical.common.enums.DataSourceType;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.bean.model.Printtype;
import com.center.medical.data.bean.param.*;
import com.center.medical.data.bean.vo.*;
import com.center.medical.data.bean.param.*;
import com.center.medical.data.bean.vo.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 * JC收费项目表(Items)表服务接口
 *
 * @author 路飞船长
 * @since 2022-11-08 18:34:04
 */
public interface ItemsService extends IService<Items> {

    /**
     * 分页查询[JC收费项目表]列表
     *
     * @param page  分页参数
     * @param param Items查询参数
     * @return 分页数据
     */
    IPage<Items> getPage(PageParam<Items> page, ItemsParam param);

    List<ItemsDataVO> getItemsData(ItemsParam param);

    /**
     * list页面双击获取收费项目信息
     *
     * @param tcId 套餐ID
     * @return 所有数据
     */
    List<ItemsDataVO> getItemsByTcId(String tcId);

    /**
     * 根据主键id获取记录详情
     *
     * @param id id主键
     */
    Items getInfoById(String id);

    /**
     * 新增/编辑操作
     *
     * @param items
     * @return
     */
    Boolean saOrUp(Items items);

    /**
     * 删除操作
     *
     * @param ids
     * @return
     */
    Boolean delete(List<String> ids);

    /**
     * 手动计算更新按钮:更新收费项目检查次数，在收费项目表中有个字段，有多少人检了此收费项目，同步的时候，更新收费项目表的字段。手动点一下更新就行，这块功能之前没做按钮
     *
     * @return
     */
    Boolean updateJccs();

    IPage<ItemsVo> getItemVoPage(PageParam<ItemsVo> page, ItemsParam param);

    /**
     * 收费项目输入码获取收费项目及检查部位
     *
     * @param page
     * @param param
     * @return
     */
    PageParam<Items> getItems(PageParam<Items> page, GetItemsParam param);

    /**
     * 创建套餐获取基础数据收费项目
     *
     * @param page
     * @param param
     * @return
     */
    IPage<GetSfxmVo> getSfxm(PageParam<GetSfxmVo> page, GetSfxmParam param);

    /**
     * 从基础数据获取收费项目
     *
     * @param page
     * @param param
     * @return
     */
    IPage<SfxmDataVo> getSfxmData(PageParam<Items> page, SfxmDataParam param);

    /**
     * 推荐收费项目-查看套餐下的检查项目
     *
     * @param tcId
     * @return
     */
    List<ItemsByTcVo> getItemsDataByTcId(String tcId);

    /**
     * 获取销售打印分类
     *
     * @param key
     * @return
     */
    List<Printtype> getXsdyfl(String key);

    /**
     * 获取费用类型
     *
     * @param key
     * @return
     */
    List<Fylx> getFylx(String key);

    /**
     * 获取所有收费项目
     *
     * @param key
     * @return
     */
    IPage<AllItemsVo> getAllItemsData(PageParam<AllItemsVo> page, String key);

    /**
     * 返回符合条件的收费项目数据
     *
     * @param page
     * @param param
     * @return
     */
    IPage<GetShowVo> getShowData(PageParam<GetShowVo> page, GetShowParam param);

    /**
     * 上传图片
     * @param file
     * @param itemsId 绑定的收费项目ID
     * @return
     * @throws Exception
     */
    String upload(MultipartFile file, String itemsId);

    /**
     * 导出收费项目设置数据
     * @param param
     * @return
     */
    List<Items> getExportData(ItemsParam param);

    /**
     * 导入收费项目设置
     * @param list
     * @return
     */
    String importItems(List<Items> list);

    /**
     * 导出页面内容
     * @param param
     * @return
     */
    List<ItemsExportAllVo> getExportAllData(ItemsParam param);

    /**
     * 批量修改插入 对接瑞林萨尔健康管理系统
     *
     * @param entityList 实体对象集合
     */
    @DataSource(value = DataSourceType.RILIN)
    @Transactional(rollbackFor = Exception.class)
    default boolean saveOrUpdateBatchRilin(Collection<Items> entityList) {
        return saveOrUpdateBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}

