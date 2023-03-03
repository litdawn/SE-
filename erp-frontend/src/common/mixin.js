const scale = {
    width: 1
}

export default {
    mounted () {
        this.calcRate()
    },
    methods: {
        calcRate () {
            const appRef = this.$refs["page"]
            if (appRef) {
                scale.width = window.innerWidth / 1920;
                appRef.style.transform = `scale(${scale.width}) translate(-50%)`
            }
            console.log(scale.width);
        },

    },
}
