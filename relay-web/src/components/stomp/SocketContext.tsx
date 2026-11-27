import type { MessageResponse } from '../../api/message.ts';
import { createContext } from 'react';

interface SocketContextValue {
    subscribeChannel: (
        channelId: string,
        onMessage: (message: MessageResponse) => void,
    ) => void;
    isConnected: boolean;
}

export const SocketContext = createContext<SocketContextValue | null>(null);
