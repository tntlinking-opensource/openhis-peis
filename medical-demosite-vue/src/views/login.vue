<template>
  <div class="login" :style="backgroundStyle">
    <div style="color: #fff;font-size: 32px;position: fixed;top: 125px;left: 100px;">
      <img src="@/assets/logo/logo2.png" alt="logo" style="width: 60px;height: 60px;vertical-align: middle;" />
      欢迎使用{{ backgroundConfig.title }}
    </div>
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">{{ backgroundConfig.title }}</h3>
      <el-form-item prop="username">
        <el-autocomplete style="width: 100%" v-model="loginForm.username" value-key="text" :fetch-suggestions="querySearchAsync" placeholder="请输入用户名">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-autocomplete>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="请输入密码" @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaEnabled">
        <el-input ref="codeInput" v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%" @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img" />
        </div>
      </el-form-item>
      <!-- <el-checkbox v-model="loginForm.rememberMe" style="margin: 0px 0px 25px 0px">记住密码</el-checkbox> -->
      <el-form-item style="width: 100%">
        <el-button :loading="loading" type="primary" style="width: 100%" @click.native.prevent="handleLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
<!--    <div class="el-login-footer">-->
<!--      <span>{{ backgroundConfig.copyright }}</span>-->
<!--    </div>-->
    <div class="footer" >Copyright © 2025 湖北天天数链技术有限公司
      <!-- 第三行：协议名称（单独占一行） -->
      <div  style="margin-top: 2px;">
        <a
          href="https://open.tntlinking.com/communityTreaty"
          target="_blank"
          rel="noopener noreferrer"
          :style="{
            color: '#fff', // 复用主题色作为链接色
            textDecoration: 'none',
          }">
          本系统软件源代码许可来源于《天天开源软件（社区版）许可协议》
        </a>
      </div>
    </div>
    <el-dialog title="修改密码" :visible.sync="open" width="520px" :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" :inline="true" label-width="80px" hide-required-asterisk>
        <el-form-item prop="newPassword" label="新密码">
          <el-input style="width: 330px" type="password" v-model="form.newPassword" placeholder="请输入新密码"></el-input>
        </el-form-item>
        <el-form-item prop="newPassword2" label="确认密码">
          <el-input style="width: 330px" type="password" v-model="form.newPassword2" placeholder="请确认新密码"></el-input>
        </el-form-item>
      </el-form>
      <span style="color: red">新密码必须包含字母和数字并且长度不小于6位</span>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCodeImg, getLogUsers, getDomain, getBackgroundConfig } from '@/api/login'
import { updateUserPwd } from '@/api/system/user'

