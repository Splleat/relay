import { fetchApi } from './client.ts';

export interface SendMessageRequest {
    content: string;
    idempotencyKey: string;
}

export interface SendMessageResponse {
    isCreated: boolean;
    message: MessageResponse;
}

export interface MessageResponse {
    messageId: string;
    channelId: string;
    senderId: string;
    content: string;
    createdAt: string;
}

export interface ChannelMessageResponse {
    messages: MessageResponse[];
    hasPrev: boolean;
    hasNext: boolean;
}

export async function sendMessage(
    channelId: string,
    request: SendMessageRequest,
): Promise<SendMessageResponse> {
    return await fetchApi<SendMessageResponse>(
        `/channels/${channelId}/messages`,
        'POST',
        request,
    );
}

export async function getChannelMessages(
    channelId: string,
    before?: string,
    after?: string,
    limit?: string,
) {
    const params = new URLSearchParams();

    if (before) params.append('before', before);

    if (after) params.append('after', after);

    if (limit) params.append('limit', limit);

    return await fetchApi<ChannelMessageResponse>(
        `/channels/${channelId}/messages?${params.toString()}`,
    );
}
