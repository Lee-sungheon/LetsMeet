import axios from '@/services/axios';

const kakaoHeader = {
    'Authorization': 'c7f41dd3c6ebfe77ee6ffffa6e688c9b',
    'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
};
const getKakaoToken = async (code) => {
    console.log('loginWithKakao');
    try {
        const data = {
            grant_type: 'authorization_code',
            client_id: 'deace353f1fd1555201a07f914bc5598',
            redirect_uri: 'https://i4d107.p.ssafy.io/kakaocallback',
            code: code,
        };
        const queryString = Object.keys(data)
            .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(data[k]))
            .join('&');
        const result = await axios.post('https://kauth.kakao.com/oauth/token', queryString, { headers: kakaoHeader });
        console.log('카카오 토큰', queryString);
        return result;
    } catch (e) {
        return e;
    }
};

const getKakaoUserInfo = async () => {
    let data = '';
    await window.Kakao.API.request({
        url: "/v2/user/me",
        success: function (response) {
            data = response;
        },
        fail: function (error) {
            console.log(error);
        },
    });
    // console.log('카카오 계정 정보', data);
    return data;
};

const getGoogleToken = (googleUser) => {
    console.log('test');
    var profile = googleUser.getBasicProfile();
    console.log('ID Token: ', googleUser.getAuthResponse().id_token);
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
};

const naverService = () => {
    const naverLogin = new window.naver.LoginWithNaverId({
        clientId: "MdMhmGDLOLYqpYpSgHux",
        callbackUrl: "https://i4d107.p.ssafy.io/navercallback",
        isPopup: false /* 팝업을 통한 연동처리 여부 */,
        // loginButton: {
        //     color: "green",
        //     type: 1,
        //     height: 60,
        // } /* 로그인 버튼의 타입을 지정 */,
    });
    const setNaver = () => {
        naverLogin.init();
    };
    const tryLogin = () => {
        location.href = naverLogin.generateAuthorizeUrl();
    };
    const getUserInfo = () => {
        setNaver();
        // console.log(naverLogin);
        naverLogin.getLoginStatus((status) => {
            if (status) {
                // this.$store.commit('SET_USER_AUTH_DATA', status.data)
                const email = naverLogin.user.email;
                return email;
                // const name = naverLogin.user.name;
                // console.log(email, name);
            } else {
                console.log("AccessToken이 올바르지 않습니다.");
            }
        });
    };
    return {
        setNaver,
        getUserInfo,
        tryLogin,
    };
};

export {
    getKakaoToken,
    getKakaoUserInfo,
    getGoogleToken,
    naverService,
};