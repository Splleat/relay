import { useActionState } from 'react';
import { logout } from '../../api/auth.ts';
import { getErrorMessage } from '../../api/errors.ts';
import { useNavigate } from 'react-router';

export function LogoutButton() {
    const navigate = useNavigate();
    const [state, action, isPending] = useActionState(
        async () => {
            try {
                await logout();

                navigate('/login');

                return { error: null };
            } catch (error) {
                const errorMessage = getErrorMessage(error);

                return { error: errorMessage };
            }
        },
        { error: null },
    );

    return (
        <form action={action} className="flex flex-col gap-2">
            {state.error && <p className="text-red-500">{state.error}</p>}
            <button type="submit" disabled={isPending}>
                {isPending ? '로그아웃 중...' : '로그아웃'}
            </button>
        </form>
    );
}
