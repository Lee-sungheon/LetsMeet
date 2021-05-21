<template>
  <v-container>
    <hooper :settings="hooperSettings" style="height:100%;">
      <slide v-for="(place, i) in ranPlace" :key="i">
        <RecommendItem :place="place" :roomInfo="roomInfo" @go_detail="goDetail" @refresh="refresh"/>
      </slide>
    </hooper>
    <div v-if="isDetail && place">
      <RandomPlaceDetail :place="place" class="mt-3" @go_close="goClose"/>
    </div>
  </v-container>
</template>

<script>
import { Hooper, Slide } from 'hooper';
import 'hooper/dist/hooper.css';
import RecommendItem from '@/components/meetingroom/recommendplace/pointrecommend/RecommendItem.vue'
import RandomPlaceDetail from '@/components/meetingroom/recommendplace/pointrecommend/RandomPlaceDetail.vue'


export default {
  name: "RandomPointRecommend",
  components: {
    Hooper,
    Slide,
    RecommendItem,
    RandomPlaceDetail,
  },
  props: {
    ranPlace: Array,
    roomInfo: Object,
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
      isDetail: false
    }
  },
  methods: {
    goDetail(data) {
      this.place = data
      this.isDetail = true
    },
    refresh() {
      this.$emit('refresh')
    },
    goClose() {
      this.isDetail = false
    }
  }
}
</script>

<style>

</style>