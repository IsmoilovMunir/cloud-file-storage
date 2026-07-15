import {useState, useEffect} from "react";
import {deleteFile, listFiles, uploadFile, downloadFile} from "../api/files.js";
import {login, logout} from '../api/auth';
import {useNavigate} from 'react-router-dom'

function FilesPage() {
    const [files, setFiles] = useState([])
    const [path, setPath] = useState('')

    useEffect(() => {
        loadFiles()
    }, [path])

    const loadFiles = async () => {
        const res = await listFiles(path)
        setFiles(res.data)
    }

    const handleUpload = async (e) => {
        const file = e.target.files[0]
        if (!file) return
        await uploadFile(file, file.name)
        loadFiles()
    }

    const handleDelete = async (fileName) => {
        await deleteFile(fileName)
        loadFiles()
    }
    const handleLogin = async e => {
        setIsLoading(true);
        try {
            await login();
            setIsLoading(true)

        } catch (err) {
            console.log(err)
        } finally {
            setIsLoading(files)
        }
        await logout(e.currentTarget.value)
        e.preventDefault()
    }

    const navigate = useNavigate()

    const handleLogout = async () => {
        await logout();
        navigate('/login')
    }

    const handleDownload = async (fileName) => {
        const res = await downloadFile(fileName)
        const url = window.URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', fileName)
        document.body.appendChild(link)
        link.click()
        link.remove()
    }


    return (

        <div>
            <button onClick={handleLogout}>Выйти</button>

            <h2>Мои файлы</h2>
            <input type="file" onChange={handleUpload}/>
            <ul>
                {files.map((file) => (
                    <li key={file}>
                        {file}
                        <button onClick={() => handleDelete(file)}> Удалить</button>
                        <button onClick={() => handleDownload(file)}>Скачать</button>
                    </li>
                ))}
            </ul>
        </div>
    )
}

export default FilesPage