// 销售团检统计 开发人：麦沃德科技 予安 
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/finance/healthyStatistics/page',
    method: 'get',
    params
  })
}

// 查询右边列表
export function getTotalList(params) {
  return request({
    url: '/finance/healthyStatistics/getTotalList',
    method: 'get',
    params
  })
}

// 查询右边列表
export function getBranchData() {
  return request({
    url: '/sell/monthtarget/getBranchData',
    method: 'get',
  })
}// 
