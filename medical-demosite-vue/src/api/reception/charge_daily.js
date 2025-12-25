// 收费日报  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 每日客服报表统计
export function getChargeList(url, params) {
  return request({
    url,
    method: 'get',
    params
  })
}

// 每日客服报表统计-今日费用结算情况
export function getBackInfoData(params) {
  return request({
    url: '/reception/chargeQuery/getBackInfoData',
    method: 'get',
    params
  })
}

// 每日记账报表统计
export function getEveryDayJZDataList(params) {
  return request({
    url: '/reception/chargeQuery/getEveryDayJZDataList',
    method: 'get',
    params
  })
}

// 每日记账报表统计
export function getTonglianDataList(params) {
  return request({
    url: '/reception/chargeQuery/getTonglianDataList',
    method: 'get',
    params
  })
}

//金蝶客户数据更新
export function upgradeCustomer() {
  return request({
    url:'/finance/kingdee/upgradeCustomer',
    method: 'post'
  })
}

//上传团检费用
export function taskAboutUpdateG(query) {
  return request({
    url:'/finance/kingdee/taskAboutUpdateG',
    method: 'post',
    params:query
  })
}

//上传个检费用
export function taskAboutUpdateT(query) {
  return request({
    url:'/finance/kingdee/taskAboutUpdateT',
    method: 'post',
    params:query
  })
}

//上传团体结算 
export function updateGroupSettlement(query){
  return request({
    url: '/finance/kingdee/updateGroupSettlement',
    method: 'post',
    params:query
  })
}

//上传个检结算 
export function updateSettlement(query){
  return request({
    url: '/finance/kingdee/updateSettlement',
    method: 'post',
    params:query
  })
}

//上传统收
export function updateSettlementOfOrg(query){
  return request({
    url: '/finance/kingdee/updateSettlementOfOrg',
    method: 'post',
    params:query
  })
}

//检验统收团体金蝶名
export function checkOrgKingdeeName(query){
  return request({
    url: '/finance/kingdee/checkOrgKingdeeName',
    method: 'post',
    params:query
  })
}

//积分和体检卡月度团体结算
export function updateMonthGroup(query){
  return request({
    url: '/finance/kingdee/updateMonthGroup',
    method: 'post',
    params:query
  })
}

//积分和体检卡月度个检结算
export function updateMonth(query){
  return request({
    url: '/finance/kingdee/updateMonth',
    method: 'post',
    params:query
  })
}

// 每日疫苗收费统计-分页查询
export function getVaccinum(params){
  return request({
    url: '/reception/chargeQuery/getVaccinum',
    method: 'get',
    params
  })
}