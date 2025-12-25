// 不合格样本回访 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 不合格样本回访统计列表
export function listPrinttype(query) {
  return request({
    url: '/member/belowSample/page',
    method: 'get',
    params: query
  })
}

// 不合格样品回访保存
export function saveOrUpdate(query) {
  return request({
    url: '/member/belowSample/saveOrUpdate',
    method: 'POST',
    data: query
  })
}

// 不合格样品详情
export function belowSample(id) {
  return request({
    url: '/member/belowSample/' + id,
    method: 'GET',
  })
}
