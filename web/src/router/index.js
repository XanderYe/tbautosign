import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/views/Main.vue';

Vue.use(Router);

export const router = new Router({
  routes: [
    {
      path: '/',
      name: 'main',
      redirect: '/index',
      component: Main,
      children: [
        {
          path: 'index',
          title: '首页',
          name: 'index',
          meta: {
            title: '贴吧签到助手',
            auth: true
          },
          component: () => import('@/views/index.vue'),
        }
      ]
    },
    {
      path: '/tieba',
      name: 'tieba',
      component: Main,
      title: "贴吧管理",
      children: [
        {
          path: 'id',
          name: 'baidu-id',
          meta: {
            title: '百度ID管理',
            auth: true
          },
          component: () => import('@/views/tieba/id.vue'),
        },
        {
          path: 'manage',
          name: 'manage',
          meta: {
            title: '签到管理',
            auth: true
          },
          component: () => import('@/views/tieba/manage.vue'),
        },
      ]
    },
    {
      path: '/tool',
      name: 'tool',
      component: Main,
      title: "小工具",
      children: [
        {
          path: 'danmu',
          name: 'danmu',
          meta: {
            title: '获取B站弹幕',
            auth: true
          },
          component: () => import('@/views/tool/danmu.vue'),
        },
        {
          path: 'avbv',
          name: 'avbv',
          meta: {
            title: 'AVBV互转',
            auth: true
          },
          component: () => import('@/views/tool/avbv.vue'),
        }
      ]
    }
  ]
});
