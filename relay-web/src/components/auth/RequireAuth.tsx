import { Navigate, Outlet } from 'react-router';
import { useAuthStatus } from '../../hooks/useAuthStatus.ts';
import { connectStomp, disconnectStomp } from '../../api/stomp.ts';
import { useEffect } from 'react';

export function RequireAuth() {
    const status = useAuthStatus();

    switch (status) {
        case 'loading':
            return <p>확인 중...</p>;
        case 'unauthenticated':
            return <Navigate to="/login" replace />;
        case 'error':
            return <p>서버에 연결할 수 없습니다.</p>;
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
