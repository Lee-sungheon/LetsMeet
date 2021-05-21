<template>
  <v-container id="onoff">
    <div id="map2"></div>
  </v-container>
</template>


<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
const KAKAO_API_KEY = '71f77d07e68b0f6c0464d85d3df14e6c'
var map = ''
var latitude = 0
var longitude = 0


export default {
  name: "MiddlePoint",
  props: {
    mrUserInfo: Array,
    roomInfo: Object,
  },
  data () {
    return {
      dialog: false,
      positions: [],
      count: 0,
      recPlace: [],
    }
  },
  watch: {
    mrUserInfo() {
      setTimeout(() => { this.initMap() }, 1000)
    }
  },
  created() {
    this.addKakaoMapScript()
  },
  mounted() {
    setTimeout(() => { this.initMap() }, 1000)
  },
  methods: {
    addKakaoMapScript() {
      const script = document.createElement("script")
      script.onload = () => kakao.maps.load()
      script.src =
        `https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${KAKAO_API_KEY}`
      document.head.appendChild(script)
    },
    initMap() {
      var con = document.getElementById("onoff")
      var delete_item = document.getElementById("map2");
      if (delete_item) {
        con.removeChild(delete_item)
      }
      var newDiv = document.createElement("div")
      newDiv.setAttribute("id","map2")
      con.appendChild(newDiv)


      map = ''
      latitude = 0
      longitude = 0
      this.count = 0
      var polygonPath = []
      this.positions = []
      for (var user of this.mrUserInfo) {
        if (user.mruUserLat !== null) {
          this.count ++
          var LatLng = new kakao.maps.LatLng(user.mruUserLat, user.mruUserLng)
          var tmp = {
            latlng: LatLng,
            title: user.uName,
            uImage: user.uImage
          }
          polygonPath.push(LatLng)
          latitude += parseFloat(user.mruUserLat)
          longitude += parseFloat(user.mruUserLng)
          this.positions.push(tmp)
        }
      }
      if (this.count > 0) {
        longitude = longitude / this.count
        latitude = latitude / this.count

        // axois
        const data = {
          mrCategory: this.roomInfo.mrCategory,
          mrCenterLat: latitude,
          mrCenterLng: longitude,
          mrNo: this.roomInfo.mrNo
        }

        axios.put(`${server_URL}/letsmeet/meetingRoom/editmidpoint`, data)
        .then((res)=> {
          console.log(res.data)
          this.recPlace = res.data
          this.$emit('rec_place', this.recPlace)
        })
        .catch((err) => {
          console.log(err)
        })

      } else {
        longitude = 128.5976698883993
        latitude = 35.86656557448651
      }
      console.log(latitude, longitude)

      var container = document.getElementById("map2");
      container.style.width = '100%'
      container.style.height = '300px'
      var options = {
        center: new kakao.maps.LatLng(latitude, longitude),
        level: 4
      }
      map = new kakao.maps.Map(container, options)

      var mapTypeControl = new kakao.maps.MapTypeControl()
      map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT)
      var zoomControl = new kakao.maps.ZoomControl()
      map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT)

      // var imageSrc = "https://cdn.vuetifyjs.com/images/john.jpg"
      var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png'

      for (var i = 0; i < this.positions.length; i ++) {
        // var imageSrc = 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg'
        // if (this.positions[i].uImage){
        //   imageSrc = this.positions[i].uImage
        // }
        var imageSize = new kakao.maps.Size(24, 35)
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize)
        new kakao.maps.Marker({
          map: map, // 마커를 표시할 지도
          position: this.positions[i].latlng, // 마커를 표시할 위치
          title : this.positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image : markerImage // 마커 이미지 
        });
        var content = 
          `<div class="text-center" style="margin-top:50px;">` +
          ` <img src="${ this.positions[i].uImage }" style="width: 30px; height: 30px; border-radius: 50%;">` +
          ' <div class="rounded-pill grey lighten-3">' +
          '  <v-textarea background-color="light-blue">' +
          `    <div>${ this.positions[i].title }</div>` + 
          '  </v-textarea>' +
          `  </div>` +
          '</div>'
        var overlay = new kakao.maps.CustomOverlay({
          content: content,
          map: map,
          position: this.positions[i].latlng  
        });
        overlay.setMap(map);
      }
      if (polygonPath.length > 0) {
        polygonPath.sort()
        var polygon = new kakao.maps.Polygon({
            path:polygonPath, // 그려질 다각형의 좌표 배열입니다
            strokeWeight: 3, // 선의 두께입니다
            strokeColor: '#39DE2A', // 선의 색깔입니다
            strokeOpacity: 0.8, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'longdash', // 선의 스타일입니다
            fillColor: '#A2FF99', // 채우기 색깔입니다
            fillOpacity: 0.7 // 채우기 불투명도 입니다
        });
  
        polygon.setMap(map)
      }

      if (this.count > 0) {
        var bounds = new kakao.maps.LatLngBounds();
        for (var j=0; j<this.positions.length; j++) {
          bounds.extend(this.positions[j].latlng);
        }       
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
        
        var imageSrc2 = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"
        var imageSize2 = new kakao.maps.Size(24, 35)
        var markerImage2 = new kakao.maps.MarkerImage(imageSrc2, imageSize2)
        new kakao.maps.Marker({
          map: map,
          position: new kakao.maps.LatLng(latitude, longitude),
          title : "중간지점 : " + latitude + ", " + longitude,
          image : markerImage2
        });
        var content2 = 
          '<div class="mt-7 rounded-pill grey lighten-3">' +
          '  <v-textarea background-color="light-blue">' +
          `    <div>중간지점</div>` + 
          '  </v-textarea>' +
          '</div>'
        overlay = new kakao.maps.CustomOverlay({
          content: content2,
          map: map,
          position: new kakao.maps.LatLng(latitude, longitude),  
        })
        overlay.setMap(map);
      }
      map.relayout()
    },
  },
}
</script>

<style>

</style>