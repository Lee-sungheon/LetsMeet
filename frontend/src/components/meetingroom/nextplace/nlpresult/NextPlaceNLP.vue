<template>
  <v-container>
    <hooper :settings="hooperSettings" style="height:100%;">
      <slide v-for="(place, i) in nlpPlaceDetail" :key="i">
        <NextItem :place="place" :roomInfo="roomInfo" @go_detail="goDetail"/>
      </slide>
    </hooper>
    <div v-if="isDetail && place">
      <RandomPlaceDetail :place="place" class="mt-3"/>
    </div>
  </v-container>
</template>

<script>
import { Hooper, Slide } from 'hooper';
import 'hooper/dist/hooper.css';
// const axios = require('axios')
// const server_URL = process.env.VUE_APP_SERVER_URL
import NextItem from '../NextItem.vue'
import RandomPlaceDetail from '@/components/meetingroom/recommendplace/pointrecommend/RandomPlaceDetail.vue'


export default {
  name: "NextPlaceNLP",
  components: {
    Hooper,
    Slide,
    NextItem,
    RandomPlaceDetail,
  },
  props: {
    roomInfo: Object,
    nlpPlaceDetail: Array,
  },
  data() {
    return {
      hooperSettings: {
        itemsToShow: 1.5,
        itemsToSlide: 1,
        infiniteScroll: "true",
        detail: "true",
        wheelControl: false,
      },
      place: null,
      isDetail: false,
      nlpPlaceInfo: [],
    }
  },
  methods: {
    // getStoreDetail() {
    //     this.new_words.forEach((place)=> {        
    //       axios.get(`${server_URL}/letsmeet/map/storedetail?detail=${place[0]}`)
    //       .then((res)=>{
    //         console.log(res.data)
    //       })
    //       .catch((err)=>{
    //         console.log(err)
    //       })
    //     })
 
    // },
    goDetail(data) {
      this.place = data
      this.isDetail = !this.isDetail
    },
    refresh() {
      this.$emit('refresh')
    },
  }
}
</script>

<style>

</style>