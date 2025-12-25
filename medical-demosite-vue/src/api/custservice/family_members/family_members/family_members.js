// 家庭会员 开发人：麦沃德科技暴雨
import request from '@/utils/request'

//分页查询
export function getListData(query) {
  return request({
    url: '/member/familyMember/page',
    method: 'get',
    params: query
  })
}

//获取家庭卡信息模块数据
export function getcardData(query) {
  return request({
    url: '/member/familyMember/add',
    method: 'get',
    params: query
  })
}

//获取其它家庭成员信息模块数据
export function getInfoListData(query) {
  return request({
    url: '/member/familyMember/getInfoListData',
    method: 'get',
    params: query
  })
}

//获取收费信息
export function getChargeInfoData(query) {
  return request({
    url: '/member/familyMember/getChargeInfoData',
    method: 'get',
    params: query
  })
}

//家庭卡号-加载
export function chargeLoadCard(query) {
  return request({
    url: '/member/familyCard/searchCardno',
    method: 'get',
    params: query
  })
}

//家庭卡号充值-加载
export function chargeLoadCards(query) {
  return request({
    url: '/member/familyMember/chargeLoadCard',
    method: 'get',
    params: query
  })
}

//身份证号-加载
export function searchIdcardno(query) {
  return request({
    url: '/member/familyMember/searchIdcardno',
    method: 'get',
    params: query
  })
}



//充值-保存
export function saveCharge(query) {
  return request({
    url: '/member/familyMember/saveCharge',
    method: 'POST',
    data: query
  })
}

//新增家庭卡办理-保存
export function SaOrUp(query) {
  return request({
    url: '/member/familyMember/saOrUp',
    method: 'POST',
    data: query
  })
}

//编辑-保存
export function saveOrUpdate(query) {
  return request({
    url: '/member/familyMember/saveOrUpdate',
    method: 'POST',
    data: query
  })
}

//获取详情
export function getdetails(id) {
  return request({
    url: '/member/familyMember/' + id,
    method: 'get'
  })
}

//获取全部支付方式
export function getPayWayData() {
  return request({
    url: '/dictpayway/getPayWayData',
    method: 'get'
  })
}

//获取分中心数据
export function getBranchData() {
  return request({
    url: '/sell/monthtarget/getBranchData',
    method: 'get'
  })
}

//体检卡搜索
export function getMedicalCardAutoComData() {
  return request({
    url: '/member/member/getMedicalCardAutoComData',
    method: 'get'
  })
}

//检查是否有重复的身份证号
export function checkIdcardno(query) {
  return request({
    url: '/member/familyMember/checkIdcardno',
    method: 'get',
    params: query
  })
}
