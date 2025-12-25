// 迟补检回访 开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 迟补检回访列表
export function getListData(query) {
  return request({
    url: '/member/forinspectionView/page',
    method: 'get',
    params: query
  })
}

// 不合格样品回访保存
export function saveOrUpdate(query) {
  return request({
    url: '/member/forinspectionView/saveOrUpdate',
    method: 'POST',
    data: query
  })
}

// 不合格样品详情
export function forinspectionView(id) {
  return request({
    url: '/member/forinspectionView/' + id,
    method: 'GET',
  })
}
