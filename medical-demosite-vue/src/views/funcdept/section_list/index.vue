<!-- 科室列表  开发人：麦沃德科技 予安 -->
<template>
  <div class="app-container section-container flex-direction-column">
    <!-- 筛选 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
      <el-form-item label="体检号" prop="patientCode">
        <el-input ref="patientCode" v-model="queryParams.patientCode" placeholder="请输入体检号" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item prop="autoFill">
        <el-checkbox v-model="queryParams.autoFill">是否补全</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleGet" style="margin-left: 32px">获取</el-button>
        <el-button type="primary" size="mini" icon="el-icon-refresh" @click="handleRepetition">数据重发</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="table-box" style="overflow-y: auto; background-color: #FFF8F4">
      <div class="section-list">
        <div class="section-item" :class="{ 'section-item': Number(item.fexaminated) === 1, 'section-item2': Number(item.fexaminated) === 0 }" v-for="item in sectionList" :key="item.id" @click="toSection(item)">
          <div class="name">
            <div class="CN">{{ item.name }}</div>
            <div class="EN">{{ item.englishName }}</div>
          </div>
          <img :src="imgPath + item.imgpath" alt="" />
        </div>
        <div class="empty-item" v-for="item in 5" :key="'empty' + item"></div>
      </div>
    </div>
  </div>
</template>
<script>
import { listSection, receiveApi } from '@/api/funcdept/section_list'
import { middleDbInterface } from '@/api/reception/registration'
import Cookies from 'js-cookie'
export default {
  name: 'Section_list',
  data() {
    return {
      // 筛选参数
      queryParams: {
        patientCode: undefined,
        autoFill: true,
      },
      // 科室列表
      sectionList: [],
      //图片地址
      imgPath: Cookies.get('imgPath'),
    }
  },
  created() {
    this.handleQuery()
  },
  mounted() {
    // let imgPath =  Cookies.get("imgPath");
    // this.imgPath = imgPath.substring(0,imgPath.length - 1);
    this.$refs.patientCode.focus()
  },
  methods: {
    // 搜索
    handleQuery() {
      // 获取科室列表
      const loading = this.$loading()
      listSection(this.queryParams).then(({ data }) => {
        if (this.queryParams.autoFill) {
          this.queryParams.patientCode = data.patientCode
        }
        this.sectionList = data.listQD
        loading.close()
      })
    },
    // 获取
    handleGet() {
      let patientcode = this.queryParams.patientCode
      if (!patientcode) {
        this.$alert('请输入体检号后重试', '提示')
        return
      }
      const msgId = this.$loading({
        lock: true,
        text: '获取数据中',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      receiveApi({ patientcode })
        .then((res) => {
          if (res.code != 200) {
            this.$alert('获取数据异常', '提醒').then(() => {
              this.handleQuery()
            })
          } else {
            this.$modal.alertSuccess('数据重发成功', '提醒')
            this.handleQuery()
          }
          msgId.close()
        })
        .catch(() => {
          msgId.close()
        })
    },
    // 数据重发
    handleRepetition() {
      let patientcode = this.queryParams.patientCode
      if (!patientcode) {
        this.$alert('请输入体检号后重试', '提示')
        return
      }
      const msgId = this.$loading({
        lock: true,
        text: '正在提交中间库',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      middleDbInterface({ patientcode })
        .then((res) => {
          if (res.code != 200) {
            // this.$confirm('中间库获取数据异常', '提醒', {
            //   confirmButtonText: '调用评价器',
            //   cancelButtonText: '取消',
            //   type: 'warning',
            // })
            //   .then(() => {
            //     this.mPageCtr()
            //     this.customerMark()
            //   })
            //   .catch(() => {
            //     this.mPageCtr()
            //   })
            this.$alert('中间库获取数据异常', '提醒').then(() => {
              this.handleQuery()
            })
          } else {
            this.$modal.alertSuccess('数据重发成功', '提醒')
            this.handleQuery()
          }
          msgId.close()
        })
        .catch(() => {
          msgId.close()
        })
    },
    // 跳转科室
    toSection(item) {
      let name = ''
      // if (item.englishName) {
      //   name = item.englishName.replace(/\s*/g, '')
      // } else {
      // }
      name = 'section' + item.id
      const obj = { path: '/funcdept/section/' + name, name }
      this.$tab.closePage(obj).then(() => {
        this.$router.push({ name, params: { patientcode: this.queryParams.patientCode } })
      })
    },
  },
}
</script>
<style lang="scss" scoped>
.section-container {
  padding: 0;
  background: transparent;

  > .el-form {
    padding: 20px 20px 0;
    background: #fff;
  }

  .section-list {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    .section-item {
      display: flex;
      align-items: center;
      justify-content: space-between;
      cursor: pointer;
      width: 15%;
      min-width: 278px;
      height: 115px;
      margin-top: 20px;
      background: #fff;
      box-shadow: 2px 4px 20px rgba(0, 0, 0, 0.06);
      transition: all 0.3s;
      border-radius: 10px;

      &:hover {
        transform: scale(1.1);
      }

      .name {
        padding: 13px 0 13px 24px;

        .CN {
          margin-bottom: 6px;
          font-weight: 600;
          font-size: 20px;
          line-height: 28px;
          color: #333333;
        }

        .EN {
          font-size: 14px;
          line-height: 20px;
          color: #c4c4c4;
          word-break: break-all;
        }
      }

      img {
        width: 115px;
        height: 115px;
      }
    }
    .section-item2 {
      display: flex;
      align-items: center;
      justify-content: space-between;
      cursor: pointer;
      width: 15%;
      min-width: 278px;
      height: 115px;
      margin-top: 20px;
      background: #fff;
      box-shadow: 2px 4px 20px rgba(0, 0, 0, 0.06);
      transition: all 0.3s;
      border-radius: 10px;
      border: 3px blue solid;
      &:hover {
        transform: scale(1.1);
      }

      .name {
        padding: 13px 0 13px 24px;

        .CN {
          margin-bottom: 6px;
          font-weight: 600;
          font-size: 20px;
          line-height: 28px;
          color: #333333;
        }

        .EN {
          font-size: 14px;
          line-height: 20px;
          color: #c4c4c4;
          word-break: break-all;
        }
      }

      img {
        width: 115px;
        height: 115px;
      }
    }

    .empty-item {
      opacity: 0;
      width: 15%;
      min-width: 278px;
      height: 0;
    }
  }
}
</style>
