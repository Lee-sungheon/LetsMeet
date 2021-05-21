<template>
  <div class="message--foreign">
    <div class="message__author">
      <v-avatar size="30">
        <img :src="image"></v-avatar>
    </div>
    <div>
      <div class="text-start">{{ author }}</div>
      <div class="message__contents">
        {{ contents }}
      </div>
    </div>
    <div class="justify-end message__date">
      {{ date }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'MessageForeign',
  props: {
    author: {
      type: String,
      required: false
    },
    date: {
      type: String,
      required: false
    },
    contents: {
      type: String,
      default: '',
      required: false
    },
    image: {
      type: String,
      default: 'https://d2u3dcdbebyaiu.cloudfront.net/uploads/atch_img/436/8142f53e51d2ec31bc0fa4bec241a919_crop.jpeg',
    }
  },
}
</script>

<style>

</style>
