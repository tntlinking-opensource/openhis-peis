// 加急报告  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/reception/PlusEmergency/page',
    method: 'get',
    params: query
  })
}

// 加急-保存
export function saveOrUpdate(params) {
  return request({
    url: '/reception/PlusEmergency/saveOrUpdate',
    method: 'PUT',
    params
  })
}