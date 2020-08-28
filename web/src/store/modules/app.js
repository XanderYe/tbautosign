

const app = {
  state:{
    isDesktop: true,
  },
  mutations:{
    setIsDesktop(state, obj){
      state.isDesktop = obj;
    }
  }
}

export default app;
