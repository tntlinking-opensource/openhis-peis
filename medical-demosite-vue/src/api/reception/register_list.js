// 登记列表  开发人：麦沃德科技半夏
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 分页查询
export function getListData(query) {
  return request({
    url: '/reception/register/page',
    method: 'get',
    params: query
  })
}

// 获取体检团体
export function getGroupList(key) {
  return request({
    url: '/abteilung/sectionResultPlan/getOrgs',
    method: 'get',
    params: key
  })
}

// 批量登记查询
export function getPatientForCode(query) {
  return request({
    url: '/reception/register/getPatientForCode',
    method: 'get',
    params: query
  })
}

// 获取所有套餐
export function getAllComboAndMealData(query) {
  return request({
    url: '/reception/register/getAllComboAndMealData',
    method: 'get',
    params: query
  })
}

// 批量登记
export function saveBatchRegister(params) {
  return request2({
    url: '/reception/register/saveBatchRegister',
    method: 'post',
    params
  })
}

// 备单人员登记操作
export function getBdRegisterData(query) {
  return request({
    url: '/reception/register/getBdRegisterData',
    method: 'get',
    params: query
  })
}

// 禁检/反禁检
export function savePausedStatus(data) {
  return request({
    url: '/reception/register/savePausedStatus',
    method: 'put',
    params: data
  })
}

// 批量设置统收限额
export function saveTsLimitEdit(data) {
  return request({
    url: '/reception/register/saveTsLimitEdit',
    method: 'post',
    data: data
  })
}

// 根据id获取多位体检者
export function getPatientsForID(params) {
  return request({
    url: '/reception/guideSheet/getPatientsForID',
    method: 'get',
    params
  })
}

// 获取条码打印机型号
export function barcodePrinter() {
  return request({
    url: '/report/getReprot/barcodePrinter',
    method: 'GET'
  })
}

// 获取短信模板
export function getComboData(params) {
  return request({
    url: '/member/smsFollowUp/getComboData',
    method: 'get',
    params
  })
}
// 发送短信
export function saveOrUpdateMsg(data) {
  return request({
    url: '/reception/register/saveOrUpdateMsg',
    method: 'post',
    data
  })
}
// 取消发送短信
export function cancelSmsPredetection(params) {
  return request({
    url: '/reception/register/cancelSmsPredetection',
    method: 'put',
    params
  })
}

// 导入老系统未检用户
export function importPeispatient(data) {
  return request({
    url: '/reception/datamove/importPeispatient',
    method: 'post',
    data
  })
}

// 平安好医生到检确认
export function confirmOrder(params) {
  return request({
    url: '/reception/register/confirmOrder',
    method: 'post',
    params
  })
}