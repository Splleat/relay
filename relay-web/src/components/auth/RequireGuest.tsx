import { useAuthStatus } from '../../hooks/useAuthStatus.ts';
import { Navigate, Outlet } from 'react-router';

export function RequireGuest() {
    const status = useAuthStatus();

    switch (status) {
        case 'loading':
            return <p>확인 중...</p>;
        case 'unauthenticated':
            return <Outlet />;
        case 'error':
            return <Outlet />;
        case 'authenticated':
            return <Navigate to="/" replace />;
    }
}
