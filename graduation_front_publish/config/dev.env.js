'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

//开发环境 修改访问后端地址
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  //BASE_API: '"https://easy-mock.com/mock/5950a2419adc231f356a6636/vue-admin"',
  //改为nginx配置地址
  BASE_API: '"http://localhost:9002"',
})

