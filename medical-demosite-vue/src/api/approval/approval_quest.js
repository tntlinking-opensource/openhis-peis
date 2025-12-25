import request from '@/utils/request'

// 分页查询列表
export function getListApi(params) {
  return request({
    url: '/workflow/workflowCase/page',
    method: 'get',
    params
  })
}
// 获取审批任务详情
export function questDetails(id) {
  return request({
    url: '/workflow/workflowCase/' + id,
    method: 'get',
    params: {
      id
    }
  })
}
// 更新节点状态
export function updateItemStatus(data) {
  return request({
    url: '/workflow/workflowCase/updateItemStatus',
    method: 'put',
    data
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

// 根据业务ID获取实例详情
export function getInfoByBzId(params) {
  return request({
    url: '/workflow/workflowCase/getInfoByBzId/' + params.bzId,
    method: 'get',
    params
  })
}

// 判断是否启动工作流
export function isOpenFlow(params) {
  return request({
    url: '/workflow/workflow/isOpen',
    method: 'get',
    params
  })
}
// 获取套餐
export function getPackage(params) {
  return request({
    url: '/sell/createorder/getPackage',
    method: 'get',
    params
  })
}