import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/app/bsSettlement/page',
    method: 'get',
    params
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
// 获取总额
export function getTotal(params) {
  return request({
    url: '/app/bsSettlement/getTotal',
    method: 'get',
    params
  })
}