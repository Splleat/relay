import {
    type ReactNode,
    useCallback,
    useEffect,
    useMemo,
    useRef,
    useState,
} from 'react';
import { Client, type IMessage } from '@stomp/stompjs';
import { getCsrfToken } from '../../api/client.ts';
import type { MessageResponse } from '../../api/message.ts';
import { SocketContext } from './SocketContext.tsx';

export function SocketProvider({ children }: { children: ReactNode }) {
    const clientRef = useRef<Client | null>(null);
    const [isConnected, setIsConnected] = useState(false);

    const subscribeChannel = useCallback(
        (channelId: string, onMessage: (message: MessageResponse) => void) => {
            const client = clientRef.current;

            if (!client?.connected) return () => {};

            console.log(`채널 구독: ${channelId}`);

            const subscribe = client.subscribe(
                `/sub/channels/${channelId}`,
                (message: IMessage) => {
                    const body: MessageResponse = JSON.parse(message.body);

                    onMessage(body);
                },
            );

            return () => {
                if (client?.connected) subscribe.unsubscribe();
            };
        },
        [],
    );

    useEffect(() => {
        const client = new Client({
            brokerURL: '/ws',
            beforeConnect: async (client) => {
                const csrf = await getCsrfToken();

                client.connectHeaders = {
                    'X-CSRF-TOKEN': csrf,
                };
            },
            onConnect: () => {
                console.log(`소켓 연결`);
                setIsConnected(true);
            },
            onWebSocketClose: () => {
                setIsConnected(false);
            },
        });

        client.activate();
        clientRef.current = client;

        return () => {
            clientRef.current?.deactivate();
        };
    }, []);

    const value = useMemo(
        () => ({ isConnected, subscribeChannel }),
        [isConnected, subscribeChannel],
    );

    return <SocketContext value={value}>{children}</SocketContext>;
}
