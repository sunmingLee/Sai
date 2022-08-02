import { apiInstance } from './index.js'

const api = apiInstance()

async function login (user, success, fail) {
  await api.post('/api/user/login', JSON.stringify(user)).then(success).catch(fail)
}

async function findById (userid, success, fail) {
  // api.defaults.headers["Authorization"] =
  //   "Bearer " + sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail)
}

function updateUser (user, success, fail) {
  // api.defaults.headers["Authorization"] =
  //   "Bearer " + sessionStorage.getItem("access-token");
  console.log(JSON.stringify(user))
  api.put('/user/update', JSON.stringify(user)).then(success).catch(fail)
}

function checkId (id, success, fail) {
  api.get(`/user/idcheck?ckid=${id}`).then(success).catch(fail)
}

function registerUser (user, success, fail) {
  api.post('/user/register', JSON.stringify(user)).then(success).catch(fail)
}

// function logout(success, fail)

export { login, findById, updateUser, checkId, registerUser }
