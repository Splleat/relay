import { useCallback, useEffect, useState } from 'react';
import { type ChannelResponse, getChannel } from '../../api/channel.ts';
import { getChannelMessages, type MessageResponse } from '../../api/message.ts';
import { ChannelHeader } from './ChannelHeader.tsx';
import { ChannelBody } from './ChannelBody.tsx';
import { MessageInput } from './MessageInput.tsx';
import { useSocket } from '../../hooks/useSocket.ts';

function mergeMessages(
    current: MessageResponse[],
    incoming: MessageResponse[],
): MessageResponse[] {
    const merged = [...current, ...incoming];
    const distinct = [
        ...new Map(
            merged.map((message) => [message.messageId, message]),
        ).values(),
    ];

    return distinct.sort((a, b) => {
        const id1 = BigInt(a.messageId);
        const id2 = BigInt(b.messageId);

        return id1 > id2 ? 1 : id1 < id2 ? -1 : 0;
    });
}

export function ChannelView({ channelId }: { channelId: string }) {
    const { isConnected, subscribeChannel } = useSocket();
    const [channel, setChannel] = useState<ChannelResponse | null>(null);
    const [messages, setMessages] = useState<MessageResponse[]>([]);
    const [hasPrev, setHasPrev] = useState(false);
    const [isPending, setIsPending] = useState(false);

    const addMessage = useCallback((message: MessageResponse) => {
        setMessages((current) => mergeMessages(current, [message]));
    }, []);

    useEffect(() => {
        if (!isConnected) return;

        return subscribeChannel(channelId, addMessage);
    }, [channelId, addMessage, isConnected, subscribeChannel]);

    const loadChannel = useCallback(() => {
        getChannel(channelId).then(setChannel);
    }, [channelId]);

    const loadInitMessages = useCallback(() => {
        getChannelMessages(channelId).then((response) => {
            setMessages((current) => mergeMessages(current, response.messages));
            setHasPrev(response.hasPrev);
        });
    }, [channelId]);

    function loadPrevMessages() {
        if (isPending || !hasPrev || messages.length === 0) return;

        setIsPending(true);

        const cursor = messages[0].messageId;

        getChannelMessages(channelId, cursor)
            .then((prev) => {
                setHasPrev(prev.hasPrev);
                setMessages((current) => mergeMessages(current, prev.messages));
            })
            .finally(() => setIsPending(false));
    }

    useEffect(() => {
        loadChannel();
        loadInitMessages();
    }, [loadChannel, loadInitMessages]);

    if (!channel) {
        return null;
    }

    return (
        <div className="flex min-h-0 flex-1 flex-col">
            <ChannelHeader channel={channel} />
            <ChannelBody
                channelMembers={channel.channelMemberList}
                messages={messages}
                loadPrevMessages={loadPrevMessages}
                hasPrev={hasPrev}
            />
            <MessageInput channelId={channelId} onSent={addMessage} />
        </div>
    );
}
