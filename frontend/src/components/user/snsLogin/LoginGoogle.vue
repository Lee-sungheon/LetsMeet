<template>
    <!-- <div class="g-signin2" id="google-signin-btn" data-onsuccess="onSignIn" ></div> -->
    <img src="@/assets/images/snsLogin/google.png" style='width:13%;' alt="">
</template>

<script>
import { getGoogleToken } from '@/services/login';
export default {
    created() {
        window.onSignIn = getGoogleToken;
    },
}
</script>

<style scoped>
    .g-signin2 {
        display: flex;
        justify-content: center;
    }
</style>