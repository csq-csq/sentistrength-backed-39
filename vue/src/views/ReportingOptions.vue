<template>
  <el-form ref="form" :model="form">
    <el-form-item label="Report Classification Rationale:">
      <el-radio-group v-model="form.radio1">

        <el-radio :label='true'>是</el-radio>
        <el-radio :label='false'>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Report Translation:">

      <el-radio-group v-model="form.radio2">

        <el-radio :label="true">是</el-radio>
        <el-radio :label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Report Internal Text Tagging:">

      <el-radio-group v-model="form.radio3">

        <el-radio :label="true">是</el-radio>
        <el-radio :label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Report Text Translated Back From Internal Text Tagging:">

      <el-radio-group v-model="form.radio4">

        <el-radio :label="true">是</el-radio>
        <el-radio :label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Report Sentiment Strength Classifications[Don't uncheck this normally ever]:">

      <el-radio-group v-model="form.radio5">

        <el-radio :label="true">是</el-radio>
        <el-radio :label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Log all results to log file in main directory:">

      <el-radio-group v-model="form.radio6">

        <el-radio :label="true">是</el-radio>
        <el-radio :label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSubmit">提交</el-button>
      <el-button>重置</el-button>
    </el-form-item>
  </el-form>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "ReportingOptions",
  data() {
    return {
      form:{
        radio1: true,
        radio2: true,
        radio3: false,
        radio4: false,
        radio5: true,
        radio6: false,
      }
    };
  },
  methods: {
    onSubmit() {
      console.log('submit!')
      request
          .post('/reportingOptions', {
            radio1: this.form.radio1,
            radio2: this.form.radio2,
            radio3: this.form.radio3,
            radio4: this.form.radio4,
            radio5: this.form.radio5,
            radio6: this.form.radio6,
          })
          .then(successResponse => {
            console.log(successResponse.code)
          })
          .catch(failResponse => {
            console.log('fail!');
          })
    }
  }

};
</script>

<style scoped>

</style>