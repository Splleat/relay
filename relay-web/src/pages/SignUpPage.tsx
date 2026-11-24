import { signUp } from '../api/auth.ts';
import { Link, useNavigate } from 'react-router';
import { useActionState } from 'react';
import { getErrorMessage } from '../api/errors.ts';
import type { FormState } from '../types/common.ts';

export function SignUpPage() {
    const navigate = useNavigate();

    const signUpAction = async (
        _prevState: FormState,
        formData: FormData,
    ): Promise<FormState> => {
        const email = formData.get('email') as string;
        const name = formData.get('name') as string;
        const password = formData.get('password') as string;

        try {
            await signUp({ email, name, password });

            navigate('/login');

            return { error: null };
        } catch (error) {
            const errorMessage = getErrorMessage(error);

            return { error: errorMessage };
        }
    };

    const [state, formAction, isPending] = useActionState(signUpAction, {
        error: null,
    });

    return (
        <div className="flex min-h-dvh items-center justify-center bg-sidebar px-4">
            <div className="w-full max-w-sm rounded-2xl border border-border bg-surface p-8 shadow-sm">
                <h1 className="text-xl font-semibold">회원가입</h1>
                <p className="mt-1 text-sm text-muted">
                    이메일로 새 계정을 만듭니다.
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
                        <label htmlFor="name" className="field-label">
                            이름
                        </label>
                        <input
                            id="name"
                            name="name"
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
                        {isPending ? '회원가입 중...' : '회원가입'}
                    </button>
                </form>
                <p className="mt-6 text-center text-sm text-muted">
                    이미 계정이 있나요?{' '}
                    <Link
                        to="/login"
                        className="font-medium text-accent hover:underline"
                    >
                        로그인
                    </Link>
                </p>
            </div>
        </div>
    );
}
