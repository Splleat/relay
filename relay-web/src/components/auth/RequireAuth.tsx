import { Navigate, Outlet } from 'react-router';
import { useAuthStatus } from '../../hooks/useAuthStatus.ts';
import { StatusScreen } from '../../ui/StatusScreen.tsx';
import { SocketProvider } from '../stomp/SocketProvider.tsx';

export function RequireAuth() {
    const status = useAuthStatus();

    switch (status) {
        case 'loading':
            return <StatusScreen>확인 중...</StatusScreen>;
        case 'unauthenticated':
            return <Navigate to="/login" replace />;
        case 'error':
            return <StatusScreen>서버에 연결할 수 없습니다.</StatusScreen>;
        case 'authenticated':
            return (
                <SocketProvider>
                    <Outlet />
                </SocketProvider>
            );
    }
}
