// 咨询与随访统计 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 查询咨询与随访统计列表
export function listPrinttype(query) {
  return request({
    url: '/member/conStatistics/getStatisticsListData',
    method: 'get',
    params: query
  })
}
