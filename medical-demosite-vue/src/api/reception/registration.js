// 登记  开发人：麦沃德科技半夏
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 体检者查询
export function getRecheckItems(query) {
  return request({
    url: '/SignInInspection/getRecheckItems',
    method: 'get',
    params: query
  })
}

// 补全套餐项目
export function completeTheProject(query) {
  return request({
    url: '/reception/register/completeTheProject',
    method: 'post',
    params: query
  })
}

// 拉取线上体检者数据
export function pullOnlineData(query) {
  return request({
    url: '/reception/register/pullOnlineData',
    method: 'post',
    params: query
  })
}


// 团体列表-搜索
export function getGroupForOrgData(query) {
  return request({
    url: '/reception/register/getGroupForOrgData',
    method: 'get',
    params: query
  })
}

// 最近体检人员列表-搜索
export function getPatientForRegister(query) {
  return request({
    url: '/reception/register/getPatientForRegister',
    method: 'get',
    params: query
  })
}

// 获取基本信息
export function getPatientData(query) {
  return request({
    url: '/reception/register/getPatientData',
    method: 'get',
    params: query
  })
}

// 获取体检者与收费项目信息
export function getCustomerData(query) {
  return request({
    url: '/reception/register/getCustomerData',
    method: 'get',
    params: query
  })
}

// 根据体检号查询不同状态的收费项目
export function getKindItems(query) {
  return request({
    url: '/reception/register/getKindItems',
    method: 'get',
    params: query
  })
}

// 获取体检者收费项目
export function getExamfeeitemData(query) {
  return request({
    url: '/reception/register/getExamfeeitemData',
    method: 'get',
    params: query
  })
}

// 获取套餐对应的折后价格
export function getTcZhPrice(query) {
  return request({
    url: '/SignInInspection/getTcZhPrice',
    method: 'get',
    params: query
  })
}

// 获取婚姻状况
export function getMarriageData() {
  return request({
    url: '/pacs/preregistration/getMarriageData',
    method: 'get',
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

// 获取籍贯
export function getAreaData(query) {
  return request({
    url: '/dictpayway/getAreaData',
    method: 'get',
    params: query
  })
}

// 取得已预约客户
export function getReservationUser(query) {
  return request({
    url: '/reception/register/getReservationUser',
    method: 'get',
    params: query
  })
}

// 获取记账单位
export function getJzOrg(query) {
  return request({
    url: '/reception/register/getJzOrg',
    method: 'get',
    params: query
  })
}

// 获取登记人
export function getLqrData(query) {
  return request({
    url: '/finance/sendCard/getLqrData',
    method: 'get',
    params: query
  })
}

// 验证是否有未退费的退项
export function refundValidate(query) {
  return request({
    url: '/reception/register/refundValidate',
    method: 'get',
    params: query
  })
}

// 保存/完成登记
export function savaRegister(data) {
  return request({
    url: data.isReservation == 1 ? '/reception/register/insert' : '/reception/register/sava',
    method: 'post',
    data: data
  })
}

// 反登记
export function setUnRegister(query) {
  return request({
    url: '/reception/charge/setUnRegister',
    method: 'put',
    params: query
  })
}

// 完成登记时，当前是第几次来本体检中心
export function getTjTime(query) {
  return request({
    url: '/reception/register/getTjTime',
    method: 'get',
    params: query
  })
}

// 通过身份证号获取最近一次人员信息
export function getDataByIdcard(query) {
  return request({
    url: '/reception/register/getDataByIdcard',
    method: 'get',
    params: query
  })
}

// 推项：获取收费项目列表
export function getSfxmData(query) {
  return request({
    url: '/reception/register/getSfxmData',
    method: 'get',
    params: query
  })
}

// 推项：获取体检套餐列表
export function getListData(query) {
  return request({
    url: '/reception/register/getListData',
    method: 'get',
    params: query
  })
}

// 推项：获取套餐下的收费项目
export function getItems(query) {
  return request({
    url: '/reception/register/getItemsData',
    method: 'get',
    params: query
  })
}

// 数据重发
export function middleDbInterface(data) {
  return request2({
    url: '/pacs_list/middleDbInterface/insert',
    method: 'post',
    params: data
  })
}

// 修改信息
export function saveEdit(data) {
  return request({
    url: '/reception/register/saveEdit',
    method: 'post',
    data: data
  })
}

// 返回设置数据
export function returnList() {
  return request({
    url: '/reception/register/returnList',
    method: 'get'
  })
}

// 用户头像上传
export function uploadImage(data) {
  return request({
    url: '/reception/register/upload',
    method: 'post',
    data: data
  })
}

// 预付方式收费保存
export function advanceRefund(data) {
  return request({
    url: '/reception/charge/advanceRefund',
    method: 'post',
    data
  })
}

// 获取收费项目图片
export function getPicture(id) {
  return request({
    url: '/items/getPicture/' + id,
    method: 'post'
  })
}
//获取问卷内容
export function questionData(query) {
  return request({
    url: '/reception/register/add',
    method: 'get',
    params: query
  })
}
//保存问卷内容
export function saveQuestionData(data) {
  return request({
    url: '/reception/register/getAnswer',
    method: 'post',
    data: data
  })
}

// 根据身份证号获取体检号
export function getPatientcodeByIdcard(idCard) {
  return request({
    url: '/reception/register/getPatientcodeByIdcard/' + idCard,
    method: 'post',
    params: {
      idCard
    }
  })
}

// 根据身份证号获取体检号
export function getVersion(params) {
  return request({
    url: '/reception/register/getVersion',
    method: 'get',
    params
  })
} 