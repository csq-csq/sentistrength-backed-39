<template>
  <el-form ref="form" :model="form">
    <el-form-item label="sentidata">
      <el-radio-group v-model="form.sentidata">
        <el-radio label='sentidata'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.sentidatastring' placeholder="词典文件夹" :disabled="form.sentidata==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="emotionlookuptable">
      <el-radio-group v-model="form.emotionlookuptable">
        <el-radio label='emotionlookuptable'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.emotionlookuptablestring' placeholder="情绪表" :disabled="form.emotionlookuptable==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="additionalfile">
      <el-radio-group v-model="form.additionalfile">
        <el-radio label='additionalfile'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.additionalfilestring' placeholder="额外文件" :disabled="form.additionalfile==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="keywords">
      <el-radio-group v-model="form.keywords">
        <el-radio label='keywords'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.keywordsstring' placeholder="关键词" :disabled="form.keywords==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="wordsBeforeKeywords">
      <el-radio-group v-model="form.wordsBeforeKeywords">
        <el-radio label='wordsBeforeKeywords'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.wordsBeforeKeywordsint' placeholder="关键词前词数" :disabled="form.wordsBeforeKeywords==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="wordsAfterKeywords">
      <el-radio-group v-model="form.wordsAfterKeywords">
        <el-radio label='wordsAfterKeywords'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.wordsAfterKeywordsint' placeholder="关键词后词数" :disabled="form.wordsAfterKeywords==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="sentiment">
      <el-radio-group v-model="form.sentiment">
        <el-radio label='sentiment'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="stress">
      <el-radio-group v-model="form.stress">
        <el-radio label='stress'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="trinary">
      <el-radio-group v-model="form.trinary">
        <el-radio label='trinary'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="binary">
      <el-radio-group v-model="form.binary">
        <el-radio label='binary'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="scale">
      <el-radio-group v-model="form.scale">
        <el-radio label='scale'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="sentenceCombineAv">
      <el-radio-group v-model="form.sentenceCombineAv">
        <el-radio label='sentenceCombineAv'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="sentenceCombineTot">
      <el-radio-group v-model="form.sentenceCombineTot">
        <el-radio label='sentenceCombineTot'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="paragraphCombineAv">
      <el-radio-group v-model="form.paragraphCombineAv">
        <el-radio label='paragraphCombineAv'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="paragraphCombineTot">
      <el-radio-group v-model="form.paragraphCombineTot">
        <el-radio label='paragraphCombineTot'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="negativeMultiplier">
      <el-radio-group v-model="form.negativeMultiplier">
        <el-radio label='negativeMultiplier'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.negativeMultiplierfloat' placeholder="negativeMultiplier" :disabled="form.negativeMultiplier==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>


    <el-form-item label="noBoosters">
      <el-radio-group v-model="form.noBoosters">
        <el-radio label='noBoosters'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noNegatingPositiveFlipsEmotion">
      <el-radio-group v-model="form.noNegatingPositiveFlipsEmotion">
        <el-radio label='noNegatingPositiveFlipsEmotion'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noNegatingNegativeNeutralisesEmotion">
      <el-radio-group v-model="form.noNegatingNegativeNeutralisesEmotion">
        <el-radio label='noNegatingNegativeNeutralisesEmotion'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noNegators">
      <el-radio-group v-model="form.noNegators">
        <el-radio label='noNegators'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noIdioms">
      <el-radio-group v-model="form.noIdioms">
        <el-radio label='noIdioms'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="questionsReduceNeg">
      <el-radio-group v-model="form.questionsReduceNeg">
        <el-radio label='questionsReduceNeg'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noEmoticons">
      <el-radio-group v-model="form.noEmoticons">
        <el-radio label='noEmoticons'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="exclamations2">
      <el-radio-group v-model="form.exclamations2">
        <el-radio label='exclamations2'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="minPunctuationWithExclamation">
      <el-radio-group v-model="form.minPunctuationWithExclamation">
        <el-radio label='minPunctuationWithExclamation'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.minPunctuationWithExclamationint' placeholder="minPunctuationWithExclamation" :disabled="form.minPunctuationWithExclamation==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="mood">
      <el-radio-group v-model="form.mood">
        <el-radio label='mood'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.moodint' placeholder="mood" :disabled="form.mood==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noMultiplePosWords">
      <el-radio-group v-model="form.noMultiplePosWords">
        <el-radio label='noMultiplePosWords'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noMultipleNegWords">
      <el-radio-group v-model="form.noMultipleNegWords">
        <el-radio label='noMultipleNegWords'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noIgnoreBoosterWordsAfterNegatives">
      <el-radio-group v-model="form.noIgnoreBoosterWordsAfterNegatives">
        <el-radio label='noIgnoreBoosterWordsAfterNegatives'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noDictionary">
      <el-radio-group v-model="form.noDictionary">
        <el-radio label='noDictionary'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noDeleteExtraDuplicateLetters">
      <el-radio-group v-model="form.noDeleteExtraDuplicateLetters">
        <el-radio label='noDeleteExtraDuplicateLetters'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="illegalDoubleLettersInWordMiddle">
      <el-radio-group v-model="form.illegalDoubleLettersInWordMiddle">
        <el-radio label='illegalDoubleLettersInWordMiddle'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.illegalDoubleLettersInWordMiddlestring' placeholder="illegalDoubleLettersInWordMiddle" :disabled="form.illegalDoubleLettersInWordMiddle==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="illegalDoubleLettersAtWordEnd">
      <el-radio-group v-model="form.illegalDoubleLettersAtWordEnd">
        <el-radio label='illegalDoubleLettersAtWordEnd'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.illegalDoubleLettersAtWordEndstring' placeholder="illegalDoubleLettersAtWordEnd" :disabled="form.illegalDoubleLettersAtWordEnd==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="noMultipleLetters">
      <el-radio-group v-model="form.noMultipleLetters">
        <el-radio label='noMultipleLetters'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="negatedWordStrengthMultiplier">
      <el-radio-group v-model="form.negatedWordStrengthMultiplier">
        <el-radio label='negatedWordStrengthMultiplier'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.negatedWordStrengthMultiplierfloat' placeholder="negatedWordStrengthMultiplier" :disabled="form.negatedWordStrengthMultiplier==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="maxWordsBeforeSentimentToNegate">
      <el-radio-group v-model="form.maxWordsBeforeSentimentToNegate">
        <el-radio label='maxWordsBeforeSentimentToNegate'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.maxWordsBeforeSentimentToNegateint' placeholder="maxWordsBeforeSentimentToNegate" :disabled="form.maxWordsBeforeSentimentToNegate==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="negatingWordsDontOccurBeforeSentiment">
      <el-radio-group v-model="form.negatingWordsDontOccurBeforeSentiment">
        <el-radio label='negatingWordsDontOccurBeforeSentiment'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="maxWordsAfterSentimentToNegate">
      <el-radio-group v-model="form.maxWordsAfterSentimentToNegate">
        <el-radio label='maxWordsAfterSentimentToNegate'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.maxWordsAfterSentimentToNegateint' placeholder="maxWordsAfterSentimentToNegate" :disabled="form.maxWordsAfterSentimentToNegate==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="negatingWordsOccurAfterSentiment">
      <el-radio-group v-model="form.negatingWordsOccurAfterSentiment">
        <el-radio label='negatingWordsOccurAfterSentiment'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="alwaysSplitWordsAtApostrophes">
      <el-radio-group v-model="form.alwaysSplitWordsAtApostrophes">
        <el-radio label='alwaysSplitWordsAtApostrophes'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="capitalsBoostTermSentiment">
      <el-radio-group v-model="form.capitalsBoostTermSentiment">
        <el-radio label='capitalsBoostTermSentiment'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="lemmaFile">
      <el-radio-group v-model="form.lemmaFile">
        <el-radio label='lemmaFile'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.lemmaFilestring' placeholder="lemmaFile" :disabled="form.lemmaFile==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="minSentencePosForQuotesIrony">
      <el-radio-group v-model="form.minSentencePosForQuotesIrony">
        <el-radio label='minSentencePosForQuotesIrony'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.minSentencePosForQuotesIronyint' placeholder="minSentencePosForQuotesIrony" :disabled="form.minSentencePosForQuotesIrony==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="minSentencePosForPunctuationIrony">
      <el-radio-group v-model="form.minSentencePosForPunctuationIrony">
        <el-radio label='minSentencePosForPunctuationIrony'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.minSentencePosForPunctuationIronyint' placeholder="minSentencePosForPunctuationIrony" :disabled="form.minSentencePosForPunctuationIrony==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="minSentencePosForTermsIrony">
      <el-radio-group v-model="form.minSentencePosForTermsIrony">
        <el-radio label='minSentencePosForTermsIrony'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.minSentencePosForTermsIronyint' placeholder="minSentencePosForTermsIrony" :disabled="form.minSentencePosForTermsIrony==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="minSentencePosForAllIrony">
      <el-radio-group v-model="form.minSentencePosForAllIrony">
        <el-radio label='minSentencePosForAllIrony'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-input v-model='form.minSentencePosForAllIronyint' placeholder="minSentencePosForAllIrony" :disabled="form.minSentencePosForAllIrony==''"></el-input>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="explain">
      <el-radio-group v-model="form.explain">
        <el-radio label='explain'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="echo">
      <el-radio-group v-model="form.echo">
        <el-radio label='echo'>是</el-radio>
        <el-radio label=''>否</el-radio>
      </el-radio-group>
      <el-divider></el-divider>
    </el-form-item>

    <el-form-item label="UTF8">
      <el-radio-group v-model="form.UTF8">
        <el-radio label='UTF8'>是</el-radio>
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
  name: "Options",
  data() {
    return {
      form: {
        sentidata: '',
        sentidatastring: '',

        emotionlookuptable: '',
        emotionlookuptablestring: '',

        additionalfile: '',
        additionalfilestring: '',

        keywords: '',
        keywordsstring: '',

        wordsBeforeKeywords: '',
        wordsBeforeKeywordsint: '',

        wordsAfterKeywords: '',
        wordsAfterKeywordsint: '',

        sentiment: '',

        stress: '',

        trinary: '',

        binary: '',

        scale: '',

        sentenceCombineAv: '',

        sentenceCombineTot: '',

        paragraphCombineAv: '',

        paragraphCombineTot: '',

        negativeMultiplier: '',
        negativeMultiplierfloat: '',

        noBoosters: '',

        noNegatingPositiveFlipsEmotion: '',

        noNegatingNegativeNeutralisesEmotion: '',

        noNegators: '',

        noIdioms: '',

        questionsReduceNeg: '',

        noEmoticons: '',

        exclamations2: '',

        minPunctuationWithExclamation: '',
        minPunctuationWithExclamationint: '',

        mood: '',
        moodint: '',

        noMultiplePosWords: '',

        noMultipleNegWords: '',

        noIgnoreBoosterWordsAfterNegatives: '',

        noDictionary: '',

        noDeleteExtraDuplicateLetters: '',

        illegalDoubleLettersInWordMiddle: '',
        illegalDoubleLettersInWordMiddlestring: '',

        illegalDoubleLettersAtWordEnd: '',
        illegalDoubleLettersAtWordEndstring: '',

        noMultipleLetters: '',

        negatedWordStrengthMultiplier: '',
        negatedWordStrengthMultiplierfloat: '',

        maxWordsBeforeSentimentToNegate: '',
        maxWordsBeforeSentimentToNegateint: '',

        negatingWordsDontOccurBeforeSentiment: '',

        maxWordsAfterSentimentToNegate: '',
        maxWordsAfterSentimentToNegateint: '',

        negatingWordsOccurAfterSentiment: '',

        alwaysSplitWordsAtApostrophes: '',

        capitalsBoostTermSentiment: '',

        lemmaFile: '',
        lemmaFilestring: '',

        minSentencePosForQuotesIrony: '',
        minSentencePosForQuotesIronyint: '',

        minSentencePosForPunctuationIrony: '',
        minSentencePosForPunctuationIronyint: '',

        minSentencePosForTermsIrony: '',
        minSentencePosForTermsIronyint: '',

        minSentencePosForAllIrony: '',
        minSentencePosForAllIronyint: '',

        explain: '',

        echo: '',

        UTF8: '',
      }
    }
  },

  methods: {
    onSubmit() {
      console.log('submit!')
      request
          .post('/options', {
            sentidata: this.form.sentidata,
            sentidatastring: this.form.sentidatastring,

            emotionlookuptable: this.form.emotionlookuptable,
            emotionlookuptablestring: this.form.emotionlookuptablestring,

            additionalfile: this.form.additionalfile,
            additionalfilestring: this.form.additionalfilestring,

            keywords: this.form.keywords,
            keywordsstring: this.form.keywordsstring,

            wordsBeforeKeywords: this.form.wordsBeforeKeywords,
            wordsBeforeKeywordsint: this.form.wordsBeforeKeywordsint,

            wordsAfterKeywords: this.form.wordsBeforeKeywords,
            wordsAfterKeywordsint: this.form.wordsAfterKeywordsint,

            sentiment: this.form.sentiment,

            stress: this.form.stress,

            trinary: this.form.trinary,

            binary: this.form.binary,

            scale: this.form.scale,

            sentenceCombineAv: this.form.sentenceCombineAv,

            sentenceCombineTot: this.form.sentenceCombineTot,

            paragraphCombineAv: this.form.paragraphCombineAv,

            paragraphCombineTot: this.form.paragraphCombineTot,

            negativeMultiplier: this.form.negativeMultiplier,
            negativeMultiplierfloat: this.form.negativeMultiplierfloat,

            noBoosters: this.form.noBoosters,

            noNegatingPositiveFlipsEmotion: this.form.noNegatingPositiveFlipsEmotion,

            noNegatingNegativeNeutralisesEmotion: this.form.noNegatingNegativeNeutralisesEmotion,

            noNegators: this.form.noNegators,

            noIdioms: this.form.noIdioms,

            questionsReduceNeg: this.form.questionsReduceNeg,

            noEmoticons: this.form.noEmoticons,

            exclamations2: this.form.exclamations2,

            minPunctuationWithExclamation: this.form.minPunctuationWithExclamation,
            minPunctuationWithExclamationint: this.form.minPunctuationWithExclamationint,

            mood: this.form.mood,
            moodint: this.form.moodint,

            noMultiplePosWords: this.form.noMultiplePosWords,

            noMultipleNegWords: this.form.noMultipleNegWords,

            noIgnoreBoosterWordsAfterNegatives: this.form.noIgnoreBoosterWordsAfterNegatives,

            noDictionary: this.form.noDictionary,

            noDeleteExtraDuplicateLetters: this.form.noDeleteExtraDuplicateLetters,

            illegalDoubleLettersInWordMiddle: this.form.illegalDoubleLettersInWordMiddle,
            illegalDoubleLettersInWordMiddlestring: this.form.illegalDoubleLettersInWordMiddlestring,

            illegalDoubleLettersAtWordEnd: this.form.illegalDoubleLettersAtWordEnd,
            illegalDoubleLettersAtWordEndstring: this.form.illegalDoubleLettersAtWordEndstring,

            noMultipleLetters: this.form.noMultipleLetters,

            negatedWordStrengthMultiplier: this.form.negatedWordStrengthMultiplier,
            negatedWordStrengthMultiplierfloat: this.form.negatedWordStrengthMultiplierfloat,

            maxWordsBeforeSentimentToNegate: this.form.maxWordsBeforeSentimentToNegate,
            maxWordsBeforeSentimentToNegateint: this.form.maxWordsBeforeSentimentToNegateint,

            negatingWordsDontOccurBeforeSentiment: this.form.negatingWordsDontOccurBeforeSentiment,

            maxWordsAfterSentimentToNegate: this.form.maxWordsAfterSentimentToNegate,
            maxWordsAfterSentimentToNegateint: this.form.maxWordsAfterSentimentToNegateint,

            negatingWordsOccurAfterSentiment: this.form.negatingWordsOccurAfterSentiment,

            alwaysSplitWordsAtApostrophes: this.form.alwaysSplitWordsAtApostrophes,

            capitalsBoostTermSentiment: this.form.capitalsBoostTermSentiment,

            lemmaFile: this.form.lemmaFile,
            lemmaFilestring: this.form.lemmaFilestring,

            minSentencePosForQuotesIrony: this.form.minSentencePosForQuotesIrony,
            minSentencePosForQuotesIronyint: this.form.minSentencePosForQuotesIronyint,

            minSentencePosForPunctuationIrony: this.form.minSentencePosForPunctuationIrony,
            minSentencePosForPunctuationIronyint: this.form.minSentencePosForPunctuationIronyint,

            minSentencePosForTermsIrony: this.form.minSentencePosForTermsIrony,
            minSentencePosForTermsIronyint: this.form.minSentencePosForTermsIronyint,

            minSentencePosForAllIrony: this.form.minSentencePosForAllIrony,
            minSentencePosForAllIronyint: this.form.minSentencePosForAllIronyint,

            explain: this.form.explain,

            echo: this.form.echo,

            UTF8: this.form.UTF8,

          })
          .then(successResponse => {
            console.log(successResponse.code)
          })
          .catch(failResponse => {
            console.log('fail!');
          })
    }
  }
}
</script>

<style scoped>

</style>