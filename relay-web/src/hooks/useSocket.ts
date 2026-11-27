import { useContext } from 'react';
import { SocketContext } from '../components/stomp/SocketContext.tsx';

export function useSocket() {
    const context = useContext(SocketContext);

    if (!context) {
        throw new Error('useSocket은 SocketProvider 내부에서 사용해야 합니다.');
    }

    return context;
}
