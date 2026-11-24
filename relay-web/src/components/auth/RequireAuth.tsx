import { Navigate, Outlet } from 'react-router';
import { useAuthStatus } from '../../hooks/useAuthStatus.ts';
import { connectStomp, disconnectStomp } from '../../api/stomp.ts';
import { useEffect } from 'react';
import { StatusScreen } from '../../ui/StatusScreen.tsx';

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
            return <SocketContext />;
    }
}

function SocketContext() {
    useEffect(() => {
        connectStomp();

        console.log('STOMP 연결');

        return () => {
            disconnectStomp();
            console.log('STOMP 연결 해제');
        };
    }, []);

    return <Outlet />;
}
