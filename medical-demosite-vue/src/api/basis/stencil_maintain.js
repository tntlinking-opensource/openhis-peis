// WORD模板维护  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 查询模板维护(用于保存科室的模板（个检用）、团检的模板、对比模板)列表
export function listMaintain(query) {
  return request({
    url: '/stencilMaintain/getListData',
    method: 'get',
    params: query
  })
}

// 查询模板维护(用于保存科室的模板（个检用）、团检的模板、对比模板)详细
export function getMaintain(id) {
  return request({
    url: '/stencilMaintain/' + id,
    method: 'get'
  })
}

// 新增模板维护(用于保存科室的模板（个检用）、团检的模板、对比模板)
export function addMaintain(data) {
  return request({
    url: '/stencilMaintain/saveOrUpdate',
    method: 'post',
    data: data
  })
}

// 修改模板维护(用于保存科室的模板（个检用）、团检的模板、对比模板)
export function updateMaintain(data) {
  return request({
    url: '/stencilMaintain/saveOrUpdate',
    method: 'put',
    data: data
  })
}

// 删除模板维护(用于保存科室的模板（个检用）、团检的模板、对比模板)
export function delMaintain(id) {
  return request({
    url: '/stencilMaintain/remove/' + id,
    method: 'delete'
  })
}