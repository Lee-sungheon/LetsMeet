<template>
    <div class="px-1">
      <div class='userinfo pt-2'>
          <div class='justify-center' style="border-bottom: 2px solid black">
              <h5> 마이페이지 </h5>
          </div>
              <!-- 톱니바퀴 -->
              <div class='justify-end'>
              <v-menu
                  left
                  bottom
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      icon
                      style="color: black;"
                      v-bind="attrs"
                      v-on="on"
                    >
                        <v-icon color="indigo accent-2" >mdi-cog</v-icon>
                      <!-- <v-icon>mdi-dots-vertical</v-icon> -->
                    </v-btn>
                  </template>

                  <v-list>
                    <v-list-item>
                      <v-list-item-title> <router-link class="ro" :to="{ name: 'PasswordChange' }"> 비밀번호 변경 </router-link></v-list-item-title>
                    </v-list-item>
                    <v-list-item @click="logout">
                      <v-list-item-title> 로그아웃 </v-list-item-title>
                    </v-list-item>
                    <v-list-item @click="deleteWant"> 
                      <v-list-item-title> 회원탈퇴 </v-list-item-title> 
                    </v-list-item>
                  </v-list>
                </v-menu>
                
                <v-dialog v-model="deleteDialog" max-width="290" persistent>
                  <v-card>
                    <div class="p-3 pl-5">
                      <h5> 회원탈퇴 </h5>
                    </div>
                    <v-card-text>
                      letsmeet 서비스를 이용하시는데 불편함이 있으셨나요? 
                      이용 및 각종 문의 사항은 고객센터로 문의 주시면 성심 성의껏 답변 드리겠습니다.
                    </v-card-text>
                    <v-card-actions>
                      <v-btn text @click="deleteDialog = false">
                        취소
                      </v-btn>
                      <v-btn text @click="deleteUser">
                        확인
                      </v-btn>

                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </div>

          <!-- 유저정보 -->
          <v-container class='pt-3 pb-5 m-1' >
            <v-row>
              <v-col
                class="account"
                cols='3'>
                  <v-badge @click.native="showImageDialog" icon="mdi-plus" bordered color="indigo accent-2" overlap bottom>
                    <v-avatar>
                      <img :src="uImage" />                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
                    </v-avatar>
                  </v-badge>

                <v-dialog v-model="ImageDialog" max-width="290" persistent>
                    <!-- 이미지 변경 모달 -->
                  <v-card class="p-3">
                    <h5 class="pb-5">이미지 변경</h5>

                    <ProfileUpload @updateImage="updateImage" />
                         <v-card-actions class="pr-0 mb-0 pb-0 mt-3">
                          <v-spacer></v-spacer>
                          <v-btn
                            color="green darken-1"
                            text
                            @click="ImageDialog = false"
                            class="pr-0"
                          >
                            취소
                          </v-btn>
                          <v-btn
                            class="pr-0"
                            color="green darken-1"
                            text
                            @click="ImageDialog = false"
                          >
                          <!-- 이미지 변경 확인 보내줘야함 -->
                            확인
                          </v-btn>
                        </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-col>


              <v-col style="font-size:20px" class="px-3">
                <v-dialog
                   v-model="dialog1"
                   persistent
                  max-width="290"
                    >
                      <template v-slot:activator="{ on, attrs }">
                          <div> {{uName}}
                            <v-icon class="pl-3 pb-1" v-bind="attrs" v-on="on">mdi-pencil</v-icon>
                          </div>
                      </template>
                      <!-- 이름변경 모달 -->
                      <v-card>
                         <v-card-title>
                          이름 변경
                        </v-card-title>
                        <v-text-field class="px-4" v-model="ChangeName" :placeholder="uName">
                        </v-text-field>

                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                            color="indigo accent-2"
                            text
                            @click="dialog1 = false"
                          >
                            취소
                          </v-btn>
                          <v-btn
                            color="indigo accent-2"
                            text
                            @click="ChangeNameClick"
                          >
                            확인
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                </v-dialog>
                <div> {{uEmail}} </div>
              </v-col>
            </v-row>
          </v-container>
        </div>
        
      <hr class='m-0'>
        <v-tabs
          v-model="tab"
          background-color="indigo accent-2"
          dark
          grow
        >
        <v-tab
          v-for='item in items'
          :key='item'
        >
          {{ item }}
        </v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab"> 
          <v-tab-item>
            <div class='p-3'>
              
              <!-- 친구추가 팝업창 -->
               <v-dialog
                  v-model="dialog2"
                  persistent
                  max-width="400"
                  >
                  
                  <template v-slot:activator="{ on, attrs }">
                    <div class='text-center pt-3' v-bind="attrs" v-on="on">
                      <h5><v-icon>mdi-plus-circle</v-icon>&nbsp; 친구 추가</h5>
                    </div>
                  </template>
                  <v-card>
                    <h4 class="p-3 text-center">
                      친구 추가
                    </h4>


                    <v-container v-if="tmplist.length > 0" class="p-3">
                      <div>
                        <v-container>
                          <v-row>
                            <v-col cols='3' v-for="(friend, i) in tmplist" :key="i">
                              <v-row>
                                <v-col class="d-flex align-center p-1">
                                <v-avatar  style="text-center">
                                  <img
                                    :src="friend[3]"
                                    alt="John"
                                  >
                                </v-avatar> 
                                </v-col>
                              </v-row>
                              <v-row class="d-flex align-center" style="text-center font-size: 0.9rem;">
                                <v-col cols='7' class="d-flex align-center p-0">{{ friend[1] }} </v-col>
                                <v-col class="d-flex-inline align-center p-0" cols='2'><v-icon @click="deleteTemporaryList(i)">mdi-close-circle-outline</v-icon></v-col>
                              </v-row>
                            </v-col>
                          </v-row>
                        </v-container>
                      </div>
                    </v-container>

                    

                    <v-card-text>
                      <v-text-field
                      v-model="addFriend"
                      placeholder="이메일 혹은 이름을 입력해주세요."
                      @keypress.enter="searchUserData"
                      @click="searchUserData"
                      append-icon="mdi-magnify"
                      single-line
                      hide-details
                      >
                      </v-text-field>
                      </v-card-text>
                      
                      <div>
                        <v-container>
                          <div v-for="(friend, i) in searchFriendList" :key="i">
                              <v-container>

                              <v-row>
                                <v-col cols='2'>
                                <v-avatar>
                                  <img
                                    :src="friend[3]"
                                  >
                                </v-avatar>
                                </v-col>
                                <v-col class="d-flex align-center pl-2" cols='5' 
                                style="font-size: 0.9rem; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"
                                >{{ friend[1] }}</v-col>
                                <v-col class="d-flex align-center" cols='3' 
                                style="font-size: 0.9rem; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"
                                >{{ friend[2] }}</v-col>
                                <v-col class="d-flex align-center" cols='2'><v-icon @click="addTemporaryList(friend)">mdi-plus</v-icon></v-col>
                              </v-row>
                              </v-container>
                          </div>
                        </v-container>
                      </div>

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
            
            <MypageFriendList :dbfriend='this.dbfriend' @get_freind_list="getFreindList"/>
            </div>
          </v-tab-item>
          <v-tab-item>
            <v-card>
              <!-- 좋아하는 장소 -->
              <MypageLikePlace />
            </v-card>
          </v-tab-item>
        </v-tabs-items>
    </div>
