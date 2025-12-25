### 销售管理 src/views/sale

- 创建套餐   /create_offer/index.vue
  > 创建套餐 -增加(sale:createOffer:add)  -编辑(sale:createOffer:edit) -删除(sale:createOffer:delete) -查看(sale:createOffer:lookup) -重复(sale:createOffer:repeat) -导出套餐(sale:createOffer:exportOffer) -导出协议套餐(sale:createOffer:exportAgreementOffer) -禁用(sale:createOffer:disable) -反禁用(sale:createOffer:disable) -设置平安id(sale:createOffer:set)
- 订单定制  /order_customization/index.vue
  > 订单定制   -增加（sale:orderCustomization:add）-编辑（sale:orderCustomization:edit-移除（sale:orderCustomization:remove）-复制（sale:orderCustomization:copy）-查看（sale:orderCustomization:view）-提交（sale:orderCustomization:submit）-撤回（sale:orderCustomization:withdraw）-导出（sale:orderCustomization:export）-订单审批（sale:orderCustomization:approval）-订单反审（sale:orderCustomization:reapproval）-导出套餐（sale:orderCustomization:exportoffer）-导出协议套餐（sale:orderCustomization:exportprotocol）-打印套餐（sale:orderCustomization:print）-健康导入摸板下载（sale:orderCustomization:phyimport）-职业导入摸板下载（sale:orderCustomization:propinprot）-总结（sale:orderCustomization:summary）-变更（sale:orderCustomization:alter）-变更提交（sale:orderCustomization:altersubmit）-变更撤回（sale:orderCustomization:alterdraw）-变更审批（sale:orderCustomization:alterapproval）-变更反审（sale:orderCustomization:alterreapproval）-上传材料（sale:orderCustomization:upinformation）-下载材料（sale:orderCustomization:downinformation）-材料通过（sale:orderCustomization:passinformation）-材料驳回（sale:orderCustomization:informationrejection）-导入名单（sale:orderCustomization:importlist）-发放修改方式（sale:orderCustomization:send）-前台变更须知（sale:orderCustomization:changecontent）-编辑开单助理（sale:orderCustomization:editoffer）
- 订单导入  /order_customization/import_list.vue 
  > 订单定制 -新增（sale:orderCustomization:addList）-删除（sale:orderCustomization:deleteList） -保存（sale:orderCustomization:SaveList）-刷新（sale:orderCustomization:flushed） -摸板（sale:orderCustomization:downloadTemplate）  
- 合同管理 /contract_management/index.vue
  > 合同管理 -增加(sale:contract_management:add) -编辑(sale:contract_management:edit) -删除(sale:contract_management:delete) -查看(sale:contract_management:find) 下载合同(sale:contract_management:download)
- 线上备单 /order_preparation/index.vue
  > 线上备单 -编辑(sale:orderPreparation:edit) -导出Excel(sale:orderPreparation:exportExcel) -健康导入模板下载(sale:orderPreparation:healthDownload) -职业导入模板下载(sale:orderPreparation:occupationDownload) -已备单(sale:orderPreparation:preparedDocuments) -结束(sale:orderPreparation:over)
- 备忘提醒 /reminder/index.vue
  > 权限配置 -新增(sale:reminder:add) -修改(sale:reminder:edit) -删除             (sale:reminder:delete) -结束(sale:reminder:over) -查询
  > 调用接口：--------
  > 特殊功能：--------
- 危机值管理 /crisis_value/index.vue
  > 危急值 -查看(sale:crisisValue:query) -处理(sale:crisisValue:handle) -查询
- 复查单打印 /review_sheet/index.vue
  > 复查单打印 -打印(sale:reviewSheet:print) -查询
- 待领取报告 /receive_report/index.vue
  > 待领取报告 -查看(sale:receiveReport:view) -查询
- 订单总结 /order_summary/index.vue
  > 订单总结 -新增(sale:orderSummary:add) -编辑(sale:orderSummary:edit) -删除(sale:orderSummary:delete) -查看(sale:orderSummary:query) -查询
- 销售年度目标 /annual_sales_target/index.vue
  > 销售年度目标 -定制年度目标(sale:annualSalesTarget:setgoals) -编辑(sale:annualSalesTarget:edit) -查看目标(sale:annualSalesTarget:query) -导出(sale:annualSalesTarget:export) -查询
- 销售季度目标 /quarterly_sales_target/index.vue
  > 销售季度目标 -销售季度目标(sale:quarterlySalesTarget:quarterly) -编辑(sale:quarterlySalesTarget:edit) -查看目标(sale:quarterlySalesTarget:viewgoals) -导出(sale:quarterlySalesTarget:export) -查询
- 销售同期对比 /sales_comparison/index.vue
  > 销售同期对比 -查询