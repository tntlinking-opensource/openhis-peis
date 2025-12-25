// 健康总检  开发人：麦沃德科技 予安/清风
import request from '@/utils/request'

// 查询健康总检列表
export function listHealth(query) {
  return request({
    url: '/total/health/page',
    method: 'get',
    params: query
  })
}

//根据体检号锁定/解锁 改变总检状态
export function isLock(query){
  return request({
    url: `total/health/lock?ids=${query.ids}&state=${query.state}`,
    method: 'put',
  })
}

// 查询健康总检列表
export function detailsHealth(query) {
  return request({
    url: '/total/health/' + query.flag + '/' + query.patientno,
    method: 'get',
    params: query
  })
}

//是否可以总检
export function canTotal(query) {
  return request({
    url: '/total/health/canTotal',
    method: 'get',
    params: query
  })
}

//获取健康总检结论词列表数据
export function getVerdictListData(query) {
  return request({
    url: '/total/health/getVerdictListData',
    method: 'get',
    params: query
  })
}
//保存追加的总检建议        
export function commitSign(query){
  return request({
    url: 'total/health/commitSign',
    method: 'post',
    data: query
  })
}

//打开分科-普通界面
export function getcommon(query){
  return request({
    url: 'total/health/common',
    method: 'get',
    params: query
  })
}

//科室小结 界面 数据 --分科普通界面数据
export function getCommonData(query){
  return request({
    url: 'total/health/getCommonData',
    method: 'get',
    params: query
  })
}

//科室小结  打开科室小结界面
export function getVerdict(query){
  return request({
    url: 'total/health/verdict',
    method: 'get',
    params: query
  })
}

//科室小结 界面 数据
export function getVerdictData(query){
  return request({
    url: 'total/health/getVerdictData',
    method: 'get',
    params: query
  })
}

//打开详情页面    判断按钮是否可用
export function test(query){
  return request({
    url: `total/health/test/?patientno=${query.patientno}`,
    method: 'get',
  })
}

//获取历史数据
export function getHistoryData(query){
  return request({
    url: `total/health/getHistoryData`,
    method: 'get',
    params: query
  })
}

//同步
export function synchronize(query){
  return request({
    url:`total/health/synchronize/?dh=${query.dh}&patientno=${query.patientno}`,
    method: 'post',
  })
}

//健康和职业总检保存 (审核/反审)
export function saveOrUpdate(query){
  return request({
    url: 'total/health/saveOrUpdate',
    method: 'post',
    data:query,
  })
}

//存入词库
export function maintain(query){
  return request({
    url: 'total/health/maintain',
    method: 'post',
    data: query
  })
}

//获取科室--此接口组件调用
export function getKs(query){
  return request({
    url: 'total/health/getKs',
    method: 'get',
    params: query
  })
}

//获取合并结论词
export function getMerge(query){
  return request({
    url: `total/health/getMerge?ids=${query}`,
    method: 'get'
  })
}

//获取合并结论词
export function getBreakUp(query){
  return request({
    url: `total/health/getBreakUp?ids=${query}`,
    method: 'get'
  })
}

//获取合并名称
export function getMergeConbination(query){
  return request({
      url: 'total/health/getMergeConbination',
      method: 'get',
      params: query
  })
}

//根据科室id和体检号获取体检项目和收费项目和小结 ----科室小结--分科-普通
export function getInspectChargeListData(query){
  return request({
    url: `total/health/getInspectChargeListData?patientno=${query.patientno}&sectionId=${query.sectionId}`,
    method: 'get',
  })
}

//获取提醒接口--数据表格
export function getRemindPatient(query){
  return request({
    url: "total/health/getRemindPatient",
    method: 'get',
    params:query
  })
}
//进入健康总检 获取综述以及健康建议,历史数据所需id
export function  gototal(query){
  return request({
    url: `total/health/gototal?flag=${query.flag}&patientno=${query.patientno}`,
    method: 'get',
  })
}

//进入健康总检--增加页
export function gototalAdd(query){
  return request({
    url :`total/health/gototalAdd?flag=${query.flag}&patientCode=${query.patientCode}`,
    method: 'get',
  })
}

//未完成
export function unfinish(query){
  return request({
    url: `total/health/unfinish?patientno=${query.patientno}`,
    method: "post",
  })
}

//获取分科检验数据
export function getgriddata(query){
  return request({
    url: "total/health/getgriddata",
    method: 'get',
    params:query
  })
}

//获取科室图片
export function viewImgTotal(query){
  return request({
    url: "total/health/viewImgTotal",
    method: 'get',
    params: query
  })
}

//分卡-检验-读卡
export function search(query){
  return request({
    url: "/abteilung/divisionInspection/search",
    method: 'get',
    params: query
  })
}

//健康总检--外送项目--获取与图片结果关联项目
export function getPictureData(query){
  return request({
    url: "/outside/sendGovern/getPictureData",
    method: 'get',
    params: query
  })
}

//健康总检--外送项目--获取已保存项目
export function getEditData(query){
  return request({
    url: "/outside/sendGovern/getEditData",
    method: 'get',
    params: query
  })
}