</template>
<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
import jwt_decode from "jwt-decode";
const config = {
          headers: { 'auth-token': window.localStorage.getItem('auth-token') },
      };
import MypageLikePlace from "../../components/user/mypage/MypageLikePlace"
import MypageFriendList from "../../components/user/mypage/MypageFriendList"
import ProfileUpload from "../../components/file/ProfileUpload"

export default {
    name: 'MyPage',
    components: {
      MypageLikePlace,
      MypageFriendList,
      ProfileUpload
    },
    methods: {
      showImageDialog () {
        console.log('클릭됨?')
        this.ImageDialog = true
      },
      // 이름 변경 url
      ChangeNameClick () {
        const changeUserInfo = { uEmail: this.uEmail, uName: this.ChangeName }
        axios.put(`${server_URL}/letsmeet/user/mypage/updatename`, changeUserInfo) 
        .then(()=> {
          this.dialog1 = false
          this.uName = this.ChangeName
          this.$store.dispatch('FETCH_USER_NAME', this.uName)
        })
        .catch((error)=> {
          console.log(error)
        })
      },
      addTemporaryList(friend) {
        for (var fr of this.friendlist){
          if (friend[1] === fr[2]){
            alert("이미 추가된 친구입니다!")
            return
          }
        }
        for (var fr1 of this.tmplist){
          if (friend[1] === fr1[2]){
            alert("이미 추가 되었습니다.")
            return
          }
        }
        this.tmplist.push([friend[0], friend[2], friend[1], friend[3]])
      },
      addMyFriendList () {
        this.dbfriend = []
        var dbfriend2 = []
        // console.log(this.tmplist)
        for(let fr of this.tmplist) {
          axios.post(`${server_URL}/letsmeet/mypage/friend/add?friend=${fr[0]}&myUNo=${this.$store.state.uNo}`, config)
          .then(()=> {
            dbfriend2.push(fr)

            var img = 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg'
            const content = `${this.$store.state.uName}님이 당신을 친구 추가하였습니다./${this.$store.state.uNo}`
            const title = '친구 추가 알림'
            if (this.$store.state.uImage) {
              img = this.$store.state.uImage
            }
            axios.post(`${server_URL}/letsmeet/alarm/create?aContent=${content}&aRecvUNo=${fr[0]}&aSenderImage=${img}&aTitle=${title}`)
            .then((res) => {
              console.log(res.data)
            })
            .catch((err) => {
              console.log(err)
            })

          })
          .catch(()=> {
            console.log('못드감')
          })
        }
        this.dialog2 = false
        console.log(this.dbfriend)
        console.log(dbfriend2)
        this.dbfriend = dbfriend2
        this.tmplist = []
      },

      searchUserData() {
        this.searchFriendList = []
        if (this.addFriend.length > 0) {
          axios.get(`${server_URL}/letsmeet/mypage/friend/search?uEmail=${this.addFriend}`, config)
          .then((res)=> {
            // 비어 있지않을 때
            const array = res.data
            if (array.length) {
              array.forEach(li => {
                if(li.uNo === this.$store.state.uNo) return
                var img = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJ0zR3D2SYdonO9a3Yfzfh1RNq1nWGN3WD4w&usqp=CAU'
                if (li.uImage) {
                  img = li.uImage
                }
                this.searchFriendList.push([li.uNo, li.uEmail, li.uName, img])
              });
            this.addFriend = ''
            } else {
              alert('검색 결과가 없습니다.')
            }
          })
          .catch(()=> {
          })
        }
      },

      logout () {
        this.$store.dispatch('LOGOUT')
        .then(()=> {
          this.$router.replace({ name: 'Login'})
        })
      },
      cancelAdd() {
        this.tmplist = []
        this.dialog2 = false
      },
      deleteTemporaryList(idx) {
        this.tmplist.splice(idx, 1)
      },
      getFreindList(data) {
        this.friendlist = data
      },
      deleteWant () {
        this.deleteDialog = true
      },
      deleteUser() {
        // const userdelete = { uEmail: this.uEmail, uPassword:  this.uPassword}
        axios.delete(`${server_URL}/letsmeet/user/delete/${this.uEmail}`, config)
        .then(()=> {
          alert('회원 탈퇴가 되었습니다.')
          this.$store.dispatch('DELETE_ACCOUNT')
          this.$router.replace({ name: 'Login' })

        })
        .catch((err)=> { console.log(err) })
      },
      updateImage(data) {
        this.uImage= data
        this.$store.dispatch('FETCH_USER_IMAGE', this.uImage)
      }
    },
    mounted() { 
      let token = localStorage.getItem('auth-token')
      let decode = jwt_decode(token);
      this.uPassword = decode.user.uPassword
      this.uEmail = this.$store.state.uEmail
      this.uName= this.$store.state.uName
      this.uImage = this.$store.state.uImage
      console.log(this.uImage)
    },
    data: () => {
      return {
        deleteDialog: false,
        ImageDialog: false,
        tmplist: [],
        friendlist: [],
        UserNo: null,
        tab: null,
        dbfriend : [],
        addFriend: '',
        searchFriendList: [],
        showNoResults: false,
        dialog1: false,
        dialog2: false,
        dialog3: false,
        uEmail: '',
        uName: '',
        uImage: '',
        uPassword: '',
        items: [
          '친구목록',
          '찜한 장소'
        ],
        ChangeNameField: false,
        ChangeName:'',
      }
    }
}
</script>
<style scoped>
  .userinfo > div {
    display: flex;
    margin: 20px;
  }
  .account {
    padding-left:25px;
  }
  .ro {
    text-decoration: none !important;
    color: inherit !important;
  }
 

</style>