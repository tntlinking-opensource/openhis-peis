import Vue from 'vue'

import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的路由个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限
 * meta : {
    noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
    title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
    icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
    breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
    activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect'),
      },
    ],
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true,
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true,
  },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true,
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true,
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true },
      },
    ],
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' },
      },
    ],
  },
  // pacs科室
  // 高清屏查看dicom图片
  {
    path: '/PACS/HDScreen',
    component: () => import('@/views/PACS/imaging_department/section_modal/HDScreen.vue'),
    name: 'HDScreen',
  },
  // 预览pacs报告
  {
    path: '/PACS/pacs_report',
    component: () => import('@/views/PACS/imaging_department/section_modal/pacs_report.vue'),
    name: 'pacsReport',
  },
  // 科室报告
  {
    path: '/preview/section_report',
    component: () => import('@/views/preview/section_report/index.vue'),
    name: 'SectionReport',
    hidden: true,
  },

  // 健康团检报告
  {
    path: '/preview/group_report/health',
    component: () => import('@/views/preview/group_report/health.vue'),
    name: 'GroupHealthReport',
    hidden: true,
  },
  // 职业团检报告
  {
    path: '/preview/group_report/disease',
    component: () => import('@/views/preview/group_report/disease.vue'),
    name: 'GroupDiseaseReport',
    hidden: true,
  },
  // 职业复查报告
  {
    path: '/preview/recheck_report/index',
    component: () => import('@/views/preview/recheck_report/index.vue'),
    name: 'RecheckReport',
    hidden: true,
  },
  // 健康个检报告
  {
    path: '/preview/individual_report/health',
    component: () => import('@/views/preview/individual_report/health.vue'),
    name: 'IndividualHealthReport',
    hidden: true,
  },

  // 职业个检报告
  {
    path: '/preview/individual_report/disease',
    component: () => import('@/views/preview/individual_report/disease.vue'),
    name: 'IndividualDiseaseReport',
    hidden: true,
  },
  // 临时报告
  {
    path: '/preview/individual_report/temporary',
    component: () => import('@/views/preview/individual_report/temporary.vue'),
    name: 'IndividualTemporaryReport',
    hidden: true,
  },
  // 隐私报告
  {
    path: '/preview/individual_report/private',
    component: () => import('@/views/preview/individual_report/private.vue'),
    name: 'IndividualPrivateReport',
    hidden: true,
  },
  // 检验报告
  {
    path: '/preview/individual_report/inspect',
    component: () => import('@/views/preview/individual_report/inspect.vue'),
    name: 'IndividualInspectReport',
    hidden: true,
  },
  // 对比报告
  {
    path: '/preview/contrast_report',
    component: () => import('@/views/preview/contrast_report/index.vue'),
    name: 'ContrastReport',
    hidden: true,
  },
  // 条码和导引单
  {
    path: '/preview/bills_model_list',
    component: () => import('@/views/preview/bills_model_list/index.vue'),
    name: 'BillsModelList',
    hidden: true,
  },

  // 条码
  {
    path: '/preview/bills_model_list/bills.vue',
    component: () => import('@/views/preview/bills_model_list/bills.vue'),
    name: 'Bills',
    hidden: true,
  },

  // 职业结果告知书
  {
    path: '/preview/results_notice',
    component: () => import('@/views/preview/results_notice/index.vue'),
    name: 'ResultsNotice',
    hidden: true,
  },

  // 搜索报告-验证
  {
    path: '/preview/search_report/search',
    component: () => import('@/views/preview/search_report/search.vue'),
    name: 'SearchReport',
    hidden: true,
  },
  // 搜索报告-列表
  {
    path: '/preview/search_report/list',
    component: () => import('@/views/preview/search_report/list.vue'),
    name: 'SearchList',
    hidden: true,
  },
  // 搜索报告-列表
  {
    path: '/preview/search_report/report_view',
    component: () => import('@/views/preview/search_report/report_view.vue'),
    name: 'ShareReportView',
    hidden: true,
  },

  // 自助登记机首页
  {
    path: '/self_register',
    hidden: false,
    permissions: ['reception:selfhelp_register:list'],
    component: () => import('@/views/reception/selfhelp_register/index'),
    name: 'selfHelp',
  },
  // 自助登记机自助登记
  {
    path: '/self_register/self_border',
    hidden: false,
    permissions: ['reception:selfhelp_register:self_border'],
    component: () => import('@/views/reception/selfhelp_register/self_border'),
    name: 'selfBoard',
  },
  // 自助登记机加项缴费
  {
    path: '/self_register/self_additem',
    hidden: false,
    permissions: ['reception:selfhelp_register:self_additem'],
    component: () => import('@/views/reception/selfhelp_register/add_item'),
    name: 'selfAddItem',
  },
  // 自助登记机排队管理
  {
    path: '/self_register/self_queue',
    hidden: false,
    permissions: ['reception:selfhelp_register:self_queue'],
    component: () => import('@/views/reception/selfhelp_register/queue_management'),
    name: 'selfQueueManagement',
  },
  // 自助登记机报告打印
  {
    path: '/self_register/self_report',
    hidden: false,
    permissions: ['reception:selfhelp_register:self_report'],
    component: () => import('@/views/reception/selfhelp_register/report_print'),
    name: 'selfReportPrint',
  },

  // 分享报告-列表
  {
    path: '/preview/report_share',
    component: () => import('@/views/preview/share_report/index.vue'),
    name: 'ShareList',
    hidden: true,
  },

  // 线上审批
  {
    path: '/approval/approval_online',
    component: () => import('@/views/approval/approval_online/index.vue'),
    name: 'ApprovalOnline',
    hidden: true,
  },
  // 线上审批-项目
  {
    path: '/approval/approval_online_item',
    component: () => import('@/views/approval/approval_online/approval_online_item.vue'),
    name: 'ApprovalOnlineItem',
    hidden: true,
  },
  // 批量打印报告
  {
    path: '/report/report_print/batch_printing',
    component: () => import('@/views/report/report_print/batch_printing.vue'),
    name: 'BatchPrinting',
    hidden: true,
  },
  // 预览报告
  {
    path: '/report/report_print/preview_report',
    component: () => import('@/views/report/report_print/preview_report.vue'),
    name: 'PreviewReport',
    hidden: true,
  },
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: '分配角色', activeMenu: '/system/user' },
      },
    ],
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: '分配用户', activeMenu: '/system/role' },
      },
    ],
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: '字典数据', activeMenu: '/system/dict' },
      },
    ],
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: { title: '调度日志', activeMenu: '/monitor/job' },
      },
    ],
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: '修改生成配置', activeMenu: '/tool/gen' },
      },
    ],
  },

  // 备单-编辑
  {
    path: '/reception/prepare_order/edit',
    component: Layout,
    hidden: true,
    permissions: ['reception:prepareOrder:edit'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/reception/prepare_order/edit'),
        name: 'PrepareOrderEdit',
        meta: { title: '备单管理', activeMenu: '/reception/prepare_order/edit' },
      },
    ],
  },
  // 备单/登记列表-登记
  {
    path: '/reception/registration',
    component: Layout,
    hidden: true,
    permissions: ['reception:registration:list'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/reception/registration'),
        name: 'Registration',
        meta: { title: '登记', activeMenu: '/reception/registration' },
      },
    ],
  },

  // 查看登记列表人员
  {
    path: '/reception/register_view',
    component: Layout,
    hidden: true,
    permissions: ['reception:registerView:list'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/reception/register_view'),
        name: 'RegisterView',
        meta: { title: '查看登记列表人员', activeMenu: '/reception/register_view' },
      },
    ],
  },

  // 退费退项
  {
    path: '/reception',
    component: Layout,
    hidden: true,
    permissions: ['reception:returnItem:list'],
    children: [
      {
        path: 'return_item',
        component: () => import('@/views/reception/return_item'),
        name: 'ReturnItem',
        meta: { title: '退费退项', activeMenu: '/reception/return_item' },
      },
    ],
  },

  // 团检报告审批-综合样本分析
  {
    path: '/groupreport/audit_analyze',
    component: Layout,
    hidden: true,
    permissions: ['groupreport:groupreportApproval:samples'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/groupreport/audit_analyze'),
        name: 'AuditAnalyze',
        meta: { title: '综合样本分析', activeMenu: '/groupreport/audit_analyze' },
      },
    ],
  },
  //撞单排查详情
  {
    path: '/sale/order_management/bump_detail',
    component: Layout,
    hidden: false,
    permissions: ['sale:orderManagement:bumpOrder:detail:list'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/sale/order_management/bump_detail'),
        name: 'bumpOrderDetail',
        meta: { title: '撞单排查详情', activeMenu: '/sale/order_management/bump_detail' }
      }
    ]
  },
  //销售主页
  {
    path: '/SaleIndex',
    component: Layout,
    hidden: false,
    permissions: ['sale:index:list'],
    children: [
      {
        path: 'SaleIndex',
        component: () => import('@/views/sale/index'),
        name: 'SaleIndex',
        meta: { title: '销售主页', activeMenu: '/sale/SaleIndex' }
      }
    ]
  },

  // AI套餐推荐
  {
    path: '/ai',
    component: Layout,
    hidden: false,
    permissions: ['ai:meal:list'],
    children: [
      {
        path: 'meal',
        component: () => import('@/views/ai/meal/index'),
        name: 'AiMealRecommend',
        meta: { title: 'AI套餐推荐', icon: 'guide', activeMenu: '/ai/meal' }
      },
      {
        path: 'report',
        component: () => import('@/views/ai/report/index'),
        name: 'AiReportInterpret',
        meta: { title: 'AI报告解读', icon: 'guide', activeMenu: '/ai/report' }
      }
    ]
  },

  //PACS-放射科DR
  {
    path: '',
    component: Layout,
    hidden: false,
    permissions: ['PACS:imagingDepartment:list'],
    children: [
      {
        path: 'PACS/color_ultrasound',
        component: createCustomComponent('ColorUltrasound', import('@/views/PACS/imaging_department/section_modal/index')),
        name: 'ColorUltrasound',
        meta: { title: '彩超', deptNo: '143' }
      },
      {
        path: 'PACS/radiology_DR',
        component: createCustomComponent('RadiologyDR', import('@/views/PACS/imaging_department/section_modal/index')),
        name: 'RadiologyDR',
        meta: { title: '放射科(DR)', deptNo: '24' }
      },
      {
        path: 'PACS/radiology_CR',
        component: createCustomComponent('RadiologyCR', import('@/views/PACS/imaging_department/section_modal/index')),
        name: 'RadiologyCR',
        meta: { title: '放射科(CR)', deptNo: '171' }
      },
      {
        path: 'PACS/molybdenum_target',
        component: createCustomComponent('molybdenumTarget', import('@/views/PACS/imaging_department/section_modal/index')),
        name: 'MolybdenumTarget',
        meta: { title: '数字钼靶', deptNo: '165' }
      },
      {
        path: 'PACS/radiology_CT',
        component: createCustomComponent('RadiologyCT', import('@/views/PACS/imaging_department/section_modal/index')),
        name: 'RadiologyCT',
        meta: { title: '放射科(CT)', deptNo: '173' }
      },
      {
        path: 'PACS/magnetic_resonance',
        component: createCustomComponent('MagneticResonance', import('@/views/PACS/imaging_department/section_modal/index')),
        name: 'MagneticResonance',
        meta: { title: '磁共振', deptNo: '402848e3625a920201625ff99a3404a5' }
      },
    ]
  },
]

function createCustomComponent(name, component) {
  return {
    name,
    data() {
      return { component: null }
    },
    async created() {
      if (component instanceof Promise) {
        try {
          const module = await component
          this.component = module?.default
        } catch (error) {
          console.error(`can not resolve component ${name}, error:`, error)
        }
        return
      }
      this.component = component
    },
    render(h) {
      return this.component ? h(this.component) : null
    },
  }
}
// 防止连续点击多次路由报错
let routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch((err) => err)
}

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes,
})