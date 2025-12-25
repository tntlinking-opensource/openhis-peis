// 科室管理 批量录入  开发人：麦沃德科技 暴雨
import request from '@/utils/request'

// 分页查询
export function getListData(query) {
  return request({
    url: '/abteilung/sectionResultPlan/page',
    method: 'get',
    params: query
  })
}

// 体检号回车查询
export function checkCode(query) {
  return request({
    url: '/abteilung/sectionResultPlan/checkCode',
    method: 'get',
    params: query
  })
}

// 保存
export function sectionResultPlan(query) {
  return request({
    url: '/abteilung/sectionResultPlan',
    method: 'POST',
    data: query
  })
}

// 获取详情
export function getDetails(id) {
  return request({
    url: '/handle/handleNewProjects/' + id,
    method: 'get',
    params: {
      id,
    }
  })
}
