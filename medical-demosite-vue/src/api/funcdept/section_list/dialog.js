// 科室列表-弹窗相关  开发人：麦沃德科技 予安
import request from '@/utils/request'
import request2 from '@/utils/request2'

// 查看档案
export function getArchiveData(query) {
  return request({
    url: '/abteilung/division/getArchiveData',
    method: 'get',
    params: query
  })
}
// 查看档案右侧数据
export function getResultData(query) {
  return request({
    url: '/abteilung/division/getResultData',
    method: 'get',
    params: query
  })
}

// 科室-左侧数据
export function getCommonData(query) {
  return request({
    url: '/abteilung/division/getCommonData',
    method: 'get',
    params: query
  })
}
// 科室-科室小结
export function getInspectChargeListData(query) {
  return request({
    url: '/abteilung/division/getInspectChargeListData',
    method: 'get',
    params: query
  })
}

// 科室加项左侧数据
export function getSfxm(query) {
  return request({
    url: '/abteilung/division/getSfxm',
    method: 'get',
    params: query
  })
}
// 科室加项右侧数据
export function getAddListData(query) {
  return request({
    url: '/abteilung/division/getAddListData',
    method: 'get',
    params: query
  })
}
// 科室加项保存数据
export function saveOrUpdate(query) {
  return request({
    url: '/abteilung/division/saveOrUpdate',
    method: 'post',
    data: query
  })
}

// 我要提醒-获取科室
export function getRemindKs(patientcode) {
  return request({
    url: '/abteilung/division/getRemindKs',
    method: 'get',
    params: {
      patientcode
    }
  })
}
// 我要提醒-获取数据详情
export function getRemindData(query) {
  return request({
    url: '/abteilung/division/remind',
    method: 'get',
    params: query
  })
}
// 我要提醒-保存科室提醒
export function saveRemind(query) {
  return request({
    url: '/abteilung/division/saveRemind',
    method: 'post',
    params: query
  })
}

// 查看提醒-获取数据
export function getRemindPatient(query) {
  return request({
    url: '/abteilung/division/getRemindPatient',
    method: 'get',
    params: query
  })
}

// 设置艾迪康代码-获取数据
export function getAdiconGridData(query) {
  return request({
    url: '/abteilung/divisionInspection/getAdiconGridData',
    method: 'get',
    params: query
  })
}
// 设置艾迪康代码-保存
export function setAdicon(query) {
  return request({
    url: '/abteilung/divisionInspection/setAdicon',
    method: 'post',
    data: query
  })
}

// 清除数据
export function handleClearData(query) {
  return request({
    url: '/abteilung/divisionInspection/clear',
    method: 'DELETE',
    params: query
  })
}

// 快捷开药分页查询
export function handleDrugPage(query) {
  return request({
    url: '/drugstore/drug/page',
    method: 'GET',
    params: query
  })
}
// 快捷开药获取药品分类
export function getClassCheckData(query) {
  return request({
    url: '/drugstore/drug/getClassCheckData',
    method: 'GET',
    params: query
  })
}
// 快捷开药-右侧-获得已开药记录
export function getAddedData(query) {
  return request({
    url: '/drugstore/prescribe/getAddedData',
    method: 'GET',
    params: query
  })
}
// 快捷开药-保存
export function saveOrUpdateDrug(query) {
  return request({
    url: '/drugstore/prescribe/saveOrUpdate',
    method: 'POST',
    data: query
  })
}
// 快捷开药-右侧-获取上次体检开的什么药
export function getLastDrugs(query) {
  return request({
    url: '/drugstore/prescribe/getLastDrugs',
    method: 'GET',
    params: query
  })
}

// 科室咨询添加或修改
export function saveOrUpdateDep(query) {
  return request({
    url: '/abteilung/departmentCon/saveOrUpdate',
    method: 'POST',
    data: query
  })
}
// 科室咨询详情
export function divisionDep(query) {
  return request({
    url: '/abteilung/departmentCon/division',
    method: 'GET',
    params: query
  })
}

// 辅助功能-问卷
export function peisQuestionnaireSecond(query) {
  return request({
    url: '/abteilung/peisQuestionnaireSecond/view',
    method: 'GET',
    params: query
  })
}
// 获取问卷数据
export function getListData(query) {
  return request({
    url: '/abteilung/peisQuestionnaireSecond/search',
    method: 'get',
    params: query
  })
}
//保存问卷
export function saveListData(peisQuestionnaireSecond) {
  return request({
    url: '/abteilung/peisQuestionnaireSecond/saveOrUpdate',
    method: 'post',
    data: peisQuestionnaireSecond
  })
}

// 获取问卷数据（新）
export function getByCode(params) {
  return request({
    url: '/abteilung/questionnaire/getByCode',
    method: 'get',
    params
  })
}

//获取科室列表
export function getKsList(id) {
  return request({
    url: '/base/ksip/list/' + id,
    method: 'get',
  })
}

//传图上传
export function upLoadFilePic(params) {
  return request2({
    url: '/abteilung/image/upLoadFile',
    method: 'get',
    params
  })
}
// 上传数据
export function uploadData(params) {
  return request2({
    url: '/abteilung/image/uploadData',
    method: 'get',
    params
  })
}

//获取影像科室图片
export function getViewImg(query) {
  return request({
    url: '/abteilung/image/getViewImg',
    method: 'get',
    params: query
  })
}

// 删除影像科室图片
export function removeImg(ids) {
  return request({
    url: '/abteilung/image/imgs/remove',
    method: 'delete',
    params: ids
  })
}

// 上传
export function uploadFile(data) {
  return request({
    url: '/common/upload',
    method: 'post',
    headers: {
      repeatSubmit: false
    },
    data
  })
}

// 查看外送报告图片
export function getAdiconPictures(params) {
  return request({
    url: '/abteilung/division/getAdiconPictures',
    method: 'get',
    params
  })
}
// 删除艾迪康图片
export function deleteAdiconPictures(params) {
  return request({
    url: '/abteilung/division/deleteAdiconPictures',
    method: 'delete',
    params
  })
}
// 获取艾迪康结果
export function getAdiconList(data) {
  return request({
    url: '/lis/lisInterface/getAdiconList',
    method: 'post',
    data
  })
}

// 科室加项******************
// 查询科室加项数据
export function getTempFeeitem(params) {
  return request({
    url: '/abteilung/tempFeeitem/getTempFeeitem',
    method: 'get',
    params
  })
}
// 生成订单及流水号等
export function payment(data) {
  return request({
    url: '/abteilung/tempFeeitem/payment',
    method: 'post',
    data
  })
}
// 支付成功后回调接口
export function callBackApi(data) {
  return request({
    url: '/abteilung/tempFeeitem/callBack',
    method: 'post',
    data
  })
}