import { useActionState } from 'react';
import { login } from '../api/auth.ts';
import { useNavigate } from 'react-router';
import { getErrorMessage } from '../api/errors.ts';

type LoginState = { error: string | null };

export function LoginPage() {
    const navigate = useNavigate();

    const loginAction = async (
        _prevState: LoginState,
        formData: FormData,
    ): Promise<LoginState> => {
        const email = formData.get('email') as string;
        const password = formData.get('password') as string;

        try {
            await login({ email, password });

            navigate('/');

            return { error: null };
        } catch (error) {
            const errorMessage = getErrorMessage(error);

            return { error: errorMessage };
        }
    };

    const [state, formAction, isPending] = useActionState(loginAction, {
        error: null,
    });

    return (
        <div>
            <form action={formAction}>
                <div className="flex flex-col gap-1">
                    <div className="flex flex-row gap-2">
                        <label htmlFor="email">이메일</label>
                        <input id="email" name="email" type="email" required />
                    </div>
                    <div className="flex flex-row gap-2">
                        <label htmlFor="password">비밀번호</label>
                        <input
                            id="password"
                            name="password"
                            type="password"
                            required
                        />
                    </div>
                    {state.error && (
                        <p className="text-red-500">{state.error}</p>
                    )}
                </div>
                {isPending ? (
                    <button type="submit" disabled>
                        로그인 중...
                    </button>
                ) : (
                    <button type="submit">로그인</button>
                )}
            </form>
        </div>
    );
}
