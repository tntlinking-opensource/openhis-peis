// 咨询与随访 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 查询咨询与随访列表
export function listPrinttype(query) {
  return request({
    url: '/member/consulation/page',
    method: 'get',
    params: query
  })
}

// 咨询与随访加载
export function loadPeople(query) {
  return request({
    url: '/member/consulation/loadPeople',
    method: 'get',
    params: query
  })
}

// 咨询与随访详情
export function consulation(id) {
  return request({
    url: '/member/consulation/'+id,
    method: 'get',

  })
}

// 咨询与随访保存
export function saveOrUpdate(query) {
  return request({
    url: '/member/consulation/saveOrUpdateConsulation',
    method: 'POST',
    data: query
  })
}
