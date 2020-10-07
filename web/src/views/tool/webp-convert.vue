<template>
  <mu-container>
    <mu-flex><h2>上传webp图片</h2></mu-flex>
    <div class="webp-upload">
      <mu-button  @click="triggerUpload">上传webp</mu-button>
      <mu-select label="格式" v-model="ext" style="margin-left: 20px; width: 150px">
        <mu-option v-for="item in exts" :key="item.id" :label="item.ext" :value="item.ext"></mu-option>
      </mu-select>
      <mu-button color="secondary" v-loading="loading" data-mu-loading-size="24" @click="uploadWebp" style="margin-left: 20px;">转换webp</mu-button>
      <input ref="uploadWebp" hidden type="file" accept="image/webp" id="upload-webp" @change="getWebp($event)">
    </div>
    <div style="margin-top: 10px; float: left">
      <mu-flex>预览图</mu-flex>
      <img :src="imgUrl" style="width: 300px;">
    </div>
    <div style="margin: 10px 0 0 30px; float: left">
      <mu-flex>转换结果</mu-flex>
      <img :src="targetImgUrl" style="width: 300px;">
    </div>
  </mu-container>
</template>

<script>
  export default {
    name: "webp-convert",
    data() {
      return {
        loading: false,
        file: null,
        imgUrl: null,
        targetImgUrl: null,
        ext: "jpg",
        exts: [
          {
            id: 1,
            ext: "jpg",
          },
          {
            id: 2,
            ext: "png",
          }
        ]
      }
    },
    methods: {
      triggerUpload() {
        this.$refs['uploadWebp'].dispatchEvent(new MouseEvent('click'));
      },
      getWebp(e) {
        this.targetImgUrl = null;
        this.file = e.target.files[0];
        let url = "";
        var reader = new FileReader();
        reader.readAsDataURL(this.file);
        let that = this;
        reader.onload = function(e) {
          url = this.result.substring(this.result.indexOf(",") + 1);
          that.imgUrl = "data:image/webp;base64," + url;
        }
      },
      uploadWebp() {
        if (this.file == null) {
          this.$snackbar({message: "请上传图片"});
          return;
        }
        this.loading = true;
        let form = new FormData;
        form.append("webp", this.file);
        form.append("ext", this.ext);
        this.$requests.postAndGetFile("/tool/webpConvert", form).then(res => {
          const {data, headers} = res;
          let blob = new Blob([data], {type: headers['content-type']});
          this.blobToBase64(blob).then(res => {
            this.targetImgUrl = res;
            this.loading = false;
          }).catch(err => {
            this.$snackbar({message: "图片转换错误"});
          })
        });
      },
      blobToBase64(blob) {
        return new Promise((resolve, reject) => {
          const fileReader = new FileReader();
          fileReader.onload = (e) => {
            resolve(e.target.result);
          }
          fileReader.readAsDataURL(blob);
          fileReader.onerror = () => {
            reject(new Error("文件流异常"));
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
