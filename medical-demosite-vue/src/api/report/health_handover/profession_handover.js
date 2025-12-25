// 职业报告交接  开发人：麦沃德科技暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/report/professionAssociate/page',
    method: 'get',
    params: query
  })
}


// 获取折线图数据
export function getChartData() {
  return request({
    url: '/report/professionAssociate/getChartData',
    method: 'POST'
  })
}

// 健康报告交接获取体检者数据
export function getPatientData(query) {
  return request({
    url: `/report/professionAssociate/getPatientData`,
    method: 'GET',
    params: query
  })
}

// 获取登录用户姓名
export function getLoginName() {
  return request({
    url: '/total/health/getLoginName ',
    method: 'GET'
  })
}

// 获取发放方式
export function getBoxData() {
  return request({
    url: `notificationmethod/getBoxData`,
    method: 'GET'
  })
}

// 交接
export function saveOrUpdate(query) {
  return request({
    url: `/report/professionAssociate/saveOrUpdate`,
    method: 'POST',
    data: query
  })
}

// 反交接
export function unaudit(query) {
  return request({
    url: `/report/professionAssociate/unaudit`,
    method: 'POST',
    data: query
  })
}

// 保存修改
export function saveEdit(query) {
  return request({
    url: `/report/professionAssociate/saveEdit`,
    method: 'PUT',
    params: query
  })
}


