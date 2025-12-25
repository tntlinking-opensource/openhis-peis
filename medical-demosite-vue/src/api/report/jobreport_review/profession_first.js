// 职业初审  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 分页查询健康一审页面数据
export function getListData(query) {
  return request({
    url: '/report/professionFirstAudit/getListData',
    method: 'get',
    params: query
  })
}

// 健康一审页面数据通过
export function professionFirstPass(id) {
  return request({
    url: '/report/professionFirstAudit/pass/' + id,
    method: 'GET'
  })
}

// 健康一审页面数据不通过
export function professionFirstUncheck(id, reason) {
  return request({
    url: `/report/professionFirstAudit/uncheck/${id}/${reason}`,
    method: 'GET'
  })
}

// 健康一审页面数据反审
export function professionFirstUnaudit(id) {
  return request({
    url: '/report/professionFirstAudit/unaudit/' + id,
    method: 'GET'
  })
}