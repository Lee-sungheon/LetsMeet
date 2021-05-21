<template>
  <v-container>
    <div class="d-flex justify-start ml-3">
      <v-icon color="indigo accent-2">mdi-google-analytics</v-icon><v-subheader class="pl-2">채팅 분석</v-subheader>
    </div>
    <v-list-item-subtitle class="d-flex justify-start ml-4">대화내용 분석 결과</v-list-item-subtitle>
    <ResultNLP :roomInfo='roomInfo' :new_words='new_words' :showTable='showTable' />

    <div class="d-flex justify-start ml-3">
      <v-icon color="indigo accent-2">mdi-map-search</v-icon><v-subheader class="pl-2">다음 장소 추천</v-subheader>
    </div>
    <v-list-item-subtitle class="d-flex justify-start ml-4">채팅의 대화내용 분석 결과를 토대로 장소 추천</v-list-item-subtitle>
    <NextPlaceNLP :roomInfo='roomInfo' :nlpPlaceDetail='nlpPlaceDetail' />

    <div class="d-flex justify-start ml-3">
      <v-icon color="indigo accent-2">mdi-help-circle</v-icon><v-subheader class="pl-2">랜덤 장소 추천</v-subheader>
    </div>
    <v-list-item-subtitle class="d-flex justify-start ml-4">다음 약속 장소를 랜덤 추천</v-list-item-subtitle>
    <NextRandomRecommend :ranPlace="ranPlace" :roomInfo="roomInfo"/>
    
  </v-container>
</template>

<script>
import NextRandomRecommend from './NextRandomRecommend.vue'
import ResultNLP from '../../../components/meetingroom/nextplace/nlpresult/ResultNLP'
import NextPlaceNLP from '../../../components/meetingroom/nextplace/nlpresult/NextPlaceNLP'

const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "NextPlace",
  data() {
    return {
      ranPlace: [],
      nlpWordsList: [],
      chatContent: "",
      words: {},
      new_words: [],
      showTable: false,
      nlpPlaceDetail: [],
      
    }
  },
  components: {
    NextRandomRecommend,
    ResultNLP,
    NextPlaceNLP
  },
   props: {
    mrUserInfo: Array,
    roomInfo: Object,
  },
  mounted() {
    this.getRandomPlace()
    this.getData()
  },
  methods: {
    getData() {
        // 채팅방 내용들 다 받아오기
        axios.get(`${server_URL}/letsmeet/chat/open?mrcMrNo=${this.roomInfo.mrNo}`)
        .then((res)=> {
          res.data.forEach((content)=> {
            this.chatContent += content.mrcContent+' '
          })
          if (this.chatContent.length <= 0) {
            this.new_words.push('대화 내용이 없습니다.')
            this.showTable = false
          } else {
            this.analyzeNLP(this.chatContent)
          }

        })
        .catch(()=> {
          console.log('안됨')
        })
      },
      // 데이터 없을 때 처리해줄거
      analyzeNLP (data) {
        //'http://aiopen.etri.re.kr:8000/WiseNLU'
        axios.post(`${server_URL}/NLU`, {
          'access_key': '1d00844e-0b14-498b-a3c8-017784783627',
          'argument': {
              'text': data,
              'analysis_code': 'ner'
          },
          headers: {'Content-Type':'application/json; charset=UTF-8'}
        })
        .then((res)=> {
          res.data.return_object.sentence.forEach((sentence) => {
            sentence.morp.forEach((morpheme) => {
              console.log(morpheme)
              if(morpheme.type === 'NNG') {
                let word = morpheme.lemma
                    if (word in this.words) {
                      this.words[word] += 1
                    } else {
                      this.words[word] = 1
                    }
                  }
                })
              })
              for( const [key, value] of Object.entries(this.words)){
                this.new_words.push([key, value])
              }
              this.new_words.sort(function (a, b){
                return b[1] - a[1]
              })
              this.showTable = true
              this.new_words = this.new_words.slice(0, 5)
              console.log(this.new_words)
              this.getnlpStoreDetail()
            })
        .catch((err)=>{
          console.log(err)
        })
      },
      getnlpStoreDetail() {
        this.new_words.forEach((place)=> {
          // const nlpList = []
          axios.get(`${server_URL}/letsmeet/map/storedetail?detail=${place[0]}&x=${this.roomInfo.mrCenterLat}&y=${this.roomInfo.mrCenterLng}`)
          .then((res)=>{
            if (res.data.length > 0) {
              const li = res.data.slice(0,3)
              li.forEach(el => {
                this.nlpPlaceDetail.push(el)
              });
            }
          })
          .catch((err)=>{
            console.log(err)
          })
        })
    },
    getRandomPlace() {
      let randoms = [];
      let i = 0;
      while (i < 6) {
        let n = Math.floor(Math.random() * 1500) + 1;
        if (! sameNum(n)) {
          randoms.push(n);
          i++;
        }
      }
      function sameNum (n) {
        return randoms.find((e) => (e === n));
      }
      for (var idx of randoms) {
        axios.get(`${server_URL}/letsmeet/map/sno?sno=${idx}`)
        .then((res)=> {
          this.ranPlace.push(res.data)
        })
        .catch((err) => {
          console.log(err)
        })
      }
    }
  }
}
</script>

<style>

</style>