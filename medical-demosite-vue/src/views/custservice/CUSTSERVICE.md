### 客服管理 src/views/custservice

- 会员管理 /membership_management
	- 中心会员 /center_member/index.vue
		> 中心会员 -添加(custservice:membershipManagement:centerMember:add) -编辑(custservice:membershipManagement:centerMember:edit) -会员升级(custservice:membershipManagement:centerMember:memberUpgrade) -会员卡挂失(custservice:membershipManagement:centerMember:memberLossReporting) -导出(custservice:membershipManagement:centerMember:export) -查询
	- 会员积分 /member_points/index.vue
		> 会员积分 -导出(custservice:membershipManagement:memberPoints:export) -查询
	- 平台会员 /platform_member/index.vue
		> 平台会员 -查询
	- 档案合并 /file_consolidation/index.vue
		> 档案合并 -合并(custservice:membershipManagement:fileConsolidation:merge) -编辑(custservice:membershipManagement:fileConsolidation:edit) -查看(custservice:membershipManagement:fileConsolidation:query) -查询 
	- 生日关怀 /birthday_care/index.vue
		> 生日关怀 -短信回访(custservice:membershipManagement:birthdayCare:informationFollow) -查询
	- 沟通记录 /communication_record/index
		> 沟通记录 -导出(custservice:membershipManagement:communicationRecord:export) -查询
- 家庭会员 /family_members
	- 家庭会员 /family_members/index.vue
		> 家庭会员 -家庭卡办理(custservice:familyMembers:handle) -充值(custservice:familyMembers:recharge) -编辑(custservice:familyMembers:edit) -导出(custservice:familyMembers:export) -新增 -编辑家庭成员(custservice:familyMembers:dialogTable:edit) -删除家庭成员(custservice:familyMembers:dialogTable:delete) -添加支付(custservice:familyMembers:paymoney:add) -删除支付 (custservice:familyMembers:paymoney:remove) -查询
	- 家庭卡消费 /family_card_consumption/index.vue
		> 家庭卡消费   消费(custservice:familyCardConsumption:charge) -查询
	- 家庭卡消费记录 /family_card_record/index.vue
		> 家庭卡消费记录 -导出(custservice:familyCardRecord:export) -查询
	- 家庭卡生日 /family_card_birthday/index.vue
			> 家庭卡生日  -查询
- 前台满意度 /satisfaction	
	- 前台满意度 /reception_satisfaction/index.vue
		> 前台满意度 -前台满意度回访(custservice:satisfaction:receptionSatisfaction:receptionreturn)
	- 科室满意度 /department_satisfaction/index.vue	
		> 科室满意度 -科室满意度回访(custservice:satisfaction:departmentSatisfaction:departmentreturn) 
		          		-取消不满意(custservice:satisfaction:departmentSatisfaction:cancel)
						-导出(custservice:satisfaction:departmentSatisfaction:export)
	- 体检满意度 /test_satisfaction/index.vue	
		> 体检满意度 -编辑客户满意度(custservice:satisfaction:testSatisfaction:informationedit) 
		          	-查看客户评价(custservice:satisfaction:testSatisfaction:informationlookup)
					-导出(custservice:satisfaction:testSatisfaction:informationexport)
	- 满意度统计 /count_satisfaction/index.vue	
		> 满意度统计 -导出(custservice:satisfaction:countSatisfaction:informationexport)
	- 满意度回访 /return_satisfaction/index.vue	
		> 满意度回访 -不满意客户回访(custservice:satisfaction:returnSatisfaction:informationedit) 
		          	-查看非常满意(custservice:satisfaction:returnSatisfaction:informationlookup)
					-导出(custservice:satisfaction:returnSatisfaction:informationexport)
	- 不满意客户深访 /dissatisfied/index.vue
		> 不满意客户深访列表 -高级客户回访(custservice:satisfaction:dissatisfied:visit) -查看非常满意(custservice:satisfaction:dissatisfied:view) -过滤(custservice:satisfaction:dissatisfied:filter) -导出(custservice:satisfaction:dissatisfied:download)
- 回访管理 /customerservice
	- 迟补检回访 custservice/customerservice/forinspection_view/index
		> 迟补检回访 -迟补检处理(custservice:customerservice:forinspectionView:add) -编辑(custservice:customerservice:forinspectionView:edit)
		-导出(ustservice:customerservice:forinspectionView:export)
	- 预约短信回访 custservice/customerservice/record_manage/index
		> 预约短信回访 -发送编辑(custservice:customerservice:recordManage:add) -取消发送(custservice:customerservice:recordManage:cancel) -查看短信 (custservice:customerservice:forinspectionView:remove)
	- 预约来检回访 custservice/customerservice/appoint_return/index
		> 预约来检回访 -预约来捡回访(custservice:customerservice:appointReturn:add) -导出Excel(custservice:customerservice:appointReturn:export)
	- 个检预检回访 custservice/customerservice/previewing_track/index
		> 个检预检回访 -个检客户预检跟踪回访(custservice:customerservice:previewingTrack:add) -导出Excel(custservice:customerservice:previewingTrack:export)
	- 个检危机值回访 custservice/customerservice/previewing_track/index
		> 个检危机值回访 -阳性结果回访(custservice:customerservice:positiveTrack:add) -导出Excel(custservice:customerservice:positiveTrack:export)
	- 不合格样本回访 custservice/customerservice/below_sample/index
		> 不合格样本回访 -不合格样本回访(custservice:customerservice:belowSample:add) -导出Excel(custservice:customerservice:belowSample:export)
- 咨询与随访统计 custservice/consult_statistics/index
	> 咨询与随访统计 -添加(custservice:consultStatistics:add) -编辑(custservice:consultStatistics:edit) -删除(custservice:consultStatistics:remove)
- 咨询与随访 custservice/consulation/index
	> 咨询与随访 -添加(custservice:consulation:add) -编辑(custservice:consulation:edit) -删除(custservice:consulation:remove)