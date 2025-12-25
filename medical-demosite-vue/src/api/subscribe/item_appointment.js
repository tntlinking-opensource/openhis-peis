// 项目预约明细 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/reservation/sortQuery/page',
    method: 'get',
    params
  })
}