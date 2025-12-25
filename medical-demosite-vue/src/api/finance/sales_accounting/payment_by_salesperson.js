// 销售员回款 开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(params) {
  return request({
    url: '/finance/financeInput/page',
    method: 'get',
    params
  })
}

// 获取年份
export function getAllYears() {
  return request({
    url: '/finance/financeInput/getAllYear',
    method: 'get',
  })
}

// 判断是否可财务录入
export function FinanceInput(params) {
  return request({
    url: '/finance/financeInput/isFinanceInput',
    method: 'get',
    params
  })
}

// 判断是否可编辑
export function isEdit(params) {
  return request({
    url: '/finance/financeInput/isEdit',
    method: 'get',
    params
  })
}

// 判断是否可查看
export function isView(params) {
  return request({
    url: '/finance/financeInput/isView',
    method: 'get',
    params
  })
}

// 数据保存或修改
export function saveOrUpdate(data) {
  return request({
    url: '/finance/financeInput/saveOrUpdate',
    method: 'POST',
    data
  })
}

// 详情
export function fiInput(ids) {
  return request({
    url: '/finance/financeInput/' + ids,
    method: 'GET',
  })
}
