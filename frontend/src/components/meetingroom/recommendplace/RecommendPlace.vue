<template>
  <v-container>
    <div class="d-flex justify-start ml-3">
      <v-icon color="indigo accent-2">mdi-record-circle-outline</v-icon><v-subheader class="pl-2">중간 지점 장소 추천</v-subheader>
    </div>
    <v-list-item-subtitle class="d-flex justify-start ml-4">만남 멤버들의 중간 지점 주변의 핫플을 추천</v-list-item-subtitle>
    <MiddlePointRecommend :recPlace="recPlace" :roomInfo="roomInfo" @refresh="refresh"/>

    <div class="d-flex justify-start ml-3">
      <v-icon color="indigo accent-2">mdi-help-circle</v-icon><v-subheader class="pl-2">랜덤 장소 추천</v-subheader>
    </div>
    <v-list-item-subtitle class="d-flex justify-start ml-4">만남 멤버 중 한 멤버 주변의 장소를 랜덤 추천</v-list-item-subtitle>
    <RandomPointRecommend :ranPlace="ranPlace" :roomInfo="roomInfo" @refresh="refresh"/>
    
    <div class="d-flex justify-start ml-3">
      <v-icon color="indigo accent-2">mdi-map-search</v-icon><v-subheader class="pl-2">직접 추천</v-subheader>
    </div>
    <v-list-item-subtitle class="d-flex justify-start ml-4">추천 장소가 맘에 들지 않는다면 직접 검색하여 선택</v-list-item-subtitle>
    <DirectSearch :roomInfo="roomInfo" @refresh="refresh"/>
  </v-container>
</template>

<script>
import MiddlePointRecommend from './pointrecommend/MiddlePointRecommend.vue'
import RandomPointRecommend from './pointrecommend/RandomPointRecommend.vue'
import DirectSearch from './directsearch/DirectSearch.vue'
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "RecommendPlace",
  components: {
    MiddlePointRecommend,
    RandomPointRecommend,
    DirectSearch,
  },
  data() {
    return {
      ranPlace: null,
    }
  },
  props: {
    recPlace: Array,
    mrUserInfo: Array,
    roomInfo: Object,
  },
  watch: {
    mrUserInfo() {
      this.getRandomPlace()
    }
  },
  mounted() {
    console.log(this.recPlace)
    this.getRandomPlace()
  },
  methods: {
    getRandomPlace() {
      var users = []
      for (var i of this.mrUserInfo){
        if (i.mruUserLat !== null) {
          users.push(i)
        }
      }
      if (users.length > 0) {
        var num = 10000000 % users.length
        const data = {
        mrCategory: this.roomInfo.mrCategory,
        mrCenterLat: users[num].mruUserLat,
        mrCenterLng: users[num].mruUserLng,
        mrNo: this.roomInfo.mrNo
      }
      axios.put(`${server_URL}/letsmeet/meetingRoom/editmidpoint`, data)
        .then((res)=> {
          this.ranPlace = res.data
        })
        .catch((err) => {
          console.log(err)
        })
      }
    },
    refresh() {
      this.$emit('refresh')
    },
  }
}
</script>

<style>

</style>