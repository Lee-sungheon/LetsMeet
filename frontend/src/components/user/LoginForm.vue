<template>
  <div class='p-3'>
    <v-form ref='form'>
    <!-- <v-card-title class="pb-0">
        <h5 >로그인</h5>
    </v-card-title> -->
    <v-card-text>
        <v-text-field 
            ref ='email'
            v-model="user.uEmail"
            label="이메일" 
            prepend-icon="email"
            :rules="[rules.required, rules.email]"
        />
        <v-text-field 
            ref ='password'
            v-model="user.uPassword"
            :type="showPassword ? 'text' : 'password'"
            :rules="[rules.required]"
            label="비밀번호"
            prepend-icon="mdi-lock"
            :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="showPassword = !showPassword"
            @keyup.enter='loginsubmit'
        />
 
    </v-card-text>
    <v-card-actions>
        <v-btn @click='loginsubmit' class="text-white" color="indigo accent-2" rounded style='width:100%'>Login</v-btn>
    </v-card-actions>
    </v-form>
  </div>
</template>


<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
export default {
    name: 'LoginForm',

    components: {
    },
    methods: {
      loginsubmit() { 
        // 이메일, 비번 다 기입했는지 확인 
        if (!this.$refs.form.validate()) return console.log('다 안채워짐')
        else {
          console.log('확인')
          localStorage.setItem('auth-token', '')
          axios.post(`${server_URL}/letsmeet/login`, this.user)
            .then((res) => {
              let token = res.data['auth-token']
              if (token === undefined) {
                alert('비밀번호가 틀렸습니다.')}
              else {
                alert('로그인 되었습니다.')
                // context.commit('SET_USER_AUTH_DATA', res.data)
                localStorage.setItem('auth-token', token)
                // axios default 헤더에 현재 token 적재
                axios.defaults.headers.common['auth-token'] = window.localStorage.getItem("auth-token");
                this.$store.commit('SET_USER_AUTH_DATA', res.data)
                this.$router.push({ name: 'Main'})
                }
              })
            .catch((err) => {
              console.log(err)
              alert('로그인에 실패하셨습니다.')
            })
          // this.$store // 현재 컴포넌트에서 저장소 접근하여
          //   .dispatch('LOGIN', this.user) // 비동기적인 Actions에 접근한다 (현 컴포넌트의 user 데이터를 가지고, 저장소에 LOGIN이라는 Actions에 접근)
          //   .then((res) => 
          //     console.log(res.data)
              
          //     // this.$router.push({ name: 'Main'})
          //     ) // 정상적으로 접근했다면 현 컴포넌트의 nextRoute url 값을 대체한다.???
          //   .catch(() => alert('로그인에 실패했습니다.'));

        }
      }
    },
    data: () => {
        return {
          user : {
            uEmail: '',
            uPassword: '',
          },
            showPassword: false,
            formHasErrors: false,
            rules: {
              required: value => !!value,
              counter: value => value.length <= 20 || '최대 20자까지 가능합니다.',
              email: value => { 
                const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                return pattern.test(value) || '이메일 형식이 아닙니다.'
            },
          }
      }
    }
}

</script>
<style scoped>

</style>