import request from '@/utils/request'

// 查询分中心维护列表
export function listBranch(query) {
  return request({
    url: '/system/branch/list',
    method: 'get',
    params: query
  })
}

// 查询分中心维护详细
export function getBranch(id) {
  return request({
    url: '/system/branch/' + id,
    method: 'get'
  })
}

// 新增分中心维护
export function addBranch(data) {
  return request({
    url: '/system/branch',
    method: 'post',
    data: data
  })
}

// 修改分中心维护
export function updateBranch(data) {
  return request({
    url: '/system/branch',
    method: 'put',
    data: data
  })
}

// 删除分中心维护
export function delBranch(id) {
  return request({
    url: '/system/branch/' + id,
    method: 'delete'
  })
}

// 获取省市区
export function getRegionData(query) {
  return request({
    url: '/sell/customer/getZoneData',
    method: 'get',
    params: query
  })
}

//金蝶 - 分中心编码更新
export function upgradeKingdeeOrganization(id) {
  return request({
    url: '/finance/kingdee/upgradeKingdeeOrganization',
    method: 'post'
  })
}

// 删除体检号
export function delCode(params) {
  return request({
    url: '/system/branch/deletePeispatient',
    method: 'delete',
    params
  })
}

// 修改项目状态-查询
export function findProjectStatus(params) {
  return request({
    url: '/system/branch/findProjectStatus',
    method: 'get',
    params
  })
}
// 修改项目状态-修改
export function modifyProjectStatus(data) {
  return request({
    url: '/system/branch/modifyProjectStatus',
    method: 'put',
    data
  })
}
// 修改健康或职业状态-修改
export function modifyPeispatientStatus(data) {
  return request({
    url: '/system/branch/modifyPeispatientStatus',
    method: 'put',
    data
  })
}

// 修改工种
export function modifyWorkType(data) {
  return request({
    url: '/system/branch/modifyWorkType',
    method: 'put',
    data
  })
}

// 获取用户兼职分中心
export function getUserCid() {
  return request({
    url: '/reception/register/getUserCid',
    method: 'get',
  })
}

// 修改开单医生
export function updateOpenDoctor(data) {
  return request({
    url: '/system/branch/updateOpenDoctor',
    method: 'put',
    data
  })
}

// 修改科室
export function modifyDepartment(data) {
  return request({
    url: '/system/branch/modifyDepartment',
    method: 'put',
    data
  })
}