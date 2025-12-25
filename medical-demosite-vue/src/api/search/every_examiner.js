// 每日体检查询 开发人：麦沃德科技 矢北
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/query/everyExaminer/getListData',
    method: 'get',
    params:query
  })
}
// 分组分页查询
export function getGroupListData(query) {
  return request({
    url: '/query/everyExaminer/getGroupData',
    method: 'get',
    params:query
  })
}
// 获取详情
export function getDetailListData(query) {
  return request({
    url: '/query/everyExaminer/getChargeData',
    method: 'get',
    params:query
  })
}