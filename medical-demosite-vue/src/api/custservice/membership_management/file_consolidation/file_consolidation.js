// 档案合并 开发人：麦沃德科技暴雨
import request from '@/utils/request'


// 档案合并列表
export function getListData(query) {
  return request({
    url: '/member/electronicReport/page',
    method: 'get',
    params: query
  })
}

// 档案合并详情
export function getEleInfoListData(params) {
  return request({
    url: '/member/electronicReport/getEleInfoListData',
    method: 'GET',
    params
  })
}

// 合并档案页面数据
export function merge(query) {
  return request({
    url: '/member/electronicReport/merge',
    method: 'get',
    params: query
  })
}

// 编辑-保存
export function saveOrUpdate(query) {
  return request({
    url: '/member/electronicReport/saveOrUpdate',
    method: 'POST',
    data: query
  })
}

// 合并档案
export function saveMerge(query) {
  return request({
    url: '/member/electronicReport/saveMerge',
    method: 'POST',
    data: query
  })
}
