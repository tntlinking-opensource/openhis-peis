// 职业体检处理意见 开发人：麦沃德科技 清风
import request from '@/utils/request'

//分页查询-职业体检处理意见
export function listData(data){
    return request({
        url:'/zyVsSummary/list',
        method:'get',
        params:data,
    })
}

//根据输入码获取职业病-职业体检处理意见
export function inputCodeData(data){
    return request({
        url:'/zyVsSummary/getZybData',
        method:'get',
        params:data,
    })
}

//获取危害因素下拉-职业体检处理意见
export function selectDangerData(data){
    return request({
        url:'/zyVsSummary/getWhysData',
        method:'get',
        params:data,
    })
}

//获取危害因素分类下拉-职业体检处理意见
export function selectClassData(data){
    return request({
        url:'/zyVsSummary/getWhflData',
        method:'get',
        params:data,
    })
}

//获取禁忌疾病-职业体检处理意见
export function diseaseData(data){
    return request({
        url:'/zyVsSummary/getJjjbData',
        method:'get',
        params:data,
    })
}

//获取结论下拉列表-职业体检处理意见
export function resultData(data){
    return request({
        url:'/zyVsSummary/getJcjlData',
        method:'get',
        params:data,
    })
}

//根据危害因素获取其输入码-职业体检处理意见
export function getInputCode(data){
    return request({
        url:'/zyVsSummary/getInputCode',
        method:'get',
        params:data,
    })
}

//删除-职业体检处理意见
export function deleteData(ids){
    return request({
        url:'/zyVsSummary/remove/' + ids,
        method:'delete',
    })
}

//批量保存-职业体检处理意见
export function saveData(data){
    return request({
        url:'/zyVsSummary/saveBatch',
        method:'post',
        data,
    })
}

//新增或编辑-职业体检处理意见
export function saveOrUpdateData(data){
    return request({
        url:'/zyVsSummary/saveOrUpdate',
        method:'post',
        data:data,
    })
}

//同步-职业体检处理意见
export function syncData(data){
    return request({
        url:'/zyVsSummary/synchronize',
        method:'get',
        params:data,
    })
}

//详情-职业体检处理意见
export function queryData(id){
    return request({
        url:'/zyVsSummary/' + id,
        method:'get',
    })
}