### 财务管理 src/views/finance

- 体检卡管理 /experience_card_management
  - 体检卡办理 /experience_card/index.vue
    > 体检卡办理 -发卡(finance:management:experience_card:sendcards) -编辑(finance:management:experience_card:edit) -积分充值(finance:management:experience_card:rechargePoints) -删除(finance:management:experience_card:delete) -导出(finance:management:experience_card:export)
  - 卡充值 /card_recharge/index.vue
    > 卡充值 -充值(finance:management:card_recharge:recharge) -消费(finance:management:card_recharge:consumption) -导出(finance:management:card_recharge:export)
  - 卡消费明细 /card_consumption/index.vue'
  > 卡消费明细
- 体检结账单 /inspect_accounts/index.vue
  >体检结账单 -禁检(finance:inspectAccounts:header:disable) -收费导出(finance:inspectAccounts:middleRight:disable) -体检导出(finance:inspectAccounts:middleLeft:export) -发卡(finance:inspectAccounts:middleLeft:sendCards) -体检禁检(finance:inspectAccounts:middleLeft:disable) -已结账(finance:inspectAccounts:middleLeft:closing) -未检禁检(finance:inspectAccounts:middleLeft:unchecked)
- 平安对账单 /safety_bill/index.vue
  > 平安对账单 导出(finance:safetyBill:export)
- 团体结算 /team_charge/index.vue
  > 团体结算 结算(finance:teamCharge:refund) 导出(finance:teamCharge:export)
- 发票管理 /control_over_invoices/index.vue
  > 发票管理 -新增(finance:controlOverInvoices:add) -编辑(finance:controlOverInvoices:edit) -删除(finance:controlOverInvoices:remove) -复制(finance:controlOverInvoices:copy) -审核(finance:controlOverInvoices:examine) -审核不通过(finance:controlOverInvoices:examineToFalse) -反审核(finance:controlOverInvoices:unexamine) -出票(finance:controlOverInvoices:ticketing) -换票申请(finance:controlOverInvoices:changeTicketsApplication) -换票撤回(finance:controlOverInvoices:changeTicketsBack) -换票审核(finance:controlOverInvoices:changeTicketsExamine) -换票反审核(finance:controlOverInvoices:changeTicketsUnexamine) -换票(finance:controlOverInvoices:changeTickets) -导出(finance:controlOverInvoices:export)
- 销售提成核算 /sales_accounting
  - 销售团检统计 /payment_by_salesperson/index.vue
    > 销售团检统计 -导出(finance:salesAccounting:salesStatistics:export)
  - 团检订单折扣 /order_discount/index.vue
    > 团检订单折扣 -查看(finance:salesAccounting:orderDiscount:query)
  - 客服销售统计 /customer_statistics/index.vue
    > 客服销售统计 -导出(finance:salesAccounting:customerStatistics:export)
  - 个检销售统计 /individual_statistics/index.vue
    > 个检销售统计 -导出(finance:salesAccounting:individualStatistics:export) -按天导出(finance:salesAccounting:individualStatistics:exportDate)
  - 销售员回款 /payment_by_salesperson/index.vue
    > 销售员回款 -财务录入(finance:salesAccounting:paymentBySalesperson:entry) -编辑(finance:salesAccounting:paymentBySalesperson:edit) -查看(finance:salesAccounting:paymentBySalesperson:query)