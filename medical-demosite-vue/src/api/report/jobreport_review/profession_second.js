// 健康复审  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询健康一审页面数据
export function getListData(query) {
  return request({
    url: '/report/professionSecondAudit/getListData',
    method: 'get',
    params: query
  })
}

// 健康一审页面数据通过
export function professionSecondPass(id) {
  return request({
    url: '/report/professionSecondAudit/pass/' + id,
    method: 'GET'
  })
}

// 健康一审页面数据不通过
export function professionSecondUncheck(id, reason) {
  return request({
    url: `/report/professionSecondAudit/uncheck/${id}/${reason}`,
    method: 'GET'
  })
}

// 健康一审页面数据反审
export function professionSecondUnaudit(id) {
  return request({
    url: '/report/professionSecondAudit/unaudit/' + id,
    method: 'GET'
  })
}