// 科室列表  开发人：麦沃德科技 予安
import request from '@/utils/request'

// 查询科室列表
export function listSection(query) {
  return request({
    url: '/abteilung/division/list',
    method: 'get',
    params: query
  })
}

// 获取pacs结果
export function receiveApi(params) {
  return request({
    url: '/pacs/pacsInterface/receive',
    method: 'post',
    params
  })
}

// **************科室相关***************
// 查看列队数据
export function getRankData(query) {
  return request({
    url: '/abteilung/division/getRankData',
    method: 'get',
    params: query
  })
}

// 查看结论词(通用)
export function getjlcData(query) {
  return request({
    url: '/abteilung/divisionFaircheck/jlcData',
    method: 'get',
    params: query
  })
}
// 保存结论词
export function saveOrUpdateJlc(query) {
  return request({
    url: '/abteilung/division/saveOrUpdateJlc',
    method: 'POST',
    data: query
  })
}

// 判断是否存在提醒
export function getRemindStr(query) {
  return request({
    url: '/abteilung/division/getRemindStr',
    method: 'get',
    params: query
  })
}

// 获取审核之后的数据
export function getNkCheckedData(query) {
  return request({
    url: '/abteilung/division/getNkCheckedData',
    method: 'get',
    params: query
  })
}

// 碳14、13数据
export function getCarbonData(query) {
  return request({
    url: '/abteilung/division/c13',
    method: 'get',
    params: query
  })
}

// #region 一般检查
// 一般检查 读卡
export function getGIData(query) {
  return request({
    url: '/abteilung/divisionFaircheck/search',
    method: 'get',
    params: query
  })
}
// 一般检查营养数据
export function getCommonStateData() {
  return request({
    url: '/abteilung/divisionFaircheck/getCommonStateData',
    method: 'get',
  })
}
// 一般检查获取体征词
export function getSign(query) {
  return request({
    url: '/abteilung/divisionFaircheck/getSign',
    method: 'get',
    params: query
  })
}
// 一般检查保存结论词
export function saveGIJlc(query) {
  return request({
    url: '/abteilung/divisionFaircheck/saveJlc',
    method: 'post',
    data: query
  })
}
// 一般检查审核
export function saveOrUpdateGI(query) {
  return request({
    url: '/abteilung/divisionFaircheck/saveOrUpdate',
    method: 'post',
    data: query
  })
}
// 一般检查反审核
export function reverseGI(query) {
  return request({
    url: '/abteilung/divisionFaircheck/reverse',
    method: 'put',
    params: query
  })
}
// #endregion

// #region 问诊检查
// 问诊检查读卡
export function getInquiryData(query) {
  return request({
    url: '/abteilung/division/commonjobinquiry',
    method: 'get',
    params: query
  })
}

// 问诊检查反审核
export function reverseInquiry(query) {
  return request({
    url: '/abteilung/division/commonjobinquiryReverse',
    method: 'put',
    params: query
  })
}
// #endregion

// #region 电测听
// 电测听读卡
export function getAudiometryData(query) {
  return request({
    url: '/abteilung/electroAudiometer/search',
    method: 'get',
    params: query
  })
}
// 电测听审核
export function saveOrUpdateEA(query) {
  return request({
    url: '/abteilung/electroAudiometer/saveOrUpdate',
    method: 'post',
    data: query
  })
}
// 电测听反审核
export function reverseEA(query) {
  return request({
    url: '/abteilung/electroAudiometer/reverse',
    method: 'put',
    params: query
  })
}
// #endregion

// 无图像检查、图像检查科室详情
export function getCase1Data(query) {
  return request({
    url: '/abteilung/division/case1',
    method: 'get',
    params: query
  })
}
// 无图像检查、图像检查科室小结
export function case1AutoSave(query) {
  return request({
    url: '/abteilung/division/autosave',
    method: 'POST',
    data: query
  })
}
// 无图像检查、图像检查审核
export function caseshenhe(query) {
  return request({
    url: '/abteilung/division/shenhe',
    method: 'post',
    data: query
  })
}
// 无图像检查、图像检查反审核
export function caseReverse(query) {
  return request({
    url: '/abteilung/division/caseReverse',
    method: 'put',
    params: query
  })
}

// 骨密度(读卡)
export function bmdGmd(query) {
  return request({
    url: '/abteilung/bmd/bmd',
    method: 'get',
    params: query
  })
}
// 骨密度(审核)
export function dmbshenheGmd(query) {
  return request({
    url: '/abteilung/bmd/dmbshenhe',
    method: 'post',
    data: query
  })
}

// 骨密度(审核数据)
export function getBmdCheckedDataGmd(query) {
  return request({
    url: '/abteilung/bmd/getBmdCheckedData',
    method: 'get',
    params: query
  })
}

// 骨密度(反审合)
export function caseReverseGmd(query) {
  return request({
    url: '/abteilung/bmd/caseReverse',
    method: 'put',
    params: query
  })
}

// 视力检查(读卡)
export function visionSljc(query) {
  return request({
    url: '/abteilung/vision/vision',
    method: 'get',
    params: query
  })
}

// 视力检查(反审核)
export function caseReverseSljc(query) {
  return request({
    url: '/abteilung/vision/caseReverse',
    method: 'put',
    params: query
  })
}

// 视力检查(审核)
export function shenheSljc(query) {
  return request({
    url: '/abteilung/vision/shenhe',
    method: 'post',
    data: query
  })
}

// 获取科室列表
export function getKsList(ksId) {
  return request({
    url: '/base/ksip/list/' + ksId,
    method: 'get',
    params: { ksId }
  })
}

// 获取博英心电图结果
export function getXDTResult(patientcode) {
  return request({
    url: '/boying/getResult/' + patientcode,
    method: 'get',
    params: {}
  })
  // const url = '/boying/getResult/' + patientcode;
  // return fetch(url, {
  //   method: 'GET',
  //   headers: { 
  //     'Content-Type': 'application/json'
  //   }
  // });
}
