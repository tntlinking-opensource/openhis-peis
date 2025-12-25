import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/app/createmealApp/page',
    method: 'get',
    params
  })
}
// 添加或修改
export function saOrUp(data) {
  return request({
    url: '/app/createmealApp/saOrUp',
    method: 'post',
    data
  })
}
// 详情
export function getDetailsApi(id) {
  return request({
    url: '/app/createmealApp/' + id,
    method: 'get',
    params: { id }
  })
}
// 删除
export function deleteApi(ids) {
  return request({
    url: '/app/createmealApp/' + ids,
    method: 'delete',
    params: { ids }
  })
}
// 修改上线状态
export function settingApi(data) {
  return request({
    url: '/app/createmealApp/goLive',
    method: 'post',
    data
  })
}