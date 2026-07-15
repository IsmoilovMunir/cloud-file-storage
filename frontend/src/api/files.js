import axios from 'axios'
const api = axios.create({
    baseURL: 'http://localhost:8080',
    withCredentials: true
})

export const listFiles = (path = '') =>
    api.get('/api/files', { params : { path } })

export const uploadFile = (file, path) =>{
    const formData = new FormData()
    formData.append('file', file)
    return api.post(`/api/files/upload?path=${path}`, formData)
}
export const deleteFile = (path) =>{
    return api.delete(`/api/files`, { params : { path } })
}
export const downloadFile = (path) =>{
    return api.get('/api/files/download', { params : { path }, responseType: 'blob' })
}
export const createFolder = (path) =>
    api.post('api/files/folder', null, { params: { path } })
export const searchFiles = (query) =>
    api.get('/api/files/search', {params: {query} })