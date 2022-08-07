<template>
    <div>
        <FeedHeader />
        <FeedContent/>
    </div>
</template>
<script>
import FeedContent from '@/components/FeedContent.vue'
import FeedHeader from '@/components/FeedHeader.vue'
import Button from '../components/common/Button.vue'

import { mapState } from 'vuex'

export default {
    name: 'Feed',
    components: {
        FeedHeader,
        Button,
        FeedContent
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
.feed-wrap {
    border: 1px solid black;
}
</style>