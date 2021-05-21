<template>
  <div>
    <v-card style="height: 100%;" class="mr-1">
      <v-img
      class= 'd-flex align-end'
      :src="place.sImage"
      height="150"
      >
      </v-img>
      <v-card-title style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
          {{place.sName}}
      </v-card-title>
      <v-card-text style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
          {{place.sRoadAddress}}
      </v-card-text>
      <v-card-actions class="d-flex justify-space-between m-0 pr-0">
        <v-btn
          color="indigo accent-2"
          text
          @click="goDetail"
        >
          상세보기
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "RecommendItem",
  props: {
    place: Object,
    roomInfo: Object,
  },
  methods: {
    goDetail() {
      this.$emit('go_detail', this.place)
    },
  }
}
</script>

<style>

</style>