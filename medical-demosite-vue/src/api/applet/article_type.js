import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/app/appArticleType/page',
    method: 'get',
    params
  })
}
// 添加或修改
export function saOrUp(data) {
  return request({
    url: '/app/appArticleType/saOrUp',
    method: 'post',
    data
  })
}
// 详情
export function getDetailsApi(id) {
  return request({
    url: '/app/appArticleType/' + id,
    method: 'get',
    params: { id }
  })
}
// 删除
export function deleteApi(ids) {
  return request({
    url: '/app/appArticleType/' + ids,
    method: 'delete',
    params: { ids }
  })
}