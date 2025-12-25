import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/app/mdSysBranch/page',
    method: 'get',
    params
  })
}
// 添加或修改
export function saOrUp(data) {
  return request({
    url: '/app/mdSysBranch/saOrUp',
    method: 'post',
    data
  })
}
// 详情
export function getDetailsApi(id) {
  return request({
    url: '/app/mdSysBranch/' + id,
    method: 'get',
    params: { id }
  })
}
// 删除
export function deleteApi(id) {
  return request({
    url: '/app/mdSysBranch/' + id,
    method: 'delete',
    params: { id }
  })
}