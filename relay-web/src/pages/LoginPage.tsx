import { useActionState } from 'react';
import { login } from '../api/auth.ts';
import { Link, useNavigate } from 'react-router';
import { getErrorMessage } from '../api/errors.ts';
import type { FormState } from '../types/common.ts';

export function LoginPage() {
    const navigate = useNavigate();

    const loginAction = async (
        _prevState: FormState,
        formData: FormData,
    ): Promise<FormState> => {
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
        <div className="flex min-h-dvh items-center justify-center bg-sidebar px-4">
            <div className="w-full max-w-sm rounded-2xl border border-border bg-surface p-8 shadow-sm">
                <h1 className="text-xl font-semibold">로그인</h1>
                <p className="mt-1 text-sm text-muted">
                    Relay에 오신 것을 환영합니다.
                </p>
                <form action={formAction} className="mt-6 flex flex-col gap-4">
                    <div>
                        <label htmlFor="email" className="field-label">
                            이메일
                        </label>
                        <input
                            id="email"
                            name="email"
                            type="email"
                            required
                            className="input"
                        />
                    </div>
                    <div>
                        <label htmlFor="password" className="field-label">
                            비밀번호
                        </label>
                        <input
                            id="password"
                            name="password"
                            type="password"
                            required
                            className="input"
                        />
                    </div>
                    {state.error && <p className="form-error">{state.error}</p>}
                    <button
                        type="submit"
                        disabled={isPending}
                        className="btn-primary mt-2 w-full"
                    >
                        {isPending ? '로그인 중...' : '로그인'}
                    </button>
                </form>
                <p className="mt-6 text-center text-sm text-muted">
                    계정이 없나요?{' '}
                    <Link
                        to="/signup"
                        className="font-medium text-accent hover:underline"
                    >
                        회원가입
                    </Link>
                </p>
            </div>
        </div>
    );
}
