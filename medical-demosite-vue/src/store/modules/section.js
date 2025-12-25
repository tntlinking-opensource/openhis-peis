const state = {
  sectionList: [],
}

const mutations = {
  CHANGE_SECTION: (state, list) => {
    state.sectionList = list || []
  },
}
export default {
  namespaced: true,
  state,
  mutations,
}

