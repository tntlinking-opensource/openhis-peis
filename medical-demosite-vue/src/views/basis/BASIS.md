### 基础数据 src/views/basis

- 平安好医生排检 /scheduling/index.vue
  > 排检列表 -添加(basis:scheduling:add) -删除(basis:scheduling:remove) -编辑(basis:scheduling:edit) -查询
- 模板维护 /stencil_maintain/index.vue
  > WORD 模板维护 -添加(basis:stencil:add) -删除(basis:stencil:remove) -编辑(basis:stencil:edit) -查询
- 职业检查设置 /occupation
  - 职业危害因素分类 hazards_category/index.vue
    > 职业危害因素分类 -添加(basis:occupation:hazardsCategory:add) -删除(basis:occupation:hazardsCategory:remove) -编辑(basis:occupation:hazardsCategory:edit) -查询
  - 职业健康危害因素 hazard_factor/index.vue
    > 职业健康危害因素(类别需要关联"职业危害因素分类") -添加(basis:occupation:hazardFactor:add) -删除(basis:occupation:hazardFactor:remove) -编辑(basis:occupation:hazardFactor:edit) -查询 -下载(basis:occupation:hazardFactor:download)
  - 省市级平台数据对照 data_comparison/index.vue
    > 省市级平台数据对照(各分类关联不同字典) -编辑字典关联(basis:occupation:dataComparison:edit) -查询
  - 危害因素重点询问症状 factor_symptom/index.vue
    > 危害因素重点询问症状(类别需要关联"职业危害因素分类") -添加(basis:occupation:factorSymptom:add) -编辑(basis:occupation:factorSymptom:edit) -删除(basis:occupation:factorSymptom:remove) -同步(basis:occupation:factorSymptom:sync)
  - 危害因素标准范围维护 factor_safeguard/index.vue
    > (关联危害因素分类表、检查项目名称表) -添加(basis:occupation:factorSafeguard:add) -编辑(basis:occupation:factorSafeguard:edit) -删除(basis:occupation:factorSafeguard:remove)
  - 职业健康症状名称维护 symptom_safeguard/index.vue
    > -添加(basis:occupation:symptomSafeguard:add) -编辑(basis:occupation:symptomSafeguard:edit) -删除(basis:occupation:symptomSafeguard:remove)
  - 职业健康检查结论 conclusion/index.vue
    > -添加(basis:occupation:conclusion:add) -编辑(basis:occupation:conclusion:edit) -删除(basis:occupation:conclusion:remove)
  - 职业病名称分类 classification/index.vue
    > -添加(basis:occupation:classification:add) -编辑(basis:occupation:classification:edit) -删除(basis:occupation:classification:remove)
  - 职业病名称维护 name_safeguard/index.vue
    > -添加(basis:occupation:nameSafeguard:add) -编辑(basis:occupation:nameSafeguard:edit) -删除(basis:occupation:nameSafeguard:remove)
  - 职业体检处理意见 deal_opinion/index.vue
    > (关联职业危害因素表、检查结论表、检查结论设置)-添加(basis:occupation:dealOpinion:add) -编辑(basis:occupation:dealOpinion:edit) -删除(basis:occupation:dealOpinion:remove) -同步(basis:occupation:dealOpinion:download) -批量添加(basis:occupation:dealOpinion:adds) -检查结论设置(basis:occupation:dealOpinion:setting)
  - 危害因素诊断标准依据维护 contrain/index.vue
    > -添加(basis:occupation:contrain:add) -编辑(basis:occupation:contrain:edit) -删除(basis:occupation:contrain:remove)
  - 个人防护用品维护 protective_equipment/index.vue
    > -添加(basis:occupation:protectiveEquipment:add) -编辑(basis:occupation:protectiveEquipment:edit) -删除(basis:occupation:protectiveEquipment:remove)
- 收费项目分类设置 /charge_category/index.vue
  > 收费项目分类设置 -添加(basis:chargeCategory:add) -删除(basis:chargeCategory:remove) -编辑(basis:chargeCategory:edit) -查询
- 收费项目设置 /charge_items/index.vue
  > - 收费项目设置 -添加(basis:chargeItems:add) -删除(basis:chargeItems:remove) -编辑(basis:chargeItems:edit) -查询 -检查项目(basis:chargeItems:query) -上传图片(basis:chargeItems:upload) -更新(basis:chargeItems:refresh) -APP 项目(basis:chargeItems:app) -取消 APP 项目(basis:chargeItems:app) -禁用(basis:chargeItems:disable) -反禁用(basis:chargeItems:disable)
  > - 添加收费项目 /charge_items/add.vue
- 检查项目类型 /basis/inspect_type/index.vue
  > 检查项目类型 -添加(basis:inspectType:add) -删除(basis:inspectType:remove) -编辑(basis:inspectType:edit) -查询
- 短信模板维护 src/views/basis/sms_template/index.vue
- > 短信模板维护 -添加(basis:smsTemplate:add) -删除(basis:smsTemplate:remove) -编辑(basis:smsTemplate:edit)
- 报告存储配置 src/views/basis/report_storage/index.vue
- > 报告存储配置 -添加(basis:reportStorage:add) -删除(basis:reportStorage:remove) -编辑(
  > basis:reportStorage:edit)
- 总检结论词维护 src/views/basis/conclusion_maintain/index.vue
  > -添加 (basis:conclusionMaintain:add) -编辑(basis:conclusionMaintain:edit) -删除(basis:conclusionMaintain:remove)
- 合并结论词维护 src/views/basis/merge_keyword/index.vue
  > -添加 (basis:mergeKeyword:add) -编辑(basis:mergeKeyword:edit) -删除(basis:mergeKeyword:remove)
- 报告发放方式 src/views/basis/issue_way/index.vue

  > 报告发放方式 -添加(basis:issueWay:add) -编辑(basis:issueWay:edit) -删除(basis:issueWay:remove)

- 收银收款方式 src/views/basis/proceeds_waylist/index

  > 报告发放方式 -添加(basis:proceedsWaylist:add) -编辑(basis:proceedsWaylist:edit) -删除(basis:proceedsWaylist:remove) 金蝶数据更新(basis:proceedsWaylist:Jd)

- 体检基础套餐维护 src/views/basis/health_small/index
  > 体检基础套餐维护 -添加(basis:healthSmall:add) -编辑(basis:healthSmall:edit) -删除(basis:healthSmall:remove) -重复(basis:healthSmall:copy) -同步(basis:healthSmall:synchronous) -活动套餐(basis:healthSmall:setActive) -取消活动套餐(basis:healthSmall:nosetActive) -推荐套餐(basis:healthSmall:setRecomend) -取消推荐套餐(basis:healthSmall:nosetRecomend) -禁用(basis:healthSmall:setBan) -反禁用(basis:healthSmall:nosetBan)
