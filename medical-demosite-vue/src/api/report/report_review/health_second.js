// 健康复审  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询健康一审页面数据
export function getListData(query) {
  return request({
    url: '/report/healthSecondAudit/getListData',
    method: 'get',
    params: query
  })
}

// 健康一审页面数据通过
export function healthSecondPass(id) {
  return request({
    url: '/report/healthSecondAudit/pass/' + id,
    method: 'GET'
  })
}

// 健康一审页面数据不通过
export function healthSecondUncheck(id, reason) {
  return request({
    url: `/report/healthSecondAudit/uncheck/${id}/${reason}`,
    method: 'GET'
  })
}

// 健康一审页面数据反审
export function healthSecondUnaudit(id) {
  return request({
    url: '/report/healthSecondAudit/unaudit/' + id,
    method: 'GET'
  })
}