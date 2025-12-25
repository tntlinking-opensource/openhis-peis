// 记账结算明细 开发人：麦沃德矢北 
import request from '@/utils/request'

export function getList(query) {
  return request({
    url: '/finance/bankSettlement/page',
    method: 'get',
    params: query
  })
}
export function getDetailData(id) {
  return request({
    url: '/finance/bankSettlement/bankRefund/' + id,
    method: 'get',

  })
}
export function getBilData(query) {
  return request({
    url: '/finance/bankSettlement/getReserBillingData',
    method: 'get',
    params: query,
  })
}
//支付方式列表
export function getPayList() {
  return request({
    url: '/finance/bankSettlement/getKingdeeReserWay',
    method: 'get',

  })
}
//保存
export function updateData(data) {
  return request({
    url: '/finance/bankSettlement/updateBankRefund',
    method: 'post',
    data: data
  })
}
//金蝶银行流水更新
export function kingDeeBank(data) {
  return request({
    url: '/finance/kingdee/upgradeKingdeeRemittanceByKingdee',
    method: 'post',
    data: data
  })
}
//其他中心编码更新
export function otherCenter(data) {
  return request({
    url: '/finance/kingdee/upgradeKingdeeOrganization',
    method: 'post',
    data: data
  })
}
//部门编码更新
export function departUpdate(data) {
  return request({
    url: '/finance/kingdee/upgradeDepartmentByKingdee',
    method: 'post',
    data: data
  })
}


//上传银行流水
export function upLoadBank(params) {
  return request({
    url: '/finance/kingdee/upgradeOtherPayable',
    method: 'post',
    params
  })
}
//金蝶数据库更新
export function kingDeeUpdate(data) {
  return request({
    url: '/finance/kingdee/upgradeCustomer',
    method: 'post',
    data: data
  })
}

// 查询汇总金额
export function summaryAmount(params) {
  return request({
    url: '/finance/bankSettlement/summaryAmount',
    method: 'get',
    params
  })
}

// 判断当前登录者是否为财务
export function isCaiWu() {
  return request({
    url: '/finance/bankSettlement/isCaiWu',
    method: 'get',
  })
}
// 结算-审核
export function approveApi(params) {
  return request({
    url: '/finance/bankSettlement/approve',
    method: 'put',
    params
  })
}
// 结算-反审核
export function unapproveApi(params) {
  return request({
    url: '/finance/bankSettlement/unapprove',
    method: 'put',
    params
  })
}