<template>
  <v-container>
    <AddMember :members="members" :roomInfo="roomInfo" @addMember="addMember"/>
    <v-list>
      <v-subheader>멤버 목록</v-subheader>
      <v-list-item-group v-model="model">
        <v-list-item
          v-for="(item, idx) in members"
          :key="idx"
        >
          <v-list-item-avatar>
            <v-img :src="item.avatar"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-text="item.uName"></v-list-item-title>
          </v-list-item-content>

          <v-list-item-content>
            <v-list-item-title v-text="item.uEmail"></v-list-item-title>
          </v-list-item-content>

          <v-list-item-content v-if="!item.icon" class="m-0 p-0">
            <v-col v-if="superNo === uNo" @click='deleteMember(item.uNo)'><v-icon>mdi-trash-can</v-icon></v-col>
          </v-list-item-content>

          <v-list-item-content v-else class="m-0 p-0">
            <v-icon
              v-if="item.icon"
              color="pink"
            >
              mdi-star
            </v-icon>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
    <v-subheader>이 멤버로 또 다른 약속을 잡고 싶다면?</v-subheader>
    <v-btn block color="indigo accent-2" class="white--text" @click="rememberMeeting">이멤버 리멤버 
      <v-icon right> mdi-account-multiple </v-icon>
    </v-btn>
  </v-container>
</template>

<script>
const axios = require('axios');
const server_URL = process.env.VUE_APP_SERVER_URL
import AddMember from './AddMember.vue'

export default {
  name: "Member",
  components: {
    AddMember,
  },
  data () {
    return {
      rNo: this.$route.params.id,
      members: [
      ],
      model: null,
      superNo : this.roomInfo.mrSuperUNo,
      uNo : this.$store.state.uNo
    }
  },
  props: {
    mrUserInfo: Array,
    roomInfo: Object,
  },
  mounted() {
    // this.MemberList()
    this.getMemberList()
  },
  methods: {
    getMemberList(){

      console.log(this.superNo)
      for (var member of this.mrUserInfo) {
        var icon = false
        if (member.uNo === this.roomInfo.mrSuperUNo){
          icon = true
        }
        var img = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJ0zR3D2SYdonO9a3Yfzfh1RNq1nWGN3WD4w&usqp=CAU'
        if (member.uImage) {
          img = member.uImage
        }
        var data = {
          icon: icon,
          uName: member.uName,
          uEmail: member.uEmail,
          avatar: img,
          uNo: member.uNo
        }
        this.members.push(data)
      }
    },
    deleteMember(uNo) {
      for (var i=0 ; i< this.members.length ; i++) {
        if (this.members[i].uNo === uNo){
          this.members.splice(i, 1)
        }
      }
      axios.delete(`${server_URL}/letsmeet/meetingRoomUser/delete?mrNo=${parseInt(this.rNo)}&uNo=${parseInt(uNo)}`)
      .then(()=> {
        this.$emit('addMember')
        console.log('성공')
      })
      .catch(()=> {
        console.log("실패")
      })
    },
    addMember(data) {
      this.members.push(data)
      this.$emit('addMember')
    },
    rememberMeeting() {
      this.$router.push({name:"MakeMeeting", params:{"members":this.mrUserInfo}})
    }
  },
}
</script>

<style>

</style>