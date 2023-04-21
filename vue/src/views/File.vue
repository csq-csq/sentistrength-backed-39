<!--<template>-->
<!--  <div>-->
<!--    <el-upload-->
<!--        class="upload-demo"-->
<!--        ref="upload"-->
<!--        action="#"-->
<!--        :on-preview="handlePreview"-->
<!--        :on-remove="handleRemove"-->
<!--        :before-remove="beforeRemove"-->
<!--        :http-request="UploadFile"-->
<!--        :auto-upload="false"-->
<!--        multiple-->
<!--        :limit="3"-->
<!--        :on-exceed="handleExceed"-->
<!--        :file-list="fileList"-->

<!--    >-->
<!--      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>-->
<!--      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>-->
<!--    </el-upload>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import request from "@/utils/request";-->

<!--export default {-->
<!--  data() {-->
<!--    return {-->
<!--      fileData: '',-->
<!--      fileList: []-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    uploadFile(file) {-->
<!--      this.fileData.append('files', file.file); // append增加数据-->
<!--    },-->
<!--    submitUpload() {-->

<!--      this.fileData = new FormData(); // new formData对象-->
<!--      this.$refs.upload.submit(); // 提交调用uploadFile函数-->
<!--      console.log(this.fileData);-->
<!--      console.log(this.fileList);-->
<!--      request-->
<!--          .post('/file/upload', {-->
<!--            files: this.fileData,-->
<!--          })-->
<!--          .then(Response => {-->
<!--            console.log(Response.code)-->
<!--            console.log(Response.message)-->
<!--            console.log(Response.result)-->
<!--            if(Response.code == 200){-->
<!--              this.fileList = [];-->
<!--              this.addDialogVisible = false;-->
<!--              //清空表单-->
<!--              // this.$refs['addForm'].resetFields();-->
<!--              // this.getTableData(this.pageNo, this.pageSize);-->
<!--            }-->
<!--          })-->
<!--    },-->
<!--    handleRemove(file, fileList) {-->
<!--      console.log(file, fileList);-->
<!--    },-->
<!--    handlePreview(file) {-->
<!--      console.log(file);-->
<!--    },-->
<!--    handleExceed(files, fileList) {-->
<!--      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);-->
<!--    },-->
<!--    beforeRemove(file, fileList) {-->
<!--      return this.$confirm(`确定移除 ${ file.name }？`);-->
<!--    },-->
<!--  }-->
<!--}-->
<!--</script>-->

<template>
  <div>
    <el-upload
        class="upload-demo"
        ref="upload"
        action="/api/file/upload2"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :file-list="fileList"
        :auto-upload="false"
        multiple
    >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
      <div slot="tip" class="el-upload__tip">只能上传文件，且不超过50Mb</div>
    </el-upload>
    <el-divider></el-divider>
    <el-button type="primary" @click="deleteUploadFiles">删除所有已上传文件</el-button>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      fileList: []
    };
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    deleteUploadFiles(){
      request
          .post('/file/deleteAllUpload', {
            1:1,
          })
    }
  }
}
</script>