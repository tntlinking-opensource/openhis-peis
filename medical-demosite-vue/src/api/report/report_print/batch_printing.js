// 批量打印  开发人：麦沃德科技半夏
import request from '@/utils/request2'

// 获取报告地址
export function getReportAddress(query) {
  return request({
    url: '/report/health/getReportAddress',
    method: 'get',
    params: query
  })
}