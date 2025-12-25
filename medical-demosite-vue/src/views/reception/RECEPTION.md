### 前台管理 src/views/reception

- 备单 /prepare_order/index.vue
  > 备单列表 -编辑(reception:prepareOrder:edit) -刷新(reception:prepareOrder:refresh) -同步订单(reception:prepareOrder:syncOrder) -同步预约(reception:prepareOrder:syncReserve) -已备单(reception:prepareOrder:prepare) -查看套餐(reception:prepareOrder:combo) -状态重置(reception:prepareOrder:reset) -导出(reception:prepareOrder:upload) -健康导入模板下载(reception:prepareOrder:health) -职业导入模板下载(reception:prepareOrder:occupation) -导出应急导引单(reception:prepareOrder:guide) -添加(reception:prepareOrder:edit:add) -删除(reception:prepareOrder:edit:remove) -保存(reception:prepareOrder:edit:save) -添加 2(reception:prepareOrder:edit:add2) -删除 2(reception:prepareOrder:edit:remove2) -清除(reception:prepareOrder:edit:clear) -导入(reception:prepareOrder:edit:import) -预登记(reception:prepareOrder:edit:register) -来检短信提醒(reception:prepareOrder:edit:sms) -未生成(reception:prepareOrder:edit:notYet) -批量设置(reception:prepareOrder:edit:settings) -保存 2(reception:prepareOrder:edit:save2)
  - 登记 /reception/registration/index.vue
    > 登记信息 -刷新(reception:registration:refresh) -清空(reception:registration:clear) -推项(reception:registration:recommend) -完成预约(reception:registration:subscribe) -团检(reception:registration:group) -重复(reception:registration:repetition) -收费(reception:registration:charge) -退项(reception:registration:return) -速打(reception:registration:print) -客户评价(reception:registration:appraise) -新增(registration:registration:add) -删除(registration:registration:remove) -加项(registration:registration:addItem) -折扣(registration:registration:discount) -保存(registration:registration:save) -刷新 2(registration:registration:refresh) -套餐卡(registration:registration:card) -导出 Excel(registration:registration:export)
- 登记列表 /register_list/index.vue
  > 登记列表 -登记(reception:registerList:register) -批量登记(reception:registerList:lotReg) -查看(reception:registerList:view) -禁检(reception:registerList:disable) -打印(reception:registerList:print) -批量申请(reception:registerList:apply) -导出(reception:registerList:export) -删除(reception:registerList:remove)
- 收费（费用管理） /reception/proceeds/index.vue
  > 收费信息 -添加(reception:proceeds:add) -删除(reception:proceeds:remove) -刷新(reception:proceeds:refresh) -会员卡(reception:proceeds:member) -体验卡(reception:proceeds:experience) -家庭卡(reception:proceeds:family) -复查额度(reception:proceeds:review) -拆分(reception:proceeds:split) -反登记(reception:proceeds:reverse) -折扣(reception:proceeds:discount) -完成预约(reception:proceeds:subscribe) -完成登记(reception:proceeds:register)
- 加急报告 reception/urgent_report/index
  >加急报告 -加急(reception:urgentReport:edit)
- 危害因素匹配 reception/peis_harm/index
  >危害因素匹配 -匹配(reception:peisHarm:matching)
- 团检加/弃项 reception/group_check/index
  >团检加/弃项 -禁检(reception:groupCheck:jinjian) -反禁检(reception:groupCheck:nojinjian) -加项(reception:groupCheck:add) -退项(reception:groupCheck:remove) -折扣(reception:groupCheck:discount) -保存(reception:groupCheck:save)
- 职业复查 reception/review_batch/index
  >职业复查 -保存(reception:reviewBatch:save)
- 收费日报 /charge_daily/index.vue
  > - 每日客服报表统计 -今日费用结算情况(reception:chargeDaily:service:view) -导出(reception:chargeDaily:service:export) -上传(reception:chargeDaily:service:upload) -结算(reception:chargeDaily:service:settlement) -检验统收团体金蝶名(reception:chargeDaily:service:inspect) -金蝶客户数据更新(reception:chargeDaily:service:refresh)
  > - 每日记账报表统计
  > - 每日自助机通联明细 导出 Excel(reception:chargeDaily:contact:export)
- 数据召回 reception/data_recall/index
  >数据召回 -召回(reception:dataRecall:recall)