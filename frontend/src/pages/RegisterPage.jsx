import { useState } from 'react'
import { register } from '../api/auth'
import { useNavigate } from 'react-router-dom'
function RegisterPage() {
    const [username, setUsername] = useState('')

    const [password, setPassword] = useState('')

    const navigate = useNavigate()

    const handleSubmit = async (e) => {
        e.preventDefault()
        await register(username, password)
        navigate('/login')

    }

    return (
        <div>
            <h2>Регистрация</h2>
            <form onSubmit={handleSubmit}>
                <input value={username} onChange={e => setUsername(e.target.value)}
                placeholder="Логин"/>
                <input value={password} onChange={e => setPassword(e.target.value)}
                placeholder="Пароль" />
                <button type="submit"> Регистироватся</button>
            </form>
        </div>
    )
}
export default RegisterPage