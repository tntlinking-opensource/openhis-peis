// 职业总检  开发人：麦沃德科技 予安/清风
import request from '@/utils/request'

// 查询健康总检列表
export function page(query) {
  return request({
    url: '/total/disease/page',
    method: 'get',
    params: query
  })
}

//职业总检-跳转职业总检页面
export function gototal(query){
  return request ({
    url:'/total/disease/gototal',
    method: 'get',
    params: query
  })
}

//职业总检-职业意见界面数据
export function getTreatmentData(query){
  return request ({
    url:'/total/disease/getTreatmentData',
    method: 'get',
    params: query
  })
}
//职业总检-科室小结数据
export function getVerdictData(query){
  return request ({
    url: "/total/disease/getVerdictData",
    method: 'get',
    params: query
  })
}
//职业总检-打开科室小结
export function verdict(query){
  return request ({
    url: "/total/disease/verdict",
    method: 'get',
    params: query
  })
}

//职业总检-获取分科-普通数据
export function getCommonData(query){
  return request ({
    url: "/total/disease/getCommonData",
    method: 'get',
    params: query
  })
}

//职业总检-打开分科-普通页面
export function common(query){
  return request ({
    url: "/total/disease/common",
    method: 'get',
    params: query
  })
}


//分科电测听
export function audio(query){
  return request ({
    url: "/total/disease/audio",
    method: 'get',
    params: query
  })
}

//未完成
export function getInspectChargeListData(query){
  return request ({
    url: `/total/disease/getInspectChargeListData?patientno=${query.patientno}&sectionId=${query.sectionId}`,
    method: 'get',
  })
}

//判断是否存在提醒
export function getRemindStr(query){
  return request ({
    url: "/abteilung/division/getRemindStr",
    method: 'get',
    data: query
  })
}

//是否总检
export function canTotal(query){
  return request ({
    url: `/total/health/canTotal?dh=${query.dh}&flag=${query.flag}&patientno=${query.patientno}`,
    method: 'get',
  })
}

//显示所有的职业病检查结论
export function showZySummaryService(query){
  return request ({
    url: "/total/disease/showZySummaryService",
    method: 'get',
    data: query
  })
}

//职业处理意见结论词--查询
export function searchTreatmentsuggestion(query){
  return request ({
    url: "/total/disease/searchTreatmentsuggestion",
    method: 'get',
    params: query
  })
}

//根据体检号获取该体检者所有收费项目
export function getItemByPeople(query){
  return request ({
    url: "/total/health/getItemByPeople",
    method: 'get',
    params: query
  })
}

//根据体检号获取该体检者所有收费项目 右侧
export function getRightItemByPeople(query){
  return request ({
    url: "/total/health/getRightItemByPeople",
    method: 'get',
    params: query
  })
}

//保存更新复查通知单
export function saveReview(query){
  return request ({
    url: "/total/health/saveReview",
    method: 'post',
    data: query
  })
}

//保存职业意见增加界面数据
export function saveTreatment(query){
  return request ({
    url: "/total/disease/saveTreatment",
    method: 'post',
    data: query
  })
}

//删除某条处理意见
export function removeRows(query){
  return request ({
    url: "/total/disease/removeRows",
    method: 'delete',
    params: query
  })
}

//解锁
export function unlock(query){
  return request ({
    url: "/total/disease/unlock",
    method: 'put',
    params: query
  })
}

//锁定
export function lock(query){
  return request ({
    url: "/total/disease/lock",
    method: 'put',
    params: query
  })
}

//未完成(反审)
export function unfinish(query){
  return request ({
    url: "/total/disease/unfinish",
    method: 'put',
    params: query
  })
}

//获得所有的职业危害因素
export function findAllHarmclass(query){
  return request ({
    url: "/harm/findAllHarmclass",
    method: 'get',
    params: query
  })
}

//分科-肺功能
export function lung(query){
  return request ({
    url: "/total/disease/lung",
    method: 'get',
    params: query
  })
}

//肺功能补充接口
export function case1(query){
  return request ({
    url: "/abteilung/division/case1",
    method: 'get',
    params: query
  })
}


//获取与图片结果关联项目--外送项目
export function getPictureData(query){
  return request ({
    url: "/outside/sendGovern/getPictureData",
    method: 'get',
    params: query
  })
}

//获取已保存项目--外送项目
export function getEditData(query){
  return request ({
    url: "/outside/sendGovern/getEditData",
    method: 'get',
    params: query
  })
}

//分科检验--检验科结果分页查询--用不到
export function searchDivision(query){
  return request ({
    url: "/abteilung/divisionInspection/searchDivision",
    method: 'get',
    params: query
  })
}
//分科检验--读卡
export function searchFkJy(query){
  return request ({
    url: "/abteilung/divisionInspection/search",
    method: 'get',
    params: query
  })
}

//职业总检-打开分科-检验
export function test(query){
  return request ({
    url: "/total/disease/test",
    method: 'get',
    params: query
  })
}

//查看科室图片
export function viewImgTotal(patientno){
  return request ({
    url: `/total/health/viewImgTotal?patientno=${patientno}`,
    method: 'get',
  })
}

//电测听-读卡
export function searchDct(query){
  return request ({
    url: "/abteilung/electroAudiometer/search",
    method: 'get',
    params: query
  })
}

//分科电测听读卡
export function eSearch(query){
  return request ({
    url: "/total/disease/search",
    method: 'get',
    params: query
  })
}

//打开复查通知界面
export function review(query){
  return request ({
    url: "/total/disease/review",
    method: 'get',
    params: query
  })
}

//获取医生数据
export function getDoctorData(query){
  return request ({
    url: "/total/disease/getDoctorData",
    method: 'get',
    params: query
  })
}

//保存职业意见增加界面数据
export function saveOrUpdate(query){
  return request ({
    url: "/total/disease/saveOrUpdate",
    method: 'post',
    data: query
  })
}

//保存职业意见增加界面数据
export function healthSaveOrUpdate(query){
  return request ({
    url: "/total/health/saveOrUpdate",
    method: 'post',
    data: query
  })
}

export function checkHarm(query){
  return request({
    url: `/total/disease/checkHarm?patientno=${query}`,
    method: 'get'
  })
}

// 获取复查项目及提示词
export function getReviewProjects(params){
  return request({
    url: `total/disease/getReviewProjects`,
    method: 'get',
    params
  })
}
