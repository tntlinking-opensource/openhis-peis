<!-- 影像科室 麦沃德科技 矢北、予安 -->
<template>
  <div class="app-container section-container flex-direction-column">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" @submit.native.prevent>
      <el-form-item label="体检号" prop="patientcode">
        <el-input v-model="queryParams.patientcode" placeholder="请输入体检号" clearable style="width: 230px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="是否补全" prop="autoFill">
        <el-checkbox v-model="queryParams.autoFill"></el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <div class="table-box" style="overflow-y: auto; background-color: #FFF8F4" id="mainBox">
      <div class="section-list">
        <div class="section-item" :class="{ 'section-item': Number(item.fExaminated) === 1, 'section-item2': Number(item.fExaminated) === 0 }" v-for="(item, index) in sectionList" :key="index" @click="toSection(item)">
          <div class="name">
            <div class="CN">{{ item.deptName }}</div>
            <div class="EN">{{ item.ENname }}</div>
          </div>
          <img :src="imgPath + item.imgpath" alt="" />
        </div>
        <div class="empty-item" v-for="item in 5" :key="'empty' + item"></div>
      </div>
    </div>
  </div>
</template>
<script>
import { getListData } from '@/api/PACS/imaging_department.js'
export default {
  name: 'Imaging_department',
  data() {
    return {
      queryParams: {
        patientcode: undefined,
        autoFill: true,
      },
      // 科室列表
      sectionList: [],
      // 图片前缀
      imgPath: this.$getCookie('imgPath'),
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取科室列表
    getList() {
      const loading = this.$loading({ target: '#mainBox' })
      getListData(this.queryParams)
        .then(({ data }) => {
          this.sectionList = data.list
          if (data.patientCode) {
            this.queryParams.patientcode = data.patientCode
          }
          loading.close()
        })
        .catch(() => {
          loading.close()
        })
    },
    // 搜索
    handleQuery() {
      this.getList()
    },
    // 跳转科室
    toSection(item) {
      let name = ''
      switch (item.deptNo) {
        case '143':
          name = 'ColorUltrasound'
          break
        case '24':
          name = 'RadiologyDR'
          break
        case '171':
          name = 'RadiologyCR'
          break
        case '165':
          name = 'MolybdenumTarget'
          break
        case '173':
          name = 'RadiologyCT'
          break
        case '402848e3625a920201625ff99a3404a5':
          name = 'MagneticResonance'
          break
      }
      const obj = { path: '/PACS/' + name, name }
      this.$tab.closePage(obj).then(() => {
        this.$router.push({ name, params: { patientcode: this.queryParams.patientcode } })
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
