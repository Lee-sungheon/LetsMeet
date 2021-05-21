<template>
  <v-container>
    <v-text-field
      v-model="search"
      @click="searchMap"
      @enter="searchMap"
      append-icon="mdi-magnify"
      label="Search"
      solo
      single-line
      hide-details
    ></v-text-field>
    <div id="map5"></div>
  </v-container>
</template>

<script>
var map = ''
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "DirectSearch",
  props: {
    roomInfo: Object
  },
  data() {
    return {
      search: ''
    }
  },
  mounted() {
    setTimeout(() => { this.initMap() }, 100)
  },
  methods: {
    initMap() {
      var container = document.getElementById("map5");
      container.style.width = '100%'
      container.style.height = '300px'
      var latitude = this.roomInfo.mrCenterLat
      var longitude = this.roomInfo.mrCenterLng
      if (this.roomInfo.mrCenterLat === null) {
        latitude = 35.86656557448651
        longitude = 128.5976698883993
      }
      var options = {
        center: new kakao.maps.LatLng(latitude, longitude),
        level: 4
      }
      map = new kakao.maps.Map(container, options)
    },
    searchMap() {
      var ps = new kakao.maps.services.Places();  
      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({zIndex:1});
      // 키워드로 장소를 검색합니다
      ps.keywordSearch(`${this.search}`, placesSearchCB); 
      function placesSearchCB (data, status) {
        if (status === kakao.maps.services.Status.OK) {

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
            // LatLngBounds 객체에 좌표를 추가합니다
            var bounds = new kakao.maps.LatLngBounds();

            for (var i=0; i<data.length; i++) {
                displayMarker(data[i]);    
                bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
            }       
            // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
            map.setBounds(bounds);
        } 
      }
      // 지도에 마커를 표시하는 함수입니다
      var that = this
      function displayMarker(place) {
          // 마커를 생성하고 지도에 표시합니다
          var marker = new kakao.maps.Marker({
              map: map,
              position: new kakao.maps.LatLng(place.y, place.x) 
          });

          // 마커에 클릭이벤트를 등록합니다
          kakao.maps.event.addListener(marker, 'click', function() {
              // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
              infowindow.setContent('<div style="padding:5px;font-size:12px;" class="makerevent">' + place.place_name + '</div>');
              infowindow.open(map, marker);
              var elements = document.getElementsByClassName("makerevent")
              for (var i = 0; i < elements.length; i++) {
                elements[i].addEventListener('click', function(){
                if (confirm(`${place.place_name} 을 모임장소로 선택하시겠습니까?`)) {
                  console.log(that.roomInfo)
                  console.log(place)
                  axios.post(`${server_URL}/letsmeet/map/add?sCategory=${that.roomInfo.mrCategory}&sLat=${place.y}&sLng=${place.x}&sName=${place.place_name}`)
                  .then((res)=> {
                    console.log(res.data)
                    if (that.roomInfo.mrSuperUNo == that.$store.state.uNo) {
                      const data = {
                        mrPlace: res.data,
                        mrNo: that.$route.params.id
                      }
                      axios.put(`${server_URL}/letsmeet/meetingRoom/finalplace`, data)
                      .then((res)=> {
                        console.log(res.data)
                        alert("약속 장소를 확정하였습니다.")
                        that.$emit('refresh')
                      })
                      .catch((err) => {
                        console.log(err)
                      })
                    }
                  })
                  .catch((err) => {
                    console.log(err)
                  })

                }
              })
            }
          })
      }
    },
  }
}
</script>

<style scoped>
</style>