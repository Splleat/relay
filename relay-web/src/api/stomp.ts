import { Client, type IMessage } from '@stomp/stompjs';
import { getCsrfToken } from './client.ts';
import type { MessageResponse } from './message.ts';

const client = new Client({
    brokerURL: '/ws',
    beforeConnect: async (client) => {
        const csrf = await getCsrfToken();

        client.connectHeaders = {
            'X-CSRF-TOKEN': csrf,
        };
    },
});

export function connectStomp() {
    client.activate();
}

export function disconnectStomp() {
    client.deactivate();
}

export function subscribeChannel(
    channelId: string,
    onMessage: (message: MessageResponse) => void,
) {
    // TODO: 소켓 연결 이전 구독 케이스 처리 필요
    if (!client.connected) return () => {};

    console.log(`채널 구독: ${channelId}`);

    const subscribe = client.subscribe(
        `/sub/channels/${channelId}`,
        (message: IMessage) => {
            const body: MessageResponse = JSON.parse(message.body);

            onMessage(body);
        },
    );

    return () => {
        subscribe.unsubscribe();

        console.log(`채널 구독 해제: ${channelId}`);
    };
}
