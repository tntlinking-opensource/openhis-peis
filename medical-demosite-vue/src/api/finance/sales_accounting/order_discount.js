// 团检订单折扣 开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/finance/orderDiscount/page',
    method: 'get',
    params
  })
}

// 详情
export function getview(params) {
  return request({
    url: '/finance/orderDiscount/view',
    method: 'get',
    params
  })
}
