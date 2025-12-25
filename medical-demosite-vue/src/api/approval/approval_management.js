import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/workflow/workflow/page',
    method: 'get',
    params
  })
}
// 获取审批类型
export function getTypeData(params) {
  return request({
    url: '/workflow/workflowType/list',
    method: 'get',
    params
  })
}
// 添加审批流
export function addWorkflow(data) {
  return request({
    url: '/workflow/workflow',
    method: 'post',
    data
  })
}
// 获取审批流详情
export function workflowDetails(id) {
  return request({
    url: '/workflow/workflow/' + id,
    method: 'get',
    params: {
      id
    }
  })
}
// 修改审批流
export function editWorkflow(data) {
  return request({
    url: '/workflow/workflow',
    method: 'put',
    data
  })
}


// 删除审批流
export function removeWorkflow(ids) {
  return request({
    url: '/workflow/workflow/' + ids,
    method: 'delete',
    params: {
      ids
    }
  })
}
