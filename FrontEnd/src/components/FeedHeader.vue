<template>
    <div>
        <h2>피드 헤더</h2>
    </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
    name: 'FeedHeader',
    date() {
        return {

        }
    },
    created() {

    },
    computed: {
        ...mapState["familyId", "userId"]
    },
    methods: {
        
    }
}
</script>