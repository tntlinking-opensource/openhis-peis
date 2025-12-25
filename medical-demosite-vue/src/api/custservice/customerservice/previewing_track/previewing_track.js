// 个检预检回访 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 个检预检回访列表
export function listPrinttype(query) {
  return request({
    url: '/member/previewingTrack/page',
    method: 'get',
    params: query
  })
}

// 个检预检回访详情
export function previewing(id) {
  return request({
    url: '/member/previewingTrack/' + id,
    method: 'GET',
  })
}

// 个检预检回访保存
export function saveOrUpdate(query) {
  return request({
    url: '/member/previewingTrack/saveOrUpdate',
    method: 'POST',
    data: query
  })
}
