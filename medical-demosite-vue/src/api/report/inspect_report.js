//柜子管理  开发人：麦沃德科技矢北
import request from '@/utils/request'

// 分页查询健康一审页面数据
export function getListData(query) {
  return request({
    url: '/report/inspectReports/page',
    method: 'get',
    params: query
  })
}
// 分页查询健康一审页面数据
export function getRightListData(query) {
  return request({
    url: '/report/inspectReports/getInfoListData',
    method: 'get',
    params: query
  })
}

// 分页查询健康一审页面数据
export function createReport(params) {
  return request({
    url: '/report/inspectReports/create',
    method: 'get',
    params
  })
}

