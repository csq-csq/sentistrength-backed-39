<template>
  <el-form ref="form" :model="form">

    <el-form-item label="isFolder">
      <el-radio-group v-model="form.isFolder">
        <el-radio label='isFolder'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>
<!--    <el-form-item label="input">-->
<!--      <el-radio-group v-model="form.input">-->
<!--        <el-radio label='input'>是</el-radio>-->
<!--        <el-radio label=''>否</el-radio>-->
<!--      </el-radio-group>-->
<!--      <el-input v-model='form.inputstring' placeholder="文件路径" :disabled="form.input==''"></el-input>-->
<!--      <el-divider></el-divider>-->
<!--    </el-form-item>-->

<!--    <el-form-item label="inputfolder">-->
<!--      <el-radio-group v-model="form.inputfolder">-->
<!--        <el-radio label='inputfolder'>是</el-radio>-->
<!--        <el-radio label=''>否</el-radio>-->
<!--      </el-radio-group>-->
<!--      <el-input v-model='form.inputfolderstring' placeholder="文件夹路径" :disabled="form.inputfolder==''"></el-input>-->
<!--      <el-divider></el-divider>-->
<!--    </el-form-item>-->


    <el-form-item label="outputfolder">
      <el-radio-group v-model="form.outputfolder">
        <el-radio label='outputfolder'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.outputfolderstring' placeholder="输出文件夹" :disabled="form.outputfolder==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="resultsextension">
      <el-radio-group v-model="form.resultsextension">
        <el-radio label='resultsextension'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.resultsextensionstring' placeholder="结果文件拓展名" :disabled="form.resultsextension==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="filesubstring">
      <el-radio-group v-model="form.filesubstring">
        <el-radio label='filesubstring'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.filesubstringstring' placeholder="文件的子字符串" :disabled="form.filesubstring==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="overwrite">
      <el-radio-group v-model="form.overwrite">
        <el-radio label='overwrite'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="text">
      <el-radio-group v-model="form.text">
        <el-radio label='text'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.textstring' placeholder="要分析的文本" :disabled="form.text==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="urlencoded">
      <el-radio-group v-model="form.urlencoded">
        <el-radio label='urlencoded'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="listen">
      <el-radio-group v-model="form.listen">
        <el-radio label='listen'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.listenint' placeholder="端口号" :disabled="form.listen==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="stdin">
      <el-radio-group v-model="form.stdin">
        <el-radio label='stdin'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="cmd">
      <el-radio-group v-model="form.cmd">
        <el-radio label='cmd'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="bText">
      <el-radio-group v-model="form.bText">
        <el-radio label='bText'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="optimise">
      <el-radio-group v-model="form.optimise">
        <el-radio label='optimise'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.optimisestring' placeholder="optimise" :disabled="form.optimise==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="annotatecol">
      <el-radio-group v-model="form.annotatecol">
        <el-radio label='annotatecol'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.annotatecolint' placeholder="annotatecol" :disabled="form.annotatecol==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="textcol">
      <el-radio-group v-model="form.textcol">
        <el-radio label='textcol'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.textcolint' placeholder="textcol" :disabled="form.textcol==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="idcol">
      <el-radio-group v-model="form.idcol">
        <el-radio label='idcol'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.idcolint' placeholder="idcol" :disabled="form.idcol==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="lang">
      <el-radio-group v-model="form.lang">
        <el-radio label='lang'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.langstring' placeholder="lang" :disabled="form.lang==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="train">
      <el-radio-group v-model="form.train">
        <el-radio label='train'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="all">
      <el-radio-group v-model="form.all">
        <el-radio label='all'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="numcorrect">
      <el-radio-group v-model="form.numcorrect">
        <el-radio label='numcorrect'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="iterations">
      <el-radio-group v-model="form.iterations">
        <el-radio label='iterations'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.iterationsint' placeholder="iterations" :disabled="form.iterations==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="minimprovement">
      <el-radio-group v-model="form.minimprovement">
        <el-radio label='minimprovement'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.minimprovementint' placeholder="minimprovement" :disabled="form.minimprovement==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="multi">
      <el-radio-group v-model="form.multi">
        <el-radio label='multi'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.multiint' placeholder="multi" :disabled="form.multi==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="termWeights">
      <el-radio-group v-model="form.termWeights">
        <el-radio label='termWeights'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="wait">
      <el-radio-group v-model="form.wait">
        <el-radio label='wait'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="help">
      <el-radio-group v-model="form.help">
        <el-radio label='help'>是</el-radio>
        <el-radio label=''>否</el-radio>
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
  name: "SentiArgs",
  data() {
    return {
      form:{
        isFolder: '',

        input: '',
        inputstring: '',

        inputfolder: '',
        inputfolderstring: '',

        outputfolder: '',
        outputfolderstring: '',

        resultsextension: '',
        resultsextensionstring: '',

        filesubstring: '',
        filesubstringstring: '',

        overwrite: '',

        text: '',
        textstring: '',

        urlencoded: '',

        listen: '',
        listenint: '',

        stdin: '',

        cmd: '',

        bText: '',

        optimise: '',
        optimisestring: '',

        annotatecol: '',
        annotatecolint: '',

        textcol:'',
        textcolint: '',

        idcol:'',
        idcolint: '',

        lang: '',
        langstring: '',

        train: '',

        all: '',

        numcorrect: '',

        iterations: '',
        iterationsint: '',

        minimprovement: '',
        minimprovementint: '',

        multi: '',
        multiint: '',

        termWeights: '',

        wait: '',

        help: '',
      }
    };
  },

  methods: {
    onSubmit() {
      console.log('submit!')
      request
          .post('/sentiArgs', {
            isFolder: this.form.isFolder,

            input: this.form.input,
            inputstring: this.form.inputstring,

            inputfolder: this.form.inputfolder,
            inputfolderstring: this.form.inputfolderstring,

            outputfolder: this.form.outputfolder,
            outputfolderstring: this.form.outputfolderstring,

            resultsextension: this.form.resultsextension,
            resultsextensionstring: this.form.resultsextensionstring,

            filesubstring: this.form.filesubstring,
            filesubstringstring: this.form.filesubstringstring,

            overwrite: this.form.overwrite,

            text: this.form.text,
            textstring: this.form.textstring,

            urlencoded: this.form.urlencoded,

            listen: this.form.listen,
            listenint: this.form.listenint,

            stdin: this.form.stdin,

            cmd: this.form.cmd,

            bText: this.form.bText,

            optimise: this.form.optimise,
            optimisestring: this.form.optimisestring,

            annotatecol: this.form.annotatecol,
            annotatecolint: this.form.annotatecolint,

            textcol: this.form.textcol,
            textcolint: this.form.textcolint,

            idcol: this.form.idcol,
            idcolint: this.form.idcolint,

            lang: this.form.lang,
            langstring: this.form.langstring,

            train: this.form.train,

            all: this.form.all,

            numcorrect: this.form.numcorrect,

            iterations: this.form.iterations,
            iterationsint: this.form.iterationsint,

            minimprovement: this.form.minimprovement,
            minimprovementint: this.form.minimprovementint,

            multi: this.form.multi,
            multiint: this.form.multiint,

            termWeights: this.form.termWeights,

            wait: this.form.wait,

            help: this.form.help,
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