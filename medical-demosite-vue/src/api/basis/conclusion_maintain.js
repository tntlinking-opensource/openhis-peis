// 总检结论词维护 开发人：麦沃德科技矢北
import request from '@/utils/request'

// 分页查询总检结论词列表
export function listBasConclusion(query) {
  return request({
    url: '/basconclusion/list',
    method: 'get',
    params: query
  })
}

// 审核总检结论词详细
export function getBasConlusion(id) {
  return request({
    url: '/basconclusion/audit/{ids}' + id,
    method: 'put'
  })
}

// 新增或者更新总检结论词
export function addBasConclusion(data) {
  return request({
    url: 'basconclusion',
    method: 'post',
    data: data
  })
}

//获取所有的分页功能findAllDepartment
export function findAllDepartment(data) 
{
  return request({
    url: '/basconclusion/findAllDepartment',
    method: 'get',
    params: data
  })
}

// 修改总检结论词
export function updateBasConclusion(data) {
  return request({
    url: '/basconclusion',
    method: 'put',
    data: data
  })
}


// 删除总检结论词
export function delBasConClusion(ids) {
  return request({
    url: '/basconclusion/' + ids,
    method: 'delete'
  })
}

//详情
export function detailBasConclusion(id) {
  return request({
    url: '/basconclusion/' + id,
    method: 'get'
  })
}
//上传
export function uploadBasConclusion(file) {
  return request({
    url: '/basconclusion/upload' ,
    method: 'post',
    params:file,
  })
}
//审核
export function reviewConclusion(ids) {
  return request({
    url: '/basconclusion/audit/' + ids,
    method: 'put'
  })
}