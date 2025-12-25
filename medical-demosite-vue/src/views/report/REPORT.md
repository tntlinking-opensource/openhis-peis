### 报告管理 src/views/report

- 报告打印 /report_print
  - 健康报告 report/report_print/health_report/index
    > 健康报告 -速成(report:reportPrint:healthReport:quickreport) -生成检验报告(report:reportPrint:healthReport:createIreport) -预览(report:reportPrint:healthReport:sample) -批量打印(report:reportPrint:healthReport:sampleBatch) -已打印(report:reportPrint:healthReport:backout) -下载PDF(report:reportPrint:healthReport:downloadPdf) -电子版交接(report:reportPrint:healthReport:electronicReportHandover) -隐私报告生成(report:reportPrint:healthReport:createPrivate) -终审交接(report:reportPrint:healthReport:reviewhandover) -新增(report:reportPrint:healthReport:add) -删除(report:reportPrint:healthReport:remove) -查看报告(report:reportPrint:healthReport:viewreport)
  - 职业报告 report/report_print/disease_report/index.vue
    > 职业报告 -速成(report:reportPrint:diseaseReport:quickreport) -生成检验报告(report:reportPrint:diseaseReport:createIreport) -预览(report:reportPrint:diseaseReport:sample) -批量打印(report:reportPrint:diseaseReport:sampleBatch) -已打印(report:reportPrint:diseaseReport:backout) -下载PDF(report:reportPrint:diseaseReport:downloadPdf) -查看报告(report:reportPrint:diseaseReport:viewreport)

- 健康报告审核 /report_review
  - 健康初审 report/report_review/health_first/index
    > 健康初审 -审核通过(report:reportReview:healthFirst:pass) -审核不通过(report:reportReview:healthFirst:unpass) -反审(report:reportReview:healthFirst:unaudit) -批量通过(report:reportReview:healthFirst:audit)
  - 健康复审 report/report_review/health_second/index
    > 健康复审 -审核通过(report:reportReview:healthSecond:pass) -审核不通过(report:reportReview:healthSecond:unpass) -反审(report:reportReview:healthSecond:unaudit) -批量通过(report:reportReview:healthSecond:audit)
  - 健康终审 report/report_review/health_last/index
    > 健康复审 -审核通过(report:reportReview:healthLast:pass) -审核不通过(report:reportReview:healthLast:unpass) -反审(report:reportReview:healthLast:unaudit) -批量通过(report:reportReview:healthLast:audit)

- 职业报告审核 /jobreport_review
  - 职业初审 report/jobreport_review/profession_first/index
    > 职业初审 -审核通过(report:jobreportReview:professionFirst:pass) -审核不通过(report:jobreportReview:professionFirst:unpass) -反审(report:jobreportReview:professionFirst:unaudit) -批量通过(report:jobreportReview:professionFirst:audit)
  - 职业复审 report/jobreport_review/profession_second/index
    > 职业复审 -审核通过(report:jobreportReview:professionSecond:pass) -审核不通过(report:jobreportReview:professionSecond:unpass) -反审(report:jobreportReview:professionSecond:unaudit) -批量通过(report:jobreportReview:professionSecond:audit)
  - 健康终审 report/jobreport_review/profession_last/index
    > 健康终审 -审核通过(report:jobreportReview:professionLast:pass) -审核不通过(report:jobreportReview:professionLast:unpass) -反审(report:jobreportReview:professionLast:unaudit) -批量通过(report:jobreportReview:professionLast:audit)

- 报告交接 /report_handover
  - 职业报告交接 页面逻辑描述:将终审通过的职业报告进行交接。
    > 页面用到的接口-分页查询(/report/professionAssociate/page) -职业报告交接获取体检者数据(/report/professionAssociate/getPatientData) -折线图数据(/report/professionAssociate/getChartData) -批量编辑柜子号保存(/report/professionAssociate/saveEdit) -职业报告交接反交接(/report/professionAssociate/unaudit)
     权限设置-交接(report:reportHandover:careerHandover:handover) -反交接(report:reportHandover:careerHandover:unhandover) -编辑(report:reportHandover:careerHandover:edit)
  - 健康报告交接 页面逻辑描述:将终审通过的健康报告进行交接。
    > 页面用到的接口 -分页查询(/report/healthAssociate/page) -健康报告交接获取体检者数据(/report/healthAssociate/getPatientData) -折线图数据(/report/healthAssociate/getChartData) -批量编辑柜子号保存(/report/professionAssociate/saveEdit) -健康报告交接反交接(/report/healthAssociate/unaudit)
    权限设置-交接(report:reportHandover:healthHandover:handover) -反交接(report:reportHandover:healthHandover:unhandover) -编辑(report:reportHandover:healthHandover:edit)

- 报告领取通知 /report_receive
  - 职业报告领取通知 report/report_receive/phone_notify/index
    > 职业报告领取通知 -电话通知(report:reportReceive:phoneNotify:notice) -短信通知(report:reportReceive:phoneNotify:sendMsg) -号码错误(report:reportReceive:phoneNotify:noticeerror) -未接通(report:reportReceive:phoneNotify:nonotice) -取消发送(report:reportReceive:phoneNotify:cancelsms) -查看短信(report:reportReceive:phoneNotify:viewsms) -导出Excel(report:reportReceive:phoneNotify:export) -添加(report:reportReceive:phoneNotify:add) -删除(report:reportReceive:phoneNotify:remove)
  - 健康报告领取通知 report/report_receive/health_notify/index
    > 健康报告领取通知 -电话通知(report:reportReceive:healthNotify:notice) -短信通知(report:reportReceive:healthNotify:sendMsg) -号码错误(report:reportReceive:healthNotify:noticeerror) -未接通(report:reportReceive:healthNotify:nonotice) -取消发送(report:reportReceive:healthNotify:cancelsms) -查看短信(report:reportReceive:healthNotify:viewsms) -导出Excel(report:reportReceive:healthNotify:export) -添加(report:reportReceive:healthNotify:add) -删除(report:reportReceive:healthNotify:remove)

- 报告领取 /report_collection
  - 职业报告领取 report/report_collection/get_reprot/index
    > 职业报告领取 -领取(report:reportCollection:getReprot:receive) -反领取(report:reportCollection:getReprot:unReceive) -导出Excel(report:reportCollection:getReprot:export) -添加(report:reportCollection:getReprot:add) -删除(report:reportCollection:getReprot:remove) 
  - 健康报告领取 report/report_collection/health_getreport/index
    > 健康报告领取 -领取(report:reportCollection:healthGetreport:receive) -反领取(report:reportCollection:healthGetreport:unReceive) -导出Excel(report:reportCollection:healthGetreport:export) -添加(report:reportCollection:healthGetreport:add) -删除(report:reportCollection:healthGetreport:remove) 

- 报告柜子管理 /chest_manage
  >报告柜子管理 -新增(report:chestManage:add) -编辑(report:chestManage:edit) -删除(report:chestManage:export) -导出Excel(report:chestManage:export)

- 报告审核统计 /reportreview_count
  >报告审核统计 

- 检验报告 inspect_report
  >检验报告 
  
- 职业结果告知书 print_notice
  >职业结果告知书 -查询(report:printNotice:query) -生成(report:printNotice:create) -预览(report:printNotice:preview) -下载(report:printNotice:download) -上传(report:printNotice:upload) -打印(report:printNotice:stamp)