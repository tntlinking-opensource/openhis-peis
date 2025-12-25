import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/app/appUser/page',
    method: 'get',
    params
  })
}