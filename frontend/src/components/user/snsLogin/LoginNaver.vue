<template>
    <img src="@/assets/images/snsLogin/naver.png" id="naverIdLogin" style='width:13%;' alt="" @click="loginWithNaver" />
    <!-- <div id="naverIdLogin"></div> -->
</template>

<script>
import { naverService } from '@/services/login';
export default {
    mounted() {
        naverService().setNaver();
    },
    methods : {
        loginWithNaver() {
            // console.log('클릭');
            naverService().tryLogin();
        },
    },
};
</script>

<style scoped>
</style>