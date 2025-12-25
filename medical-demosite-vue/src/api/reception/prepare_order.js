// 备单  开发人：麦沃德科技 予安
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 分页查询
export function getListData(query) {
  return request({
    url: '/reception/order/page',
    method: 'get',
    params: query
  })
}
// 获取备单数量
export function getStatistics(query) {
  return request({
    url: '/reception/order/getStatistics',
    method: 'get',
    params: query
  })
}

// 获取客户单位下拉
export function getAllOrg(query) {
  return request({
    url: '/sell/customer/getAllOrg',
    method: 'get',
    params: query
  })
}
// 获取销售经理下拉
export function getAllUserSql2(query) {
  return request({
    url: '/reception/order/getAllUserSql2',
    method: 'get',
    params: query
  })
}

// 同步订单
export function getNetList(curCenterId) {
  return request({
    url: '/reception/order/getNetList/' + curCenterId,
    method: 'get',
    params: {
      curCenterId
    }
  })
}

// 已备单
export function markTbzt(params) {
  return request({
    url: '/reception/order/markTbzt',
    method: 'post',
    params
  })
}

// 状态重置
export function returnToZero(params) {
  return request({
    url: '/reception/order/returnToZero',
    method: 'post',
    params
  })
}

// 导出应急导引单
export function exportGuidanceList(params) {
  return request({
    url: '/reception/order/exportGuidanceList',
    method: 'post',
    params
  })
}

// 编辑-获取订单详情
export function editApi(query) {
  return request({
    url: '/reception/order/edit',
    method: 'get',
    params: query
  })
}
// 编辑-查看当前订单套餐
export function getGroupData(query) {
  return request({
    // url: '/reception/order/getGroupData', 不分页接口
    url: '/reception/order/getGroupDataPage',
    method: 'get',
    params: query
  })
}
// 编辑-工种信息查询
export function getBaseWorktypeSql(query) {
  return request({
    url: '/reception/order/getBaseWorktypeSql',
    method: 'get',
    params: query
  })
}
// 编辑-设置工种
export function setWorktype(query) {
  return request({
    url: '/reception/order/setWorktype',
    method: 'put',
    params: query
  })
}
// 编辑-可重复
export function setRepeated(query) {
  return request({
    url: '/reception/order/setRepeated',
    method: 'put',
    params: query
  })
}
// 编辑-获取付款方式
export function getPaywayData() {
  return request({
    url: '/reception/chargeQuery/getPaywayData',
    method: 'get',
  })
}
// 编辑-体检团体分组保存
export function saveOrUpdateGroup(data) {
  return request({
    url: '/reception/order/saveOrUpdateGroup',
    method: 'post',
    data
  })
}
// 编辑-备单状态更改(通知销售)
export function updateNotifyRemoteOrder(params) {
  return request({
    url: '/reception/order/updateNotifyRemoteOrder',
    method: 'put',
    params
  })
}

// 编辑-获取分组下相应的人员信息（右侧）
export function getPatientData(params) {
  return request({
    url: '/reception/order/getPatientData',
    // url: '/reception/order/getPatientDataList',
    method: 'get',
    params
  })
}
// 编辑-保存人员信息（右侧）
export function saveOrUpdatePatientBc(data) {
  return request({
    url: '/reception/order/saveOrUpdatePatientBc',
    method: 'post',
    data
  })
}
// 编辑-获取民族数据
export function getNationData() {
  return request({
    url: '/total/RecordManage/getNationData',
    method: 'get',
  })
}
// 编辑-预登记人员信息（右侧）
export function saveOrUpdatePatient(data) {
  return request({
    url: '/reception/order/saveOrUpdatePatient',
    method: 'post',
    data
  })
}
// 编辑-批量设置会员信息（右侧）
export function updatehy(params) {
  return request({
    url: '/reception/order/updatehy',
    method: 'put',
    params
  })
}
// 编辑-清除人员信息（右侧）
export function removeAll(params) {
  return request2({
    url: '/reception/order/removeAll',
    method: 'DELETE',
    params
  })
}
// 编辑-来检短信提醒-保存（右侧）
export function saveSmsToExam(data) {
  return request({
    url: '/reception/order/saveSmsToExam',
    method: 'POST',
    data
  })
}

// 编辑-获取当前客户体检套餐
export function getAllTcForOrder(params) {
  return request({
    url: '/reception/order/getAllTcForOrder',
    method: 'get',
    params
  })
}

// 根据订单号导入老数据到新系统中
export function importData(data) {
  return request({
    // url: '/reception/orderImportData/importData',
    url: 'reception/datamove/importData',
    method: 'post',
    data
  })
}

// 禁检/反禁检
export function updateGroupLimit(params) {
  return request({
    url: '/reception/order/updateGroupLimit',
    method: 'put',
    params
  })
}

// 根据订单号发送检前通知短信
export function appointmentSMSByDddh(params) {
  return request({
    url: '/reception/order/appointmentSMSByDddh',
    method: 'get',
    params
  })
}

// 查询待提交的审批
export function getToBeSubmitted(params) {
  return request({
    url: '/workflow/workflowReType/getToBeSubmitted',
    method: 'get',
    params
  })
}
// 校正会员类型
export function correctMembershipType(params) {
  return request({
    url: '/workflow/workflowReType/correctMembershipType',
    method: 'post',
    params
  })
}
// 提交审批
export function submitForApproval(data) {
  return request({
    url: '/workflow/workflowReType/submitForApproval',
    method: 'post',
    data
  })
}

// 生成团检分享码
export function generateGroupCode(data) {
  return request({
    url: '/reservation/reservationGroupCode/generateGroupCode',
    method: 'post',
    data
  })
}
// 修改团检分享码
export function modifyGroupCode(data) {
  return request({
    url: '/reservation/reservationGroupCode/modifyGroupCode',
    method: 'post',
    data
  })
}

// 同步订单下的体检者
export function syncOrderData(orderIds) {
  return request2({
    url: '/function/syncOrderData/0/' + orderIds,
    method: 'get',
    params: {
      isRegistered: '0',
      orderIds
    }
  })
}

// 订单同步
export function syncOrderData1(orderIds) {
  return request2({
    url: '/function/syncOrder/' + orderIds,
    method: 'get',
    params: {
      orderIds
    }
  })
}