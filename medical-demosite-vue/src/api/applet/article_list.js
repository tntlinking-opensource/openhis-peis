import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/app/appArticle/page',
    method: 'get',
    params
  })
}
// 添加或修改
export function saOrUp(data) {
  return request({
    url: '/app/appArticle/saOrUp',
    method: 'post',
    data
  })
}
// 详情
export function getDetailsApi(id) {
  return request({
    url: '/app/appArticle/' + id,
    method: 'get',
    params: { id }
  })
}
// 删除
export function deleteApi(ids) {
  return request({
    url: '/app/appArticle/' + ids,
    method: 'delete',
    params: { ids }
  })
}
// 修改上线状态
export function settingApi(data) {
  return request({
    url: '/app/appArticle/goLive',
    method: 'post',
    data
  })
}