import Cookies from 'js-cookie'
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: 'Login',
  data() {
    return {
      defaultBackground: require('@/assets/images/login-background.jpg'),
      backgroundStyle: {
        backgroundImage: `url(${require('@/assets/images/login-background.jpg')})`, // 默认背景
        backgroundSize: 'cover'
      },
      codeUrl: '',
      loginForm: {
        username: '',
        password: '',
        // rememberMe: false,
        code: '',
        uuid: '',
      },
      loginRules: {
        username: [{ required: true, trigger: 'change', message: '请输入您的账号' }],
        password: [{ required: true, trigger: 'change', message: '请输入您的密码' }],
        code: [{ required: true, trigger: 'change', message: '请输入验证码' }],
      },
      loading: false,
      // 验证码开关
      captchaEnabled: false,
      // 注册开关
      register: false,
      redirect: undefined,
      // 密码强度不够则需要修改密码
      open: false,
      // 新密码表单
      form: {
        newPassword: '',
        newPassword2: '',
      },
      // 当前年份
      currentYear: '',
      // 背景配置
      backgroundConfig: {
        background: ''
      },
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true,
    },
  },
  created() {
    this.currentYear = new Date().getFullYear()
    this.getCode()
    this.getBackgroundConfig()
    if (window.location.href.includes('self_register')) {
      if (localStorage.getItem('selfName') && localStorage.getItem('selfPassword')) {
        this.loginForm.username = localStorage.getItem('selfName')
        this.loginForm.password = localStorage.getItem('selfPassword')
        this.$nextTick(() => {
          this.handleLogin()
        })
      }
    }
  },
  computed: {
    backgroundImage() {
      return this.backgroundConfig.background;
    }
  },
  methods: {
    // 获取背景配置
    getBackgroundConfig() {
      getBackgroundConfig().then((res) => {
        this.backgroundConfig = res.data;
        // 使用配置的背景图（如果有）
        if (res.data.background) {
          // 创建Image预加载背景图
          const img = new Image();
          img.src = res.data.background;

          img.onload = () => {
            // 图片加载成功，设置背景
            this.backgroundStyle.backgroundImage = `url(${res.data.background})`;
          };

          img.onerror = () => {
            // 图片加载失败时保留默认背景
            console.error('背景图加载失败，使用默认背景');
          };
        }
      }).catch((error) => {
        console.error('获取背景配置失败:', error);
      });
    },
    // 根据输入内容查询用户
    querySearchAsync(queryString = '', cb) {
      if (queryString || queryString == ' ') {
        queryString = queryString.trim()
      }
      getLogUsers({ key: queryString }).then(({ data }) => {
        cb(data)
      })
    },
    getCode() {
      getCodeImg().then((res) => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled
        if (this.captchaEnabled) {
          this.codeUrl = 'data:image/gif;base64,' + res.img
          this.loginForm.uuid = res.uuid
        }
      })
    },
    // 登录
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true
          // Cookies.set('username', encodeURIComponent(this.loginForm.username), { expires: 30 })
          this.$store
            .dispatch('Login', this.loginForm)
            .then(() => {
              getDomain()
                .then((res) => {
                  // Cookies.set('imgPath', res.data.isOnline == 1 ? res.data.rsPfDomain : res.data.rsLcDomain || process.env.VUE_APP_BASE_API, { expires: 30 })
                  Cookies.set('imgPath', res.data.isOnline == 1 ? res.data.rsPfDomain : '/resours_host', { expires: 30 })
                  Cookies.set('isOnline', res.data.isOnline, { expires: 30 })
                  //
                  if (res.data.isOnline == 1) {
                    let level = 0
                    if (/[0-9]/.test(this.loginForm.password)) {
                      //密码中是否有数字,或者是字母,或者是特殊符号
                      level++
                    }
                    if (/[a-zA-Z]/.test(this.loginForm.password)) {
                      // 判断密码中有没有字母
                      level++
                    }
                    if (level != 2) {
                      this.open = true
                      return
                    }
                  }
                  if (window.location.href.includes('self_register')) {
                    localStorage.setItem('selfName', this.loginForm.username)
                    localStorage.setItem('selfPassword', this.loginForm.password)
                  }
                  if (localStorage.getItem('selfName')) {
                    this.$router.push({ path: '/self_register/self_border' }).catch(() => {})
                  } else {
                    this.$router.push({ path: this.redirect || '/' }).catch(() => {})
                  }
                })
                .catch((error) => {})
            })
            .catch(() => {
              this.loading = false
              if (this.captchaEnabled) {
                this.getCode()
                this.loginForm.code = ''
                this.$refs.codeInput.focus()
              }
            })
        }
      })
    },
    // 确认更改密码
    submitForm() {
      let error = ''
      if (!this.form.newPassword) {
        error = '新密码不能为空'
      } else if (!this.form.newPassword2) {
        error = '确认密码不能为空'
      } else if (this.form.newPassword.length < 6) {
        error = '新密码长度不能小于6位'
      }
      let level = 0
      if (/[0-9]/.test(this.form.newPassword)) {
        //密码中是否有数字,或者是字母,或者是特殊符号
        level++
      }
      if (/[a-zA-Z]/.test(this.form.newPassword)) {
        // 判断密码中有没有字母
        level++
      }
      if (level != 2) {
        error = '新密码必须包含字母和数字'
      }
      if (error) {
        this.$alert(error, '提示')
        return
      }
      updateUserPwd(this.loginForm.password, this.form.newPassword).then(() => {
        this.$router.push({ path: this.redirect || '/' }).catch(() => {})
      })
    },
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: v-bind('backgroundImage');
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  margin-left: 40vw;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    // height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
@media (max-width: 500px) {
  .login-form {
    width: 90%;
    margin-left: 0;
  }
}
.footer {
  font-size: 12px;
  position: absolute;
  right: 20px;
  bottom: 20px;
  color: #fff;
}
</style>
