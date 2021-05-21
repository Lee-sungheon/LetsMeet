<template>
  <div>
    <v-tabs
      color="indigo accent-2"
      centered
      fixed-tabs
      slider-color="indigo accent-2"
    >
      <v-tab>진행중</v-tab>
      <v-tab>완료</v-tab>
      
      <v-tab-item v-if="doing_temp.length > 0">
        <v-card
          class="mx-auto mt-3"
          v-model="checkList"
          v-for="(value, idx) in doing_temp"
          :key="idx"
          @click="goDetail(value.mrNo)"
        >
          <div style="z-index: 9999; position: absolute; right:0;">
            <v-menu
              left
              bottom
              style="z-index: 9999;"
              z-index=9999
              absolute
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  style="color: black; z-index: 9999;"
                  v-bind="attrs"
                  v-on="on"
                >
                  <v-icon color=white>mdi-dots-vertical</v-icon>
                </v-btn>
              </template>

              <v-list>
                <v-list-item v-if="value.mrSuperUNo === uNo">
                  <!-- router-link 걸어주기 -->
                    <router-link class='ro' :to="{ name: 'UpdateMeetingRoom', params: {'id': value.mrNo}}" >
                      <v-list-item-title>
                        수정
                      </v-list-item-title>
                    </router-link>
                </v-list-item>
                <v-list-item @click="deleteRoom(value.mrNo)">
                  <v-list-item-title>삭제</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
          <v-img 
          height="200px"
          :src="value.mrImage"
          style="filter: brightness(50%);">
          </v-img>
          <v-fade-transition>
            <v-overlay
              z-index=5
              absolute 
              >
              <div class="d-flex flex-column mt-5">
                <div class="d-flex justify-center"><v-icon class="mb-1">{{type[value.mrCategory][0]}} </v-icon> {{ value.mrCategory}} 약속</div>
                <div class="d-flex justify-center"><h4>{{ value.mrName }}</h4></div>
                <div class="d-flex justify-center">
                  <span v-if="value.mrDate">{{ value.mrDate }}</span>
                  <span v-else>약속날짜 미정</span>
                </div>
                <div class="d-flex justify-center">{{ value.mrUCnt }}명</div>
              </div>
            </v-overlay>
          </v-fade-transition>
        </v-card>
        <infinite-loading @infinite="doingInfiniteHandler"></infinite-loading>
      </v-tab-item>
      <v-tab-item v-if="done_temp.length > 0">
        <v-card
          class="mx-auto mt-3"
          v-for="(value, idx) in done_temp"
          :key="idx"
          @click="goDetail(value.mrNo)"
        >
          <div style="z-index: 9999; position: absolute; right:0;">
            <v-menu
              left
              bottom
              style="z-index: 9999;"
              z-index=9999
              absolute
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  style="color: black; z-index: 9999;"
                  v-bind="attrs"
                  v-on="on"
                >
                  <v-icon color=white>mdi-dots-vertical</v-icon>
                </v-btn>
              </template>

              <v-list>
                <v-list-item @click="deleteRoom(value.mrNo)">
                  <v-list-item-title>삭제</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
          <v-img 
          height="200px"
          :src="value.mrImage"
          style="filter: brightness(50%);">
          </v-img>
          <v-fade-transition>
            <v-overlay
              absolute >
              <div class="d-flex flex-column mt-5">
                <div class="d-flex justify-center"><v-icon class="mb-1">{{type[value.mrCategory][0]}} </v-icon> {{ value.mrCategory}} 약속</div>
                <div class="d-flex justify-center"><h4>{{ value.mrName }}</h4></div>
                <div class="d-flex justify-center">
                  <span v-if="value.mrDate">{{ value.mrDate }}</span>
                  <span v-else>약속날짜 미정</span>
                </div>
                <div class="d-flex justify-center">{{ value.mrUCnt }}명</div>
              </div>
            </v-overlay>
          </v-fade-transition>
        </v-card>
        <infinite-loading @infinite="doneInfiniteHandler"></infinite-loading>
      </v-tab-item>
    </v-tabs>
    <div class="d-flex justify-center">
      <back-to-top bottom="65px" right="" style="display: block !important;">
        <v-btn fab dark small color="indigo accent-2">
          <v-icon dark>mdi-chevron-up</v-icon>
        </v-btn>
      </back-to-top>
    </div>
  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading'
