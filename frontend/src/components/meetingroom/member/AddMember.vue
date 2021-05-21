<template>
  <div class="mt-3">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="90%"
      >
      <template v-slot:activator="{ on, attrs }">
        <div class='text-center' v-bind="attrs" v-on="on">
          <h5><v-icon>mdi-plus-circle</v-icon>&nbsp; 멤버 추가</h5>
        </div>
      </template>
      <v-card>
        <h4 class="p-3 text-center">
          멤버 추가
        </h4>
        <v-container v-if="tmplist.length > 0">
          <div>
            <v-container>
              <v-row>
                <v-col cols='3' v-for="(friend, i) in tmplist" :key="i">
                  <v-row>
                    <v-avatar>
                      <img
                        :src="friend[3]"
                      >
                    </v-avatar>
                  </v-row>
                  <v-row class="d-flex align-center" style="font-size: 0.9rem;">{{ friend[2] }}
                    <v-col class="d-flex align-center" cols='2'><v-icon @click="deleteTemporaryList(i)">mdi-close-circle-outline</v-icon></v-col>
                  </v-row>
                </v-col>
              </v-row>
            </v-container>
          </div>
        </v-container>

        <v-card-text>
          <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="이메일 혹은 이름을 입력해주세요."
          single-line
          hide-details
          @input="searchInput"
          >
          </v-text-field>
        </v-card-text>
        
        <v-container v-if="search">
          <div v-for="(friend, i) in searchmyfriendlist" :key="i">
            <v-container>
              <v-row>
                <v-col cols='2'>
                <v-avatar>
                  <img
                    :src="friend[3]"
                  >
                </v-avatar>
                </v-col>
                <v-col class="d-flex align-center pl-2" cols='2' 
                style="font-size: 0.9rem; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"
                >{{ friend[1] }}</v-col>
                <v-col class="d-flex align-center" cols='6' 
                style="font-size: 0.9rem; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"
                >{{ friend[2] }}</v-col>
                <v-col class="d-flex align-center" cols='2'><v-icon @click="addTemporaryList(friend)">mdi-plus</v-icon></v-col>
              </v-row>
            </v-container>
          </div>
        </v-container>

        <v-container v-else>
          <div v-for="(friend, i) in allmyfriendlist" :key="i">
            <v-container>
              <v-row>
                <v-col cols='2'>
                <v-avatar>
                  <img
                    :src="friend[3]"
                  >
                </v-avatar>
                </v-col>
                <v-col class="d-flex align-center pl-2" cols='2' 
                style="font-size: 0.9rem; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"
                >{{ friend[1] }}</v-col>
                <v-col class="d-flex align-center" cols='6' 
                style="font-size: 0.9rem; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"
                >{{ friend[2] }}</v-col>
                <v-col class="d-flex align-center" cols='2'><v-icon @click="addTemporaryList(friend)">mdi-plus</v-icon></v-col>
              </v-row>
            </v-container>
          </div>
        </v-container>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="indigo darken-1"
            text
            @click="cancelAdd"
          >
            취소
          </v-btn>
          <v-btn
            color="indigo darken-1"
            text
            @click="addMyFriendList"
          >
            확인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "AddMember",
  data() {
    return {
      tmplist: [],
      addFriend: '',
      dialog: false,
      allmyfriendlist: [],
      search: '',
      searchmyfriendlist: [],
    }
  },
  props: {
    members: Array,
    roomInfo: Object,
  },
  watch: {
    allmyfriendlist() {

    }
  },
  mounted() {
    this.getMemberList()
  },
  methods: {
    addTemporaryList(friend) {
      if (this.members.length + this.tmplist.length >= 4) {
        alert("코로나 방역 수칙을 지켜주세요!! 5인 이상 집합 금지!!")
        return
      }
      for (var mb of this.members){
        if (friend[2] === mb.uEmail){
          alert("이미 추가된 멤버입니다!")
          return
        }
      }
      for (var mb2 of this.tmplist){
        if (friend[2] === mb2[1]){
          alert("이미 추가된 멤버입니다!")
          return
        }
      }
      this.tmplist.push([friend[0], friend[2], friend[1], friend[3]])
    },
    addMyFriendList () {
      for(let mb of this.tmplist) {
        const data = {
          mruMrNo: this.$route.params.id,
          mruUNo: mb[0],
        }
        axios.post(`${server_URL}/letsmeet/meetingRoomUser/adduser`, data)
        .then(()=> {
          var data = {
            icon: false,
            uName: mb[2],
            uEmail: mb[1],
            avatar: mb[3],
            uNo: mb[0]
          }
          var img = 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg'
          const content = `${this.$store.state.uName}님이 '${this.roomInfo.mrName}'방에 초대하였습니다./${this.roomInfo.mrNo}`
          const title = '방 초대 알림'
          if (this.$store.state.uImage) {
            img = this.$store.state.uImage
          }
          axios.post(`${server_URL}/letsmeet/alarm/create?aContent=${content}&aRecvUNo=${mb[0]}&aSenderImage=${img}&aTitle=${title}`)
          .then((res) => {
            console.log(res.data)
          })
          .catch((err) => {
            console.log(err)
          })
          this.$emit('addMember', data)
        })
        .catch(()=> {
          console.log('못드감')
        })
      }
      this.dialog = false
      this.tmplist = []
    },
    cancelAdd() {
      this.tmplist = []
      this.dialog = false
    },
    deleteTemporaryList(idx) {
      this.tmplist.splice(idx, 1)
    },
    getMemberList(){
      axios.get(`${server_URL}/letsmeet/mypage/friend?myUNo=${this.$store.state.uNo}`)
      .then((res)=> {
        const array = res.data
        array.forEach(el => {
          var img = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJ0zR3D2SYdonO9a3Yfzfh1RNq1nWGN3WD4w&usqp=CAU'
          if (el.uImage) {
            img = el.uImage
          }
          this.allmyfriendlist.push([el.uNo, el.uName, el.uEmail, img])
        });
        console.log(this.allmyfriendlist)
      })
      .catch(()=> {
        console.log('data 못받아옴')
      })
    },
    searchInput() { 
      if(this.search.length !== 0){ clearTimeout(this.debounce); 
        this.debounce = setTimeout(() => { 
          const filteredList = this.allmyfriendlist.filter(item => item[1].includes(this.search))
          const filteredList2 = this.allmyfriendlist.filter(item => item[2].includes(this.search))
          const sumlist = filteredList2.concat(filteredList)
          this.searchmyfriendlist = sumlist.filter((item, pos) => sumlist.indexOf(item) === pos)
        }, 10)
      }else{ 
        clearTimeout(this.debounce); 
        this.debounce = setTimeout(() => { this.searchmyfriendlist = []
        }, 10)
      }
    },
  },
}
</script>

<style>

</style>