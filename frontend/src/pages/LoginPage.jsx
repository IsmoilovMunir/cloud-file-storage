import { useState } from 'react'
import { login } from "../api/auth.js";
import { register } from "../api/auth.js";
import { useNavigate } from "react-router-dom"
function LoginPage() {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const navigate = useNavigate()

    const handleSubmit = async (e) => {
        e.preventDefault()
        await login(username, password)
        navigate('/files')
    }
    const handleGoToRegister = async e => {
        navigate('/register')
    }
    return (
        <div>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <input value={username} onChange={e => setUsername(e.target.value)}
                placeholder="Username" />
                <input value={password} onChange={e => setPassword(e.target.value)}
                placeholder="Password" />
                <button type="submit">Войти</button>
            </form>
            <button onClick={handleGoToRegister}>Зарегистрироваться</button>
        </div>
    )
}
export default LoginPage