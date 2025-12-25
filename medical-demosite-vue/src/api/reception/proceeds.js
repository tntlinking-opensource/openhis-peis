// 费用管理  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 验证是否有未退费的退项
export function refundValidate(query) {
  return request({
    url: '/reception/register/refundValidate',
    method: 'get',
    params: query
  })
}

// 获取当前选中的已预约用户信息
export function getCustomerData(query) {
  return request({
    url: '/reception/order/getCustomerData',
    method: 'get',
    params: query
  })
}
// 收费信息
export function getChargeData(query) {
  return request({
    url: '/reception/charge/getChargeData',
    method: 'get',
    params: query
  })
}

// 获取所有的登记人
export function getAllUserSql2(query) {
  return request({
    url: '/reception/order/getAllUserSql2',
    method: 'get',
    params: query
  })
}

// 收费
export function receiveRefund(data) {
  return request({
    url: '/reception/charge/receiveRefund',
    method: 'post',
    data
  })
}

// 修改收费方式
export function changePaymentMethod(data) {
  return request({
    url: '/reception/charge/changePaymentMethod',
    method: 'post',
    data
  })
}

// 获取收费方式
export function getPayWayData() {
  return request({
    url: '/reception/register/getPayWayData',
    method: 'get',
  })
}

// 获取收费方式
export function setUnRegister(params) {
  return request({
    url: '/reception/charge/setUnRegister',
    method: 'put',
    params
  })
}

// 完成登记
export function handleInsert(data) {
  return request({
    url: '/reception/register/insert',
    method: 'POST',
    data
  })
}

// 完成登记
export function getReadItems(params) {
  return request({
    url: '/reception/register/getReadItems',
    method: 'get',
    params
  })
}

// 获取会员卡详情
export function getMemberData(params) {
  return request({
    // url: '/member/member/getMemberData',
    url: 'finance/rechargeCard/getOldMemberCardData',
    method: 'get',
    params
  })
}
// 获取体检卡详情
export function getCardData(params) {
  return request({
    // url: '/finance/rechargeCard/getCardData',
    url: 'finance/rechargeCard/getOldCardData',
    method: 'get',
    params
  })
}
// 查询是否绑定家庭卡
export function searchPatientcode(params) {
  return request({
    url: '/reception/charge/searchPatientcode',
    method: 'get',
    params
  })
}

// 卡及微信支付
export function handlePay(data) {
  return request({
    url: '/reception/charge/pay',
    method: 'POST',
    data
  })
}

// 预收或结算
export function handleAdvancePay(type, data) {
  return request({
    url: type == 1 ? '/reception/charge/advancePay' : '/finance/tallyQuery/pay',
    method: 'POST',
    data
  })
}

// 卡及微信预退款
export function refundApi(data) {
  return request({
    url: '/reception/charge/refund',
    method: 'POST',
    data
  })
}

// 获取分中心列表
export function getBranchData(params) {
  return request({
    url: '/sell/createorder/getBranchData',
    method: 'get',
    params
  })
}

// 获取缴费单
export function chargeDataPrint(params) {
  return request({
    url: '/reception/charge/chargeDataPrint',
    method: 'get',
    params
  })
}

// 查询通联是否支付成功
export function queryTongLian(params) {
  return request({
    url: '/reception/charge/queryTongLian',
    method: 'post',
    params
  })
}

// 老系统家庭卡查询
export function getOldFamilyCard(params) {
  return request({
    url: '/finance/rechargeCard/getOldFamilyCard',
    method: 'get',
    params
  })
}

// 查询随行付是否支付成功
export function querySuiXing(data) {
  return request({
    url: '/reception/charge/querySuiXing',
    method: 'post',
    data
  })
} 