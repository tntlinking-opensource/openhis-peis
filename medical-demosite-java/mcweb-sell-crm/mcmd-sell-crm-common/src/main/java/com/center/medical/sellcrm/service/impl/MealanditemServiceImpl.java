package com.center.medical.sellcrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.center.medical.common.exception.ServiceException;
import com.center.medical.common.utils.Render;
import com.center.medical.common.utils.StringUtils;
import com.center.medical.common.utils.page.PageParam;
import com.center.medical.data.bean.model.Items;
import com.center.medical.data.bean.model.Printtype;
import com.center.medical.data.dao.ItemsMapper;
import com.center.medical.data.dao.PrinttypeMapper;
import com.center.medical.sellcrm.bean.model.Comboanditem;
import com.center.medical.sellcrm.bean.model.Createmeal;
import com.center.medical.sellcrm.bean.model.Mealanditem;
import com.center.medical.sellcrm.bean.param.ApproveTjtcDataParam;
import com.center.medical.sellcrm.bean.vo.ItemDataVo;
import com.center.medical.sellcrm.bean.vo.getTjtcAndItemVo;
import com.center.medical.sellcrm.dao.ComboanditemMapper;
import com.center.medical.sellcrm.dao.CreatecomboMapper;
import com.center.medical.sellcrm.dao.CreatemealMapper;
import com.center.medical.sellcrm.dao.MealanditemMapper;
import com.center.medical.sellcrm.service.MealanditemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 普通套餐与收费项目关联表(Mealanditem)表服务实现类
 *
 * @author 路飞船长
 * @since 2022-11-21 19:41:49
 */
@Slf4j
@Service("mealanditemService")
@RequiredArgsConstructor
public class MealanditemServiceImpl extends ServiceImpl<MealanditemMapper, Mealanditem> implements MealanditemService {

    private final MealanditemMapper mealanditemMapper;
    private final ComboanditemMapper comboanditemMapper;
    private final ItemsMapper itemsMapper;
    private final PrinttypeMapper printtypeMapper;
    private final CreatemealMapper createmealMapper;
    private final CreatecomboMapper createcomboMapper;

    /**
     * 分页查询[普通套餐与收费项目关联表]列表
     *
     * @param page  分页参数
     * @param param Mealanditem查询参数
     * @return 分页数据
     */
    @Override
    public IPage<Mealanditem> getPage(PageParam<Mealanditem> page, Mealanditem param) {
        return mealanditemMapper.getPage(page, param);
    }

    /**
     * 根据主键id获取记录详情
     *
     * @param id 主键id
     * @return 详情信息
     */
    @Override
    public Mealanditem getInfoById(String id) {
        return mealanditemMapper.getInfoById(id);
    }

    /**
     * 获取收费项目
     * @param page
     * @param tcId
     * @param tcstate
     * @return
     */
    @Override
    public IPage<ItemDataVo> getItemData(PageParam<ItemDataVo> page, String tcId, String tcstate) {
        IPage<ItemDataVo> ipage = new PageParam<>();
        //查询普通套餐关联的收费项目
        if("0".equals(tcstate)){
            ipage = mealanditemMapper.getItemData(page,tcId);
        }else{
            ipage = comboanditemMapper.getItemData(page,tcId);
        }
        return ipage;
    }

