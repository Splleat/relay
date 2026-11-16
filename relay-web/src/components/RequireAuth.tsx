import { useEffect, useState } from 'react';
import { Navigate, Outlet } from 'react-router';
import { getMe } from '../api/user.ts';
import { ApiError } from '../api/errors.ts';

type AuthStatus = 'loading' | 'authenticated' | 'unauthenticated' | 'error';

export function RequireAuth() {
    const [status, setStatus] = useState<AuthStatus>('loading');

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                await getMe();

                setStatus('authenticated');
            } catch (error) {
                if (error instanceof ApiError && error.status === 401) {
                    setStatus('unauthenticated');
                } else {
                    console.error(error);

                    setStatus('error');
                }
            }
        };

        void fetchProfile();
    }, []);

    switch (status) {
        case 'loading':
            return <p>확인 중...</p>;
        case 'unauthenticated':
            return <Navigate to="/login" replace />;
        case 'error':
            return <p>서버에 연결할 수 없습니다.</p>;
        case 'authenticated':
            return <Outlet />;
    }
}
