// 健康初审  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询健康一审页面数据
export function getListData(query) {
  return request({
    url: '/report/healthFirstAudit/getListData',
    method: 'get',
    params: query
  })
}

// 健康一审页面数据通过
export function healthFirstPass(id) {
  return request({
    url: '/report/healthFirstAudit/pass/' + id,
    method: 'GET'
  })
}

// 健康一审页面数据不通过
export function healthFirstUncheck(id, reason) {
  return request({
    url: `/report/healthFirstAudit/uncheck/${id}/${reason}`,
    method: 'GET'
  })
}

// 健康一审页面数据反审
export function healthFirstUnaudit(id) {
  return request({
    url: '/report/healthFirstAudit/unaudit/' + id,
    method: 'GET'
  })
}