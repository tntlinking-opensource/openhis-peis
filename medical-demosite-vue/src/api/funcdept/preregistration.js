// 检完签到  开发人：麦沃德科技矢北
import request from '@/utils/request'
export function getPatientData(query) {
  return request({
    url: '/SignInInspection/getRecheckItems',
    method: 'get',
    params: query
  })
}
// 查看全部pdf报告
export function viewThirdPartyImages(query) {
  return request({
    url: '/SignInInspection/viewThirdPartyImages',
    method: 'get',
    params: query
  })
}
// 查询备单人员信息
export function getInspectData(query) {
  return request({
    url: '/SignInInspection/getPatientData',
    method: 'get',
    params: query
  })
}
// 弃检查
export function giveUpCheck(query) {
  return request({
    url: '/SignInInspection/saveOrUpdateItem',
    method: 'post',
    data: query
  })
}
// 交单 
export function submitForm(data) {
  return request({
    url: `/SignInInspection/surrender`,
    method: 'put',
    params: data
  })
}
// 批量交单 
export function submitForms(query) {
  return request({
    url: `/SignInInspection/surrender`,
    method: 'put',
    params: query
  })
}
// 补检
export function buJian(query) {
  return request({
    url: '/SignInInspection/updateItemsDeal',
    method: 'post',
    data: query
  })
}
// 拒检
export function juJian(data) {
  return request({
    url: `/SignInInspection/jujiann`,
    method: 'put',
    params:data
  })
}
// 反拒检
export function fanJuJian(ids) {
  return request({
    url: `/SignInInspection/fanjujian/`+ids,
    method: 'put',
  })
}

//危急值检测
export function isDanger(query) {
  return request({
    url: '/SignInInspection/checkDanger',
    method: 'get',
    params: query
  })
}
// 获取支付方式
export function getPayWay(params) {
  return request({
    url: '/dictpayway/getPayWayData',
    method: 'get',
    params
  })
}

// 获取通知方式
export function getIssueWayData() {
  return request({
    url: '/SignInInspection/getIssueWayData',
    method: 'get',
  })
}
// 获取民族
export function getNationData() {
  return request({
    url: '/sell/itemListOnline/getNationData',
    method: 'get',
  })
}
// 获取收费项目图片
export function getPicture(id) {
  return request({
    url: '/items/getPicture/' + id,
    method: 'post'
  })
}

// 上传
export function uploadFile(data) {
  return request({
    url: '/common/upload',
    method: 'post',
    data: data
  })
}

// 获取自助项目弹窗数据
export function getPopData(params) {
  return request({
    url: '/SignInInspection/getPopData',
    method: 'get',
    params
  })
}
// 完成检测
export function setPopData(ids) {
  return request({
    url: '/SignInInspection/setPopData',
    method: 'put',
    params:{
      ids
    }
  })
}
// 自助项目弃检
export function popGiveUp(params) {
  return request({
    url: '/SignInInspection/popGiveUp',
    method: 'put',
    params
  })
}

// 查询上传的第三方系统pdf报告
export function queryThirdReports(params) {
  return request({
    url: '/SignInInspection/queryThirdReports',
    method: 'get',
    params
  })
}
// 删除上传的第三方系统pdf报告
export function deleteThirdReports(params) {
  return request({
    url: '/SignInInspection/deleteThirdReports',
    method: 'post',
    params
  })
}

// 设置已检未检
export function modifyProjectStatus(data) {
  return request({
    url: '/SignInInspection/modifyProjectStatus',
    method: 'post',
    data
  })
}