import BackToTop from 'vue-backtotop'
import moment from 'moment'
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
const config = {
          headers: { 'auth-token': window.localStorage.getItem('auth-token') },
      };

export default {
  name: "Main",
  data() {
    return {
      uNo: this.$store.state.uNo,
      del_dialog: false,
      doing_list: [],
      done_list: [],
      doing_temp: [],
      done_temp: [],
      checkList: '',
      type: {
        '밥': ['mdi-food', 'https://www.gyeongju.go.kr/upload/content/thumb/20200529/A42F50C69A8A4DDB94DA408C290735C1.jpg'],
        '카페': [ 'mdi-coffee', 'http://www.lightingnews.net/images/theme/cafe_01.png'],
        '술': ['mdi-glass-mug-variant', 'https://img.lovepik.com/photo/50011/5863.jpg_wh860.jpg'],
        '스터디': ['mdi-book-open-page-variant', 'https://modo-phinf.pstatic.net/20180304_283/1520151276251GkP1Q_JPEG/mosaOtd1XG.jpeg?type=w720'],
        '놀거리': ['mdi-snowboard', 'https://www.travel.taipei/image/65598/1024x768'],
        '관광지': ['mdi-airplane', 'https://post-phinf.pstatic.net/MjAxNzA1MTFfMTQg/MDAxNDk0NDgyODE4OTI2.P7H0n7pqJChBq_g42dZAwi_K16adlRxBerf26cW1Hvgg.oqVaoHWzt7D7dFhW_W62oMaoRn9TCStRJ6A9j_D7C0Yg.JPEG/%EC%84%AC.jpg?type=w1200'],
      }
    };
  },
  components: {
    InfiniteLoading,
    BackToTop,
  },
  created() {
    this.getRoomList()
  },
  methods: {
    deleteRoom(roomNumber) {
      // this.del_dialog = true
      if (confirm(`정말 방을 나가시겠습니까?`)) {
        this.deleteRoomReal(roomNumber)
      }
    },
    deleteRoomReal (roomNumber) {
      console.log(roomNumber)
      axios.delete(`${server_URL}/letsmeet/meetingRoomUser/delete?mrNo=${roomNumber}&uNo=${this.$store.state.uNo}`, config)
      .then((res)=> {
        this.del_dialog = false
        console.log(res.data)
        this.getRoomList()
        
      })
      .catch(()=> {
        console.log('방 삭제 안됨')
      })
    },
    doingInfiniteHandler($state) {
      setTimeout(() => {
        for (let i = 1; i <= 3; i++) {
          if (this.doing_list.length > 0) {
            this.doing_temp.push(this.doing_list.shift());
          } else {
            $state.complete();
          }
        }
        $state.loaded();
      }, 1000);
    },
    doneInfiniteHandler($state) {
      setTimeout(() => {
        for (let i = 1; i <= 3; i++) {
          if (this.done_list.length > 0) {
            this.done_temp.push(this.done_list.shift());
          } else {
            $state.complete();
          }
        }
        $state.loaded();
      }, 1000);
    },
    getRoomList() {
      this.doing_list = [],
      this.done_list = [],
      this.doing_temp = [],
      this.done_temp = [],
      axios.get(`${server_URL}/letsmeet/main?uNo=${this.$store.state.uNo}`)
      .then((res)=> {
        
        const data = res.data

        for (var val of data) {
          if (val.mrDate < moment().format('YYYY-MM-DD')){
            this.done_list.push(val)
          } else {
            this.doing_list.push(val)
          }
        }
        for (var i=0 ; i<3 ; i++) {
          if (this.done_list.length > 0){
            this.done_temp.push(this.done_list.shift());
          }
          if (this.doing_list.length > 0){
          this.doing_temp.push(this.doing_list.shift());
          }
        }
      })
      .catch((err) => {
        console.log(err)
      })
    },
    goDetail(mrNo) {
      console.log(mrNo)
      this.$router.push({name:"MeetingRoom", params:{"id":mrNo}})
    },
  },
}
</script>

<style>
  .ro {
    text-decoration: none !important;
    color: inherit !important;
  }
</style>