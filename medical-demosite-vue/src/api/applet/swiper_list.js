import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/app/indexImg/page',
    method: 'get',
    params
  })
}
// 添加或修改
export function saOrUp(data) {
  return request({
    url: '/app/indexImg/saOrUp',
    method: 'post',
    data
  })
}
// 详情
export function getDetailsApi(imgId) {
  return request({
    url: '/app/indexImg/' + imgId,
    method: 'get',
    params: { imgId }
  })
}
// 删除
export function deleteApi(imgId) {
  return request({
    url: '/app/indexImg/' + imgId,
    method: 'delete',
    params: { imgId }
  })
}