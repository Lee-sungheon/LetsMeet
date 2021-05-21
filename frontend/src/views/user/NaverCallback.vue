<template>
    <div>
        naverLogin
    </div>
</template>

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL

// import { naverService } from '@/services/login';
export default {
    data: () => {
        return  {
          user: {
          uEmail : '',
          uPassword: '',
          uName: '',
        }
      }
    },
    mounted() {
        const naverLogin = new window.naver.LoginWithNaverId({
        clientId: "MdMhmGDLOLYqpYpSgHux",
        callbackUrl: `${server_URL}/navercallback`,
        isPopup: false /* 팝업을 통한 연동처리 여부 */,
    });
        naverLogin.init();
        // console.log(naverLogin);
        naverLogin.getLoginStatus((status) => {
            if (status) {
                this.user.uEmail = naverLogin.user.email + '_' + naverLogin.user.id;
                this.user.uName = naverLogin.user.name;
                this.user.uPassword = "NaverPassword";
                // console.log(this.user);
            } else {
                console.log("AccessToken이 올바르지 않습니다.");
            }
            axios.post(`${server_URL}/letsmeet/auth/naver/callback`, this.user)
              .then((res) => {
              // console.log(res);
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
        });
    },
};
</script>