<template>
    <div>
        <FeedHeader />
        <FeedContent/>
        <BottomTap feedLink="nav-link active" refrideLink="nav-link" albumLink="nav-link" calendarLink="nav-link"></BottomTap>
    </div>
</template>
<script>
import FeedContent from '@/components/FeedContent.vue'
import FeedHeader from '@/components/FeedHeader.vue'
import Button from '../components/common/Button.vue'
import BottomTap from '@/components/common/BottomTap.vue'
import { mapState } from 'vuex'

export default {
    name: 'Feed',
    components: {
        FeedHeader,
        Button,
        FeedContent,
        BottomTap
    },
    created() {
    },
    computed: {
    },
    methods: {

    },
    data() {
        return {

        }        
    }
}

</script>
<style lang="scss">

</style>