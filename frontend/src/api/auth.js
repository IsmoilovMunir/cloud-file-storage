import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
})
export const login = (username, password) =>
    api.post('/api/auth/login', new URLSearchParams({username, password}))
export const register = (username, password) =>
    api.post('/api/auth/register', {username, password})

export const logout = () =>
    api.post('/api/auth/logout')