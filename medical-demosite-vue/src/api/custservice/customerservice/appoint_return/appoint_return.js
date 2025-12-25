// 预约来检回访 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 预约来检回访列表
export function listPrinttype(query) {
  return request({
    url: '/member/advanceVisitWrite/page',
    method: 'get',
    params: query
  })
}

// 预约来检回访保存
export function saveOrUpdate(query) {
  return request({
    url: '/member/advanceVisitWrite/saveOrUpdate',
    method: 'POST',
    data: query
  })
}

// 预约来检回访详情
export function advanceVisitWrite(id) {
  return request({
    url: '/member/advanceVisitWrite/' + id,
    method: 'GET',
  })
}
