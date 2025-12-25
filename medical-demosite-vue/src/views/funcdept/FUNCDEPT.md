### 科室管理 src/views/funcdept

- 批量录入 src/views/funcdept/section_resultplan/index.vue
  > 批量录入 -主要功能:右面是添加数据，选择科室，输入体检号以后，按回车查，右侧表格会加载出数据，点保存，左侧会新增未审核数据，然后后台线程会自动将其变成已审核。
  > -保存(funcdept:sectionResultplan:add) -删除(funcdept:sectionResultplan:remove)
- 加项处理 src/views/funcdept/clinicallab/index.vue
  > 加项处理 -处理(funcdept:clinicallab:editRow) -反处理(funcdept:clinicallab:noeditRow)
- 危急值提报 funcdept/crisis_value/index
  > 危急值提报 -危急值添加(funcdept:crisisValue:add) -编辑危急值(funcdept:crisisValue:edit) -删除(funcdept:crisisValue:delete) -业务处理(funcdept:crisisValue:businessProcessin) -回访处理(funcdept:crisisValue:returnVisit) -专家处理(funcdept:crisisValue:expertHandling) -导出 Excel(funcdept:crisisValue:export)
- 检完签到 funcdept/preregistration/index
  > 检完签到 -满意度录入(funcdept:preregistration:handleMyd) -交单(funcdept:preregistration:handleJd) -弃检(funcdept:preregistration:abandonCheck) -反弃检(funcdept:preregistration:noabandonCheck) -迟检(funcdept:preregistration:lateCheck) -反迟检(funcdept:preregistration:nolateCheck) -补偿(funcdept:preregistration:suppinspection) -反补偿(funcdept:preregistration:nosuppinspection)
- 检验结果导出 src/views/funcdept/inspection_export/index.vue
  > 检验结果导出 -导出(funcdept:inspectionExport:export)
- 药房管理 /drugstore
  - 药品管理 src/views/funcdept/drugstore/drug_control/index.vue
    > 药品管理 -新增(funcdept:drugstore:drugControl:add) -编辑(funcdept:drugstore:drugControl:edit) -删除(funcdept:drugstore:drugControl:remove) -查看(funcdept:drugstore:drugControl:show)
  - 审核发药 src/views/funcdept/drugstore/drug_prescribe/index.vue
    > 审核发药
  - 售药统计 src/views/funcdept/drugstore/drug_census/index.vue
    > 售药统计-导出(funcdept:drugstore:drugCensus:editRow)
- 核酸检测 funcdept/nucleic_test/index
  > 核酸检测
- 检验样本 /sample
  - 检验加项 funcdept/sample/inspection_addition/index
    > 检验加项 -处理(funcdept:sample:InspectionAddition:handle) -反处理(funcdept:sample:InspectionAddition:nohandle)
  - 样本录入 funcdept/sample/sample_entry/index
    > 样本录入 -录入(funcdept:sample:sampleEntry:add) -删除(funcdept:sample:sampleEntry:delete) -导出 Excel(funcdept:sample:sampleEntry:export)
  - 样本交接 /sample_handover/index.vue
    > 样本交接 -样本交接(funcdept:sample:sampleHandover:handover) -删除(funcdept:sample:sampleHandover:remove) -查询
  - 不合格样本 /unqualified_samples/index.vue
    > 不合格样本 -新增(funcdept:sample:unqualifiedSamples:add) -删除(funcdept:sample:unqualifiedSamples:remove) -导出(funcdept:sample:unqualifiedSamples:export) -查询
- 科室列表 src/views/funcdept/section_list/index_left/professional_greetings 
    >图像检查  -重置(funcdept:section:reset) -队列(funcdept:section:queue) -科室(funcdept:section:section) 
              -科室加项(funcdept:section:sectionAdd) -传图(funcdept:section:transmitPic) -看图(funcdept:section:pic)
              -辅助功能(funcdept:section:aided)       
    >无图像检查 -重置(funcdept:section:reset) -队列(funcdept:section:queue) -科室(funcdept:section:section) 
              -科室加项(funcdept:section:sectionAdd) -职业性问诊(funcdept:section:transmitPic) -神经内科(funcdept:section:neurology)
               -问卷(funcdept:section:occupation) -提醒(funcdept:section:remind) -辅助功能(funcdept:section:aided)  
- 外送管理 /deliveryManage
  - 外送登记 funcdept/delivery_manage/delivery_registration/index
    > 外送登记 -登记(funcdept:deliveryManage:deliveryRegistration:add) -删除(funcdept:deliveryManage:deliveryRegistration:remove) -导出(funcdept:deliveryManage:deliveryRegistration:export)
  - 外送结果录入 funcdept/delivery_manage/delivery_enter/index (停用)
  - 外送结果上传 funcdept/delivery_manage/delivery_upload/index
    > 外送结果上传 -新增(funcdept:deliveryManage:deliveryUpload:add) -编辑(funcdept:deliveryManage:deliveryUpload:edit) -删除(funcdept:deliveryManage:deliveryUpload:remove) -查看(funcdept:deliveryManage:deliveryUpload:see)