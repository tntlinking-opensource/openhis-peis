'use strict'
const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

const CompressionPlugin = require('compression-webpack-plugin')

const name = process.env.VUE_APP_TITLE || '沃德健管云平台' // 网页标题

const port = process.env.port || process.env.npm_config_port || 80 // 端口

const version = new Date().getTime();
// vue.config.js 配置说明
//官方vue.config.js 参考文档 https://cli.vuejs.org/zh/config/#css-loaderoptions
// 这里只列一部分，具体配置参考文档
module.exports = {
  // 部署生产环境和开发环境下的URL。
  // 默认情况下，Vue CLI 会假设你的应用是被部署在一个域名的根路径上
  // 例如 https://www.ruoyi.vip/。如果应用被部署在一个子路径上，你就需要用这个选项指定这个子路径。例如，如果你的应用被部署在 https://www.ruoyi.vip/admin/，则设置 baseUrl 为 /admin/。
  publicPath: process.env.NODE_ENV === "production" ? "/" : "/",
  // 在npm run build 或 yarn build 时 ，生成文件的目录名称（要和baseUrl的生产环境路径一致）（默认dist）
  outputDir: 'dist',
  // 用于放置生成的静态资源 (js、css、img、fonts) 的；（项目打包之后，静态资源会放在这个文件夹下）
  assetsDir: 'static',
  // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
  productionSourceMap: false,
  transpileDependencies: ['quill'],
  // webpack-dev-server 相关配置
  devServer: {
    host: '0.0.0.0',
    port: port,
    open: true,
    proxy: {
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      // '/dev_api': {
      //   // target: 'XXX.XXX.XXX:8080',
      //   target: 'http://XXX.XXX.XXX:8090',
      //   changeOrigin: true,
      //   pathRewrite: {
      //     '^/dev_api': '',
      //   },
      // },
      // 图片地址
      '/resours_host': {
        // target: 'XXX.XXX.XXX:8080',
        // 锦都
        target: 'http://XXX.XXX.XXX:8080',
        changeOrigin: true,
        pathRewrite: {
          '^/resours_host': '',
        },
      },
      // 线上接口-查询报告
      [process.env.VUE_APP_ONLINE_API]: {
        target: `https://XXX.XXX.XXX`,
        changeOrigin: true,
        pathRewrite: {
          ['^' + process.env.VUE_APP_ONLINE_API]: '',
        },
      },
    },
    disableHostCheck: true,
  },
  css: {
    loaderOptions: {
      sass: {
        sassOptions: { outputStyle: "expanded" },
      },
      // 自适应视窗
      // postcss: {
      //   plugins: [
      //     require('postcss-pxtorem')({
      //       rootValue: 16, // 换算的基数
      //       minPixelValue: 2,
      //       selectorBlackList: [], // 忽略转换正则匹配项 列入一些ui库, ['.el'] 就是忽略elementUI库
      //       propList: ['*'],
      //     }),
      //   ]
      // }
    },
    extract: {
      // 修改打包后css文件名   // css打包文件，添加时间戳
      filename: `css/[name].${version}.css`,
      chunkFilename: `css/[name].${version}.css`
    }
  },
  configureWebpack: {
    name: name,
    resolve: {
      alias: {
        '@': resolve('src'),
      },
    },
    plugins: [
      // http://doc.ruoyi.vip/ruoyi-vue/other/faq.html#使用gzip解压缩静态文件
      new CompressionPlugin({
        cache: true, // 不启用文件缓存
        test: /\.(js|css|html)?$/i, // 压缩文件格式
        filename: '[path][base].gz[query]', // 压缩后的文件名
        algorithm: 'gzip', // 使用gzip压缩
        minRatio: 0.3, // 压缩率小于1才会压缩
        deleteOriginalAssets: false, // 压缩后删除原文件
      }),
    ],
    output: { // 输出重构  打包编译后的 文件名称  【模块名称.版本号.时间戳】
      filename: `static/js/[name].${version}.js`,
      chunkFilename: `static/js/[name].${version}.js`
    },
  },
  chainWebpack(config) {
    config.plugins.delete('preload') // TODO: need test
    config.plugins.delete('prefetch') // TODO: need test

    // set svg-sprite-loader
    config.module.rule('svg').exclude.add(resolve('src/assets/icons')).end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/assets/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]',
      })
      .end()

    config.when(process.env.NODE_ENV !== 'development', config => {
      config
        .plugin('ScriptExtHtmlWebpackPlugin')
        .after('html')
        .use('script-ext-html-webpack-plugin', [
          {
            // `runtime` must same as runtimeChunk name. default is `runtime`
            inline: /runtime\..*\.js$/,
          },
        ])
        .end()
      config.optimization.splitChunks({
        chunks: 'all',
        cacheGroups: {
          libs: {
            name: 'chunk-libs',
            test: /[\\/]node_modules[\\/]/,
            priority: 10,
            chunks: 'initial', // only package third parties that are initially dependent
          },
          // 专门为大型库创建独立的chunk
          pdfLib: {
            name: 'chunk-pdf-lib',
            test: /[\\/]node_modules[\\/](pdf-lib)[\\/]/,
            priority: 25,
            chunks: 'all'
          },
          echarts: {
            name: 'chunk-echarts',
            test: /[\\/]node_modules[\\/](echarts)[\\/]/,
            priority: 20,
            chunks: 'all'
          },
          cornerstone: {
            name: 'chunk-cornerstone',
            test: /[\\/]node_modules[\\/](cornerstone-core|cornerstone-math|cornerstone-tools|cornerstone-wado-image-loader|cornerstone-web-image-loader|dicom-parser)[\\/]/,
            priority: 15,
            chunks: 'all'
          },
          jspdf: {
            name: 'chunk-jspdf',
            test: /[\\/]node_modules[\\/](jspdf)[\\/]/,
            priority: 18,
            chunks: 'all'
          },
          elementUI: {
            name: 'chunk-elementUI', // split elementUI into a single package
            priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
            test: /[\\/]node_modules[\\/]_?element-ui(.*)/, // in order to adapt to cnpm
          },
          commons: {
            name: 'chunk-commons',
            test: resolve('src/components'), // can customize your rules
            minChunks: 3, //  minimum common number
            priority: 5,
            reuseExistingChunk: true,
          },
        },
      })
      config.optimization.runtimeChunk('single'),
      {
        from: path.resolve(__dirname, './public/robots.txt'), //防爬虫文件
        to: './', //到根目录下
      }
    })
    config.plugin('compressionPlugin').use(new CompressionPlugin({
      test: /\.(js|css|less)$/, // 匹配文件名
      threshold: 900, // 对超过1k的数据压缩
      deleteOriginalAssets: false, // 不删除源文件
      minRatio: 0.3, // 压缩比
    }))
  },
}