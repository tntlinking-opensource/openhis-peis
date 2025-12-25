//财务产值报表 开发人：麦沃德矢北
import request from '@/utils/request'

export function getReport(query) {
  return request({
    url: '/finance/outputValueReport/getList',
    method: 'get',
    params: query
  })
}
// 获取体检产值
export function getInspectOutputValue(params) {
  return request({
    url: '/finance/outputValueReport/getInspectOutputValue',
    method: 'get',
    params
  })
}
export function getVaccinesOutputValue(params) {
  return request({
    url: '/finance/outputValueReport/getVaccinesOutputValue',
    method: 'get',
    params
  })
}