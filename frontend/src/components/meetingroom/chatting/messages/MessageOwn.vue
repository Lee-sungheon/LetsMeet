<template>
  <div
    class="message--own">
    <div class="message__date">
      {{ date }}
    </div>
    <div class="message__contents">
      {{ contents }}
    </div>
  </div>
</template>

<script>

export default {
  name: 'MessageOwn',
  components: {
  },
  props: {
    date: {
      type: String,
      required: false
    },
    contents: {
      type: String,
      default: '',
      required: false
    }
  }
}
</script>

<style>

</style>
