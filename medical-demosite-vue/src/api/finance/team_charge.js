// 团体结算 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/finance/teamCharge/page',
    method: 'get',
    params
  })
}

// 获取结算信息
export function getBillingData(params) {
  return request({
    url: '/finance/teamCharge/getBillingData',
    method: 'get',
    params
  })
}

// 获取收费方式
export function saveOrUpdate(data) {
  return request({
    url: '/finance/teamCharge/saveOrUpdate',
    method: 'POST',
    data
  })
}

// 结算-卡扣款
export function saveOrUpdateFee(data) {
  return request({
    url: '/finance/teamCharge/saveOrUpdateFee',
    method: 'POST',
    data
  })
}
