<template>
  <div>
    <v-text-field
          class="px-3 pb-3"
          v-model="search"
          append-icon="mdi-magnify"
          label="이메일 혹은 이름을 입력해주세요."
          single-line
          hide-details
          @input="searchInput"
          >
    </v-text-field>

    <v-container v-if="search" class="pb-3">
      <div v-for="(friend, i) in searchmyfriendlist" :key="i">
            <v-container>
              <v-row>
                <v-col cols='2'>
                <v-avatar>
                  <img
                    :src="friend[3]"
                    alt="John"
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
    <div class='p-2' v-for="(friend, i) in allmyfriendlist" :key="i">
      <div>
        <v-container>
          <v-row>
          <v-avatar>
            <img
              :src="friend[3]"
              alt="John"
            >
          </v-avatar>
            <v-col cols="3" style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">{{ friend[1] }}</v-col>
            <v-col style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">{{ friend[2] }}</v-col>
            <v-col cols="2" class="text-right" @click='deleteFriend(i, friend[0])'><v-icon>mdi-trash-can</v-icon></v-col>
          </v-row>
        </v-container>
        </div>
      </div>
    </v-container>
    </div>
</template>
<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
// const config = {
//           headers: { 'auth-token': window.localStorage.getItem('auth-token') },
//       }
export default {
  name: 'MypageFriendList',
  props: {
    dbfriend: Array,
  },
  created() {
    this.friendList()
  },
  watch: {
    dbfriend(db) {
      for(let i=0; i<db.length; i++) {
        console.log('aaaa', db[i])
        if (i === db.length-1) {
          this.allmyfriendlist.push(db[i])
        }
      }
    },
  },
  methods: {
    searchInput() {
      // console.log(this.allmyfriendlist) 
      if(this.search.length !== 0){ clearTimeout(this.debounce); 
        this.debounce = setTimeout(() => { 
          const filteredList = this.allmyfriendlist.filter(item => item[1].includes(this.search))
          const filteredList2 = this.allmyfriendlist.filter(item => item[2].includes(this.search))
          const sumfriendlist = filteredList2.concat(filteredList)
          this.searchmyfriendlist = sumfriendlist.filter((item, pos) => sumfriendlist.indexOf(item) === pos)
          console.log(this.searchmyfriendlist)
        }, 10)
      }else{ 
        clearTimeout(this.debounce); 
        this.debounce = setTimeout(() => { this.searchmyfriendlist = []
        }, 10)
      }
    },

    // 친구리스트 불러오기
    friendList(){
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
      this.$emit('get_freind_list', this.allmyfriendlist)
    },
    deleteFriend(index, friend) {
      this.allmyfriendlist.splice(index, 1)
      axios.delete(`${server_URL}/letsmeet/mypage/friend/delete?friend=${friend}&myUNo=${this.$store.state.uNo}`)
      .then(()=> {
        console.log('성공')
      })
      .catch(()=> {
        console.log('실패')
      })
    },
  },
  data: () => {
    return {
      allmyfriendlist: [],
      search: '',
      searchmyfriendlist: [],
    }
  }
}
</script>