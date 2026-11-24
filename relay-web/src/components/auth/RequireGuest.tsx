import { useAuthStatus } from '../../hooks/useAuthStatus.ts';
import { Navigate, Outlet } from 'react-router';
import { StatusScreen } from '../../ui/StatusScreen.tsx';

export function RequireGuest() {
    const status = useAuthStatus();

    switch (status) {
        case 'loading':
            return <StatusScreen>확인 중...</StatusScreen>;
        case 'unauthenticated':
            return <Outlet />;
        case 'error':
            return <Outlet />;
        case 'authenticated':
            return <Navigate to="/" replace />;
    }
}
