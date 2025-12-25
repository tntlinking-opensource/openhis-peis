import request from '@/utils/request'

// 分页查询列表
export function getListData(params) {
  return request({
    url: '/workflow/approvalStatus/page',
    method: 'get',
    params
  })
}