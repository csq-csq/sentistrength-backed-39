<template>
  <div>
    <h2>文件下载</h2>
    <p>模拟下载,假设下面是某个文件列表</p>
    <div @click="downFile"><i class="el-icon-document"></i>123456</div>
  </div>

</template>

<script>
export default {
  name: "Output",
  methods: {
    downFile() {
      alert("开始下载")
      //动态获取刚刚上传的文件的路径,所以必须先上传,再下载,当然你也可以把路径写死
      //实际项目中,获取目标文件路径即可,这个不是重点
      var url = "http://localhost:8080/api/file/download?filePath=" + "Result.txt"
      //这里直接使用window.open 发起请求在新页面显示返回的内容,也可以使用axios,这样比较简单,效果类似
      window.open(url)
    },
  }
}
</script>

<style scoped>

</style>