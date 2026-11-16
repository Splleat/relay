import { signUp } from '../api/auth.ts';
import { useNavigate } from 'react-router';
import { useActionState } from 'react';
import { getErrorMessage } from '../api/errors.ts';

type SignUpState = { error: string | null };

export function SignUpPage() {
    const navigate = useNavigate();

    const signUpAction = async (
        _prevState: SignUpState,
        formData: FormData,
    ): Promise<SignUpState> => {
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
        <div>
            <form action={formAction}>
                <div className="flex flex-col gap-1">
                    <div className="flex flex-row gap-2">
                        <label htmlFor="email">이메일</label>
                        <input id="email" name="email" type="email" required />
                    </div>
                    <div className="flex flex-row gap-2">
                        <label htmlFor="name">이름</label>
                        <input id="name" name="name" required />
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
                        회원가입 중...
                    </button>
                ) : (
                    <button type="submit">회원가입</button>
                )}
            </form>
        </div>
    );
}