    /**
     * 获取套餐下关联的收费项目
     * @param approveTjtcDataParam
     * @return
     */
    @Override
    public List<getTjtcAndItemVo> getTjtcAndItemData(ApproveTjtcDataParam approveTjtcDataParam) {
        List<getTjtcAndItemVo> data = new ArrayList();
        //套餐状态
        if("0".equals(approveTjtcDataParam.getApprtcstate())){
            //查询普通套餐关联的收费项目
            List<Mealanditem> list = mealanditemMapper.selectList(new QueryWrapper<Mealanditem>().eq("tcid", approveTjtcDataParam.getApprddId()));
            if(ObjectUtils.isNotEmpty(list)){
                //获取收费项目
                for(Mealanditem mealAndItem:list){
                    Items items = itemsMapper.selectById(mealAndItem.getSfxmid());
                    getTjtcAndItemVo tjtcAndItemVo = new getTjtcAndItemVo();
                    tjtcAndItemVo.setId(items.getId());
                    tjtcAndItemVo.setSfxmmc(items.getExamfeeitemName());
                    tjtcAndItemVo.setSfxmsrm(items.getSfxmsrm());
                    tjtcAndItemVo.setXb(String.valueOf(items.getXb()));
                    tjtcAndItemVo.setJcyy(items.getJcyy());
                    tjtcAndItemVo.setJg(items.getUnitprice());
                    tjtcAndItemVo.setBz(items.getBz());
                    tjtcAndItemVo.setTjlx(String.valueOf(items.getTjlx()));
                    tjtcAndItemVo.setSsks(items.getDepartName());
                    tjtcAndItemVo.setXmzt("0");
                    tjtcAndItemVo.setTcid(approveTjtcDataParam.getApprddId());

                    if(ObjectUtils.isNotEmpty(items.getXsdyfl())){
                        Printtype pt = printtypeMapper.getInfoById(items.getXsdyfl());
                        if(ObjectUtils.isNotEmpty(pt)){
                            tjtcAndItemVo.setPrintType(pt.getPrintName());
                            tjtcAndItemVo.setPrintShunxu(pt.getShunxu());
                        }
                    }
                    data.add(tjtcAndItemVo);
                }
            }

        }else{
            //查询最小套餐关联的收费项目
            List<Comboanditem> list = comboanditemMapper.selectList(new QueryWrapper<Comboanditem>().eq("tcid", approveTjtcDataParam.getApprddId()).eq("is_delete", 0));
            //循环
            for (Comboanditem comboanditem : list) {
                Items items = itemsMapper.selectById(comboanditem.getSfxmid());
                if(ObjectUtils.isNotEmpty(items)){
                    //设置属性
                    getTjtcAndItemVo comboAndItem = new getTjtcAndItemVo();
                    comboAndItem.setId(items.getId());
                    comboAndItem.setSfxmmc(items.getExamfeeitemName());
                    comboAndItem.setSfxmsrm(items.getSfxmsrm());
                    comboAndItem.setXb(String.valueOf(items.getXb()));
                    comboAndItem.setJcyy(items.getJcyy());
                    comboAndItem.setJg(items.getUnitprice());
                    comboAndItem.setBz(items.getBz());
                    comboAndItem.setTjlx(String.valueOf(items.getTjlx()));
                    comboAndItem.setSsks(items.getDepartName());
                    comboAndItem.setXmzt("1");

                    if(ObjectUtils.isNotEmpty(items.getXsdyfl())){
                        Printtype pt = printtypeMapper.getInfoById(items.getXsdyfl());
                        if(ObjectUtils.isNotEmpty(pt)){
                            comboAndItem.setPrintType(pt.getPrintName());
                            comboAndItem.setPrintShunxu(pt.getShunxu());
                        }
                    }
                    data.add(comboAndItem);
                }
            }
        }
        return data;
    }

    /**
     * 编辑时收费项目右边显示
     * @param id
     * @return
     */
    @Override
    public List<Map> getSfxmsData(String id) {
        //通过套餐id获取关联表
        List<Mealanditem> list = mealanditemMapper.getMAIByTcid(id);
        List data = new ArrayList();
        if(null!=list){
            //套餐表
            Createmeal meal = createmealMapper.getInfoById(id);
            //职业体检类别
            Integer zylx = meal.getZytjlb();
            //接害因素
            List<String> jhyss = meal.getJhys()==null?null:new ArrayList(Arrays.asList(meal.getJhys().split(",")));
            //获取收费项目
            for(Mealanditem mealAndItem:list){
                Map result = new HashMap();
                String itemId = mealAndItem.getSfxmid();

                if(zylx!=null && StringUtils.isNotEmpty(jhyss)){
                    //是否必检
                    Integer sfbj = createcomboMapper.getSfbj(itemId, jhyss, zylx);
                    result.put("sfbj",sfbj);

                }
                //排序
                result.put("sort", mealAndItem.getItemSort());
                //JC收费项目表
                Items items = itemsMapper.selectOne(new LambdaQueryWrapper<Items>().eq(Items::getId,itemId));
                if (ObjectUtils.isEmpty(items)){
                    throw new ServiceException("检查项目不存在!id:"+itemId);
                }
                //这里判断收费项目是否被删除会导致右侧列表卡住，一直在转圈，暂时注释掉
//                if (ObjectUtils.isNotEmpty(items.getIsDelete()) && items.getIsDelete() == 1){
//                    throw new ServiceException("检查项目:"+items.getExamfeeitemName()+"已被删除！");
//                }

                //是否复制套餐
                boolean isSystem=(mealAndItem.getIsSystem()!=null&&mealAndItem.getIsSystem().intValue()==1)?true:false;
                result.put("id", items.getId());
                result.put("sfxmmc", items.getExamfeeitemName());
                result.put("sfxmsrm", items.getSfxmsrm());
                result.put("xb", items.getXb());

                result.put("jcyy", items.getJcyy());
                result.put("jg", items.getUnitprice());
                result.put("yhj", mealAndItem.getPrice());
                result.put("bz", items.getBz());
                result.put("tjlx", items.getTjlx());

                result.put("costprice",items.getCostprice());
                result.put("ssks", items.getDepartName());
                result.put("fDiscountdisabled", items.getFDiscountdisabled());
                result.put("sfbx", mealAndItem.getSfbx());
                result.put("group", mealAndItem.getItemGroup());
                result.put("groupType", mealAndItem.getGroupType());
                result.put("typeName", Render.getGroupType(mealAndItem.getGroupType()));
                //销售打印分类
                String xsdyfl = items.getXsdyfl();
                if(xsdyfl!=null){
                    //销售打印分类设置
                    Printtype pt = printtypeMapper.getInfoById(xsdyfl);
                    if(pt!=null){
                        result.put("dysx", pt.getShunxu());
                        result.put("xsdyfl", pt.getPrintName());
                    }
                }
                if(isSystem){
                    //复制套餐变红
                    result.put("isRed", "1");
                }
                data.add(result);
            }
        }

        return data;
    }
}

