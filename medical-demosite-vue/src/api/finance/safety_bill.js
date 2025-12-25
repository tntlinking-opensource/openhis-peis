// 平安对账单 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/finance/pingAnStatement/getOrderListData',
    method: 'get',
    params
  })
}

// 获取所有分中心
export function getfzxApi() {
  return request({
    url: '/finance/pingAnStatement/fzx',
    method: 'get',
  })
}

// 获取体检者数据
export function getPatientListData(params) {
  return request({
    url: '/finance/pingAnStatement/getPatientListData',
    method: 'get',
    params
  })
} 