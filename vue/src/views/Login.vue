<template>
  <div>
    用户名:<input type="text" v-model="loginForm.username" placeholder="请输入用户名"/>
    <br><br>
    密码： <input type="password" v-model="loginForm.password" placeholder="请输入密码"/>
    <br><br>
    <button v-on:click="login">登录</button>
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      request
          .post('/login', {
            username: this.loginForm.username,
            password: this.loginForm.password
          })
          .then(successResponse => {
            console.log(successResponse.code)
            if (successResponse.code === 200) {
              this.$router.replace({path: '/home'})
            }
            console.log(successResponse.message)
          })
          .catch(failResponse => {
            console.log(failResponse.message)
          })
    }
  }
}

</script>

<style scoped>

</style>