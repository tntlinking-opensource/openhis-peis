import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/system/helpDocument/page',
    method: 'get',
    params
  })
}
// 添加帮助文档
export function addHelpDocument(data) {
  return request({
    url: '/system/helpDocument',
    method: 'post',
    data
  })
}
// 删除帮助文档
export function removeHelpDocument(ids) {
  return request({
    url: '/system/helpDocument/' + ids,
    method: 'delete',
    params: {
      ids
    }
  })
}
// 获取帮助文档详情
export function HelpDocumentDetails(id) {
  return request({
    url: '/system/helpDocument/' + id,
    method: 'get',
    params: {
      id
    }
  })
}
// 修改帮助文档
export function editHelpDocument(data) {
  return request({
    url: '/system/helpDocument',
    method: 'put',
    data
  })
}
