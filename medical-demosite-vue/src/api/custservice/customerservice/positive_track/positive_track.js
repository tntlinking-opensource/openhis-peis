// 个检危机值回访 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 个检危机值回访列表
export function listPrinttype(query) {
  return request({
    url: '/member/peispatient/page',
    method: 'get',
    params: query
  })
}

// 个检危机值回访保存
export function peispatient(id) {
  return request({
    url: '/member/peispatient/' + id,
    method: 'GET',
  })
}

// 个检危机值阳性结果回访
export function saveOrUpdate(query) {
  return request({
    url: '/member/peispatient/saveOrUpdate',
    method: 'POST',
    data: query
  })
}
