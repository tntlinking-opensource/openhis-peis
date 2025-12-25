//报告审核统计 开发人：麦沃德科技矢北 
 import  request from '@/utils/request'
  // 分页查询健康一审页面数据
export function getListData(query) {
  return request({
    url: '/report/totalAudit/page',
    method: 'get',
    params: query
  })
}
