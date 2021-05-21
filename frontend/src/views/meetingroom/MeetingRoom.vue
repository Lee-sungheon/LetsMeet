<template>
  <v-container v-if="mrUserInfo">
    <v-card
      class="mx-auto mb-5"
      max-width="100%"
    >
      <v-img
        v-if="roomInfo.mrCategory"
        :src="roomInfo.mrImage"
        height="200px"
      ></v-img>
      <v-card-actions>
        <v-btn
          text
        >
          <h5>{{ roomInfo.mrName }}</h5>
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn
          icon
          @click="show = !show"
        >
          <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
        </v-btn>
      </v-card-actions>

      <v-expand-transition>
        <v-container v-show="show">
          <v-divider></v-divider>
          약속 유형 : {{ roomInfo.mrCategory }} <br>
          멤버 : {{ mrUserInfo[0].uName }} <span v-if="mrUserInfo.length > 1">외 {{ mrUserInfo.length - 1 }}명 </span><br>
          시간 : <span v-if="roomInfo.mrDate">{{ roomInfo.mrDate }}</span><span v-else>미정</span><br>
          장소 : <span v-if="this.place">{{ this.place.sName }}</span><span v-else>미정</span><br>
        </v-container>
      </v-expand-transition>
    </v-card>



    <v-sheet elevation="6">
    <v-tabs
      background-color="indigo accent-2"
      dark
      centered
      next-icon="mdi-arrow-right-bold-box-outline"
      prev-icon="mdi-arrow-left-bold-box-outline"
      show-arrows
      v-model="tab"
    >
      <v-tab>
        <span v-if="done">추억 공유</span>
        <span v-else>약속 선택</span>
      </v-tab>
      <v-tab>
        <span v-if="done">다음 장소</span>
        <span v-else>장소 추천</span>
      </v-tab>
      <v-tab>
        채팅
      </v-tab>
      <v-tab>
        멤버
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab" touchless>
      <v-tab-item>
        <Gallery v-if="done" :mrNo="mrNo" />
        <DeterminePromise v-else :roomInfo="roomInfo" :mrUserInfo="mrUserInfo"
         @refresh="refresh" @rec_place="getPlace" @refresh3="refresh3" />
      </v-tab-item>
      <v-tab-item>
        <NextPlace v-if="done" :roomInfo="roomInfo" :mrUserInfo="mrUserInfo"/>
        <RecommendPlace v-else :roomInfo="roomInfo" :mrUserInfo="mrUserInfo" :recPlace="recPlace" @refresh="refresh2" />
      </v-tab-item>
      <v-tab-item>
        <Chatting :mrNo="mrNo" :mrUserInfo="mrUserInfo"/>
      </v-tab-item>
      <v-tab-item>
        <Member :roomInfo="roomInfo" :mrUserInfo="mrUserInfo" @addMember="addMember"/>
      </v-tab-item>
    </v-tabs-items>
  </v-sheet>
  </v-container>
</template>

<script>
import Member from "../../components/meetingroom/member/Member.vue";
import DeterminePromise from "../../components/meetingroom/determinepromise/DeterminePromise.vue";
import Chatting from "../../components/meetingroom/chatting/Chatting.vue";
import RecommendPlace from "../../components/meetingroom/recommendplace/RecommendPlace.vue";
import Gallery from "../../components/meetingroom/gallery/Gallery.vue";
import NextPlace from "../../components/meetingroom/nextplace/NextPlace.vue";
import moment from 'moment'
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "MeetingRoom",
  components: {
    Member,
    DeterminePromise,
    Chatting,
    RecommendPlace,
    Gallery,
    NextPlace,
  },
  data () {
    return {
      show: false,
      tab: 'tab-1',
      mrNo: 0,
      roomInfo: {},
      type: {
        '밥': ['mdi-food', 'https://www.gyeongju.go.kr/upload/content/thumb/20200529/A42F50C69A8A4DDB94DA408C290735C1.jpg'],
        '카페': [ 'mdi-coffee', 'http://www.lightingnews.net/images/theme/cafe_01.png'],
        '술': ['mdi-glass-mug-variant', 'https://img.lovepik.com/photo/50011/5863.jpg_wh860.jpg'],
        '스터디': ['mdi-book-open-page-variant', 'https://modo-phinf.pstatic.net/20180304_283/1520151276251GkP1Q_JPEG/mosaOtd1XG.jpeg?type=w720'],
        '놀거리': ['mdi-snowboard', 'https://www.travel.taipei/image/65598/1024x768'],
        '관광지': ['mdi-dots-horizontal', 'http://img.rflogix.com/agm/main/1024/10_1_20200407112854.jpg'],
      },
      mrUserInfo: null,
      recPlace: [],
      place: null,
      done: false,
    }
  },
  created() {
    this.mrNo = this.$route.params.id
    this.getRoomInfo()
  },
  mounted() {
  },
  methods: {
    getRoomInfo() {
      axios.get(`${server_URL}/letsmeet/meetingRoom/detail?mrNo=${this.mrNo}`)
      .then((res)=> {
        console.log('제발')
        console.log(res.data)
        this.roomInfo = res.data
        if (this.roomInfo.mrDate && this.roomInfo.mrDate <= moment().format('YYYY-MM-DD')){
          console.log("TRUE")
          this.done = true
        }
        console.log(this.roomInfo)
        if (this.roomInfo.mrPlace) {
          axios.get(`${server_URL}/letsmeet/map/sno?sno=${this.roomInfo.mrPlace}`)
          .then((res)=> {
            this.place = res.data
            console.log(this.place)
          })
          .catch((err) => {
            console.log(err)
          })
        }
      })
      .catch((err) => {
        console.log(err)
      })
      axios.get(`${server_URL}/letsmeet/meetingRoomUser/userInfo?mrNo=${this.mrNo}`)
      .then((res)=> {
        this.mrUserInfo = res.data
        // console.log(this.mrUserInfo)
      })
      .catch((err) => {
        console.log(err)
      })
    },
    addMember() {
      this.getRoomInfo()
    },
    refresh() {
      this.getRoomInfo()
    },
    getPlace(data) {
      this.recPlace = data
    },
    refresh2() {
      this.getRoomInfo()
      if (this.roomInfo.mrDate) {
        this.postAlarm()
      }
    },
    refresh3() {
      this.getRoomInfo()
      if (this.place) {
        this.postAlarm()
      }
    },
    postAlarm() {
      var img = this.roomInfo.mrImage
      const content = `'${this.roomInfo.mrName}'방의 약속 일정이 최종 확정되었습니다./${this.roomInfo.mrNo}`
      const title = '일정 확정 알림'
      for (var mb of this.mrUserInfo) {
        axios.post(`${server_URL}/letsmeet/alarm/create?aContent=${content}&aRecvUNo=${mb.uNo}&aSenderImage=${img}&aTitle=${title}`)
        .then((res) => {
          console.log(res.data)
        })
        .catch((err) => {
          console.log(err)
        })
      }
    }
  }
}
</script>
<style scoped>

</style>