<template>
  <div>
    <v-card
      class="mx-auto"
      max-width="400"
    >
      <v-img :src="place.sImage" />
      <v-container>
        <v-row>
          <v-col cols=10>
            <h3 class="mt-2">{{ place.sName }} <span class="ml-2" style="color: #536DFE !important;">{{ place.sScore }}</span></h3>
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

      <v-list-item>
        <v-list-item-icon>
          <v-icon>mdi-phone</v-icon>
        </v-list-item-icon>
        {{ place.sNumber }}
      </v-list-item>
      
      <hr>

      <v-list-item>
        <v-list-item-subtitle>상세 정보</v-list-item-subtitle>
      </v-list-item>

      <v-list-item>
        음식 종류 : {{ place.sCategoryDetail }}
      </v-list-item>
      <v-list-item>
        가격대 : {{ place.sPrice }}
      </v-list-item>
      <v-list-item>
        영업 시간 : <br>{{ place.sTime }}
      </v-list-item>

      <hr>

      <v-list-item>
        <v-list-item-subtitle>리뷰 보기</v-list-item-subtitle>
      </v-list-item>
      <StoreReview :place="place" />
    </v-card>
  </div>
</template>

<script>
var map = '' 
// const axios = require('axios')
import StoreReview from '../../../components/meetingroom/recommendplace/pointrecommend/StoreReview'

export default {
    name: 'MypageLikePlaceDetail',
    components: {
        StoreReview
    },
    props: {
        place: Object,
    },
    mounted () {
        // console.log(this.place)
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
    }
}
</script>