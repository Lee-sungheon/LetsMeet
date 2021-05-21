<template>
    <v-container class="mt-5 pt-5">
      <v-row>
      <vue-word-cloud class="vue-word" :words="new_words"  style=" height: 100px; width: 220px;" 
      :color="([, weight]) => weight > 3 ? 'DeepPink' : weight > 2 ? 'RoyalBlue' : 'Indigo'"
      font-family="Roboto">
        <template slot-scope="{text, weight}">
          <div class='heretext' :title="weight" style="cursor: pointer;" animation-overlap>
            {{ text }}
          </div>
        </template> 
      </vue-word-cloud>
      </v-row>
          <v-simple-table dense class="pt-5 mt-3" v-if="showTable">
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">
                    단어
                  </th>
                  <th class="text-left">
                    빈도수
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr
                  v-for="(item, i) in new_words"
                  :key="i"
                >
                  <td>{{ item[0] }}</td>
                  <td>{{ item[1] }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
    </v-container>
</template>
<script>
import VueWordCloud from 'vuewordcloud';
// const axios = require('axios')
// const server_URL = process.env.VUE_APP_SERVER_URL

export default {
    name: 'ResultNLP',
    props: {
      roomInfo: Object,
      new_words: Array,
      showTable: Boolean,
    },
    components: {
      [VueWordCloud.name]: VueWordCloud,
    },
    data: ()=> {
      return {

      }
    },
    methods: {
    //   getData() {
    //     // 채팅방 내용들 다 받아오기
    //     axios.get(`${server_URL}/letsmeet/chat/open?mrcMrNo=1`)
    //     .then((res)=> {
    //       res.data.forEach((content)=> {
    //         this.chatContent += content.mrcContent
    //       })
    //       if (this.chatContent.length <= 0) {
    //         this.new_words.push('대화 내용이 없습니다.')
    //         this.showTable = false
    //       } else {
    //         this.analyzeNLP(this.chatContent)
    //       }

    //     })
    //     .catch(()=> {
    //       console.log('안됨')
    //     })
    //   },
    //   // 데이터 없을 때 처리해줄거
    //   analyzeNLP (data) {
    //     axios.post('http://aiopen.etri.re.kr:8000/WiseNLU', {
    //       'access_key': '1d00844e-0b14-498b-a3c8-017784783627',
    //       'argument': {
    //           'text': data,
    //           'analysis_code': 'ner'
    //       },
    //       headers: {'Content-Type':'application/json; charset=UTF-8'}
    //     })
    //     .then((res)=> {
    //       res.data.return_object.sentence.forEach((sentence) => {
    //         sentence.morp.forEach((morpheme) => {
    //           if(morpheme.type === 'NNG') {
    //             let word = morpheme.lemma
    //                 if (word in this.words) {
    //                   this.words[word] += 1
    //                 } else {
    //                   this.words[word] = 1
    //                 }
    //               }
    //             })
    //           })
    //           for( const [key, value] of Object.entries(this.words)){
    //             this.new_words.push([key, value])
    //           }
    //           this.new_words.sort(function (a, b){
    //             return b[1] - a[1]
    //           })
    //           this.showTable = true
    //           this.new_words = this.new_words.slice(0, 5)
    //           this.$emit('nlp_words', this.new_words)
    //         })
    //     .catch((err)=>{
    //       console.log(err)
    //     })
    // } 
  }
}
</script>
<style scoped>
.vue-word {
  display: flex;
  left: 63px;
}
</style>