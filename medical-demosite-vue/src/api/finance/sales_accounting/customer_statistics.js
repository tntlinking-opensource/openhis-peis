// 客服销售统计 开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/finance/individualCustStatistics/page',
    method: 'get',
    params
  })
}


