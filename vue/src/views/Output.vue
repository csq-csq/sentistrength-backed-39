<template>
  <div>
    <h2>文件下载</h2>
    <p>模拟下载,假设下面是某个文件列表</p>
    <div @click="downloadFile"><i class="el-icon-document"></i>123456</div>
  </div>

</template>

<script>
import {download} from "@/utils/download";

export default {
  name: "Output",
  methods: {
    downloadFile(){
      download("Result.txt")
    }
  }
}
</script>

<style scoped>

</style>