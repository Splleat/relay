import { type ChangeEvent, type SubmitEvent, useState } from 'react';
import { login } from '../api/auth.ts';
import { useNavigate } from 'react-router';
import { getErrorMessage } from '../api/errors.ts';

export function LoginPage() {
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleEmailChange = (e: ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
    };

    const handlePasswordChange = (e: ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    };

    const handleSubmit = async (e: SubmitEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (!email.trim()) {
            setError('이메일을 입력해주세요.');
            return;
        }

        if (!password.trim()) {
            setError('비밀번호를 입력해주세요.');
            return;
        }

        try {
            await login({ email, password });

            navigate('/');
        } catch (error) {
            const errorMessage = getErrorMessage(error);

            setError(errorMessage);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div className="flex flex-col gap-1">
                    <div className="flex flex-row gap-2">
                        <label htmlFor="email">이메일</label>
                        <input
                            id="email"
                            type="email"
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                    </div>
                    <div className="flex flex-row gap-2">
                        <label htmlFor="password">비밀번호</label>
                        <input
                            id="password"
                            type="password"
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                    </div>
                    {error && <p className="text-red-500">{error}</p>}
                </div>
                <button type="submit">로그인</button>
            </form>
        </div>
    );
}
