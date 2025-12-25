import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  for (let i = 0; i < localStorage.length; i++) {
    if (localStorage.key(i).includes('section')) {
      localStorage.removeItem(localStorage.key(i))
    }
    if (localStorage.key(i) == 'pacsRummagerInfo') {
      localStorage.removeItem(localStorage.key(i))
    }
    if (localStorage.key(i) == 'pacsWriteInfo') {
      localStorage.removeItem(localStorage.key(i))
    }
  }
  return Cookies.remove(TokenKey)
}
