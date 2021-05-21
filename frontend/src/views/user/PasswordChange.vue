<template>
  <div>
    <div class="d-flex justify-center mx-5 pt-4" style="border-bottom:2px solid black">
      <h5>비밀번호 변경</h5>
    </div>
    <v-container>
      <v-row class="px-5 pt-5">
        <v-text-field 
        label='현재 비밀번호'
        v-model="nowpw"
        @keyup="nowpwcheck"
        :rules="[NowPasswordCheck]"
        :type="showPassword1 ? 'text' : 'password'"
        append-icon="showPassword1 ? 'mdi-eye' : 'mdi-eye-off'"
        @click:append="showPassword1 = !showPassword1"
        >
        </v-text-field>
      </v-row>
      <v-row class="px-5 pt-2">
        <v-text-field label='새 비밀번호'
          ref='newPassword'
          v-model="user.uPassword"
          :rules="[NewPasswordCheck]"
          :type="showPassword2 ? 'text' : 'password'"
          :append-icon="showPassword2 ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="showPassword2 = !showPassword2"
        >
        </v-text-field>
      </v-row>
      <v-row class="px-5 pt-2">
          <v-text-field 
          label='비밀번호 다시 입력'
          v-model="newpasswordconfirm"
          :rules="[rules.required, NewDoublePasswordCheck]"
          :type="showPassword3 ? 'text' : 'password'"
          :append-icon="showPassword3 ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="showPassword3 = !showPassword3"
          >

        </v-text-field>
        <br><br><br><br>
      </v-row>
        <v-btn @click="PasswordChangeMethod" class="px-3 my-5" color="indigo accent-2" rounded style='color: white; width:100%'> 정보 수정 </v-btn>
      <router-link class="ro" :to="{ name: 'MyPage' }"><v-icon large>mdi-arrow-left-circle</v-icon></router-link>
    </v-container>
  </div>

</template>
<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
import jwt_decode from "jwt-decode";
const config = {
          headers: { 'auth-token': window.localStorage.getItem('auth-token') },
      }

export default {
    name: 'PasswordChange',
    data: () => {
      return  {
        uImageId: '',
        user: {
          uEmail : "",
          uPassword: '',
          // uName: '',
          uSalt: '',
        },
        dialog1: false,
        dialog2: false,
        newPassword:'',
        beforepw: '',
        nowpw: '',
        passwordModify: false,
        newpasswordconfirm: '',
        showPassword1: false,
        showPassword2: false,
        showPassword3: false,
        Nowpassword : false,
        rules: {
            required: value => !!value,
        },
      }
    },
    computed: {
      NowPasswordCheck () {
        return this.Nowpassword === true || '현재 비밀번호와 일치하지 않습니다.'
      },
      NewPasswordCheck () {
        return () => (this.nowpw !== this.user.uPassword) || '이전 비밀번호와 일치합니다. 다시 입력해주세요.'
      },
      NewDoublePasswordCheck() {
        return () => (this.user.uPassword === this.newpasswordconfirm) || '비밀번호가 일치하지 않습니다.' 
      }
    },
    mounted() { 
      this.user.uEmail = this.$store.state.uEmail
      this.user.uSalt = this.$store.state.uSalt
      // this.user.uName= this.$store.state.uName
      let token = localStorage.getItem('auth-token')
      let decode = jwt_decode(token);
      this.beforepw = decode.user.uPassword
    },

    methods: {
      nowpwcheck() {
        axios.get(`${server_URL}/letsmeet/user/mypage/updatecheckpassword?pastPassword=${this.nowpw}&uEmail=${this.user.uEmail}&uPassword=${this.beforepw}`)
        .then((res)=> {
          console.log(res.data)
          if (res.data === '현재 비번 일치'){
            this.Nowpassword = true
          } else {
            this.Nowpassword = false
          }
        })
        .catch(()=> {
          console.log('안되나아???')
          this.Nowpassword = false
        })

      },
      PasswordChangeMethod() {
        axios.put(`${server_URL}/letsmeet/user/mypage/updatepassword`, this.user, config)
        .then((res)=> {
          console.log(res.data)
          alert('비밀번호가 변경되었습니다.')
          this.$router.replace({ name: 'MyPage' })

        })
        .catch(() => {
          console.log('비밀번호 변경 실패')
        })
      },

    },
} 

</script>
