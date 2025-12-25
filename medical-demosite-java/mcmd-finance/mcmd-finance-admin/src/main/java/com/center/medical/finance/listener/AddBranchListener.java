package com.center.medical.finance.listener;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.center.medical.bean.event.AddBranchEvent;
import com.center.medical.bean.model.DrugAndFzx;
import com.center.medical.bean.model.HarmAndFzx;
import com.center.medical.common.core.domain.entity.SysCenDep;
import com.center.medical.common.core.domain.entity.SysDept;
import com.center.medical.data.bean.model.*;
import com.center.medical.data.service.*;
import com.center.medical.sellcrm.bean.model.Comboandfzx;
import com.center.medical.sellcrm.bean.model.Createcombo;
import com.center.medical.sellcrm.service.ComboandfzxService;
import com.center.medical.sellcrm.service.CreatecomboService;
import com.center.medical.service.DrugAndFzxService;
import com.center.medical.service.HarmAndFzxService;
import com.center.medical.system.service.ISysDeptService;
import com.center.medical.system.service.SysCenDepService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 新增分中心心时，添加其他跟分中心的关联表
 */
@Slf4j
@Component("addBranchListener")
@AllArgsConstructor
public class AddBranchListener {

    private final CreatecomboService createcomboService;
    private final HarmAndFzxService harmAndFzxService;
    private final HarmService harmService;
    private final ISysDeptService sysDeptService;
    private final SysCenDepService sysCenDepService;
    private final ComboandfzxService comboandfzxService;
    private final BasconclusionService basconclusionService;
    private final ConclusionAndFzxService conclusionAndFzxService;
    private final BasexamltemService basexamltemService;
    private final ExamAndFzxService examAndFzxService;
    private final ItemsService itemsService;
    private final ItemsAndFzxService itemsAndFzxService;
    private final OccupationDrugService occupationDrugService;
    private final DrugAndFzxService drugAndFzxService;


    @Async
    @EventListener(AddBranchEvent.class)
    public void addBranchListener(AddBranchEvent event) {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        String fzxId = event.getCid();
        log.info("添加其他跟分中心的关联表：{}", JSONUtil.toJsonStr(event));
        //使用线程处理
        executorService.execute(() -> {
            addComboAndFzx(fzxId);
        });

        executorService.execute(() -> {
            addConclusionAndFzx(fzxId);
        });

        executorService.execute(() -> {
            addExamAndFzx(fzxId);
        });

        executorService.execute(() -> {
            addHarmAndFzx(fzxId);
        });

        executorService.execute(() -> {
            addItemsAndFzx(fzxId);
        });

        executorService.execute(() -> {
            addSysCenDep(fzxId);
        });

        executorService.execute(() -> {
            addDrugAndFzx(fzxId);
        });

        executorService.shutdown();
    }


    /**
     * 职业病/禁忌症 分中心
     *
     * @param fzxId
     */
    private void addDrugAndFzx(String fzxId) {
        List<DrugAndFzx> drugAndFzxList = new ArrayList<>();
        List<OccupationDrug> list = occupationDrugService.list(new LambdaQueryWrapper<OccupationDrug>().eq(OccupationDrug::getIsPublic, 1));
        for (OccupationDrug occupationDrug : list) {
            DrugAndFzx drugAndFzx = new DrugAndFzx();
            drugAndFzx.setDrugId(occupationDrug.getId());
            drugAndFzx.setFzxId(fzxId);
            drugAndFzx.setTbzt(0);
            drugAndFzxList.add(drugAndFzx);
        }
        drugAndFzxService.saveBatch(drugAndFzxList);
    }

    /**
     * 收费项目和分中心关联表
     *
     * @param fzxId
     */
    private void addItemsAndFzx(String fzxId) {
        List<ItemsAndFzx> itemsAndFzxList = new ArrayList<>();
        List<Items> list = itemsService.list(new LambdaQueryWrapper<Items>()
                .eq(Items::getIsDelete, 0).eq(Items::getIsPublic, 1));
        for (Items items : list) {
            ItemsAndFzx itemsAndFzx = new ItemsAndFzx();
            itemsAndFzx.setItemsId(items.getId());
            itemsAndFzx.setFzxId(fzxId);
            itemsAndFzx.setTbzt(0);
            itemsAndFzxList.add(itemsAndFzx);
        }
        itemsAndFzxService.saveBatch(itemsAndFzxList);
    }

