<template>
  <div>
    <v-card
      class="mx-auto"
      max-width="400"
    >
      <v-img :src="place.sImage">
        <v-icon class="mb-1" color="red" style="position: absolute; right: 5px; top:5px;" @click="$emit('go_close')">mdi-close-circle</v-icon>
      </v-img>
      <v-container>
        <v-row>
          <v-col cols=10>
            <h3 class="mt-2">{{ place.sName }} <span class="ml-2" style="color: #536DFE !important;" v-if="place.sScore != 0">{{ place.sScore }}</span></h3>
          </v-col>
          <v-col cols=2 class="d-flex align-center">
            <v-row>
              <v-col class="m-0 p-0 d-flex justify-center" cols=12>
                <v-icon color="red" @click="likeStore"> {{ heart }}</v-icon>
              </v-col>
              <v-col class="m-0 p-0 d-flex justify-center" cols=12>
                <span style="font-size: 0.6rem; color: gray;">찜하기</span>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-container>

      <hr>
      <v-list-item>
        <v-list-item-subtitle>주소</v-list-item-subtitle>
      </v-list-item> 

      <v-list-item>
        <v-list-item-icon>
          <v-icon>mdi-map-marker</v-icon>
        </v-list-item-icon>
        <v-list-item-subtitle>{{ place.sRoadAddress }}</v-list-item-subtitle>
      </v-list-item>
      <div>
        <div id="map3"></div>
      </div>
      <hr>

      <v-list-item>
        <v-list-item-subtitle>전화번호</v-list-item-subtitle>
      </v-list-item>

      <v-list-item v-if="place.sNumber">
        <v-list-item-icon>
          <v-icon>mdi-phone</v-icon>
        </v-list-item-icon>
        {{ place.sNumber }}
      </v-list-item>
      
      <hr>

      <v-list-item>
        <v-list-item-subtitle>상세 정보</v-list-item-subtitle>
      </v-list-item>

      <v-list-item v-if="place.sCategoryDetail">
        음식 종류 : {{ place.sCategoryDetail }}
      </v-list-item>
      <v-list-item v-if="place.sPrice">
        가격대 : {{ place.sPrice }}
      </v-list-item>
      <v-list-item v-if="place.sTime">
        영업 시간 : <br>{{ place.sTime }}
      </v-list-item>

      <hr>

      <StoreReview :place="place" />
    </v-card>
  </div>
</template>

<script>
var map = ''
const axios = require('axios')
const server_URL = process.env.VUE_APP_SERVER_URL

import StoreReview from './StoreReview'
export default {
  name: "PlaceDetail",
  components: {
    StoreReview,
  },
  data() {
    return {
      heart: 'mdi-heart-outline',
      like_store: []
    }
  },
  props: {
    place: Object,
  },
  mounted() {
    this.getLikeStore()
    setTimeout(() => { this.initMap() }, 100)
  },
  methods: {
    initMap() {
      var container = document.getElementById("map3");
      container.style.width = '100%'
      container.style.height = '300px'
      var latitude = this.place.sLat
      var longitude = this.place.sLng
      var options = {
        center: new kakao.maps.LatLng(latitude, longitude),
        level: 4
      }
      map = new kakao.maps.Map(container, options)
      var markers = []
      addMarker(options['center'])
      map.setCenter(options['center'])
      map.relayout()
      function addMarker(position) {
        var marker = new kakao.maps.Marker({
            position: position
        })
        marker.setMap(map)
        markers.push(marker)
      } 
    },
    getLikeStore() {
      axios.get(`${server_URL}/letsmeet/mypage/likestore?uNo=${this.$store.state.uNo}`)
      .then((res)=> {
        console.log(res.data)
        this.like_store = res.data
        for (var store of this.like_store) {
          if (store.lsSNo === this.place.sNo) {
            this.heart = "mdi-heart"
          }
        }
      })
      .catch(()=> {
        console.log('못드감')
      })
    },
    likeStore(){
      if (this.heart === "mdi-heart-outline") {
        axios.post(`${server_URL}/letsmeet/mypage/likestore/add?lsSNo=${this.place.sNo}&lsUNo=${this.$store.state.uNo}`)
        .then((res)=> {
          console.log(res.data)
          this.heart = "mdi-heart"
        })
        .catch(()=> {
          console.log('못드감')
        })
      } else {
        axios.delete(`${server_URL}/letsmeet/mypage/likestore/delete?lsSNo=${this.place.sNo}&lsUNo=${this.$store.state.uNo}`)
        .then((res)=> {
          console.log(res.data)
          this.heart = "mdi-heart-outline"
        })
        .catch((err)=> {
          console.log(err)
        })
      }
    },
  },
}
</script>

<style>

</style>