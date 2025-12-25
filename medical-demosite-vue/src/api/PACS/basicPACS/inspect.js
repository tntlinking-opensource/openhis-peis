// PACS检查项目  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 获取pacs检查项目
export function getExamList(query) {
  return request({
    url: '/pacs/exam/page',
    method: 'get',
    params: query
  })
}

// 获取pacs体征词
export function getSignList(query) {
  return request({
    url: '/pacs/exam/sign/list',
    method: 'get',
    params: query
  })
}

// 获取pacs检查项目详情
export function getExamData(id) {
  return request({
    url: '/pacs/exam/' + id,
    method: 'get',
  })
}

// 删除pacs检查项目
export function delExam(ids) {
  return request({
    url: '/pacs/exam/' + ids,
    method: 'delete',
  })
}

// 添加pacs检查项目
export function saveExam(data) {
  return request({
    url: '/pacs/exam/save',
    method: 'post',
    data: data
  })
}

// 编辑pacs检查项目
export function updateExam(data) {
  return request({
    url: '/pacs/exam/update',
    method: 'put',
    data: data
  })
}