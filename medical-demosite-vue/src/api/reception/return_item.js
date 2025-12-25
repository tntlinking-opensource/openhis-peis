// 退费退项  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 退项保存
export function updateTui(data) {
  return request({
    url: '/reception/register/updateTui',
    method: 'POST',
    data
  })
}

// 获取右侧收费项目
export function getExamfeeByPatientCode(params) {
  return request({
    url: '/reception/register/getExamfeeByPatientCode',
    method: 'GET',
    params
  })
}

// 判断是否存在已退项未退费的检验科项目
export function checkInspection(params) {
  return request({
    url: '/reception/charge/checkInspection',
    method: 'GET',
    params
  })
}

// 通知检验科有退款项
export function noticeApi(params) {
  return request({
    url: '/pacs_list/middleDbInterface/insert',
    method: 'POST',
    params
  })
}

// 取消退款
export function returnItem(params) {
  return request({
    url: '/reception/register/returnItem',
    method: 'PUT',
    params
  })
}

// 老系统体检卡退款
export function receiveTjkCard(url, data) {
  return request({
    url: '/reception/charge/' + url,
    method: 'POST',
    data
  })
}

// 通联退款
export function refundThirdPayment(data) {
  return request({
    url: '/reception/charge/refundThirdPayment',
    method: 'POST',
    data
  })
}

// 退款管理分页查询
export function refundManagement(params) {
  return request({
    url: '/reception/charge/refundManagement',
    method: 'get',
    params
  })
}