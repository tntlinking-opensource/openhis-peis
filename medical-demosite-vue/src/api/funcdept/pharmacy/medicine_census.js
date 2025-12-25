// 售药统计  开发人：麦沃德科技 矢北
import request from '@/utils/request'
// 查询药品管理列表
export function getListData(query) {
  return request({
    url: '/drugstore/statistics/getStatisticsListData',
    method: 'get',
    params: query
  })
}
//导出
export function exportListData() {
  return request({
    url: '/drugstore/prescribe/export',
    method: 'post',

  })
}