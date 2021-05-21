<template>
    <img
        class="kakao_btn"
        src="@/assets/images/snsLogin/kakao.jpg" style='width:13%;' alt=""
        @click="loginWithKakao"
    />
</template>

<script>
export default {
    name: "LoginKakao",
    methods: {
        loginWithKakao() {
            const params = {
                redirectUri: "https://i4d107.p.ssafy.io/kakaocallback",
            };
            window.Kakao.Auth.authorize(params);
        },
    },
};
</script>