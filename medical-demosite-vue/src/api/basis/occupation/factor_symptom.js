// 危害因素重点询问症状 开发人：麦沃德科技 予安
import request from '@/utils/request'

// 查询JC重点询问症状列表
export function listSymptom(query) {
  return request({
    url: '/emphasisAskSymptom/getAskData',
    method: 'get',
    params: query
  })
}

// 查询JC重点询问症状详细
export function getSymptom(id) {
  return request({
    url: '/emphasisAskSymptom/' + id,
    method: 'get'
  })
}

// 新增JC重点询问症状
export function addSymptom(data) {
  return request({
    url: '/emphasisAskSymptom/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改JC重点询问症状
export function updateSymptom(data) {
  return request({
    url: '/emphasisAskSymptom/saveEdit',
    method: 'post',
    data: data
  })
}

// 删除JC重点询问症状
export function delSymptom(id) {
  return request({
    url: '/emphasisAskSymptom/remove/' + id,
    method: 'delete'
  })
}

// 同步JC重点询问症状
export function asyncSymptom(id) {
  return request({
    url: '/emphasisAskSymptom/synchronize',
    method: 'put',
    data: {
      id
    }
  })
}