// 对比报告  开发人: 麦沃德科技 清风
import request from '@/utils/request'

//下载报告
export function downloadWord(query) {
  return request({
    url: `total/RecordManage/downloadWord?ids=${query.ids}`,
    method: 'get',
  })
}

//获取对比报告列表信息
export function getCompareReport(query) {
  return request({
    url: `total/RecordManage/getCompareReport?patientarchiveno=${query.patientarchiveno}`,
    method: 'get',
    data: query
  })
}

//获取民族数据--完成
export function getNationData() {
  return request({
    url: 'total/RecordManage/getNationData',
    method: 'get',
  })
}

//获取某个档案下的所有体检者信息--完成
export function getPeispatient(query) {
  return request({
    url: `total/RecordManage/getPeispatient?patientarchiveno=${query.patientarchiveno}`,
    method: 'get',
  })
}

//分页查询--完成
export function page(query) {
  return request({
    url: 'total/RecordManage/page',
    method: 'get',
    params: query
  })
}

//生成对比报告预览
export function previewContrastReport(query) {
  return request({
    url: 'total/RecordManage/previewContrastReport',
    method: 'get',
    params: query
  })
}

//预览
export function sample(query) {
  return request({
    url: `total/RecordManage/sample?id=${query.id}`,
    method: 'get',
  })
}

//下载报告
export function downloadWordDbbg(query) {
  return request({
    url: `/total/RecordManage/downloadWord?ids=${query.ids}`,
    method: 'get',
  })
}

// 导入老系统体检号
export function importComparativeReport(data) {
  return request({
    url: `/reception/datamove/importComparativeReport`,
    method: 'post',
    data
  })
}