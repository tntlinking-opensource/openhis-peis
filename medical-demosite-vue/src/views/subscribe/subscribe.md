### 体检预约 src/views/subscribe

- 预约管理 /appointment_management/index.vue
  > 预约管理列表 -添加(subscribe:appointmentManagement:add) -删除(subscribe:appointmentManagement:remove) 
                -编辑(basis:appointmentManagement:edit)
- 预约审核 /appointment_review/index.vue
  > 预约审核 -通过(subscribe:appointmentReview:approved) -不通过(subscribe:appointmentReview:approved) -查询
- 我的预约 /my_appointment/index.vue
  > 排检列表 -添加(basis:myAppointment:add) -删除(basis:myAppointment:remove) -编辑(basis:myAppointment:edit)
- 预约详情 /appointment_particulars/index.vue
  > 预约详情 -团队导出(subscribe:appointmentParticulars:teamexport) -团队导出(subscribe:appointmentParticulars:personalexport)
              -日期导出(subscribe:appointmentParticulars:personalexport) -团队日期导出(subscribe:appointmentParticulars:teamdateexport)
- 项目预约明细 /appointment_details/index.vue
  > 预约明细列表 -导出(subscribe:appointmentDetails:export)