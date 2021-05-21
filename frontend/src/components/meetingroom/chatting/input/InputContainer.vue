<template>
  <div class="input__container">
    <slot name="input-field">
      <div class="input__field">
        <input
          v-model="message"
          type="text"
          placeholder="메시지를 입력해주세요."
          autofocus
          @keydown.enter.prevent="send"><br>
      </div>
    </slot>
    <slot name="input-button">
      <div
        class="input__button"
        @click="send">
        보내기
      </div>
    </slot>
  </div>
</template>

<script>
export default {
  name: 'InputContainer',
  data: function () {
    return {
      message: '',
    }
  },
  methods: {
    onNewOwnMessage () {
      if (!this.message || this.message === '') {
        return
      }
      this.$emit('newOwnMessage', this.message)
      this.message = ''
    },
    inputMessage(value) {
      this.message = value
    },
    send () {
      this.onNewOwnMessage ()
    },
  }
}
</script>

<style lang="scss" scoped>
.visible {
  display: none;
}
</style>
