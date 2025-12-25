// 个检销售统计 开发人：麦沃德科技 予安 
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/finance/individualStatistics/page',
    method: 'get',
    params
  })
}

// 获取关联的数据
export function getDetailsData(params) {
  return request({
    url: '/finance/individualStatistics/getListData',
    method: 'get',
    params
  })
}

