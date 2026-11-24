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
        <form action={action} className="flex flex-col items-center gap-1">
            {state.error && (
                <p className="px-1 text-center text-[10px] leading-tight text-danger">
                    {state.error}
                </p>
            )}
            <button
                type="submit"
                disabled={isPending}
                title="로그아웃"
                aria-label="로그아웃"
                className="flex size-11 cursor-pointer items-center justify-center rounded-xl text-rail-fg transition-colors hover:bg-rail-item hover:text-white disabled:opacity-50"
            >
                <svg
                    viewBox="0 0 24 24"
                    className="size-5"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    aria-hidden="true"
                >
                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                    <path d="m16 17 5-5-5-5" />
                    <path d="M21 12H9" />
                </svg>
            </button>
        </form>
    );
}
