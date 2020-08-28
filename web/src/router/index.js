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
          component: () => import('@/views/index.vue'),
        }
      ]
    },
    {
      path: '/tieba',
      name: 'tieba',
      component: Main,
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
    }
  ]
});
