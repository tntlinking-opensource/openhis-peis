// 收费信息查询 开发人：麦沃德科技 矢北 
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/query/chargeInfo/page',
    method: 'get',
    params: query
  })
}
// 获取支付方式
export function getPayWay(query) {
  return request({
    url: '/dictpayway/getPayWayData',
    method: 'get',
    params: query
  })
}

// 获取合计数据
export function financeCountAmount(params) {
  return request({
    url: '/query/chargeInfo/financeCountAmount',
    method: 'get',
    params
  })
}