### 统计管理 src/views/statistical

- 职业检查统计 /professionchecks
  - CDC职业病直报数据查询   /occupationaldisease/index.vue
    > CDC职业病直报数据查询 -导出('statistical:professionChecks:occupationalDisease:export')
  - 职业健康检查结果结论附表   /conclusion_table/index.vue
    > 职业健康检查结果结论附表 -导出(statistical:professionChecks:conclusionTable:export)
  - 职业健康检查拒检补检人员名单   /people_list/index.vue
    > 职业健康检查拒检补检人员名单 -导出('statistical:professionChecks:peopleList:view')
  - 职业健康检查职业禁忌证人员名单   /forbidden_list/index.vue
    > 职业健康检查职业禁忌证人员名单 -导出('statistical:professionChecks:forbiddenList:view')
  - 职业健康检查可疑职业病人名单   /possible_list/index.vue
    > 职业健康检查可疑职业病人名单 -导出('statistical:professionChecks:possibleList:view')
  - 职业健康检查结果汇总表(按单位)   /summary_table/index.vue
    >  职业健康检查结果汇总表(按单位) -导出('statistical:satisfaction:summaryTable:export')
  - 职业健康检查结果汇总表(按危害因素)   /sec_summary_table/index.vue
    >  职业健康检查结果汇总表(按危害因素) -导出('statistical:satisfaction:secSummaryTable:export')
- 体检状态明细   /group_status/index.vue
  > 体检状态明细 -导出Excel(statistical:groupStatus:export) 
- 体检状态统计   /examiner_status/index.vue
  > 体检状态统计 -导出Excel(statistical:examinerStatus:export) 
  体检费用统计 
- 收费明细   /statistical/inspect_cost/finance_count/index.vue
  > 收费明细 -导出('statistical:inspect_cost:finance_count:export')
- 费用合计   /statistical/inspect_cost/finance_total/index.vue
  > 个检收费统计 -导出('statistical:inspect_cost:finance_total:export')
- 个检收费统计   /statistical/inspect_cost/personal_total/index.vue
  > 个检收费统计 -导出('statistical:inspectCost:personalTotal:export')
- 销售个检费用   /statistical/inspect_cost/sell_personal/index.vue
  > 销售个检费用 -导出('statistical:inspectCost:sellPersonal:exportbyday')  
- 团检费用统计   /statistical/inspect_cost/sell_group/index.vue
  > 团检费用统计 -导出('statistical:inspectCost:sellGroup:export')
- 每日体检项目统计   /every_project/index.vue
  > 每日体检项目统计 -导出统计(statistical:everyProject:export) -导出人员清单(statistical:everyProject:exportPeople) 
- 每月每日套餐统计   /total_combo/index.vue
  > 每月每日套餐统计 -导出Excel(statistical:totalCombo:export) 
- 体检团体分布情况   /group_distribute/index.vue
  > 体检团体分布情况 
- 收费项目分布情况   /charge_distribute/index.vue
  > 收费项目分布情况 -导出Excel(statistical:chargeDistribute:export) 
- 工作量统计   /workload/
  - 科室工作量   /division_workload/index.vue
    > 科室工作量 -导出Excel(statistical:workload:divisionWorkload:export) 
  - 医生工作量   /division_doctor/index.vue
    > 医生工作量
  - 总检工作量   /general_checking/index.vue
    > 总检工作量
  - 加项工作量   /total_add/index.vue
    > 加项工作量 -导出Excel(statistical:workload:totalAdd:export) 
- 科室满意度排名   /statistics_items/index.vue
  > 科室满意度排名 
- 体检项目检况统计   /satisfaction_analyse/index.vue
  > 体检项目检况统计 -导出Excel(statistical:statisticsItems:export) 