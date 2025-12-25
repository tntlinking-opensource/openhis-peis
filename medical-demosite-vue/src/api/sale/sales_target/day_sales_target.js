import request from '@/utils/request'

// 查询季度目标列表
export function listTarget(params) {
  return request({
    url: '/sell/dayTarget/page',
    method: 'get',
    params
  })
}