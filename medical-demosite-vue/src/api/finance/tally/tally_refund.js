// 记账结算 开发人：麦沃德 予安
import request from '@/utils/request'

// 分页查询
export function getListApi(params) {
  return request({
    url: '/finance/tallyQuery/page',
    method: 'get',
    params
  })
}

// 获取结算信息
export function getBillingData(params) {
  return request({
    url: '/finance/tallyQuery/getBillingData',
    method: 'get',
    params
  })
}

// 结算展现页数据
export function saveOrUpdate(data) {
  return request({
    url: '/finance/tallyQuery/update',
    method: 'POST',
    data
  })
}

// 卡结算
export function saveOrUpdateFee(data) {
  return request({
    url: '/finance/tallyQuery/saveOrUpdateFee',
    method: 'POST',
    data
  })
}

// 卡及微信预退款
export function refundApi(data) {
  return request({
    url: 'finance/tallyQuery/refund',
    method: 'POST',
    data
  })
}