    /**
     * 添加检查项目和分中心关联表
     *
     * @param fzxId
     */
    private void addExamAndFzx(String fzxId) {
        List<ExamAndFzx> examAndFzxList = new ArrayList<>();
        List<Basexamltem> list = basexamltemService.list(new LambdaQueryWrapper<Basexamltem>()
                .eq(Basexamltem::getIsDelete, 0).eq(Basexamltem::getIsPublic, 1));
        for (Basexamltem basexamltem : list) {
            ExamAndFzx examAndFzx = new ExamAndFzx();
            examAndFzx.setExamId(basexamltem.getId());
            examAndFzx.setFzxId(fzxId);
            examAndFzx.setTbzt(0);
            examAndFzxList.add(examAndFzx);
        }
        examAndFzxService.saveBatch(examAndFzxList);
    }

    /**
     * 总检结论词和分中心
     *
     * @param fzxId
     */
    private void addConclusionAndFzx(String fzxId) {
        List<ConclusionAndFzx> conclusionAndFzxList = new ArrayList<>();
        List<Basconclusion> list = basconclusionService.list(new LambdaQueryWrapper<Basconclusion>()
                .eq(Basconclusion::getIsDelete, 0).eq(Basconclusion::getIsPublic, 1));
        for (Basconclusion basconclusion : list) {
            ConclusionAndFzx conclusionAndFzx = new ConclusionAndFzx();
            conclusionAndFzx.setConclusionId(basconclusion.getId());
            conclusionAndFzx.setFzxId(fzxId);
            conclusionAndFzx.setTbzt(0);
            conclusionAndFzxList.add(conclusionAndFzx);
        }
        conclusionAndFzxService.saveBatch(conclusionAndFzxList);
    }

    /**
     * 最小套餐和分中心
     *
     * @param fzxId
     */
    private void addComboAndFzx(String fzxId) {
        List<Comboandfzx> comboandfzxList = new ArrayList<>();
        List<Createcombo> list = createcomboService.list(new LambdaQueryWrapper<Createcombo>().eq(Createcombo::getIsDelete, 0));
        for (Createcombo createcombo : list) {
            Comboandfzx comboAndFzx = new Comboandfzx();
            comboAndFzx.setTcid(createcombo.getId());
            comboAndFzx.setFzxid(fzxId);
            comboAndFzx.setTbzt(0);
            comboandfzxList.add(comboAndFzx);
        }
        comboandfzxService.saveBatch(comboandfzxList);
    }

    /**
     * 添加分中心部门表
     *
     * @param fzxId
     */
    private void addSysCenDep(String fzxId) {
        List<SysCenDep> sysCenDeps = new ArrayList<>();
        List<SysDept> list = sysDeptService.selectAllDeptList();
        for (SysDept sysDept : list) {
            String xh = ObjectUtils.isNotEmpty(sysDept.getDydXh()) ? String.valueOf(sysDept.getDydXh()) : null;
            SysCenDep sysCenDep = new SysCenDep();
            sysCenDep.setCid(fzxId);
            sysCenDep.setDid(sysDept.getDeptNo());
            sysCenDep.setXh(xh);
            sysCenDep.setSjbggs(ObjectUtils.isNotEmpty(sysDept.getSjbggs()) ? String.valueOf(sysDept.getSjbggs()) : null);
            sysCenDep.setJcdd(sysDept.getJcdd());
            sysCenDep.setJklx(sysDept.getJklx());
            sysCenDep.setKsh(sysDept.getDeptNo());
            sysCenDep.setDescription(sysDept.getDescription());
            sysCenDep.setDydXh(xh);
            sysCenDep.setDydMemo(sysDept.getDydMemo());
            sysCenDeps.add(sysCenDep);
        }
        sysCenDepService.saveBatch(sysCenDeps);

    }

    /**
     * 添加分中心时,顺便添加接害因素和分中心关联表
     *
     * @param fzxId
     */
    private void addHarmAndFzx(String fzxId) {
        List<HarmAndFzx> harmAndFzxs = new ArrayList<>();
        //暂时只插入公共的
        List<Harm> list = harmService.list(new LambdaQueryWrapper<Harm>()
                .eq(Harm::getIsDelete, 0).eq(Harm::getIsPublic, 1));
        for (Harm harm : list) {
            HarmAndFzx harmAndFzx = new HarmAndFzx();
            harmAndFzx.setFzxId(fzxId);
            harmAndFzx.setHarmId(harm.getId());
            harmAndFzx.setTbzt(0);
            harmAndFzxs.add(harmAndFzx);
        }
        //批量插入
        harmAndFzxService.saveBatch(harmAndFzxs);

    }


}
