<template>
  <mu-container class="manage">
    <mu-tabs inverse color="primary" indicator-color="secondary"  center :value.sync="tabIndex">
      <mu-tab v-for="tbuser in tbuserList" :key="tbuser.tid" @change="changeTab(tbuser)">{{tbuser.baiduName}}</mu-tab>
    </mu-tabs>
    <mu-button color="secondary" @click="refresh">刷新贴吧</mu-button>
    <mu-button color="secondary" @click="sign">{{signBtnLoading ? '签到中' : '一键签到'}}</mu-button>
    <mu-data-table stripe :columns="columns" :sort.sync="sort" @sort-change="handleSortChange" :data="tbinfoList" style="margin-top: 16px;">
      <template slot-scope="scope">
        <td>{{scope.row.tiebaId}}</td>
        <td>{{scope.row.title}}</td>
        <td>{{scope.row.curScore}}</td>
        <td>{{scope.row.levelId}}</td>
        <td>{{scope.row.levelName}}</td>
        <td :class="scope.row.status ? 'signed' : 'not-sign'">{{scope.row.status ? "已签" : "未签"}}</td>
        <td>
          <mu-tooltip content="签到">
            <mu-button icon small color="primary" @click="sign(scope.row.tid)">
              <mu-icon value="done"></mu-icon>
            </mu-button>
          </mu-tooltip>
          <mu-tooltip content="删除">
            <mu-button icon small color="red" @click="openDeleteModal(scope.row.tid)">
              <mu-icon value="delete"></mu-icon>
            </mu-button>
          </mu-tooltip>
        </td>
      </template>
    </mu-data-table>
    <mu-flex justify-content="end" style="margin-top: 16px; padding-bottom: 16px;" v-if="tbinfoList.length > 0">
      <mu-pagination raised circle :page-size="rows" :total="total" :current.sync="page"
                     @change="getTbInfoList"></mu-pagination>
    </mu-flex>
    <mu-dialog title="提示" width="400" max-width="80%" :esc-press-close="false" :overlay-close="false"
               :open.sync="deleteModal">
      是否确认删除？
      <mu-button slot="actions" flat color="primary" @click="deleteModal = false">取消</mu-button>
      <mu-button slot="actions" flat color="primary" @click="deleteConfirm">确认</mu-button>
    </mu-dialog>
  </mu-container>
</template>

<script>
  import Util from "@/libs/util";
  export default {
    name: "manage",
    data() {
      return {
        tbuserList: [],
        tabIndex: 0,
        tbuser: {
          tid: null,
          baiduId: "",
          baiduName: "",
          bduss: null,
        },
        tbinfoList: [],
        columns: [
          {
            title: "贴吧ID",
            name: "tiebaId"
          },
          {
            title: "贴吧名称",
            name: "title"
          },
          {
            title: "经验",
            name: "curScore",
            sortable: true
          },
          {
            title: "等级",
            name: "levelId"
          },
          {
            title: "头衔",
            name: "levelName"
          },
          {
            title: "状态",
            name: "status",
            sortable: true
          },
          {
            title: "操作",
            name: "operate"
          },
        ],
        sort: {
          name: '',
          order: 'asc'
        },
        page: 1,
        rows: 20,
        total: 0,
        signBtnLoading: false,
        deleteModal: false,
        deleteId: null
      }
    },
    methods: {
      getTbuserList() {
        this.$requests.get("/tbuser/tbusers").then(res => {
          if (res.data.code === 0) {
            this.tbuserList = res.data.data;
            if (this.tbuserList.length > 0) {
              this.tbuser = this.tbuserList[0];
              this.getTbInfoList();
            } else {
              this.$snackbar({message: "请先绑定百度ID"})
            }
          } else {
            this.$snackbar({message: res.data.msg});
          }
        })
      },
      getTbInfoList() {
        this.$requests.get("/tbinfo/getTbInfoList", {
          tbUserId: this.tbuser.tid,
          page: this.page,
          rows: this.rows
        }).then(res => {
          if (res.data.code === 0) {
            this.tbinfoList = res.data.data.list;
            this.total = res.data.data.total;
          } else {
            this.$snackbar({message: res.data.msg});
          }
        })
      },
      changeTab(tbuser) {
        this.tbuser = tbuser;
        this.getTbInfoList();
      },
      handleSortChange ({name, order}) {
        this.tbinfoList = this.tbinfoList.sort((a, b) => order === 'asc' ? a[name] - b[name] : b[name] - a[name]);
      },
      refresh() {
        this.$requests.post("/tbinfo/refresh", {
          tbUserId: this.tbuser.tid
        }).then(res => {
          if (res.data.code === 0) {
            this.$snackbar({message: "刷新成功"});
            this.getTbInfoList();
          } else {
            this.$snackbar({message: res.data.msg});
          }
        })
      },
      sign(tid) {
        tid = parseFloat(tid);
        if (!tid) {
          this.signBtnLoading = true;
        }
        console.log(tid);
        this.$requests.post("/tbinfo/sign", {
          tbUserId: this.tbuser.tid,
          tid: !!tid ? tid : null
        }).then(res => {
          if (res.data.code === 0) {
            this.$snackbar({message: "签到成功"});
            this.getTbInfoList();
          } else {
            this.$snackbar({message: res.data.msg});
          }
          if (!tid) {
            this.signBtnLoading = false;
          }
        })
      },
      openDeleteModal(tid) {
        this.deleteId = tid;
        this.deleteModal = true;
      },
      deleteConfirm() {
        this.$requests.post("/tbinfo/delete", {
          tid: this.deleteId
        }).then(res => {
          if (res.data.code === 0) {
            this.$snackbar({message: "删除成功"});
            this.deleteModal = false;
            this.getTbInfoList();
          } else {
            this.$snackbar({message: res.data.msg});
          }
        })
      }
    },
    mounted() {
      const isLogin = Boolean(Number(localStorage.getItem("isLogin")));
      if (isLogin) {
        this.getTbuserList();
      } else {
        this.$snackbar({message: "使用此功能请先登录"});
        Util.$emit('login');
      }
    }
  }
</script>

<style lang="less">
  .manage {
    .not-sign {
      color: red;
    }
  }
</style>
