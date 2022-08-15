<template>
    <div class="album-wrap">
        <router-view></router-view>
        <BottomTap feedLink="nav-link" refrideLink="nav-link" albumLink="nav-link active" calendarLink="nav-link"></BottomTap>
    </div>
</template>

<script>
import BottomTap from '@/components/common/BottomTap.vue'
// import fab from 'vue-fab'

export default {
  components: { BottomTap },
  data () {
    return {
    //   bgColor: '#778899',
    //   position: 'bottom-right',
    //   fabActions: [
    //     {
    //       name: 'cache',
    //       icon: 'cached'
    //     },
    //     {
    //       name: 'alertMe',
    //       icon: 'add_alert'
    //     }
    //   ]
    }
  },
  methods: {
    // cache () {
    //   console.log('Cache Cleared')
    // },
    // alert () {
    //   alert('Clicked on alert icon')
    // }
  }
}
</script>

<style scoped lang="scss">
.album-wrap{
    width: 900px;
}
</style>
