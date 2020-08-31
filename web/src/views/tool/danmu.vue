<template>
  <mu-container>
    <mu-flex><h2>输入B站链接</h2></mu-flex>
    <mu-flex>支持链接类型：</mu-flex>
    <mu-flex>https://www.bilibili.com/bangumi/play/epxxxxxx、https://www.bilibili.com/video/avxxxxxx、https://www.bilibili.com/video/BVxxxxxx</mu-flex>
    <mu-form :model="linkForm" ref="linkForm" label-position="top" label-width="100">
      <mu-form-item prop="url" label="请输入B站链接" :rules="linkRules">
        <mu-text-field v-model="linkForm.url" placeholder="请输入B站链接"></mu-text-field>
      </mu-form-item>
    </mu-form>
    <mu-button color="secondary" @click="resolve">解析</mu-button>
    <div v-show="show" style="margin-top : 40px">
      <p><a href="#" target="_blank" id="comment-link-a">{{resLink}}</a></p>
      <p>请点击链接，并右键另存为</p>
      <p>弹幕转字幕工具：<a href="https://pan.baidu.com/s/1Az3pINb2vEaQlaj0YrvhsQ" target="_blank">Danmu2Ass</a></p>
      <p>推荐播放器：<a href="https://potplayer.org/" target="_blank">PotPlayer</a></p>
      <p>建议将PlayResY改为2倍(2160)即半屏弹幕食用更佳</p>
    </div>
  </mu-container>
</template>

<script>
  export default {
    name: "danmu",
    data() {
      return {
        linkForm: {
          url: ""
        },
        resLink: "",
        show: false,
        linkRules: [
          {validate: (val) => !!val, message: '必须填写B站链接'},
        ]
      }
    },
    methods: {
      resolve() {
        this.$refs.linkForm.validate().then((validate) => {
          if (validate) {
            this.$requests.post("/tool/danmu", this.linkForm).then((res) => {
              if (res.data.code === 0) {
                this.resLink = res.data.data;
                this.show = true;
              } else {
                this.$snackbar({message: res.data.msg});
              }
            })
          }
        })

      }
    }
  }
</script>

<style scoped>

</style>
