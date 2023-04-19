<template>
  <el-form ref="form" :model="form">

    <el-form-item label="emotion of sentences used in comment:">
      <el-radio-group v-model="form.radio1">
        <el-radio label="average">average</el-radio>
        <el-radio label="strongest">strongest</el-radio>
        <el-radio label="total">total</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="emotion of words used in sentence:">
      <el-radio-group v-model="form.radio2">
        <el-radio label="average">average</el-radio>
        <el-radio label="strongest">strongest</el-radio>
        <el-radio label="total">total</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Ignore negative emotion in question sentences:">
      <el-radio-group v-model="form.radio3">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Allow multiple +ve words to increase +ve emotion:">
      <el-radio-group v-model="form.radio4">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Allow multiple -ve words to increase -ve emotion:">
      <el-radio-group v-model="form.radio5">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Booster words increase emotion or decrease may fail for +ve:">
      <el-radio-group v-model="form.radio6">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Count neutral emotions as positive for emphasis:">
      <el-radio-group v-model="form.radio7">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Repeated letters boost emotion:">
      <el-radio-group v-model="form.radio8">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Miss and derivative words count as +2:">
      <el-radio-group v-model="form.radio9">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Exclamation mark counts as +2 unless sentence has negative emotion:">
      <el-radio-group v-model="form.radio10">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Correct spellings due to repeated letters:">
      <el-radio-group v-model="form.radio11">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="You or Your counts as a min of +2 unless sentence has negative emotion:">
      <el-radio-group v-model="form.radio12">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Use emoticons:">
      <el-radio-group v-model="form.radio13">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Use idiom lookup table to override matching word strengths:">
      <el-radio-group v-model="form.radio14">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Negative words flip emotion of the following word:">
      <el-radio-group v-model="form.radio15">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Maximum number of addition words since negative for negative to flip emotion(default=1;ignore one intervening word:">
      <el-radio-group v-model="form.radio16">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Never count booster words when counting intervening words after negative:">
      <el-radio-group v-model="form.radio17">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Correct repeated letter spelling differences:">
      <el-radio-group v-model="form.radio18">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Set min repeated letters to boost sentiment - default=2:">
      <el-radio-group v-model="form.radio19">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="Set letters that can occur twice within words:">
      <el-radio-group v-model="form.radio20">
        <el-radio label="true">是</el-radio>
        <el-radio label="false">否</el-radio>
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
export default {
  name: "SentimentAnalysisOptions",
  data() {
    return {
      form:{
        radio1: 'strongest',
        radio2: 'strongest',
        radio3: 'false',
        radio4: 'true',
        radio5: 'true',
        radio6: 'true',
        radio7: 'true',
        radio8: 'true',
        radio9: 'true',
        radio10: 'false',
        radio11: 'true',
        radio12: 'false',
        radio13: 'true',
        radio14: 'true',
        radio15: 'true',
        radio16: 'false',
        radio17: 'true',
        radio18: 'true',
        radio19: 'false',
        radio20: 'false',
      }
    };
  },
  methods: {
    onSubmit() {
      console.log('submit!');
    }
  }
};
</script>

<style scoped>

</style>