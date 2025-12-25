// 职业复查  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 获取数据
export function getListData(params) {
  return request({
    url: '/reception/reviewBatch/getListData',
    method: 'get',
    params
  })
}

// 获取收费项目数据
export function getItemsListData(params) {
  return request({
    url: '/reception/reviewBatch/getItemsListData',
    method: 'get',
    params
  })
}

// 职业复查保存
export function reviewBatch(data) {
  return request({
    url: '/reception/reviewBatch/reviewBatch',
    method: 'POST',
    data
  })
}
