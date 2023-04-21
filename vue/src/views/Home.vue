<template>
  <div>
    <h1>sentistrength主页</h1>
<!--    <el-form ref="form" :model="form" label-width="80px">-->
<!--      <el-input v-model="form.text" placeholder="please enter some text"></el-input>-->

<!--      <el-button type="primary" @click="onSubmit">Quick Test</el-button>-->
<!--    </el-form>-->
    <el-divider></el-divider>
    <div>
      <el-button type="primary" @click="Submit">开始运行</el-button>
    </div>
    <el-divider></el-divider>
    <div>
      <el-button type="primary" @click="downloadFile">下载结果</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {download} from "@/utils/download";

export default {
  name: "Home",
  data() {
    return {
      form: {
        text:'',
      }
    }
  },
  methods:{
    onSubmit() {
      // console.log('submit!');
      request
          .post('/quickTest', {
            text: this.form.text,
          })
          .then(Response => {
            console.log(Response.code)
            console.log(Response.message)
            console.log(Response.result)
          })
    },
    Submit() {
      // console.log('submit!');
      request
          .post('/submit', {
            1:1,
          })
          .then(Response => {
            console.log(Response.code)
            console.log(Response.message)
            console.log(Response.result)
          })
    },
    downloadFile(){
      download("Result.zip")
    },
  }
}
</script>

<style scoped>

</style>