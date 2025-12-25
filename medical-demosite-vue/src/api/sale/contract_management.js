import request from '@/utils/request'

// 查询普通套餐列表
export function getListData(query) {
  return request({
    url: '/sell/sellpact/page',
    method: 'get',
    params: query
  })
}

// 添加
export function addListData(data) {
  return request({
    url: '/sell/sellpact',
    method: 'POST',
    data
  })
}

// 修改
export function editListData(data) {
  return request({
    url: '/sell/sellpact',
    method: 'PUT',
    data
  })
}

// 查看
export function sellpact(id) {
  return request({
    url: '/sell/sellpact/' + id,
    method: 'get',
  })
}

// 删除
export function Dsellpact(id) {
  return request({
    url: '/sell/sellpact/' + id,
    method: 'DELETE',
  })
}

// 生成合同号
export function getHthData() {
  return request({
    url: '/sell/sellpact/getHthData',
    method: 'get',
  })
}

// 材料路径保存
export function saveClUrl(data) {
  return request({
    url: '/sell/createorder/saveClUrl',
    method: 'put',
    params: data
  })
}

// 合同下载
export function downloadFiles(query) {
  return request({
    url: '/sell/sellpact/download',
    method: 'get',
    params: query
  })
}
