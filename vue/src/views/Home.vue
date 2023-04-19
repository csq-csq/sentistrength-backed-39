<template>
  <div>
    <h1>sentistrength主页</h1>
    <el-form ref="form" :model="form" label-width="80px">
      <el-input v-model="form.text" placeholder="please enter some text"></el-input>

      <el-button type="primary" @click="onSubmit">Quick Test</el-button>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

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
  }
}
</script>

<style scoped>

</style>