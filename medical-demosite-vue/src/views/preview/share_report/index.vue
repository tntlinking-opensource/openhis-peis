<!-- 分享报告-验证 开发人：麦沃德科技予安 -->
<template>
  <div class="preview-share-report">
    <div v-if="!isValidation">
      <div class="search-logo">
        <img src="../../../assets/logo/logo.png" />
      </div>
      <el-form class="search-box" ref="searchForm" @submit.native.prevent>
        <el-form-item class="search-input">
          <el-input v-model="extractedCode" type="password" placeholder="请输入提取码" @keyup.enter.native="handleSearch"></el-input>
        </el-form-item>
        <el-form-item class="search-button">
          <el-button type="primary" @click="handleSearch">查阅报告</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-else>
      <header class="list-header">
        <div>
          <el-input size="mini" style="width: 140px" v-model="patientName" placeholder="请输入姓名"></el-input>
          <el-button size="mini" class="addbtn" @click="handleValidity">查询</el-button>
        </div>
      </header>
      <div class="total-info">
        <span style="font-weight: 600">总共{{ fileNum }}个文件，</span>有效期{{ validityDate }}天
      </div>
      <div class="list-item" :class="{ 'fill-up': reportList.length == 1 }" v-for="item in reportList" :key="item.id" @click="toReport(item)">
        <div style="display: flex; align-items: center">
          <div class="item-name">{{ item.patientName }}</div>
          <span class="item-sex" style="background-color: skyblue" v-if="item.idSex == 0">男</span>
          <span class="item-sex" style="background-color: pink" v-if="item.idSex == 1">女</span>
        </div>
        <div class="item-tag">
          <div class="tag-date">{{ item.createTime }}</div>
          <div class="tag-btn" :style="{ '--theme': theme }">查看报告</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { validationCode, lastAccess } from '@/api/preview/share_report.js'
export default {
  name: 'ShareReport',
  data() {
    return {
      // 分享的链接id
      shareId: undefined,
      // 分享的链接提取码
      extractedCode: undefined,
      // 是否验证成功
      isValidation: false,
      // 分享个数
      fileNum: 0,
      // 有效期
      validityDate: 0,
      // 姓名筛选
      patientName: '',
      // 报告列表
      reportList: [],
      // 筛选列表
      validityList: [],
    }
  },
  computed: {
    theme() {
      return this.$store.state.settings.theme
    },
  },
  watch: {
    patientName: {
      handler(newVal) {
        if (!newVal) {
          this.reportList = this.validityList
        }
      },
    },
  },
  created() {
    document.title = '体检报告查询'
    this.shareId = this.$route.query.id
    this.extractedCode = this.$route.query.extractedCode
    if (!this.shareId) {
      this.$alert('分享报告链接错误,请重新获取分享', '提示')
      return
    }
    if (localStorage.getItem('shareId') && localStorage.getItem('extractedCode')) {
      if (localStorage.getItem('shareId') == this.shareId) {
        if (!this.extractedCode) {
          this.extractedCode = localStorage.getItem('shareId')
        }
      }
    }
    if (this.shareId && this.extractedCode) {
      this.handleSearch()
    }
  },
  methods: {
    // 验证提取码
    handleSearch() {
      if (!this.extractedCode) {
        this.$message({
          showClose: true,
          message: '提取码不能为空',
          type: 'warning',
        })
        return
      }
      validationCode({
        id: this.shareId,
        extractedCode: this.extractedCode,
      })
        .then(({ data }) => {
          localStorage.setItem('shareId', this.shareId)
          localStorage.setItem('extractedCode', this.extractedCode)
          this.isValidation = true
          this.reportList = data.list
          this.validityList = JSON.parse(JSON.stringify(data.list))
          this.fileNum = data.num
          this.validityDate = data.expirationDate
          lastAccess({
            id: this.shareId,
          })
        })
        .catch((error) => {
          console.error(error)
        })
    },
    // 跳转报告页面
    toReport(item) {
      const routeUrl = this.$router.resolve({
        name: 'IndividualHealthReport', //这里是跳转页面的name
        query: {
          from: 2,
          id: item.id,
          cid: item.branchId,
        },
      })
      window.open(routeUrl.href, '_blank')
    },
    // 筛选体检者姓名
    handleValidity() {
      if (this.patientName) {
        let array = []
        this.validityList.forEach((el) => {
          if (el.patientName.includes(this.patientName)) {
            array.push(el)
          }
        })
        this.reportList = array
      }
    },
  },
}
</script>

<style lang="scss">
.preview-share-report {
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

  .list-header {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding-top: 20px;

    .addbtn {
      color: #fbe7e8;
      border-color: #f2b8ba;
      background-color: #d41318;
    }
  }
  .total-info {
    margin-top: 12px;
  }

  .list-item {
    // width: 48%;
    padding: 12px;
    margin: 12px 0 0;
    border-radius: 5px;
    background: #fff;
    cursor: pointer;

    .item-name {
      color: #333;
      font-size: 16px;
      line-height: 22px;
    }
    .item-sex {
      color: white;
      margin-left: 8px;
      line-height: 22px;
      padding: 0 8px;
      border-radius: 3px;
      font-size: 12px;
    }

    .item-tag {
      margin-top: 8px;
      display: flex;
      align-items: center;

      .tag-date {
        color: #666;
        font-size: 14px;
        line-height: 20px;
        flex: 1;
      }

      .tag-btn {
        color: #{'var(--theme)'};
        font-size: 14px;
        font-weight: 400;
        line-height: 20px;
        margin-left: 12px;
      }
    }

    &.fill-up {
      width: 100%;
      margin: 12px 0 0;
    }
  }
}
@media screen and (max-width: 820px) {
  .preview-share-report .list-item {
    width: 100%;
    margin: 12px 0 0;
  }
}
</style>
