import request from '@/utils/request'

// 查询职业健康症状名称维护列表
export function getListApi(query) {
  return request({
    url: '/occupationSymptom/getListData',
    method: 'get',
    params: query
  })
}

// 查询职业健康症状名称维护详细
export function getDetailsApi(id) {
  return request({
    url: '/occupationSymptom/' + id,
    method: 'get',
  })
}

// 新增职业健康症状名称维护
export function addApi(data) {
  return request({
    url: '/occupationSymptom/saveOrUpdate',
    method: 'post',
    data,
  })
}

// 修改职业健康症状名称维护
export function updateApi(data) {
  return request({
    url: '/medical/harm',
    method: 'put',
    data: data
  })
}

// 删除职业健康症状名称维护
export function delApi(id) {
  return request({
    url: '/occupationSymptom/remove/' + id,
    method: 'delete'
  })
}
