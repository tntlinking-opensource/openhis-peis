// 危急值提报  开发人：麦沃德科技 清风
import request from '@/utils/request'

// 查询我的客户列表
export function getPage(query) {
    return request({
      url: '/abteilung/CrisisValue/page',
      method: 'get',
      params: query
    })
}

//危急值添加
export function crisisValueAdd(query){
    return request({
        url: '/abteilung/CrisisValue/saveOrUpdate',
        method: 'post',
        data: query
    })
}

//危急值删除
export function crisisValueDelete(ids){
    return request({
        url: `/abteilung/CrisisValue/${ids}`,
        method: 'delete',
    })
}
//危急值详情
export function crisisValueDetail(id){
    return request({
        url: `abteilung/CrisisValue/${id}`,
        method: 'get',
    })
}

//危急值业务处理
export function saveYw(query){
    return request({
        url: '/abteilung/CrisisValue/saveYw',
        method: "post",
        data: query
    })
}

//回访处理
export function saveHf(query){
    return request ({
        url:"/abteilung/CrisisValue/saveHf",
        method: "post",
        data:query,
    })
}

//危急值专家处理
export function saveZj(query){
    return request({
        url: '/abteilung/CrisisValue/saveZj',
        method: 'post',
        data: query
    })
}

//危急值业务处理详情--获取通知列表
export function getIssueWayData(){
    return request({
        url: '/SignInInspection/getIssueWayData',
        method: 'get',
    })
}

//根据体检号搜索信息
export function getTjzData(query){
    return request({
        url: '/addSatisficing/getTjzData',
        method: 'get',
        params:query
    })
}