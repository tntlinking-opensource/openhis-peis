<!-- 报告搜索-验证 开发人：麦沃德科技半夏 -->
<template>
  <div class="preview-search-report">
    <div class="search-logo">
      <img src="../../../assets/logo/logo.png" />
    </div>
    <el-form class="search-box" ref="searchForm" :model="searchForm" @submit.native.prevent>
      <el-form-item class="search-input" prop="mobile">
        <el-input v-model="searchForm.mobile" type="tel" placeholder="请输入联系电话" @keyup.enter.native="sendCode"></el-input>
      </el-form-item>
      <el-form-item class="search-input" prop="validCode">
        <el-input ref="validCode" v-model="searchForm.validCode" type="number" placeholder="请输入验证码" @keyup.enter.native="handelSearch"></el-input>
        <el-button type="primary" v-if="intDiff > 0">{{ intDiff }}秒后重试</el-button>
        <el-button type="primary" @click="sendCode" v-else>获取验证码</el-button>
      </el-form-item>
      <!-- <el-form-item class="search-input" prop="mobile">
        <el-input v-model="searchForm.mobile" placeholder="请输入身份证号"></el-input>
      </el-form-item>
      <div class="search-tips">请输入身份证号</div> -->
      <el-form-item class="search-button">
        <el-button type="primary" @click="handelSearch">查询报告</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { sendLoginCode, smsLogin } from '@/api/preview/search_report.js'
import { getUrlQuery } from '@/utils/getUrlQuery.js'
export default {
  name: 'SearchReport',
  data() {
    return {
      // 倒计时
      intDiff: 0,
      // 计时器
      regTimer: null,
      // 表单
      searchForm: {
        mobile: null,
        validCode: null,
      },
    }
  },
  created() {
    document.title = '体检报告查询'
    // let reportToken = getUrlQuery2('reportToken')
    let reportToken = this.$route.query.reportToken ? this.$route.query.reportToken.split(' ').join('+') : ''
    let reportPhone = getUrlQuery('reportPhone')
    if (reportToken && reportPhone) {
      var data = {
        token: reportToken,
        mobile: reportPhone,
      }
      localStorage.setItem('searchReport', JSON.stringify(data))
    }
  },
  mounted() {
    if (localStorage.getItem('searchReport')) {
      let routeUrl = this.$router.resolve({
        name: 'SearchList',
      })
      window.open(routeUrl.href, '_self')
    }
  },
  methods: {
    // 发送验证码
    sendCode() {
      if (!this.searchForm.mobile) {
        this.$message({
          showClose: true,
          message: '联系电话不能为空',
          type: 'warning',
        })
        return
      }
      const reg = /^1[0-9]{10,10}$/
      if (!reg.test(this.searchForm.mobile)) {
        this.$message({
          showClose: true,
          message: '手机号格式不正确',
          type: 'warning',
        })
        return
      }
      sendLoginCode({ mobile: this.searchForm.mobile }).then((res) => {
        this.intDiff = 59
        this.regTimer = setInterval(() => {
          this.intDiff--
          if (this.intDiff == 0) {
            this.intDiff = null
            clearInterval(this.regTimer)
          }
        }, 1000)
        this.$refs.validCode.focus()
      })
    },
    // 搜索
    handelSearch() {
      if (!this.searchForm.mobile) {
        this.$message({
          showClose: true,
          message: '联系电话不能为空',
          type: 'warning',
        })
        return
      }
      const reg = /^1[0-9]{10,10}$/
      if (!reg.test(this.searchForm.mobile)) {
        this.$message({
          showClose: true,
          message: '手机号格式不正确',
          type: 'warning',
        })
        return
      }
      if (!this.searchForm.validCode) {
        this.$message({
          showClose: true,
          message: '验证码不能为空',
          type: 'warning',
        })
        return
      }
      smsLogin(this.searchForm)
        .then((res) => {
          var data = {
            mobile: this.searchForm.mobile,
            token: res.accessToken,
            userId: res.userId,
          }
          localStorage.setItem('searchReport', JSON.stringify(data))
          // window.sessionStorage.setItem('searchReport', JSON.stringify(data))
          let routeUrl = this.$router.resolve({
            name: 'SearchList',
          })
          window.open(routeUrl.href, '_self')
        })
        .catch((error) => {
          console.error(error)
          // this.$modal.msgError('发送短信失败，请1分钟后再试')
        })
    },
  },
}
</script>

<style lang="scss">
.preview-search-report {
  padding: 0 20px;

  .search-logo {
    width: 100%;
    height: 34vh;
    min-height: 160px;
    display: flex;
    justify-content: center;
    align-items: flex-end;

    img {
      width: 140px;
      height: auto;
      margin: 0 auto;
    }
  }

  .search-box {
    margin: 12vh auto 0;
    max-width: 450px;

    .search-input {
      margin: 12px 0 0;
      background: #f6f6f6;
      border-radius: 5px;

      .el-form-item__content {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }

      .el-input {
        flex: 1;

        .el-input__inner {
          background: transparent;
          color: #999;
          font-size: 14px;
          height: 40px;
          line-height: 40px;
          // border: none;
        }
      }

      .el-button {
        width: 96px;
        height: 40px;
        line-height: 40px;
        padding: 0;
        border-radius: 5px;

        span {
          font-size: 14px;
        }
      }
    }
    .search-tips {
      margin-top: 8px;
      font-size: 12px;
      color: red;
      text-align: right;
    }

    .search-button {
      margin: 24px 0 0;

      .el-button {
        width: 100%;
        height: 50px;
        line-height: 50px;
        padding: 0;
        border-radius: 5px;
      }
    }
  }
}
</style>
