import Vue from 'vue'
import snackbarComponent from './snackbar.vue'

const SnackbarConstructor = Vue.extend(snackbarComponent);

function snackbar ({ message, position = "top", duration = 3000 }) {
  const snackBarDom = new SnackbarConstructor({
    el: document.createElement('div'),
    data () {
      return {
        position: position,
        message: message,
        open: false,
      }
    }
  })
  // 添加节点
  document.body.appendChild(snackBarDom.$el)
  snackBarDom.open = true
  // 过渡时间
  setTimeout(() => {
    snackBarDom.open = false
  }, duration)
}

export default snackbar;
