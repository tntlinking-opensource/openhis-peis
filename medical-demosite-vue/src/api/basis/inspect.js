// 平安好医生排检  开发人：麦沃德科技半夏
import request from '@/utils/request'

// 查询JC检查项目类型列表
export function listBasexamltemtype(query) {
  return request({
    url: '/basexamltemtype/page',
    method: 'get',
    params: query
  })
}

// 查询JC检查项目类型详细
export function getBasexamltemtype(id) {
  return request({
    url: '/basexamltemtype/' + id,
    method: 'get'
  })
}

// 新增JC检查项目类型
export function addBasexamltemtype(data) {
  return request({
    url: '/basexamltemtype',
    method: 'post',
    data: data
  })
}

// 修改JC检查项目类型
export function updateBasexamltemtype(data) {
  return request({
    url: '/basexamltemtype',
    method: 'put',
    data: data
  })
}

// 删除JC检查项目类型
export function delBasexamltemtype(id) {
  return request({
    url: '/basexamltemtype/' + id,
    method: 'delete'
  })
}

// 查询JC检查项目列表
export function listBasexamltem(query) {
  return request({
    url: '/basexamltem/list',
    method: 'get',
    params: query
  })
}

// 查询JC检查项目详细
export function getBasexamltem(id) {
  return request({
    url: '/basexamltem/' + id,
    method: 'get'
  })
}

// 新增JC检查项目
export function addBasexamltem(data) {
  return request({
    url: '/basexamltem',
    method: 'post',
    data: data
  })
}

// 修改JC检查项目
export function updateBasexamltem(data) {
  return request({
    url: '/basexamltem',
    method: 'put',
    data: data
  })
}

// 删除JC检查项目
export function delBasexamltem(id) {
  return request({
    url: '/basexamltem/' + id,
    method: 'delete'
  })
}

//查看JC检查项目列表内表
export function getFeatureListData(query) {
  return request({
    url: '/basexamltem/getFeatureListData',
    method: 'get',
    params: query
  })
}

//获取分中心
export function getBranchData() {
  return request({
    url: '/sell/monthtarget/getBranchData',
    method: 'get',
  })
}

//第三方查询接口
//分页
export function getItemRelatedInformation(query) {
  return request({
    url: '/basexamltem/getItemRelatedInformation',
    method: 'get',
    params: query
  })
}

//添加或修改
export function saveOrUpdate(query) {
  return request({
    url: '/basexamltem/saveOrUpdate',
    method: 'POST',
    data:query
  })
}

//详情
export function edit(query) {
  return request({
    url: '/basexamltem/edit',
    method: 'get',
    params: query
  })
}

//删除
export function remove(query) {
  return request({
    url: `/basexamltem/remove?ids=${query.ids}`,
    method: 'DELETE',
  })
}

//项目类型
export function typelist(){
  return request({
    url: '/basexamltemtype/list',
    method: 'get'
  })
}

// 通过输入码查询艾迪康项目代码表
export function getAdiconSelectData(key){
  return request({
    url: '/basexamltem/getAdiconSelectData',
    method: 'get',
    params:{
      key
    }
  })
}