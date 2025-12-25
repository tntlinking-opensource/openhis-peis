import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/app/sysConfig/page',
    method: 'get',
    params
  })
}
// 修改
export function saOrUp(data) {
  return request({
    url: '/app/sysConfig/saOrUp',
    method: 'post',
    data
  })
}
// 详情
export function getDetailsApi(id) {
  return request({
    url: '/app/sysConfig/' + id,
    method: 'get',
    params: { id }
  })
}