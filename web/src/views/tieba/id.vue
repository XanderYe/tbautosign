<template>
  <mu-container class="id-list">
    <mu-button color="secondary" @click="openAddModal">添加</mu-button>
    <mu-data-table stripe :columns="columns" @sort-change="" :data="tbuserList" style="margin-top: 16px;">
      <template slot-scope="scope">
        <td>{{scope.row.baiduId}}</td>
        <td>{{scope.row.baiduName}}</td>
        <td class="bduss-column">{{scope.row.bduss}}</td>
        <td>
          <mu-button icon small color="primary" @click="openEditModal(scope.row)">
            <mu-icon value="edit"></mu-icon>
          </mu-button>
          <mu-button icon small color="red" @click="openDeleteModal(scope.row.tid)">
            <mu-icon value="delete"></mu-icon>
          </mu-button>
        </td>
      </template>
    </mu-data-table>

    <mu-dialog width="600" :title="dialogTitle" transition="scale" :fullscreen="!$store.state.app.isDesktop"
               :overlay-close="false" :open.sync="addModal">
      <mu-form :model="tbuser" ref="tbuserForm" label-position="top" label-width="100">
        <mu-form-item prop="bduss" label="请输入BDUSS" :rules="bdussRule">
          <mu-text-field v-model="tbuser.bduss" placeholder="请输入BDUSS"></mu-text-field>
        </mu-form-item>
      </mu-form>
      <mu-expansion-panel>
        <div slot="header">点击查看在 Chrome 浏览器下的绑定方法</div>
        <mu-flex>1.使用 Chrome 或 Chromium 内核的浏览器</mu-flex>
        <mu-flex>2.打开百度首页 http://www.baidu.com/</mu-flex>
        <mu-flex>3.右键，点击 查看网页信息</mu-flex>
        <mu-flex>4.确保已经登录百度，然后点击 显示 Cookie 和网站数据</mu-flex>
        <mu-flex>5.如图，依次展开 passport.baidu.com -> Cookie -> BDUSS</mu-flex>
        <mu-flex>6.按下 Ctrl+A 全选，然后复制并输入到上面的表单即可</mu-flex>
        <mu-flex>请注意，一旦退出登录，可能导致 BDUSS 失效，因此建议在隐身模式下登录</mu-flex>
      </mu-expansion-panel>
      <mu-button slot="actions" flat color="primary" @click="addModal = false">取消</mu-button>
      <mu-button slot="actions" flat color="primary" @click="saveTbuser">保存</mu-button>
    </mu-dialog>

    <mu-dialog title="提示" width="400" max-width="80%" :esc-press-close="false" :overlay-close="false"
               :open.sync="deleteModal">
      是否确认删除所选ID？
      <mu-button slot="actions" flat color="primary" @click="deleteModal = false">取消</mu-button>
      <mu-button slot="actions" flat color="primary" @click="deleteConfirm">确认</mu-button>
    </mu-dialog>
  </mu-container>
</template>

<script>
  import Util from "@/libs/util";
  export default {
    name: "id",
    data() {
      return {
        tbuserList: [],
        tbuser: {
          tid: null,
          baiduId: "",
          baiduName: "",
          bduss: null,
        },
        addModal: false,
        dialogTitle: "添加BDUSS",
        // 删除确认框
        deleteModal: false,
        bdussRule: [
          {validate: (val) => !!val, message: '必须填写BDUSS'},
        ],
        columns: [
          {
            title: "百度ID",
            name: "baiduId"
          },
          {
            title: "百度昵称",
            name: "baiduName"
          },
          {
            title: "BDUSS",
            name: "bduss",
            width: 360,
          },
          {
            title: "操作",
            name: "operate"
          }
        ],
      }
    },
    methods: {
      getList() {
        this.$requests.get("/tbuser/tbusers").then(res => {
          if (res.data.code === 0) {
            this.tbuserList = res.data.data;
          }
        })
      },
      openAddModal() {
        this.tbuser = {
          tid: null,
          baiduId: "",
          baiduName: "",
          bduss: null,
        };
        this.addModal = true;
      },
      openEditModal(data) {
        this.tbuser = data;
        this.dialogTitle = "修改BDUSS";
        this.addModal = true;
      },
      saveTbuser() {
        this.$refs.tbuserForm.validate().then((validate) => {
          if (validate) {
            this.$requests.post("/tbuser/BDUSS", this.tbuser).then((res) => {
              if (res.data.code === 0) {
                this.addModal = false;
                this.$snackbar({message: "保存成功"});
                this.getList();
              } else {
                this.$snackbar({message: res.data.msg});
              }
            })
          }
        })
      },
      openDeleteModal(id) {
        this.tbuser.tid = id;
        this.deleteModal = true;
      },
      deleteConfirm() {
        this.$requests.post("/tbuser/delete", {id: this.tbuser.tid}).then((res) => {
          if (res.data.code === 0) {
            this.deleteModal = false;
            this.$snackbar({message: "删除成功"});
            this.getList();
          } else {
            this.$snackbar({message: res.data.msg});
          }
        })
      },
    },
    mounted() {
      const isLogin = Boolean(Number(localStorage.getItem("isLogin")));
      if (isLogin) {
        this.getList();
      } else {
        this.$snackbar({message: "使用此功能请先登录"});
        Util.$emit('login');
      }
    }
  }
</script>

<style lang="less">
  .id-list {
    .bduss-column {
      white-space: nowrap;
      overflow-x: scroll;
      text-overflow: clip;
    }
  }
</style>
