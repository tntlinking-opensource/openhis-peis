/*
 * @Author: Hikari
 * @Date: 2021-04-26 18:52:16
 * @LastEditTime: 2021-04-26 20:16:36
 * @LastEditors: Please set LastEditors
 * @Description: 读取身份证信息
 * @FilePath: /cloud-desk-top/components/common/idcard/sdk.js
 */
import Vue from 'vue';
import axios from 'axios'

const sdk = new function () {
  var type = null;

  // 初始化SDK 默认使用华视
  this.init_sdk = function (qudao = 'hs') {
    this.type = qudao;
  };

  // 读取身份证信息
  this.read_card = function () {
    return new Promise((resolve, reject) => {
      axios({
        method: 'post',
        url: 'http://localhost:19196/readCard'
      }).then(res => {
        let data = res.data;
        let result = data;
        if (result.resultFlag == 0) {
          resolve(result)
        } else {
          reject(result);
        }
      }).catch(err => {
        reject();
      })
      // Vue.prototype.$api.post(`/idcard/readCard`)
      // .then( res=>{
      //     let result = res;
      //     if( result.resultFlag == 0){
      //         debugger
      //         resolve( res )
      //     }else{
      //         reject();
      //     }
      // })
      // .catch( err => {
      //     reject();
      // })
    })
  };


  // 链接
  this.open_device = function () {
    return new Promise((resolve, reject) => {
      axios({
        method: 'post',
        url: 'http://localhost:19196/OpenDevice'
      }).then(res => {
        let data = res.data;
        let result = data;
        if (result.resultFlag == 0) {
          resolve(result)
        } else {
          reject();
        }
      })
        .catch(err => {
          reject();
        })
    })
  };


  // 断开
  this.close_device = function () {
    return new Promise((resolve, reject) => {
      axios({
        method: 'post',
        url: 'http://localhost:19196/CloseDevice'
      }).then(res => {
        let data = res;
        let result = data;
        if (result.resultFlag == 0) {
          resolve(result)
        } else {
          reject();
        }
      })
        .catch(err => {
          reject();
        })
    })
  };
};

export default sdk;